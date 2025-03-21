package com.treasurehunting.java.ui;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.Preferences;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Inventory {

    private BufferedImage img;

    private Entity owner;

    private Vector2f pos;
    private int scale;

    public Inventory(Entity owner, BufferedImage img, Vector2f pos, int scale) {
        this.owner = owner;
        this.img = img;
        this.pos = pos;
        this.scale = scale;
    }

    public void update() {

    }

    public void render(Graphics2D g2d) {
        g2d.drawImage(img, Preferences.GAME_WIDTH / 2 - img.getWidth()*scale / 2, Preferences.GAME_HEIGHT / 2 - img.getHeight()*scale / 2, (img.getWidth()*scale), img.getHeight() * scale, null);
    }

}
