package com.example.androidgame;

import android.os.Bundle;

import com.example.aengine.AButton;
import com.example.aengine.AFont;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
import com.example.aengine.ASound;
import com.example.aengine.AndroidEngine;

import java.util.ArrayList;
import java.util.Iterator;

public class RandomBoardScene extends AScene {
    private AndroidEngine engine;
    private Board board;
    private AButton backToMenuButton;
    private AFont font;
    private boolean backToMenu;

    private ASound winSound;


    public RandomBoardScene(AndroidEngine engine_, int numRows, int numCols){
        this.engine = engine_;
        this.board = new Board(0,numRows,numCols,this.engine.getGraphics(), this.engine.getAudio());
        setUpScene();
    }

    private void createButtons(){
        float x,y,w,h;
        x = 50;
        y = 0;
        w = this.engine.getGraphics().getLogicWidth() / 5;
        h = this.engine.getGraphics().getLogicHeight() / 15;

        this.backToMenuButton = this.engine.getGraphics().newButton("Volver",
                x - (w / 2), y - (h / 2), w, h,
                6,25, 9,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

    }

    private void createSounds() {
        this.winSound = this.engine.getAudio().newSound("win.wav");
        this.engine.getAudio().setVolume(this.winSound, 0.75f);
    }

    @Override
    protected void setUpScene(){
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        this.backToMenu = false;
        createButtons();
        createSounds();
    }

    @Override
    public void update() {
        if(this.board.checkWin()){
         // añadir botón pa volver etc etc etc
        }
        if(this.backToMenu) this.engine.getCurrentState().removeScene(2);
    }

    @Override
    public void render() {
        this.board.render();
        this.engine.getGraphics().drawButton(this.backToMenuButton);
        String livesText = "Remaining Lives: " + this.board.getLives();
        this.engine.getGraphics().drawText(this.font,  livesText,this.engine.getGraphics().getLogicWidth()/5.0f,
                this.engine.getGraphics().getLogicHeight(),15, this.engine.getGraphics().newColor(200,0,200,255));
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
                    this.board.handleInputs(event);
                    break;
                case TOUCH_RELEASED:
                    this.board.handleInputs(event);
                    if(this.board.getLives()==0 || this.backToMenuButton.checkCollision(
                        this.engine.getGraphics().realToLogicX(((AInput.TouchInputEvent)event).x),
                        this.engine.getGraphics().realToLogicY(((AInput.TouchInputEvent)event).y)))
                            this.backToMenu = true;
                    break;
                default:
                    break;
            }
        }
        this.engine.getInput().clearEventList();
    }

    @Override
    public void saveScene(Bundle outState){
        if(outState !=null){
            outState.putSerializable("board", this.board);
        }
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine){
        if(savedInstanceState!=null){
            this.engine = engine;
            this.board = (Board) savedInstanceState.getSerializable("board");
            this.board.updateGraphics(this.engine.getGraphics());
            setUpScene();
        }
    }
}