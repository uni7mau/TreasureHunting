package com.treasurehunting.java.obstacle;

import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.GameSceneManager;
import com.treasurehunting.java.scene.PlayScene;

import java.awt.*;

public class Portal extends Obstacle {

    public Portal(Vector2f pos) {
        super(Assets.portalSS, pos, Assets.TILE_SIZE, Assets.TILE_SIZE);

        anim.setDelay(5);
        bounds.setWidth(2*width / 3);
        bounds.setXOffset(1*width / 6);
    }

    @Override
    public void animate() { }

    @Override
    public void activeEvent(GameObject go) {
        if (go instanceof Player) {
            if (Assets.switchNextMap()) {
                GameSceneManager.pop(GameSceneManager.PLAY);
                GameSceneManager.add(GameSceneManager.PLAY);
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (!PlayScene.tm.checkInFog(bounds)) {
            super.render(g2d);
        }
    }

}
