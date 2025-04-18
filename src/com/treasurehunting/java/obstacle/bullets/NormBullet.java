package com.treasurehunting.java.obstacle.bullets;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;

public class NormBullet extends Bullet {

    // Square bullet
    public NormBullet(Entity owner, int width, int height, Vector2f startPos, double overallDist, int direction, int dmg, int bulletSpeed) {
        super(owner, Assets.normBullet, width, height, startPos, overallDist, direction, dmg, bulletSpeed);

        spriteSheets.put(Assets.DIE, Assets.normBullet);
    }

}
