package com.treasurehunting.java.obstacle.bullets;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;

public class ExplodeBomb extends Bomb {

    public ExplodeBomb(int width, int height, Vector2f startPos, int dmg, int explodeSpeed) {
        super(Assets.explodeBomb, width, height, startPos, dmg, explodeSpeed);
    }

}
