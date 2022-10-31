package com.example.logica;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IInput;
import com.example.engine.IScene;
import com.example.engine.ISound;
import com.example.engine.IState;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

public class Scene1 implements IScene {
    private IGraphics graphics;
    private IInput input;
    private IAudio audio;
    private Tablero board;

    private ISound backgroundMusic;

    public Scene1(IEngine engine){
        this.graphics = engine.getGraphics();
        this.input = engine.getInput();
        this.audio = engine.getAudio();
        board = new Tablero(5,5,this.graphics, this.audio);
        engine.getState().setScene(this);

        createMusic();
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        board.render(graphics);
    }

    @Override
    public void handleInputs() {
        ArrayList<IInput.Event> eventList = this.input.getEventList();
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
                    break;
                default:
                    break;
            }
        }
        this.input.clearEventList();
    }

    private void createMusic(){
        //Background music
        this.backgroundMusic = this.audio.newSound("music.wav");
        this.audio.setLooping(this.backgroundMusic, true);
        this.audio.setVolume(this.backgroundMusic, 0.25f);
        this.audio.playSound(this.backgroundMusic);
    }
}