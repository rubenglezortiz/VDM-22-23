package com.example.logica;

import com.example.engine.IColor;
import com.example.engine.IGraphics;

public class Casilla extends GameObject {
    enum Estado {DESELECCIONADA, SELECCIONADA, ELIMINADA, INCORRECTA};

    boolean solucion;
    int row, col;
    Estado state;

    public Casilla(int row_, int col_, boolean sol, IGraphics graphics){
        super(graphics.getWidth()/10, graphics.getWidth()/10);
        state = Estado.DESELECCIONADA;
        row = row_;
        col = col_;
        solucion = sol;
    }

    public boolean isSolucion() {
        return solucion;
    }

    public void setSolucion(boolean solucion_) {
        solucion = solucion_;
    }

    public void cambiaEstado(){
        switch (state){
            case DESELECCIONADA: state = Estado.SELECCIONADA; break;
            case SELECCIONADA: state = Estado.ELIMINADA; break;
            case ELIMINADA: state = Estado.DESELECCIONADA; break;
            case INCORRECTA: state = Estado.DESELECCIONADA; break;
        }
    }

    public boolean pintadaErronea(){
        return (state == Estado.SELECCIONADA && !solucion) || (state == Estado.INCORRECTA);
    }

    public int comprueba(){
        if (solucion && (state == Estado.DESELECCIONADA  || state == Estado.ELIMINADA ))
            return 1; //Devuelve 1 para sumar esta casilla a la cantidad que faltan por pintar

        if (pintadaErronea()) {
            state = Estado.INCORRECTA;
            return 2;
        }

        return 0;
    }

    public void render(IGraphics graphics, int xGraphic_, int yGraphic_){
        IColor icolor;
        switch (state){
            case DESELECCIONADA:
                icolor = graphics.newColor(200,200,200,255); // Gris
                break;
            case SELECCIONADA:
                icolor = graphics.newColor(0,0,255,255); // Azul
                break;
            case ELIMINADA:
                icolor = graphics.newColor(0,0,0,255); // Blanco
                break;
            case INCORRECTA:
                icolor = graphics.newColor(255,0,0,255); // Rojo
                break;
            default:
                icolor = graphics.newColor(255,255,255,255); // Blanco
                break;
        }
        //if(isSolucion())  icolor = graphics.newColor(255,0,0,255); // Debug
        this.x = xGraphic_;
        this.y = yGraphic_;
        if (state == Estado.ELIMINADA) {
            graphics.drawRectangle(xGraphic_, yGraphic_, w, h, icolor);
            graphics.drawLine(this.x, this.y, this.x+w, this.y +h, icolor);
        }
        else graphics.fillRectangle(xGraphic_, yGraphic_, w, h, icolor);
    }

    public boolean checkCollisions(int x, int y)
    {
        if (x >= this.x && x <= this.x + this.w &&
                y >= this.y  && y <= this.y + this.h) return true;
        return false;
    }

    public boolean esCorregida(){
        return state == Estado.INCORRECTA;
    }

    public void checkOut(){
        state = Estado.SELECCIONADA;
    }

}