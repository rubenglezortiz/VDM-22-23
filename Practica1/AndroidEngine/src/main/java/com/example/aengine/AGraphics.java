package com.example.aengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.util.DisplayMetrics;
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
    private float screenWidth, screenHeight;
    private float logicWidth, logicHeight;
    private float scaleFactorX, scaleFactorY, scaleFactor;
    private float offsetX, offsetY;
    private boolean scaleInX;
    private Typeface defaultFont;

    // Thread
    private Thread renderThread;
    private boolean running;

    public AGraphics(SurfaceView myView_){
        this.myView = myView_;
        this.holder = this.myView.getHolder();
        this.paint = new Paint();
        this.paint.setColor(0xFFFFFFFF);
        this.assetManager = this.myView.getContext().getAssets();
        this.logicWidth = 400;
        this.logicHeight = 600;

        DisplayMetrics metrics = this.myView.getContext().getResources().getDisplayMetrics();
        this.screenHeight = metrics.heightPixels;
        this.screenWidth = metrics.widthPixels;
        setResolution((float)this.screenWidth, (float)this.screenHeight);
        this.defaultFont = this.paint.getTypeface();
    }
    public AGraphics(SurfaceView myView_,int logicWidth_, int logicHeight_){
        this.myView = myView_;
        this.holder = this.myView.getHolder();
        this.paint = new Paint();
        this.paint.setColor(0xFFFFFFFF);
        this.assetManager = this.myView.getContext().getAssets();
        this.logicWidth = logicWidth_;
        this.logicHeight = logicHeight_;

        DisplayMetrics metrics = this.myView.getContext().getResources().getDisplayMetrics();
        this.screenHeight = metrics.heightPixels;
        this.screenWidth = metrics.widthPixels;
        setResolution((float)this.screenWidth, (float)this.screenHeight);
        this.defaultFont = this.paint.getTypeface();
    }

    // Android functions
    @Override
    public void prepareFrame() {
        this.canvas = this.holder.lockCanvas();
        this.clear(newColor(220,220,220,255));
    }

    @Override
    public void finishFrame() {
        this.holder.unlockCanvasAndPost(this.canvas);
    }

    @Override
    public boolean changeBuffer() { return (this.holder.getSurface().isValid()); }

    @Override
    public void setResolution(float newX, float newY){
        this.scaleFactorX = (float)this.screenWidth / (float) this.logicWidth;
        this.scaleFactorY = (float)this.screenHeight / (float) this.logicHeight;
        this.scaleFactor = Math.min(this.scaleFactorX, this.scaleFactorY);

        this.scaleInX = getWidth() * 3 > getHeight() * 2;

        if (this.scaleInX){
            this.offsetX = (int)(((float)getWidth() / 2.0f) - (((float) this.logicWidth / 2.0f) * this.scaleFactor));
            this.offsetY = 0;
        }
        else{
            this.offsetX = 0;
            this.offsetY = (int)(((float)getHeight() / 2.0f) - (((float) this.logicHeight / 2.0f)  * this.scaleFactor));
        }
    }

    @Override
    public void translate(int x, int y) {
        this.canvas.translate(x, y);
    }

    @Override
    public float realToLogicX(float x) { return (x - this.offsetX) / getScaleFactor(); }

    @Override
    public float realToLogicY(float y) { return (y - this.offsetY) / getScaleFactor(); }

    @Override
    public float realToLogicScale(float s) { return s / getScaleFactor(); }

    @Override
    public float logicToRealX(float x) { return (x * getScaleFactor()) + this.offsetX;}

    @Override
    public float logicToRealY(float y){ return (y * getScaleFactor()) + this.offsetY; }

    @Override
    public float logicToRealScale(float s){ return s * getScaleFactor(); }

    @Override
    public void setColor(IColor color){ this.paint.setColor(((AColor)color).getARGBColor()); }

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
    public IButton newButton(String text, int x, int y, int w, int h, int tX, int tY, int tSize, IFont f, IColor mColor, IColor bgColor) {
        return new AButton(text, x, y, w, h, tX, tY, tSize, (AFont)f, (AColor) mColor, (AColor) bgColor);
    }


    @Override
    public void clear(IColor color) {
        setColor(color);
        this.canvas.drawRect(0,0, getWidth(), getHeight(), this.paint);
    }

    @Override
    public void drawLine(float x, float y, float x_stop, float y_stop, IColor color) {
        setColor(color);
        this.canvas.drawLine(logicToRealX(x), logicToRealY(y),
                logicToRealX(x_stop), logicToRealY(y_stop), this.paint);
    }

    @Override
    public void drawRectangle(float x_, float y_, float w_, float h_, IColor color) {
        setColor(color);
        float x = logicToRealX(x_);
        float y = logicToRealY(y_);
        float w = logicToRealScale(w_);
        float h = logicToRealScale(h_);
        this.canvas.drawLine(x,y,x+w,y,this.paint);
        this.canvas.drawLine(x,y,x,y + h,this.paint);
        this.canvas.drawLine(x,y+h,x+w,y+h,this.paint);
        this.canvas.drawLine(x+w,y,x+w,y+h,this.paint);
    }

    @Override
    public void fillRectangle(float x_, float y_, float w_, float h_,IColor color){
        setColor(color);
        float x = logicToRealX(x_);
        float y = logicToRealY(y_);
        float w = logicToRealScale(w_);
        float h = logicToRealScale(h_);
        this.canvas.drawRect(x,y,x+w, y+h, this.paint);
    }

    @Override
    public void drawImage(IImage image, float x, float y, float w, float h) {
        //INTENTAR METER VALORES POR DEFECTO
        ((AImage)image).setWidth((int)logicToRealScale(w));
        ((AImage)image).setHeight((int)logicToRealScale(h));
        this.canvas.drawBitmap(((AImage)image).getBitmap(),logicToRealX(x), logicToRealY(y), this.paint);
    }


    @Override
    public void drawText(String text, float x, float y, float textSize, IColor color) {
        float prevTextSize = this.paint.getTextSize();
        this.paint.setTextSize(logicToRealScale(textSize));
        this.paint.setColor(((AColor)color).getARGBColor());
        this.canvas.drawText(text,logicToRealX(x),logicToRealY(y), this.paint);
        this.paint.setTextSize(prevTextSize);
    }

    @Override
    public void drawText(IFont font, String text, float x, float y, float textSize, IColor color) {
        setColor(color);
        this.paint.setTypeface(((AFont)font).getTypeface());
        this.paint.setTextSize(logicToRealScale(textSize));
        this.canvas.drawText(text,logicToRealX(x),logicToRealY(y), this.paint);
        this.paint.setTypeface(this.defaultFont);
    }

    @Override
    public void drawButton(IButton button) {
        float butX = (float) button.getPosX();
        float butY = (float) button.getPosY();
        float butW = (float) button.getWidth();
        float butH = (float) button.getHeight();

        this.fillRectangle(butX,butY,butW,butH, button.getBackgroundColor());
        this.drawRectangle(butX,butY,butW,butH, button.getMainColor());

        if(button.getFont()!=null)
            this.drawText(button.getFont(), button.getText(), butX + button.getTextX(), butY + button.getTextY(), button.getTextSize(), button.getMainColor());
        else this.drawText(button.getText(), butX + button.getTextX(), butY + button.getTextY(), button.getTextSize(), button.getMainColor());

    }

    // Getters
    @Override
    public float getWidth() { return this.screenWidth; }

    @Override
    public float getHeight() {
        return this.screenHeight;
    }

    @Override
    public float getLogicWidth() {
       return this.logicWidth;
    }

    @Override
    public float getLogicHeight() {
        return this.logicHeight;
    }

    @Override
    public float getScaleFactor() { return this.scaleFactor; }
}
