package com.example.desktopengine;

import com.example.engine.IState;
import com.example.logica.Scene1;

public class DState implements IState {
    IState currState;
    @Override
    public void update() {
        if(currState!=null)
            currState.update();
    }

    @Override
    public void render() {
        if(currState!=null)
            currState.update();
    }

    @Override
    public void handleInputs() {

    }

    @Override
    public void setScene(IState scene) {
        currState = scene;
    }
}
