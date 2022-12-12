package com.example.aengine;

import android.graphics.Color;
import android.os.Build;

import java.io.Serializable;

public class AColor implements Serializable {
    private int r,g,b,a;

    public AColor(int r_,int g_,int b_){
        this.r = r_; this.g = g_; this.b = b_; this.a = 255;
    }
    public AColor(int r_,int g_,int b_,int a_){
        this.r = r_; this.g = g_; this.b = b_; this.a = a_;
    }

    public Color getColor(){
        Color miColor = new Color();
        miColor.red(this.r);
        miColor.blue(this.b);
        miColor.green(this.g);
        return miColor;
    }

    public int getARGBColor() {
        return (this.a & 0xff) << 24 | (this.r & 0xff) << 16 | (this.g & 0xff) << 8 | (this.b & 0xff);
    }
}
