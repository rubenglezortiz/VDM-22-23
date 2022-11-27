package com.example.aengine;

import android.os.Bundle;
import android.transition.Scene;

import java.util.Iterator;
import java.util.Stack;

public class AState  {
    private Stack<AScene> scenes;

    public AState(){
        this.scenes = new Stack<AScene>();
    }

    public void update() {
        if(this.scenes.size() > 0)
            this.scenes.peek().update();
    }

    public void render() {
        if(this.scenes.size() > 0)
            this.scenes.peek().render();
    }

    public void handleInputs() {
        if(this.scenes.size() > 0)
            this.scenes.peek().handleInputs();
    }

    public void saveScene(Bundle outState){
        outState.putSerializable("scenes", this.scenes);
        Iterator<AScene> it = this.scenes.iterator();
        while(it.hasNext())
           it.next().saveScene(outState);
    }

    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine){
        this.scenes = (Stack<AScene>) savedInstanceState.getSerializable("scenes");
        Iterator<AScene> it = this.scenes.iterator();
        while(it.hasNext())
            it.next().restoreScene(savedInstanceState, engine);
    }


    public void addScene(AScene scene) { this.scenes.push(scene); }

    public void removeScene(int numScenes) {
        int i = 0;
        while (this.scenes.size()>1&& i<numScenes) {
            this.scenes.pop();
            i++;
        }
    }
}
