package com.treasurehunting.java.skills.abilities;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.gameobjects.obstacles.bullets.UltimateBullet;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.skills.SumonSkill;

import java.util.ArrayList;

public class UltimateEnergyWave extends SumonSkill {

    public UltimateEnergyWave(Entity owner) {
        super(owner, "Ultimate Energy Wave", 100, 500, 1000, 25000, "CASTMAGIC");
    }

    @Override
    public void sumItem() {
        if (!PlayScene.tobeAdded.containsKey(Assets.ultimateBulletID)) PlayScene.tobeAdded.put(Assets.ultimateBulletID, new ArrayList<>());
        PlayScene.tobeAdded.get(Assets.ultimateBulletID).add(
                new UltimateBullet(
                        owner,
                        3* Assets.TILE_SIZE, 3* Assets.TILE_SIZE,
                        new Vector2f(owner.getPos().x + owner.getBounds().getXOffset() - (3* Assets.TILE_SIZE / 2), owner.getPos().y + owner.getBounds().getYOffset() - (3* Assets.TILE_SIZE / 2)),
                        1000,
                        owner.getCurrDirection(),
                        500,
                        2000
                )
        );
    }

}
