package com.example.androidgame;

import android.os.Bundle;
import android.view.View;
import com.example.aengine.AAudio;
import com.example.aengine.AButton;
import com.example.aengine.AColor;
import com.example.aengine.AExternal;
import com.example.aengine.AGraphics;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
import com.example.aengine.AndroidEngine;

public class HistorySuperScene extends AScene {
    private String argentinaImage, coinsImage, backgroundMusic, argentinaBackgroundMusic;
    protected String font;
    protected GameData data;
    protected AColor[][] palettes;
    protected AButton returnButton;
    private float northLatitude, southLatitude, eastLongitude, westLongitude;

    HistorySuperScene(AndroidEngine engine, GameData data_){
        this.font = "font.TTF";
        this.coinsImage ="moneda.png"; this.argentinaImage = "argentina.png";
        this.backgroundMusic = "music.wav"; this.argentinaBackgroundMusic = "duki.mp3";
        this.data = data_;
        this.palettes = new AColor[4][2];
        this.palettes[0][0] = new AColor(255,255,255);
        this.palettes[0][1] = new AColor(0,0,255);
        this.palettes[1][0] = new AColor(255,0,128);
        this.palettes[1][1] = new AColor(128,0,128);
        this.palettes[2][0] = new AColor(255,128,0);
        this.palettes[2][1] = new AColor(255,255,0);
        this.palettes[3][0] = new AColor(117, 170,219);
        this.palettes[3][1] = new AColor(252,191,73);
        engine.getGraphics().newImage(this.coinsImage);
        this.northLatitude = -9.6f; this.southLatitude = -53;
        this.westLongitude = -71; this.eastLongitude = -56;
        float x,y,w,h;
        x =  engine.getGraphics().getLogicWidth()/ 9.0f;
        y =  engine.getGraphics().getLogicHeight()/ 10.0f;
        w = h =  engine.getGraphics().getLogicHeight() / 12.0f;
        this.returnButton =  engine.getGraphics().newButton("Volver.png",
                x - (w / 2), y - (h / 2), w, h,
                engine.getGraphics().newColor(0,0,0,0));
        checkCoords(engine.getExternal(), engine.getAudio());
        createMusic(engine.getAudio());
    }

    private void createMusic(AAudio audio) {
        audio.newSound(this.backgroundMusic);
        audio.newSound(this.argentinaBackgroundMusic);
        audio.setVolume(this.backgroundMusic, 0.25f);
        audio.setVolume(this.argentinaBackgroundMusic, 0.25f);
        audio.setLooping(this.backgroundMusic, true);
        audio.setLooping(this.argentinaBackgroundMusic, true);
    }

    @Override
    protected void setUpScene(AGraphics graphics, AAudio audio) {
        graphics.newImage(this.coinsImage);
        graphics.newImage(this.argentinaImage);

    }

    private void checkCoords(AExternal external, AAudio audio){
        //external.checkCoords();
        double actualLatitude = external.getLatitude();
        double actualLongitude = external.getLongitude();
        if(actualLatitude > this.southLatitude && actualLatitude < this.northLatitude &&
            actualLongitude > this.westLongitude && actualLongitude < this.eastLongitude) {
            this.data.actPalette = 3;
            audio.pauseSound(this.backgroundMusic);
            audio.playSound(this.argentinaBackgroundMusic);
            audio.setBackgroundMusic(this.argentinaBackgroundMusic);
        }
        else {
            this.data.actPalette = this.data.selectedPalette;
            audio.pauseSound(this.argentinaBackgroundMusic);
            audio.playSound(this.backgroundMusic);
            audio.setBackgroundMusic(this.backgroundMusic);
        }
    }

    @Override
    public void update(AndroidEngine engine) {
        checkCoords(engine.getExternal(), engine.getAudio());
    }

    @Override
    public void render(AGraphics graphics) {
        graphics.setBackgroundColor(this.palettes[this.data.actPalette][0]);
        if(this.data.actPalette == 3) graphics.drawImage(this.argentinaImage, 0.0f, 0.0f, graphics.getLogicWidth(), graphics.getLogicHeight()/4.0f);
        graphics.drawImage(this.coinsImage, 350,0,25,25);
        graphics.drawText(this.font, String.valueOf(this.data.coins), 300 ,20, 20, new AColor(0,0,0,255));
    }

    @Override
    public void handleInputs(AInput input, AAudio audio, AExternal external) {
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
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine_) {
        this.data = (GameData) savedInstanceState.getSerializable("data");
        this.coinsImage = savedInstanceState.getString("coins_image");
        setUpScene(engine_.getGraphics(), engine_.getAudio());
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        this.data.restoreDataFromFile(myView);
    }
}
