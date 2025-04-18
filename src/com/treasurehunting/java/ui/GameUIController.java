package com.treasurehunting.java.ui;

import com.treasurehunting.java.scene.GameSceneManager;
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
        if (GameSceneManager.isStateActive(GameSceneManager.HUB)) {
            for (Map.Entry<Integer, Button> entry : view.hubUI.entrySet()) {
                entry.getValue().input(mouse, key);
            }
        }
        if (GameSceneManager.isStateActive(GameSceneManager.PAUSE)) {
            for (Map.Entry<Integer, Button> entry : view.pauseUI.entrySet()) {
                entry.getValue().input(mouse, key);
            }
        }
        if (GameSceneManager.isStateActive(GameSceneManager.GAMEOVER)) {
            for (Map.Entry<Integer, Button> entry : view.gameOverUI.entrySet()) {
                entry.getValue().input(mouse, key);
            }
        }
        if (GameSceneManager.isStateActive(GameSceneManager.WIN)) {
            for (Map.Entry<Integer, Button> entry : view.winUI.entrySet()) {
                entry.getValue().input(mouse, key);
            }
        }
    }

    public void update(double time) {
        if (GameSceneManager.isStateActive(GameSceneManager.HUB)) {
            for (Map.Entry<Integer, Button> entry : view.hubUI.entrySet()) {
                entry.getValue().update(time);
            }
        }
        if (GameSceneManager.isStateActive(GameSceneManager.PAUSE)) {
            for (Map.Entry<Integer, Button> entry : view.pauseUI.entrySet()) {
                entry.getValue().update(time);
            }
        }
        if (GameSceneManager.isStateActive(GameSceneManager.GAMEOVER)) {
            for (Map.Entry<Integer, Button> entry : view.gameOverUI.entrySet()) {
                entry.getValue().update(time);
            }
        }
    }

}
