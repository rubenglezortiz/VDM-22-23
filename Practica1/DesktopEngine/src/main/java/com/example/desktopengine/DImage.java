package com.example.desktopengine;


import com.example.engine.IImage;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DImage implements IImage {

    private Image image;
    private int width, height;

    public DImage(String name, int width_, int height_){
        try {
            this.image = ImageIO.read(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = width_;
        height = height_;
    }

    @Override
    public int getWidth() { return width; }

    @Override
    public int getHeight() { return height; }

    public Image getImage(){ return this.image; }
}
