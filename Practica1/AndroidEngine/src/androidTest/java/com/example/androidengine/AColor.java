package com.example.androidengine;

import android.graphics.Color;

import com.example.engine.IColor;


public class AColor implements IColor {
    Color miColor;

    public AColor(int r_,int g_,int b_){
        miColor = new Color();
        miColor.red(r_);
        miColor.blue(b_);
        miColor.green(g_);
    }
    public AColor(int r_,int g_,int b_,int a_){
        miColor = new Color();
        miColor.red(r_);
        miColor.blue(b_);
        miColor.green(g_);
        miColor.alpha(a_);
    }

    public Color getColor(){
        return miColor;
    }
    public int getARGBColor() { return miColor.toArgb(); }
}
