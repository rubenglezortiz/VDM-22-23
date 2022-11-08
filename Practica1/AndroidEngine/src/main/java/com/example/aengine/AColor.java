package com.example.aengine;

import android.graphics.Color;
import android.os.Build;

import com.example.engine.IColor;


public class AColor implements IColor {
    private Color miColor;
    private int r,g,b,a;

    public AColor(int r_,int g_,int b_){
        this.r = r_; this.g = g_; this.b = b_; this.a = 255;
        this.miColor = new Color();
        this.miColor.red(this.r);
        this.miColor.blue(this.b);
        this.miColor.green(this.g);
    }
    public AColor(int r_,int g_,int b_,int a_){
        this.r = r_; this.g = g_; this.b = b_; this.a = a_;
        this.miColor = new Color();
        this.miColor.red(this.r);
        this.miColor.green(this.g);
        this.miColor.blue(this.b);
        this.miColor.alpha(this.a);
    }

    public Color getColor(){
        return miColor;
    }

    public int getARGBColor() {
        return (this.a & 0xff) << 24 | (this.r & 0xff) << 16 | (this.g & 0xff) << 8 | (this.b & 0xff);
    }
}
