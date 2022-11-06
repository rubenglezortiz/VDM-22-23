package com.example.aengine;

import android.view.MotionEvent;
import android.view.View;

import com.example.engine.IInput;

import java.util.ArrayList;

public class AInput implements IInput, View.OnTouchListener {
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
        events.add(newEvent);
    }


    @Override
    public ArrayList<Event> getEventList() {return events;}

    @Override
    public void clearEventList() {events.clear();}
}
