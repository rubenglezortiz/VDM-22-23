package com.example.androidgame;

import android.os.Bundle;
import android.view.View;

import com.example.aengine.AAudio;
import com.example.aengine.AButton;
import com.example.aengine.AExternal;
import com.example.aengine.AGraphics;
import com.example.aengine.AInput;
import com.example.aengine.AndroidEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class TitleScene extends HistorySuperScene implements Serializable {
    private AButton storyButton, quickGameButton, paletteButton;
    private String backgroundMusic;
    private int changeScene;

    public TitleScene(AndroidEngine engine_, GameData data_){
        super(engine_.getGraphics(), data_);
        this.changeScene = 0;
        this.backgroundMusic = "music.wav";

        setUpScene(engine_.getGraphics(), engine_.getAudio());
        engine_.getExternal().loadBanner();
    }

    private void createMusic(AAudio audio) {
        //Background music
        audio.newSound(this.backgroundMusic);
        audio.playSound(this.backgroundMusic);
        audio.setVolume(this.backgroundMusic, 0.25f);
        audio.setLooping(this.backgroundMusic, true);
        audio.setBackgroundMusic(this.backgroundMusic);
    }

    private void createButtons(AGraphics graphics){
        int x,y1, y2, y3, w,h;
        x = graphics.getLogicWidth() / 2;
        y1 = graphics.getLogicHeight() * 2 / 6;
        w = graphics.getLogicWidth() / 3;
        h = graphics.getLogicHeight() / 10;

        this.storyButton = graphics.newButton("ModoHistoria.png",
                x - (w / 2), y1 - (h / 2), w, h,
                graphics.newColor(0,0,0,0));

        y2 = graphics.getLogicHeight() * 3 / 6;
        this.quickGameButton = graphics.newButton("PartidaRapida.png",
                x - (w / 2), y2 - (h / 2), w, h,
                graphics.newColor(0,0,0,0));

        y3 = graphics.getLogicHeight() * 4 / 6;
        this.paletteButton = graphics.newButton("Colores.png",
                x - (w / 2), y3 - (h / 2), w, h,
                graphics.newColor(0,0,0,0));
    }
    @Override
    protected void setUpScene(AGraphics graphics, AAudio audio) {
        super.setUpScene(graphics,audio);
        graphics.newFont(this.font, false);
        createButtons(graphics);
        createMusic(audio);
    }

    @Override
    public void update(AndroidEngine engine) {
        if (this.changeScene != 0) {
            if (this.changeScene == 1)
                engine.getCurrentState().addScene(new SelectCategoryScene(engine, this.data));
            else if (this.changeScene == 2)
                engine.getCurrentState().addScene(new QuickBoardSelectionScene(engine, this.data));
            else if(this.changeScene == 3)
                engine.getCurrentState().addScene(new PaletteScene(engine, this.data));
            this.changeScene = 0;
        }
    }

    @Override
    public void render(AGraphics graphics) {
        super.render(graphics);
        graphics.drawText(this.font, "NONOGRAMS", graphics.getLogicWidth()/2.0f - ((10*30)/2.0f),
                75, 27, graphics.newColor(0,0,0,255));
        graphics.drawButton(this.storyButton);
        graphics.drawButton(this.quickGameButton);
        graphics.drawButton(this.paletteButton);

    }

    @Override
    public synchronized void handleInputs(AInput input, AAudio audio, AExternal external) {
        super.handleInputs(input,audio,external);
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) input.getEventList().clone();
        Iterator<AInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            AInput.Event event = it.next();
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = ((AInput.TouchInputEvent) event).x;
                    float collisionY = ((AInput.TouchInputEvent) event).y;
                    if (this.storyButton.checkCollision(collisionX, collisionY)) this.changeScene = 1;
                    if (this.quickGameButton.checkCollision(collisionX, collisionY)) this.changeScene = 2;
                    if (this.paletteButton.checkCollision(collisionX, collisionY)) this.changeScene = 3;
                    break;
                default:
                    break;
            }
        }
        input.clearEventList();
    }

    @Override
    public void saveScene(Bundle outState) {
        super.saveScene(outState);
        outState.putInt("changeScene", this.changeScene);
    }

    @Override
    public void saveSceneInFile(View myView) {
        super.saveSceneInFile(myView);
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
        super.restoreScene(savedInstanceState, engine);
        this.changeScene = savedInstanceState.getInt("changeScene");
        setUpScene(engine.getGraphics(), engine.getAudio());
        engine.getExternal().loadBanner();
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        super.restoreSceneFromFile(myView);
    }
}