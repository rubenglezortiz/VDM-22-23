package com.example.desktopengine;

import com.example.engine.IColor;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

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

    // Thread
    private Thread renderThread;
    private boolean running;

    public DGraphicsEngine(JFrame window_){
        this.window = window_;
        this.logicWidth = this.window.getWidth();
        this.logicHeight = this.window.getHeight();
        this.window.setSize(600, 400);
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
    }

    // Canvas functions

    @Override
    public void prepareFrame() {
        this.canvas = (Graphics2D) this.buffer.getDrawGraphics();
        this.clear(newColor(255,255,255,255));
        this.setResolution(this.window.getWidth(), this.window.getHeight());
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
    public void setResolution(float xScale, float yScale) {
       /*this.scaleFactorX = xScale/logicWidth;
       this.scaleFactorY = yScale/logicHeight;
       float scaleFactor = Math.min(scaleFactorX, scaleFactorY);
       float x = (this.getWidth()-logicWidth) / (2.0f);
       float y = (this.getHeight()- logicHeight)/ (2.0f);
       canvas.translate(x,y);
        */
       //canvas.scale(scaleFactor, scaleFactor);
       //System.out.println("Canvas Width (i600): " + window.getWidth() + "  Canvas Height (i400): " + window.getHeight());
       //System.out.println("scaleFactorX: " + scaleFactorX + "  scaleFactorY: " + scaleFactorY);
    }

    @Override
    public void translate (int x, int y){
        //NI IDEA
    }

    @Override
    public int realToLogicX(int x) {
        return (int) (x+(this.getWidth()-logicWidth)/ 2) ;
    }

    @Override
    public int realToLogicY(int y) {
        return (int) (y+(this.getHeight()- logicHeight)/2);
    }

    @Override
    public int realToLogicScale(int s) {
        return (int) (s * Math.min(scaleFactorX, scaleFactorY));
    }

    @Override
    public void setColor(IColor color){
        canvas.setColor(((DColor)color).getColor());
    }

    @Override
    public void setFont(IFont font) {
        canvas.setFont(((DFont)font).getFont());
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

    // Draw
    @Override
    public void clear (IColor color){
        setColor(color);
        this.canvas.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public void drawLine(float x, float y, float x_stop, float y_stop, IColor color){
        setColor(color);
        canvas.drawLine((int)x, (int)y, (int)x_stop, (int)y_stop);
    }

    @Override
    public void drawRectangle(float x, float y, float w, float h, IColor color) {
        setColor(color);
        canvas.drawRect((int)x, (int)y, (int)w, (int)h);
    }

    @Override
    public void fillRectangle(float x, float y, float w, float h, IColor  color) {
        setColor(color);
        this.canvas.fillRect((int)x, (int)y, (int)w, (int)h);
    }

    @Override
    public void drawImage(IImage image, int x, int y, int w, int h) {
        canvas.drawImage(((DImage)image).getImage(),x,y, w, h,null);
    }

    @Override
    public void drawText(String text, float x, float y, float textSize, IColor color) {
        setColor(color);
        this.canvas.setFont(this.canvas.getFont().deriveFont(textSize));
        this.canvas.drawString(text,x,y);
    }

    @Override
    public void drawText(IFont font, String text, float x, float y, float textSize, IColor color) {
        font.setSize(textSize);
        setColor(color);
        this.canvas.setFont(((DFont)font).getFont());
        this.canvas.drawString(text,x,y);
    }


    // Getters
    @Override
    public int getWidth() {
        return this.window.getWidth();
    }

    @Override
    public int getHeight() {
        return this.window.getHeight();
    }

    @Override
    public int getLogicWidth() {
        return this.logicWidth;
    }

    @Override
    public int getLogicHeight() { return this.logicHeight; }
}
