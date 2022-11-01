package com.example.logica;
import com.example.engine.IAudio;
import com.example.engine.IGraphics;
import com.example.engine.IInput;
import com.example.engine.ISound;

import java.util.ArrayList;

public class Tablero {
    private int numCols, numRows;
    private Casilla[][] tablero;
    private ArrayList<Integer>[] colsList;
    private ArrayList<Integer>[] rowsList;
    private boolean mousePressed;
    private Casilla pressedCell;
    private IGraphics graphics;
    private IAudio audio;

    private boolean checkPressed = false;
    private int casErroneas = 0;
    private int casRestantes = 0;
    private boolean win = false;

    private ISound cellSound;
    private ISound winSound;
    private ISound failSound;

    public Tablero(int nC, int nR,IGraphics graphics_, IAudio audio_){
        this.numCols = nC;
        this.numRows = nR;
        this.tablero = new Casilla[numRows][numCols];
        this.graphics = graphics_;
        this.audio = audio_;
        int numCasillas = nC * nR;
        int casillaSize = Math.min(this.graphics.getWidth(), this.graphics.getHeight());
        int windowRelativeSize = 3;
        int contador = 0;
        for(int i=0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                boolean isSol = false;

                if (contador < numCasillas * 0.75){
                    int rnd = (int) (Math.random() * numCasillas);
                    isSol = rnd < numCasillas * 0.65; //En teoría se añaden el 65% de las casillas
                    if (isSol) contador++;
                }
                tablero[i][j] = new Casilla(i,j, casillaSize/numRows/windowRelativeSize,
                        casillaSize/numCols/windowRelativeSize, isSol, graphics);
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

        mousePressed = false;

        createSounds();
       // // ----------------------------- RENDER ------------------------------------
       // for (int i = 0; i < numRows; i++){
       //     for (int j = 0; j < numCols; j++) {
       //         if (tablero[i][j].isSolucion()) System.out.print(" x ");
       //         else System.out.print(" . ");
       //     }
       //     System.out.println("");
       // }

       // System.out.println("LISTAS COLUMNAS");
       // System.out.println("");

       // for (int i = 0; i < numCols; i++){
       //     for (int j = 0; j < colsList[i].size(); j++) {
       //         System.out.print(colsList[i].get(j));
       //         System.out.print(" ");
       //     }
       //     System.out.println("");
       // }
       // System.out.println("");

       // System.out.println("LISTAS FILAS");
       // System.out.println("");

       // for (int i = 0; i < numRows; i++){
       //     for (int j = 0; j < rowsList[i].size(); j++) {
       //         System.out.print(rowsList[i].get(j));
       //         System.out.print(" ");
       //     }
       //     System.out.println("");//
       // }
       // System.out.println("");
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
        int width = graphics.getLogicWidth();
        int height = graphics.getLogicHeight();
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
                tablero[i][j].render(graphics,xInicial+i*(casillaW+separacion),yInicial+j*(casillaH+separacion));
            }
        }

