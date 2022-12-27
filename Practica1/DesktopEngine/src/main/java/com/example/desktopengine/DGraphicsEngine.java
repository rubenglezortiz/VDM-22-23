package com.example.desktopengine;

import com.example.engine.IButton;
import com.example.engine.IColor;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class DGraphicsEngine implements IGraphics {
    // Java variables
    private JFrame window;
    private BufferStrategy buffer;
    private Graphics2D canvas;

    // Class variables
    private int logicWidth, logicHeight;
    private float scaleFactorX, scaleFactorY;
    private boolean scaleInX;
    private int offsetX, offsetY;
    private Font defaultFont;

    // Thread
    private Thread renderThread;
    private boolean running;

    public DGraphicsEngine(JFrame window_){
        this.window = window_;
        this.logicWidth = 400;
        this.logicHeight = 600;
        this.window.setSize(this.logicWidth, this.logicHeight);

        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int intentos = 1000;
        while(intentos > 0) {
            try {
                this.window.createBufferStrategy(2);
                break;
            } catch (Exception e){
                e.printStackTrace();
            }
            intentos--;
        }
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }

        this.buffer = this.window.getBufferStrategy();
        this.canvas = (Graphics2D) this.buffer.getDrawGraphics();
        this.window.setIgnoreRepaint(true);
        this.window.setVisible(true);
        this.defaultFont = this.canvas.getFont();
    }

    // Canvas functions

    @Override
    public void prepareFrame() {
        this.canvas = (Graphics2D) this.buffer.getDrawGraphics();
        this.setResolution(this.window.getWidth(), this.window.getHeight());
        this.clear(newColor(255,255,255,255));
    }

    @Override
    public void finishFrame() {
        this.canvas.dispose();
    }

    @Override
    public boolean changeBuffer() {
        if (this.buffer.contentsRestored()) {
            return false;
        }
        this.buffer.show();
        return !this.buffer.contentsLost();
    }

    @Override
    public void setResolution(float newX, float newY) {
       this.scaleFactorX = newX / (float) this.logicWidth;
       this.scaleFactorY = newY / (float) this.logicHeight;

       this.scaleInX = getWidth() * 3 > getHeight() * 2;

       float scaleFactor = getScaleFactor();

        if (this.scaleInX){
            this.offsetX = (int)(((float)getWidth() / 2.0f) - (((float) this.logicWidth / 2.0f) * scaleFactor));
            this.offsetY = 0;
        }
        else{
            this.offsetX = 0;
            this.offsetY = (int)(((float)getHeight() / 2.0f) - (((float) this.logicHeight / 2.0f)  * scaleFactor));
        }
    }

    @Override
    public void translate (int x, int y){ this.canvas.translate(x,y);}

    @Override
    public int realToLogicX(int x) { return (int) ((float)(x - this.offsetX) / getScaleFactor()); }

    @Override
    public int realToLogicY(int y) { return (int) ((float)(y - this.offsetY) / getScaleFactor()); }

    @Override
    public int realToLogicScale(int s) { return (int) ((float) s / getScaleFactor()); }

    @Override
    public int logicToRealX(int x) { return ((int) ((float)x * getScaleFactor()) + this.offsetX) ; }

    @Override
    public int logicToRealY(int y) { return ((int) ((float)y * getScaleFactor()) + this.offsetY) ; }

    @Override
    public int logicToRealScale(int s) { return (int) ((float)s * getScaleFactor()); }


    @Override
    public void setColor(IColor color){
        this.canvas.setColor(((DColor)color).getColor());
    }

    @Override
    public void setFont(IFont font) {
        this.canvas.setFont(((DFont)font).getFont());
    }

    // Create
    @Override
    public IColor newColor(int r_, int g_, int b_, int a_) {
        return new DColor(r_, g_, b_, a_);
    }

    @Override
    public IImage newImage(String name) {
        return new DImage(name);
    }

    @Override
    public IFont newFont(String name, boolean bold) {
        return new DFont(name,bold);
    }

    @Override
    public IButton newButton(String text, int x, int y, int w, int h, int tX, int tY, int tSize, IFont f, IColor mColor, IColor bgColor) {
        return new DButton(text, x, y, w, h, tX, tY, tSize, (DFont)f, (DColor)mColor, (DColor)bgColor);
    }

    // Draw
    @Override
    public void clear (IColor color){
        setColor(color);
        this.canvas.fillRect(0,0,getWidth(), getHeight());
    }

    @Override
    public void drawLine(float x, float y, float x_stop, float y_stop, IColor color){
        setColor(color);
        this.canvas.drawLine(logicToRealX((int)x), logicToRealY((int)y), logicToRealX((int)x_stop), logicToRealY((int)y_stop));
    }

    @Override
    public void drawRectangle(float x, float y, float w, float h, IColor color) {
        setColor(color);
        this.canvas.drawRect(logicToRealX((int)x), logicToRealY((int)y),
                logicToRealScale((int)w), logicToRealScale((int)h));
    }

    @Override
    public void fillRectangle(float x, float y, float w, float h, IColor  color) {
        setColor(color);
        this.canvas.fillRect(logicToRealX((int)x), logicToRealY((int)y),
                logicToRealScale((int)w), logicToRealScale((int)h));
    }

    @Override
    public void drawImage(IImage image, int x, int y, int w, int h) {
        this.canvas.drawImage(((DImage)image).getImage(), logicToRealX(x), logicToRealY(y),
                logicToRealScale(w),logicToRealScale(h),null);
    }

    @Override
    public void drawText(String text, float x, float y, float textSize, IColor color) {
        setColor(color);
        this.canvas.setFont(this.canvas.getFont().deriveFont(logicToRealScale((int)textSize)));
        this.canvas.drawString(text,logicToRealX((int)x),logicToRealY((int)y));
        this.canvas.setFont(this.defaultFont);
    }

    @Override
    public void drawText(IFont font, String text, float x, float y, float textSize, IColor color) {
        setColor(color);
        this.canvas.setFont(((DFont)font).getFont().deriveFont(logicToRealScale((int)textSize)));
        this.canvas.drawString(text,logicToRealX((int)x),logicToRealY((int)y));
    }

    @Override
    public void drawButton(IButton button) {
        float butX = (float) button.getPosX();
        float butY = (float) button.getPosY();
        float butW = (float) button.getWidth();
        float butH = (float) button.getHeight();

        this.fillRectangle(butX,butY,butW,butH, button.getBackgroundColor());
        this.drawRectangle(butX,butY,butW,butH, button.getMainColor());

        if(button.getFont()!=null)
            this.drawText(button.getFont(), button.getText(), butX + button.getTextX(), butY + button.getTextY(), button.getTextSize(), button.getMainColor());
        else this.drawText(button.getText(), butX + button.getTextX(), butY + button.getTextY(), button.getTextSize(), button.getMainColor());

    }

    // Getters
    @Override
    public int getWidth() {
        return this.window.getWidth();
    }

    @Override
    public int getHeight() { return this.window.getHeight(); }

    @Override
    public int getLogicWidth() {
        return this.logicWidth;
    }

    @Override
    public int getLogicHeight() { return this.logicHeight; }

    @Override
    public float getScaleFactor() { return Math.min(this.scaleFactorX, this.scaleFactorY); }
}
