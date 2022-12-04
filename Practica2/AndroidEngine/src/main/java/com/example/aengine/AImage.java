package com.example.aengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

public class AImage  {
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

    public void setWidth(float width) { this.bitmap.setWidth((int)width); }

    public void setHeight(float height) { this.bitmap.setHeight((int)height); }
}
