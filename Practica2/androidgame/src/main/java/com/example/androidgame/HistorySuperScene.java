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
    private final String coinsImage, argentinaImage;
    protected String font;
    protected GameData data;
    protected AColor[][] palettes;
    protected AButton returnButton;
    protected boolean firstFrame;
    private final float northLatitude, southLatitude, eastLongitude, westLongitude;

    HistorySuperScene(AndroidEngine engine, GameData data_){
        this.font = "font.TTF";
        this.coinsImage ="moneda.png"; this.argentinaImage = "argentina.png";
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
        engine.getGraphics().newImage(this.argentinaImage);
        this.northLatitude = -9.6f; this.southLatitude = -53;
        this.westLongitude = -71; this.eastLongitude = -56;
        float x,y,w,h;
        x =  engine.getGraphics().getLogicWidth()/ 9.0f;
        y =  engine.getGraphics().getLogicHeight()/ 10.0f;
        w = h =  engine.getGraphics().getLogicHeight() / 12.0f;
        this.returnButton =  engine.getGraphics().newButton("Volver.png",
                x - (w / 2), y - (h / 2), w, h,
                engine.getGraphics().newColor(0,0,0,0));
        
        createReturnButton(engine.getGraphics());
        this.firstFrame = true;
    }

    @Override
    protected void setUpScene(AGraphics graphics, AAudio audio) {
        graphics.newImage(this.coinsImage);
        createReturnButton(graphics);
        this.firstFrame = true;
        graphics.newImage(this.argentinaImage);
    }

    @Override
    public void update(AndroidEngine engine) {
        float offx,offy,w,h;
        w = h = engine.getGraphics().logicToRealScale(60.0f);
        offx = offy = w / 4.0f;

        this.returnButton.changeButton(engine.getGraphics(), offx, offy, w, h);
        checkCoords(engine.getExternal());
    }

    @Override
    public void render(AGraphics graphics) {
        graphics.setBackgroundColor(this.palettes[this.data.actPalette][0]);
        if(this.data.actPalette == 3) graphics.drawImage(this.argentinaImage, 0.0f, 0.0f, graphics.getLogicWidth(), graphics.getLogicHeight()/4.0f, true);
        graphics.drawImage(this.coinsImage, 350,0,25,25, true);

        graphics.drawText(this.font, String.valueOf(this.data.coins), 300 ,20, 20, new AColor(0,0,0,255));
    }

    @Override
    public void handleInputs(AGraphics graphics, AInput input, AAudio audio, AExternal external) {
    }

    private void checkCoords(AExternal external){
        double actualLatitude = external.getLatitude();
        double actualLongitude = external.getLongitude();
        if(actualLatitude == 0.0f && actualLongitude == 0.0f) return;
        if(actualLatitude > this.southLatitude && actualLatitude < this.northLatitude &&
                actualLongitude > this.westLongitude && actualLongitude < this.eastLongitude)
            this.data.actPalette = 3;
        else this.data.actPalette = this.data.selectedPalette;
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
        setUpScene(engine_.getGraphics(), engine_.getAudio());
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        this.data.restoreDataFromFile(myView);
    }

    private void createReturnButton(AGraphics graphics){
        float offx,offy,w,h;
        w = h = graphics.logicToRealScale(60.0f);
        offx = offy = w / 4.0f;
        //offx = offy = 0;

        this.returnButton = graphics.newButtonWithAlignment("Volver.png",
                AButton.horizontalAlignment.LEFT,
                    AButton.verticalAlignment.TOP,
                offx, offy, w, h,
                graphics.newColor(0,0,0,0));
    }
}
