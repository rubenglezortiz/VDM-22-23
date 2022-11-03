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
    private boolean backToMenu;
    private ISound backgroundMusic;
    private float timer;

    public MainScene(IEngine engine_, int numRows, int numCols){
        this.backToMenu = false;
        this.engine = engine_;
        board = new Board(numRows,numCols,this.engine.getGraphics(), this.engine.getAudio());
        this.timer = 0.0f;
        createMusic();
    }

    @Override
    public void update() {
        long actualTime = System.nanoTime() / 1000000; //Miliseconds
        if(board.getCheckPressed()){
            timer = (actualTime/1000.0f) + 5.0f;
            board.pressedOut();
        }

        if ((actualTime/1000.0f) > timer) board.checkOut();

        if(this.backToMenu) this.engine.getCurrentState().removeScene(2);
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
                    board.handleInputs(event);
                    break;
                case KEY_DOWN:
                    board.handleInputs(event);
                    if(((IInput.KeyInputEvent)event).key=='Q')
                        this.backToMenu = true;
                        //this.engine.getAudio().stopSound(this.backgroundMusic);
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