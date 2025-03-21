package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.bullets.SkillBullet;
import com.treasurehunting.java.states.PlayState;

public class BombShooting extends SumonSkill {

    public BombShooting(Entity owner) {
        super(owner, "Energy Bomb", 10, 1, 100, 500, 4000, "SHOOTING");
    }

    @Override
    public void summItem() {
        PlayState.gameObjects.add(
                new SkillBullet(
                        100, 100,
                        new Vector2f(owner.getPos().x + owner.getBounds().getXOffset() - (100/2), owner.getPos().y + owner.getBounds().getYOffset() - (100/2)),
                        600,
                        owner.getCurrDirection(),
                        owner.getAtk(),
                        500
                )
        );
    }

}
