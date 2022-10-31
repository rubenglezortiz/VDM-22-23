package com.example.desktopengine;

import com.example.engine.IScene;
import com.example.engine.IState;

public class DState implements IState {
    IScene currScene;
    @Override
    public void update() {
        if(currScene!=null)
            currScene.update();
    }

    @Override
    public void render() {
        if(currScene!=null)
            currScene.render();
    }

    @Override
    public synchronized void handleInputs() {
        if(currScene!=null)
            currScene.handleInputs();
    }

    @Override
    public void setScene(IScene scene) {
        currScene = scene;
    }
}
