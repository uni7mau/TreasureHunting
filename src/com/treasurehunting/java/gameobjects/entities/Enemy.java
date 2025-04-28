package com.treasurehunting.java.gameobjects.entities;

import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.skills.Skill;

import java.awt.*;

public abstract class Enemy extends Entity {

    public Enemy(SpriteSheet spriteSheet, Vector2f origin, int width, int height, String name) {
        super(spriteSheet, origin, width, height, name);
    }

    public abstract void chase(Player player);
    public abstract void attack(Player player);

    public void update(double time, Player player) {
        super.update(time);

        if (!(health == 0)) {
            chase(player);
            attack(player);
            loadPos();
        }
    }

    @Override
    public void freeze() {
        // Eat the method
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