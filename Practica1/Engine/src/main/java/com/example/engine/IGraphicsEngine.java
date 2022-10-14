package com.example.engine;

import java.io.IOException;

public interface IGraphicsEngine {
    //image newImage()
    //ifont new Font()
    public abstract void setResolution(int w, int h);
    public abstract void setColor();
    public abstract void setFont();
    public abstract void drawImage(String filePath, int x, int y) throws IOException;
    public abstract void drawRectangle();
    public abstract void fillRectangle();
    public abstract void drawLine();
    public abstract void drawText();
}
