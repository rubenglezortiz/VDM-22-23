package com.example.aengine;
import android.annotation.SuppressLint;
import android.view.SurfaceView;
import com.google.android.gms.ads.AdView;

public class AndroidEngine implements Runnable {
    private final AGraphics graphics;
    private final AAudio audio;
    private final AInput input;
    private final AState currentState;
    private final AExternal external;

    private final SurfaceView myView;
    private Thread currentThread;

    private boolean running;

    @SuppressLint("ClickableViewAccessibility")
    public AndroidEngine(SurfaceView myView_, AdView adView_){
        this.myView = myView_;
        this.graphics = new AGraphics(this.myView);
        this.audio = new AAudio(this.myView.getContext().getAssets());
        this.input = new AInput();
        this.external = new AExternal(this.myView, adView_);

        this.myView.setOnLongClickListener(this.input);
        this.myView.setOnTouchListener(this.input);
        this.currentState = new AState(this.myView, this);
        this.currentThread = new Thread(this);
        this.currentThread.start();
    }

    public AGraphics getGraphics() { return this.graphics;}

    public AInput getInput() {return this.input;}

    public AState getCurrentState() { return this.currentState;}

    public AAudio getAudio() { return this.audio;}

    public AExternal getExternal() { return this.external;}

    public int getTime() {
        long time = System.nanoTime() / 1000000;
        time /= 1000.0f;
        return (int)time;
    }

    @Override
    public void run() {
        System.out.println("inicio hilo ");
        if ( this.currentThread != Thread.currentThread()) {
            // Evita que cualquiera que no sea esta clase llame a este Runnable en un Thread
            // Programación defensiva
            throw new RuntimeException("run() should not be called directly");
        }
        while(this.running && this.myView.getWidth() == 0);

        while(this.running) {
            //Update
            this.currentState.update();
            //Inputs
            this.currentState.handleInputs();
            while (!this.graphics.changeBuffer());
            //Pre frame
            this.graphics.prepareFrame();
            //Render
            this.currentState.render();
            //Post frame
            this.graphics.finishFrame();
        }
    }

    public void resume(){
        if (!this.running) {
            this.running = true;
            this.audio.resumeAllSounds();
            this.currentThread = new Thread(this);
            this.currentThread.start();
        }
    }

    public void pause() {
        if (this.running) {
            this.running = false;
                try {
                    this.audio.pauseAllSounds();
                    this.currentThread.join();
                    this.currentThread = null;
                } catch (InterruptedException ie) {}
        }
    }

    public SurfaceView getSurfaceView(){return this.myView;}
}
