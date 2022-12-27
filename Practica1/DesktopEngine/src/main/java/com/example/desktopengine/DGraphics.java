package com.example.desktopengine;

import com.example.engine.IButton;
import com.example.engine.IColor;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.util.HashMap;

import javax.swing.JFrame;

public class DGraphics implements IGraphics {
    // Java variables
    private JFrame window;
    private BufferStrategy buffer;
    private Graphics2D canvas;
    // Class variables
    private float logicWidth, logicHeight;
    private float scaleFactorX, scaleFactorY;
    private boolean scaleInX;
    private float offsetX, offsetY;
    private Font defaultFont;
    // Thread
    private Thread renderThread;
    private boolean running;
    // Maps
    private final HashMap<String, DFont> fonts;
    private final HashMap<String, DImage> images;

    public DGraphics(JFrame window_){
        this.window = window_;
        this.logicWidth = 400;
        this.logicHeight = 600;
        this.window.setSize((int)this.logicWidth, (int)this.logicHeight);

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
    public DGraphicsEngine(JFrame window_,int logicWidth_,int logicHeight_){
        this.window = window_;
        this.logicWidth = logicWidth_;
        this.logicHeight = logicHeight_;
        this.window.setSize(this.logicWidth, this.logicHeight);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.fonts = new HashMap<>();
        this.images = new HashMap<>();

        int tries = 1000;
        while(tries > 0) {
            try {
                this.window.createBufferStrategy(2);
                break;
            } catch (Exception e){
                e.printStackTrace();
            }
            tries--;
        }
        if (tries == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }

        this.buffer = this.window.getBufferStrategy();
        this.canvas = (Graphics2D) this.buffer.getDrawGraphics();
        this.window.setIgnoreRepaint(true);
        this.window.setVisible(true);
        this.defaultFont = this.canvas.getFont();
    }



    //________________________________CREATE________________________________
    @Override
    public IColor newColor(int r_, int g_, int b_, int a_) {
        return new DColor(r_, g_, b_, a_);
    }

    @Override
    public void newImage(String key) {
        if(this.images.containsKey(key))return;
        DImage image = new DImage(key);
        this.images.put(key, image);
    }

    @Override
    public void newFont(String key, boolean bold) {
        if(this.fonts.containsKey(key)) return;
        DFont font = new DFont(key,bold);
        this.fonts.put(key, font);
    }

    @Override
    public IButton newButton(String text, int x, int y, int w, int h, int tX, int tY, int tSize, IFont f, IColor mColor, IColor bgColor) {
        return new DButton(text, x, y, w, h, tX, tY, tSize, (DFont)f, (DColor)mColor, (DColor)bgColor);
    }



    //________________________________DRAW________________________________
    @Override
    public void clear (IColor color){
        setColor(color);
        this.canvas.fillRect(0,0,getWidth(), getHeight());
    }

    @Override
    public void drawLine(float x, float y, float x_stop, float y_stop, IColor color){
        setColor(color);
        this.canvas.drawLine(realToLogicX((int)x), realToLogicY((int)y), realToLogicX((int)x_stop), realToLogicY((int)y_stop));
    }

    @Override
    public void drawRectangle(float x, float y, float w, float h, IColor color) {
        setColor(color);
        this.canvas.drawRect(realToLogicX((int)x), realToLogicY((int)y),
                realToLogicScale((int)w), realToLogicScale((int)h));
    }

    @Override
    public void fillRectangle(float x, float y, float w, float h, IColor  color) {
        setColor(color);
        this.canvas.fillRect(realToLogicX((int)x), realToLogicY((int)y),
                realToLogicScale((int)w), realToLogicScale((int)h));
    }

    @Override
    public void drawImage(String key, float x, float y, float w, float h) {
        if(this.images.containsKey(key)) {
            this.canvas.drawImage(this.images.get(key).getImage(), realToLogicX((int) x), realToLogicY((int) y),
                    realToLogicScale((int) w), realToLogicScale((int) h), null);
        }
    }

    @Override
    public void drawText(String text, float x, float y, float textSize, IColor color) {
        setColor(color);
        this.canvas.setFont(this.canvas.getFont().deriveFont(textSize * getScaleFactor()));
        this.canvas.drawString(text,realToLogicX((int)x),realToLogicY((int)y));
        this.canvas.setFont(this.defaultFont);
    }

    @Override
    public void drawText(String key, String text, float x, float y, float textSize, IColor color) {
        setColor(color);
        if(this.fonts.containsKey(key)) {
            this.canvas.setFont(this.fonts.get(key).getFont().deriveFont(textSize * getScaleFactor()));
            this.canvas.drawString(text, realToLogicX((int) x), realToLogicY((int) y));
        }
    }

    @Override
    public void drawButton(IButton button) {
        float butX = (float) button.getPosX();
        float butY = (float) button.getPosY();
        float butW = (float) button.getWidth();
        float butH = (float) button.getHeight();

        this.fillRectangle(butX,butY,butW,butH, button.getBackgroundColor());
        this.drawRectangle(butX,butY,butW,butH, button.getMainColor());
    }

    //________________________________GETTERS________________________________
    @Override
    public int getWidth() { return this.window.getWidth(); }

    @Override
    public int getHeight() { return this.window.getHeight(); }

    @Override
    public int getLogicWidth() { return this.logicWidth; }

    @Override
    public int getLogicHeight() { return this.logicHeight; }

    @Override
    public float getScaleFactor() { return Math.min(this.scaleFactorX, this.scaleFactorY); }



    //________________________________GRAPHICS FUNCTIONS________________________________
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
    public float realToLogicX(float x) { return (x - this.offsetX) / getScaleFactor(); }

    @Override
    public float realToLogicY(float y) { return (y - this.offsetY) / getScaleFactor(); }

    @Override
    public float realToLogicScale(float s) { return s / getScaleFactor(); }

    @Override
    public float logicToRealX(float x) { return (x * getScaleFactor()) + this.offsetX ; }

    @Override
    public float logicToRealY(float y) { return (y * getScaleFactor()) + this.offsetY ; }

    @Override
    public float logicToRealScale(float s) { return s * getScaleFactor(); }


    @Override
    public void setColor(IColor color){
        this.canvas.setColor(((DColor)color).getColor());
    }

    @Override
    public void setFont(IFont font) {
        this.canvas.setFont(((DFont)font).getFont());
    }
}