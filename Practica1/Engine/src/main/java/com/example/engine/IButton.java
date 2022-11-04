package com.example.engine;

public interface IButton {
    public int getX();
    public int getY();
    public int getWidth();
    public int getHeight();
    public void render();
    public boolean checkCollision(int coordX, int coordY);
}
