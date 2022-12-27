package com.example.desktopgame;

import com.example.desktopengine.DesktopEngine;
import com.example.logica.TitleScene;

import javax.swing.JFrame;

public class main {

    public static void main(String[] args) {
        JFrame window = new JFrame("Nonograms");
        window.setSize(600, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        DesktopEngine myEngine = new DesktopEngine(window,400,600);
        TitleScene titleScene = new TitleScene(myEngine);
        myEngine.getCurrentState().addScene(titleScene);
    }
}