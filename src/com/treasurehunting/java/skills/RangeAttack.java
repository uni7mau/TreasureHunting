package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.utils.GameSettings;

public class RangeAttack extends StaminaSkill {

    public RangeAttack(Entity owner, int delay) {
        super(
                owner,
                "Attack",
                10,
                (owner.getSpriteSheet(Assets.RANGEATTACK).getSpriteRow(0).length - 1) * delay * (1000 / GameSettings.GAME_HERTZ),
                3000,
                "RANGEATTACK"
        );
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
