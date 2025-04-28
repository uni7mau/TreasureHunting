package com.treasurehunting.java.skills.abilities;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.gameobjects.obstacles.bullets.CritBullet;
import com.treasurehunting.java.gameobjects.obstacles.bullets.NormBullet;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.skills.SumonSkill;

import java.util.ArrayList;
import java.util.Random;

public class Shooting extends SumonSkill {

    public Shooting(Entity owner) {
        super(owner, "Shooting", 5, 0, 25, 25, "SHOOTING");
    }

    // TODO: do this
    @Override
    public void sumItem() {
        if (!PlayScene.tobeAdded.containsKey(Assets.normBulletID)) PlayScene.tobeAdded.put(Assets.normBulletID, new ArrayList<>());
        if (!PlayScene.tobeAdded.containsKey(Assets.critBulletID)) PlayScene.tobeAdded.put(Assets.critBulletID, new ArrayList<>());

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
            PlayScene.tobeAdded.get(Assets.normBulletID).add(
                    new NormBullet(
                            owner,
                            20, 20,
                            new Vector2f(
                                    owner.getPos().x + owner.getBounds().getXOffset() + adjustX,
                                    owner.getPos().y + owner.getBounds().getYOffset() + adjustY
                            ),
                            400,
                            targetDirect,
                            owner.getAtk(),
                            300
                    )
            );
        } else {
            PlayScene.tobeAdded.get(Assets.critBulletID).add(
                    new CritBullet(
                            owner,
                            25, 25,
                            new Vector2f(
                                    owner.getPos().x + owner.getBounds().getXOffset() + adjustX,
                                    owner.getPos().y + owner.getBounds().getYOffset() + adjustY
                            ),
                            450,
                            targetDirect,
                            owner.getAtk() + dmg,
                            200
                    )
            );
        }
    }

}
