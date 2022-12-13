package com.example.androidgame;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.aengine.AAudio;
import com.example.aengine.AButton;
import com.example.aengine.AGraphics;
import com.example.aengine.AInput;
import com.example.aengine.AState;
import com.example.aengine.AndroidEngine;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SelectCategoryScene extends HistorySuperScene implements Serializable {
    private String filename;
    private int changeScene;
    private boolean backToMenu;
    private AButton returnButton, forestButton, seaButton, cityButton, desertButton;
    private int forestFinished, seaFinished, cityFinished, desertFinished;

    public SelectCategoryScene(AndroidEngine engine_, GameData data_){
        super(engine_.getGraphics(), data_);
        this.filename = "category";
        this.changeScene = 0;
        this.backToMenu = false;

        //En el futuro, se tendrá que leer de fichero para cargar partida guardada
        this.forestFinished = this.seaFinished = this.cityFinished = this.desertFinished = 0;
        this.createButtons(engine_.getGraphics());
    }

    private void createButtons(AGraphics graphics){
        int x1, x2, y1, y2, w, h, tx, ty, tSize;
        x1 = graphics.getLogicWidth() / 4;
        x2 = graphics.getLogicWidth() * 3 / 4;
        y1 = graphics.getLogicHeight() * 3 / 8;
        y2 = graphics.getLogicHeight() * 11 / 16;
        w = graphics.getLogicWidth() * 5 / 13;
        h = graphics.getLogicHeight() / 4;
        tx = 12;
        ty = 12;
        tSize = 12;

        String locked = "BLOQUEADO";
        String sea = "MAR";
        String city = "CIUDAD";
        String desert = "DESIERTO";

        if (forestFinished < 5) sea = locked;
        if (seaFinished < 5) city = locked;
        if (cityFinished < 5) desert = locked;

        this.forestButton = graphics.newButton("BOSQUE",
                x1 - (w / 2.0f), y1 - (h / 2.0f), w, h,
                tx,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(250, 215, 160, 255));

        this.seaButton = graphics.newButton(sea,
                x2 - (w / 2.0f), y1 - (h / 2.0f), w, h,
                tx,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(250, 215, 160, 255));

        this.cityButton = graphics.newButton(city,
                x1 - (w / 2.0f), y2 - (h / 2.0f), w, h,
                tx,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(250, 215, 160, 255));

        this.desertButton = graphics.newButton(desert,
                x2 - (w / 2), y2 - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(250, 215, 160, 255));


        int x, y;
        x =  graphics.getLogicWidth() / 7;
        y =  graphics.getLogicHeight() / 16;
        w = graphics.getLogicWidth() / 4;
        h = graphics.getLogicHeight() / 16;

        this.returnButton = graphics.newButton("Volver",
                x - (w / 2), y - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(255, 255, 255, 255));

    }

    protected void setUpScene() {}

    @Override
    public void update(AndroidEngine engine) {
        if (this.changeScene != 0){
            engine.getCurrentState().addScene(new LevelScene(engine, this.changeScene - 1, this.data));
            this.changeScene = 0;
        }
        if(this.backToMenu){
            engine.getCurrentState().removeScene(1);
        }
    }

    @Override
    public void render(AGraphics graphics) {
        super.render(graphics);
        //graphics.setFont(this.font);
        graphics.drawText("Elige la categoría en la que quieres jugar",
                graphics.getLogicWidth()/10.0f,
                graphics.getLogicHeight() * 3.0f/ 16.0f, 8,
                graphics.newColor(0,0,0,255));

        graphics.drawButton(this.forestButton);
        graphics.drawButton(this.seaButton);
        graphics.drawButton(this.cityButton);
        graphics.drawButton(this.desertButton);
        graphics.drawButton(this.returnButton);
    }

    @Override
    public void handleInputs(AGraphics graphics, AInput input, AAudio audio) {
        super.handleInputs(graphics, input, audio);
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) input.getEventList().clone();
        for (AInput.Event event : eventList) {
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = graphics.realToLogicX(((AInput.TouchInputEvent) event).x);
                    float collisionY = graphics.realToLogicY(((AInput.TouchInputEvent) event).y);
                    if (this.forestButton.checkCollision(collisionX, collisionY))
                        this.changeScene = 1;
                    else if (this.forestButton.checkCollision(collisionX, collisionY)) {
                        if (this.forestFinished >= 5) this.changeScene = 2;}
                    else if (this.seaButton.checkCollision(collisionX, collisionY)) {
                        if (this.seaFinished >= 5) this.changeScene = 3;}
                    else if (this.cityButton.checkCollision(collisionX, collisionY)) {
                        if (this.cityFinished >= 5) this.changeScene = 4;}
                    else if (this.returnButton.checkCollision(collisionX, collisionY)) this.backToMenu = true;
                    break;
                default:
                    break;
            }
        }
        input.clearEventList();
    }

    @Override
    public void saveScene(Bundle outState) {
        /*outState.putInt("coins", this.coins);
        outState.putInt("forest", this.forestFinished);
        outState.putInt("sea", this.seaFinished);
        outState.putInt("city", this.cityFinished);
        outState.putInt("desert", this.desertFinished);
        outState.putInt("changeScene", this.changeScene);
         */
    }

    @Override
    public void saveSceneInFile(View myView) {
        super.saveSceneInFile(myView);

        Gson gson = new Gson();
        String aux = gson.toJson(this.forestFinished);

        try(FileOutputStream fos = myView.getContext().openFileOutput(this.filename, Context.MODE_PRIVATE)){
            fos.write(aux.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(FileOutputStream fos = myView.getContext().openFileOutput(this.filename, Context.MODE_PRIVATE)){
            fos.write(aux.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
        super.restoreScene(savedInstanceState, engine);
        /*this.coins = savedInstanceState.getInt("coins");
        this.forestFinished = savedInstanceState.getInt("forest");
        this.seaFinished = savedInstanceState.getInt("sea");
        this.cityFinished = savedInstanceState.getInt("city");
        this.desertFinished = savedInstanceState.getInt("desert");
        this.changeScene = savedInstanceState.getInt("changeScene");
        setUpScene();
         */
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
            this.forestFinished = gson.fromJson(line, int.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}