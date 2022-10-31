package com.example.engine;

import java.awt.FontFormatException;
import java.awt.Paint;
import java.awt.image.IndexColorModel;
import java.io.IOException;

public interface IGraphics {
    // Canvas
    public void setResolution(float xScale, float yScale);
    public void translate (int x, int y);
    public void setColor(IColor color);
    public void setFont(IFont font);

    // Create
    public IColor newColor(int r_, int g_, int b_, int a_);
    public IImage newImage(String name);
    public IFont newFont (String name, boolean bold);

    // Draw
    public void clear (IColor color);
    public void drawLine(float x, float y, float x_stop, float y_stop, IColor color);
    public void drawRectangle(float x, float y, float w, float h, IColor  color);
    public void fillRectangle(float x, float y, float w, float h,IColor color);
    public void drawImage(IImage image, int x, int y, int w, int h);
    public void drawText(String text, float x, float y, float textSize, IColor color);
    public void drawText(IFont font, String text, float x, float y, float textSize, IColor color);

    // Getters
    public int getWidth();
    public int getHeight();
    public int getLogicWidth();
    public int getLogicHeight();

    public int realToLogicX(int x);
    public int realToLogicY(int y);


}