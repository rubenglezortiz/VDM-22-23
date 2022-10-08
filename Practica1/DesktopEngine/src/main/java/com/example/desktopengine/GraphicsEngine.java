package com.example.desktopengine;

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

public class GraphicsEngine {
    private JFrame myView;
    private BufferStrategy bufferStrategy;
    private Graphics2D graphics2D;

    private Thread renderThread;
    private boolean running;

    public GraphicsEngine(){

    }

    public void setResolution(){

    }
    public void setColor(String color){
        graphics2D.setColor(Color.decode(color));
    }

    public void setFont(String filePath, int size) throws IOException, FontFormatException {
        InputStream is = new FileInputStream(filePath);
        Font font = Font.createFont(Font.TRUETYPE_FONT, is);
        font = font.deriveFont(font.getStyle(),size);
        graphics2D.setFont(font);
    }

    public void drawImage(String filePath, int x, int y, int w, int h) throws IOException{
        Image img = ImageIO.read(new File(filePath));
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(img,x, y, w, h,null);
    }

    public void drawRectangle(int x, int y, int w, int h, String color){
        graphics2D.setColor(Color.decode(color));
        graphics2D.drawRect(x, y, w, h);
    }

    public void fillRectangle(int x, int y, int w, int h, String color){
        graphics2D.setColor(Color.decode(color));
        graphics2D.fillRect(x, y, w, h);
    }

    public void drawLine(int x, int y, int x_stop, int y_stop, String color){
        graphics2D.setColor(Color.decode(color));
        graphics2D.drawLine(x, y, x_stop, y_stop);
    }

    public void drawText(String text, float x, float y){
        graphics2D.drawString(text, x, y);
    }

}
