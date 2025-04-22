package com.treasurehunting.java.scene;

import com.treasurehunting.java.ui.GameUIController;
import com.treasurehunting.java.ui.GameUI;
import com.treasurehunting.java.utils.*;

import java.awt.*;

public class GameSceneManager {

    private static GameScene[] scenes = new GameScene[5];

    public static final int HUB = 0;
    public static final int PLAY = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;
    public static final int WIN = 4;

    private static GameUI view;
    private static GameUIController controller;

    public GameSceneManager() {
        add(HUB);

        view = new GameUI();
        controller = new GameUIController(view, new GameUIModel());
    }

    public static boolean isSceneActive(int scene) {
        return scenes[scene] != null;
    }

    public static void add(int scene) {
        if (scenes[scene] != null) { return; }

        if (scene == HUB) {
            scenes[HUB] = new HUBScene();
        } else if (scene == PLAY) {
            scenes[PLAY] = new PlayScene();
        } else if (scene == PAUSE) {
            scenes[PAUSE] = new PauseScene();
        } else if (scene == GAMEOVER) {
            scenes[GAMEOVER] = new GameOverScene();
        } else if (scene == WIN) {
            scenes[WIN] = new WinScene();
        }
    }

    public static void pop(int scene) {
        scenes[scene] = null;
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        controller.input(mouse, key);

        for (int i = 0; i < scenes.length; i++) {
            if (scenes[i] != null) {
                scenes[i].input(mouse, key);
            }
        }
    }

    public void update(double time) {
//        System.out.println(isStateActive(PLAY) + " " + isStateActive(PAUSE) + " " + isStateActive(GAMEOVER) + " " + isStateActive(HUB) + " " + isStateActive(WIN));
        controller.update(time);

        for (int i = 0; i < scenes.length; i++) {
            if (scenes[i] != null) {
                scenes[i].update(time);
            }
        }
    }

    public void render(Graphics2D g2d) {
        for (int i = 0; i < scenes.length; i++) {
            if (scenes[i] != null) {
                scenes[i].render(g2d);
            }
        }

        view.render(g2d);
    }

}