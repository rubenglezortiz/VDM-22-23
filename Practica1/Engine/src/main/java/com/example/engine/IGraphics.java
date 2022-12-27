package com.example.engine;

import java.awt.FontFormatException;
import java.awt.Paint;
import java.awt.image.IndexColorModel;
import java.io.IOException;

public interface IGraphics {
    //Canvas functions
    public void prepareFrame();
    public void finishFrame();
    public boolean changeBuffer();
    public void setResolution(float newX, float newY);
    public void translate (int x, int y);
    public int realToLogicX(int x);
    public int realToLogicY(int y);
    public int realToLogicScale(int s);
    public int logicToRealX(int x);
    public int logicToRealY(int y);
    public int logicToRealScale(int s);
    public void setColor(IColor color);
    public void setFont(IFont font);

    //Create
    public IColor newColor(int r_, int g_, int b_, int a_);
    public void newImage(String name);
    public void newFont (String name, boolean bold);
    public IButton newButton(String text, int x, int y, int w, int h, int tX, int tY, int tSize, IFont f, IColor mColor, IColor bgColor);

    // Draw
    public void clear (IColor color);
    public void drawLine(float x, float y, float x_stop, float y_stop, IColor color);
    public void drawRectangle(float x, float y, float w, float h, IColor  color);
    public void fillRectangle(float x, float y, float w, float h,IColor color);
    public void drawImage(String image, float x, float y, float w, float h);
    public void drawText(String text, float x, float y, float textSize, IColor color);
    public void drawText(String font, String text, float x, float y, float textSize, IColor color);
    public void drawButton(IButton button);

    // Getters
    public int getWidth();
    public int getHeight();
    public int getLogicWidth();
    public int getLogicHeight();
    public float getScaleFactor();
}