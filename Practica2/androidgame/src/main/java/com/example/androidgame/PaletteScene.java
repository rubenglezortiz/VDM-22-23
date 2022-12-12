package com.example.androidgame;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.aengine.AAudio;
import com.example.aengine.AButton;
import com.example.aengine.AColor;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class PaletteScene extends HistorySuperScene {
    private String filename;
    private boolean back, lockp2, lockp3;
    private int costp2, costp3;
    private AButton returnButton, p1, p2, p3;

    public PaletteScene(AndroidEngine engine_){
        super(engine_.getGraphics());
        this.filename = "palette";
        this.back = false;
        this.lockp2 = this.lockp3 = true;
        this.costp2 = 50;
        this.costp3 = 100;
        restoreSceneFromFile(engine_.getSurfaceView());
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

        this.p1 = graphics.newButton("P1",
                x3x - (w / 2), y - (h / 2), w, h,
                18,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(255, 255, 255, 255));

        this.p2 = graphics.newButton("P2",
                x5x - (w / 2), y - (h / 2), w, h,
                18,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(255, 255, 255, 255));

        this.p3 = graphics.newButton("P3",
                x10x - (w / 2), y - (h / 2), w, h,
                7,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(255, 255, 255, 255));
        x =  graphics.getLogicWidth() / 7;
        y =  graphics.getLogicHeight() / 16;
        w = graphics.getLogicWidth() / 4;
        h = graphics.getLogicHeight() / 16;
        tx = 10;
        ty = 25;
        tSize = 12;
        this.returnButton = graphics.newButton("Volver",
                x - (w / 2), y - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(255, 255, 255, 255));
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
        graphics.drawText(Integer.toString(this.actPalette), 300,100, 20, graphics.newColor(0,0,0,255));
    }

    @Override
    public void handleInputs(AGraphics graphics, AInput input, AAudio audio){
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) input.getEventList().clone();
        for (AInput.Event event : eventList)
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = graphics.realToLogicX(((AInput.TouchInputEvent) event).x);
                    float collisionY = graphics.realToLogicY(((AInput.TouchInputEvent) event).y);
                    if (this.p1.checkCollision(collisionX, collisionY)) this.actPalette = 0;
                    else if (this.p2.checkCollision(collisionX, collisionY)){
                        if(this.lockp2 && this.costp2 <= this.coins) {
                            this.coins -= this.costp2;
                            this.lockp2 = false;
                        }
                        if(!this.lockp2) this.actPalette = 1;
                    }
                    else if (this.p3.checkCollision(collisionX, collisionY)){
                        if(this.lockp3 && this.costp3 <= this.coins) {
                            this.coins -= this.costp3;
                            this.lockp3 = false;
                        }
                        if(!this.lockp3) this.actPalette = 2;
                    }
                    graphics.setBackgroundColor(this.palettes[this.actPalette][0]);
                    if(this.returnButton.checkCollision(collisionX,collisionY)) this.back = true;
                    break;
                default:
                    break;
            }
        input.clearEventList();
    }

    @Override
    public void saveScene(Bundle outState) {}

    @Override
    public void saveSceneInFile(View myView) {
        super.saveSceneInFile(myView);
        Gson gson = new Gson();
        String aux = gson.toJson(this.lockp2);
        aux += '\n' + gson.toJson(this.lockp3);

        try(FileOutputStream fos = myView.getContext().openFileOutput(this.filename, Context.MODE_PRIVATE)){
            fos.write(aux.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        super.restoreSceneFromFile(myView);
        Gson gson = new Gson();
        try {
            FileInputStream fis = myView.getContext().openFileInput(this.filename);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            this.lockp2 = gson.fromJson(line, boolean.class);
            line = reader.readLine();
            this.lockp3 = gson.fromJson(line,boolean.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}