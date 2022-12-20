package com.example.aengine;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class AInput implements View.OnTouchListener, View.OnLongClickListener{
    private float xTouch, yTouch;
    private AGraphics graphics;

    public AInput(AGraphics graphics_) {this.graphics = graphics_;}

    public enum InputType{
        TOUCH_PRESSED,
        TOUCH_RELEASED,
        LONG_TOUCH
    }

    public static class Event {
        public InputType type;
        public int index;

        public Event(int index_, InputType type_){
            this.index = index_;
            this.type = type_;
        }
    }

    public static class TouchInputEvent extends Event {
        public float x, y;
        public int mouseButton;

        public TouchInputEvent(float x_, float y_, int mouseButton_, int index_, InputType type_) {
            super(index_, type_);
            this.x = x_;
            this.y = y_;
            this.mouseButton = mouseButton_;
        }
    }

    private ArrayList<Event> events = new ArrayList<Event>();

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        this.xTouch = motionEvent.getX();
        this.yTouch = motionEvent.getY();
        if(motionEvent.getAction()==MotionEvent.ACTION_DOWN)
            addEvent(this.xTouch, this.yTouch, InputType.TOUCH_PRESSED);
        if (motionEvent.getAction()==MotionEvent.ACTION_UP)
            addEvent(this.xTouch, this.yTouch, InputType.TOUCH_RELEASED);
        return false;
    }

    @Override
    public boolean onLongClick(View view) {
        addEvent(this.xTouch, this.yTouch, InputType.LONG_TOUCH);
        return true;
    }

    public void addEvent(float x, float y, InputType type){
        //this.events.add(new TouchInputEvent(this.graphics.realToLogicX(x), this.graphics.realToLogicY(y), 1, 0, type));
        this.events.add(new TouchInputEvent(x, y, 1, 0, type));

    }

    public ArrayList<Event> getEventList() {return this.events;}

    public void clearEventList() {this.events.clear();}
}
