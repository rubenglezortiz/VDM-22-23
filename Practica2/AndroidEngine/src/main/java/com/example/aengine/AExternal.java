package com.example.aengine;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AExternal implements LocationListener {
    private Activity activity;
    private final View myView;
    private final AdView mAdView;
    private RewardedAd mRewardedAd;
    private NotificationCompat.Builder builder;
    private NotificationManagerCompat notManager;
    private LocationManager locationManager;
    private double latitude, longitude;

    public AExternal(View view_, AdView mAdView_) {
        this.myView = view_;
        this.mAdView = mAdView_;

    }

    //ADDS
    public void setUpExternal(Activity activity_){
        this.activity = activity_;
        createNotification();
        setUpLocationManager();
    }

    public void loadBanner() {
        this.mAdView.loadAd(new AdRequest.Builder().build());
    }

    public void loadRewardedAd(){
        String TAG = null;
        this.activity.runOnUiThread(() -> {
            if (mRewardedAd != null) {
                mRewardedAd.show(activity, rewardItem -> {
                    // Handle the reward.
                    Log.d(TAG, "The user earned the reward.");
                    //int rewardAmount = rewardItem.getAmount();
                    //String rewardType = rewardItem.getType();
                });
                AdRequest adRequest = new AdRequest.Builder().build();
                RewardedAd.load(activity, "ca-app-pub-3940256099942544/5224354917",
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
            }
            else {
                System.out.println("The rewarded ad wasn't ready yet.");
                Log.d(TAG, "The rewarded ad wasn't ready yet.");
            }
        });
    }

    public void setmRewardedAd(RewardedAd rewardedAd_)
    {
        this.mRewardedAd = rewardedAd_;
    }

    //NOTIFICATIONS
    public void createNotification(){
        //_______________NOTIFICATION_______________
        //_______________CANAL_______________
        if (Build.VERSION. SDK_INT >= Build.VERSION_CODES. O) {
            CharSequence name = this.activity.getString(R.string.channel_name);
            String description = this.activity.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("my_id", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = this.activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        //NOTIFICATION
         this.builder = new NotificationCompat.Builder(this.myView.getContext(), "my_id")
                .setSmallIcon(R.drawable.corazon_con_vida)
                .setContentTitle("PEAZO JUEGO EH")
                .setContentText("Mira que paletas más reguapas")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Ni el Ikea te ofrece tremenda gama de colores"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        this.notManager = NotificationManagerCompat.from(this.activity);
    }

    public void pushNotification(){
        this.notManager.notify(0, this.builder.build());
    }

    //LOCATION
    private void setUpLocationManager() {
        if (ActivityCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            locationPermission();
        this.locationManager = (LocationManager) this.activity.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    private void locationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            ActivityCompat.requestPermissions(this.activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 0);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {}

    @Override
    public void onProviderDisabled(@NonNull String provider) {}

    public double getLatitude(){ return this.latitude; }

    public double getLongitude(){ return this.longitude; }

    public void startActivity(Intent intent)
    {
        this.activity.startActivity(intent);
    }
    public void setGPS(){
        setUpLocationManager();
    }
}
