package com.example.aengine;

import com.example.engine.IInput;

import java.util.ArrayList;

public class AInput implements IInput {
    private ArrayList<Event> events = new ArrayList<Event>();

    @Override
    public ArrayList<Event> getEventList() {return events;}

    @Override
    public void clearEventList() {events.clear();}
}
