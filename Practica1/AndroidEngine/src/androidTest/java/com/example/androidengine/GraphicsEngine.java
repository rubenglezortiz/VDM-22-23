package com.example.androidengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.engine.IColor;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

import java.io.IOException;
import java.io.InputStream;

public class GraphicsEngine implements IGraphics {
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
    public void setResolution(float xScale, float yScale){
        //NI IDEA
    }

    @Override
    public void translate(int x, int y) {
        //NI IDEA
    }

    ;

    @Override
    public void setColor(IColor color){
        //Deberíamos comprobar que se pase bien el parámetro "color"???
        this.paint.setColor(((AColor)color).getARGBColor());
    };

    @Override
    public void setFont(IFont font){
        //NI IDEA
    }

    @Override
    public IColor newColor(int r_, int g_, int b_, int a_) {
        return new AColor(r_, g_, b_, a_);
    }

    @Override
    public IImage newImage(String name, int width, int height) {
        return new AImage(name, width, height);
    }

    @Override
    public IFont newFont(String name, int size, boolean bold) {
        return new AFont(name, size, bold);
    }

    @Override
    public void clear(IColor color) {
        drawRectangle(0,0, getWidth(), getHeight(), color);
    }

    @Override
    public void drawImage(IImage image, int x, int y, int w, int h) {
        drawImage(image, x, y, w, h, 0);
    }

    public void drawImage(IImage image, int x, int y, int w, int h, int rotation) {
        //INTENTAR METER VALORES POR DEFECTO
        ((AImage)image).setWidth(w);
        ((AImage)image).setHeight(h);
        canvas.drawBitmap(((AImage)image).getBitmap(), x,y, this.paint);
    }


    @Override
    public void drawRectangle(float x, float y, float w, float h, IColor color) {
        setColor(color);
        canvas.drawRect(x, y, w, h, paint);
    }


    @Override
    public void fillRectangle(IColor color){
        setColor(color);
        //NI IDEA
    }

    @Override
    public void drawLine(float x, float y, float x_stop, float y_stop, IColor color) {
        setColor(color);
        canvas.drawLine(x,y,x_stop, y_stop, paint);
    }

    @Override
    public void drawText(IFont font, String text, float x, float y, float textSize, IColor color) {
        setColor(color);
        paint.setTextSize(textSize);
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public int getWidth() { return this.canvas.getWidth(); } //NO ESTOY SEGURO

    @Override
    public int getHeight() {
        return this.canvas.getHeight();
    }  //NO ESTOY SEGURO

    @Override
    public int getLogicWidth() {
        return 0;
    }

    @Override
    public int getLogicHeight() {
        return 0;
    }

    @Override
    public int realToLogicX(int x) {
        return 0;
    }

    @Override
    public int realToLogicY(int y) {
        return 0;
    }


}
