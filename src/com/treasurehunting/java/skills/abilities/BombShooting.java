package com.treasurehunting.java.skills.abilities;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.gameobjects.obstacles.bullets.SkillBullet;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.skills.SumonSkill;

import java.util.ArrayList;

public class BombShooting extends SumonSkill {

    public BombShooting(Entity owner) {
        super(owner, "Energy Bomb", 50, 100, 500, 4000, "THROWBOMB");
    }

    @Override
    public void sumItem() {
        if (!PlayScene.tobeAdded.containsKey(Assets.bombBulletID)) PlayScene.tobeAdded.put(Assets.bombBulletID, new ArrayList<>());

        PlayScene.tobeAdded.get(Assets.bombBulletID).add(
                new SkillBullet(
                        owner,
                        Assets.TILE_SIZE, Assets.TILE_SIZE,
                        new Vector2f(owner.getPos().x + owner.getBounds().getXOffset() + owner.getBounds().getWidth() / 2 - (Assets.TILE_SIZE / 2), owner.getPos().y + owner.getBounds().getYOffset() + owner.getBounds().getHeight() / 2  - (Assets.TILE_SIZE / 2)),
                        600,
                        owner.getCurrDirection(),
                        owner.getAtk() + dmg,
                        500
                )
        );
    }

}
