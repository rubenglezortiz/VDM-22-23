package com.example.engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public interface IInput {
    public void keyTyped(KeyEvent e); //KEYEVENT SIRVE TANTO PARA JAVA COMO PARA ANDROID?
    public void keyPressed(KeyEvent e);
    public void keyReleased(KeyEvent e);

    public void mouseClicked(MouseEvent mouseEvent);
    public void mousePressed(MouseEvent mouseEvent);
    public void mouseReleased(MouseEvent mouseEvent);
    public void mouseEntered(MouseEvent mouseEvent);
    public void mouseExited(MouseEvent mouseEvent);
    public ArrayList<Event> getEventList();
    public void clearEventList();


    public static enum InputType{
        TOUCH_PRESSED,
        TOUCH_RELEASED,
        TOUCH_MOVE,
        TOUCH_CLICKED,
        KEY_DOWN,
        KEY_UP,
        KEY_LONG,
    }

    public static class Event {
        public InputType type;
        public int index;

        public Event(int index_, InputType type_){
            this.index = index_;
            this.type = type_;
        }
    }

    public static class MouseInputEvent extends Event{
        public int x;
        public int y;

        public MouseInputEvent(int x_, int y_, int index_, InputType type_) {
            super(index_, type_);
            this.x = x_;
            this.y = y_;
        }
    }

    public static class KeyInputEvent extends Event{
        public char key;

        public KeyInputEvent(char key_, int index_, InputType type_){
            super(index_, type_);
            this.key = key_;
        }
    }

}
