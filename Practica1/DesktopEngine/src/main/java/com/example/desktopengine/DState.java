package com.example.desktopengine;

import com.example.engine.IScene;
import com.example.engine.IState;

import java.util.Stack;

public class DState implements IState {
    private Stack<IScene> scenes;
    private DesktopEngine myEngine;

    public DState(DesktopEngine engine_){
        this.myEngine = engine_;
        this.scenes = new Stack<IScene>();
    }

    @Override
    public void update() {
        if(this.scenes.size() > 0)
            this.scenes.peek().update(this.myEngine);
    }

    @Override
    public void render() {
        if(this.scenes.size() > 0)
            this.scenes.peek().render(this.myEngine.getGraphics());
    }

    @Override
    public void handleInputs() {
        if(this.scenes.size() > 0)
            this.scenes.peek().handleInputs(this.myEngine.getInput(), this.myEngine.getAudio());
    }

    @Override
    public void addScene(IScene scene) { scenes.push(scene); }

    @Override
    public void removeScene(int numScenes) {
        int i = 0;
        while (this.scenes.size()>1&& i<numScenes) {
            this.scenes.pop();
            i++;
        }
    }
}
