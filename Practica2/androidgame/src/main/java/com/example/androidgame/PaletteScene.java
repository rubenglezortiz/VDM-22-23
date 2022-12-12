package com.example.androidgame;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.aengine.AButton;
import com.example.aengine.AColor;
import com.example.aengine.AInput;
import com.example.aengine.AndroidEngine;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PaletteScene extends HistorySuperScene {
    private String filename;
    private boolean back;
    private AButton returnButton, p1, p2, p3;

    public PaletteScene(AndroidEngine engine_){
        super(engine_);
        this.filename = "palette";
        this.back = false;
        restoreSceneFromFile(this.engine.getSurfaceView());
        this.createButtons();
    }

    private void createButtons(){
        int x, x3x, x5x, x10x, y, w, h, tx, ty, tSize;
        x3x = this.engine.getGraphics().getLogicWidth() / 4;
        x5x = this.engine.getGraphics().getLogicWidth() * 2 / 4;
        x10x = this.engine.getGraphics().getLogicWidth() * 3 / 4;
        y = this.engine.getGraphics().getLogicHeight() / 2;
        w = this.engine.getGraphics().getLogicWidth() / 5;
        h = this.engine.getGraphics().getLogicHeight() / 10;
        ty = 35;
        tSize = 15;

        this.p1 = this.engine.getGraphics().newButton("P1",
                x3x - (w / 2), y - (h / 2), w, h,
                18,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

        this.p2 = this.engine.getGraphics().newButton("P2",
                x5x - (w / 2), y - (h / 2), w, h,
                18,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

        this.p3 = this.engine.getGraphics().newButton("P3",
                x10x - (w / 2), y - (h / 2), w, h,
                7,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));
        x =  this.engine.getGraphics().getLogicWidth() / 7;
        y =  this.engine.getGraphics().getLogicHeight() / 16;
        w = this.engine.getGraphics().getLogicWidth() / 4;
        h = this.engine.getGraphics().getLogicHeight() / 16;
        tx = 10;
        ty = 25;
        tSize = 12;
        this.returnButton = this.engine.getGraphics().newButton("Volver",
                x - (w / 2), y - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));
    }

    @Override
    protected void setUpScene() {}

    @Override
    public void update(){
        if (this.back) this.engine.getCurrentState().removeScene(1);
    }

    @Override
    public void render(){
        this.engine.getGraphics().drawButton(this.returnButton);
        this.engine.getGraphics().drawButton(this.p1);
        this.engine.getGraphics().drawButton(this.p2);
        this.engine.getGraphics().drawButton(this.p3);
        this.engine.getGraphics().drawText(Integer.toString(this.actPalette), 300,100, 20, this.engine.getGraphics().newColor(0,0,0,255));
    }

    @Override
    public void handleInputs(){
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) this.engine.getInput().getEventList().clone();
        for (AInput.Event event : eventList)
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = this.engine.getGraphics().realToLogicX(((AInput.TouchInputEvent) event).x);
                    float collisionY = this.engine.getGraphics().realToLogicY(((AInput.TouchInputEvent) event).y);
                    if (this.p1.checkCollision(collisionX, collisionY)) this.actPalette = 0;
                    else if (this.p2.checkCollision(collisionX, collisionY)) this.actPalette = 1;
                    else if (this.p3.checkCollision(collisionX, collisionY)) this.actPalette = 2;
                    this.engine.getGraphics().setBackgroundColor(this.palettes[this.actPalette][0]);
                    if(this.returnButton.checkCollision(collisionX,collisionY)) this.back = true;
                  break;
                default:
                    break;
            }
        this.engine.getInput().clearEventList();
    }

    @Override
    public void saveScene(Bundle outState) {}

    @Override
    public void saveSceneInFile(View myView) {
        super.saveSceneInFile(myView);
        /*Gson gson = new Gson();
        String aux = gson.toJson(this.currentLevel);

        try(FileOutputStream fos = myView.getContext().openFileOutput(this.filename, Context.MODE_PRIVATE)){
            fos.write(aux.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
        this.engine = engine;
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        super.restoreSceneFromFile(myView);
        /*
        Gson gson = new Gson();
        try {
            FileInputStream fis = myView.getContext().openFileInput(this.filename);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

         */
    }
}