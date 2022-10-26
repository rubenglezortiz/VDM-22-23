package com.example.engine;

import org.graalvm.compiler.nodeinfo.InputType;

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



    public static enum InputTouchType{
        TOUCH_DOWN,
        TOUCH_UP,
        TOUCH_MOVE,
    }

    public static enum InputKeyType{
        KEY_DOWN,
        KEY_UP,
        KEY_LONG,
    }

    public static class Event{
        public int x;
        public int y;
        public InputTouchType type;
        public int index;

        public Event(int x, int y, InputTouchType type){

        }

        public Event(int x, int y, int index, InputTouchType type){

        }
    }

}
