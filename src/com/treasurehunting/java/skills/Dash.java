package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.states.GameStateManager;

public class Dash extends StaminaSkill {

    double camBuffDuration = 500;

    public Dash(Entity owner) {
        super(owner, "Dash", 0, 400, 1000, "DASH", Assets.playerSkillIcon.getSubimage(0, 0, 20, 20));
    }

    @Override
    public void updateHitBoxDirection() {
        // No hit bound needed
    }

    private void move(Entity owner) {
        if (!owner.getState("FALLEN")) {
            if (owner instanceof Player) {
                if (owner.getCurrDirection() == Assets.DOWN && !owner.getTc().collisionTile(0, 5f)) {
                    owner.getPos().setY(owner.getPos().y + 5f);
                    owner.getBounds().getPos().y += 5f;
                    owner.getSense().getPos().y += 5f;
                } else if (owner.getCurrDirection() == Assets.LEFTDOWN && !owner.getTc().collisionTile(-5f, 5f)) {
                    owner.getPos().setX(owner.getPos().x - 5f);
                    owner.getPos().setY(owner.getPos().y + 5f);
                    owner.getBounds().getPos().x -= 5f;
                    owner.getBounds().getPos().y += 5f;
                    owner.getSense().getPos().x -= 5f;
                    owner.getSense().getPos().y += 5f;
                } else if (owner.getCurrDirection() == Assets.LEFT && !owner.getTc().collisionTile(-5f, 0)) {
                    owner.getPos().setX(owner.getPos().x - 5f);
                    owner.getBounds().getPos().x -= 5f;
                    owner.getSense().getPos().x -= 5f;
                } else if (owner.getCurrDirection() == Assets.LEFTUP && !owner.getTc().collisionTile(-5f, -5f)) {
                    owner.getPos().setX(owner.getPos().x - 5f);
                    owner.getPos().setY(owner.getPos().y - 5f);
                    owner.getBounds().getPos().x -= 5f;
                    owner.getBounds().getPos().y -= 5f;
                    owner.getSense().getPos().x -= 5f;
                    owner.getSense().getPos().y -= 5f;
                } else if (owner.getCurrDirection() == Assets.UP && !owner.getTc().collisionTile(0, -5f)) {
                    owner.getPos().setY(owner.getPos().y - 5f);
                    owner.getBounds().getPos().y -= 5f;
                    owner.getSense().getPos().y -= 5f;
                } else if (owner.getCurrDirection() == Assets.RIGHTUP && !owner.getTc().collisionTile(5f, -5f)) {
                    owner.getPos().setX(owner.getPos().x + 5f);
                    owner.getPos().setY(owner.getPos().y - 5f);
                    owner.getBounds().getPos().x += 5f;
                    owner.getBounds().getPos().y -= 5f;
                    owner.getSense().getPos().x += 5f;
                    owner.getSense().getPos().y -= 5f;
                } else if (owner.getCurrDirection() == Assets.RIGHT && !owner.getTc().collisionTile(5f, 0)) {
                    owner.getPos().setX(owner.getPos().x + 5f);
                    owner.getBounds().getPos().x += 5f;
                    owner.getSense().getPos().x += 5f;
                } else if (owner.getCurrDirection() == Assets.RIGHTDOWN && !owner.getTc().collisionTile(5f, 5f)) {
                    owner.getPos().setX(owner.getPos().x + 5f);
                    owner.getPos().setY(owner.getPos().y + 5f);
                    owner.getBounds().getPos().x += 5f;
                    owner.getBounds().getPos().y += 5f;
                    owner.getSense().getPos().x += 5f;
                    owner.getSense().getPos().y += 5f;
                }
            }
        }
    }

    @Override
    public void update(double time) {
        super.update(time);

        if (activating) {
            move(owner);
        }

        if ((activeTime / 1000000) < (time / 1000000) - camBuffDuration) {
            GameStateManager.cam.setMaxSpeed(owner.getMaxSpeed());
        } else {
            GameStateManager.cam.setMaxSpeed(10f);
        }
    }

}
