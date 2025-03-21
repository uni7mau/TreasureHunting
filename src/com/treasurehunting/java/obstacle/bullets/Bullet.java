package com.treasurehunting.java.obstacle.bullets;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.Obstacle;
import com.treasurehunting.java.states.GameStateManager;
import com.treasurehunting.java.states.PlayState;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Bullet extends Obstacle {

    protected Vector2f startPos;
    protected double overallDist;
    protected int direction = 6;
    protected double activeTime;

    protected int dmg = 0;
    protected int bulletSpeed = 300;
    protected float force = 5f;

    public Bullet(SpriteSheet spriteSheet, int width, int height, Vector2f startPos, double overallDist, int direction, int dmg, int bulletSpeed) {
        super(spriteSheet, new Vector2f(startPos), width, height);

        this.startPos = startPos;
        this.overallDist = overallDist;
        this.direction = direction;
        this.dmg = dmg;
        this.bulletSpeed = bulletSpeed;
        activeTime = System.nanoTime();

        setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(0), bulletSpeed/100);
    }

    // s = v * delta t
    public void setHitboxPosition(double distAfterUpdate) {
        if (direction == Assets.DOWN) {  // down
            pos.y += (float)distAfterUpdate;
            sense.getPos().y += (float) distAfterUpdate;
            bounds.getPos().y += (float) distAfterUpdate;
        } else if (direction == Assets.LEFTDOWN) { // leftdown
            pos.x -= (float)( Math.cos(45)*distAfterUpdate );
            pos.y += (float)( Math.cos(45)*distAfterUpdate );
            sense.getPos().x -= (float)( Math.cos(45)*distAfterUpdate );
            sense.getPos().y += (float)( Math.cos(45)*distAfterUpdate );
            bounds.getPos().x -= (float)( Math.cos(45)*distAfterUpdate );
            bounds.getPos().y += (float)( Math.cos(45)*distAfterUpdate );
        } else if (direction == Assets.LEFT) { // left
            pos.x -= (float)distAfterUpdate;
            sense.getPos().x -= (float)distAfterUpdate;
            bounds.getPos().x -= (float)distAfterUpdate;
        } else if (direction == Assets.LEFTUP) { // leftup
            pos.x -= (float)( Math.cos(45)*distAfterUpdate );
            pos.y -= (float)( Math.cos(45)*distAfterUpdate );
            sense.getPos().x -= (float)( Math.cos(45)*distAfterUpdate );
            sense.getPos().y -= (float)( Math.cos(45)*distAfterUpdate );
            bounds.getPos().x -= (float)( Math.cos(45)*distAfterUpdate );
            bounds.getPos().y -= (float)( Math.cos(45)*distAfterUpdate );
        } else if (direction == Assets.UP) { // up
            pos.y -= (float)distAfterUpdate;
            sense.getPos().y -= (float)distAfterUpdate;
            bounds.getPos().y -= (float)distAfterUpdate;
        } else if (direction == Assets.RIGHTUP) { // rightup
            pos.x += (float)( Math.cos(45)*distAfterUpdate );
            pos.y -= (float)( Math.cos(45)*distAfterUpdate );
            sense.getPos().x += (float)( Math.cos(45)*distAfterUpdate );
            sense.getPos().y -= (float)( Math.cos(45)*distAfterUpdate );
            bounds.getPos().x += (float)( Math.cos(45)*distAfterUpdate );
            bounds.getPos().y -= (float)( Math.cos(45)*distAfterUpdate );
        } else if (direction == Assets.RIGHT) { // right
            pos.x += (float)distAfterUpdate;
            sense.getPos().x += (float)distAfterUpdate;
            bounds.getPos().x += (float)distAfterUpdate;
        } else if (direction == Assets.RIGHTDOWN) { // rightdown
            pos.x += (float)( Math.cos(45)*distAfterUpdate );
            pos.y += (float)( Math.cos(45)*distAfterUpdate );
            sense.getPos().x += (float)( Math.cos(45)*distAfterUpdate );
            sense.getPos().y += (float)( Math.cos(45)*distAfterUpdate );
            bounds.getPos().x += (float)( Math.cos(45)*distAfterUpdate );
            bounds.getPos().y += (float)( Math.cos(45)*distAfterUpdate );
        }
    }

    @Override
    public void animate() {
        // If there is more animation
    }

    public void update(double time) {
        super.update(time);

        double deltaT = ( time / 1000000 - activeTime / 1000000);
        activeTime = time;
        double distAfterUpdate = (deltaT * overallDist) / bulletSpeed;
        setHitboxPosition(distAfterUpdate);
        for (int i = 0; i < PlayState.gameObjects.size(); i++) {
            if (PlayState.gameObjects.get(i) instanceof Entity target) {
                if (bounds.collides(target.getBounds())) {
                    target.healthDec(
                            (int) (dmg),
                            force * (1 - target.getRes()),
                            direction
                    );
                }
            }
        }

        if (Vector2f.dist(startPos, pos) > overallDist) {
            DESTROY_STATE = true;
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (GameStateManager.cam.getBounds().collides(bounds)) {
            g2d.setColor(Color.red);
            g2d.drawRect((int) (pos.getWorldVar().x + bounds.getXOffset()), (int) (pos.getWorldVar().y + bounds.getYOffset()), bounds.getWidth(), bounds.getHeight());

            //Make a backup so that we can reset our graphics object after using it.
            AffineTransform backup = g2d.getTransform();
            //rx is the x coordinate for rotation, ry is the y coordinate for rotation, and angle
            //is the angle to rotate the image. If you want to rotate around the center of an image,
            //use the image's center x and y coordinates for rx and ry.
            AffineTransform a = AffineTransform.getRotateInstance(Math.toRadians((direction - 6) * 45), pos.getWorldVar().x + (double) width / 2, pos.getWorldVar().y + (double) height / 2);
            //Set our Graphics2D object to the transform
            g2d.setTransform(a);
            //Draw our image like normal
            g2d.drawImage(anim.getImage().image, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, width, height, null);
            //Reset our graphics object so we can draw with it again.
            g2d.setTransform(backup);

//        // Check midle bullet img:
//        g2d.setColor(Color.PINK);
//        g2d.drawLine(0, (int)pos.getWorldVar().y + height / 2, Preferences.GAME_WIDTH, (int)pos.getWorldVar().y + height / 2);
//        g2d.drawLine((int)pos.getWorldVar().x + width / 2, 0, (int)pos.getWorldVar().x + width / 2, Preferences.GAME_HEIGHT);
        }
    }

}
