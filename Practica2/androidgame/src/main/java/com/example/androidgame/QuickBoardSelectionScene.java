package com.example.androidgame;

import android.os.Bundle;
import android.view.View;

import com.example.aengine.AButton;
import com.example.aengine.AFont;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
import com.example.aengine.AndroidEngine;

import java.util.ArrayList;
import java.util.Iterator;

public class QuickBoardSelectionScene extends AScene {
    private AFont font;
    private AndroidEngine engine;
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
        this.engine = engine_;
        //setUpScene();
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        createButtons();
    }

    private void createButtons(){
        int x, x3x, x5x, x10x, y, w, h, tx, ty, tSize;
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
        x =  this.engine.getGraphics().getLogicWidth() / 7;
        y =  this.engine.getGraphics().getLogicHeight() / 16;
        w = this.engine.getGraphics().getLogicWidth() / 4;
        h = this.engine.getGraphics().getLogicHeight() / 16;
        tx = 10;
        ty = 25;
        tSize = 12;
        this.returnButton = this.engine.getGraphics().newButton("Volver",
                x - (w / 2), y - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));
    }


    @Override
    protected void setUpScene() {

    }

    @Override
    public void update() {
        if(this.changeScene){
            this.changeScene = false;
            if(this.boardSize == 1) this.engine.getCurrentState().addScene(new QuickBoardScene(this.engine,3,3));
            else if (this.boardSize == 2) this.engine.getCurrentState().addScene(new QuickBoardScene(this.engine,5,5));
            else if (this.boardSize == 3) this.engine.getCurrentState().addScene(new QuickBoardScene(this.engine,10,10));
        }
        if(this.backToMenu){
            //saveSceneInFile(this.engine.getSurfaceView());
            this.engine.getCurrentState().removeScene(1);
        }
    }

    @Override
    public void render() {
        this.engine.getGraphics().setFont(this.font);
        this.engine.getGraphics().drawText("LEVEL",this.engine.getGraphics().getLogicWidth()/2.0f - ((5*50)/2.0f),
                100, 45, this.engine.getGraphics().newColor(0,0,0,255));
        this.engine.getGraphics().drawButton(this.returnButton);
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
                    float collisionX = this.engine.getGraphics().realToLogicX(((AInput.TouchInputEvent) event).x);
                    float collisionY = this.engine.getGraphics().realToLogicY(((AInput.TouchInputEvent) event).y);
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
        this.engine.getInput().clearEventList();
    }

    @Override
    public void saveScene(Bundle outState) {
        /*outState.putInt("boardSize", this.boardSize);
        outState.putBoolean("changeScene", this.changeScene);
         */
    }

    @Override
    public void saveSceneInFile(View myView) {

    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
        /*this.boardSize = savedInstanceState.getInt("boardSize");
        this.changeScene = savedInstanceState.getBoolean("changeScene");
        setUpScene();
         */
        this.engine = engine;
    }

    @Override
    public void restoreSceneFromFile(View myView) {

    }

}