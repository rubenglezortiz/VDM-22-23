package com.example.engine;

public interface IGraphicsEngine {
    //image newImage()
    //ifont new Font()
    abstract void setResolution();
    abstract void setColor();
    abstract void setFont();
    abstract void drawImage();
    abstract void drawRectangle();
    abstract void fillRectangle();
    abstract void drawLine();
    abstract void drawText();
}
