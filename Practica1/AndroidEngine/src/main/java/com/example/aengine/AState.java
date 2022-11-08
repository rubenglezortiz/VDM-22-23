package com.example.aengine;

import com.example.engine.IScene;
import com.example.engine.IState;

import java.util.Stack;

public class AState implements IState {
    private Stack<IScene> scenes;

    public AState(){
        this.scenes = new Stack<IScene>();
    }

    @Override
    public void update() {
        if(this.scenes.size() > 0)
            this.scenes.peek().update();
    }

    @Override
    public void render() {
        if(this.scenes.size() > 0)
            this.scenes.peek().render();
    }

    @Override
    public void handleInputs() {
        if(this.scenes.size() > 0)
            this.scenes.peek().handleInputs();
    }

    @Override
    public void addScene(IScene scene) { this.scenes.push(scene); }

    @Override
    public void removeScene(int numScenes) {
        int i = 0;
        while (this.scenes.size()>1&& i<numScenes) {
            this.scenes.pop();
            i++;
        }
    }
}
