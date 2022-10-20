package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.engine.IImage;

import java.io.IOException;
import java.io.InputStream;

public class AImage implements IImage {
    Bitmap bitmap;
    //INTENTAR METER VALORES POR DEFECTO
    //InputStream is = assetManager.open(filePath);
    //Bitmap bitmap = BitmapFactory.decodeStream(is);
    //bitmap.setWidth(w); bitmap.setHeight(h); //COMPROBAR Q ESTO FUNCIONE

    public AImage(String name, int width, int height, AssetManager assetMan){
        InputStream is = null;
        try {
            is = assetMan.open(name);
            bitmap = BitmapFactory.decodeStream(is);
            bitmap.setWidth(width); //COMPROBAR Q ESTO FUNCIONE
            bitmap.setHeight(height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return getHeight();
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setWidth(int width){
        bitmap.setWidth(width);
    }

    public void setHeight(int height){
        bitmap.setHeight(height);
    }

}
