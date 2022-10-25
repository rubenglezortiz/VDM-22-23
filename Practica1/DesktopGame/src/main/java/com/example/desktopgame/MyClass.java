package com.example.desktopgame;


import com.example.desktopengine.DesktopEngine;
import com.example.logica.Tablero;

import javax.swing.JFrame;

public class MyClass {

    public static void main(String[] args) {
        JFrame window = new JFrame("jueguito");

        window.setSize(600, 400);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        DesktopEngine myEngine = new DesktopEngine(window);
        Tablero tablero = new Tablero(5,5);
        int i = 0;
        myEngine.setTablero(tablero);




    }
}