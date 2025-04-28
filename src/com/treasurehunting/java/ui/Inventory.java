package com.treasurehunting.java.ui;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.GameSettings;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Inventory {

    private HashMap<String, BufferedImage> imgComponent;

    private Entity owner;

    private Vector2f pos;
    private int scale;

    private boolean isOpen = false;

    public Inventory(Entity owner, Vector2f pos, int scale) {
        this.owner = owner;
        imgComponent = new HashMap<>();
        imgComponent.put("MainBoard", Assets.buttonSS.getSubimage(32, 1120, 183, 280));
        imgComponent.put("Pattern", Assets.buttonSS.getSubimage(256, 1120, 183, 280));
        imgComponent.put("Box", Assets.buttonSS.getSubimage(256, 1120, 19, 21));
        imgComponent.put("BoxPressed", Assets.buttonSS.getSubimage(195, 902, 19, 21));
        imgComponent.put("SubBoard", Assets.buttonSS.getSubimage(282, 1463, 173, 49));

        this.pos = pos;
        this.scale = scale;
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        if (key.getKeys().get(GameSettings.INVENTORY).down) { isOpen = !isOpen; }
    }

    public void update() {

    }

    public void render(Graphics2D g2d) {
        if (isOpen) {
            g2d.drawImage(imgComponent.get("MainBoard"), GameSettings.GAME_WIDTH / 2 - imgComponent.get("MainBoard").getWidth() * scale / 2, GameSettings.GAME_HEIGHT / 2 - imgComponent.get("MainBoard").getHeight() * scale / 2, (imgComponent.get("MainBoard").getWidth() * scale), imgComponent.get("MainBoard").getHeight() * scale, null);
            g2d.drawImage(imgComponent.get("Pattern"), GameSettings.GAME_WIDTH / 2 - imgComponent.get("Pattern").getWidth() * scale / 2, GameSettings.GAME_HEIGHT / 2 - imgComponent.get("Pattern").getHeight() * scale / 2, (imgComponent.get("Pattern").getWidth() * scale), imgComponent.get("Pattern").getHeight() * scale, null);
            g2d.drawImage(imgComponent.get("SubBoard"), GameSettings.GAME_WIDTH / 2 - (imgComponent.get("SubBoard").getWidth() - 50) * scale / 2, 150, (imgComponent.get("SubBoard").getWidth() * scale) - 100, imgComponent.get("SubBoard").getHeight() * scale - 50, null);
        }
    }

}
