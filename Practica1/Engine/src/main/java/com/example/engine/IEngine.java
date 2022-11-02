package com.example.engine;

public interface IEngine  {
    public abstract IGraphics getGraphics();
    public abstract IInput getInput();
    public abstract IState getState();
    public abstract IAudio getAudio();
}
