package com.example.aengine;

import android.os.Bundle;
import android.transition.Scene;
import android.view.SurfaceView;
import android.view.View;

import java.util.Iterator;
import java.util.Stack;

public class AState  {
    private Stack<AScene> scenes;
    private SurfaceView myView;
    private AndroidEngine myEngine;

    public AState(SurfaceView view, AndroidEngine engine) {
        this.myEngine = engine;
        this.myView = view;
        this.scenes = new Stack<AScene>();
    }

    public void update() {
        if(this.scenes.size() > 0)
            this.scenes.peek().update(this.myEngine);
    }

    public void render() {
        if(this.scenes.size() > 0)
            this.scenes.peek().render(this.myEngine.getGraphics());
    }

    public void handleInputs() {
        if(this.scenes.size() > 0)
            this.scenes.peek().handleInputs(this.myEngine.getGraphics(), this.myEngine.getInput(), this.myEngine.getAudio());
    }

    public void saveScene(Bundle outState){
        outState.putSerializable("scenes", this.scenes);
        for (AScene scene : this.scenes) scene.saveScene(outState);
    }

    public void saveSceneInFile(View myView){
        for (AScene scene : this.scenes) scene.saveSceneInFile(myView);
    }

    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine){
        this.scenes = (Stack<AScene>) savedInstanceState.getSerializable("scenes");
        for (AScene scene : this.scenes) scene.restoreScene(savedInstanceState, engine);
    }

    public void restoreSceneFromFile(View myView) {
        for (AScene scene : this.scenes) scene.restoreSceneFromFile(myView);
    }

    public void addScene(AScene scene) {
        if(this.scenes.size()>0)
            this.scenes.peek().saveSceneInFile(this.myView);
        this.scenes.push(scene);
        this.scenes.peek().restoreSceneFromFile(this.myView);
    }

    public void removeScene(int numScenes) {
        int i = 0;
        while (this.scenes.size()>1&& i<numScenes) { //COMPROBAR SI FUNCIONA CUANDO SE BORRA MÁS DE UNA ESCENA DE GOLPE
            this.scenes.pop().saveSceneInFile(this.myView);
            if(this.scenes.size()>0)
                this.scenes.peek().restoreSceneFromFile(this.myView);
            i++;
        }
    }
}
