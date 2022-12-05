package com.example.androidgame;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.aengine.AButton;
import com.example.aengine.AFont;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
import com.example.aengine.AndroidEngine;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;

public class SelectCategoryScene extends HistorySuperScene {
    private String filename;
    private int changeScene;
    private boolean backToMenu;
    private AButton returnButton, forestButton, seaButton, cityButton, desertButton;
    private int forestFinished, seaFinished, cityFinished, desertFinished;

    public SelectCategoryScene(AndroidEngine engine_){
        super(engine_);
        this.filename = "category";
        this.changeScene = 0;
        this.backToMenu = false;

        //En el futuro, se tendrá que leer de fichero para cargar partida guardada
        this.forestFinished = this.seaFinished = this.cityFinished = this.desertFinished = 0;
        restoreSceneFromFile(this.engine.getSurfaceView());
        this.createButtons();
    }

    private void createButtons(){
        int x1, x2, y1, y2, w, h, tx, ty, tSize;
        x1 = this.engine.getGraphics().getLogicWidth() / 4;
        x2 = this.engine.getGraphics().getLogicWidth() * 3 / 4;
        y1 = this.engine.getGraphics().getLogicHeight() * 3 / 8;
        y2 = this.engine.getGraphics().getLogicHeight() * 11 / 16;
        w = this.engine.getGraphics().getLogicWidth() * 5 / 13;
        h = this.engine.getGraphics().getLogicHeight() / 4;
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

        this.forestButton = this.engine.getGraphics().newButton("BOSQUE",
                x1 - (w / 2), y1 - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(250, 215, 160, 255));

        this.seaButton = this.engine.getGraphics().newButton(sea,
                x2 - (w / 2), y1 - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(250, 215, 160, 255));

        this.cityButton = this.engine.getGraphics().newButton(city,
                x1 - (w / 2), y2 - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(250, 215, 160, 255));

        this.desertButton = this.engine.getGraphics().newButton(desert,
                x2 - (w / 2), y2 - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(250, 215, 160, 255));


        int x, y;
        x =  this.engine.getGraphics().getLogicWidth() / 7;
        y =  this.engine.getGraphics().getLogicHeight() / 16;
        w = this.engine.getGraphics().getLogicWidth() / 4;
        h = this.engine.getGraphics().getLogicHeight() / 16;

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
    public void update() {
        if (this.changeScene != 0){
            //saveSceneInFile(this.engine.getSurfaceView());
            this.engine.getCurrentState().addScene(new LevelScene(this.engine, this.changeScene - 1));
            this.changeScene = 0;
        }
        if(this.backToMenu){
            //saveSceneInFile(this.engine.getSurfaceView());
            this.engine.getCurrentState().removeScene(1);
        }
    }

    @Override
    public void render() {
        this.engine.getGraphics().setFont(this.font);
        this.engine.getGraphics().drawText("Elige la categoría en la que quieres jugar",
                this.engine.getGraphics().getLogicWidth()/10.0f,
                this.engine.getGraphics().getLogicHeight() * 3.0f/ 16.0f, 8,
                this.engine.getGraphics().newColor(0,0,0,255));

        this.engine.getGraphics().drawButton(this.forestButton);
        this.engine.getGraphics().drawButton(this.seaButton);
        this.engine.getGraphics().drawButton(this.cityButton);
        this.engine.getGraphics().drawButton(this.desertButton);
        this.engine.getGraphics().drawButton(this.returnButton);
        this.engine.getGraphics().drawText(Integer.toString(this.coins), 100,100, 20, this.engine.getGraphics().newColor(0,0,0,255));
    }

    @Override
    public void handleInputs() {
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) this.engine.getInput().getEventList().clone();
        for (AInput.Event event : eventList) {
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = this.engine.getGraphics().realToLogicX(((AInput.TouchInputEvent) event).x);
                    float collisionY = this.engine.getGraphics().realToLogicY(((AInput.TouchInputEvent) event).y);
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
                case LONG_TOUCH:
                    this.coins++;
                    break;
                default:
                    break;
            }
        }
        this.engine.getInput().clearEventList();
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