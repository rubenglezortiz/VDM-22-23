package com.example.logica;

import com.example.engine.IAudio;
import com.example.engine.IButton;
import com.example.engine.IEngine;
import com.example.engine.IFont;
import com.example.engine.IGraphics;
import com.example.engine.IInput;
import com.example.engine.IScene;
import com.example.engine.ISound;

import java.util.ArrayList;
import java.util.Iterator;

import sun.awt.AWTAutoShutdown;

public class MainScene implements IScene {
    private final Board board;
    private IButton checkButton, backToMenuButton;
    private final String font, winSound, failSound;
    private float timer;
    private boolean backToMenu;

    public MainScene(IEngine engine_, int numRows, int numCols){
        this.font = "font.TTF";
        engine_.getGraphics().newFont(this.font, false);
        this.board = new Board(numRows, numCols, engine_.getGraphics(), engine_.getAudio());
        this.backToMenu = false;
        this.timer = 0.0f;
        this.winSound = "win.wav";
        this.failSound = "fail.wav";
        createButtons(engine_.getGraphics());
        createSounds(engine_.getAudio());
    }

    @Override
    public void update(IEngine engine) {
        long actualTime = engine.getTime();
        if(this.board.getCheckPressed()){
            this.timer = (actualTime) + 5.0f;
            this.board.pressedOut();
        }

        if ((actualTime) > this.timer) this.board.checkOut();

        if(this.backToMenu) engine.getCurrentState().removeScene(2);
    }

    @Override
    public void render(IGraphics graphics) {
        this.board.render(graphics);
        graphics.drawButton(this.backToMenuButton);
        graphics.drawButton(this.checkButton);
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
                case TOUCH_PRESSED:
                    board.handleInputs(event, audio);
                    break;
                case TOUCH_RELEASED:
                    board.handleInputs(event, audio);
                    if (this.backToMenuButton.checkCollision(x,y))
                                this.backToMenu = true;
                    else if (this.checkButton.checkCollision(x,y))
                            if(this.board.checkWin()) audio.playSound(this.winSound);
                            else audio.playSound(this.failSound);
                    break;
                default:
                    break;
            }
        }
        input.clearEventList();
    }

    private void createButtons(IGraphics graphics){
        int x,y,w,h;
        x = (int)graphics.getLogicWidth() / 3;
        y = (int)graphics.getLogicHeight() * 6 / 7;
        w = h = (int)graphics.getLogicWidth() / 7;

        this.backToMenuButton = graphics.newButton("Volver.png",
                x - (w / 2), y - (h / 2), w, h,
                graphics.newColor(255, 255, 255, 255));

        x = (int)graphics.getLogicWidth() * 2 / 3;
        w = h*3;

        this.checkButton = graphics.newButton("Comprueba.png",
                x - (w / 2), y - (h / 2), w, h,
                graphics.newColor(255, 255, 255, 255));
    }

    private void createSounds(IAudio audio){
        audio.newSound(this.winSound);
        audio.setVolume(this.winSound, 0.75f);
        audio.newSound(this.failSound);
        audio.setVolume(this.failSound, 0.5f);
    }
}