        // Se dibujan las pistas de las filas
        int textSize = casillaW/2;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < colsList[i].size() ;j++)
                graphics.drawText(colsList[i].get(j).toString(),
                        xInicial-colsList[i].size()*textSize+j*textSize,
                        yInicial+casillaH/2+i*(casillaH+separacion)+separacion,
                            textSize, graphics.newColor(0, 0, 0, 255));
        }
        // Se dibujan las pistas de las columnas
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < rowsList[i].size() ;j++)
                graphics.drawText(rowsList[i].get(j).toString(),
                        xInicial+casillaW/2+i*(casillaW+separacion)-separacion,
                       yInicial-rowsList[i].size()*textSize+j*textSize+textSize/2,
                        textSize, graphics.newColor(0, 0, 0, 255));
        }
        if (checkPressed)
        {
            if (win) graphics.drawText("Felicidades, has ganado",
                    xInicial,
                    yInicial/2,
                    textSize, graphics.newColor(0, 200, 0, 255));
            else
            {
                if (casRestantes != 0) graphics.drawText("Te faltan " + casRestantes + " casillas",
                        xInicial,
                        yInicial/2,
                        textSize, graphics.newColor(0, 200, 0, 255));
                if (casErroneas != 0) graphics.drawText("Tienes mal " + casErroneas + " casillas",
                       xInicial,
                        yInicial/2,
                        textSize, graphics.newColor(0, 200, 0, 255));
            }
        }
        // Dibujado de rectangulos para el borde
        graphics.drawRectangle(xInicial,
                            yInicial-4*textSize,
                            anchoTablero,
                            altoTablero+4*textSize,
                            graphics.newColor(0,0,0,255));

        graphics.drawRectangle(xInicial-4*textSize,
                            yInicial,
                           anchoTablero+4*textSize,
                            altoTablero,
                            graphics.newColor(0,0,0,255));
    }

    public void handleInputs(IInput.Event event) {
        switch (event.type) {
            case TOUCH_PRESSED:
                if(((IInput.MouseInputEvent)event).mouseButton == 1){
                    mousePressed = true;
                    checkCellsCollision(((IInput.MouseInputEvent)event).x, ((IInput.MouseInputEvent)event).y, true);
                }
                break;
            case TOUCH_RELEASED:
                if(((IInput.MouseInputEvent)event).mouseButton == 1){
                    checkCellsCollision(((IInput.MouseInputEvent)event).x, ((IInput.MouseInputEvent)event).y, false);
                }
                break;
            case KEY_DOWN:
                if (((IInput.KeyInputEvent)event).key == 'A')
                    checkWin();
                break;
            default:
                break;
        }
    }

    private void checkCellsCollision(int mouseX, int mouseY, boolean pressed){
        int i = 0, j = 0;
        boolean checked = false;

        while(i < numCols && !checked){
            j = 0;
            while(j < numRows && !checked){
                if(pressed){
                    if(tablero[i][j].checkCollisions(mouseX, mouseY)){
                        checked = true;
                        pressedCell = tablero[i][j];
                    }
                }
                else{
                    if(tablero[i][j].checkCollisions(mouseX, mouseY)){
                        checked = true;
                        if(pressedCell==tablero[i][j]){
                            tablero[i][j].cambiaEstado();
                            this.audio.playSound(this.cellSound);
                        }
                    }
                }
                j++;
            }
            i++;
        }
    }

    public boolean checkWin()
    {
        checkPressed = true;
        casRestantes = 0;
        casErroneas = 0;
        for (int i = 0; i < numCols; i++)
        {
            for (int j = 0; j < numRows; j++)
            {
                int res = tablero[i][j].comprueba();
                if (res == 2) casErroneas++;
                else if (res == 1) casRestantes++;
            }
        }
        if (casRestantes == 0 && casErroneas == 0) {
            win = true;
            this.audio.playSound(this.winSound);
            return true;
        }
        else {
            win = false;
            this.audio.playSound(this.failSound);
            return false;
        }
    }

    private void createSounds(){
        //Cell sound
        this.cellSound = this.audio.newSound("cell.wav");

        //Win sound
        this.winSound = this.audio.newSound("win.wav");
        this.audio.setVolume(this.winSound, 0.75f);

        //Fail sound
        this.failSound = this.audio.newSound("fail.wav");
        this.audio.setVolume(this.failSound, 0.5f);
    }

    public boolean getCheckPressed() { return checkPressed; }

    public void checkOut() {
        checkPressed = false;

        int i = 0;
        while (casErroneas > 0 && i < numCols){
            int j = 0;
            while (casErroneas > 0 && j < numRows) {
                if (tablero[i][j].esCorregida()){
                    tablero[i][j].checkOut();
                    casErroneas--;
                }
                j++;
            }
            i++;
        }
    }
}
