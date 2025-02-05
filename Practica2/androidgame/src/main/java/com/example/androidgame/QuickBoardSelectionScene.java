package com.example.androidgame;

import android.os.Bundle;
import android.view.View;

import com.example.aengine.AAudio;
import com.example.aengine.AButton;
import com.example.aengine.AExternal;
import com.example.aengine.AGraphics;
import com.example.aengine.AInput;
import com.example.aengine.AndroidEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class QuickBoardSelectionScene extends HistorySuperScene implements Serializable {
    private boolean changeScene;
    private int boardSize;

    private boolean backToMenu;
    private AButton button3x3;
    private AButton button5x5;
    private AButton button10x10;

    public QuickBoardSelectionScene(AndroidEngine engine_, GameData data){
        super(engine_, data);
        this.changeScene = false;
        this.boardSize = 0;
        this.backToMenu = false;
        createButtons(engine_.getGraphics());
    }

    private void createButtons(AGraphics graphics){
        int x3x, x5x, x10x, y, w, h;
        x3x = graphics.getLogicWidth() / 4;
        x5x = graphics.getLogicWidth() * 2 / 4;
        x10x = graphics.getLogicWidth() * 3 / 4;
        y = graphics.getLogicHeight() / 2;
        w = graphics.getLogicWidth() / 5;
        h = graphics.getLogicHeight() / 10;

        this.button3x3 = graphics.newButton("3x3.png",
                x3x - (w / 2.f), y - (h / 2.f), w, h,
                graphics.newColor(0,0,0,0));

        this.button5x5 = graphics.newButton("5x5.png",
                x5x - (w / 2.f), y - (h / 2.f), w, h,
                graphics.newColor(0,0,0,0));

        this.button10x10 = graphics.newButton("10x10.png",
                x10x - (w / 2.f), y - (h / 2.f), w, h,
                graphics.newColor(0,0,0,0));
    }


    @Override
    protected void setUpScene(AGraphics graphics, AAudio audio) {
        super.setUpScene(graphics, audio);
        createButtons(graphics);
    }

    @Override
    public void update(AndroidEngine engine) {
        super.update(engine);
        if(this.changeScene){
            this.changeScene = false;
            if(this.boardSize == 1) engine.getCurrentState().addScene(new BoardScene(engine, 0, 3,3, this.data));
            else if (this.boardSize == 2) engine.getCurrentState().addScene(new BoardScene(engine, 0, 5,5, this.data));
            else if (this.boardSize == 3) engine.getCurrentState().addScene(new BoardScene(engine,0, 10, 10, this.data));
        }
        if(this.backToMenu){
            engine.getCurrentState().removeScene(1);
        }
    }

    @Override
    public void render(AGraphics graphics) {
        super.render(graphics);
        graphics.drawText(this.font,"LEVEL",graphics.getLogicWidth()/2.0f - ((5*50)/2.0f),
                100, 45, graphics.newColor(0,0,0,255));
        graphics.drawButton(this.returnButton);
        graphics.drawButton(this.button3x3);
        graphics.drawButton(this.button5x5);
        graphics.drawButton(this.button10x10);
    }

    @Override
    public synchronized void handleInputs(AGraphics graphics, AInput input, AAudio audio, AExternal external) {
        super.handleInputs(graphics, input, audio,external);
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) input.getEventList().clone();
        Iterator<AInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            AInput.Event event = it.next();
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = ((AInput.TouchInputEvent) event).x;
                    float collisionY = ((AInput.TouchInputEvent) event).y;
                    if (this.button3x3.checkCollision(graphics, collisionX, collisionY)){
                        this.changeScene = true;
                        this.boardSize = 1;
                    }
                    if (this.button5x5.checkCollision(graphics, collisionX, collisionY)){
                        this.changeScene = true;
                        this.boardSize = 2;
                    }
                    if (this.button10x10.checkCollision(graphics, collisionX, collisionY)){
                        this.changeScene = true;
                        this.boardSize = 3;
                    }
                    if (this.returnButton.checkCollision(graphics, collisionX, collisionY))
                        this.backToMenu = true;
                    break;
                default:
                    break;
            }
        }
        input.clearEventList();
    }

    @Override
    public void saveScene(Bundle outState) {
        outState.putInt("boardSize", this.boardSize);
        outState.putBoolean("changeScene", this.changeScene);
    }

    @Override
    public void saveSceneInFile(View myView) {

    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine_) {
        this.boardSize = savedInstanceState.getInt("boardSize");
        this.changeScene = savedInstanceState.getBoolean("changeScene");
        setUpScene(engine_.getGraphics(), engine_.getAudio());
    }

    @Override
    public void restoreSceneFromFile(View myView) {
    }

}
