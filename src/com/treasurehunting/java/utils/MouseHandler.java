package com.treasurehunting.java.utils;

import com.treasurehunting.java.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private int mouseX = -1;
    private int mouseY = -1;
    private int mouseB = -1;

    public MouseHandler(GamePanel gamePanel) {
        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);
    }

    public int getX() { return mouseX; }

    public int getY() { return mouseY; }

    public int getButton() { return mouseB; }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        mouseB = e.getButton();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseB = -1;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
