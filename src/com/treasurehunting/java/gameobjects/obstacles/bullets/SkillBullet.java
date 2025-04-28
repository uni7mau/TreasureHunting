package com.treasurehunting.java.gameobjects.obstacles.bullets;

import com.treasurehunting.java.gameobjects.entities.Entity;
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
            ExplodeBomb newBomb =
                    new ExplodeBomb(
                            owner,
                            250,
                            new Vector2f(pos.x + bounds.getWidth() / 2 - 125, pos.y + bounds.getHeight() / 2 - 125),
                            dmg*2,
                            400
                    );
            PlayScene.tobeAdded.get(Assets.explodeBombID).add(newBomb);
        }
    }

}
