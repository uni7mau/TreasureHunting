package com.treasurehunting.java.gameobjects.obstacles.affects;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.gameobjects.GameObject;
import com.treasurehunting.java.gameobjects.obstacles.Obstacle;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;

import java.awt.*;

public class Trap extends Obstacle {

    public Trap(Vector2f pos) {
        super(Assets.trapSS, pos, Assets.TILE_SIZE, Assets.TILE_SIZE);

        anim.setDelay(10);
    }

    @Override
    public void animate() { }

    @Override
    public void activeEvent(GameObject go) {
        if (go instanceof Entity) {
            go.healthDec(50, 50, getDirection(go));
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (!PlayScene.tm.checkInFog(bounds)) {
            super.render(g2d);
        }
    }

}
