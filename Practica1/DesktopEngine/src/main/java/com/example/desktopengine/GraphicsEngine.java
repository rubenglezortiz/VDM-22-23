package com.example.desktopengine;

import com.example.engine.IColor;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GraphicsEngine implements IGraphics {
    // Class variables
    float logicWidth, logicHeight;

    // Java variables
    private JFrame window;
    private BufferStrategy buffer;
    private Graphics2D canvas;

    // Thread (creo q es opcional)
    private Thread renderThread;
    private boolean running;

    public GraphicsEngine(JFrame window_){
        this.window = window_;
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
    public void show(){
        this.canvas = (Graphics2D) this.buffer.getDrawGraphics();
        this.buffer.show();
    }

    public void dispose(){
        this.buffer.dispose();
    }
    // Canvas
    @Override
    public void setResolution(float xScale, float yScale) {
        //NI IDEA
    }

    @Override
    public void translate (int x, int y){
        //NI IDEA
    }

    @Override
    public void setColor(IColor color){
        //canvas.setColor(Color.decode(color));
        canvas.setColor(((DColor)color).getColor());
    }

    @Override
    public void setFont(IFont font) {
        /*InputStream is = new FileInputStream(filePath);
        Font font = Font.createFont(Font.TRUETYPE_FONT, is);
        font = font.deriveFont(font.getStyle(),size);
        canvas.setFont(font);*/
        canvas.setFont(((DFont)font).getFont());
    }

    @Override
    public IColor newColor(int r_, int g_, int b_, int a_) {
        return new DColor(r_, g_, b_, a_);
    }

    // Create
    @Override
    public IImage newImage(String name, int width, int height) {
        return new DImage(name, width, height);
    }

    @Override
    public IFont newFont(String name, int size, boolean bold) {
        return new DFont(name, size, bold);
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
        canvas.drawImage(((DImage)image).getImage(),x,y,null);
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
  //  protected void renderText() throws IOException, FontFormatException {
  //      String filePath = new String("font.TTF");
  //      InputStream is = new FileInputStream(filePath);
  //      Font awtFont = Font.createFont(Font.TRUETYPE_FONT, is);
  //      awtFont = awtFont.deriveFont(awtFont.getStyle(),10);
//
  //      this.graphics2D.setFont(awtFont);
  //      this.graphics2D.drawString("holaa",100,100);
//
  //  }
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
        return 0;
    }

    @Override
    public int getLogicHeight() {
        return 0;
    }

    @Override
    public int realToLogicX(int x) {
        return 0;
    }

    @Override
    public int realToLogicY(int y) {
        return 0;
    }

}
