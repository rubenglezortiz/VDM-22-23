package com.example.engine;

public interface IState {
    public void update();
    public void render();
    public void handleInputs();
    public void setScene(IState scene);
}
