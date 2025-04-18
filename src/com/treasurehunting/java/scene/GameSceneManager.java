package com.treasurehunting.java.scene;

import com.treasurehunting.java.ui.GameUIController;
import com.treasurehunting.java.ui.GameUI;
import com.treasurehunting.java.utils.*;

import java.awt.*;

public class GameSceneManager {

    private static GameScene[] states = new GameScene[5];

    public static final int PLAY = 0;
    public static final int PAUSE = 1;
    public static final int GAMEOVER = 2;
    public static final int HUB = 3;
    public static final int WIN = 4;

    public static GameUI view;
    public static GameUIController controller;

    public GameSceneManager() {
        add(HUB);

        view = new GameUI();
        controller = new GameUIController(view, new GameUIModel());
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
        } else if (state == WIN) {
            states[WIN] = new WinScene();
        }
    }

    public static void pop(int state) {
        states[state] = null;
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        controller.input(mouse, key);

        for (int i = 0; i < states.length; i++) {
            if (states[i] != null && states[GAMEOVER] == null) {
                states[i].input(mouse, key);
            }
        }

        key.getKeys().get(GameSettings.ESCAPE).tick();

        if (key.getKeys().get(GameSettings.ESCAPE).clicked) {
            if (GameSceneManager.isStateActive(GameSceneManager.PAUSE)) {
                GameSceneManager.pop(GameSceneManager.PAUSE);
            } else if (GameSceneManager.isStateActive(GameSceneManager.PLAY)) {
                GameSceneManager.add(GameSceneManager.PAUSE);
            }
        }
    }

    public void update(double time) {
//        System.out.println(isStateActive(PLAY) + " " + isStateActive(PAUSE) + " " + isStateActive(GAMEOVER) + " " + isStateActive(HUB) + " " + isStateActive(WIN));
        controller.update(time);

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

        view.render(g2d);
    }

}