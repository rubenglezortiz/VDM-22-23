package com.example.aengine;

import com.example.engine.IScene;
import com.example.engine.IState;

import java.util.Stack;

public class AState implements IState {
    private Stack<IScene> scenes;

    public AState(){
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
    public void handleInputs() {
       // if(scenes.size() > 0)
         //   scenes.peek().handleInputs();
    }

    @Override
    public void addScene(IScene scene) { scenes.push(scene); }

    @Override
    public void removeScene(int numScenes) {
        int i = 0;
        while (scenes.size()>1&& i<numScenes) {
            scenes.pop();
            i++;
        }
    }
}
