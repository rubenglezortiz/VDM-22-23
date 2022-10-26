package com.example.desktopengine;

import com.example.engine.IInput;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class DInput implements IInput, KeyListener, MouseListener, MouseMotionListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

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

    }


    private List<Event> eventos = new ArrayList<Event>();

    public void addEvent(MouseEvent evento){
        InputTouchType tipo;
        if(evento.getID()== MouseEvent.BUTTON1_DOWN_MASK){
            tipo = InputTouchType.TOUCH_DOWN;
        }
        else tipo = InputTouchType.TOUCH_DOWN; //CAMBIAR ESTO
        eventos.add(new Event(evento.getX(), evento.getY(), 0, tipo));
    }

    public List<Event> getEventList(){
        return null;
    }
}
