package com.example.aengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.engine.IButton;
import com.example.engine.IColor;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;

public class AGraphics implements IGraphics {
    // Android variables
    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;
    private AssetManager assetManager;

    // Class Variables
    private int logicWidth, logicHeight;
    private float scaleFactorX, scaleFactorY;

    // Thread
    private Thread renderThread;
    private boolean running;

    public AGraphics(SurfaceView myView_){
        this.myView = myView_;
        this.holder = this.myView.getHolder();
        this.paint = new Paint();
        this.paint.setColor(0xFFFFFFFF);
        //this.assetManager = getAssets(); //DA ERROR
    }

    // Android functions
    @Override
    public void prepareFrame() {
        this.canvas = this.holder.lockCanvas();
        this.clear(newColor(255,255,255,255));
        //set resolution, translate etc
    }

    @Override
    public void finishFrame() {
        this.holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean changeBuffer() {
        return (this.holder.getSurface().isValid());
    }

    @Override
    public void setResolution(float newX, float newY){
        //NI IDEA
    }

    @Override
    public void translate(int x, int y) {
        //NI IDEA
    }

    @Override
    public int realToLogicX(int x) {return 0;}

    @Override
    public int realToLogicY(int y) {return 0;}

    @Override
    public int realToLogicScale(int s) {return 0;}

    @Override
    public void setColor(IColor color){
        this.paint.setColor(((AColor)color).getARGBColor());
    }

    @Override
    public void setFont(IFont font){
        this.paint.setTypeface(((AFont)font).getTypeface());
        this.paint.setTextSize(((AFont)font).getSize());
    }

    // Create

    @Override
    public IColor newColor(int r_, int g_, int b_, int a_) {
        return new AColor(r_, g_, b_, a_);
    }

    @Override
    public IImage newImage(String name) {
        return new AImage(name, this.assetManager);
    }

    @Override
    public IFont newFont(String name, boolean bold) {
        return new AFont(name, bold, this.assetManager);
    }

    @Override
    public IButton newButton(String text, int x, int y, int w, int h) { return new AButton(text, x, y, w, h); }


    @Override
    public void clear(IColor color) {
        setColor(color);
        fillRectangle(0,0, getWidth(), getHeight(), color);
    }

    @Override
    public void drawLine(float x, float y, float x_stop, float y_stop, IColor color) {
        setColor(color);
        canvas.drawLine(x,y,x_stop, y_stop, paint);
    }

    @Override
    public void drawRectangle(float x, float y, float w, float h, IColor color) {
        setColor(color);
        canvas.drawLine(x,y,x+w,y,this.paint);
        canvas.drawLine(x,y,x,y + h,this.paint);
        canvas.drawLine(x,y+h,x+w,y+h,this.paint);
        canvas.drawLine(x+w,y,x+w,y+h,this.paint);
    }

    @Override
    public void fillRectangle(float x, float y, float w, float h,IColor color){
        setColor(color);
        canvas.drawRect(x, y, w, h, paint);
    }

    @Override
    public void drawImage(IImage image, int x, int y, int w, int h) {
        //INTENTAR METER VALORES POR DEFECTO
        //((AImage)image).setWidth(w);
        //((AImage)image).setHeight(h);
        canvas.drawBitmap(((AImage)image).getBitmap(), x,y, this.paint);
    }


    @Override
    public void drawText(String text, float x, float y, float textSize, IColor color) {
        float prevTextSize = this.paint.getTextSize();
        this.paint.setTextSize(textSize);
        this.paint.setColor(((AColor)color).getARGBColor());
        canvas.drawText(text,x,y, this.paint);
        this.paint.setTextSize(prevTextSize);
    }

    @Override
    public void drawText(IFont font, String text, float x, float y, float textSize, IColor color) {
        setColor(color);
        this.paint.setTypeface(((AFont)font).getTypeface());
        this.paint.setTextSize(textSize);
        this.canvas.drawText(text, x, y, paint);
    }

    // Getters
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
}
