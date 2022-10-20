package com.example.desktopengine;

import com.example.engine.IColor;

import java.awt.Color;

public class DColor implements IColor {
    Color miColor;

    public DColor(int r_,int g_,int b_){
        miColor = new Color(r_,g_,b_);
    }

    public Color getColor(){
        return miColor;
    }
}
