package com.example.logica;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IInput;
import com.example.engine.IScene;

import java.util.ArrayList;
import java.util.Iterator;

import jdk.tools.jmod.Main;

public class TitleScene implements IScene {
    private IFont font;
    private IEngine engine;
    public TitleScene(IEngine engine_){
        this.engine = engine_;
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        engine.getState().addScene(this);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        this.engine.getGraphics().setFont(this.font);
        this.engine.getGraphics().drawText("NONOGRAMS",this.engine.getGraphics().getWidth()/2 - ((10*50)/2),
                100, 50, this.engine.getGraphics().newColor(0,0,0,255));
    }

    @Override
    public void handleInputs() {
        ArrayList<IInput.Event> eventList = this.engine.getInput().getEventList();
        Iterator<IInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.Event event = it.next();
            switch (event.type) {
                case KEY_DOWN:
                    MainScene mainScene = new MainScene(this.engine, 3,3);
                    break;
                default:
                    break;
            }
        }
        this.engine.getInput().clearEventList();
    }
}
