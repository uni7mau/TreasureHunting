package com.treasurehunting.java.obstacle.bullets;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;

import java.util.ArrayList;

public class SkillBullet extends Bullet {

    public SkillBullet(Entity owner, int width, int height, Vector2f startPos, double overallDist, int direction, int dmg, int bulletSpeed) {
        super(owner, Assets.skillBullet, width, height, startPos, overallDist, direction, dmg, bulletSpeed);

        UNBREAK_STATE = true;

        spriteSheets.put(Assets.DIE, Assets.skillBullet);
    }

    @Override
    public void update(double time) {
        super.update(time);

        if (DIE_STATE) {
            if (!PlayScene.tobeAdded.containsKey(Assets.explodeBombID)) PlayScene.tobeAdded.put(Assets.explodeBombID, new ArrayList<>());
            PlayScene.tobeAdded.get(Assets.explodeBombID).add(
                    new ExplodeBomb(
                            owner,
                            2* Assets.TILE_SIZE, 2* Assets.TILE_SIZE,
                            new Vector2f(bounds.getPos().x + bounds.getXOffset() + bounds.getWidth() / 2 - 2* Assets.TILE_SIZE, bounds.getPos().y + bounds.getYOffset() + bounds.getHeight() / 2 - 2* Assets.TILE_SIZE),
                            dmg*2,
                            3000
                    )
            );
        }
    }

}
