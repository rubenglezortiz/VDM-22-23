package com.example.aengine;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class AInput implements View.OnTouchListener {
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

    public static class MouseInputEvent extends Event {
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
    private ArrayList<Event> events = new ArrayList<Event>();

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
            addEvent(motionEvent, InputType.TOUCH_PRESSED);
            return true;
        }
        if (motionEvent.getAction()==MotionEvent.ACTION_UP) {
            addEvent(motionEvent, InputType.TOUCH_RELEASED);
            return true;
        }
        return false;
    }

    public synchronized void addEvent(MotionEvent event, InputType type){
        Event newEvent = new MouseInputEvent((int)event.getX(), (int)event.getY(), 1, 0, type);
        this.events.add(newEvent);
    }

    public ArrayList<Event> getEventList() {return this.events;}

    public void clearEventList() {this.events.clear();}
}
