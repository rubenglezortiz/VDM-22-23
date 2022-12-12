package com.example.androidgame;

import android.os.Bundle;
import android.view.View;

import com.example.aengine.AAudio;
import com.example.aengine.AButton;
import com.example.aengine.AFont;
import com.example.aengine.AGraphics;
import com.example.aengine.AImage;
import com.example.aengine.AInput;
import com.example.aengine.ASound;
import com.example.aengine.AndroidEngine;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardScene extends HistorySuperScene {
    private Board board;
    private AButton backToMenuButton, levelFinishedButton;
    private AImage liveImage, noLiveImage;
    private AFont font;
    private boolean backToMenu, levelFinished, backToSelectionLevelScene;
    private int levelId;

    private ASound winSound;


    public BoardScene(AndroidEngine engine, int id_, int numCols, int numRows){
        super(engine.getGraphics());
        this.levelFinished = false;
        this.levelId = id_;
        this.board = new Board(this.levelId,numCols,numRows, engine);
        setUpScene(engine.getGraphics(), engine.getAudio());
    }

    private void createImages(AGraphics graphics){
        this.liveImage = graphics.newImage("corazon_con_vida.png");
        this.noLiveImage = graphics.newImage("corazon_sin_vida.png");
    }

    private void createSounds(AAudio audio) {
        this.winSound = audio.newSound("win.wav");
        audio.setVolume(this.winSound, 0.75f);
    }

    private void createButtons(AGraphics graphics){
        float x,y,w,h;
        x = graphics.getLogicWidth()/10.0f;
        y = graphics.getLogicHeight()/10.0f;
        w = graphics.getLogicWidth() / 5.0f;
        h = graphics.getLogicHeight() / 15.0f;

        this.backToMenuButton = graphics.newButton("MENU",
                x - (w / 2), y - (h / 2), w, h,
                10,25, 9,
                this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(255, 255, 255, 255));

        x = (graphics.getLogicWidth()/2.0f - w/3 );
        this.levelFinishedButton = graphics.newButton("CONTINUE",
                x, graphics.getLogicHeight() / 5.0f * 4.0f, w,h,
                4,25,8, this.font,
                graphics.newColor(0, 0, 0, 255),
                graphics.newColor(255, 255, 255, 255));

    }

    protected void setUpScene(AGraphics graphics, AAudio audio){
        this.backToMenu = this.backToSelectionLevelScene = false;
        createImages(graphics);
        createSounds(audio);
        createButtons(graphics);
    }

    @Override
    public void update(AndroidEngine engine) {
        if(!this.levelFinished && this.board.checkWin() || this.board.getCurrentLives() == 0){
            this.levelFinished = true;

            if (this.levelId != 0){
                if (this.levelId <= 20)      this.forestLevels++;
                else if (this.levelId <= 40) this.seaLevels++;
                else if (this.levelId <= 60) this.cityLevels++;
                else                    this.desertLevels++;
            }
        }
        if(this.backToMenu) engine.getCurrentState().removeScene(2);
        if(this.backToSelectionLevelScene) engine.getCurrentState().removeScene(1);
    }

    @Override
    public void render(AGraphics graphics) {
        super.render(graphics);
        this.board.render();
        graphics.drawButton(this.backToMenuButton);
        if(this.levelFinished) graphics.drawButton(this.levelFinishedButton);
        renderLives(graphics);
    }

    private void renderLives(AGraphics graphics) {
        float offset = graphics.getLogicWidth()/10.0f;
        float x = (graphics.getLogicWidth()/2.0f);
        for(int i = 0; i < this.board.getInitLives() ; i++){
            AImage image = (i < this.board.getCurrentLives()) ? liveImage : noLiveImage;
            graphics.drawImage(image, (int)(x + offset * (i-1)),
                    graphics.getLogicHeight()/7.0f*6.0f, graphics.getLogicWidth()/10.0f,
                    graphics.getLogicWidth()/10.0f);
        }
    }

    @Override
    public synchronized void handleInputs(AGraphics graphics, AInput input) {
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) input.getEventList().clone();
        Iterator<AInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            AInput.Event event = it.next();
            switch (event.type) {
                case TOUCH_PRESSED:
                case LONG_TOUCH:
                    if(!this.levelFinished) this.board.handleInputs(event);
                    break;
                case TOUCH_RELEASED:
                    float collisionX = graphics.realToLogicX(((AInput.TouchInputEvent) event).x);
                    float collisionY = graphics.realToLogicY(((AInput.TouchInputEvent) event).y);
                    this.board.handleInputs(event);
                    if(this.backToMenuButton.checkCollision(collisionX, collisionY)) this.backToMenu = true;
                    else if (this.levelFinished && this.levelFinishedButton.checkCollision(collisionX, collisionY))
                        this.backToSelectionLevelScene = true;
                    break;
                default:
                    break;
            }
        }
        input.clearEventList();
    }

    @Override
    public void saveScene(Bundle outState){
        if(outState !=null){
            outState.putSerializable("board", this.board);
            outState.putBoolean("levelFinished", this.levelFinished);
        }


    }

    @Override
    public void saveSceneInFile(View myView) {
        super.saveSceneInFile(myView);
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine){
        super.restoreScene(savedInstanceState, engine);
        /*if(savedInstanceState!=null){
            this.engine = engine;
            this.board.updateGraphics(this.engine.getGraphics());
            setUpScene();
            //this.board = (Board) savedInstanceState.getSerializable("board");
            //this.levelFinished = savedInstanceState.getBoolean("levelFinished");
        }
         */
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        super.restoreSceneFromFile(myView);
        this.board.setCellColor(this.palettes[this.actPalette][1]);
    }
}