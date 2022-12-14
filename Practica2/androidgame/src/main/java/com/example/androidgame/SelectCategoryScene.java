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
    private int changeScene;
    private boolean backToMenu;
    private AButton returnButton, forestButton, seaButton, cityButton, desertButton;

    public SelectCategoryScene(AndroidEngine engine_, GameData data_){
        super(engine_.getGraphics(), data_);
        this.changeScene = 0;
        this.backToMenu = false;

        //En el futuro, se tendrá que leer de fichero para cargar partida guardada
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

        String sea = "Playa.png";
        String city = "Ciudad.png";
        String desert = "Desierto.png";

        if (this.data.forestLevels < 5) sea = "PlayaBloqueado.png";
        if (this.data.seaLevels < 5) city = "CiudadBloqueado.png";
        if (this.data.cityLevels < 5) desert = "DesiertoBloqueado.png";

        this.forestButton = graphics.newButton("Bosque.png",
                x1 - (w / 2.0f), y1 - (h / 2.0f), w, h,
                tx,ty, tSize,
                graphics.newColor(0,0,0,0));

        this.seaButton = graphics.newButton(sea,
                x2 - (w / 2.0f), y1 - (h / 2.0f), w, h,
                tx,ty, tSize,
                graphics.newColor(0,0,0,0));

        this.cityButton = graphics.newButton(city,
                x1 - (w / 2.0f), y2 - (h / 2.0f), w, h,
                tx,ty, tSize,
                graphics.newColor(0,0,0,0));

        this.desertButton = graphics.newButton(desert,
                x2 - (w / 2), y2 - (h / 2), w, h,
                tx,ty, tSize,
                graphics.newColor(0,0,0,0));


        int x, y;
        x =  graphics.getLogicWidth() / 7;
        y =  graphics.getLogicHeight() / 16;
        w = graphics.getLogicWidth() / 4;
        h = graphics.getLogicHeight() / 16;

        this.returnButton = graphics.newButton("Volver.png",
                x - (w / 2), y - (h / 2), w, h,
                tx,ty, tSize,
                graphics.newColor(0,0,0,0));

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
    public void handleInputs(AInput input, AAudio audio) {
        super.handleInputs(input, audio);
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) input.getEventList().clone();
        for (AInput.Event event : eventList) {
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = ((AInput.TouchInputEvent) event).x;
                    float collisionY = ((AInput.TouchInputEvent) event).y;
                    if (this.forestButton.checkCollision(collisionX, collisionY))
                        this.changeScene = 1;
                    else if (this.seaButton.checkCollision(collisionX, collisionY)) {
                        if (this.data.forestLevels >= 5) this.changeScene = 2;}
                    else if (this.cityButton.checkCollision(collisionX, collisionY)) {
                        if (this.data.seaLevels >= 5) this.changeScene = 3;}
                    else if (this.desertButton.checkCollision(collisionX, collisionY)) {
                        if (this.data.cityLevels >= 5) this.changeScene = 4;}
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
    }
}