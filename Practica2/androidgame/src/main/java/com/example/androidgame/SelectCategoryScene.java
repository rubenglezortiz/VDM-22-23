package com.example.androidgame;

import com.example.aengine.AButton;
import com.example.aengine.AFont;
import com.example.aengine.AInput;
import com.example.aengine.AScene;
import com.example.aengine.AndroidEngine;

import java.util.ArrayList;
import java.util.Iterator;

public class SelectCategoryScene extends AScene {
    private AndroidEngine engine;
    private AFont font;
    private int changeScene;
    private boolean backToMenu;

    private AButton returnButton;
    private AButton forestButton;
    private AButton seaButton;
    private AButton cityButton;
    private AButton desertButton;

    private int forestFinished;
    private int seaFinished;
    private int cityFinished;
    private int desertFinished;

    private int coins;

    public SelectCategoryScene(AndroidEngine engine_){
        this.engine = engine_;
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        this.backToMenu = false;
        this.changeScene = 0;

        //En el futuro, se tendrá que leer de fichero para cargar partida guardada
        this.coins = 0;
        this.forestFinished = 0;
        this.seaFinished = 0;
        this.cityFinished = 0;
        this.desertFinished = 0;

        createButtons();
    }

    @Override
    public void update() {
        if (this.changeScene != 0){
            switch(this.changeScene){
                case 1:
                    //new ForestScene
                    break;
                case 2:
                    //new SeaScene
                    break;
                case 3:
                    //new CityScene
                    break;
                case 4:
                    //new DesertScene
                    break;
                default:
                    break;
            }
            this.changeScene = 0;
        }
        if(this.backToMenu) this.engine.getCurrentState().removeScene(1);
    }

    @Override
    public void render() {
        this.engine.getGraphics().setFont(this.font);
        this.engine.getGraphics().drawText("Elige la categoría en la que quieres jugar",
                this.engine.getGraphics().getLogicWidth()/2.0f - ((5*50)/2.0f),
                this.engine.getGraphics().getLogicHeight() * 3/ 16, 10,
                this.engine.getGraphics().newColor(0,0,0,255));

        this.engine.getGraphics().drawButton(this.forestButton);
        this.engine.getGraphics().drawButton(this.seaButton);
        this.engine.getGraphics().drawButton(this.cityButton);
        this.engine.getGraphics().drawButton(this.desertButton);
        this.engine.getGraphics().drawButton(this.returnButton);
    }

    @Override
    public void handleInputs() {
        ArrayList<AInput.Event> eventList = (ArrayList<AInput.Event>) this.engine.getInput().getEventList().clone();
        Iterator<AInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            AInput.Event event = it.next();
            switch (event.type) {

                case TOUCH_RELEASED:
                    if(((AInput.MouseInputEvent)event).mouseButton == 1){
                        if (this.forestButton.checkCollision(this.engine.getGraphics().logicToRealX(((AInput.MouseInputEvent)event).x),
                                this.engine.getGraphics().logicToRealY(((AInput.MouseInputEvent)event).y))){
                            this.changeScene = 1;
                        }
                        else if (this.seaButton.checkCollision(this.engine.getGraphics().logicToRealX(((AInput.MouseInputEvent)event).x),
                                this.engine.getGraphics().logicToRealY(((AInput.MouseInputEvent)event).y))){
                            this.changeScene = 2;
                        }
                        else if (this.cityButton.checkCollision(this.engine.getGraphics().logicToRealX(((AInput.MouseInputEvent)event).x),
                                this.engine.getGraphics().logicToRealY(((AInput.MouseInputEvent)event).y))){
                            this.changeScene = 3;
                        }
                        else if (this.desertButton.checkCollision(this.engine.getGraphics().logicToRealX(((AInput.MouseInputEvent)event).x),
                                this.engine.getGraphics().logicToRealY(((AInput.MouseInputEvent)event).y))){
                            this.changeScene = 4;
                        }
                        else if (this.returnButton.checkCollision(this.engine.getGraphics().logicToRealX(((AInput.MouseInputEvent)event).x),
                                this.engine.getGraphics().logicToRealY(((AInput.MouseInputEvent)event).y))){
                            this.backToMenu = true;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        this.engine.getInput().clearEventList();
    }

    private void createButtons(){
        int x1, x2, y1, y2, w, h, tx, ty, tSize;
        x1 = this.engine.getGraphics().getLogicWidth() / 13;
        x2 = this.engine.getGraphics().getLogicWidth() * 7 / 13;
        y1 = this.engine.getGraphics().getLogicHeight() / 4;
        y2 = this.engine.getGraphics().getLogicHeight() * 9 / 16;
        w = this.engine.getGraphics().getLogicWidth() * 5 / 13;
        h = this.engine.getGraphics().getLogicHeight() / 4;
        tx = 12;
        ty = 12;
        tSize = 12;

        String locked = "BLOQUEADO";
        String sea = "MAR";
        String city = "CIUDAD";
        String desert = "DESIERTO";

        if (forestFinished < 5) sea = locked;
        if (seaFinished < 5) city = locked;
        if (cityFinished < 5) desert = locked;

        this.forestButton = this.engine.getGraphics().newButton("BOSQUE",
                x1 - (w / 2), y1 - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(250, 215, 160, 255));



        this.seaButton = this.engine.getGraphics().newButton(sea,
                x2 - (w / 2), y1 - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(250, 215, 160, 255));

        this.cityButton = this.engine.getGraphics().newButton(city,
                x1 - (w / 2), y2 - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(250, 215, 160, 255));

        this.desertButton = this.engine.getGraphics().newButton(desert,
                x2 - (w / 2), y2 - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(250, 215, 160, 255));


        int x, y;
        x =  this.engine.getGraphics().getLogicWidth() / 13;
        y =  this.engine.getGraphics().getLogicHeight() / 16;
        w = this.engine.getGraphics().getLogicWidth() / 4;
        h = this.engine.getGraphics().getLogicHeight() / 16;

        this.returnButton = this.engine.getGraphics().newButton("Volver",
                x - (w / 2), y - (h / 2), w, h,
                tx,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

    }
}