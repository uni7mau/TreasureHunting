package com.treasurehunting.java.obstacle.bullets;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;

public class ExplodeBomb extends Bomb {

    public ExplodeBomb(Entity owner, int width, int height, Vector2f startPos, int dmg, int explodeSpeed) {
        super(owner, Assets.explodeBomb, width, height, startPos, dmg, explodeSpeed);

        spriteSheets.put(Assets.DIE, Assets.explodeBomb);
    }

}
