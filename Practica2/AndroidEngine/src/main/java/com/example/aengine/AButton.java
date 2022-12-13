package com.example.aengine;

import android.widget.Button;

import java.io.Serializable;


public class AButton implements Serializable {
    private String name;
    private float posX, posY, width, height, textX, textY, textSize;
    private AColor backgroundColor;

    public AButton(String buttonName, float x, float y, float w, float h, float tX, float tY, int tSize, AColor bgColor) {
        this.name = buttonName;
        this.posX = x;
        this.posY = y;
        this.width = w;
        this.height = h;
        this.textX = tX;
        this.textY = tY;
        this.textSize = tSize;
        this.backgroundColor = bgColor;
    }

    public float getPosX() {
        return this.posX;
    }

    public float getPosY() {
        return this.posY;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() { return this.height; }

    public String getName() { return this.name; }

    public float getTextX() { return this.textX; }

    public float getTextY() { return this.textY; }

    public float getTextSize() { return this.textSize; }

    public AColor getBackgroundColor() { return this.backgroundColor; }

    public boolean checkCollision(float coordX, float coordY) {
        return (coordX >= this.posX &&
                coordX <= this.posX + this.width &&
                coordY >= this.posY &&
                coordY <= this.posY + this.height);
    }
}
