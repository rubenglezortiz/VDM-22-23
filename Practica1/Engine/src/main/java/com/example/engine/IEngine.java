package com.example.engine;

public interface IEngine  {
    public abstract IGraphics getGraphics();
    public abstract IInput getInput();
    public abstract IState getCurrentState();
    public abstract IAudio getAudio();
    public abstract int getTime();
}
