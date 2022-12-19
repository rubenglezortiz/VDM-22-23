package com.example.aengine;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;

public class AExternal {
    private String TAG;
    private Activity activity;
    private View myView;
    private AdView mAdView;
    private AdRequest adRequestBanner;
    private RewardedAd mRewardedAd;
    private NotificationCompat.Builder builder;
    private NotificationManagerCompat notManager;

    public AExternal(View view_, AdView mAdView_) {
        this.myView = view_;
        this.mAdView = mAdView_;
    }

    public void loadBanner() {
        this.adRequestBanner = new AdRequest.Builder().build();
        this.mAdView.loadAd(adRequestBanner);
    }

    public void loadRewardedAd(){
        this.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mRewardedAd != null) {
                    mRewardedAd.show(activity, new OnUserEarnedRewardListener() {
                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                            // Handle the reward.
                            Log.d(TAG, "The user earned the reward.");
                            //int rewardAmount = rewardItem.getAmount();
                            //String rewardType = rewardItem.getType();
                        }
                    });
                }
                else {
                    System.out.println("The rewarded ad wasn't ready yet.");
                    Log.d(TAG, "The rewarded ad wasn't ready yet.");
                }
            }
        });
    }

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

    public void setActivity(Activity activity_)
    {
        this.activity = activity_;
    }

    public void setmRewardedAd(RewardedAd rewardedAd_)
    {
        this.mRewardedAd = rewardedAd_;
    }
}
