package com.example.androidgame;

import com.example.aengine.AAudio;
import com.example.aengine.AGraphics;
import com.example.aengine.AInput;
import com.example.aengine.ASound;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
    private int numCols, numRows,  initLives, currentLives;
    private Cell[][] board;
    private ArrayList<Integer>[] colsList;
    private ArrayList<Integer>[] rowsList;
    private Cell pressedCell;
    private AGraphics graphics;
    private AAudio audio;

    private boolean win = false;
    private int margin = 50;
    private int textMessagesSize;
    private ASound cellSound, failSound;

    public Board(int id,int nC, int nR, AGraphics graphics_, AAudio audio_){
        this.initLives = this.currentLives = 3;
        this.graphics = graphics_;
        this.audio = audio_;
        this.textMessagesSize = this.graphics.getLogicWidth() / 20;
        int cellSize = Math.min(this.graphics.getLogicWidth() - this.margin * 3, this.graphics.getLogicHeight() - margin * 3);
        if (id == 0) {
            this.numCols = nC;
            this.numRows = nR;
            this.board = new Cell[this.numRows][this.numCols];
            int numCells = nC * nR;
            int cont = 0;
            for (int i = 0; i < this.numRows; i++) {
                for (int j = 0; j < this.numCols; j++) {
                    boolean isSol = false;

                    if (cont < numCells * 0.75) {
                        int rnd = (int) (Math.random() * numCells);
                        isSol = rnd < numCells * 0.65; //En teoría se añaden el 65% de las casillas
                        if (isSol) cont++;
                    }
                    this.board[i][j] = new Cell(i, j, cellSize / this.numCols,
                            cellSize / this.numRows, isSol, this.graphics);
                }
            }

            while (cont < numCells * 0.3) {
                int numRandX = (int) (Math.random() * this.numCols);
                int numRandY = (int) (Math.random() * this.numRows);

                if (!this.board[numRandY][numRandX].isSolution()) {
                    this.board[numRandY][numRandX].setSolution(true);
                    cont++;
                }
            }
        }
        else
        {

           System.out.println("Aqui va la generacion automatica");
/*
            //Leer un archivo
            File levelFile = new File();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(levelFile)));
                this.numRows = Integer.parseInt(br.readLine());
                this.numCols = Integer.parseInt(br.readLine());

                this.board = new Cell[this.numRows][this.numCols];

                for (int i = 0; i < this.numRows; i++){
                    String row = br.readLine();
                    for (int j = 0; j < this.numCols; j++){
                        char aux = row.charAt(j);
                        boolean isSol;
                        if (aux == '1'){
                            isSol = true;
                        }
                        else if (aux == '0'){
                            isSol = false;
                        }
                        else throw new Exception("El nivel está mal creado.");

                        this.board[i][j] = new Cell(i, j, cellSize / this.numCols,
                                cellSize / this.numRows, isSol, this.graphics);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
*/
        }

        //Aquí la generación de las casillas ya se ha terminado.
        clueGenerator();
        createSounds();
    }

    private void clueGenerator(){
        //Inicializar listas de pistas
        this.colsList = new ArrayList[this.numCols];
        this.rowsList = new ArrayList[this.numRows];

        for (int i = 0; i < this.numCols; i++){
            this.colsList[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < this.numRows; i++){
            this.rowsList[i] = new ArrayList<Integer>();
        }

        boolean thisCell = false;
        int sum = 0; //Sumador de casillas consecutivas anteriores a esta

        //COLUMNAS
        for (int j = 0; j < this.numCols; j++){
            for (int i = 0; i < this.numRows; i++){
                thisCell = this.board[i][j].isSolution();
                if (thisCell) {
                    sum++;

                    //Si ya es la última fila de la columna, se añade a la lista
                    if (i == (this.numRows - 1)){
                        this.colsList[j].add(sum);
                        sum = 0;
                    }
                }
                else{
                    //Si anteriormente a esta casilla, traía un sum, se añade a la lista
                    if (sum > 0) {
                        this.colsList[j].add(sum);
                        sum = 0;
                    }
                }
            }
        }

        //FILAS
        for (int i = 0; i < this.numRows; i++){
            for (int j = 0; j < this.numCols; j++){
                thisCell = this.board[i][j].isSolution();
                if (thisCell) {
                    sum++;

                    //Si ya es la última fila de la columna, se añade a la lista
                    if (j == (this.numCols - 1)){
                        this.rowsList[i].add(sum);
                        sum = 0;
                    }
                }
                else{
                    //Si anteriormente a esta casilla, traía un sum, se añade a la lista
                    if (sum > 0) {
                        this.rowsList[i].add(sum);
                        sum = 0;
                    }
                }
            }
        }
    }

    public void render() {
        int cellSize = Math.min(this.graphics.getLogicWidth() - this.margin*3, this.graphics.getLogicHeight() - margin*3);
        int width = this.graphics.getLogicWidth();
        int height = this.graphics.getLogicHeight();
        // Ancho y alto de cada casilla
        int casillaW = cellSize/numCols;
        int casillaH = cellSize/this.numRows;
        // Distancia de separación entre casillas
        int separacion = casillaW/10;
        // Ancho y alto del tablero
        int anchoTablero = this.numCols*casillaW + (this.numCols-1)*separacion;
        int altoTablero = this.numRows*casillaH + (this.numRows-1)*separacion;
        // Posición desde la que se generan las casillas; partimos de la mitad de la pantalla y restamos la mitad de lo que ocupa el tablero para que este quede centrado
        int xInicial = width/2-anchoTablero/2+this.margin/2;
        int yInicial = height/2-altoTablero/2;
        // Renderizado de casillas
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numCols; j++) {
                if (!this.win || this.board[i][j].isSolution())
                    this.board[i][j].render(xInicial+i*(casillaW+separacion),yInicial+j*(casillaH+separacion), this.graphics);
            }
        }

        if (!this.win && currentLives > 0){
            // Se dibujan las pistas de las filas
            int mayorFilas = 1;
            int textSize = casillaW/2;
            for (int i = 0; i < this.numRows; i++) {
                if (mayorFilas < this.colsList[i].size()) mayorFilas = this.colsList[i].size();
                for (int j = 0; j < this.colsList[i].size() ;j++)
                    graphics.drawText(this.colsList[i].get(j).toString(),
                            xInicial-this.colsList[i].size()*textSize+j*textSize,
                            yInicial+casillaH/2+i*(casillaH+separacion)+separacion,
                            textSize, graphics.newColor(0, 0, 0, 255));
            }
            int mayorCols = 1;
            // Se dibujan las pistas de las columnas
            for (int i = 0; i < this.numRows; i++) {
                if (mayorCols < this.rowsList[i].size()) mayorCols = this.rowsList[i].size();
                for (int j = 0; j < this.rowsList[i].size() ;j++)
                    graphics.drawText(this.rowsList[i].get(j).toString(),
                            xInicial+casillaW/2+i*(casillaW+separacion)-separacion,
                            yInicial-this.rowsList[i].size()*textSize+j*textSize+textSize/2,
                            textSize, graphics.newColor(0, 0, 0, 255));
            }

            // Dibujado de rectangulos para el borde
            // Arriba
            graphics.drawRectangle(xInicial,yInicial-(mayorCols+1)*textSize, anchoTablero, altoTablero+(mayorCols+1)*textSize, graphics.newColor(0,0,0,255));
            // Abajo
            graphics.drawRectangle(xInicial-(mayorFilas+1)*textSize, yInicial, anchoTablero+(mayorFilas+1)*textSize, altoTablero, graphics.newColor(0,0,0,255));

        }
        else {
            if(currentLives == 0) {
                graphics.drawText(this.graphics.newFont("font.TTF", false), "YOU LOSE",
                        xInicial + 35,
                        yInicial + this.numRows * (casillaH + separacion) + this.textMessagesSize,
                        this.textMessagesSize, graphics.newColor(200, 0, 0, 255));
            }
            else {
                graphics.drawText(this.graphics.newFont("font.TTF", false), "YOU WIN",
                        xInicial + 35,
                        yInicial + this.numRows * (casillaH + separacion) + this.textMessagesSize,
                        this.textMessagesSize, graphics.newColor(0, 200, 0, 255));
            }
        }

    }

    public void handleInputs(AInput.Event event) {
        if(event.type == AInput.InputType.TOUCH_RELEASED && checkWin()) return;
        if(((AInput.TouchInputEvent)event).mouseButton == 1){
            checkCellsCollision(((AInput.TouchInputEvent)event).x, ((AInput.TouchInputEvent)event).y, event.type);
        }
    }

    private void checkCellsCollision(float mouseX, float mouseY, AInput.InputType type){
        int i = 0, j;
        boolean checked = false;
        while(i < this.numCols && !checked){
            j = 0;
            while(j < this.numRows && !checked){
                if(this.board[i][j].checkCollisions(mouseX, mouseY)) {
                    checked = true;
                    switch (type) {
                        case TOUCH_PRESSED:
                            this.pressedCell = this.board[i][j];
                            break;
                        case TOUCH_RELEASED:
                           if(this.pressedCell == this.board[i][j])
                               if(!this.board[i][j].changeState()){
                                   this.failSound.play(); this.currentLives--;
                               }
                               else this.cellSound.play();
                            break;
                        case LONG_TOUCH:
                            this.board[i][j].changeStateToRemoved();
                            break;
                    }
                } j++;
            } i++;
        }
    }

    public boolean checkWin() {
        int i = 0, j;
        this.win = true;
        while(i <this.numCols && this.win){
            j = 0;
            while (j<this.numRows && this.win) {
                this.win = this.board[i][j].check();
                j++;
            }
            i++;
        }
        return this.win;
    }

    private void createSounds(){
        this.cellSound = this.audio.newSound("cell.wav");
        this.failSound = this.audio.newSound("fail.wav");
        this.audio.setVolume(this.failSound, 0.5f);
    }

    public void updateGraphics(AGraphics graphics_) {
        this.graphics = graphics_;
        for(int i=0; i < this.numRows; i++){
            for (int j = 0; j < this.numCols; j++){
                this.board[i][j].updateGraphics(graphics_);
            }
        }
    }

    public int getInitLives(){return this.initLives;}

    public int getCurrentLives(){return this.currentLives;}
}
