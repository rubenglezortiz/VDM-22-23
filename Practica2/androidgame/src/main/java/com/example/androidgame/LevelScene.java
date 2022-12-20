package com.example.androidgame;

import android.os.Bundle;
import android.view.View;
import com.example.aengine.AAudio;
import com.example.aengine.AButton;
import com.example.aengine.AColor;
import com.example.aengine.AExternal;
import com.example.aengine.AGraphics;
import com.example.aengine.AInput;
import com.example.aengine.AndroidEngine;
import java.io.Serializable;
import java.util.ArrayList;

public class LevelScene extends HistorySuperScene implements Serializable {
    private int changeScene;
    private boolean backToMenu;
    private final String TAG = "LevelScene";
    private AButton[][] levels;

    private int categoryId;
    private int currentLevel;
    private String textId;
    private int rows, cols;

    public LevelScene(AndroidEngine engine_, int id_, GameData data){
        super(engine_.getGraphics(), data);
        this.backToMenu = false;
        this.changeScene = 0;
        this.rows = 5;
        this.cols = 4;

        this.categoryId = id_;
        setUpScene(engine_.getGraphics(), engine_.getAudio());
    }


    private void createButtons(AGraphics graphics){
        this.levels = new AButton[this.rows][this.cols]; //[y][x] : [i][j]

        int x0,y0, xi, yi, w,h;
        x0 = graphics.getLogicWidth() / 5;
        y0 = graphics.getLogicHeight() / 4;
        xi = graphics.getLogicWidth() / 5;
        yi = xi;
        w = graphics.getLogicWidth() / 6;
        h = w;

        int x, y;
        String text;
        AColor unlockedLevelColor = graphics.newColor(250, 215, 160, 255);
        AColor lockedLevelColor = graphics.newColor(100, 65, 10, 255);
        for(int i = 0; i < this.rows; ++i){
            y = y0 + (yi * i);
            for(int j = 0; j < this.cols; ++j){
                x = x0 + (xi * j);
                int level = i * this.cols + j + 1;
                text = "Level" + Integer.toString(level) + ".png";
                AColor color = (this.currentLevel>=level) ?  unlockedLevelColor : lockedLevelColor;
                this.levels[i][j] = graphics.newButton(text,
                        x - (w / 2), y - (h / 2), w, h,
                        color);
            }
        }
    }
    @Override
    protected void setUpScene(AGraphics graphics, AAudio audio) {
        super.setUpScene(graphics, audio);
        if(this.categoryId == 0) {
            this.textId = "Bosque";
            this.currentLevel = this.data.forestLevels;
        }
        else if (this.categoryId == 1){
            this.textId = "Mar";
            this.currentLevel = this.data.seaLevels;
        }
        else if (this.categoryId == 2) {
            this.textId = "Ciudad";
            this.currentLevel = this.data.cityLevels;
        }
        else {
            this.textId = "Desierto";
            this.currentLevel = this.data.desertLevels;
        }
        createButtons(graphics);
    }

    @Override
    public void update(AndroidEngine engine){
        super.update(engine);
        if (this.changeScene != 0){
            BoardScene level = new BoardScene(engine, this.changeScene, this.categoryId, 0,0, this.data);
            engine.getCurrentState().addScene(level);
            this.changeScene = 0;
        }
        if(this.backToMenu) engine.getCurrentState().removeScene(1);
    }

    @Override
    public void render(AGraphics graphics){
        super.render(graphics);
        graphics.drawText(this.textId,
                graphics.getLogicWidth() / 2.0f - 90,
                graphics.getLogicHeight() / 8.0f, 25,
                graphics.newColor(0,0,0,255));

        graphics.drawButton(this.returnButton);

        for (int i = 0; i < this.rows; ++i){
            for (int j = 0; j < this.cols; ++j){
                graphics.drawButton(this.levels[i][j]);
            }
        }
        graphics.drawText(Integer.toString(this.data.forestLevels), 300,100, 20, graphics.newColor(0,0,0,255));
    }

    @Override
    public void handleInputs(AGraphics graphics, AInput input, AAudio audio, AExternal external){
        super.handleInputs(graphics, input, audio, external);
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) input.getEventList().clone();
        for (AInput.Event event : eventList)
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = ((AInput.TouchInputEvent) event).x;
                    float collisionY = ((AInput.TouchInputEvent) event).y;
                    if (this.returnButton.checkCollision(graphics, collisionX, collisionY))
                        this.backToMenu = true;
                    else for (int i = 0; i < this.rows; ++i)
                            for (int j = 0; j < this.cols; ++j)
                                if ((i * this.cols + j) < this.currentLevel)
                                    if (this.levels[i][j].checkCollision(graphics, collisionX, collisionY))
                                        this.changeScene = i * this.cols + j + 1 + (this.categoryId * 20);
                    break;
                default:
                    break;
            }
        input.clearEventList();
    }

    @Override
    public void saveScene(Bundle outState) {}

    @Override
    public void saveSceneInFile(View myView) {
        super.saveSceneInFile(myView);
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
        super.restoreScene(savedInstanceState, engine);
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        super.restoreSceneFromFile(myView);

        if(this.categoryId == 0) {
            this.textId = "Bosque";
            this.currentLevel = this.data.forestLevels;
        }
        else if (this.categoryId == 1){
            this.textId = "Mar";
            this.currentLevel = this.data.seaLevels;
        }
        else if (this.categoryId == 2) {
            this.textId = "Ciudad";
            this.currentLevel = this.data.cityLevels;
        }
        else {
            this.textId = "Desierto";
            this.currentLevel = this.data.desertLevels;
        }
    }
}