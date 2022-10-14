package com.example.engine;

import java.awt.FontFormatException;
import java.awt.Paint;
import java.io.IOException;

public interface IGraphicsEngine {
    //image newImage()
    //ifont new Font()
    public abstract void setResolution(int w, int h);
    public abstract void setColor(String color);
    public abstract void setFont(String filePath, int size) throws IOException, FontFormatException, FontFormatException, FontFormatException;
    public abstract void drawImage(String filePath, int x, int y, int w, int h) throws IOException;
    public abstract void drawRectangle(float x, float y, float w, float h, String  color);
    public abstract void fillRectangle(String color);
    public abstract void drawLine(float x, float y, float x_stop, float y_stop, String color);
    public abstract void drawText(String text, float x, float y, float textSize);
}
