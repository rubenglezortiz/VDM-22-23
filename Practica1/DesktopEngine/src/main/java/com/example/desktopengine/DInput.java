package com.example.desktopengine;

import com.example.engine.IInput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class DInput implements IInput, KeyListener, MouseListener, MouseMotionListener {

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
    public void mouseDragged(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        addEvent(mouseEvent, InputType.TOUCH_MOVE);
    }

    public void addEvent(KeyEvent event, InputType tipo){
        Event newEvent = new KeyInputEvent(KeyEvent.getKeyText((event.getKeyCode())).charAt(0), 0, tipo);
        eventos.add(newEvent);
    }

    public void addEvent(MouseEvent event, InputType tipo){
        Event newEvent = new MouseInputEvent(event.getX(), event.getY(), 0, tipo);
        eventos.add(newEvent);
    }

    private ArrayList<Event> eventos = new ArrayList<Event>();

    public ArrayList<Event> getEventList(){
        return eventos;
    }

    @Override
    public void clearEventList() {
        eventos.clear();
    }
}
