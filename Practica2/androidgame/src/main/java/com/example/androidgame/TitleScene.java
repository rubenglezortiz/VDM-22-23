package com.example.androidgame;

import android.os.Bundle;
import android.view.View;

import com.example.aengine.AButton;
import com.example.aengine.AFont;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
import com.example.aengine.ASound;
import com.example.aengine.AndroidEngine;

import java.util.ArrayList;
import java.util.Iterator;

public class TitleScene extends HistorySuperScene {
    private AndroidEngine engine;
    private AButton storyButton, quickGameButton, paletteButton;
    private ASound backgroundMusic;
    private AFont font;
    private int changeScene;

    public TitleScene(AndroidEngine engine_){
        super(engine_);
        this.changeScene = 0;
        this.engine = engine_;
        setUpScene();
    }

    private void createMusic(){
        //Background music
        if(this.backgroundMusic == null) {
            this.backgroundMusic = this.engine.getAudio().newSound("music.wav");
            this.engine.getAudio().setLooping(this.backgroundMusic, true);
            this.engine.getAudio().setVolume(this.backgroundMusic, 0.25f);
            this.engine.getAudio().playSound(this.backgroundMusic);
            this.engine.getAudio().setBackgroundMusic(this.backgroundMusic);
        }
    }

    private void createButtons(){
        int x,y1, y2, y3, w,h;
        x = this.engine.getGraphics().getLogicWidth() / 2;
        y1 = this.engine.getGraphics().getLogicHeight() * 2 / 6;
        w = this.engine.getGraphics().getLogicWidth() / 3;
        h = this.engine.getGraphics().getLogicHeight() / 10;

        this.storyButton = this.engine.getGraphics().newButton("Modo Historia",
                x - (w / 2), y1 - (h / 2), w, h,
                8,35, 8,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

        y2 = this.engine.getGraphics().getLogicHeight() * 3 / 6;
        this.quickGameButton = this.engine.getGraphics().newButton("Partida Rápida",
                x - (w / 2), y2 - (h / 2), w, h,
                5,35, 8,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

        y3 = this.engine.getGraphics().getLogicHeight() * 4 / 6;
        this.paletteButton = this.engine.getGraphics().newButton("Colores",
                x - (w / 2), y3 - (h / 2), w, h,
                5,35, 8,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));
    }

    @Override
    protected void setUpScene() {
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        createButtons();
        createMusic();
    }

    @Override
    public void update() {
        if (this.changeScene != 0) {
            if (this.changeScene == 1)
                this.engine.getCurrentState().addScene(new SelectCategoryScene(this.engine));
            else if (this.changeScene == 2)
                this.engine.getCurrentState().addScene(new QuickBoardSelectionScene(this.engine));
            else if(this.changeScene == 3)
                this.engine.getCurrentState().addScene(new PaletteScene(this.engine));
            this.changeScene = 0;
        }
    }

    @Override
    public void render() {
        this.engine.getGraphics().drawText(this.font, "NONOGRAMS", this.engine.getGraphics().getLogicWidth()/2.0f - ((10*30)/2.0f),
                100, 27, this.engine.getGraphics().newColor(0,0,0,255));

        this.engine.getGraphics().drawButton(this.storyButton);
        this.engine.getGraphics().drawButton(this.quickGameButton);
        this.engine.getGraphics().drawButton(this.paletteButton);
    }

    @Override
    public synchronized void handleInputs() {
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) this.engine.getInput().getEventList().clone();
        Iterator<AInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            AInput.Event event = it.next();
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = this.engine.getGraphics().realToLogicX(((AInput.TouchInputEvent) event).x);
                    float collisionY = this.engine.getGraphics().realToLogicY(((AInput.TouchInputEvent) event).y);
                    if (this.storyButton.checkCollision(collisionX, collisionY)) this.changeScene = 1;
                    if (this.quickGameButton.checkCollision(collisionX, collisionY)) this.changeScene = 2;
                    if (this.paletteButton.checkCollision(collisionX, collisionY)) this.changeScene = 3;
                    break;
                default:
                    break;
            }
        }
        this.engine.getInput().clearEventList();
    }

    @Override
    public void saveScene(Bundle outState) {
        outState.putInt("changeScene", this.changeScene);
    }

    @Override
    public void saveSceneInFile(View myView) {

    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
        this.changeScene = savedInstanceState.getInt("changeScene");
        this.engine = engine;
        setUpScene();
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        super.restoreSceneFromFile(myView);
    }
}


