package com.treasurehunting.java.gameobjects.obstacles.affects;

import com.treasurehunting.java.gameobjects.GameObject;
import com.treasurehunting.java.gameobjects.entities.Player;
import com.treasurehunting.java.gameobjects.obstacles.Obstacle;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;

import java.awt.*;

public class Box extends Obstacle {

    public Box(Vector2f pos) {
        super(Assets.boxSS.getSprite(0, 0), pos, Assets.TILE_SIZE - 1, Assets.TILE_SIZE - 1);

        bounds.setWidth(4*width / 5);

        hasAnim = false;
        physicBody = true;
    }

    @Override
    public void animate() { }

    @Override
    public void activeEvent(GameObject go) {
        if (go instanceof Player player) {
            player.gainInfor(this);
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (!PlayScene.tm.checkInFog(bounds)) {
            super.render(g2d);
        }
    }

}
