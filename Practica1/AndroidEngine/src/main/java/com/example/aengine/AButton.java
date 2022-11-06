package com.example.aengine;

import android.widget.Button;

import com.example.engine.IButton;
import com.example.engine.IColor;
import com.example.engine.IFont;

public class AButton implements IButton {
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

    @Override
    public int getPosX() {
        return this.posX;
    }

    @Override
    public int getPosY() {
        return this.posY;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() { return this.height; }

    @Override
    public String getText() { return this.text; }

    @Override
    public int getTextX() { return this.textX; }

    @Override
    public int getTextY() { return this.textY; }

    @Override
    public int getTextSize() { return this.textSize; }

    @Override
    public IFont getFont() { return this.font; }

    @Override
    public IColor getMainColor() { return this.mainColor; }

    @Override
    public IColor getBackgroundColor() { return this.backgroundColor; }

    @Override
    public boolean checkCollision(int coordX, int coordY) {
        return (coordX >= this.posX &&
                coordX <= this.posX + this.width &&
                coordY >= this.posY &&
                coordY <= this.posY + this.height);
    }

}
