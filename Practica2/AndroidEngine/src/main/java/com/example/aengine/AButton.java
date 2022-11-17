package com.example.aengine;

import android.widget.Button;


public class AButton {
    private String text;
    private int posX, posY, width, height, textX, textY, textSize;
    private AFont font;
    private AColor mainColor, backgroundColor;

    public AButton(String buttonText, int x, int y, int w, int h, int tX, int tY, int tSize, AFont f, AColor mColor, AColor bgColor) {
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

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() { return this.height; }

    public String getText() { return this.text; }

    public int getTextX() { return this.textX; }

    public int getTextY() { return this.textY; }

    public int getTextSize() { return this.textSize; }

    public AFont getFont() { return this.font; }

    public AColor getMainColor() { return this.mainColor; }

    public AColor getBackgroundColor() { return this.backgroundColor; }

    public boolean checkCollision(int coordX, int coordY) {
        return (coordX >= this.posX &&
                coordX <= this.posX + this.width &&
                coordY >= this.posY &&
                coordY <= this.posY + this.height);
    }
}
