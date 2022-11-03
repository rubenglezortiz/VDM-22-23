package com.example.aengine;


import android.view.SurfaceView;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IInput;
import com.example.engine.IState;


public class AndroidEngine implements IEngine, Runnable {
    private AGraphics graphics;
    private AAudio audio;
    private AInput input;
    private AState currentState;

    private SurfaceView myView;
    private Thread currentThread;

    public AndroidEngine(SurfaceView myView_){
        this.myView = myView_;
        this.graphics = new AGraphics(this.myView); //DESDE DÓNDE SE PASA EL SURFACE VIEW???
        this.audio = new AAudio();
        this.currentState = new AState();

        this.currentThread = new Thread(this);
        this.currentThread.start();
    }

    @Override
    public IGraphics getGraphics() { return this.graphics;}

    @Override
    public IInput getInput() {return this.input;}

    @Override
    public IState getCurrentState() { return this.currentState;}

    @Override
    public IAudio getAudio() { return this.audio;}

    @Override
    public void run() {
        if ( this.currentThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }
        while(this.currentThread != null){
            //update
            this.currentState.update();
            //inputs
            this.currentState.handleInputs();
            do {
                //preparar Frame
                this.graphics.prepareFrame();
                //render
                this.currentState.render();
                //terminar Frame
                this.graphics.finishFrame();
            }while(!this.graphics.changeBuffer());
        }
    }
}
