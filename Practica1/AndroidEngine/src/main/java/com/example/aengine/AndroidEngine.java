package com.example.aengine;


import android.annotation.SuppressLint;
import android.icu.util.Calendar;
import android.text.format.Time;
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

    private boolean running;

    @SuppressLint("ClickableViewAccessibility")
    public AndroidEngine(SurfaceView myView_){
        this.myView = myView_;
        this.graphics = new AGraphics(this.myView); //DESDE DÓNDE SE PASA EL SURFACE VIEW???
        this.audio = new AAudio(this.myView.getContext().getAssets());
        this.input = new AInput(this.graphics);
        this.myView.setOnTouchListener(this.input);
        this.currentState = new AState(this.myView, this);
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
    public int getTime() {
        long time = System.nanoTime() / 1000000;
        time /= 1000.0f;
        return (int)time;
    }

    @Override
    public void run() {
        System.out.println("inicio hilo ");
        if ( this.currentThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }
        while(this.running && this.myView.getWidth() == 0){
            System.out.println(this.running);
        };

        while(this.running) {
            //Update
            this.currentState.update();
            //Inputs
            this.currentState.handleInputs();
            while (!this.graphics.changeBuffer());
            //Pre frame
            this.graphics.prepareFrame();
            //Render
            this.currentState.render();
            //Post frame
            this.graphics.finishFrame();
        }
    }

    public void resume(){
        if (!this.running) {
            this.running = true;
            this.audio.resumeAllSounds();
            this.currentThread = new Thread(this);
            this.currentThread.start();
        }
    }

    public void pause() {
        if (this.running) {
            this.running = false;
            while (true) {
                try {
                    this.audio.pauseAllSounds();
                    this.currentThread.join();
                    this.currentThread = null;
                    break;
                } catch (InterruptedException ie) {}
            }
        }
    }
}
