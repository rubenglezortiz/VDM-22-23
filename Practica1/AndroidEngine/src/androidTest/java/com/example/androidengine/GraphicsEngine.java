package com.example.androidengine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

public class GraphicsEngine /*extends IGraphics*/  {
    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;

    private AssetManager assetManager;

    //private Thread renderThread;
    //private boolean running;

    public GraphicsEngine(SurfaceView myView){
        this.myView = myView;
        this.holder = this.myView.getHolder();
        this.paint = new Paint();
        this.paint.setColor(0xFFFFFFFF);
        //this.assetManager = getAssets(); //DA ERROR
    }

    void setResolution(){

    };

    void setColor(String color){
        //Deberíamos comprobar que se pase bien el parámetro "color"???
        this.paint.setColor(Integer.parseInt(color));
    };

    void setFont(){
        //NI IDEA
    };

    void drawImage(String filePath, int x, int y) throws IOException { //INTENTAR METER VALORES POR DEFECTO
        drawImage(filePath, x, y,100,100);
    }

   void drawImage(String filePath, int x, int y, int w, int h) throws IOException {
        InputStream is = assetManager.open(filePath);
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        bitmap.setWidth(w); bitmap.setHeight(h); //COMPROBAR Q ESTO FUNCIONE
        canvas.drawBitmap(bitmap, x,y, this.paint);
    };



    void drawRectangle(float x, float y, float w, float h, String color){
        paint.setColor(Integer.parseInt(color));
        canvas.drawRect(x,y,w,h,paint);
    };


    void fillRectangle(){
        //NI IDEA
    };
    void drawLine(float x, float y, float x_stop, float y_stop, String color){
        paint.setColor(Integer.parseInt(color));
        canvas.drawLine(x,y,x_stop, y_stop, paint);
    };

    void drawText(String text, float x, float y, float textSize){
        paint.setColor(Integer.parseInt(text));
        paint.setTextSize(textSize);
        canvas.drawText(text, x, y, paint);
    };
}
