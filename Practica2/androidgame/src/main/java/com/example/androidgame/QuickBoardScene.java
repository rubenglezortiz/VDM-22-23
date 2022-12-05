package com.example.androidgame;

import android.os.Bundle;
import android.view.View;

import com.example.aengine.AButton;
import com.example.aengine.AFont;
import com.example.aengine.AImage;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
import com.example.aengine.ASound;
import com.example.aengine.AndroidEngine;

import java.util.ArrayList;
import java.util.Iterator;

public class QuickBoardScene extends AScene {
    private AndroidEngine engine;
    private Board board;
    private AButton backToMenuButton, levelFinishedButton;
    private AImage liveImage, noLiveImage;
    private AFont font;
    private boolean backToMenu, levelFinished, backToSelectionLevelScene;

    private ASound winSound;


    public QuickBoardScene(AndroidEngine engine_, int numRows, int numCols){
        this.engine = engine_;
        this.levelFinished = false;
        this.board = new Board(0,numRows,numCols,this.engine.getGraphics(), this.engine.getAudio());
        setUpScene();
    }

    private void createImages(){
        this.liveImage = this.engine.getGraphics().newImage("corazon_con_vida.png");
        this.noLiveImage = this.engine.getGraphics().newImage("corazon_sin_vida.png");
    }

    private void createSounds() {
        this.winSound = this.engine.getAudio().newSound("win.wav");
        this.engine.getAudio().setVolume(this.winSound, 0.75f);
    }

    private void createButtons(){
        float x,y,w,h;
        x = this.engine.getGraphics().getLogicWidth()/10.0f;
        y = this.engine.getGraphics().getLogicHeight()/10.0f;
        w = this.engine.getGraphics().getLogicWidth() / 5.0f;
        h = this.engine.getGraphics().getLogicHeight() / 15.0f;

        this.backToMenuButton = this.engine.getGraphics().newButton("MENU",
                x - (w / 2), y - (h / 2), w, h,
                10,25, 9,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

        x = (this.engine.getGraphics().getLogicWidth()/2.0f - w/3 );
        this.levelFinishedButton = this.engine.getGraphics().newButton("CONTINUE",
                x, this.engine.getGraphics().getLogicHeight() / 5.0f * 4.0f, w,h,
                4,25,8, this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

    }

    @Override
    protected void setUpScene(){
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        this.backToMenu = this.backToSelectionLevelScene = false;
        createImages();
        createSounds();
        createButtons();
    }

    @Override
    public void update() {
        if(!this.levelFinished && this.board.checkWin() || this.board.getCurrentLives() == 0){ this.levelFinished = true;}
        if(this.backToMenu) this.engine.getCurrentState().removeScene(2);
        if(this.backToSelectionLevelScene) this.engine.getCurrentState().removeScene(1);
    }

    @Override
    public void render() {
        this.board.render();
        this.engine.getGraphics().drawButton(this.backToMenuButton);
        if(this.levelFinished) this.engine.getGraphics().drawButton(this.levelFinishedButton);
        renderLives();
    }

    private void renderLives() {
        float offset = this.engine.getGraphics().getLogicWidth()/10.0f;
        float x = (this.engine.getGraphics().getLogicWidth()/2.0f);
        for(int i = 0; i < this.board.getInitLives() ; i++){
            AImage image = (i < this.board.getCurrentLives()) ? liveImage : noLiveImage;
           this.engine.getGraphics().drawImage(image, (int)(x + offset * (i-1)),
                    this.engine.getGraphics().getLogicHeight()/7.0f*6.0f, this.engine.getGraphics().getLogicWidth()/10.0f,
                    this.engine.getGraphics().getLogicWidth()/10.0f);
        }
    }

    @Override
    public synchronized void handleInputs() {
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) this.engine.getInput().getEventList().clone();
        Iterator<AInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            AInput.Event event = it.next();
            switch (event.type) {
                case TOUCH_PRESSED:
                case LONG_TOUCH:
                    if(!this.levelFinished) this.board.handleInputs(event);
                    break;
                case TOUCH_RELEASED:
                    float collisionX = this.engine.getGraphics().realToLogicX(((AInput.TouchInputEvent) event).x);
                    float collisionY = this.engine.getGraphics().realToLogicY(((AInput.TouchInputEvent) event).y);
                    this.board.handleInputs(event);
                    if(this.backToMenuButton.checkCollision(collisionX, collisionY)) this.backToMenu = true;
                    else if (this.levelFinished && this.levelFinishedButton.checkCollision(collisionX, collisionY))
                        this.backToSelectionLevelScene = true;
                    break;
                default:
                    break;
            }
        }
        this.engine.getInput().clearEventList();
    }

    @Override
    public void saveScene(Bundle outState){
        /*if(outState !=null){
            outState.putSerializable("board", this.board);
            outState.putBoolean("levelFinished", this.levelFinished);
        }

         */
    }

    @Override
    public void saveSceneInFile(View myView) {

    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine){
        if(savedInstanceState!=null){
            this.engine = engine;
            this.board.updateGraphics(this.engine.getGraphics());
            setUpScene();
            //this.board = (Board) savedInstanceState.getSerializable("board");
            //this.levelFinished = savedInstanceState.getBoolean("levelFinished");
        }
    }

    @Override
    public void restoreSceneFromFile(View myView) {

    }
}