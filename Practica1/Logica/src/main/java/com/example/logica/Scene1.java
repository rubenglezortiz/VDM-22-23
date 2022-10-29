package com.example.logica;

import com.example.engine.IEngine;
import com.example.engine.IGraphics;
import com.example.engine.IImage;
import com.example.engine.IScene;
import com.example.engine.IState;

import java.awt.Image;

public class Scene1 implements IScene {
    private IImage imagen;
    private IGraphics graphics;

    private Tablero board;

    public Scene1(IEngine engine){
        this.graphics = engine.getGraphics();
        board = new Tablero(5,5);
        engine.getState().setScene(this);
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        board.render(graphics);
        //this.graphics.drawImage(this.imagen, 0,0, 100,100);
    }

    @Override
    public void handleInputs() {

    }
}