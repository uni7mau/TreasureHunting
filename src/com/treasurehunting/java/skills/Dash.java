package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.scene.PlayScene;

public class Dash extends StaminaSkill {

    private double camBuffDuration = 800;
    private int force = 5;

    public Dash(Entity owner) {
        super(owner, "Dash", 0, 400, 1500, "DASH");
    }

    @Override
    public void updateHitBoxDirection() {
        // No hit bound needed
    }

    private void move(Entity owner) {
        if (!owner.getState("FALLEN")) {
            owner.addForce(force, owner.getCurrDirection());
        }
    }

    @Override
    public void update(double time) {
        super.update(time);

        if (activating) { move(owner); }

        if ((activeTime / 1000000) < (time / 1000000) - camBuffDuration) {
            PlayScene.cam.setMaxSpeed(owner.getMaxSpeed());
        } else {
            PlayScene.cam.setMaxSpeed(owner.getMaxSpeed() + 3f);
        }
    }

}
