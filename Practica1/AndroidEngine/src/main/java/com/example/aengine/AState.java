package com.example.aengine;

import android.view.Surface;
import android.view.SurfaceView;

import com.example.engine.IScene;
import com.example.engine.IState;

import java.util.Stack;

public class AState implements IState {
    private Stack<IScene> scenes;
    private SurfaceView myView; //POR SI SE NECESITA PARA LA PERSISTENCIA
    private AndroidEngine myEngine;

    public AState(SurfaceView view, AndroidEngine engine) {
        this.myEngine = engine;
        this.myView = view;
        this.scenes = new Stack<>();
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
