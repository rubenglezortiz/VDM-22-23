package com.example.desktopengine;


import com.example.engine.IImage;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DImage implements IImage {

    private Image imagen;

    public DImage(String name){
        try {
            this.imagen = ImageIO.read(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getAncho() {
        return 0;
    }

    @Override
    public int getAlto() {
        return 0;
    }

    public Image getImagen(){
        return this.imagen;
    }

}
