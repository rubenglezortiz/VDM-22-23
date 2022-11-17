package com.example.androidgame;

import com.example.aengine.AButton;
import com.example.aengine.AFont;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
import com.example.aengine.ASound;
import com.example.aengine.AndroidEngine;

import java.util.ArrayList;
import java.util.Iterator;

public class TitleScene extends AScene {
    private AndroidEngine engine;
    private AButton startButton;
    private ASound backgroundMusic;
    private AFont font;
    private boolean changeScene;

    public TitleScene(AndroidEngine engine_){
        this.changeScene = false;
        this.engine = engine_;
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        createMusic();
        createButton();
    }

    public void update() {
        if (this.changeScene) {
            this.changeScene = false;
            this.engine.getCurrentState().addScene(new BoardSelectionScene(this.engine));
        }
    }

    public void render() {
        this.engine.getGraphics().drawText(this.font, "NONOGRAMS", this.engine.getGraphics().getLogicWidth()/2.0f - ((10*30)/2.0f),
                100, 27, this.engine.getGraphics().newColor(0,0,0,255));

        this.engine.getGraphics().drawButton(this.startButton);
    }

    public synchronized void handleInputs() {
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) this.engine.getInput().getEventList().clone();
        Iterator<AInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            AInput.Event event = it.next();
            switch (event.type) {
                case TOUCH_RELEASED:
                    if(((AInput.MouseInputEvent)event).mouseButton == 1){
                        if (this.startButton.checkCollision(this.engine.getGraphics().logicToRealX(((AInput.MouseInputEvent)event).x),
                                this.engine.getGraphics().logicToRealY(((AInput.MouseInputEvent)event).y))){
                            this.changeScene = true;
                        }
                    }
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
        this.engine.getAudio().setBackgroundMusic(this.backgroundMusic);
    }

    private void createButton(){
        int x,y,w,h;
        x = this.engine.getGraphics().getLogicWidth() / 2;
        y = this.engine.getGraphics().getLogicHeight() * 2 / 3;
        w = this.engine.getGraphics().getLogicWidth() / 3;
        h = this.engine.getGraphics().getLogicHeight() / 10;

        this.startButton = this.engine.getGraphics().newButton("Start",
                x - (w / 2), y - (h / 2), w, h,
                10,35, 18,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));
    }
}


