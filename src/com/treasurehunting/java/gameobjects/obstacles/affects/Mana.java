package com.treasurehunting.java.gameobjects.obstacles.affects;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.gameobjects.GameObject;
import com.treasurehunting.java.gameobjects.obstacles.Obstacle;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;

import java.awt.*;

public class Mana extends Obstacle {

    public static int w = 4 * Assets.TILE_SIZE / 5;
    public static int h = 4 * Assets.TILE_SIZE / 5;

    public Mana(Vector2f pos) {
        super(Assets.manaSS, pos, w, h);
    }

    @Override
    public void animate() { }

    @Override
    public void activeEvent(GameObject go) {
        if (go instanceof Entity target && target.getHealth() != 0) {
            target.manaDec(-120);
            DIE_STATE = true;
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (!PlayScene.tm.checkInFog(bounds)) {
            super.render(g2d);
            g2d.drawRect((int) pos.getWorldVar().x, (int) pos.getWorldVar().y, width, height);
        }
    }

}
