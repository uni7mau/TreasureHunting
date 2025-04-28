package com.treasurehunting.java.gameobjects.obstacles.affects;

import com.treasurehunting.java.gameobjects.GameObject;
import com.treasurehunting.java.gameobjects.entities.Player;
import com.treasurehunting.java.gameobjects.obstacles.Obstacle;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.GameSceneManager;
import com.treasurehunting.java.scene.PlayScene;

import java.awt.*;

public class Chest extends Obstacle {

    public Chest(Vector2f pos) {
        super(Assets.chestSS, pos, Assets.TILE_SIZE - 1, Assets.TILE_SIZE - 1);
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
