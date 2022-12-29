package com.example.desktopengine;

import com.example.engine.IButton;
import com.example.engine.IColor;
import com.example.engine.IImage;


public class DButton implements IButton {
    private String imageKey;
    private int posX, posY, width, height;
    private DColor backgroundColor;

    public DButton(String img, int x, int y, int w, int h, DColor bgColor){
        this.imageKey = img;
        this.posX = x;
        this.posY = y;
        this.width = w;
        this.height = h;
        this.backgroundColor = bgColor;
    }

    @Override
    public int getPosX() { return this.posX; }

    @Override
    public int getPosY() { return this.posY; }

    @Override
    public int getWidth() { return this.width; }

    @Override
    public int getHeight() { return this.height; }

    @Override
    public String getImageKey() { return this.imageKey; }

    @Override
    public IColor getBackgroundColor(){ return this.backgroundColor; }

    @Override
    public boolean checkCollision(int coordX, int coordY) {
        return (coordX >= this.posX &&
                coordX <= this.posX + this.width &&
                coordY >= this.posY &&
                coordY <= this.posY + this.height);
    }
}
