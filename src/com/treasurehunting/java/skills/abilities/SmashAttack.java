package com.treasurehunting.java.skills.abilities;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.skills.StaminaSkill;

public class SmashAttack extends StaminaSkill {

    public SmashAttack(Entity owner) {
        super(owner, "Smash", owner.getAtk(), 1000, 3000, "SMASH");
    }

    @Override
    public void updateHitBoxDirection() {
        hitBound.setWidth(2 * owner.getBounds().getWidth());
        hitBound.setHeight(2 * owner.getBounds().getHeight());
        hitBound.setXOffset(owner.getBounds().getXOffset() + (float) owner.getBounds().getWidth() / 2 - (float) hitBound.getWidth() / 2);
        hitBound.setYOffset(owner.getBounds().getYOffset() + (float) owner.getBounds().getHeight() / 2 - (float) hitBound.getHeight() / 2);
    }

}
