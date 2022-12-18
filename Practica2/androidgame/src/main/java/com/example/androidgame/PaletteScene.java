package com.example.androidgame;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.aengine.AAudio;
import com.example.aengine.AButton;
import com.example.aengine.AColor;
import com.example.aengine.AExternal;
import com.example.aengine.AGraphics;
import com.example.aengine.AInput;
import com.example.aengine.AState;
import com.example.aengine.AndroidEngine;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PaletteScene extends HistorySuperScene implements Serializable {
    private boolean back;
    private int costp2, costp3;
    private AButton p1, p2, p3;

    public PaletteScene(AndroidEngine engine_, GameData data){
        super(engine_.getGraphics(), data);
        this.back = false;
        this.costp2 = 50;
        this.costp3 = 100;
        this.createButtons(engine_.getGraphics());
    }

    private void createButtons(AGraphics graphics){
        int x, x3x, x5x, x10x, y, w, h, tx, ty, tSize;
        x3x = graphics.getLogicWidth() / 4;
        x5x = graphics.getLogicWidth() * 2 / 4;
        x10x = graphics.getLogicWidth() * 3 / 4;
        y = graphics.getLogicHeight() / 2;
        w = graphics.getLogicWidth() / 5;
        h = graphics.getLogicHeight() / 10;
        ty = 35;
        tSize = 15;

        this.p1 = graphics.newButton("Paleta1.png",
                x3x - (w / 2), y - (h / 2), w, h,
                18,ty, tSize,
                graphics.newColor(0,0,0,0));

        this.p2 = graphics.newButton("Paleta2.png",
                x5x - (w / 2), y - (h / 2), w, h,
                18,ty, tSize,
                graphics.newColor(0,0,0,0));

        this.p3 = graphics.newButton("Paleta3.png",
                x10x - (w / 2), y - (h / 2), w, h,
                7,ty, tSize,
                graphics.newColor(0,0,0,0));
    }

    protected void setUpScene() {}

    @Override
    public void update(AndroidEngine engine){
        if (this.back) engine.getCurrentState().removeScene(1);
    }

    @Override
    public void render(AGraphics graphics){
        super.render(graphics);
        graphics.drawButton(this.returnButton);
        graphics.drawButton(this.p1);
        graphics.drawButton(this.p2);
        graphics.drawButton(this.p3);
    }

    @Override
    public void handleInputs(AInput input, AAudio audio, AExternal external){
        super.handleInputs(input, audio, external);
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) input.getEventList().clone();
        for (AInput.Event event : eventList)
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = ((AInput.TouchInputEvent) event).x;
                    float collisionY = ((AInput.TouchInputEvent) event).y;
                    if (this.p1.checkCollision(collisionX, collisionY)) this.data.actPalette = 0;
                    else if (this.p2.checkCollision(collisionX, collisionY)){
                        if(this.data.lockp2 && this.costp2 <= this.data.coins) {
                            this.data.coins -= this.costp2;
                            this.data.lockp2 = false;
                        }
                        if(!this.data.lockp2) this.data.actPalette = 1;
                    }
                    else if (this.p3.checkCollision(collisionX, collisionY)){
                        if(this.data.lockp3 && this.costp3 <= this.data.coins) {
                            this.data.coins -= this.costp3;
                            this.data.lockp3 = false;
                        }
                        if(!this.data.lockp3) this.data.actPalette = 2;
                    }
                    if(this.returnButton.checkCollision(collisionX,collisionY)) this.back = true;
                    break;
                case LONG_TOUCH:
                    this.data.coins+=10;
                    break;
                default:
                    break;
            }
        input.clearEventList();
    }

    @Override
    public void saveScene(Bundle outState) {
    }

    @Override
    public void saveSceneInFile(View myView) {
        super.saveSceneInFile(myView);
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        super.restoreSceneFromFile(myView);
    }
}