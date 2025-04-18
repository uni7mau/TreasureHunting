package com.treasurehunting.java.obstacle;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.utils.GameSettings;

import java.awt.*;

public class Trap extends Obstacle {

    public Trap(Vector2f pos) {
        super(Assets.trapSS, pos, GameSettings.TILE_SIZE, GameSettings.TILE_SIZE);

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
        g2d.setColor(Color.white);
        g2d.drawRect((int) bounds.getPos().getWorldVar().x, (int) bounds.getPos().getWorldVar().y, bounds.getWidth(), bounds.getHeight());

        if (!PlayScene.tm.checkInFog(bounds)) {
            super.render(g2d);
        }
    }

}
