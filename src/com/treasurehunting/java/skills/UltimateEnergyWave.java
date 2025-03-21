package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.bullets.UltimateBullet;
import com.treasurehunting.java.states.PlayState;

public class UltimateEnergyWave extends SumonSkill {

    public UltimateEnergyWave(Entity owner) {
        super(owner, "Ultimate Energy Wave", 100, 1, 500, 500, 25000, "CASTMAGIC");
    }

    @Override
    public void summItem() {
        PlayState.gameObjects.add(
                new UltimateBullet(
                        200, 200,
                        new Vector2f(owner.getPos().x + owner.getBounds().getXOffset() - (200 / 2), owner.getPos().y + owner.getBounds().getYOffset() - (200 / 2)),
                        600,
                        owner.getCurrDirection(),
                        500,
                        2000
                )
        );
    }

}
