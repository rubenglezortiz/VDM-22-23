package com.example.desktopengine;

import com.example.engine.IInput;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class DInput implements IInput, KeyListener, MouseListener, MouseMotionListener {
    private ArrayList<MyInputEvent> events;
    private DGraphicsEngine graphics;

    public DInput(DGraphicsEngine graphics_){
        this.graphics = graphics_;
        this.events = new ArrayList<>();
    }

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

    public synchronized void addEvent(KeyEvent event, InputType type){
        MyInputEvent newEvent = new MyInputEvent(type, 0, KeyEvent.getKeyText((event.getKeyCode())).charAt(0));
        events.add(newEvent);
    }

    public synchronized void addEvent(MouseEvent event, InputType type){
        MyInputEvent newEvent = new MyInputEvent(type, 0, (int)this.graphics.realToLogicX(event.getX()),
                (int)this.graphics.realToLogicY(event.getY()),1);
        this.events.add(newEvent);
    }

    @Override
    public synchronized ArrayList<MyInputEvent> getEventList(){
        return this.events;
    }

    @Override
    public synchronized void clearEventList() {
        this.events.clear();
    }
}
