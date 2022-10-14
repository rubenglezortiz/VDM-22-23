package com.example.androidengine;


import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphicsEngine;
import com.example.engine.IStateEngine;

import android.view.SurfaceView;


public class AndroidEngine extends IEngine {
    private GraphicsEngine graphics;
    private AudioEngine audio;
    private StateEngine state;


    AndroidEngine(){
        //graphics = new GraphicsEngine(); DESDE DÓNDE SE PASA EL SURFACE VIEW???
        audio = new AudioEngine();
        state = new StateEngine();
    }

    @Override
    public IGraphicsEngine getGraphics() { return graphics;}

    @Override
    public IAudio getAudio() { return audio;}

    @Override
    public IStateEngine getState() { return state;}

    @Override
    public void run() {

    }
}
