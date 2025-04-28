package com.treasurehunting.java.gameobjects.obstacles.bullets;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;

public class ExplodeBomb extends Bomb {

    public ExplodeBomb(Entity owner, float radius, Vector2f startPos, int dmg, int explodeSpeed) {
        super(owner, Assets.explodeBomb, radius, startPos, dmg, explodeSpeed);

        spriteSheets.put(Assets.DIE, Assets.explodeBomb);

        anim.setActiveFrame(1, Assets.IDLE);
    }

}
