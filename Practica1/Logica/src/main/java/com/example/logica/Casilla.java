package com.example.logica;

import com.example.engine.IColor;
import com.example.engine.IGraphics;

public class Casilla extends GameObject {
    enum Estado {DESELECCIONADA, SELECCIONADA, ELIMINADA, INCORRECTA};
    boolean solucion;
    int row, col;
    Estado state;
    IGraphics graphics;

    public Casilla(int row_, int col_, int w, int h, boolean sol, IGraphics graphics_){
        super(w, h);
        this.graphics = graphics_;
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
        this.y = yGraphic_; //System.out.println(this.y);
        if (state == Estado.ELIMINADA) {
            graphics.drawRectangle(graphics.realToLogicScale(this.x), graphics.realToLogicScale(this.y),
                    graphics.realToLogicScale(this.w), graphics.realToLogicScale(this.h), icolor);
            graphics.drawLine(graphics.realToLogicScale(this.x), graphics.realToLogicScale(this.y) ,
                    graphics.realToLogicScale(this.x)+graphics.realToLogicScale(w),
                    graphics.realToLogicScale(this.y) + this.graphics.realToLogicScale(this.h), icolor);
        }
        else graphics.fillRectangle(graphics.realToLogicScale(this.x), graphics.realToLogicScale(this.y),
                graphics.realToLogicScale(this.w), graphics.realToLogicScale(this.h), icolor);
    }

    public boolean checkCollisions(int x, int y)
    {
        if (x >= this.graphics.realToLogicScale(this.x) &&
                x <= this.graphics.realToLogicScale(this.x) + this.graphics.realToLogicScale(this.w) &&
                y >= this.graphics.realToLogicScale(this.y)  &&
                y <= this.graphics.realToLogicScale(this.y) + this.graphics.realToLogicScale(this.h))
            return true;
        return false;
    }

    public boolean esCorregida(){
        return state == Estado.INCORRECTA;
    }

    public void checkOut(){
        state = Estado.SELECCIONADA;
    }

}