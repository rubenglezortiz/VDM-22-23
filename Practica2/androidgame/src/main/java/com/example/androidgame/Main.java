package com.example.androidgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import com.example.aengine.AndroidEngine;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Main extends AppCompatActivity {
    private static AdView mAdView;
    private SurfaceView myView;
    private AndroidEngine myEngine;
    private GameData data;
    private int jsonInt;
    private RewardedAd mRewardedAd;
    private final String TAG = "MainActivity";
    private static AdRequest adRequestBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        this.myView = findViewById(R.id.surfaceView);
        this.mAdView = findViewById(R.id.adView);
        this.myEngine = new AndroidEngine(this.myView);
        if (savedInstanceState!=null){
            this.myEngine.getCurrentState().restoreScene(savedInstanceState, this.myEngine);
        }
        else {
            this.data = new GameData();
            TitleScene titleScene = new TitleScene(this.myEngine,data);
            this.myEngine.getCurrentState().addScene(titleScene);
            this.myEngine.getCurrentState().restoreSceneFromFile(this.myView);
        }

        //_______________ADS_______________
        this.myEngine.getCurrentState().saveSceneInFile(myView);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.toString());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });
        this.adRequestBanner = new AdRequest.Builder().build();
        this.mAdView.loadAd(adRequestBanner);
    }
   // if (mRewardedAd != null) {
   //     mRewardedAd.show(, new OnUserEarnedRewardListener() {
   //         @Override
   //         public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
   //             // Handle the reward.
   //             Log.d(TAG, "The user earned the reward.");
   //             //int rewardAmount = rewardItem.getAmount();
   //             //String rewardType = rewardItem.getType();
   //         }
   //     });
   // } else {
   //     System.out.println("The rewarded ad wasn't ready yet.");
   //     Log.d(TAG, "The rewarded ad wasn't ready yet.");
   // };
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