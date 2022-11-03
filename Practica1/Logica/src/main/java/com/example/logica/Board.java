package com.example.logica;
import com.example.engine.IAudio;
import com.example.engine.IGraphics;
import com.example.engine.IInput;
import com.example.engine.ISound;

import java.util.ArrayList;

public class Board {
    private int numCols, numRows;
    private Cell[][] board;
    private ArrayList<Integer>[] colsList;
    private ArrayList<Integer>[] rowsList;
    private Cell pressedCell;
    private IGraphics graphics;
    private IAudio audio;

    private boolean checkPressed = false;
    private  boolean showLevelInfo = false;
    private int wrongCells = 0;
    private int remainingCells = 0;
    private boolean win = false;

    private ISound cellSound;
    private ISound winSound;
    private ISound failSound;

    public Board(int nC, int nR, IGraphics graphics_, IAudio audio_){
        this.numCols = nC;
        this.numRows = nR;
        this.board = new Cell[numRows][numCols];
        this.graphics = graphics_;
        this.audio = audio_;
        int numCells = nC * nR;
        int cellSize = Math.min(this.graphics.getWidth(), this.graphics.getHeight());
        int windowRelativeSize = 3;
        int cont = 0;
        for(int i=0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                boolean isSol = false;

                if (cont < numCells * 0.75){
                    int rnd = (int) (Math.random() * numCells);
                    isSol = rnd < numCells * 0.65; //En teoría se añaden el 65% de las casillas
                    if (isSol) cont++;
                }
                board[i][j] = new Cell(i,j, cellSize/numRows/windowRelativeSize,
                        cellSize/numCols/windowRelativeSize, isSol, graphics);
            }
        }

        while (cont < numCells * 0.3){
            int numRandX = (int) (Math.random() * numCols);
            int numRandY = (int) (Math.random() * numRows);

            if (!board[numRandY][numRandX].isSolution()){
                board[numRandY][numRandX].setSolution(true);
                cont++;
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


        boolean thisCell = false;
        int sum = 0; //Sumador de casillas consecutivas anteriores a esta

        //COLUMNAS
        for (int j = 0; j < numCols; j++){
            for (int i = 0; i < numRows; i++){
                thisCell = board[i][j].isSolution();
                if (thisCell) {
                    sum++;

                    //Si ya es la última fila de la columna, se añade a la lista
                    if (i == (numRows - 1)){
                        colsList[j].add(sum);
                        sum = 0;
                    }
                }
                else{
                    //Si anteriormente a esta casilla, traía un sum, se añade a la lista
                    if (sum > 0) {
                        colsList[j].add(sum);
                        sum = 0;
                    }
                }
            }
        }

        //FILAS
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                thisCell = board[i][j].isSolution();
                if (thisCell) {
                    sum++;

                    //Si ya es la última fila de la columna, se añade a la lista
                    if (j == (numCols - 1)){
                        rowsList[i].add(sum);
                        sum = 0;
                    }
                }
                else{
                    //Si anteriormente a esta casilla, traía un sum, se añade a la lista
                    if (sum > 0) {
                        rowsList[i].add(sum);
                        sum = 0;
                    }
                }
            }
        }

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
    
    public void render(IGraphics graphics) {
        int width = graphics.getLogicWidth();
        int height = graphics.getLogicHeight();
        // Ancho y alto de cada casilla
        int casillaW = board[0][0].w;
        int casillaH = board[0][0].h;
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
                board[i][j].render(graphics,xInicial+i*(casillaW+separacion),yInicial+j*(casillaH+separacion));
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
        if (showLevelInfo)
        {
            if (win) graphics.drawText("Felicidades, has ganado",
                    xInicial,
                    yInicial/2,
                    textSize, graphics.newColor(0, 200, 0, 255));
            else
            {
                if (remainingCells != 0) graphics.drawText("Te faltan " + remainingCells + " casillas",
                        xInicial,
                        yInicial/2,
                        textSize, graphics.newColor(0, 200, 0, 255));
                if (wrongCells != 0) graphics.drawText("Tienes mal " + wrongCells + " casillas",
                       xInicial,
                        yInicial/2,
                        textSize, graphics.newColor(0, 200, 0, 255));
            }
        }
        // Dibujado de rectangulos para el borde
        graphics.drawRectangle(xInicial,yInicial-4*textSize, anchoTablero, altoTablero+4*textSize, graphics.newColor(0,0,0,255));
        graphics.drawRectangle(xInicial-4*textSize, yInicial, anchoTablero+4*textSize, altoTablero, graphics.newColor(0,0,0,255));
    }

    public void handleInputs(IInput.Event event) {
        switch (event.type) {
            case TOUCH_PRESSED:
                if(((IInput.MouseInputEvent)event).mouseButton == 1){
                    checkCellsCollision(((IInput.MouseInputEvent)event).x, ((IInput.MouseInputEvent)event).y, true);
                }
                break;
            case TOUCH_RELEASED:
                if(((IInput.MouseInputEvent)event).mouseButton == 1){
                    checkCellsCollision(((IInput.MouseInputEvent)event).x, ((IInput.MouseInputEvent)event).y, false);
                }
                break;
            case KEY_DOWN:
                if (((IInput.KeyInputEvent)event).key == 'A') {
                    checkWin();
                }
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
                    if(board[i][j].checkCollisions(mouseX, mouseY)){
                        checked = true;
                        pressedCell = board[i][j];
                    }
                }
                else{
                    if(board[i][j].checkCollisions(mouseX, mouseY)){
                        checked = true;
                        if(pressedCell== board[i][j]){
                            board[i][j].changeState();
                            this.audio.playSound(this.cellSound);
                        }
                    }
                }
                j++;
            }
            i++;
        }
    }

    public boolean checkWin() {
        this.checkPressed = true;
        this.showLevelInfo = true;
        remainingCells = 0;
        wrongCells = 0;
        for (int i = 0; i < numCols; i++)
        {
            for (int j = 0; j < numRows; j++)
            {
                int res = board[i][j].check();
                if (res == 2) wrongCells++;
                else if (res == 1) remainingCells++;
            }
        }
        if (remainingCells == 0 && wrongCells == 0) {
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

    public void checkOut() {
        this.showLevelInfo = false;

        int i = 0;
        while (wrongCells > 0 && i < numCols){
            int j = 0;
            while (wrongCells > 0 && j < numRows) {
                if (board[i][j].isIncorrect()){
                    board[i][j].checkOut();
                    wrongCells--;
                }
                j++;
            }
            i++;
        }
    }

    public boolean getCheckPressed() { return checkPressed; }

    public void pressedOut() {
        this.checkPressed = false;
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
}
