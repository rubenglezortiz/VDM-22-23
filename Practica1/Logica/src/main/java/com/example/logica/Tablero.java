package com.example.logica;
import com.example.engine.IColor;
import com.example.engine.IGraphics;
import com.example.engine.IInput;

import java.awt.Color;
import java.util.ArrayList;

public class Tablero {


    int numCols, numRows;
    Casilla[][] tablero;
    ArrayList<Integer>[] colsList;
    ArrayList<Integer>[] rowsList;

    public Tablero(int nC, int nR,IGraphics graphics){
        numCols = nC;
        numRows = nR;
        tablero = new Casilla[numRows][numCols];

        //ArrayList<Boolean> loteria = new ArrayList<Boolean>();
        int numCasillas = nC * nR;
        //int numSolucion = (int) (Math.random() * (numCasillas * 0.3) + (numCasillas * 0.4));

        //for (int i = 0; i < numSolucion; i++) loteria.add(true);
        //for (int i = numSolucion; i < numCasillas; i++) loteria.add(false);

        int contador = 0;
        for(int i=0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                boolean isSol = false;

                if (contador < numCasillas * 0.75){
                    int rnd = (int) (Math.random() * numCasillas);
                    isSol = rnd < numCasillas * 0.65; //En teoría se añaden el 65% de las casillas
                    if (isSol) contador++;
                }

                tablero[i][j] = new Casilla(i,j, isSol,graphics);
            }
        }

        while (contador < numCasillas * 0.3){
            int numRandX = (int) (Math.random() * numCols);
            int numRandY = (int) (Math.random() * numRows);

            if (!tablero[numRandY][numRandX].isSolucion()){
                tablero[numRandY][numRandX].setSolucion(true);
                contador++;
            }
        }

        //Aquí la generación de las casillas ya se ha terminado.


        // ----------------- GENERACIÓN DE PISTAS ---------------------

        //Inicializar listas de pistas
        colsList = new ArrayList[numCols];
        rowsList = new ArrayList[numRows];

        for (int i = 0; i < numCols; i++){
            colsList[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < numRows; i++){
            rowsList[i] = new ArrayList<Integer>();
        }


        boolean estaCasilla = false;
        int sumador = 0; //Sumador de casillas consecutivas anteriores a esta

        //COLUMNAS
        for (int j = 0; j < numCols; j++){
            for (int i = 0; i < numRows; i++){
                estaCasilla = tablero[i][j].isSolucion();
                if (estaCasilla) {
                    sumador++;

                    //Si ya es la última fila de la columna, se añade a la lista
                    if (i == (numRows - 1)){
                        colsList[j].add(sumador);
                        sumador = 0;
                    }
                }
                else{
                    //Si anteriormente a esta casilla, traía un sumador, se añade a la lista
                    if (sumador > 0) {
                        colsList[j].add(sumador);
                        sumador = 0;
                    }
                }
            }
        }

        //FILAS
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                estaCasilla = tablero[i][j].isSolucion();
                if (estaCasilla) {
                    sumador++;

                    //Si ya es la última fila de la columna, se añade a la lista
                    if (j == (numCols - 1)){
                        rowsList[i].add(sumador);
                        sumador = 0;
                    }
                }
                else{
                    //Si anteriormente a esta casilla, traía un sumador, se añade a la lista
                    if (sumador > 0) {
                        rowsList[i].add(sumador);
                        sumador = 0;
                    }
                }
            }
        }

        // ----------------------------- RENDER ------------------------------------
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++) {
                if (tablero[i][j].isSolucion()) System.out.print(" x ");
                else System.out.print(" . ");
            }
            System.out.println("");
        }

        System.out.println("LISTAS COLUMNAS");
        System.out.println("");

        for (int i = 0; i < numCols; i++){
            for (int j = 0; j < colsList[i].size(); j++) {
                System.out.print(colsList[i].get(j));
                System.out.print(" ");
            }
            System.out.println("");
        }
        System.out.println("");

        System.out.println("LISTAS FILAS");
        System.out.println("");

        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < rowsList[i].size(); j++) {
                System.out.print(rowsList[i].get(j));
                System.out.print(" ");
            }
            System.out.println("");
        }
        System.out.println("");

    }

    //Método que devuelve la cantidad de casillas que te faltan por pintar, y en caso de que no te falten, si
    //la solución es correcta o no.

    public int comprueba(boolean finish){
        int contador = 0;
        finish = true;

        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                contador += tablero[i][j].comprueba();

                if (finish && tablero[i][j].pintadaErronea()) finish = false;
            }
        }

        if (contador > 0) finish = false;
        return contador;
    }

    public void render(IGraphics graphics) {
        int width = graphics.getWidth();
        int height = graphics.getHeight();
        // Ancho y alto de cada casilla
        int casillaW = tablero[0][0].w;
        int casillaH = tablero[0][0].h;
        // Distancia de separación entre casillas
        int separacion = casillaW/10;
        // Ancho y alto del tablero
        int anchoTablero = numCols*casillaW + (numCols-1)*separacion;
        int altoTablero = numRows*casillaH + (numRows-1)*separacion;
        // Posición desde la que se generan las casillas; partimos de la mitad de la pantalla y restamos la mitad de lo que ocupa el tablero para que este quede centrado
        int xInicial = width/2-anchoTablero/2;
        int yInicial = height/2-altoTablero/2;
        // Renderizado de casillas
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                tablero[i][j].render(graphics, numRows, numCols,xInicial+i*(casillaW+separacion),yInicial+j*(casillaH+separacion));
            }
        }

        // Se dibujan las pistas de las filas
        int textSize = casillaW/2;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < colsList[i].size() ;j++)
                graphics.drawText(colsList[i].get(j).toString(), xInicial-colsList[i].size()*textSize+j*textSize,yInicial+casillaH/2+i*(casillaH+separacion)+separacion, textSize, graphics.newColor(200, 0, 0, 255));
        }
        // Se dibujan las pistas de las columnas
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < rowsList[i].size() ;j++)
                graphics.drawText(rowsList[i].get(j).toString(), xInicial+casillaW/2+i*(casillaW+separacion)-separacion,yInicial-rowsList[i].size()*textSize+j*textSize+textSize/2 , textSize, graphics.newColor(200, 0, 0, 255));
        }
    }

    public void handleInputs(IInput.Event event) {
        switch (event.type) {
            case TOUCH_PRESSED:
                break;
            case TOUCH_RELEASED:
                break;
            case TOUCH_CLICKED:

                break;
            default:
                break;
        }
    }
}
