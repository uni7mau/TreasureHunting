package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.bullets.UltimateBullet;
import com.treasurehunting.java.scene.PlayScene;

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
                        200, 200,
                        new Vector2f(owner.getPos().x + owner.getBounds().getXOffset() - (200 / 2), owner.getPos().y + owner.getBounds().getYOffset() - (200 / 2)),
                        1000,
                        owner.getCurrDirection(),
                        500,
                        2000
                )
        );
    }

}
