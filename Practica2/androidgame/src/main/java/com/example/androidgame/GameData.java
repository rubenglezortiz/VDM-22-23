package com.example.androidgame;

import android.content.Context;
import android.view.View;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class GameData implements Serializable {
    public String filename;
    public int coins, forestLevels, seaLevels, cityLevels, desertLevels, selectedPalette, actPalette, levelInProgress;
    public boolean lockp2, lockp3;

    public GameData(){
        this.filename = "data";
        this.coins = this.levelInProgress = 0;
        this.forestLevels = this.seaLevels = this.cityLevels = this.desertLevels = 1;
        this.actPalette = this.selectedPalette = 0;
        this.lockp2 = this.lockp3 = true;
    }

    public void saveDataInFile(View myView){
        Gson gson = new Gson();
        String aux = gson.toJson(this.coins);
        aux += '\n' + gson.toJson(this.forestLevels);
        aux += '\n' + gson.toJson(this.seaLevels);
        aux += '\n' + gson.toJson(this.cityLevels);
        aux += '\n' + gson.toJson(this.desertLevels);
        aux += '\n' + gson.toJson(this.actPalette);
        aux += '\n' + gson.toJson(this.selectedPalette);
        aux += '\n' + gson.toJson(this.levelInProgress);
        aux += '\n' + gson.toJson(this.lockp2);
        aux += '\n' + gson.toJson(this.lockp3);

        try(FileOutputStream fos = myView.getContext().openFileOutput(this.filename, Context.MODE_PRIVATE)){
            fos.write(aux.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restoreDataFromFile(View myView){
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
            line = reader.readLine();
            this.selectedPalette = gson.fromJson(line,int.class);
            line = reader.readLine();
            this.levelInProgress = gson.fromJson(line,int.class);
            line = reader.readLine();
            this.lockp2 = gson.fromJson(line, boolean.class);
            line = reader.readLine();
            this.lockp3 = gson.fromJson(line, boolean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
