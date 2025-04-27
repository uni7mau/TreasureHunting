package com.treasurehunting.java.obstacle.bullets;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.Chest;
import com.treasurehunting.java.obstacle.Mana;
import com.treasurehunting.java.obstacle.Obstacle;
import com.treasurehunting.java.obstacle.Portal;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Bullet extends Obstacle {

    protected boolean FLY_STATE = true; // for fly through the holes
    protected boolean UNBREAK_STATE = false; // for skipping walls, objects, ...
    protected boolean DESTROY_STATE = false; // for destroy tile

    protected Vector2f startPos;
    protected double overallDist;
    protected int direct;
    protected double activeTime;

    protected int dmg;
    protected int bulletSpeed;
    protected float force = 5f;

    protected Entity owner;

    public Bullet(Entity owner, SpriteSheet spriteSheet, int width, int height, Vector2f startPos, double overallDist, int direction, int dmg, int bulletSpeed) {
        super(spriteSheet, new Vector2f(startPos), width, height);

        this.owner = owner;

        this.startPos = startPos;
        this.overallDist = overallDist;
        this.direct = direction;
        this.dmg = dmg;
        this.bulletSpeed = bulletSpeed;
        activeTime = System.nanoTime();

        setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(0), bulletSpeed/100);
    }

    @Override
    public boolean getState(String state) {
        if (state.equals("FLY")) return FLY_STATE;
        else return super.getState(state);
    }

    @Override
    public void setState(String state, boolean b) {
        if (state.equals("FLY")) FLY_STATE = b;
        else super.setState(state, b);
    }

    public void setHitboxPosition(double distAfterUpdate) {
        addForce((float) distAfterUpdate, direct);
    }

    @Override
    public void animate() {
        // If there is more animation
    }

    @Override
    public void activeEvent(GameObject go) {
        if ( !(go instanceof Bullet | go instanceof Bomb || go instanceof Mana || go instanceof Portal || go instanceof Chest || go.getClass() == owner.getClass()) ) {
            if (bounds.collides(go.getBounds())) {
                go.healthDec(
                        dmg,
                        force * (1 - go.getRes()),
                        direct
                );
            }
        }
    }

    @Override
    public void update(double time) {
        super.update(time);

        double deltaT = ( time / 1000000 - activeTime / 1000000);
        activeTime = time;
        double distAfterUpdate = (deltaT * overallDist) / bulletSpeed;
        setHitboxPosition(distAfterUpdate);
        if (Vector2f.dist(startPos, pos) > overallDist) {
            DIE_STATE = true;
        } else {
            if (UNBREAK_STATE) {
                pos.x += dx + bonusDx;
                bounds.getPos().x += dx + bonusDx;
                sense.getPos().x += dx + bonusDx;

                pos.y += dy + bonusDy;
                bounds.getPos().y += dy + bonusDy;
                sense.getPos().y += dy + bonusDy;
            } else {
                if (!tileCollision.collisionTile(dx + bonusDx, 0)) {
                    pos.x += dx + bonusDx;
                    bounds.getPos().x += dx + bonusDx;
                    sense.getPos().x += dx + bonusDx;
                    DIE_STATE = false;
                } else {
                    DIE_STATE = true;
                }
                if (!DIE_STATE) {
                    if (!tileCollision.collisionTile(0, dy + bonusDy)) {
                        pos.y += dy + bonusDy;
                        bounds.getPos().y += dy + bonusDy;
                        sense.getPos().y += dy + bonusDy;
                        DIE_STATE = false;
                    } else {
                        DIE_STATE = true;
                    }
                }

                bonusDx = 0;
                bonusDy = 0;
            }

            if (DESTROY_STATE) {
                overallDist -= tileCollision.destroyTile(dx, dy)*50; // Need to calculate more effective
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        //Make a backup so that we can reset our graphics object after using it.
        AffineTransform backup = g2d.getTransform();
        //rx is the x coordinate for rotation, ry is the y coordinate for rotation, and angle
        //is the angle to rotate the image. If you want to rotate around the center of an image,
        //use the image's center x and y coordinates for rx and ry.
        AffineTransform a = AffineTransform.getRotateInstance(Math.toRadians((direct - 6) * 45), pos.getWorldVar().x + (double) width / 2, pos.getWorldVar().y + (double) height / 2);
        //Set our Graphics2D object to the transform
        g2d.setTransform(a);
        //Draw our image like normal
        g2d.drawImage(anim.getImage().image, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, width, height, null);
        //Reset our graphics object so we can draw with it again.
        g2d.setTransform(backup);

//        // Check middle bullet img:
//        g2d.setColor(Color.PINK);
//        g2d.drawLine(0, (int)pos.getWorldVar().y + height / 2, Preferences.GAME_WIDTH, (int)pos.getWorldVar().y + height / 2);
//        g2d.drawLine((int)pos.getWorldVar().x + width / 2, 0, (int)pos.getWorldVar().x + width / 2, Preferences.GAME_HEIGHT);
    }

}
