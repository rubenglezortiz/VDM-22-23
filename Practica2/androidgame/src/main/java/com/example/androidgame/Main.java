package com.example.androidgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.example.aengine.AndroidEngine;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main extends AppCompatActivity {
    private AdView mAdView;
    private SurfaceView myView;
    private AndroidEngine myEngine;
    private int jsonInt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        this.myView = (SurfaceView)findViewById(R.id.surfaceView);
        this.mAdView = (AdView)findViewById(R.id.adView);
        //setContentView(this.myView);
        this.myEngine = new AndroidEngine(this.myView);
        if (savedInstanceState!=null){
            this.myEngine.getCurrentState().restoreScene(savedInstanceState, this.myEngine);
        }
        else {
            TitleScene titleScene = new TitleScene(this.myEngine);
            this.myEngine.getCurrentState().addScene(titleScene);
            this.myEngine.getCurrentState().restoreSceneFromFile(this.myView);
        }
        this.myEngine.getCurrentState().saveSceneInFile(myView);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        this.mAdView.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        System.out.println("_______________RESUME_______________");
        super.onResume();
        this.myEngine.resume();
    }

    @Override
    protected void onPause() {
        System.out.println("_______________PAUSE_______________");
        super.onPause();
        this.myEngine.pause();
    }

    @Override
    protected void onStop() {
        System.out.println("_______________STOP_______________");
        super.onStop();
        this.myEngine.getCurrentState().saveSceneInFile(this.myView);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        System.out.println("_______________SAVE_______________");
        super.onSaveInstanceState(outState);
        this.myEngine.getCurrentState().saveScene(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        System.out.println("_______________RESTORE_______________");
        super.onRestoreInstanceState(savedInstanceState);
        this.myEngine.getCurrentState().restoreScene(savedInstanceState, this.myEngine);
    }

    @Override
    protected void onDestroy() {
        System.out.println("_______________DESTROY_______________");
        super.onDestroy();
    }
}