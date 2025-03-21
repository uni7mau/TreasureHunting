package com.treasurehunting.java.ui;

import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.states.GameStateManager;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;
import com.treasurehunting.java.utils.Preferences;

import java.awt.*;

public class PlayerUI {

    private HealthBar healthBar;
    private ManaBar manaBar;
    private Inventory inventory;

    public PlayerUI(Player p) {
        healthBar = new HealthBar(p, new Vector2f(10, 10), 3);
        manaBar = new ManaBar(p, new Vector2f(10, 80), 4);

        SpriteSheet inventorySS = Assets.inventorySS;
        Vector2f invenPos = new Vector2f(Preferences.GAME_WIDTH/2 - inventorySS.getWidth()/2, Preferences.GAME_HEIGHT/2 - inventorySS.getHeight()/2);
        inventory = new Inventory(p, inventorySS.getSprite(0, 0).image, invenPos, 3);
    }

    public void update(double time) {
        healthBar.update(time);
        manaBar.update(time);
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        healthBar.input(mouse, key);
        manaBar.input(mouse, key);
    }

    public void render(Graphics2D g2d) {
        healthBar.render(g2d);
        manaBar.render(g2d);
        if (GameStateManager.isStateActive(GameStateManager.MENU)) {
            inventory.render(g2d);
        }
    }

}
