package com.example.engine;

public abstract class IEngine implements Runnable {
    IGraphicsEngine graphics;
    public abstract IGraphicsEngine getGraphics();
    public abstract IAudio getAudio();
    public abstract IStateEngine getState();
}
