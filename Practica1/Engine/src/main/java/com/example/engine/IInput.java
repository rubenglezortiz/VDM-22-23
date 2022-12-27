package com.example.engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public interface IInput {
    public ArrayList<MyInputEvent> getEventList();
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

    public static class MyInputEvent {
        public InputType type;
        public int index, x, y, mouseButton;
        public char key;

        public MyInputEvent(InputType type_, int index_, int x_, int y_, int mouseButton_){
            this.type = type_;
            this.index = index_;
            this.x = x_;
            this.y = y_;
            this.mouseButton = mouseButton_;
            this.key = ' ';
        }

        public MyInputEvent(InputType type_, int index_, char key){
            this.type = type_;
            this.index = index_;
            this.x = this.y =  this. mouseButton = 0;
            this.key = key;
        }
    }
}
