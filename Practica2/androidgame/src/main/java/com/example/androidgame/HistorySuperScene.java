package com.example.androidgame;

import android.os.Bundle;
import android.view.View;
import com.example.aengine.AAudio;
import com.example.aengine.AColor;
import com.example.aengine.AGraphics;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
import com.example.aengine.AndroidEngine;
import java.util.ArrayList;

public class HistorySuperScene extends AScene {
    protected String filename, font, coinsImage;
    protected GameData data;
    protected AColor[][] palettes;

    HistorySuperScene(AGraphics graphics, GameData data_){
        this.filename = "super";
        this.font = "font.TTF";
        this.coinsImage ="moneda.png";
        this.data = data_;
        this.palettes = new AColor[3][2];
        this.palettes[0][0] = new AColor(255,255,255);
        this.palettes[0][1] = new AColor(0,0,255);
        this.palettes[1][0] = new AColor(255,0,128);
        this.palettes[1][1] = new AColor(128,0,128);
        this.palettes[2][0] = new AColor(255,128,0);
        this.palettes[2][1] = new AColor(255,255,0);
        graphics.newImage(this.coinsImage);
    }

    @Override
    protected void setUpScene(AGraphics graphics, AAudio audio) {
        graphics.newImage(this.coinsImage);
    }

    @Override
    public void update(AndroidEngine engine) {}

    @Override
    public void render(AGraphics graphics) {
        graphics.setBackgroundColor(this.palettes[this.data.actPalette][0]);
        graphics.drawImage(this.coinsImage, 350,0,25,25);
        graphics.drawText(this.font, String.valueOf(this.data.coins), 320 ,17, 20, new AColor(0,0,0,255));
    }

    @Override
    public void handleInputs(AInput input, AAudio audio) {
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) input.getEventList().clone();
        for (AInput.Event event : eventList) {
            if (event.type == AInput.InputType.LONG_TOUCH)
                this.data.coins += 10;
        }
    }

    @Override
    public void saveScene(Bundle outState) {
        outState.putSerializable("data", this.data);
        outState.putString("coins_image", this.coinsImage);
    }

    @Override
    public void saveSceneInFile(View myView) {
        this.data.saveDataInFile(myView);
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
        this.data = (GameData) savedInstanceState.getSerializable("data");
        this.coinsImage = savedInstanceState.getString("coins_image");
        setUpScene(engine.getGraphics(), engine.getAudio());
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        this.data.restoreDataFromFile(myView);
    }
}
