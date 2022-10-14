package com.example.logica;

public class Casilla {

    enum Estado {DESELECCIONADA, SELECCIONADA, ELIMINADA, INCORRECTA};



    boolean solucion;
    int x, y;
    Estado state;

    public Casilla(int x_, int y_, boolean sol){
        state = Estado.DESELECCIONADA;
        x = x_;
        y = y_;
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

    public void render(/*IGraphicsEngine graphics*/){
        /* String color;
        switch (state){
            case DESELECCIONADA:
                color = "blanco";
                break;
            case SELECCIONADA:
                color = "azul";
            case ELIMINADA:
                color = "pues el q sea";
                break;
            case INCORRECTA:
                color = "rojo";
                break;
            default: break;
        }
        graphics.drawRectangle(pos.x*width, pos.y*height, w,h, "color");
        */
    }


}