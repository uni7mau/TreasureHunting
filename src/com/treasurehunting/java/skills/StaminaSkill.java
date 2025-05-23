package com.treasurehunting.java.skills;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;

import java.awt.*;

public abstract class StaminaSkill extends Skill {

    protected String counterEffect = "";
    protected AABB hitBound;

    public StaminaSkill(Entity owner, String skillName, int dmg, int skillSpeed, int cooldown, String counterEffect) {
        super(owner, skillName, dmg, 0, skillSpeed, cooldown);

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

        if (request && canActive) {
            activeTime = time;
        }

        if (activating) {
            owner.setState(counterEffect, true);
            if (owner.getAnimation().checkActiveFrame(Assets.takeAnimId(counterEffect))) {
                for (int i = 0; i < targets.size(); i++) {
                    if (hitBound.collides(targets.get(i).getBounds())) {
                        targets.get(i).healthDec(
                                owner.getAtk() + dmg,
                                owner.getForce() * (1 - targets.get(i).getRes()),
                                owner.getCurrDirection()
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
//        if (activating) {
//            g2d.setColor(Color.red);
//            g2d.drawRect((int) (hitBound.getPos().getWorldVar().x + hitBound.getXOffset()), (int) (hitBound.getPos().getWorldVar().y + hitBound.getYOffset()), hitBound.getWidth(), hitBound.getHeight());
//        }
    }

}
