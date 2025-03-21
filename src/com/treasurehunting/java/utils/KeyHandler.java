package com.treasurehunting.java.utils;

import com.treasurehunting.java.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {

    public static ArrayList<Key> keys = new ArrayList<>();

    public class Key {
        public int presses, absorbs;
        public boolean down, clicked;

        public Key() { keys.add(this); }

        public void toogle(boolean pressed) {
            if (pressed != down) {
                down = pressed;
            }
            if (pressed) {
                presses++;
            }
        }

        public void tick() {
            if (absorbs < presses) {
                absorbs++;
                clicked = true;
            } else {
                clicked = false;
            }
        }
    }

    public Key up = new Key();
    public Key down = new Key();
    public Key left = new Key();
    public Key right = new Key();
    public Key menu = new Key();
    public Key escape = new Key();
    public Key dash = new Key();
    public Key speedy = new Key();
    public Key normAttack = new Key();
    public Key elementSkill = new Key();
    public Key ultimateSkill = new Key();
    public Key reload = new Key();

    public KeyHandler(GamePanel gamePanel) { gamePanel.addKeyListener(this); }

    public void releaseAll() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).down = false;
        }
    }

    public void tickAll() {
        for (int i = 0; i < keys.size(); i++) {
            keys.get(i).tick();
        }
    }

    public void toogleAll(KeyEvent e, boolean pressed) {
        if (e.getKeyCode() == KeyEvent.VK_W) up.toogle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_S) down.toogle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_A) left.toogle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_D) right.toogle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_E) menu.toogle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) escape.toogle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) speedy.toogle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) dash.toogle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_J) normAttack.toogle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_K) elementSkill.toogle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_L) ultimateSkill.toogle(pressed);
        if (e.getKeyCode() == KeyEvent.VK_R) reload.toogle(pressed);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //just eat the method
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toogleAll(e, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toogleAll(e, false);
    }
}
