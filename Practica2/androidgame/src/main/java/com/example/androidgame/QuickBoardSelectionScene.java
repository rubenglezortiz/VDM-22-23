package com.example.androidgame;

import android.os.Bundle;
import android.view.View;

import com.example.aengine.AButton;
import com.example.aengine.AFont;
import com.example.aengine.AGraphics;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
import com.example.aengine.AndroidEngine;

import java.util.ArrayList;
import java.util.Iterator;

public class QuickBoardSelectionScene extends AScene {
    private AFont font;
    private boolean changeScene;
    private int boardSize;

    private boolean backToMenu;
    private AButton returnButton;
    private AButton button3x3;
    private AButton button5x5;
    private AButton button10x10;

    public QuickBoardSelectionScene(AndroidEngine engine_){
        this.changeScene = false;
        this.boardSize = 0;
        this.backToMenu = false;
        //setUpScene();
        createButtons(engine_.getGraphics());
    }

    private void createButtons(AGraphics graphics){
        int x, x3x, x5x, x10x, y, w, h, tx, ty, tSize;
        x3x = graphics.getLogicWidth() / 4;
        x5x = graphics.getLogicWidth() * 2 / 4;
        x10x = graphics.getLogicWidth() * 3 / 4;
        y = graphics.getLogicHeight() / 2;
        w = graphics.getLogicWidth() / 5;
        h = graphics.getLogicHeight() / 10;
        ty = 35;
        tSize = 15;

        this.button3x3 = graphics.newButton("3x3",
                x3x - (w / 2), y - (h / 2), w, h,
                18,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(255, 255, 255, 255));

        this.button5x5 = graphics.newButton("5x5",
                x5x - (w / 2), y - (h / 2), w, h,
                18,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(255, 255, 255, 255));

        this.button10x10 = graphics.newButton("10x10",
                x10x - (w / 2), y - (h / 2), w, h,
                7,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(255, 255, 255, 255));
        x =  graphics.getLogicWidth() / 7;
        y =  graphics.getLogicHeight() / 16;
        w = graphics.getLogicWidth() / 4;
        h = graphics.getLogicHeight() / 16;
        tx = 10;
        ty = 25;
        tSize = 12;
        this.returnButton = graphics.newButton("Volver",
                x - (w / 2), y - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(255, 255, 255, 255));
    }


    protected void setUpScene() {

    }

    @Override
    public void update(AndroidEngine engine) {
        if(this.changeScene){
            this.changeScene = false;
            if(this.boardSize == 1) engine.getCurrentState().addScene(new QuickBoardScene(engine, 0,3,3));
            else if (this.boardSize == 2) engine.getCurrentState().addScene(new QuickBoardScene(engine, 0,5,5));
            else if (this.boardSize == 3) engine.getCurrentState().addScene(new QuickBoardScene(engine,0, 10,10));
        }
        if(this.backToMenu){
            engine.getCurrentState().removeScene(1);
        }
    }

    @Override
    public void render(AGraphics graphics) {
        //graphics.setFont(this.font);
        graphics.drawText("LEVEL",graphics.getLogicWidth()/2.0f - ((5*50)/2.0f),
                100, 45, graphics.newColor(0,0,0,255));
        graphics.drawButton(this.returnButton);
        graphics.drawButton(this.button3x3);
        graphics.drawButton(this.button5x5);
        graphics.drawButton(this.button10x10);
    }

    @Override
    public synchronized void handleInputs(AGraphics graphics, AInput input) {
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) input.getEventList().clone();
        Iterator<AInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            AInput.Event event = it.next();
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = graphics.realToLogicX(((AInput.TouchInputEvent) event).x);
                    float collisionY = graphics.realToLogicY(((AInput.TouchInputEvent) event).y);
                    if (this.button3x3.checkCollision(collisionX, collisionY)){
                        this.changeScene = true;
                        this.boardSize = 1;
                    }
                    if (this.button5x5.checkCollision(collisionX, collisionY)){
                        this.changeScene = true;
                        this.boardSize = 2;
                    }
                    if (this.button10x10.checkCollision(collisionX, collisionY)){
                        this.changeScene = true;
                        this.boardSize = 3;
                    }
                    if (this.returnButton.checkCollision(collisionX, collisionY))
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
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
        /*this.boardSize = savedInstanceState.getInt("boardSize");
        this.changeScene = savedInstanceState.getBoolean("changeScene");
        setUpScene();

        this.engine = engine;
        */
    }

    @Override
    public void restoreSceneFromFile(View myView) {

    }

}
