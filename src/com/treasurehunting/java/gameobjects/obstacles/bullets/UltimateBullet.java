package com.treasurehunting.java.gameobjects.obstacles.bullets;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;

public class UltimateBullet extends Bullet {

    public UltimateBullet(Entity owner, int width, int height, Vector2f startPos, double overallDist, int direction, int dmg, int bulletSpeed) {
        super(owner, Assets.ultimateBullet, width, height, startPos, overallDist, direction, dmg, bulletSpeed);

        anim.setDelay(4);

        UNBREAK_STATE = true;
        DESTROY_STATE = true;

        spriteSheets.put(Assets.DIE, Assets.ultimateBullet);
    }

}
