package com.example.androidgame;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.aengine.AAudio;
import com.example.aengine.AButton;
import com.example.aengine.AExternal;
import com.example.aengine.AGraphics;
import com.example.aengine.AInput;
import com.example.aengine.AndroidEngine;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

public class BoardScene extends HistorySuperScene implements Serializable {
    private Board board;
    private AButton levelFinishedButton, lifeAdButton;
    private String liveImage, noLiveImage;
    private boolean backToMenu, levelFinished, backToSelectionLevelScene, isHorizontal;
    private int levelId, categoryId;
    private String winSound, boardInProgressFile;

    public BoardScene(AndroidEngine engine_, int id_, int category, int numCols, int numRows, GameData data){
        super(engine_, data);
        this.winSound = "win.wav";
        this.boardInProgressFile = "board";
        this.liveImage = "corazon_con_vida.png";
        this.noLiveImage = "corazon_sin_vida.png";
        this.levelFinished = false;
        this.levelId = id_;
        this.categoryId = category;
        if(this.levelId!=0) createHistoryBoard(engine_, numCols, numRows);
        else createRandomBoard(engine_, numCols, numRows);
        this.board.setCellColor(this.palettes[this.data.actPalette][1]);
        setUpScene(engine_.getGraphics(), engine_.getAudio());
    }

    private void createHistoryBoard(AndroidEngine engine, int numCols, int numRows) {
        if (this.levelId == this.data.levelInProgress)
            restoreSceneFromFile(engine.getSurfaceView());
        else{
            this.data.levelInProgress = this.levelId;
            this.board = new Board(this.levelId, numCols, numRows, engine);
        }
    }

    private void createRandomBoard(AndroidEngine engine, int numCols, int numRows) {
        this.board = new Board(0, numCols, numRows, engine);
    }

    private void createImages(AGraphics graphics){
        graphics.newImage(this.liveImage);
        graphics.newImage(this.noLiveImage);
    }

    private void createSounds(AAudio audio) {
        audio.newSound(this.winSound);
        audio.setVolume(this.winSound, 0.75f);
    }

    private void createButtons(AGraphics graphics){
        float x,y,w,h;
        y = graphics.getLogicHeight() / 5.0f * 4.0f;
        w = graphics.getLogicWidth() / 5.0f;
        h = graphics.getLogicHeight() / 15.0f;

        x = (graphics.getLogicWidth()/2.0f - w/3 );
        this.levelFinishedButton = graphics.newButton("Continuar.png",
                x, y, w,h,
                graphics.newColor(0,0,0,0));
        this.lifeAdButton = graphics.newButton("Continuar.png",
                x, y, w,h,
                graphics.newColor(0,0,0,0));

    }

    @Override
    protected void setUpScene(AGraphics graphics, AAudio audio){
        super.setUpScene(graphics, audio);
        this.backToMenu = this.backToSelectionLevelScene = false;
        createImages(graphics);
        createSounds(audio);
        createButtons(graphics);

        this.isHorizontal = graphics.getHorizontallyScaled();
        if (this.isHorizontal) changeToHorizontal(graphics);
    }

    @Override
    public void update(AndroidEngine engine) {
        boolean win = this.board.checkWin();
        if(!this.levelFinished && win || this.board.getCurrentLives() == 0){
            this.data.levelInProgress = 0;
            this.levelFinished = true;
            if (this.levelId != 0 && win){
                if (this.levelId == this.data.forestLevels){
                    this.data.forestLevels++;
                    this.data.coins += 5;
                }
                else if (this.levelId - 20 == this.data.seaLevels){
                    this.data.seaLevels++;
                    this.data.coins += 5;
                }
                else if (this.levelId - 40 == this.data.cityLevels){
                    this.data.cityLevels++;
                    this.data.coins += 5;
                }
                else if (this.levelId - 60 == this.data.desertLevels){
                    this.data.desertLevels++;
                    this.data.coins += 5;
                }
            }
        }
        if(this.backToMenu) {
            engine.getCurrentState().removeScene(2);
        }
        if(this.backToSelectionLevelScene) engine.getCurrentState().removeScene(1);
    }

    @Override
    public void render(AGraphics graphics) {
        super.render(graphics);

        if (this.board.getCurrentLives()<this.board.getInitLives()&&!this.levelFinished)
            graphics.drawButton(this.lifeAdButton);
        graphics.drawButton(this.returnButton);
        this.board.render(graphics);
        if(this.levelFinished) graphics.drawButton(this.levelFinishedButton);
        renderLives(graphics);
    }

    private void renderLives(AGraphics graphics) {
        float offset = graphics.getLogicWidth()/10.0f;
        float x = (graphics.getLogicWidth()/2.0f);
        for(int i = 0; i < this.board.getInitLives() ; i++){
            String key = (i < this.board.getCurrentLives()) ? this.liveImage : this.noLiveImage;
            graphics.drawImage(key, (int)(x + offset * (i-1)),
                    graphics.getLogicHeight()/7.0f*6.0f,
                    graphics.getLogicWidth()/10.0f,
                    graphics.getLogicWidth()/10.0f);
        }
    }

    private void changeToHorizontal(AGraphics graphics){

    }

    private void changeToVertical(AGraphics graphics){

    }

    @Override
    public synchronized void handleInputs(AInput input, AAudio audio, AExternal external) {
        super.handleInputs(input, audio, external);
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) input.getEventList().clone();
        Iterator<AInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            AInput.Event event = it.next();
            switch (event.type) {
                case TOUCH_PRESSED:
                case LONG_TOUCH:
                    if(!this.levelFinished) this.board.handleInputs(event,audio);
                    break;
                case TOUCH_RELEASED:
                    float collisionX = ((AInput.TouchInputEvent) event).x;
                    float collisionY = ((AInput.TouchInputEvent) event).y;
                    this.board.handleInputs(event, audio);
                    if(this.returnButton.checkCollision(collisionX, collisionY)) this.backToMenu = true;
                    else if(this.lifeAdButton.checkCollision(collisionX, collisionY) && this.board.getCurrentLives() < this.board.getInitLives()&&!this.levelFinished) {
                        external.loadRewardedAd();
                        this.board.gainLife();
                    }
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
        super.saveScene(outState);
        if(outState !=null){
            outState.putSerializable("board", this.board);
            outState.putBoolean("levelFinished", this.levelFinished);
        }
    }

    @Override
    public void saveSceneInFile(View myView) {
        super.saveSceneInFile(myView);
        Gson gson = new Gson();
        String aux = gson.toJson(this.board);

        try(FileOutputStream fos = myView.getContext().openFileOutput(this.boardInProgressFile, Context.MODE_PRIVATE)){
            fos.write(aux.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine_){
        super.restoreScene(savedInstanceState, engine_);
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        super.restoreSceneFromFile(myView);
        Gson gson = new Gson();
        try {
            FileInputStream fis = myView.getContext().openFileInput(this.boardInProgressFile);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            String line = reader.readLine();
            this.board = gson.fromJson(line, Board.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

}