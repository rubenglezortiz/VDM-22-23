package com.example.aengine;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

import java.util.HashMap;
import java.util.Objects;

public class AGraphics implements IGraphics {
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
    // Maps
    private final HashMap<String, AFont> fonts;
    private final HashMap<String, AImage> images;

    public AGraphics(SurfaceView myView_){
        this.myView = myView_;
        this.holder = this.myView.getHolder();
        this.paint = new Paint();
        this.paint.setColor(0xFFFFFFFF);
        this.assetManager = this.myView.getContext().getAssets();
        this.logicWidth = 400; this.logicHeight = 600;
        DisplayMetrics metrics = this.myView.getContext().getResources().getDisplayMetrics();
        this.screenHeight = metrics.heightPixels;
        this.screenWidth = metrics.widthPixels;
        setResolution((float)this.screenWidth, (float)this.screenHeight);
        this.defaultFont = this.paint.getTypeface();
        this.fonts = new HashMap<>();
        this.images = new HashMap<>();
    }


    //________________________________CREATE________________________________
    @Override
    public IColor newColor(int r_, int g_, int b_, int a_) {
        return new AColor(r_, g_, b_, a_);
    }

    @Override
    public void newImage(String name) {
        if(this.images.containsKey(name))return;
        AImage image = new AImage(name, this.assetManager);
        this.images.put(name, image);
    }

    @Override
    public void newFont(String name, boolean bold) {
        if(this.fonts.containsKey(name)) return;
        AFont font = new AFont(name, bold, this.assetManager);
        this.fonts.put(name, font);
    }

    @Override
    public IButton newButton(String text, int x, int y, int w, int h, int tX, int tY, int tSize, IFont f, IColor mColor, IColor bgColor) {
        return new AButton(text, x, y, w, h, tX, tY, tSize, (AFont)f, (AColor) mColor, (AColor) bgColor);
    }



    //________________________________DRAW________________________________
    @Override
    public void clear(IColor color) {
        setColor(color);
        this.canvas.drawRect(0,0, getWidth(), getHeight(), this.paint);
    }

    @Override
    public void drawLine(float x, float y, float x_stop, float y_stop, IColor color) {
        setColor(color);
        this.canvas.drawLine(realToLogicX((int)x), realToLogicY((int)y),
                realToLogicX((int)x_stop), realToLogicY((int)y_stop), this.paint);
    }

    @Override
    public void drawRectangle(float x_, float y_, float w_, float h_, IColor color) {
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

    @Override
    public void fillRectangle(float x_, float y_, float w_, float h_,IColor color){
        setColor(color);
        int x = realToLogicX((int)x_);
        int y = realToLogicY((int)y_);
        int w = realToLogicScale((int)w_);
        int h = realToLogicScale((int)h_);
        this.canvas.drawRect(x,y,x+w, y+h, this.paint);
    }

    @Override
    public void drawImage(String key, float x_, float y_, float w_, float h_) {
        if(this.images.containsKey(key)) {
            this.canvas.drawBitmap(Objects.requireNonNull(images.get(key)).getBitmap(), null,
                    new Rect((int) x_, (int) y_,(int)(x_ + w_), (int)(y_ + h_)), this.paint);
        }
    }

    @Override
    public void drawText(String text, float x, float y, float textSize, IColor color) {
        float prevTextSize = this.paint.getTextSize();
        this.paint.setTextSize(textSize * getScaleFactor());
        this.paint.setColor(((AColor)color).getColor());
        this.canvas.drawText(text,realToLogicX((int)x),realToLogicY((int)y), this.paint);
        this.paint.setTextSize(prevTextSize);
    }

    @Override
    public void drawText(String font, String text, float x, float y, float textSize, IColor color) {
        setColor(color);
        if(this.fonts.containsKey(font)) {
            this.paint.setTypeface(Objects.requireNonNull(this.fonts.get(font)).getTypeface());
            this.paint.setTextSize(logicToRealScale((int) textSize));
            this.canvas.drawText(text, logicToRealX((int) x), logicToRealY((int) y), this.paint);
            this.paint.setTypeface(Objects.requireNonNull(this.fonts.get(defaultFont)).getTypeface());
        }
    }

    @Override
    public void drawButton(IButton button) {
        float butX = (float) button.getPosX();
        float butY = (float) button.getPosY();
        float butW = (float) button.getWidth();
        float butH = (float) button.getHeight();
        this.fillRectangle(butX,butY,butW,butH, button.getBackgroundColor());
        this.drawRectangle(butX,butY,butW,butH, button.getMainColor());
    }



    //________________________________GETTERS________________________________
    @Override
    public int getWidth() { return this.screenWidth; }

    @Override
    public int getHeight() {
        return this.screenHeight;
    }

    @Override
    public int getLogicWidth() {
       return this.logicWidth;
    }

    @Override
    public int getLogicHeight() {
        return this.logicHeight;
    }

    @Override
    public float getScaleFactor() { return this.scaleFactor; }



    //________________________________GRAPHICS FUNCTIONS________________________________
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
    public int realToLogicX(int x) { return ((int) ((float)x * getScaleFactor()) + this.offsetX) ; }

    @Override
    public int realToLogicY(int y) { return ((int) ((float)y * getScaleFactor()) + this.offsetY) ; }

    @Override
    public int realToLogicScale(int s) {  return (int) ((float)s * getScaleFactor()); }

    @Override
    public int logicToRealX(int x) { return (int) ((float)(x - this.offsetX) / getScaleFactor());}

    @Override
    public int logicToRealY(int y){ return (int) ((float)(y - this.offsetY) / getScaleFactor());}

    @Override
    public int logicToRealScale(int s){ return (int) ((float) s/ getScaleFactor()); }

    @Override
    public void setColor(IColor color){
        this.paint.setColor(((AColor)color).getColor());
    }

    @Override
    public void setFont(IFont font){
        this.paint.setTypeface(((AFont)font).getTypeface());
        this.paint.setTextSize(((AFont)font).getSize());
    }
}
