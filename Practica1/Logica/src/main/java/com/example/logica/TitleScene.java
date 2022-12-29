package com.example.logica;

import com.example.engine.IAudio;
import com.example.engine.IButton;
import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IInput;
import com.example.engine.IScene;

import java.util.ArrayList;
import java.util.Iterator;

public class TitleScene implements IScene {
    private IButton startButton;
    private String backgroundMusic;
    private String font;
    private boolean changeScene;

    public TitleScene(IEngine engine_){
        this.changeScene = false;
        this.font = "font.TTF";
        engine_.getGraphics().newFont(this.font, false);
        this.backgroundMusic = "music.wav";
        createMusic(engine_.getAudio());
        createButton(engine_.getGraphics());
    }

    @Override
    public void update(IEngine engine) {
        if (this.changeScene) {
            this.changeScene = false;
            engine.getCurrentState().addScene(new BoardSelectionScene(engine));
        }
    }

    @Override
    public void render(IGraphics graphics) {
        graphics.drawText(this.font, "NONOGRAMS", graphics.getLogicWidth()/2.0f - ((10*30)/2.0f),
                100, 27, graphics.newColor(0,0,0,255));

        graphics.drawButton(this.startButton);
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
                        if (this.startButton.checkCollision(x, y)){
                            this.changeScene = true;
                        }
                    }
                    break;

                default:
                    break;
            }
        }
        input.clearEventList();
    }

    private void createMusic(IAudio audio){
        //Background music
        audio.newSound(this.backgroundMusic);
        audio.setLooping(this.backgroundMusic, true);
        audio.setVolume(this.backgroundMusic, 0.25f);
        audio.playSound(this.backgroundMusic);
        audio.setBackgroundMusic(this.backgroundMusic);
    }

    private void createButton(IGraphics graphics){
        int x,y,w,h;
        x = (int)graphics.getLogicWidth() / 2;
        y = (int)graphics.getLogicHeight() * 2 / 3;
        w = (int)graphics.getLogicWidth() / 3;
        h = (int)graphics.getLogicHeight() / 10;

        this.startButton = graphics.newButton("PartidaRapida.png",
                x - (w / 2), y - (h / 2), w, h,
                graphics.newColor(255, 255, 255, 255));
    }
}
