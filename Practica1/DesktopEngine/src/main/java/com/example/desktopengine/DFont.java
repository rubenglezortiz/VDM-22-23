package com.example.desktopengine;

import com.example.engine.IFont;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DFont implements IFont {

    private Font fuente;

    public DFont(String name, float tam, boolean esNeg){
        try {
            FileInputStream fis = new FileInputStream(name);
            this.fuente = Font.createFont(Font.TRUETYPE_FONT, fis);
            if (esNeg){
                this.fuente = this.fuente.deriveFont(Font.BOLD);
            }
            this.fuente = this.fuente.deriveFont(tam);


        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getSize() {
        return this.fuente.getSize();
    }

    @Override
    public boolean isBold() {
        return this.fuente.isBold();
    }

    public Font getFont() { return fuente; }
    @Override
    public void setSize(float size) { this.fuente.deriveFont(size);}
}
