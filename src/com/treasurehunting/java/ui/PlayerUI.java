package com.treasurehunting.java.ui;

import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.skills.AbilityController;
import com.treasurehunting.java.states.GameStateManager;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;
import com.treasurehunting.java.utils.Preferences;

import java.awt.*;

public class PlayerUI {

    private HealthBar healthBar;
    private ManaBar manaBar;
    private Inventory inventory;
    private AbilityView abilityBar;
    private AbilityController abilityController;

    public PlayerUI(Player p) {
        healthBar = new HealthBar(p, new Vector2f(10, 10), 3);
        manaBar = new ManaBar(p, new Vector2f(10, 80), 4);
        inventory = new Inventory(p, new Vector2f(Preferences.GAME_WIDTH / 2 - Assets.inventorySS.getWidth() / 2, Preferences.GAME_HEIGHT / 2 - Assets.inventorySS.getHeight()/2), 2);
        abilityBar = new AbilityView();
        abilityController = new AbilityController.Builder()
                .withAbilities(p.getSkills())
                .build(abilityBar);
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        healthBar.input(mouse, key);
        manaBar.input(mouse, key);
        abilityController.input();
    }

    public void update(double time) {
        healthBar.update(time);
        manaBar.update(time);
        abilityController.update(1000 / Preferences.GAME_HERTZ);
    }

    public void render(Graphics2D g2d) {
        healthBar.render(g2d);
        manaBar.render(g2d);
        if (GameStateManager.isStateActive(GameStateManager.MENU)) {
            inventory.render(g2d);
        }
        abilityBar.render(g2d);
    }

}
