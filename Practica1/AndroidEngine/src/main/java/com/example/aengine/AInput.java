package com.example.aengine;

import android.view.MotionEvent;
import android.view.View;

import com.example.engine.IInput;

import java.util.ArrayList;

public class AInput implements IInput, View.OnTouchListener {
    private ArrayList<MyInputEvent> events;
    private AGraphics graphics;

    public AInput(AGraphics graphics_){
        this.graphics = graphics_;
        this.events = new ArrayList<>();
    }

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
        MyInputEvent newEvent = new MyInputEvent(type, 0, (int)this.graphics.realToLogicX(event.getX()),  (int)this.graphics.realToLogicY(event.getY()),1);
        this.events.add(newEvent);
    }


    @Override
    public ArrayList<MyInputEvent> getEventList() {return this.events;}

    @Override
    public void clearEventList() {this.events.clear();}
}
