package com.example.aengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.Objects;

public class AGraphics  {
    // Android variables
    private final SurfaceView myView;
    private final SurfaceHolder holder;
    private Canvas canvas;
    private final Paint paint;
    private final AssetManager assetManager;

    // Class Variables
    private int screenWidth, screenHeight;
    private final int logicWidth, logicHeight;
    private float scaleFactor;
    private int offsetX, offsetY;
    private boolean isHorizontallyScaled;

    // Items Variables
    private  AColor background;
    private final AColor defaultColor;
    private final String defaultFont;
    private final HashMap<String, AFont> fonts;
    private final HashMap<String, AImage> images;

    public AGraphics(SurfaceView myView_){
        this.myView = myView_;
        this.holder = this.myView.getHolder();
        this.paint = new Paint();
        this.paint.setColor(0xFFFFFFFF);
        this.assetManager = this.myView.getContext().getAssets();
        this.logicWidth = 400;
        this.logicHeight = 600;
        this.defaultColor = newColor(255,255,255,255);
        this.background = newColor(255,255,255,255);
        this.fonts = new HashMap<>();
        this.images = new HashMap<>();
        this.defaultFont = "default";
        this.fonts.put(this.defaultFont, new AFont(this.paint.getTypeface()));
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
        float scaleFactorX = newWidth / (float) this.logicWidth;
        float scaleFactorY = newHeight / (float) this.logicHeight;
        this.scaleFactor = Math.min(scaleFactorX, scaleFactorY);

        this.isHorizontallyScaled = screenWidth * 3 > screenHeight * 2;

        if (this.isHorizontallyScaled){
            this.offsetX = (int)(((float)getWidth() / 2.0f) - (((float) this.logicWidth / 2.0f) * this.scaleFactor));
            this.offsetY = 0;
        }
        else{
            this.offsetX = 0;
            this.offsetY = (int)(((float)getHeight() / 2.0f) - (((float) this.logicHeight / 2.0f)  * this.scaleFactor));
        }
    }

    /*public void translate(int x, int y) {
        this.canvas.translate(x, y);
    }*/

    public float logicToRealX(float x) { return x * getScaleFactor() + this.offsetX ; }

    public float logicToRealY(float y) { return  y * getScaleFactor() + this.offsetY ; }

    public float logicToRealScale(float s) {  return s * getScaleFactor(); }

    public float realToLogicX(float x) { return ((x - this.offsetX) / getScaleFactor());}

    public float realToLogicY(float y){ return ((y - this.offsetY) / getScaleFactor());}

    public int realToLogicScale(int s){ return (int) ((float) s / getScaleFactor()); }

    public void setColor(AColor color){
        this.paint.setColor(color.getColor());
    }

    /*public void setFont(AFont font){
        this.paint.setTypeface(font.getTypeface());
        this.paint.setTextSize(font.getSize());
    }
     */

    // Create

    public AColor newColor(int r_, int g_, int b_, int a_) {
        return new AColor(r_, g_, b_, a_);
    }

    public void newImage(String name) {
        if(this.images.containsKey(name))return;
        AImage image = new AImage(name, this.assetManager);
        this.images.put(name, image);
    }

    public void newFont(String name, boolean bold) {
        if(this.fonts.containsKey(name)) return;
        AFont font = new AFont(name, bold, this.assetManager);
        this.fonts.put(name, font);
    }

    public AButton newButton(String name, float x, float y, float w, float h, AColor bgColor) {
        if(!this.images.containsKey(name))
            this.images.put(name, new AImage(name, this.assetManager));
        return new AButton(name, x, y, w, h, bgColor);
    }

    public void changeButtonPosition(AButton button, float x, float y){
         button.changePosition(x, y);
    }

    public void setBackgroundColor(AColor backgroundColor){
        this.background = backgroundColor;
    }

    public void clear() {
        setColor(this.background);
        this.canvas.drawRect(0,0, getWidth(), getHeight(), this.paint);

        setColor(this.defaultColor);
    }

    public void drawLine(float x, float y, float x_stop, float y_stop, AColor color) {
        setColor(color);
        this.canvas.drawLine(logicToRealX((int)x), logicToRealY((int)y),
                logicToRealX((int)x_stop), logicToRealY((int)y_stop), this.paint);

        setColor(this.defaultColor);
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

        setColor(this.defaultColor);
    }

    public void fillRectangle(float x_, float y_, float w_, float h_,AColor color){
        setColor(color);
        float x = logicToRealX(x_);
        float y = logicToRealY(y_);
        float w = logicToRealScale(w_);
        float h = logicToRealScale(h_);
        this.canvas.drawRect(x,y,x+w, y+h, this.paint);

        setColor(this.defaultColor);
    }

    public void drawImage(String key, float x, float y, float w, float h) {
        x = logicToRealX(x);
        y = logicToRealY(y);
        w = logicToRealScale(w);
        h = logicToRealScale(h);
        if(this.images.containsKey(key)) {
            this.canvas.drawBitmap(Objects.requireNonNull(images.get(key)).getBitmap(), null,
                    new Rect((int)x, (int)y,(int)(x+w), (int)(y+h)), this.paint);
        }
    }

    public void drawText(String text, float x, float y, float textSize, AColor color) {
        float prevTextSize = this.paint.getTextSize();
        this.paint.setTextSize(logicToRealScale((int) textSize));
        this.paint.setColor((color).getColor());
        this.canvas.drawText(text,logicToRealX((int)x),logicToRealY((int)y), this.paint);
        this.paint.setTextSize(prevTextSize);
    }

    public void drawText(String font, String text, float x, float y, float textSize, AColor color) {
        setColor(color);
        if(this.fonts.containsKey(font)) {
            this.paint.setTypeface(Objects.requireNonNull(this.fonts.get(font)).getTypeface());
            this.paint.setTextSize(logicToRealScale((int) textSize));
            this.canvas.drawText(text, logicToRealX((int) x), logicToRealY((int) y), this.paint);
            this.paint.setTypeface(Objects.requireNonNull(this.fonts.get(defaultFont)).getTypeface());
        }
    }

    public void drawButton(AButton button) {
        float butX = button.getPosX();
        float butY = button.getPosY();
        float butW = button.getWidth();
        float butH = button.getHeight();

        this.fillRectangle(butX,butY,butW,butH, button.getBackgroundColor());
        if(this.images.containsKey(button.getName()))
            this.drawImage(button.getName(), butX, butY, butW, butH);
        else System.out.println("!!!!!!!!!!!!!!NO VALID IMAGE NAME!!!!!!!!!!!!!!");
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

    public boolean getHorizontallyScaled(){ return this.isHorizontallyScaled; }
}