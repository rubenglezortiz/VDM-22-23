package com.example.desktopengine;

import com.example.engine.IColor;

public class DColor implements IColor {
    Color miColor;

    public DColor(int mColor){
        miColor = mColor;
    }

    public Color getColor(){
        return miColor;
    }
}
