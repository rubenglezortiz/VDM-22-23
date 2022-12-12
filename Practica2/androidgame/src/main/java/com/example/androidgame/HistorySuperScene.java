package com.example.androidgame;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.aengine.AButton;
import com.example.aengine.AColor;
import com.example.aengine.AFont;
import com.example.aengine.AScene;
import com.example.aengine.AndroidEngine;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HistorySuperScene extends AScene {
    private String filename;
    protected  AndroidEngine engine;
    protected AFont font;
    protected int coins, currentLevel;
    protected AColor[][] palettes;
    protected int actPalette;

    HistorySuperScene(AndroidEngine engine_){
        this.filename = "super";
        this.engine = engine_;
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        this.coins = 0;
        this.currentLevel = 1;
        this.actPalette = 0;
        this.palettes = new AColor[3][2];
        this.palettes[0][0] = new AColor(255,255,255);
        this.palettes[0][1] = new AColor(0,0,255);
        this.palettes[1][0] = new AColor(255,0,128);
        this.palettes[1][1] = new AColor(128,0,128);
        this.palettes[2][0] = new AColor(255,128,0);
        this.palettes[2][1] = new AColor(255,255,0);
    }

    @Override
    protected void setUpScene() {}

    @Override
    public void update() {}

    @Override
    public void render() {}

    @Override
    public void handleInputs() {}

    @Override
    public void saveScene(Bundle outState) {
    }

    @Override
    public void saveSceneInFile(View myView) {
        Gson gson = new Gson();
        String aux = gson.toJson(this.coins);
        aux += '\n' + gson.toJson(this.currentLevel);
        aux += '\n' + gson.toJson(this.actPalette);

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
        this.engine = engine;
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        Gson gson = new Gson();
        try {
            FileInputStream fis = myView.getContext().openFileInput(this.filename);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            this.coins = gson.fromJson(line, int.class);
            line = reader.readLine();
            this.currentLevel = gson.fromJson(line,int.class);
            line = reader.readLine();
            this.actPalette = gson.fromJson(line,int.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.engine.getGraphics().setBackgroundColor(this.palettes[this.actPalette][0]);
    }
}
