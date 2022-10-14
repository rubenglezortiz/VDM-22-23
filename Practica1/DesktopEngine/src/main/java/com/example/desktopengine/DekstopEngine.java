package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphicsEngine;
import com.example.engine.IStateEngine;

public class DekstopEngine extends IEngine {
    private GraphicsEngine graphics;
    private AudioEngine audio;
    private StateEngine state;

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