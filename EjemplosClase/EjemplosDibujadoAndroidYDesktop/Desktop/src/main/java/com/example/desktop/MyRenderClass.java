package com.example.desktop;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

//Clase interna encargada de obtener el SurfaceHolder y pintar con el canvas
public class MyRenderClass implements Runnable{

    private JFrame myView;
    private BufferStrategy bufferStrategy;
    private Graphics2D graphics2D;

    private Thread renderThread;

    private boolean running;

    private MyScene scene;

    public MyRenderClass(JFrame myView){
        this.myView = myView;
        this.myView.addComponentListener(new ComponentAdapter()
        {
            public void componentResized(ComponentEvent evt) {
                //Component c = (Component)evt.getSource();
                System.out.println("componentResized: "+evt.getSource());
                graphics2D.dispose();

                bufferStrategy.show();
                graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
            }
        });

        this.bufferStrategy = this.myView.getBufferStrategy();
        this.graphics2D = (Graphics2D) bufferStrategy.getDrawGraphics();
    }

    public int getWidth(){
        return this.myView.getWidth();
    }

    public int getHeight(){
        return this.myView.getHeight();
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
                //System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;

            // Pintamos el frame
            do {
                do {
                    Graphics graphics = this.bufferStrategy.getDrawGraphics();
                    try {
                        this.render();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (FontFormatException e) {
                        e.printStackTrace();
                    } finally {
                        graphics.dispose(); //Elimina el contexto gráfico y libera recursos del sistema realacionado
                    }
                } while(this.bufferStrategy.contentsRestored());
                this.bufferStrategy.show();
            } while(this.bufferStrategy.contentsLost());

            /*
            // Posibilidad: cedemos algo de tiempo. Es una medida conflictiva...
            try { Thread.sleep(1); } catch(Exception e) {}
            */
        }
    }

    protected void update(double deltaTime) {
        this.scene.update(deltaTime);
    }

    public void setScene(MyScene scene) {
        this.scene = scene;
    }

    protected void renderCircle(float x, float y, float r){
        this.graphics2D.setColor(Color.white);
        this.graphics2D.fillOval((int)x, (int)y, (int)r*2, (int)r*2);
        this.graphics2D.setPaintMode();
        this.graphics2D.setColor((Color.green));
        this.graphics2D.fillRect((int)0,(int)250,(int)200,(int)100);
    }

    protected void renderText() throws IOException, FontFormatException {
        String filePath = new String("font.TTF");
        InputStream is = new FileInputStream(filePath);
        Font awtFont = Font.createFont(Font.TRUETYPE_FONT, is);
        awtFont = awtFont.deriveFont(awtFont.getStyle(),10);

        this.graphics2D.setFont(awtFont);
        this.graphics2D.drawString("holaa",100,100);

    }

    protected void renderImage() throws IOException {
        String filePath = "./gato1.jpg";
        Image img = ImageIO.read(new File(filePath));
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(img, 0,0, 200,200,null);
    }

    protected void render() throws IOException, FontFormatException {
        // "Borramos" el fondo.
        this.graphics2D.setColor(Color.BLUE);
        this.graphics2D.fillRect(0,0, this.getWidth(), this.getHeight());
        // Pintamos la escena
        this.scene.render();
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

