package com.treasurehunting.java.obstacle.bullets;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.Obstacle;
import com.treasurehunting.java.scene.PlayScene;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Bomb extends Obstacle {

    protected Vector2f startPos;
    protected double r;
    protected double activeTime;

    protected int dmg = 0;
    protected int explodeSpeed = 300;
    protected float force = 5f;

    public Bomb(SpriteSheet spriteSheet, int width, int height, Vector2f startPos, int dmg, int explodeSpeed) {
        super(spriteSheet, new Vector2f(startPos), width, height);

        this.startPos = startPos;
        this.r = Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2) / 2);
        this.dmg = dmg;
        this.explodeSpeed = explodeSpeed;
        activeTime = System.nanoTime();

        bounds = new AABB(startPos, (int) r);

        setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(0), 4);
    }

    @Override
    public void animate() {  }

    @Override
    public void update(double time) {
        super.update(time);

        anim.update();
        for (Map.Entry<Integer, List<GameObject>> entry : PlayScene.gameObjects.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (PlayScene.gameObjects.get(i) instanceof Entity target) {
                    if (bounds.collides(target.getBounds())) {
                        target.healthDec(
                                (int) (dmg),
                                force * (1 - target.getRes()),
                                0 // TODO: check x, y to the enemy
                        );
                    }
                }
            }
        }
        if (anim.hasPlayedOnce()) {
            DESTROY_STATE = true;
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.red);
        g2d.drawOval((int) pos.getWorldVar().x, (int) pos.getWorldVar().y, (int) bounds.getRadius()*2, (int) bounds.getRadius()*2);
        g2d.drawImage(anim.getImage().image, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, (int) r*2, (int) r*2, null);
    }

}
