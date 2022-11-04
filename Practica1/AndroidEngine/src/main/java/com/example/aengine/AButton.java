package com.example.aengine;

import android.widget.Button;

import com.example.engine.IButton;

public class AButton implements IButton {
    private String text;
    private int posX, posY, width, height;

    public AButton(String buttonText, int x, int y, int w, int h) {
        text = buttonText;
        posX = x;
        posY = y;
        width = w;
        height = h;
    }

    @Override
    public int getX() {
        return posX;
    }

    @Override
    public int getY() {
        return posY;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void render() {

    }

    @Override
    public boolean checkCollision(int coordX, int coordY) {
        return (coordX >= this.posX &&
                coordX <= this.posX + this.width &&
                coordY >= this.posY &&
                coordY <= this.posY + this.height);
    }

}
