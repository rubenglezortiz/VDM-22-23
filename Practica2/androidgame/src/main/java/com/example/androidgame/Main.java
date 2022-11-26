package com.example.androidgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;

import com.example.aengine.AndroidEngine;

public class Main extends AppCompatActivity {
    private SurfaceView myView;
    private AndroidEngine myEngine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.myView = new SurfaceView(this);
        setContentView(this.myView);

        this.myEngine = new AndroidEngine(this.myView);
        TitleScene titleScene = new TitleScene (this.myEngine);
        this.myEngine.getCurrentState().addScene(titleScene);

        //MainScene ms = new MainScene(this.myEngine,5,5);
        //this.myEngine.getCurrentState().addScene(ms);
        if (savedInstanceState!=null){
            this.myEngine.getCurrentState().restoreScene(savedInstanceState);
        }

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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        this.myEngine.getCurrentState().saveScene(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int a = savedInstanceState.getInt("ejemplo");
        System.out.println(a);
    }
}