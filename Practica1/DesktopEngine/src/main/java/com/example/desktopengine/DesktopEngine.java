package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IInput;
import com.example.engine.IStateManager;

import javax.swing.JFrame;

public class DesktopEngine implements IEngine, Runnable {
    private DGraphics graphics;
    private DAudio audio;
    private DInput input;
    private IStateManager currentState;

    private JFrame window;
    private Thread currentThread;

    public DesktopEngine(JFrame window_){
        this.window = window_;
        this.graphics = new DGraphics(window);
        this.audio = new DAudio();
        this.input = new DInput(this.graphics);
        this.window.addKeyListener(this.input);
        this.window.addMouseListener(this.input);
        this.window.addMouseMotionListener(this.input);
        this.currentState = new IStateManager(this);

        this.currentThread = new Thread(this);
        this.currentThread.start();
    }
    public DesktopEngine(JFrame window_,int logicWidth, int logicHeight){
        this.window = window_;
        this.graphics = new DGraphics(window,logicWidth,logicHeight);
        this.audio = new DAudio();
        this.input = new DInput(this.graphics);
        this.window.addKeyListener(this.input);
        this.window.addMouseListener(this.input);
        this.window.addMouseMotionListener(this.input);
        this.currentState = new IStateManager( this);

        this.currentThread = new Thread(this);
        this.currentThread.start();
    }

    @Override
    public IGraphics getGraphics() { return this.graphics;}

    @Override
    public IInput getInput() {return this.input;}

    @Override
    public IStateManager getCurrentState() { return this.currentState;}

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
        if(this.currentThread!=Thread.currentThread()){
            throw new RuntimeException("this.currentThread not equal to Thrad.currentThread()");
        }
        while(this.currentState != null) {
            //Update
            this.currentState.update();
            //Inputs
            this.currentState.handleInputs();
            do {
                //Pre frame
                this.graphics.prepareFrame();
                //Render
                this.currentState.render();
                //Post frame
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