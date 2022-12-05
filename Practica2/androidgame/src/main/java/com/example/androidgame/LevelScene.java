package com.example.androidgame;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.aengine.AButton;
import com.example.aengine.AColor;
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
import java.util.ArrayList;
import java.util.Iterator;
public class LevelScene extends HistorySuperScene {
    private String filename;
    private int changeScene;
    private boolean backToMenu;

    private AButton returnButton;
    private AButton[][] levels;

    private int id;
    private String textId;
    private int rows, cols;

    public LevelScene(AndroidEngine engine_, int id_){
        super(engine_);
        this.filename = "level";
        this.backToMenu = false;
        this.changeScene = 0;
        this.currentLevel = 1;
        this.rows = 5;
        this.cols = 4;

        //En el futuro, se tendrá que leer de fichero para cargar partida guardada
        this.id = id_;
        if(this.id == 0) this.textId = "Bosque";
        else if (this.id == 1) this.textId = "Mar";
        else if (this.id == 2) this.textId = "Ciudad";
        else this.textId = "Desierto";

        restoreSceneFromFile(this.engine.getSurfaceView());
        this.createButtons();
    }

    private void createButtons(){
        this.levels = new AButton[this.rows][this.cols]; //[y][x] : [i][j]

        int x0,y0, xi, yi, w,h;
        x0 = this.engine.getGraphics().getLogicWidth() / 5;
        y0 = this.engine.getGraphics().getLogicHeight() / 4;
        xi = this.engine.getGraphics().getLogicWidth() / 5;
        yi = xi;
        w = this.engine.getGraphics().getLogicWidth() / 6;
        h = w;

        int tx = 15;
        int ty = 40;
        int tSize = 20;

        int x, y;
        String text;
        AColor unlockedLevelColor = this.engine.getGraphics().newColor(250, 215, 160, 255);
        AColor lockedLevelColor = this.engine.getGraphics().newColor(100, 65, 10, 255);
        for(int i = 0; i < this.rows; ++i){
            y = y0 + (yi * i);
            for(int j = 0; j < this.cols; ++j){
                x = x0 + (xi * j);
                int level = i * this.cols + j + 1 + (this.id * 20);
                text = Integer.toString(level);
                AColor color = (this.currentLevel>=level) ?  unlockedLevelColor : lockedLevelColor;
                this.levels[i][j] = this.engine.getGraphics().newButton(text,
                        x - (w / 2), y - (h / 2), w, h,
                        tx,ty, tSize,
                        this.font,
                        this.engine.getGraphics().newColor(0, 0, 0, 255),
                        color);
            }
        }

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
        if (this.changeScene != 0){
            //saveSceneInFile(this.engine.getSurfaceView());
            //new Nivel(this.changeScene) //changeScene indica el nivel al que pasas, habiendo 80 niveles.
            this.changeScene = 0;
        }
        if(this.backToMenu){
            //saveSceneInFile(this.engine.getSurfaceView());
            this.engine.getCurrentState().removeScene(1);
        }
    }

    @Override
    public void render(){
        this.engine.getGraphics().setFont(this.font);
        this.engine.getGraphics().drawText(this.textId,
                this.engine.getGraphics().getLogicWidth() / 2.0f - 90,
                this.engine.getGraphics().getLogicHeight() / 8.0f, 25,
                this.engine.getGraphics().newColor(0,0,0,255));

        this.engine.getGraphics().drawButton(this.returnButton);

        for (int i = 0; i < this.rows; ++i){
            for (int j = 0; j < this.cols; ++j){
                this.engine.getGraphics().drawButton(this.levels[i][j]);
            }
        }

        this.engine.getGraphics().drawText(Integer.toString(this.coins), 300,100, 20, this.engine.getGraphics().newColor(0,0,0,255));
    }

    @Override
    public void handleInputs(){
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) this.engine.getInput().getEventList().clone();
        for (AInput.Event event : eventList)
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = this.engine.getGraphics().realToLogicX(((AInput.TouchInputEvent) event).x);
                    float collisionY = this.engine.getGraphics().realToLogicY(((AInput.TouchInputEvent) event).y);
                    if (this.returnButton.checkCollision(collisionX, collisionY))
                        this.backToMenu = true;
                    else for (int i = 0; i < this.rows; ++i)
                            for (int j = 0; j < this.cols; ++j)
                                if (this.levels[i][j].checkCollision(collisionX, collisionY))
                                    this.changeScene = i * this.cols + j + 1 + (this.id * 20);
                    break;
                //DEBUG
                case LONG_TOUCH:
                  this.coins++;
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
        Gson gson = new Gson();
        String aux = gson.toJson(this.currentLevel);

        try(FileOutputStream fos = myView.getContext().openFileOutput(this.filename, Context.MODE_PRIVATE)){
            fos.write(aux.getBytes(StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
        this.engine = engine;
        this.currentLevel = savedInstanceState.getInt("currentLevel");
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
            this.currentLevel = gson.fromJson(line, int.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}