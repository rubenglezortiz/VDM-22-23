package com.example.aengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.engine.IImage;

import java.io.IOException;
import java.io.InputStream;

public class AImage implements IImage {
    private Bitmap bitmap;

    public AImage(String name, AssetManager assetMan){
        InputStream is = null;
        try {
            is = assetMan.open(name);
            this.bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(){
        return this.bitmap;
    }

    public void setWidth(int width) { this.bitmap.setWidth(width); }

    public void setHeight(int height) { this.bitmap.setHeight(height); }
}
