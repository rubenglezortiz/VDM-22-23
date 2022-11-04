package com.example.aengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.engine.IImage;

import java.io.IOException;
import java.io.InputStream;

public class AImage implements IImage {
    private Bitmap bitmap;
    //INTENTAR METER VALORES POR DEFECTO
    //InputStream is = assetManager.open(filePath);
    //Bitmap bitmap = BitmapFactory.decodeStream(is);
    //bitmap.setWidth(w); bitmap.setHeight(h); //COMPROBAR Q ESTO FUNCIONE

    public AImage(String name, AssetManager assetMan){
        InputStream is = null;
        try {
            is = assetMan.open(name);
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmap(){
        return bitmap;
    }
}
