package com.example.engine;
import java.util.Stack;

public class IStateManager {
    private Stack<IScene> scenes;
    private IEngine myEngine;

    public IStateManager(IEngine engine_){
        this.myEngine = engine_;
        this.scenes = new Stack<IScene>();
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
            this.scenes.peek().handleInputs(this.myEngine.getInput(), this.myEngine.getAudio());
    }

    public void addScene(IScene scene) { scenes.push(scene); }

    public void removeScene(int numScenes) {
        int i = 0;
        while (this.scenes.size()>1&& i<numScenes) {
            this.scenes.pop();
            i++;
        }
    }
}
