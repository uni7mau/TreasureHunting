package com.treasurehunting.java.scene;

import com.treasurehunting.java.utils.*;

import java.awt.*;

public class GameSceneManager {

    private static GameScene[] states = new GameScene[5];

    public static final int PLAY = 0;
    public static final int PAUSE = 1;
    public static final int GAMEOVER = 2;
    public static final int HUB = 3;

    public GameSceneManager() {
        add(HUB);
    }

    public static boolean isStateActive(int state) {
        return states[state] != null;
    }

    public static void add(int state) {
        if (states[state] != null) { return; }

        if (state == PLAY) {
            states[PLAY] = new PlayScene();
        } else if (state == PAUSE) {
            states[PAUSE] = new PauseScene();
        } else if (state == GAMEOVER) {
            states[GAMEOVER] = new GameOverScene();
        } else if (state == HUB) {
            states[HUB] = new HomeScene();
        }
    }

    public static void pop(int state) {
        states[state] = null;
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null && states[GAMEOVER] == null) {
                states[i].input(mouse, key);
            }
        }
    }

    public void update(double time) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].update(time);
            }
        }
    }

    public void render(Graphics2D g2d) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].render(g2d);
            }
        }
    }

}