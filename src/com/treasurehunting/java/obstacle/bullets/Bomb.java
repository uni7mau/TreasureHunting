package com.treasurehunting.java.obstacle.bullets;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.Mana;
import com.treasurehunting.java.obstacle.Obstacle;

import java.awt.*;

public class Bomb extends Obstacle {

    protected Vector2f startPos;
    protected double r;
    protected double activeTime;

    protected int dmg = 0;
    protected int explodeSpeed = 300;
    protected float force = 5f;

    protected Entity owner;

    public Bomb(Entity owner, SpriteSheet spriteSheet, int width, int height, Vector2f startPos, int dmg, int explodeSpeed) {
        super(spriteSheet, new Vector2f(startPos), width, height);

        this.owner = owner;

        this.startPos = startPos;
        this.r = width;
        this.dmg = dmg;
        this.explodeSpeed = explodeSpeed;
        activeTime = System.nanoTime();

        bounds = new AABB(startPos, (int) r);

        setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(0), 4);
    }

    @Override
    public void animate() {  }

    @Override
    public void activeEvent(GameObject go) {
        if (!(go instanceof Bullet || go instanceof Mana) && !(go.getClass() == owner.getClass())) {
            if (bounds.collides(go.getBounds())) {
                go.healthDec(
                        dmg,
                        force * (1 - go.getRes()),
                        0
                );
            }
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

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.red);
        g2d.drawOval((int) pos.getWorldVar().x, (int) pos.getWorldVar().y, (int) bounds.getRadius()*2, (int) bounds.getRadius()*2);
        g2d.drawImage(anim.getImage().image, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, (int) r*2, (int) r*2, null);
    }

}
