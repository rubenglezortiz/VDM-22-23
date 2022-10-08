package com.example.androidengine;


import android.view.SurfaceView;

public class AndroidEngine /*extends IEngine*/ {
    private GraphicsEngine graphics;
    private AudioEngine audio;
    private StateEngine state;


    AndroidEngine(){
        //graphics = new GraphicsEngine(); DESDE DÓNDE SE PASA EL SURFACE VIEW???
        audio = new AudioEngine();
        state = new StateEngine();
    }

    public GraphicsEngine getGraphics() {return graphics;}
    public AudioEngine getAudio() {return audio;}
    public StateEngine getState() {return state;}
}
