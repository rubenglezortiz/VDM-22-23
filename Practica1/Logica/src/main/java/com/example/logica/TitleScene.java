package com.example.logica;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IInput;
import com.example.engine.IScene;

import java.util.ArrayList;
import java.util.Iterator;

public class TitleScene implements IScene {
    private IGraphics graphics;
    private IInput input;
    private IFont font;

    public TitleScene(IEngine engine){
        this.graphics = engine.getGraphics();
        this.input = engine.getInput();
        this.font = graphics.newFont("font.TTF", false);
        engine.getState().setScene(this);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        this.graphics.setFont(this.font);
        this.graphics.drawText("NONOGRAMS",this.graphics.getWidth()/2 - ((10*50)/2), 100, 50, this.graphics.newColor(0,0,0,255));
    }

    @Override
    public void handleInputs() {
        ArrayList<IInput.Event> eventList = this.input.getEventList();
        Iterator<IInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.Event event = it.next();
            switch (event.type) {
                default:
                    break;
            }
        }
        this.input.clearEventList();
    }
}
