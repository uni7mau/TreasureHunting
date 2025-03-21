package com.treasurehunting.java.obstacle.bullets;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;

public class CritBullet extends Bullet {

    // Square bullet
    public CritBullet(int width, int height, Vector2f startPos, double overallDist, int direction, int dmg, int bulletSpeed) {
        super(Assets.critBullet, width, height, startPos, overallDist,  direction, dmg, bulletSpeed);
    }

}
