package com.treasurehunting.java.ui;

import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.Obstacle;
import com.treasurehunting.java.skills.AbilityController;
import com.treasurehunting.java.scene.GameSceneManager;
import com.treasurehunting.java.utils.GameSettings;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;

import java.awt.*;

public class PlayerUI {

    private final HealthBar healthBar;
    private final ManaBar manaBar;
    private final Inventory inventory;

    private final AbilityBar abilityBar;
    private final AbilityController abilityController;

    public PlayerUI(Player p) {
        healthBar = new HealthBar(p, new Vector2f(10, 10), 3);
        manaBar = new ManaBar(p, new Vector2f(10, 80), 4);
        inventory = new Inventory(p, new Vector2f(GameSettings.GAME_WIDTH / 2 - 183 / 2, 280 / 2), 2);
        abilityBar = new AbilityBar();
        abilityController = new AbilityController.Builder()
                .withAbilities(p.getSkills())
                .build(abilityBar);
    }

    public void showInfor(Obstacle obs) {
        // Overview
        // It's too many things to do now...
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        healthBar.input(mouse, key);
        manaBar.input(mouse, key);
        abilityController.input(mouse, key);

        key.getKeys().get(GameSettings.INVENTORY).tick();
        if (key.getKeys().get(GameSettings.INVENTORY).clicked) {
            if (!GameSceneManager.isStateActive(GameSceneManager.PAUSE) && !GameSceneManager.isStateActive(GameSceneManager.GAMEOVER)) {
                inventory.input(mouse, key);
            }
        }
    }

    public void update(double time) {
        healthBar.update(time);
        manaBar.update(time);
        abilityController.update(time);
    }

    public void render(Graphics2D g2d) {
        healthBar.render(g2d);
        manaBar.render(g2d);
        abilityBar.render(g2d);
        if (!GameSceneManager.isStateActive(GameSceneManager.PAUSE) && !GameSceneManager.isStateActive(GameSceneManager.GAMEOVER)) { inventory.render(g2d); }
    }

}
