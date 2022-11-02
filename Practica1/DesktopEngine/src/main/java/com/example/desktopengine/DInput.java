package com.example.desktopengine;

import com.example.engine.IInput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class DInput implements IInput, KeyListener, MouseListener, MouseMotionListener {
    private ArrayList<Event> events = new ArrayList<Event>();

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        addEvent(keyEvent, InputType.KEY_DOWN);
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        addEvent(keyEvent, InputType.KEY_UP);
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        addEvent(mouseEvent, InputType.TOUCH_CLICKED);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        addEvent(mouseEvent, InputType.TOUCH_PRESSED);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        addEvent(mouseEvent, InputType.TOUCH_RELEASED);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        //addEvent(mouseEvent, InputType.TOUCH_MOVE);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
    }

    public void addEvent(KeyEvent event, InputType tipo){
        Event newEvent = new KeyInputEvent(KeyEvent.getKeyText((event.getKeyCode())).charAt(0), 0, tipo);
        events.add(newEvent);
    }

    public void addEvent(MouseEvent event, InputType tipo){
        Event newEvent = new MouseInputEvent(event.getX(), event.getY(), event.getButton(), 0, tipo);
        events.add(newEvent);
    }

    @Override
    public ArrayList<Event> getEventList(){
        return events;
    }

    @Override
    public void clearEventList() {
        events.clear();
    }
}
