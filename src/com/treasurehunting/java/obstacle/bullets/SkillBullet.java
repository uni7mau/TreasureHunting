package com.treasurehunting.java.obstacle.bullets;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.states.PlayState;

public class SkillBullet extends Bullet {

    public SkillBullet(int width, int height, Vector2f startPos, double overallDist, int direction, int dmg, int bulletSpeed) {
        super(Assets.skillBullet, width, height, startPos, overallDist, direction, dmg, bulletSpeed);
    }

    @Override
    public void update(double time) {
        super.update(time);
        if (Vector2f.dist(startPos, pos) > overallDist) {
            PlayState.gameObjects.add(new ExplodeBomb(100, 100, new Vector2f(pos.x - (float)width / 2, pos.y - (float)height / 2), 100, 3000));
        }
    }

}
