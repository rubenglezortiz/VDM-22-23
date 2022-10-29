package com.example.androidengine;


import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IInput;
import com.example.engine.IState;


public class AndroidEngine implements IEngine {
    private GraphicsEngine graphics;
    private AudioEngine audio;
    private StateEngine state;


    AndroidEngine(){
        //graphics = new GraphicsEngine(); DESDE DÓNDE SE PASA EL SURFACE VIEW???
        audio = new AudioEngine();
        state = new StateEngine();
    }

    @Override
    public IGraphics getGraphics() { return graphics;}

    @Override
    public IAudio getAudio() { return audio;}

    @Override
    public IState getState() { return state;}

    @Override
    public IInput getInput() { return null; }
}
