package com.example.logica;

import com.example.engine.IButton;
import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IInput;
import com.example.engine.IScene;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardSelectionScene implements IScene {
    private IFont font;
    private IEngine engine;
    private boolean changeScene;
    private int boardSize;

    private IButton button3x3;
    private IButton button5x5;
    private IButton button10x10;

    public BoardSelectionScene(IEngine engine_){
        this.changeScene = false; this.boardSize = 0;
        this.engine = engine_;
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        createButtons();
    }

    @Override
    public void update() {
        if(this.changeScene){
            this.changeScene = false;
            if(this.boardSize == 1) this.engine.getCurrentState().addScene(new MainScene(this.engine,3,3));
            else if (this.boardSize == 2) this.engine.getCurrentState().addScene(new MainScene(this.engine,5,5));
            else if (this.boardSize == 3) this.engine.getCurrentState().addScene(new MainScene(this.engine,10,10));
        }
    }

    @Override
    public void render() {
        this.engine.getGraphics().setFont(this.font);
        this.engine.getGraphics().drawText("LEVEL",this.engine.getGraphics().getLogicWidth()/2.0f - ((5*50)/2.0f),
                100, 45, this.engine.getGraphics().newColor(0,0,0,255));
        //this.engine.getGraphics().drawText("1 = tablero 3x3\n 2 = tablero 5x5 \n 3 = tablero 10x10",
        //        this.engine.getGraphics().getLogicWidth()/2.0f-((15*10*3)/2.0f),  200, 10,
        //        this.engine.getGraphics().newColor(255,0,255,255));
        this.engine.getGraphics().drawButton(this.button3x3);
        this.engine.getGraphics().drawButton(this.button5x5);
        this.engine.getGraphics().drawButton(this.button10x10);
    }

    @Override
    public synchronized void handleInputs() {
        ArrayList<IInput.Event> eventList = (ArrayList<IInput.Event>) this.engine.getInput().getEventList().clone();
        Iterator<IInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.Event event = it.next();
            switch (event.type) {
                //case KEY_DOWN:
                    //this.changeScene = true;
                    //if(((IInput.KeyInputEvent)event).key == '1') this.boardSize = 1;
                    //else if (((IInput.KeyInputEvent)event).key == '2') this.boardSize = 2;
                    //else if (((IInput.KeyInputEvent)event).key == '3') this.boardSize = 3;
                    //break;

                case TOUCH_RELEASED:
                    if(((IInput.MouseInputEvent)event).mouseButton == 1){
                        if (this.button3x3.checkCollision(this.engine.getGraphics().logicToRealX(((IInput.MouseInputEvent)event).x),
                                this.engine.getGraphics().logicToRealY(((IInput.MouseInputEvent)event).y))){
                            this.changeScene = true;
                            this.boardSize = 1;
                        }
                        if (this.button5x5.checkCollision(this.engine.getGraphics().logicToRealX(((IInput.MouseInputEvent)event).x),
                                this.engine.getGraphics().logicToRealY(((IInput.MouseInputEvent)event).y))){
                            this.changeScene = true;
                            this.boardSize = 2;
                        }
                        if (this.button10x10.checkCollision(this.engine.getGraphics().logicToRealX(((IInput.MouseInputEvent)event).x),
                                this.engine.getGraphics().logicToRealY(((IInput.MouseInputEvent)event).y))){
                            this.changeScene = true;
                            this.boardSize = 3;
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
        int x3x, x5x, x10x, y, w, h, tx, ty, tSize;
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
                12,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

        this.button5x5 = this.engine.getGraphics().newButton("5x5",
                x5x - (w / 2), y - (h / 2), w, h,
                12,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

        this.button10x10 = this.engine.getGraphics().newButton("10x10",
                x10x - (w / 2), y - (h / 2), w, h,
                5,ty, tSize,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));
    }
}
