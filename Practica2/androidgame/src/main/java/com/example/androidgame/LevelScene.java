package com.example.androidgame;

import android.os.Bundle;

import com.example.aengine.AButton;
import com.example.aengine.AFont;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
import com.example.aengine.AndroidEngine;

import java.util.ArrayList;
import java.util.Iterator;

public class LevelScene extends AScene {
    private AndroidEngine engine;
    private AFont font;
    private int changeScene;
    private boolean backToMenu;

    private AButton returnButton;
    private AButton[][] levels;

    private int coins;
    private int rows, cols;

    private int id;
    private String textId;

    public LevelScene(AndroidEngine engine_, int id_){
        this.engine = engine_;
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        this.backToMenu = false;
        this.changeScene = 0;

        this.rows = 5;
        this.cols = 4;

        //En el futuro, se tendrá que leer de fichero para cargar partida guardada
        this.coins = 0;
        this.id = id_;
        switch(this.id){
            case 0:
                this.textId = "Bosque";
                break;
            case 1:
                this.textId = "Mar";
                break;
            case 2:
                this.textId = "Ciudad";
                break;
            default:
                this.textId = "Desierto";
                break;
        }


        this.createButtons();
    }


    @Override
    public void update(){
        if (this.changeScene != 0){
            //new Nivel(this.changeScene) //changeScene indica el nivel al que pasas, habiendo 80 niveles.
            this.changeScene = 0;
        }
        if(this.backToMenu) this.engine.getCurrentState().removeScene(1);
    }

    @Override
    public void render(){
        this.engine.getGraphics().setFont(this.font);
        this.engine.getGraphics().drawText(this.textId,
                this.engine.getGraphics().getLogicWidth() / 2 - 90,
                this.engine.getGraphics().getLogicHeight() / 8, 25,
                this.engine.getGraphics().newColor(0,0,0,255));

        this.engine.getGraphics().drawButton(this.returnButton);

        for (int i = 0; i < this.rows; ++i){
            for (int j = 0; j < this.cols; ++j){
                this.engine.getGraphics().drawButton(this.levels[i][j]);
            }
        }
    }

    @Override
    public void handleInputs(){
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) this.engine.getInput().getEventList().clone();
        Iterator<AInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            AInput.Event event = it.next();
            switch (event.type) {

                case TOUCH_RELEASED:
                    if (this.returnButton.checkCollision(this.engine.getGraphics().realToLogicX(((AInput.MouseInputEvent)event).x),
                            this.engine.getGraphics().realToLogicY(((AInput.MouseInputEvent)event).y)))
                    {
                        this.backToMenu = true;
                    }
                    else{
                        for (int i = 0; i < this.rows; ++i){
                            for (int j = 0; j < this.cols; ++j) {
                                if (this.levels[i][j].checkCollision(this.engine.getGraphics().realToLogicX(((AInput.MouseInputEvent)event).x),
                                        this.engine.getGraphics().realToLogicY(((AInput.MouseInputEvent)event).y))){
                                    this.changeScene = i * this.cols + j + 1 + (this.id * 20);
                                }

                            }
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
    protected void setUpScene() {

    }

    @Override
    public void saveScene(Bundle outState) {

    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine) {
        this.engine = engine;
    }

    private void createButtons(){
        this.levels = new AButton[this.rows][this.cols]; //[y][x] : [i][j]

        int x0,y0, xi, yi, w,h;
        x0 = this.engine.getGraphics().getLogicWidth() / 5;
        y0 = this.engine.getGraphics().getLogicHeight() / 4;
        xi = this.engine.getGraphics().getLogicWidth() / 5;
        yi = xi;
        w = this.engine.getGraphics().getLogicWidth() / 6;
        h = w;

        int tx = 15;
        int ty = 40;
        int tSize = 20;

        int x, y;
        String text;
        for(int i = 0; i < this.rows; ++i){
            y = y0 + (yi * i);
            for(int j = 0; j < this.cols; ++j){

                x = x0 + (xi * j);

                text = Integer.toString(i * this.cols + j + 1 + (this.id * 20));

                this.levels[i][j] = this.engine.getGraphics().newButton(text,
                        x - (w / 2), y - (h / 2), w, h,
                        tx,ty, tSize,
                        this.font,
                        this.engine.getGraphics().newColor(0, 0, 0, 255),
                        this.engine.getGraphics().newColor(250, 215, 160, 255));
            }
        }

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
}
