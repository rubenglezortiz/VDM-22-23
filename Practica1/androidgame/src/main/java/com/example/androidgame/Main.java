package com.example.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;

import com.example.aengine.AndroidEngine;
import com.example.logica.BoardSelectionScene;
import com.example.logica.MainScene;
import com.example.logica.TitleScene;

import java.util.Objects;

public class Main extends AppCompatActivity {
    private SurfaceView myView;
    private AndroidEngine myEngine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        this.myView = new SurfaceView(this);
        setContentView(this.myView);

        this.myEngine = new AndroidEngine(this.myView);
        TitleScene titleScene = new TitleScene (this.myEngine);
        this.myEngine.getCurrentState().addScene(titleScene);
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
}