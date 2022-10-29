package com.example.engine;

public interface IEngine  { //METER ESTO COMO UNA INTERFAZ
    //IGraphicsEngine graphics;
    public abstract IGraphics getGraphics();
    public abstract IAudio getAudio();
    public abstract IState getState();
    public abstract IInput getInput();
}
