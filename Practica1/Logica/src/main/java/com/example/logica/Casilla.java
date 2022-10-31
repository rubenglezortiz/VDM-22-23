package com.example.logica;

import com.example.engine.IColor;
import com.example.engine.IGraphics;

public class Casilla {

    enum Estado {DESELECCIONADA, SELECCIONADA, ELIMINADA, INCORRECTA};



    boolean solucion;
    int x, y, w, h, xGraphic, yGraphic;
    Estado state;

    public Casilla(int x_, int y_, boolean sol, IGraphics graphics){
        state = Estado.DESELECCIONADA;
        x = x_;
        y = y_;
        w = graphics.getWidth()/10;
        h = w;
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

    public void render(IGraphics graphics,int numRows, int numCols,int xGraphic_, int yGraphic_){
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
        this.xGraphic = xGraphic_;
        this.yGraphic = yGraphic_;
        if (state == Estado.ELIMINADA) {
            graphics.drawRectangle(xGraphic_, yGraphic_, w, h, icolor);
            graphics.drawLine(this.xGraphic, this.yGraphic, this.xGraphic+w, this.yGraphic +h, icolor);
        }
        else graphics.fillRectangle(xGraphic_, yGraphic_, w, h, icolor);
    }

    public boolean checkCollisions(int x, int y)
    {
        if (x >= this.xGraphic && x <= this.xGraphic+this.w &&
                y >= this.yGraphic && y <= this.yGraphic+this.h) return true;
        return false;
    }


}