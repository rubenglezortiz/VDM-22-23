package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IInput;
import com.example.engine.IState;

import javax.swing.JFrame;

public class DesktopEngine implements IEngine, Runnable {
    private Thread currentThread;
    private DGraphicsEngine graphics;
    private DAudioEngine audio;
    private DInput input;
    private DState currentState;

    private JFrame window;

    public DesktopEngine(JFrame window_){
        this.window = window_;
        this.graphics = new DGraphicsEngine(window);
        this.audio = new DAudioEngine();
        this.input = new DInput();
        this.window.addKeyListener(this.input);
        this.window.addMouseListener(this.input);
        this.window.addMouseMotionListener(this.input);
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
    public IInput getInput() {return this.input;}

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

    public void resume(){
        this.currentThread = new Thread( this);
        this.currentThread.start();
    }

    public void stop() throws InterruptedException {
        this.currentThread.join();
    }
}