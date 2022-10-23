package com.example.desktop;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MyMouseListener implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        System.out.println("Has clikao el boton:" + mouseEvent.getButton()+
                "\nEn las coordenAs X:"+ mouseEvent.getPoint().x+ "  Y:" + mouseEvent.getPoint().y +
                "\ndel raOn: "+ mouseEvent.getID());
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
}
