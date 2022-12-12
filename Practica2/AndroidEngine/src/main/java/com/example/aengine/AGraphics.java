package com.example.aengine;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AGraphics  {
    // Android variables
    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;
    private AssetManager assetManager;

    // Class Variables
    private int screenWidth, screenHeight;
    private int logicWidth, logicHeight;
    private float scaleFactorX, scaleFactorY, scaleFactor;
    private int offsetX, offsetY;
    private boolean scaleInX;
    private Typeface defaultFont;

    // Thread
    private Thread renderThread;
    private boolean running;

    // Other Variables
    private  AColor background;

    public AGraphics(SurfaceView myView_){
        this.myView = myView_;
        this.holder = this.myView.getHolder();
        this.paint = new Paint();
        this.paint.setColor(0xFFFFFFFF);
        this.assetManager = this.myView.getContext().getAssets();
        this.logicWidth = 400;
        this.logicHeight = 600;
        this.defaultFont = this.paint.getTypeface();
        this.background = newColor(255,255,255,255);
    }

    // Android functions
    public void prepareFrame() {
        this.canvas = this.holder.lockCanvas();
        DisplayMetrics metrics = this.myView.getContext().getResources().getDisplayMetrics();
        this.screenHeight = metrics.heightPixels;
        this.screenWidth = metrics.widthPixels;
        setResolution((float)this.screenWidth, (float)this.screenHeight);
        this.clear();
    }

    public void finishFrame() {
        this.holder.unlockCanvasAndPost(this.canvas);
    }

    public boolean changeBuffer() { return (this.holder.getSurface().isValid()); }

    public void setResolution(float newWidth, float newHeight){
        this.scaleFactorX = newWidth / (float) this.logicWidth;
        this.scaleFactorY = newHeight / (float) this.logicHeight;
        this.scaleFactor = Math.min(this.scaleFactorX, this.scaleFactorY);

        this.scaleInX = screenWidth * 3 > screenHeight * 2;

        if (this.scaleInX){
            this.offsetX = (int)(((float)getWidth() / 2.0f) - (((float) this.logicWidth / 2.0f) * this.scaleFactor));
            this.offsetY = 0;
        }
        else{
            this.offsetX = 0;
            this.offsetY = (int)(((float)getHeight() / 2.0f) - (((float) this.logicHeight / 2.0f)  * this.scaleFactor));
        }
    }

    public void translate(int x, int y) {
        this.canvas.translate(x, y);
    }

    public float logicToRealX(float x) { return x * getScaleFactor() + this.offsetX ; }

    public float logicToRealY(float y) { return  y * getScaleFactor() + this.offsetY ; }

    public float logicToRealScale(float s) {  return s * getScaleFactor(); }

    public float realToLogicX(float x) { return ((float)(x - this.offsetX) / getScaleFactor());}

    public float realToLogicY(float y){ return ((float)(y - this.offsetY) / getScaleFactor());}

    public int realToLogicScale(int s){ return (int) ((float) s / getScaleFactor()); }

    public void setColor(AColor color){
        this.paint.setColor(((AColor)color).getARGBColor());
    }

    public void setFont(AFont font){
        this.paint.setTypeface(((AFont)font).getTypeface());
        this.paint.setTextSize(((AFont)font).getSize());
    }

    // Create

    public AColor newColor(int r_, int g_, int b_, int a_) {
        return new AColor(r_, g_, b_, a_);
    }

    public AImage newImage(String name) {
        return new AImage(name, this.assetManager);
    }

    public AFont newFont(String name, boolean bold) {
        return new AFont(name, bold, this.assetManager);
    }

    public AButton newButton(String text, float x, float y, float w, float h, float tX, float tY, int tSize, AFont f, AColor mColor, AColor bgColor) {
        return new AButton(text, x, y, w, h, tX, tY, tSize, f,  mColor, bgColor);
    }

    public void setBackgroundColor(AColor backgroundColor){
        this.background = backgroundColor;
    }

    public void clear() {
        setColor(this.background);
        this.canvas.drawRect(0,0, getWidth(), getHeight(), this.paint);
    }

    public void drawLine(float x, float y, float x_stop, float y_stop, AColor color) {
        setColor(color);
        this.canvas.drawLine(logicToRealX((int)x), logicToRealY((int)y),
                logicToRealX((int)x_stop), logicToRealY((int)y_stop), this.paint);
    }

    public void drawRectangle(float x_, float y_, float w_, float h_, AColor color) {
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

    public void fillRectangle(float x_, float y_, float w_, float h_,AColor color){
        setColor(color);
        float x = logicToRealX(x_);
        float y = logicToRealY(y_);
        float w = logicToRealScale(w_);
        float h = logicToRealScale(h_);
        this.canvas.drawRect(x,y,x+w, y+h, this.paint);
    }

    public void drawImage(AImage image, float x, float y, float w, float h) {
        x = logicToRealX(x);
        y = logicToRealY(y);
        w = logicToRealScale(w);
        h = logicToRealScale(h);
        this.canvas.drawBitmap(image.getBitmap(), null,
                new Rect((int)x, (int)y,(int)(x+w), (int)(y+h)), this.paint);
    }


    public void drawText(String text, float x, float y, float textSize, AColor color) {
        float prevTextSize = this.paint.getTextSize();
        this.paint.setTextSize(logicToRealScale((int) textSize));
        this.paint.setColor(((AColor)color).getARGBColor());
        this.canvas.drawText(text,logicToRealX((int)x),logicToRealY((int)y), this.paint);
        this.paint.setTextSize(prevTextSize);
    }

    public void drawText(AFont font, String text, float x, float y, float textSize, AColor color) {
        setColor(color);
        this.paint.setTypeface(((AFont)font).getTypeface());
        this.paint.setTextSize(logicToRealScale((int) textSize));
        this.canvas.drawText(text,logicToRealX((int)x),logicToRealY((int)y), this.paint);
        this.paint.setTypeface(this.defaultFont);
    }

    public void drawButton(AButton button) {
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
    public int getWidth() { return this.screenWidth; }

    public int getHeight() {
        return this.screenHeight;
    }

    public int getLogicWidth() {
        return this.logicWidth;
    }

    public int getLogicHeight() {
        return this.logicHeight;
    }

    public float getScaleFactor() { return this.scaleFactor; }
}