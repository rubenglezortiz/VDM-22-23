package com.example.engine;

public interface IButton {
    public int getPosX();
    public int getPosY();
    public int getWidth();
    public int getHeight();
    public String getText();
    public int getTextX();
    public int getTextY();
    public int getTextSize();
    public IFont getFont();
    public IColor getMainColor();
    public IColor getBackgroundColor();
    public boolean checkCollision(int coordX, int coordY);
}
