package com.treasurehunting.java.obstacle;

import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.GameSceneManager;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.utils.GameSettings;

import java.awt.*;

public class Chest extends Obstacle {

    public Chest(Vector2f pos) {
        super(Assets.chestSS, pos, GameSettings.TILE_SIZE, GameSettings.TILE_SIZE);
    }

    @Override
    public void animate() { }

    @Override
    public void activeEvent(GameObject go) {
        if (go instanceof Player) {
            GameSceneManager.add(GameSceneManager.WIN);
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
