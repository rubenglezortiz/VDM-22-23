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

    // Android functions
    public void prepareFrame() {
        this.canvas = this.holder.lockCanvas();
        this.clear(newColor(220,220,220,255));
    }

    public void finishFrame() {
        this.holder.unlockCanvasAndPost(this.canvas);
    }

    public boolean changeBuffer() { return (this.holder.getSurface().isValid()); }

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

    public void translate(int x, int y) {
        this.canvas.translate(x, y);
    }

    public int realToLogicX(int x) { return ((int) ((float)x * getScaleFactor()) + this.offsetX) ; }

    public int realToLogicY(int y) { return ((int) ((float)y * getScaleFactor()) + this.offsetY) ; }

    public int realToLogicScale(int s) {  return (int) ((float)s * getScaleFactor()); }

    public int logicToRealX(int x) { return (int) ((float)(x - this.offsetX) / getScaleFactor());}

    public int logicToRealY(int y){ return (int) ((float)(y - this.offsetY) / getScaleFactor());}

    public int logicToRealScale(int s){ return (int) ((float) s/ getScaleFactor()); }

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

    public AButton newButton(String text, int x, int y, int w, int h, int tX, int tY, int tSize, AFont f, AColor mColor, AColor bgColor) {
        return new AButton(text, x, y, w, h, tX, tY, tSize, (AFont)f, (AColor) mColor, (AColor) bgColor);
    }


    public void clear(AColor color) {
        setColor(color);
        this.canvas.drawRect(0,0, getWidth(), getHeight(), this.paint);
    }

    public void drawLine(float x, float y, float x_stop, float y_stop, AColor color) {
        setColor(color);
        this.canvas.drawLine(realToLogicX((int)x), realToLogicY((int)y),
                realToLogicX((int)x_stop), realToLogicY((int)y_stop), this.paint);
    }

    public void drawRectangle(float x_, float y_, float w_, float h_, AColor color) {
        setColor(color);
        int x = realToLogicX((int)x_);
        int y = realToLogicY((int)y_);
        int w = realToLogicScale((int)w_);
        int h = realToLogicScale((int)h_);
        this.canvas.drawLine(x,y,x+w,y,this.paint);
        this.canvas.drawLine(x,y,x,y + h,this.paint);
        this.canvas.drawLine(x,y+h,x+w,y+h,this.paint);
        this.canvas.drawLine(x+w,y,x+w,y+h,this.paint);
    }

    public void fillRectangle(float x_, float y_, float w_, float h_,AColor color){
        setColor(color);
        int x = realToLogicX((int)x_);
        int y = realToLogicY((int)y_);
        int w = realToLogicScale((int)w_);
        int h = realToLogicScale((int)h_);
        this.canvas.drawRect(x,y,x+w, y+h, this.paint);
    }

    public void drawImage(AImage image, int x, int y, int w, int h) {
        //INTENTAR METER VALORES POR DEFECTO
        ((AImage)image).setWidth(realToLogicScale(w));
        ((AImage)image).setHeight(realToLogicScale(h));
        this.canvas.drawBitmap(((AImage)image).getBitmap(),realToLogicX(x), realToLogicY(y), this.paint);
    }


    public void drawText(String text, float x, float y, float textSize, AColor color) {
        float prevTextSize = this.paint.getTextSize();
        this.paint.setTextSize(textSize * getScaleFactor());
        this.paint.setColor(((AColor)color).getARGBColor());
        this.canvas.drawText(text,realToLogicX((int)x),realToLogicY((int)y), this.paint);
        this.paint.setTextSize(prevTextSize);
    }

    public void drawText(AFont font, String text, float x, float y, float textSize, AColor color) {
        setColor(color);
        this.paint.setTypeface(((AFont)font).getTypeface());
        this.paint.setTextSize(textSize * getScaleFactor());
        this.canvas.drawText(text,realToLogicX((int)x),realToLogicY((int)y), this.paint);
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