package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;

import com.example.aengine.AndroidEngine;
import com.example.logica.MainScene;

public class Main extends AppCompatActivity {
    private SurfaceView myView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.myView = new SurfaceView(this);
        setContentView(this.myView);

        AndroidEngine myEngine = new AndroidEngine(this.myView);
        //MainScene mainScene = new MainScene();
    }
}