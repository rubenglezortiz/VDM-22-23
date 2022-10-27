package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IState;
import com.example.logica.Tablero;

import javax.swing.JFrame;
import javax.swing.plaf.synth.Region;

public class DesktopEngine implements IEngine, Runnable {
    private Thread currentThread;
    private DGraphicsEngine graphics;
    private DAudioEngine audio;
    private DState currentState;

    private JFrame window;

    private Tablero tablero; //ESTO HAY QUE ELIMINARLO

    public DesktopEngine(JFrame window_){
        this.window = window_;
        this.graphics = new DGraphicsEngine(window);
        this.audio = new DAudioEngine();
        this.currentState = new DState();

        this.currentThread = new Thread(this);
        this.currentThread.start();
    }


    @Override
    public IGraphics getGraphics() { return this.graphics;}

    @Override
    public IAudio getAudio() { return this.audio;}

    @Override
    public IState getState() { return this.currentState;}

    @Override
    public void run() {
        if(this.currentThread!=Thread.currentThread()){
            //lanzar error
        }

        while(this.currentState != null) {
            //update
            this.currentState.update();
            //inputs
            this.currentState.handleInputs();
            //render
            //preparar Frame
            do {
                this.graphics.prepareFrame();
                this.currentState.render();
                //terminar Frame
                this.graphics.finishFrame();
            }while(!this.graphics.cambioBuffer());

            /*if(tablero != null)
                tablero.render(getGraphics());*/
            //this.graphics.show();
            //this.graphics.dispose(); //Elimina el contexto gráfico y libera recursos del sistema realacionado
        }
    }

    public void setTablero(Tablero tablero_){
        this.tablero = tablero_;
    }

    public void resume(){
        this.currentThread = new Thread( this);
        this.currentThread.start();
    }

    public void stop() throws InterruptedException {
        this.currentThread.join();
    }
}