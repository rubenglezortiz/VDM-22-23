package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IState;
import com.example.logica.Tablero;

import javax.swing.JFrame;

public class DesktopEngine implements IEngine, Runnable {
    private Thread renderThread;
    private DGraphicsEngine graphics;
    private DAudioEngine audio;
    private DStateEngine state;
    private Tablero tablero;

    public DesktopEngine(JFrame window){
        graphics = new DGraphicsEngine(window);
        audio = new DAudioEngine();
        state = new DStateEngine();

        this.renderThread = new Thread(this);
        this.renderThread.start();
    }

    @Override
    public IGraphics getGraphics() { return graphics;}

    @Override
    public IAudio getAudio() { return audio;}

    @Override
    public IState getState() { return state;}

    @Override
    public void run() {
        while(true) {
            if(tablero != null)
                tablero.render(getGraphics());
            //DColor color = new DColor(0,0,0,255);
            //this.graphics.clear(color);
            this.graphics.dispose(); //Elimina el contexto gráfico y libera recursos del sistema realacionado


            this.graphics.show();
        }

    }

    public void setTablero(Tablero tablero_){
        this.tablero = tablero_;
    }

}