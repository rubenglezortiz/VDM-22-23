package com.example.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface IInput {
    public void keyTyped(KeyEvent e); //KEYEVENT SIRVE TANTO PARA JAVA COMO PARA ANDROID?
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);

    public void mouseClicked(MouseEvent mouseEvent);
    public void mousePressed(MouseEvent mouseEvent);
    public void mouseReleased(MouseEvent mouseEvent);
    public void mouseEntered(MouseEvent mouseEvent);
    public void mouseExited(MouseEvent mouseEvent);

}
