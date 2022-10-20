package com.example.desktopengine;

import com.example.engine.IAudio;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IState;

public class DekstopEngine implements IEngine {
    private GraphicsEngine graphics;
    private AudioEngine audio;
    private StateEngine state;

    @Override
    public IGraphics getGraphics() { return graphics;}

    @Override
    public IAudio getAudio() { return audio;}

    @Override
    public IState getState() { return state;}
}