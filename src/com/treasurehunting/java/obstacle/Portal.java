package com.treasurehunting.java.obstacle;

import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.GameSceneManager;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.utils.GameSettings;

import java.awt.*;

public class Portal extends Obstacle {

    public Portal(Vector2f pos) {
        super(Assets.portalSS, pos, GameSettings.TILE_SIZE, GameSettings.TILE_SIZE);

        anim.setDelay(5);
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
