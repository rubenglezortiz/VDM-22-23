package com.example.aengine;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;

public class AExternal {
    private AdView mAdView;
    private AdRequest adRequestBanner;
    private RewardedAd mRewardedAd;
    private Activity activity;
    private String TAG;
    public AExternal(AdView mAdView_)
    {
        mAdView = mAdView_;
    }
    public void loadBanner()
    {
        this.adRequestBanner = new AdRequest.Builder().build();
        this.mAdView.loadAd(adRequestBanner);
    }
    public void loadRewardedAd()
    {


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
    public void setActivity(Activity activity_)
    {
        this.activity = activity_;
    }
    public void setmRewardedAd(RewardedAd rewardedAd_)
    {
        this.mRewardedAd = rewardedAd_;
    }
}
