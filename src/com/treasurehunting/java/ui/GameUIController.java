package com.treasurehunting.java.ui;

import com.treasurehunting.java.scene.GameSceneManager;
import com.treasurehunting.java.utils.GameSettings;
import com.treasurehunting.java.utils.GameUIModel;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;

import java.util.Map;

public class GameUIController {

    private GameUI view;
    private GameUIModel model;

    public GameUIController(GameUI view, GameUIModel model) {
        this.view = view;
        this.model = model;
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        key.getKeys().get(GameSettings.ESCAPE).tick();

        if (key.getKeys().get(GameSettings.ESCAPE).clicked) {
            if (GameSceneManager.isSceneActive(GameSceneManager.PAUSE)) {
                GameSceneManager.pop(GameSceneManager.PAUSE);
            } else if (GameSceneManager.isSceneActive(GameSceneManager.PLAY)) {
                GameSceneManager.add(GameSceneManager.PAUSE);
            }
        }

        if (GameSceneManager.isSceneActive(GameSceneManager.HUB)) {
            for (Map.Entry<Integer, Button> entry : view.hubUI.entrySet()) {
                entry.getValue().input(mouse, key);
            }
        }
        if (GameSceneManager.isSceneActive(GameSceneManager.PAUSE)) {
            for (Map.Entry<Integer, Button> entry : view.pauseUI.entrySet()) {
                entry.getValue().input(mouse, key);
            }
        }
        if (GameSceneManager.isSceneActive(GameSceneManager.GAMEOVER)) {
            for (Map.Entry<Integer, Button> entry : view.gameOverUI.entrySet()) {
                entry.getValue().input(mouse, key);
            }
        }
        if (GameSceneManager.isSceneActive(GameSceneManager.WIN)) {
            for (Map.Entry<Integer, Button> entry : view.winUI.entrySet()) {
                entry.getValue().input(mouse, key);
            }
        }
    }

    public void update(double time) {
        if (GameSceneManager.isSceneActive(GameSceneManager.HUB)) {
            for (Map.Entry<Integer, Button> entry : view.hubUI.entrySet()) {
                entry.getValue().update(time);
            }
        }
        if (GameSceneManager.isSceneActive(GameSceneManager.PAUSE)) {
            for (Map.Entry<Integer, Button> entry : view.pauseUI.entrySet()) {
                entry.getValue().update(time);
            }
        }
        if (GameSceneManager.isSceneActive(GameSceneManager.GAMEOVER)) {
            for (Map.Entry<Integer, Button> entry : view.gameOverUI.entrySet()) {
                entry.getValue().update(time);
            }
        }
    }

}
