package com.example.logica;

import com.example.engine.IButton;
import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IInput;
import com.example.engine.IScene;
import com.example.engine.ISound;

import java.util.ArrayList;
import java.util.Iterator;

public class TitleScene implements IScene {
    private IFont font;
    private IEngine engine;
    private boolean changeScene;
    private ISound backgroundMusic;
    private IButton startButton;

    public TitleScene(IEngine engine_){
        this.changeScene = false;
        this.engine = engine_;
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        createMusic();
        createButton();
    }

    @Override
    public void update() {
        if (changeScene) {
            changeScene = false;
            engine.getCurrentState().addScene(new BoardSelectionScene(this.engine));
        }

    }

    @Override
    public void render() {
        this.engine.getGraphics().setFont(this.font);
        this.engine.getGraphics().drawText("NONOGRAMS",this.engine.getGraphics().getLogicWidth()/2.0f - ((10*30)/2.0f),
                100, 27, this.engine.getGraphics().newColor(0,0,0,255));
    }

    @Override
    public synchronized void handleInputs() {
        ArrayList<IInput.Event> eventList = this.engine.getInput().getEventList();
        Iterator<IInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.Event event = it.next();
            switch (event.type) {
                case KEY_DOWN:
                    changeScene = true;
                    break;
                    //case CLICK EN BOTONES
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

    private void createButton(){
        this.startButton = this.engine.getGraphics().newButton("Start",
                this.engine.getGraphics().getLogicWidth() / 2,
                this.engine.getGraphics().getLogicHeight() * 5 / 6,
                this.engine.getGraphics().getLogicWidth() / 6,
                this.engine.getGraphics().getLogicHeight() / 12 );
    }
}
