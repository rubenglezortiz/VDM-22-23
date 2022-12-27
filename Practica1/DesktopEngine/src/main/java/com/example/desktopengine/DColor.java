package com.example.desktopengine;

import com.example.engine.IColor;
import java.awt.Color;

public class DColor implements IColor {
    private final int r,g,b,a;

    public DColor(int r_,int g_,int b_){
        r = r_; g = g_; b = b_; a=255;
    }

    public DColor(int r_,int g_,int b_,int a_){
        r = r_; g = g_; b = b_; a = a_;
    }

    public Color getColor(){
        return new Color(r, g, b, a);
    }
}
