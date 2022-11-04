package com.example.desktopengine;

import com.example.engine.IButton;


public class DButton implements IButton {
    private String buttonText;
    private int posX, posY, width, height;

    public DButton(String text, int x, int y, int w, int h){
        this.buttonText = text;
        this.posX = x;
        this.posY = y;
        this.width = w;
        this.height = h;
    }

    @Override
    public int getX() { return this.posX; }

    @Override
    public int getY() { return this.posY; }

    @Override
    public int getWidth() { return this.width; }

    @Override
    public int getHeight() { return this.height; }

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
