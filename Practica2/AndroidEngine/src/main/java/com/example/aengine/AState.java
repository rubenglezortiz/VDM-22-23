package com.example.aengine;

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

    public void addScene(AScene scene) { this.scenes.push(scene); }

    public void removeScene(int numScenes) {
        int i = 0;
        while (this.scenes.size()>1&& i<numScenes) {
            this.scenes.pop();
            i++;
        }
    }
}
