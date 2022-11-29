package com.example.androidgame;

import com.example.aengine.AColor;
import com.example.aengine.AGraphics;

public class Cell extends GameObject {
    enum State {UNMARKED, MARKED, REMOVED, INCORRECT};
    private boolean solution;
    private int row, col;
    private State state;


    public Cell(int row_, int col_, int w, int h, boolean sol, AGraphics graphics_){
        super(w, h, graphics_);
        this.state = State.UNMARKED;
        this.row = row_;
        this.col = col_;
        this.solution = sol;
    }

    public boolean isSolution() {
        return this.solution;
    }

    public void setSolution(boolean solution_) { this.solution = solution_; }

    public void changeState(){
        switch (state){
            case UNMARKED: this.state = State.MARKED; break;
            case MARKED: this.state = State.REMOVED; break;
            case REMOVED: this.state = State.UNMARKED; break;
            case INCORRECT: this.state = State.UNMARKED; break;
        }
    }

    public boolean wrongMarked(){
        return (this.state == State.MARKED && !solution) || (state == State.INCORRECT);
    }

    public int check(){
        if (this.solution && (this.state == State.UNMARKED || state == State.REMOVED))
            return 1; //Devuelve 1 para sumar esta casilla a la cantidad que faltan por pintar

        if (wrongMarked()) {
            this.state = State.INCORRECT;
            return 2;
        }

        return 0;
    }

    public void render(int xGraphic_, int yGraphic_, AGraphics graphics_){
        this.graphics = graphics_;
        AColor icolor;
        switch (state){
            case UNMARKED:
                icolor = graphics.newColor(200,200,200,255); // Grey
                break;
            case MARKED:
                icolor = graphics.newColor(0,0,255,255); // BLue
                break;
            case REMOVED:
                icolor = graphics.newColor(0,0,0,255); // White
                break;
            case INCORRECT:
                icolor = graphics.newColor(255,0,0,255); // Red
                break;
            default:
                icolor = graphics.newColor(255,255,255,255); // White
                break;
        }
        //if(isSolucion())  icolor = graphics.newColor(255,0,0,255); // Debug
        this.x = xGraphic_;
        this.y = yGraphic_;
        if (this.state == State.REMOVED) {
            graphics.drawRectangle(this.x, this.y, this.w, this.h, icolor);
            graphics.drawLine( this.x, this.y,this.x+w, this.y +this.h, icolor);
        }
        else graphics.fillRectangle(this.x, this.y, this.w, this.h, icolor);
    }

    public boolean checkCollisions(int x, int y) {
        int x1Aux = this.graphics.realToLogicX(this.x);
        int x2Aux = this.graphics.realToLogicX(this.x+ this.w);
        int y1Aux = this.graphics.realToLogicX(this.y);
        int y2Aux = this.graphics.realToLogicX(this.y+ this.h);
        boolean auxBoolean = (x >= this.graphics.realToLogicX(this.x) &&
                x <= this.graphics.realToLogicX(this.x + this.w) &&
                y >= this.graphics.realToLogicY(this.y)  &&
                y <= this.graphics.realToLogicY(this.y + this.h));

        return (x >= this.graphics.realToLogicX(this.x) &&
                x <= this.graphics.realToLogicX(this.x + this.w) &&
                y >= this.graphics.realToLogicY(this.y)  &&
                y <= this.graphics.realToLogicY(this.y + this.h));
    }

    public boolean isIncorrect(){
        return this.state == State.INCORRECT;
    }

    public void checkOut(){
        this.state = State.MARKED;
    }

}