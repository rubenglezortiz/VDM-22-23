package com.example.aengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.example.engine.IFont;

public class AFont implements IFont {

    private Typeface typeface;

    public AFont(String name, boolean isB, AssetManager assetMan){
        this.typeface = Typeface.createFromAsset(assetMan, name);
        if (isB) this.typeface = Typeface.create(this.typeface, Typeface.BOLD);
    }

    @Override
    public float getSize() {
        return this.typeface.getStyle();
    }

    @Override
    public boolean isBold() { return this.typeface.isBold(); }

    public Typeface getTypeface() { return this.typeface; };
}
