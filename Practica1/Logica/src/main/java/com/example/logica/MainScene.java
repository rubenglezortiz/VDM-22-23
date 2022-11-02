package com.example.logica;

import com.example.engine.IEngine;
import com.example.engine.IInput;
import com.example.engine.IScene;
import com.example.engine.ISound;

import java.util.ArrayList;
import java.util.Iterator;

public class MainScene implements IScene {
    private IEngine engine;
    private Board board;

    private ISound backgroundMusic;
    private float timer;
    private long lastUpdateTime;

    public MainScene(IEngine engine_, int numRows, int numCols){
        this.engine = engine_;
        board = new Board(numRows,numCols,this.engine.getGraphics(), this.engine.getAudio());
        this.timer = 0.0f;
        createMusic();
        engine.getState().addScene(this);
    }

    @Override
    public void update() {
        //Borrar el mensaje de correción al cabo de un tiempo
        if (board.getCheckPressed()){
            long actualTime = System.nanoTime() / 1000000; //Miliseconds
             timer = timer + ((actualTime - lastUpdateTime) / 1000.0f); //Timer is in seconds
            if (timer > 5.0f){
                board.checkOut();
                timer = 0.0f;
            }
            lastUpdateTime = actualTime;
        }
        else lastUpdateTime = System.nanoTime() / 1000000; //Miliseconds

    }

    @Override
    public void render() {
        this.board.render(this.engine.getGraphics());
    }

    @Override
    public synchronized void handleInputs() {
        ArrayList<IInput.Event> eventList = this.engine.getInput().getEventList();
        Iterator<IInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.Event event = it.next();
            switch (event.type) {
                case TOUCH_PRESSED:
                case TOUCH_RELEASED:
                case KEY_DOWN:
                    board.handleInputs(event);
                    //this.engine.getAudio().stopSound(this.backgroundMusic);
                    //this.engine.getState().removeScene();
                    break;
                default:
                    break;
            }
        }
        this.engine.getInput().clearEventList();
    }

    private void createMusic(){
        //Background music
        this.backgroundMusic = this.engine.getAudio().newSound("music.wav");
        this.engine.getAudio().setLooping(this.backgroundMusic, true);
        this.engine.getAudio().setVolume(this.backgroundMusic, 0.25f);
        this.engine.getAudio().playSound(this.backgroundMusic);
    }
}