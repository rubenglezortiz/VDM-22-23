package com.example.logica;

import com.example.engine.IAudio;
import com.example.engine.IButton;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IInput;
import com.example.engine.IScene;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardSelectionScene implements IScene {
    private String font;
    private boolean changeScene;
    private int boardSize;

    private IButton button3x3;
    private IButton button5x5;
    private IButton button10x10;

    public BoardSelectionScene(IEngine engine_){
        this.changeScene = false; this.boardSize = 0;
        this.font = "font.TTF";
        engine_.getGraphics().newFont(this.font, false);
        createButtons(engine_.getGraphics());
    }

    @Override
    public void update(IEngine engine) {
        if(this.changeScene){
            this.changeScene = false;
            if(this.boardSize == 1) engine.getCurrentState().addScene(new MainScene(engine,3,3));
            else if (this.boardSize == 2) engine.getCurrentState().addScene(new MainScene(engine,5,5));
            else if (this.boardSize == 3) engine.getCurrentState().addScene(new MainScene(engine,10,10));
        }
    }

    @Override
    public void render(IGraphics graphics) {
        graphics.drawText(this.font, "LEVEL",graphics.getLogicWidth()/2.0f - ((5*50)/2.0f),
                100, 45, graphics.newColor(0,0,0,255));
        graphics.drawButton(this.button3x3);
        graphics.drawButton(this.button5x5);
        graphics.drawButton(this.button10x10);
    }

    @Override
    public synchronized void handleInputs(IInput input, IAudio audio) {
        ArrayList<IInput.MyInputEvent> eventList = (ArrayList<IInput.MyInputEvent>) input.getEventList().clone();
        Iterator<IInput.MyInputEvent> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.MyInputEvent event = it.next();
            int x = event.x;
            int y = event.y;
            switch (event.type) {
                case TOUCH_RELEASED:
                    if(event.mouseButton == 1){
                        if (this.button3x3.checkCollision(x,y)){
                            this.changeScene = true;
                            this.boardSize = 1;
                        }
                        if (this.button5x5.checkCollision(x,y)){
                            this.changeScene = true;
                            this.boardSize = 2;
                        }
                        if (this.button10x10.checkCollision(x,y)){
                            this.changeScene = true;
                            this.boardSize = 3;
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        input.clearEventList();
    }

    private void createButtons(IGraphics graphics){
        int x3x, x5x, x10x, y, w, h;
        x3x = (int)graphics.getLogicWidth() / 4;
        x5x = (int)graphics.getLogicWidth() * 2 / 4;
        x10x = (int)graphics.getLogicWidth() * 3 / 4;
        y = (int)graphics.getLogicHeight() / 2;
        w = (int)graphics.getLogicWidth() / 5;
        h = (int)graphics.getLogicHeight() / 10;

        this.button3x3 = graphics.newButton("3x3.png",
                x3x - (w / 2), y - (h / 2), w, h,
                graphics.newColor(255, 255, 255, 255));

        this.button5x5 = graphics.newButton("5x5.png",
                x5x - (w / 2), y - (h / 2), w, h,
                graphics.newColor(255, 255, 255, 255));

        this.button10x10 = graphics.newButton("10x10.png",
                x10x - (w / 2), y - (h / 2), w, h,
                graphics.newColor(255, 255, 255, 255));
    }
}
