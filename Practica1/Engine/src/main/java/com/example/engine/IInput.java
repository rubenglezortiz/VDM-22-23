package com.example.engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public interface IInput {
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
        public int mouseButton;

        public MouseInputEvent(int x_, int y_, int mouseButton_, int index_, InputType type_) {
            super(index_, type_);
            this.x = x_;
            this.y = y_;
            this.mouseButton = mouseButton_;
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
