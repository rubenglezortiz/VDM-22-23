package com.example.aengine;
import java.io.Serializable;


public class AButton implements Serializable {
    private final String name;
    private float posX, posY;
    private final float width, height;
    private final AColor backgroundColor;

    public AButton(String buttonName, float x, float y, float w, float h, AColor bgColor) {
        this.name = buttonName;
        this.posX = x;
        this.posY = y;
        this.width = w;
        this.height = h;
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

    public AColor getBackgroundColor() { return this.backgroundColor; }

    public boolean checkCollision(float coordX, float coordY) {
        return (coordX >= this.posX &&
                coordX <= this.posX + this.width &&
                coordY >= this.posY &&
                coordY <= this.posY + this.height);
    }

    public void  changePosition(float x, float  y)
    {
        this.posX = x;
        this.posY = y;
    }
}
