package com.example.aengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import androidx.annotation.NonNull;


public class AFont  {

    private Typeface typeface;

    public  AFont(Typeface t_){
        this.typeface = t_;
    }

    public AFont(String name, boolean isB, AssetManager assetMan){
        this.typeface = Typeface.createFromAsset(assetMan, name);
        if (isB) this.typeface = Typeface.create(this.typeface, Typeface.BOLD);
    }

    public float getSize() {
        return this.typeface.getStyle();
    }

    public boolean isBold() { return this.typeface.isBold(); }

    public Typeface getTypeface() { return this.typeface; }
}
