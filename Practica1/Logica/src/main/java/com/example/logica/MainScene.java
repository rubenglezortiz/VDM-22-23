package com.example.logica;

import com.example.engine.IButton;
import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IInput;
import com.example.engine.IScene;

import java.util.ArrayList;
import java.util.Iterator;

public class MainScene implements IScene {
    private IEngine engine;
    private Board board;
    private IButton checkButton, backToMenuButton;
    private IFont font;
    private float timer;
    private boolean backToMenu;

    public MainScene(IEngine engine_, int numRows, int numCols){
        this.engine = engine_;
        this.font = this.engine.getGraphics().newFont("font.TTF", false);
        board = new Board(numRows,numCols,this.engine.getGraphics(), this.engine.getAudio());
        this.createButtons();
        this.backToMenu = false;
        this.timer = 0.0f;
    }

    @Override
    public void update() {
        long actualTime = this.engine.getTime();
        if(board.getCheckPressed()){
            timer = (actualTime) + 5.0f;
            board.pressedOut();
        }

        if ((actualTime) > timer) board.checkOut();

        if(this.backToMenu) this.engine.getCurrentState().removeScene(2);
    }

    @Override
    public void render() {
        this.board.render(this.engine.getGraphics());
        this.engine.getGraphics().drawButton(this.backToMenuButton);
        this.engine.getGraphics().drawButton(this.checkButton);
    }

    @Override
    public synchronized void handleInputs() {
        ArrayList<IInput.Event> eventList = (ArrayList<IInput.Event>) this.engine.getInput().getEventList().clone();
        Iterator<IInput.Event> it = eventList.iterator();
        while (it.hasNext()) {
            IInput.Event event = it.next();
            switch (event.type) {
                case TOUCH_PRESSED:
                    board.handleInputs(event);
                    break;
                case TOUCH_RELEASED:
                    board.handleInputs(event);
                    if (this.backToMenuButton.checkCollision(this.engine.getGraphics().logicToRealX(((IInput.MouseInputEvent)event).x),
                            this.engine.getGraphics().logicToRealY(((IInput.MouseInputEvent)event).y)))
                                this.backToMenu = true;
                    else if (this.checkButton.checkCollision(this.engine.getGraphics().logicToRealX(((IInput.MouseInputEvent)event).x),
                            this.engine.getGraphics().logicToRealY(((IInput.MouseInputEvent)event).y)))
                        this.board.checkWin();
                    break;
                default:
                    break;
            }
        }
        this.engine.getInput().clearEventList();
    }

    private void createButtons(){
        int x,y,w,h;
        x = this.engine.getGraphics().getLogicWidth() / 3;
        y = this.engine.getGraphics().getLogicHeight() * 6 / 7;
        w = this.engine.getGraphics().getLogicWidth() / 5;
        h = this.engine.getGraphics().getLogicHeight() / 15;

        this.backToMenuButton = this.engine.getGraphics().newButton("Volver",
                x - (w / 2), y - (h / 2), w, h,
                6,25, 9,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));

        x = this.engine.getGraphics().getLogicWidth() * 2 / 3;

        this.checkButton = this.engine.getGraphics().newButton("Comprueba",
                x - (w / 2), y - (h / 2), w, h,
                2,25, 7,
                this.font,
                this.engine.getGraphics().newColor(0, 0, 0, 255),
                this.engine.getGraphics().newColor(255, 255, 255, 255));
    }
}