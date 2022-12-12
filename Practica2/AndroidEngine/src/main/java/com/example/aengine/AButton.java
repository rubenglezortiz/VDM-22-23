package com.example.aengine;

import android.widget.Button;

import java.io.Serializable;


public class AButton {
    private String text;
    private float posX, posY, width, height, textX, textY, textSize;
    private AFont font;
    private AColor mainColor, backgroundColor;

    public AButton(String buttonText, float x, float y, float w, float h, float tX, float tY, int tSize, AFont f, AColor mColor, AColor bgColor) {
        this.text = buttonText;
        this.posX = x;
        this.posY = y;
        this.width = w;
        this.height = h;
        this.textX = tX;
        this.textY = tY;
        this.textSize = tSize;
        this.font = f;
        this.mainColor = mColor;
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

    public String getText() { return this.text; }

    public float getTextX() { return this.textX; }

    public float getTextY() { return this.textY; }

    public float getTextSize() { return this.textSize; }

    public AFont getFont() { return this.font; }

    public AColor getMainColor() { return this.mainColor; }

    public AColor getBackgroundColor() { return this.backgroundColor; }

    public boolean checkCollision(float coordX, float coordY) {
        return (coordX >= this.posX &&
                coordX <= this.posX + this.width &&
                coordY >= this.posY &&
                coordY <= this.posY + this.height);
    }
}
