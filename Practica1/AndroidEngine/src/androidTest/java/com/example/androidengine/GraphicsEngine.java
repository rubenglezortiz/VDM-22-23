package com.example.androidengine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.engine.IGraphicsEngine;

import java.io.IOException;
import java.io.InputStream;

public class GraphicsEngine implements IGraphicsEngine {
    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;

    private AssetManager assetManager;
    //private Thread renderThread;
    //private boolean running;

    public GraphicsEngine(SurfaceView myView){
        //this.myView = myView;
        this.holder = this.myView.getHolder();
        this.paint = new Paint();
        this.paint.setColor(0xFFFFFFFF);
        //this.assetManager = getAssets(); //DA ERROR
    }

    @Override
    public void setResolution(int w, int h){
        //NI IDEA
    };

    @Override
    public void setColor(String color){
        //Deberíamos comprobar que se pase bien el parámetro "color"???
        this.paint.setColor(Integer.parseInt(color));
    };

    @Override
    public void setFont(String filePath, int size) throws IOException {
        //NI IDEA
    }

    @Override
    public void drawImage(String filePath, int x, int y, int w, int h) throws IOException {
        //INTENTAR METER VALORES POR DEFECTO
        InputStream is = assetManager.open(filePath);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        bitmap.setWidth(w); bitmap.setHeight(h); //COMPROBAR Q ESTO FUNCIONE
        canvas.drawBitmap(bitmap, x,y, this.paint);
    }

    @Override
    public void drawRectangle(float x, float y, float w, float h, String  color) {
        paint.setColor(Integer.parseInt(color));
        canvas.drawRect(x, y, w, h, paint);
    }

    public void drawImage(String filePath, int x, int y) throws IOException {
        drawImage(filePath, x, y,100,100);
    }

    @Override
    public void fillRectangle(String color){
        //NI IDEA
    }

    @Override
    public void drawLine(float x, float y, float x_stop, float y_stop, String color) {
        paint.setColor(Integer.parseInt(color));
        canvas.drawLine(x,y,x_stop, y_stop, paint);
    }

    @Override
    public void drawText(String text, float x, float y, float textSize) {
        paint.setColor(Integer.parseInt(text));
        paint.setTextSize(textSize);
        canvas.drawText(text, x, y, paint);
    }
}
