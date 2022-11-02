package com.example.logica;

import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IInput;
import com.example.engine.IScene;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardSelectionScene implements IScene {

    private IFont font;
    private IEngine engine;
    public BoardSelectionScene(IEngine engine_){
        this.engine = engine_;
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        this.engine.getGraphics().setFont(this.font);
        this.engine.getGraphics().drawText("LEVEL",this.engine.getGraphics().getLogicWidth()/2.0f - ((5*50)/2.0f),
                100, 50, this.engine.getGraphics().newColor(0,0,0,255));
        this.engine.getGraphics().drawText("1 = tablero 3x3\n 2 = tablero 5x5 \n 3 = tablero 10x10",
                this.engine.getGraphics().getLogicWidth()/2.0f-((15*10*3)/2.0f),  200, 10,
                this.engine.getGraphics().newColor(255,0,255,255));
    }

    @Override
    public synchronized void handleInputs() {
        ArrayList<IInput.Event> eventList = this.engine.getInput().getEventList();
        Iterator<IInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.Event event = it.next();
            switch (event.type) {
                case KEY_DOWN:
                    int boardSize;
                    if(((IInput.KeyInputEvent)event).key == '1')
                        this.engine.getCurrentState().addScene(new MainScene(this.engine,3,3));
                    else if (((IInput.KeyInputEvent)event).key == '2')
                        this.engine.getCurrentState().addScene(new MainScene(this.engine,5,5));
                    else if (((IInput.KeyInputEvent)event).key == '3')
                        this.engine.getCurrentState().addScene(new MainScene(this.engine,10,10));
                    break;
                default:
                    break;
            }
        }
        this.engine.getInput().clearEventList();
    }
}
