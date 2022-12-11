package com.example.androidgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.example.aengine.AndroidEngine;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main extends AppCompatActivity {
    private SurfaceView myView;
    private AndroidEngine myEngine;
    private int jsonInt;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //this.myView = new SurfaceView(this);
        this.myView = findViewById(R.id.surfaceView);
        setContentView(R.layout.activity_main);
        this.myEngine = new AndroidEngine(this.myView);

        if (savedInstanceState!=null){
            this.myEngine.getCurrentState().restoreScene(savedInstanceState, this.myEngine);
        }
        else {
            TitleScene titleScene = new TitleScene (this.myEngine);
            this.myEngine.getCurrentState().addScene(titleScene);
            this.myEngine.getCurrentState().restoreSceneFromFile(this.myView);
        }
        this.myEngine.getCurrentState().saveSceneInFile(myView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.myEngine.resume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.myEngine.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //GUARDAR LA INFO EN EL ONSAVEINSTANCE
        this.myEngine.getCurrentState().saveSceneInFile(this.myView);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        this.myEngine.getCurrentState().saveScene(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.myEngine.getCurrentState().restoreScene(savedInstanceState);
    }
}