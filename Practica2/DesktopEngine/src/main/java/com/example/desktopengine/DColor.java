package com.example.desktopengine;

import com.example.engine.IColor;

import java.awt.Color;

public class DColor implements IColor {
    private Color miColor;

    public DColor(int r_,int g_,int b_){
        this.miColor = new Color(r_,g_,b_);
    } //a = 255
    public DColor(int r_,int g_,int b_,int a_){
        this.miColor = new Color(r_,g_,b_,a_);
    }

    public Color getColor(){
        return this.miColor;
    }
}
