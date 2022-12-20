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

public class SelectCategoryScene extends HistorySuperScene implements Serializable {
    private int changeScene;
    private boolean backToMenu;
    private AButton forestButton, seaButton, cityButton, desertButton;

    public SelectCategoryScene(AndroidEngine engine_, GameData data_){
        super(engine_, data_);
        this.changeScene = 0;
        this.backToMenu = false;

        //En el futuro, se tendrá que leer de fichero para cargar partida guardada
        this.createButtons(engine_.getGraphics());
    }

    @Override
    protected void setUpScene(AGraphics graphics, AAudio audio) {
        super.setUpScene(graphics, audio);
        this.data.levelInProgress = 0;
        createButtons(graphics);
    }

    private void createButtons(AGraphics graphics){
        int x1, x2, y1, y2, w, h;
        x1 = graphics.getLogicWidth() / 4;
        x2 = graphics.getLogicWidth() * 3 / 4;
        y1 = graphics.getLogicHeight() * 3 / 8;
        y2 = graphics.getLogicHeight() * 11 / 16;
        w = graphics.getLogicWidth() * 5 / 13;
        h = graphics.getLogicHeight() / 4;

        String sea = "Playa.png";
        String city = "Ciudad.png";
        String desert = "Desierto.png";

        if (this.data.forestLevels < 5) sea = "PlayaBloqueado.png";
        if (this.data.seaLevels < 5) city = "CiudadBloqueado.png";
        if (this.data.cityLevels < 5) desert = "DesiertoBloqueado.png";

        this.forestButton = graphics.newButton("Bosque.png",
                x1 - (w / 2.0f), y1 - (h / 2.0f), w, h,
                graphics.newColor(0,0,0,0));

        this.seaButton = graphics.newButton(sea,
                x2 - (w / 2.0f), y1 - (h / 2.0f), w, h,
                graphics.newColor(0,0,0,0));

        this.cityButton = graphics.newButton(city,
                x1 - (w / 2.0f), y2 - (h / 2.0f), w, h,
                graphics.newColor(0,0,0,0));

        this.desertButton = graphics.newButton(desert,
                x2 - (w / 2.f), y2 - (h / 2.f), w, h,
                graphics.newColor(0,0,0,0));
    }

    @Override
    public void update(AndroidEngine engine) {
        super.update(engine);
        if (this.changeScene != 0){
            engine.getCurrentState().addScene(new LevelScene(engine, this.changeScene - 1, this.data));
            this.changeScene = 0;
        }
        if(this.backToMenu){
            engine.getCurrentState().removeScene(1);
        }
    }

    @Override
    public void render(AGraphics graphics) {
        super.render(graphics);
        graphics.drawButton(this.forestButton);
        graphics.drawButton(this.seaButton);
        graphics.drawButton(this.cityButton);
        graphics.drawButton(this.desertButton);
        graphics.drawButton(this.returnButton);
    }

    @Override
    public void handleInputs(AGraphics graphics, AInput input, AAudio audio, AExternal external) {
        super.handleInputs(graphics, input, audio,external);
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) input.getEventList().clone();
        for (AInput.Event event : eventList) {
            switch (event.type) {
                case TOUCH_RELEASED:
                    float collisionX = ((AInput.TouchInputEvent) event).x;
                    float collisionY = ((AInput.TouchInputEvent) event).y;
                    if (this.forestButton.checkCollision(graphics, collisionX, collisionY))
                        this.changeScene = 1;
                    else if (this.seaButton.checkCollision(graphics, collisionX, collisionY)) {
                        if (this.data.forestLevels >= 5) this.changeScene = 2;}
                    else if (this.cityButton.checkCollision(graphics, collisionX, collisionY)) {
                        if (this.data.seaLevels >= 5) this.changeScene = 3;}
                    else if (this.desertButton.checkCollision(graphics, collisionX, collisionY)) {
                        if (this.data.cityLevels >= 5) this.changeScene = 4;}
                    else if (this.returnButton.checkCollision(graphics, collisionX, collisionY)) this.backToMenu = true;
                    break;
                default:
                    break;
            }
        }
        input.clearEventList();
    }

    @Override
    public void saveScene(Bundle outState) {
        super.saveScene(outState);
    }

    @Override
    public void saveSceneInFile(View myView) {
        super.saveSceneInFile(myView);
    }

    @Override
    public void restoreScene(Bundle savedInstanceState, AndroidEngine engine_) {
        super.restoreScene(savedInstanceState, engine_);
    }

    @Override
    public void restoreSceneFromFile(View myView) {
        super.restoreSceneFromFile(myView);
    }
}