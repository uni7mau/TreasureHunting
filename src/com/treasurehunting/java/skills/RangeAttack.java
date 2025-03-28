package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.graphics.Assets;

public class RangeAttack extends StaminaSkill {

    public RangeAttack(Entity owner) {
        super(owner, "Attack", 5, 300, 3000, "RANGEATTACK", Assets.playerSkillIcon.getSubimage(0, 0, 20, 20));
    }

    @Override
    public void updateHitBoxDirection() {
        if (owner.getCurrDirection() == Assets.DOWN && !activating) {
            hitBound.setWidth(2 * owner.getBounds().getHeight());
            hitBound.setHeight((float) (3 * owner.getBounds().getWidth()) / 2);
            hitBound.setXOffset(owner.getBounds().getXOffset() + (float) owner.getBounds().getWidth() / 2 - (float) hitBound.getWidth() / 2);
            hitBound.setYOffset(owner.getBounds().getYOffset() + (float) owner.getBounds().getHeight() / 2 - (float) (1 * hitBound.getHeight()) / 3);
        } else if (owner.getCurrDirection() == Assets.LEFT && !activating) {
            hitBound.setWidth((float) (3 * owner.getBounds().getWidth()) / 2);
            hitBound.setHeight(2 * owner.getBounds().getHeight());
            hitBound.setXOffset(owner.getBounds().getXOffset() + (float) owner.getBounds().getWidth() / 2 - (float) (2 * hitBound.getWidth()) / 3);
            hitBound.setYOffset(owner.getBounds().getYOffset() + (float) owner.getBounds().getHeight() / 2 - (float) hitBound.getHeight() / 2);
        } else if (owner.getCurrDirection() == Assets.UP && !activating) {
            hitBound.setWidth(2 * owner.getBounds().getHeight());
            hitBound.setHeight((float) (3 * owner.getBounds().getWidth()) / 2);
            hitBound.setXOffset(owner.getBounds().getXOffset() + (float) owner.getBounds().getWidth() / 2 - (float) hitBound.getWidth() / 2);
            hitBound.setYOffset(owner.getBounds().getYOffset() + (float) owner.getBounds().getHeight() / 2 - (float) (2 * hitBound.getHeight()) / 3);
        } else if (owner.getCurrDirection() == Assets.RIGHT && !activating) {
            hitBound.setWidth((float) (3 * owner.getBounds().getWidth()) / 2);
            hitBound.setHeight(2 * owner.getBounds().getHeight());
            hitBound.setXOffset(owner.getBounds().getXOffset() + (float) owner.getBounds().getWidth() / 2 - (float) (1 * hitBound.getWidth()) / 3);
            hitBound.setYOffset(owner.getBounds().getYOffset() + (float) owner.getBounds().getHeight() / 2 - (float) hitBound.getHeight() / 2);
        }
    }

}
