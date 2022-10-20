package com.example.androidengine;

import com.example.engine.IFont;

public class AFont implements IFont {

    int size;
    boolean isBold;
    public AFont(String fontName, int s, boolean isB){
        size = s;
        isBold = isB;
    }
    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isBold() {
        return isBold;
    }
}
