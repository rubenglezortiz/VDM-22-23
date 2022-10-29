package com.example.logica;

import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IInput;
import com.example.engine.IScene;
import com.example.engine.IState;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Iterator;

public class Scene1 implements IScene {
    private IImage imagen;
    private IGraphics graphics;
    private IInput input;
    private Tablero board;

    public Scene1(IEngine engine){
        this.graphics = engine.getGraphics();
        this.input = engine.getInput();
        board = new Tablero(5,5,this.graphics);
        engine.getState().setScene(this);
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        board.render(graphics);
    }

    @Override
    public void handleInputs() {
        ArrayList<IInput.Event> eventList = this.input.getEventList();
        Iterator<IInput.Event> it = eventList.iterator();

        while (it.hasNext()) {
            IInput.Event event = it.next();
            switch (event.type) {
                case TOUCH_PRESSED:
                case TOUCH_RELEASED:
                case TOUCH_CLICKED:
                    board.handleInputs(event);
                    break;
                case KEY_DOWN:
                    board.handleInputs(event);
                    break;
                default:
                    break;
            }
        }
        this.input.clearEventList();
    }
}