package com.treasurehunting.java.utils;

import com.treasurehunting.java.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class KeyHandler implements KeyListener {

    private HashMap<Integer, Key> keys = new HashMap<>();

    public HashMap<Integer, Key> getKeys() { return keys; }

    public static class Key {

        public int presses, absorbs;
        public boolean down, clicked;

        public Key() {  }

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

    public KeyHandler(GamePanel gamePanel) {
        gamePanel.addKeyListener(this);

        keys.put(GameSettings.UP, new KeyHandler.Key());
        keys.put(GameSettings.DOWN, new KeyHandler.Key());
        keys.put(GameSettings.LEFT, new KeyHandler.Key());
        keys.put(GameSettings.RIGHT, new KeyHandler.Key());
        keys.put(GameSettings.SKILL1, new KeyHandler.Key());
        keys.put(GameSettings.SKILL2, new KeyHandler.Key());
        keys.put(GameSettings.SKILL3, new KeyHandler.Key());
        keys.put(GameSettings.SKILL4, new KeyHandler.Key());
        keys.put(GameSettings.SKILL5, new KeyHandler.Key());
        keys.put(GameSettings.ADDITION1, new KeyHandler.Key());
        keys.put(GameSettings.ADDITION2, new KeyHandler.Key());
        keys.put(GameSettings.ESCAPE, new KeyHandler.Key());
        keys.put(GameSettings.INVENTORY, new KeyHandler.Key());
        keys.put(GameSettings.MAP, new KeyHandler.Key());
        keys.put(GameSettings.CHARINFO, new KeyHandler.Key());
        keys.put(GameSettings.SPACE, new KeyHandler.Key());
    }

    public void toogleAll(KeyEvent e, boolean pressed) {
        if (keys.get(e.getKeyCode()) != null) {
            keys.get(e.getKeyCode()).toogle(pressed);
        }
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
