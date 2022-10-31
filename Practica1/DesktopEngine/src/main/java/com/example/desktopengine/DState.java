package com.example.desktopengine;

import com.example.engine.IScene;
import com.example.engine.IState;

import java.util.Stack;

public class DState implements IState {
    private Stack<IScene> scenes;

    public DState(){
        scenes = new Stack<IScene>();
    }

    @Override
    public void update() {
        if(scenes.size() > 0)
            scenes.peek().update();
    }

    @Override
    public void render() {
        if(scenes.size() > 0)
            scenes.peek().render();
    }

    @Override
    public synchronized void handleInputs() {
        if(scenes.size() > 0)
            scenes.peek().handleInputs();
    }

    @Override
    public void addScene(IScene scene) {
        scenes.push(scene);
    }

    @Override
    public void removeScene() {
        scenes.pop();
    }
}
