package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IState;
import com.example.logica.Tablero;

import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.IOException;

import javax.swing.JFrame;

public class DesktopEngine implements IEngine, Runnable {
    private Thread renderThread;
    private GraphicsEngine graphics;
    private AudioEngine audio;
    private StateEngine state;
    private Tablero tablero;

    public DesktopEngine(JFrame window){
        graphics = new GraphicsEngine(window);
        audio = new AudioEngine();
        state = new StateEngine();

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
            System.out.println("Bucle");
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