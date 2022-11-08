package com.example.desktopengine;

import com.example.engine.IFont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DFont implements IFont {
    private Font fuente;
    private String path = "./Assets/Fonts/";

    public DFont(String name, boolean esNeg){
        try {
            FileInputStream fis = new FileInputStream(this.path + name);
            this.fuente = Font.createFont(Font.TRUETYPE_FONT, fis);
            if (esNeg) {
                this.fuente = this.fuente.deriveFont(Font.BOLD);
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public Font getFont() { return this.fuente; }

    @Override
    public float getSize() {
        return this.fuente.getSize();
    }

    @Override
    public boolean isBold() {
        return this.fuente.isBold();
    }

    /*@Override
    public void setSize(float size) { this.fuente.deriveFont(size);}*/
}
