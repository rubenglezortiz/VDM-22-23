package com.example.androidgame;

import com.example.aengine.AColor;
import com.example.aengine.AGraphics;

import java.io.Serializable;

public class Cell extends GameObject implements Serializable {
    enum State {UNMARKED, MARKED, REMOVED, INCORRECT};
    //Cuando se mantiene pulsada una casilla, se detectan los input LONG_TOUCH y RELEASED
    //este último cambia en la misma iteración el estado de la casilla de REMOVED a UNMARKED
    //la variable recentlyRemoved evita esto
    private boolean solution, recentlyRemoved;
    private State state;
    private AColor markedColor;

    public Cell(int w, int h, boolean sol){
        super(w, h);
        this.state = State.UNMARKED;
        this.solution = sol;
        this.markedColor = new AColor(255,255,255,255);
        this.recentlyRemoved = false;
    }

    public boolean isSolution() {
        return this.solution;
    }

    public void setSolution(boolean solution_) { this.solution = solution_; }

    public boolean changeState(){ // returns false if the pressed cell is incorrect
        if(this.state == State.UNMARKED) {
            if (this.solution) this.state = State.MARKED;
            else {
                this.state = State.INCORRECT;
                return false;
            }
        }
        else if (this.state == State.REMOVED) {
            if(!this.recentlyRemoved) this.state = State.UNMARKED;
            else this.recentlyRemoved = false;
        }
        return true;
    }

    public void changeStateToRemoved(){
        if(this.state == State.UNMARKED) {
            this.state = State.REMOVED;
            this.recentlyRemoved = true;
        }
    }

    public boolean check(){
        return this.solution && this.state == State.MARKED || !this.solution && this.state!=State.MARKED;
    }

    public void render(int xGraphic_, int yGraphic_, AGraphics graphics){
        AColor icolor;
        switch (state){
            case UNMARKED:
                icolor = graphics.newColor(200,200,200,255); // Grey
                break;
            case MARKED:
                icolor = this.markedColor;
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
        this.x = xGraphic_;
        this.y = yGraphic_;
        if (this.state == State.REMOVED) {
            graphics.drawRectangle(this.x, this.y, this.w, this.h, icolor);
            graphics.drawLine( this.x, this.y,this.x+w, this.y +this.h, icolor);
        }
        else graphics.fillRectangle(this.x, this.y, this.w, this.h, icolor);
    }

    public boolean checkCollisions(float x, float y) {
        return (x >= this.x && x <= this.x + this.w &&
                y >= (this.y) && y <= this.y + this.h);
    }

    public void setMarkedColor(AColor c){ this.markedColor = c;}
}