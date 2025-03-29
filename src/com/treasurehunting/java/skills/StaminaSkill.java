package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class StaminaSkill extends Skill {

    protected String counterEffect = "";
    protected AABB hitBound;

    public StaminaSkill(Entity owner, String skillName, int dmg, int skillSpeed, int cooldown, String counterEffect, BufferedImage icon) {
        super(owner, skillName, dmg, 0, skillSpeed, cooldown, icon);

        hitBound = new AABB(
                new Vector2f(owner.getPos()),
                owner.getWidth(),
                owner.getHeight()
        );
        this.counterEffect = counterEffect;
    }

    public AABB getHitBound() {
        return hitBound;
    }

    public abstract void updateHitBoxDirection();

    @Override
    public void update(double time) {
        hitBound.getPos().setVector(owner.getPos());
        updateCanActive(time);
        updateActivating(time);
        updateHitBoxDirection();

        for (int i = 0; i < targets.size(); i++) {
            if (hitBound.collides(targets.get(i).getBounds()) && !targets.get(i).getState("INVINCIBLE")) {
                request = true;
            }
        }

        if (request && canActive) {
            activeTime = time;
        }

        if (activating) {
            owner.setState(counterEffect, true);
            for (int i = 0; i < targets.size(); i++) {
                if (owner.getAnimation().getCurrFrame() == owner.getSpriteSheet(Assets.takeAnimId(counterEffect)).getSpriteRow(owner.getCurrDirection()).length / 2) {
                    if (hitBound.collides(targets.get(i).getBounds())) {
                        targets.get(i).healthDec(
                                (int) (dmg),
                                owner.getForce() * (1 - targets.get(i).getRes()),
                                owner.getCurrDirection() // TODO: check x, y to the enemy
                        );
                    }
                }
            }
        } else {
            if (owner.getState(counterEffect) && owner.getAnimation().hasPlayedOnce()) {
                owner.setState(counterEffect, false);
            }
        }
        request = false;
    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.setColor(Color.red);
        g2d.drawRect((int) (hitBound.getPos().getWorldVar().x + hitBound.getXOffset()), (int) (hitBound.getPos().getWorldVar().y + hitBound.getYOffset()), hitBound.getWidth(), hitBound.getHeight());
    }

}
