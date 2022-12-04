package com.example.androidgame;

import android.os.Bundle;

import com.example.aengine.AButton;
import com.example.aengine.AFont;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
import com.example.aengine.AndroidEngine;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardSelectionScene extends AScene {
    private AFont font;
    private AndroidEngine engine;
    private boolean changeScene;
    private int boardSize;

    private AButton button3x3;
    private AButton button5x5;
    private AButton button10x10;

    public BoardSelectionScene(AndroidEngine engine_){
        this.changeScene = false;
        this.boardSize = 0;
        this.engine = engine_;
        setUpScene();
    }

    private void createButtons(){
        int x3x, x5x, x10x, y, w, h, tx, ty, tSize;
        x3x = this.engine.getGraphics().getLogicWidth() / 4;
        x5x = this.engine.getGraphics().getLogicWidth() * 2 / 4;
        x10x = this.engine.getGraphics().getLogicWidth() * 3 / 4;
        y = this.engine.getGraphics().getLogicHeight() / 2;
        w = this.engine.getGraphics().getLogicWidth() / 5;
        h = this.engine.getGraphics().getLogicHeight() / 10;
        ty = 35;
        tSize = 15;

        this.button3x3 = this.engine.getGraphics().newButton("3x3",
                x3x - (w / 2), y - (h / 2), w, h,
                18,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

        this.button5x5 = this.engine.getGraphics().newButton("5x5",
                x5x - (w / 2), y - (h / 2), w, h,
                18,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

        this.button10x10 = this.engine.getGraphics().newButton("10x10",
                x10x - (w / 2), y - (h / 2), w, h,
                7,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));
    }

    @Override
    protected void setUpScene() {
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        createButtons();
    }

    @Override
    public void update() {
        if(this.changeScene){
            this.changeScene = false;
            if(this.boardSize == 1) this.engine.getCurrentState().addScene(new RandomBoardScene(this.engine,3,3));
            else if (this.boardSize == 2) this.engine.getCurrentState().addScene(new RandomBoardScene(this.engine,5,5));
            else if (this.boardSize == 3) this.engine.getCurrentState().addScene(new RandomBoardScene(this.engine,10,10));
        }
    }

    @Override
    public void render() {
        this.engine.getGraphics().setFont(this.font);
        this.engine.getGraphics().drawText("LEVEL",this.engine.getGraphics().getLogicWidth()/2.0f - ((5*50)/2.0f),
                100, 45, this.engine.getGraphics().newColor(0,0,0,255));
        this.engine.getGraphics().drawButton(this.button3x3);
        this.engine.getGraphics().drawButton(this.button5x5);
        this.engine.getGraphics().drawButton(this.button10x10);
    }

    @Override
    public synchronized void handleInputs() {
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) this.engine.getInput().getEventList().clone();
        Iterator<AInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            AInput.Event event = it.next();
            switch (event.type) {
                case TOUCH_RELEASED:
                    if(((AInput.TouchInputEvent)event).mouseButton == 1){
                        if (this.button3x3.checkCollision(this.engine.getGraphics().realToLogicX(((AInput.TouchInputEvent)event).x),
                                this.engine.getGraphics().realToLogicY(((AInput.TouchInputEvent)event).y))){
                            this.changeScene = true;
                            this.boardSize = 1;
                        }
                        if (this.button5x5.checkCollision(this.engine.getGraphics().realToLogicX(((AInput.TouchInputEvent)event).x),
                                this.engine.getGraphics().realToLogicY(((AInput.TouchInputEvent)event).y))){
                            this.changeScene = true;
                            this.boardSize = 2;
                        }
                        if (this.button10x10.checkCollision(this.engine.getGraphics().realToLogicX(((AInput.TouchInputEvent)event).x),
                                this.engine.getGraphics().realToLogicY(((AInput.TouchInputEvent)event).y))){
                            this.changeScene = true;
                            this.boardSize = 3;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        this.engine.getInput().clearEventList();
    }

    @Override
    public void saveScene(Bundle outState) {
        outState.putInt("boardSize", this.boardSize);
        outState.putBoolean("changeScene", this.changeScene);
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
        this.boardSize = savedInstanceState.getInt("boardSize");
        this.changeScene = savedInstanceState.getBoolean("changeScene");
        this.engine = engine;
        setUpScene();
    }

}