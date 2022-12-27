package com.example.aengine;

import com.example.engine.IColor;


public class AColor implements IColor {
    private final int r,g,b,a;

    public AColor(int r_,int g_,int b_){
        this.r = r_; this.g = g_; this.b = b_; this.a = 255;
    }
    public AColor(int r_,int g_,int b_,int a_){
        this.r = r_; this.g = g_; this.b = b_; this.a = a_;
    }

    public int getColor() {
        return (this.a & 0xff) << 24 | (this.r & 0xff) << 16 | (this.g & 0xff) << 8 | (this.b & 0xff);
    }
}
