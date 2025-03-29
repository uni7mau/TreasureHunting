package com.treasurehunting.java.utils;

import com.treasurehunting.java.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

public class KeyHandler implements KeyListener {

    // TODO: move static element below to settings file
    public static int UP = KeyEvent.VK_W;
    public static int DOWN = KeyEvent.VK_S;
    public static int LEFT = KeyEvent.VK_A;
    public static int RIGHT = KeyEvent.VK_D;
    public static int SKILL1 = KeyEvent.VK_SHIFT;
    public static int SKILL2 = KeyEvent.VK_J;
    public static int SKILL3 = KeyEvent.VK_K;
    public static int SKILL4 = KeyEvent.VK_L;
    public static int SKILL5 = KeyEvent.VK_I;
    public static int ADDITION1 = KeyEvent.VK_R;
    public static int ADDITION2 = KeyEvent.VK_CONTROL;
//    public static int ADDITION3 = KeyEvent.;
//    public static int ADDITION4 = KeyEvent.;
//    public static int ADDITION5 = KeyEvent.;
    public static int ESCAPE = KeyEvent.VK_ESCAPE;
    public static int INVENTORY = KeyEvent.VK_E;
    public static int MAP = KeyEvent.VK_M;
    public static int CHARINFO = KeyEvent.VK_C;
    public static int SPACE = KeyEvent.VK_SPACE;

    public static HashMap<Integer, Key> keys = new HashMap<>();

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

        KeyHandler.keys.put(UP, new KeyHandler.Key());
        KeyHandler.keys.put(DOWN, new KeyHandler.Key());
        KeyHandler.keys.put(LEFT, new KeyHandler.Key());
        KeyHandler.keys.put(RIGHT, new KeyHandler.Key());
        KeyHandler.keys.put(SKILL1, new KeyHandler.Key());
        KeyHandler.keys.put(SKILL2, new KeyHandler.Key());
        KeyHandler.keys.put(SKILL3, new KeyHandler.Key());
        KeyHandler.keys.put(SKILL4, new KeyHandler.Key());
        KeyHandler.keys.put(SKILL5, new KeyHandler.Key());
        KeyHandler.keys.put(ADDITION1, new KeyHandler.Key());
        KeyHandler.keys.put(ADDITION2, new KeyHandler.Key());
        KeyHandler.keys.put(ESCAPE, new KeyHandler.Key());
        KeyHandler.keys.put(INVENTORY, new KeyHandler.Key());
        KeyHandler.keys.put(MAP, new KeyHandler.Key());
        KeyHandler.keys.put(CHARINFO, new KeyHandler.Key());
        KeyHandler.keys.put(SPACE, new KeyHandler.Key());
    }

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
