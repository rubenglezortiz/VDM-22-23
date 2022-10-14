package com.example.desktopengine;

import com.example.engine.IGraphicsEngine;

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

public class GraphicsEngine implements IGraphicsEngine {
    private JFrame myView;
    private BufferStrategy bufferStrategy;
    private Graphics2D graphics2D;

    private Thread renderThread;
    private boolean running;

    public GraphicsEngine(){

    }

    @Override
    public void setResolution(int w, int h) {
        //NI IDEA
    }

    @Override
    public void setColor(String color){
        graphics2D.setColor(Color.decode(color));
    }

    @Override
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

    @Override
    public void drawRectangle(float x, float y, float w, float h, String color) {
        graphics2D.setColor(Color.decode(color));
        graphics2D.drawRect((int)x, (int)y, (int)w, (int)h);
    }

    @Override
    public void fillRectangle(String color) {
        graphics2D.setColor(Color.decode(color));
        //NECESITAMOS SABER QUÉ HACE FILL RECTANGLE EXACTAMENTE
        //graphics2D.fillRect(x, y, w, h);
    }

    @Override
    public void drawLine(float x, float y, float x_stop, float y_stop, String color) {
        graphics2D.setColor(Color.decode(color));
        graphics2D.drawLine((int)x, (int)y, (int)x_stop, (int)y_stop);
    }

    @Override
    public void drawText(String text, float x, float y, float textSize) {
        graphics2D.drawString(text, x, y);
    }

}
