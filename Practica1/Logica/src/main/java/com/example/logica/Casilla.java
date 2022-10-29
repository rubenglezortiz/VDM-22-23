package com.example.logica;

import com.example.engine.IColor;
import com.example.engine.IGraphics;

import java.awt.Graphics;

public class Casilla {

    enum Estado {DESELECCIONADA, SELECCIONADA, ELIMINADA, INCORRECTA};



    boolean solucion;
    int x, y, w, h;
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
        return state == Estado.SELECCIONADA && !solucion;
    }

    public int comprueba(){
        if (solucion && (state == Estado.DESELECCIONADA  || state == Estado.ELIMINADA ))
            return 1; //Devuelve 1 para sumar esta casilla a la cantidad que faltan por pintar

        if (pintadaErronea())
            state = Estado.INCORRECTA;

        return 0;
    }

    public void render(IGraphics graphics,int numRows, int numCols,int xGraphic, int yGraphic){
        IColor icolor;
        switch (state){
            case DESELECCIONADA:
                icolor = graphics.newColor(0,0,0,255); // Blanco
                break;
            case SELECCIONADA:
                icolor = graphics.newColor(0,0,255,255); // Azul
                break;
            case ELIMINADA:
                icolor = graphics.newColor(255,255,255,255); // Negro
                break;
            case INCORRECTA:
                icolor = graphics.newColor(255,0,0,255); // Rojo
                break;
            default:
                icolor = graphics.newColor(255,255,255,255); // Negro
                break;
        }
        if(isSolucion())  icolor = graphics.newColor(255,0,0,255); // Debug
        graphics.fillRectangle(xGraphic, yGraphic, w, h, icolor);
    }


}