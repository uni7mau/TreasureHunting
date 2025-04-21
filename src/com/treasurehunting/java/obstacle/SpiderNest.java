package com.treasurehunting.java.obstacle;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;

import java.awt.*;

public class SpiderNest extends Obstacle {

    public SpiderNest(Vector2f pos) {
        super(Assets.spiderNestSS.getSprite(0, 0), pos, Assets.TILE_SIZE, 2* Assets.TILE_SIZE);

        bounds.setHeight(height / 2);
        bounds.setYOffset(height / 2);

        hasAnim = false;
    }

    @Override
    public void animate() { }

    @Override
    public void activeEvent(GameObject go) {
        if (go instanceof Entity) {
            go.slowdown(0.74f); // This is weird
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (!PlayScene.tm.checkInFog(bounds)) {
            super.render(g2d);
        }
    }

}
