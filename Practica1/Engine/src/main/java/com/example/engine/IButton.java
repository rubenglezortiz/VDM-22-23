package com.example.engine;

public interface IButton {
    public int getPosX();
    public int getPosY();
    public int getWidth();
    public int getHeight();
    public String getImageKey();
    public IColor getBackgroundColor();
    public boolean checkCollision(int coordX, int coordY);
}
