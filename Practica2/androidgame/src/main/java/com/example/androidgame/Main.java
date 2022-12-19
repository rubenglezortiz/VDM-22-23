package com.example.androidgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import com.example.aengine.AndroidEngine;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class Main extends AppCompatActivity implements LocationListener {
    private static AdView mAdView;
    private SurfaceView myView;
    private AndroidEngine myEngine;
    private GameData data;
    private int jsonInt;
    private RewardedAd mRewardedAd;
    private final String TAG = "MainActivity";

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        this.myView = findViewById(R.id.surfaceView);
        this.mAdView = findViewById(R.id.adView);
        this.myEngine = new AndroidEngine(this.myView, this.mAdView);
        this.myEngine.getExternal().setActivity(this);
        this.myEngine.getExternal().createNotification();
        if (savedInstanceState != null) {
            this.myEngine.getCurrentState().restoreScene(savedInstanceState, this.myEngine);
        } else {
            this.data = new GameData();
            TitleScene titleScene = new TitleScene(this.myEngine, data);
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
                        myEngine.getExternal().setmRewardedAd(mRewardedAd);
                        Log.d(TAG, "Ad was loaded.");
                    }
                });

        //LOCATION
        while (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            LocationPermission();

        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    private void LocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},0);
        }
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

    @Override
    public void onLocationChanged(@NonNull Location location) {
        double a = location.getLatitude();
    }
}