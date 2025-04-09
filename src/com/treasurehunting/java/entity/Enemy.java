package com.treasurehunting.java.entity;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.skills.Skill;

import java.awt.*;

public abstract class Enemy extends Entity {

    public Enemy(SpriteSheet spriteSheet, Vector2f origin, int width, int height, String name) {
        super(spriteSheet, origin, width, height, name);
    }

    public AABB getSense() { return sense; }

    public abstract void chase(Player player);
    public abstract void attack(Player player);

    private void resetPosition() {
        pos.resetOri();
        bounds.getPos().resetOri();
        sense.getPos().resetOri();

        setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(Assets.RIGHT), 10);
    }

    public void update(double time, Player player) {
        super.update(time);

        if (!DIE_STATE) {
            chase(player);
            attack(player);

            if (!FALLEN_STATE) {
                if (!tileCollision.collisionTile(dx, 0)) {
                    pos.x += dx;
                    bounds.getPos().x += dx;
                    sense.getPos().x += dx;
                    blockedX = false;
                } else {
                    blockedX = true;
                }
                if (!tileCollision.collisionTile(0, dy)) {
                    pos.y += dy;
                    bounds.getPos().y += dy;
                    sense.getPos().y += dy;
                    blockedY = false;
                } else {
                    blockedY = true;
                }

                tileCollision.normalTile(dx, 0);
                tileCollision.normalTile(0, dy);
            } else {
                blockedX = true;
                blockedY = true;
                if (FALLEN_STATE) {
                    resetPosition();
                    dx = 0;
                    dy = 0;
                    FALLEN_STATE = false;
                }
            }

            if (health == 0) {
                DIE_STATE = true;
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        if (!PlayScene.tm.checkInFog(bounds)) {
            super.render(g2d);
            for (int i = 0; i < skills.size(); i++) {
                Skill skill = skills.get(i);
                if (skill != null) {
                    skill.render(g2d);
                }
            }

            g2d.drawImage(
                    anim.getImage().image,
                    (int) pos.getWorldVar().x,
                    (int) pos.getWorldVar().y,
                    width,
                    height,
                    null
            );

            // Health Bar UI
            g2d.setColor(Color.red);
            g2d.fillRect(
                    (int) (pos.getWorldVar().x + bounds.getXOffset()),
                    (int) (pos.getWorldVar().y + height + 5),
                    (int) bounds.getWidth(),
                    5
            );
            g2d.setColor(Color.green);
            g2d.fillRect(
                    (int) (pos.getWorldVar().x + bounds.getXOffset()),
                    (int) (pos.getWorldVar().y + height + 5),
                    (int) (bounds.getWidth() * healthPercent),
                    5
            );
        }
    }

}