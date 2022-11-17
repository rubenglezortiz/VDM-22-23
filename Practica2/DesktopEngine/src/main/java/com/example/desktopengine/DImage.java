package com.example.desktopengine;

import com.example.engine.IImage;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DImage implements IImage {
    private Image image;

    public DImage(String name){
        try {
            this.image = ImageIO.read(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image getImage(){ return this.image; }
}
