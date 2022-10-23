package com.example.ejemplosdibujadoandroidydesktop;


import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private SurfaceView renderView;

    private MyRenderClass render;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Creamos el SurfaceView que "contendrá" nuestra escena
        this.renderView = new SurfaceView(this);
        setContentView(this.renderView);
        MyScene scene = new MyScene();

        this.render = new MyRenderClass(this.renderView);
        scene.init(render);
        render.setScene(scene);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.render.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.render.pause();
    }

    //Clase interna que representa la escena que queremos pintar
    class MyScene {
        private int x;
        private int y;
        private int radius;
        private int speed;

        private MyRenderClass renderClass;

        public MyScene(){
            this.x=100;
            this.y=0;
            this.radius = 100;
            this.speed = 150;
        }

        public void init(MyRenderClass renderClass){
            this.renderClass = renderClass;
        }

        public void update(double deltaTime){
            int maxX = this.renderClass.getWidth()-this.radius;

            this.x += this.speed * deltaTime;
            this.y += 1;
            while(this.x < this.radius || this.x > maxX) {
                // Vamos a pintar fuera de la pantalla. Rectificamos.
                if (this.x < this.radius) {
                    // Nos salimos por la izquierda. Rebotamos.
                    this.x = this.radius;
                    this.speed *= -1;
                } else if (this.x > maxX) {
                    // Nos salimos por la derecha. Rebotamos
                    this.x = 2 * maxX - this.x;
                    this.speed *= -1;
                }
            }
        }

        public void render() throws IOException {
            renderClass.renderImages();
            renderClass.renderCircle(this.x, this.y, this.radius);
            renderClass.renderText();
        }
    }

    //Clase interna encargada de obtener el SurfaceHolder y pintar con el canvas
    class MyRenderClass implements Runnable{

        private SurfaceView myView;
        private SurfaceHolder holder;
        private Canvas canvas;

        private Thread renderThread;

        private boolean running;

        private Paint paint;

        private MyScene scene;

        private AssetManager assetManager;

        public MyRenderClass(SurfaceView myView){
            this.myView = myView;
            this.holder = this.myView.getHolder();
            this.paint = new Paint();
            this.paint.setColor(0xFFFFFFFF);
            this.assetManager = getAssets();
        }

        public int getWidth(){
            return this.myView.getWidth();
        }

        @Override
        public void run() {
            if (renderThread != Thread.currentThread()) {
                // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
                // Programación defensiva
                throw new RuntimeException("run() should not be called directly");
            }

            // Si el Thread se pone en marcha
            // muy rápido, la vista podría todavía no estar inicializada.
            while(this.running && this.myView.getWidth() == 0);
            // Espera activa. Sería más elegante al menos dormir un poco.

            long lastFrameTime = System.nanoTime();

            long informePrevio = lastFrameTime; // Informes de FPS
            int frames = 0;

            // Bucle de juego principal.
            while(running) {
                long currentTime = System.nanoTime();
                long nanoElapsedTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                // Informe de FPS
                double elapsedTime = (double) nanoElapsedTime / 1.0E9;
                this.update(elapsedTime);
                if (currentTime - informePrevio > 1000000000l) {
                    long fps = frames * 1000000000l / (currentTime - informePrevio);
                    System.out.println("" + fps + " fps");
                    frames = 0;
                    informePrevio = currentTime;
                }
                ++frames;

                // Pintamos el frame
                while (!this.holder.getSurface().isValid()); //esperamos a que el holder esté listo
                this.canvas = this.holder.lockCanvas();
                try {
                    this.render();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                this.holder.unlockCanvasAndPost(canvas);

                /*
                // Posibilidad: cedemos algo de tiempo. Es una medida conflictiva...
                try { Thread.sleep(1); } catch(Exception e) {}
    			*/
            }
        }

        protected void update(double deltaTime) {
            scene.update(deltaTime);
        }

        public void setScene(MyScene scene) {
            this.scene = scene;
        }

        protected void renderCircle(float x, float y, float r){
            this.paint.setColor(0xFFFF0000);
            canvas.drawCircle(x, y, r, this.paint);
        }

        protected void renderText(){
            this.paint.setTextSize(50);
            this.paint.setColor(0xFF000000);
            canvas.drawText("MIRA QUE MONOS LOS GATETES",180,1500, this.paint);
        }

        protected void renderImages() throws IOException {
            String filename = "gato1.jpg";
            int a = 0;
            InputStream is = assetManager.open(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            canvas.drawBitmap(bitmap, 400,700, this.paint);

            filename = "gato2.jpg";
            is = assetManager.open(filename);
            bitmap = BitmapFactory.decodeStream(is);
            canvas.drawBitmap(bitmap, 0,0, this.paint);
        }

        protected void render() throws IOException {
            // "Borramos" el fondo.
            this.canvas.drawColor(0xFF00FFFF); // ARGB
            scene.render();
        }

        public void resume() {
            if (!this.running) {
                // Solo hacemos algo si no nos estábamos ejecutando ya
                // (programación defensiva)
                this.running = true;
                // Lanzamos la ejecución de nuestro método run() en un nuevo Thread.
                this.renderThread = new Thread(this);
                this.renderThread.start();
            }
        }

        public void pause() {
            if (this.running) {
                this.running = false;
                while (true) {
                    try {
                        this.renderThread.join();
                        this.renderThread = null;
                        break;
                    } catch (InterruptedException ie) {
                        // Esto no debería ocurrir nunca...
                    }
                }
            }
        }
    }
}