package com.example.androidgame;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.aengine.AAudio;
import com.example.aengine.AButton;
import com.example.aengine.AColor;
import com.example.aengine.AFont;
import com.example.aengine.AGraphics;
import com.example.aengine.AImage;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
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

public class HistorySuperScene extends AScene {
    protected String filename, font;
    private String coinsImage;
    protected int coins, forestLevels, seaLevels, cityLevels, desertLevels;
    protected AColor[][] palettes;
    protected int actPalette;

    HistorySuperScene(AGraphics graphics){
        this.filename = "super";
        this.font = ("font.TTF");
        this.coinsImage ="moneda.png";
        this.coins = 0;
        this.forestLevels = this.seaLevels = this.cityLevels = this.desertLevels = 1;
        this.actPalette = 0;
        this.palettes = new AColor[3][2];
        this.palettes[0][0] = new AColor(255,255,255);
        this.palettes[0][1] = new AColor(0,0,255);
        this.palettes[1][0] = new AColor(255,0,128);
        this.palettes[1][1] = new AColor(128,0,128);
        this.palettes[2][0] = new AColor(255,128,0);
        this.palettes[2][1] = new AColor(255,255,0);
    }

    //@Override
    //protected void setUpScene() {}

    @Override
    public void update(AndroidEngine engine) {}

    @Override
    public void render(AGraphics graphics) {
        //graphics.setFont(this.font);
        graphics.drawImage(this.coinsImage, 350,0,25,25);
        graphics.drawText(this.font, String.valueOf(this.coins), 320 ,17, 10, new AColor(0,0,0,255));
    }

    @Override
    public void handleInputs(AGraphics graphics, AInput input, AAudio audio) {}

    @Override
    public void saveScene(Bundle outState) {
    }

    @Override
    public void saveSceneInFile(View myView) {
        Gson gson = new Gson();
        String aux = gson.toJson(this.coins);
        aux += '\n' + gson.toJson(this.forestLevels);
        aux += '\n' + gson.toJson(this.seaLevels);
        aux += '\n' + gson.toJson(this.cityLevels);
        aux += '\n' + gson.toJson(this.desertLevels);
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
            this.forestLevels = gson.fromJson(line,int.class);
            line = reader.readLine();
            this.seaLevels = gson.fromJson(line,int.class);
            line = reader.readLine();
            this.cityLevels = gson.fromJson(line,int.class);
            line = reader.readLine();
            this.desertLevels = gson.fromJson(line,int.class);
            line = reader.readLine();
            this.actPalette = gson.fromJson(line,int.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //this.engine.getGraphics().setBackgroundColor(this.palettes[this.actPalette][0]);
    }
}
