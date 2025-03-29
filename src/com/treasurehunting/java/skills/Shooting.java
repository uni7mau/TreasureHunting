package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.bullets.CritBullet;
import com.treasurehunting.java.obstacle.bullets.NormBullet;
import com.treasurehunting.java.states.PlayState;

import java.util.Random;

public class Shooting extends SumonSkill {

    public Shooting(Entity owner) {
        super(owner, "Shooting", 5, 0, 100, 100, "SHOOTING", Assets.playerSkillIcon.getSubimage(0, 0, 20, 20));
    }

    // TODO: do this
    @Override
    public void summItem() {
        float adjustX = 0;
        float adjustY = 0;

        int targetDirect = owner.getCurrDirection();
        if (targetDirect == 0) {
            adjustX = 5;
            adjustY = 25;
        } else if (targetDirect == 1) {
            adjustX = -23;
            adjustY = 5;
        } else if (targetDirect == 2) {
            adjustX = -33;
            adjustY = -17;
        } else if (targetDirect == 3) {
            adjustX = -17;
            adjustY = -34;
        } else if (targetDirect == 4) {
            adjustX = 17;
            adjustY = -50;
        } else if (targetDirect == 5) {
            adjustX = 44;
            adjustY = -30;
        } else if (targetDirect == 6) {
            adjustX = owner.getBounds().getWidth() + 17;
            adjustY = -17;
        } else if (targetDirect == 7) {
            adjustX = 43;
            adjustY = 10;
        }


        Random random = new Random();
        int value = random.nextInt(100);
        // Change by crit rate
        if (value < 50) { // 50% cho A
            PlayState.gameObjects.add(
                    new NormBullet(
                            20, 20,
                            new Vector2f(
                                    owner.getPos().x + owner.getBounds().getXOffset() + adjustX,
                                    owner.getPos().y + owner.getBounds().getYOffset() + adjustY
                            ),
                            500,
                            targetDirect,
                            owner.getAtk(),
                            300
                    )
            );
        } else {
            PlayState.gameObjects.add(
                    new CritBullet(
                            20, 20,
                            new Vector2f(
                                    owner.getPos().x + owner.getBounds().getXOffset() + adjustX,
                                    owner.getPos().y + owner.getBounds().getYOffset() + adjustY
                            ),
                            500,
                            targetDirect,
                            owner.getAtk() + dmg,
                            300
                    )
            );
        }
    }

}
