package com.example.desktopengine;

import com.example.engine.IScene;
import com.example.engine.IState;
import com.example.logica.Scene1;

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
    public void handleInputs() {
        if(currScene!=null)
            currScene.handleInputs();
    }

    @Override
    public void setScene(IScene scene) {
        currScene = scene;
    }
}
