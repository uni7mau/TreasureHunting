package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.bullets.CritBullet;
import com.treasurehunting.java.obstacle.bullets.NormBullet;
import com.treasurehunting.java.states.PlayState;

import java.util.Random;

public class Shooting extends SumonSkill {

    private double lastShootTime = 0;

    public Shooting(Entity owner) {
        super(owner, "Shooting", 5, 1, 0, 100, 100, "SHOOTING");
    }

    // TODO: do this
    @Override
    public void summItem() {
        Random random = new Random();
        int value = random.nextInt(100);
        // Change by crit rate
        if (value < 50) { // 50% cho A
            PlayState.gameObjects.add(
                    new NormBullet(
                            20, 20,
                            new Vector2f(owner.getPos().x + owner.getBounds().getXOffset(), owner.getPos().y + owner.getBounds().getYOffset()),
                            500,
                            owner.getCurrDirection(),
                            owner.getAtk(),
                            300
                    )
            );
        } else {
            PlayState.gameObjects.add(
                    new CritBullet(
                            20, 20,
                            new Vector2f(owner.getPos().x + owner.getBounds().getXOffset(), owner.getPos().y + owner.getBounds().getYOffset()),
                            500,
                            owner.getCurrDirection(),
                            owner.getAtk() + dmg,
                            300
                    )
            );
        }
    }

    // This update is a bit different
    @Override
    public void update(double time) {
        updateCanActive(time);
        updateActivating(time);

        if (request && canActive) {
            Random random = new Random();
            int value = random.nextInt(100);
            // Change by crit rate
            if (value < 50) { // 50% cho A
                PlayState.gameObjects.add(
                        new NormBullet(
                                20, 20,
                                new Vector2f(owner.getPos().x + owner.getBounds().getXOffset(), owner.getPos().y + owner.getBounds().getYOffset()),
                                500,
                                owner.getCurrDirection(),
                                owner.getAtk(),
                                300
                        )
                );
            } else {
                PlayState.gameObjects.add(
                        new CritBullet(
                                20, 20,
                                new Vector2f(owner.getPos().x + owner.getBounds().getXOffset(), owner.getPos().y + owner.getBounds().getYOffset()),
                                500,
                                owner.getCurrDirection(),
                                owner.getAtk() + dmg,
                                300
                        )
                );
            }
            activeTime = System.nanoTime();
            lastShootTime = time;
        }

        if (activating) {
            if (time / 1000000 - lastShootTime / 1000000 > (double)skillSpeed/timeCast) {
                Random random = new Random();
                int value = random.nextInt(100);
                // Change by crit rate
                if (value < 50) { // 50% cho A
                    PlayState.gameObjects.add(
                            new NormBullet(
                                    20, 20,
                                    new Vector2f(owner.getPos().x + owner.getBounds().getXOffset(), owner.getPos().y + owner.getBounds().getYOffset()),
                                    500,
                                    owner.getCurrDirection(),
                                    owner.getAtk() + dmg,
                                    300
                            )
                    );
                } else {
                    PlayState.gameObjects.add(
                            new CritBullet(
                                    20, 20,
                                    new Vector2f(owner.getPos().x + owner.getBounds().getXOffset(), owner.getPos().y + owner.getBounds().getYOffset()),
                                    500,
                                    owner.getCurrDirection(),
                                    owner.getAtk() + dmg,
                                    300
                            )
                    );
                }
                lastShootTime = time;
            }
        }
    }

}
