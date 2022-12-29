package com.example.logica;
import com.example.engine.IAudio;
import com.example.engine.IGraphics;
import com.example.engine.IInput;
import java.util.ArrayList;

public class Board {
    private final int numCols, numRows;
    private Cell[][] board;
    private final ArrayList<Integer>[] colsList;
    private final ArrayList<Integer>[] rowsList;
    private Cell pressedCell;

    private boolean checkPressed = false;
    private  boolean showLevelInfo = false;
    private int wrongCells = 0;
    private int remainingCells = 0;
    private boolean win = false;
    private final int margin = 50;
    private final int textMessagesSize;
    private final String cellSound;

    public Board(int nC, int nR, IGraphics graphics_, IAudio audio_){
        this.numCols = nC;
        this.numRows = nR;
        this.board = new Cell[numRows][numCols];
        this.textMessagesSize = (int)graphics_.getLogicWidth()/40;
        this.cellSound = "cell.wav";
        int numCells = nC * nR;
        int cellSize = Math.min((int)graphics_.getLogicWidth() - this.margin*3, (int)graphics_.getLogicHeight() - margin*3);
        int cont = 0;
        for(int i=0; i < this.numRows; i++){
            for (int j = 0; j < this.numCols; j++){
                boolean isSol = false;

                if (cont < numCells * 0.75){
                    int rnd = (int) (Math.random() * numCells);
                    isSol = rnd < numCells * 0.65; //En teoría se añaden el 65% de las casillas
                    if (isSol) cont++;
                }
                this.board[i][j] = new Cell(i,j, cellSize/this.numCols,
                        cellSize/this.numRows, isSol, graphics_);
            }
        }

        while (cont < numCells * 0.3){
            int numRandX = (int) (Math.random() * this.numCols);
            int numRandY = (int) (Math.random() * this.numRows);

            if (!this.board[numRandY][numRandX].isSolution()){
                this.board[numRandY][numRandX].setSolution(true);
                cont++;
            }
        }

        //Aquí la generación de las casillas ya se ha terminado.


        // ----------------- GENERACIÓN DE PISTAS ---------------------

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
        createSounds(audio_);
    }

    public void render(IGraphics graphics) {
        int width = (int)graphics.getLogicWidth();
        int height = (int)graphics.getLogicHeight();
        // Ancho y alto de cada casilla
        int casillaW = this.board[0][0].w;
        int casillaH = this.board[0][0].h;
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
                    this.board[i][j].render(graphics,xInicial+i*(casillaW+separacion),yInicial+j*(casillaH+separacion));
            }
        }


        if (!this.win){
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

        if (this.showLevelInfo)
        {
            if (this.win) graphics.drawText("Felicidades, has ganado",
                    xInicial,
                    yInicial+this.numRows*(casillaH+separacion)+this.textMessagesSize,
                    this.textMessagesSize, graphics.newColor(0, 200, 0, 255));
            else
            {
                if (this.remainingCells != 0) graphics.drawText("Te faltan " + this.remainingCells + " casillas",
                        xInicial,
                        yInicial+this.numRows*(casillaH+separacion)+this.textMessagesSize,
                    this.textMessagesSize, graphics.newColor(200, 0, 0, 255));
                if (this.wrongCells != 0) graphics.drawText("Tienes mal " + this.wrongCells + " casillas",
                       xInicial,
                        yInicial+this.numRows*(casillaH+separacion)+this.textMessagesSize+this.textMessagesSize,
                        this.textMessagesSize, graphics.newColor(200, 0, 0, 255));
            }
        }
    }

    public void handleInputs(IInput.MyInputEvent event, IAudio audio) {
        switch (event.type) {
            case TOUCH_PRESSED:
                if((event.mouseButton == 1)){
                    checkCellsCollision(audio, event.x, event.y, true);
                }
                break;
            case TOUCH_RELEASED:
                if(event.mouseButton == 1){
                    checkCellsCollision(audio, event.x, event.y, false);
                }
                break;
            default:
                break;
        }
    }

    private void checkCellsCollision(IAudio audio, int mouseX, int mouseY, boolean pressed){
        int i = 0, j = 0;
        boolean checked = false;

        while(i < this.numCols && !checked){
            j = 0;
            while(j < this.numRows && !checked){
                if(pressed){
                    if(this.board[i][j].checkCollisions(mouseX, mouseY)){
                        checked = true;
                        this.pressedCell = this.board[i][j];
                    }
                }
                else{
                    if(this.board[i][j].checkCollisions(mouseX, mouseY)){
                        checked = true;
                        if(this.pressedCell== this.board[i][j]){
                            this.board[i][j].changeState();
                            audio.playSound(this.cellSound);
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
        this.remainingCells = 0;
        this.wrongCells = 0;
        for (int i = 0; i < this.numCols; i++)
        {
            for (int j = 0; j < this.numRows; j++)
            {
                int res = this.board[i][j].check();
                if (res == 2) this.wrongCells++;
                else if (res == 1) this.remainingCells++;
            }
        }
        this.win = (this.remainingCells == 0 && this.wrongCells == 0);
        return this.win;
    }

    public void checkOut() {
        this.showLevelInfo = false;

        int i = 0;
        while (this.wrongCells > 0 && i < this.numCols){
            int j = 0;
            while (this.wrongCells > 0 && j < this.numRows) {
                if (this.board[i][j].isIncorrect()){
                    this.board[i][j].checkOut();
                    this.wrongCells--;
                }
                j++;
            }
            i++;
        }
    }

    public boolean getCheckPressed() { return this.checkPressed; }

    public void pressedOut() {
        this.checkPressed = false;
    }

    private void createSounds(IAudio audio){
        audio.newSound(this.cellSound);
    }
}
