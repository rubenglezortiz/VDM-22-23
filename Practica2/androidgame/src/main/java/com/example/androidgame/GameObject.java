package com.example.androidgame;

public class GameObject {
    int x, y, w, h;

    public GameObject(int x_, int y_, int w_, int h_){
        this.x = x_;
        this.y = y_;
        this.w = w_;
        this.h = h_;
    }

    public GameObject(int w_, int h_){
        this.x = this.y = 0;
        this.w = w_; this.h = h_;
    }

    public boolean checkCollisions(int x, int y) {
        if (x >= this.x && x <= this.x+this.w &&
                y >= this.y && y <= this.y+this.h) return true;
        return false;
    }
}
