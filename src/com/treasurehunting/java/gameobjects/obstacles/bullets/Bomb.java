package com.treasurehunting.java.gameobjects.obstacles.bullets;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.gameobjects.GameObject;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.gameobjects.obstacles.affects.Chest;
import com.treasurehunting.java.gameobjects.obstacles.affects.Mana;
import com.treasurehunting.java.gameobjects.obstacles.Obstacle;
import com.treasurehunting.java.gameobjects.obstacles.affects.Portal;

public abstract class Bomb extends Obstacle {

    protected double activeTime;

    protected int dmg = 0;
    protected int explodeSpeed = 300;
    protected float force = 5f;

    protected Entity owner;

    public Bomb(Entity owner, SpriteSheet spriteSheet, float radius, Vector2f startPos, int dmg, int explodeSpeed) {
        super(spriteSheet, startPos, radius);

        this.owner = owner;

        bounds.setRadius(radius);
        bounds.getPos().flag();
        sense.setRadius(radius);
        sense.getPos().flag();

        this.dmg = dmg;
        this.explodeSpeed = explodeSpeed;
        activeTime = System.nanoTime();

        setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(0), explodeSpeed / 100);
    }

    @Override
    public void animate() {  }

    @Override
    public void activeEvent(GameObject go) {
        if ( !(go instanceof Bullet || go instanceof Bomb || go instanceof Mana || go instanceof Portal || go instanceof Chest || go.getClass() == owner.getClass()) ) {
            go.healthDec(
                    dmg,
                    force * (1 - go.getRes()),
                    0
            );
        }
    }

    @Override
    public void update(double time) {
        if (anim.hasPlayedOnce()) {
            DIE_STATE = true;
        } else {
            super.update(time);
        }
    }

}
