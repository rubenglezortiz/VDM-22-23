package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.example.engine.IFont;

public class AFont implements IFont {

    Typeface typeface;

    int size;
    boolean isBold;
    public AFont(String fontPath, int s, boolean isB, AssetManager assetMan){
        size = s;
        isBold = isB;

        Typeface typeface = Typeface.createFromAsset(assetMan, fontPath);
        if (isB) typeface = Typeface.create(typeface, Typeface.BOLD);
    }
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isBold() {
        return isBold;
    }

    public Typeface getTypeface() { return typeface; };
}
