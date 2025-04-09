package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;

import java.awt.*;

public abstract class SumonSkill extends Skill {

    protected String counterEffect = "";

    public SumonSkill(Entity owner, String skillName, int dmg, int manaCost, int skillSpeed, int cooldown, String counterEffect) {
        super(owner, skillName, dmg, manaCost, skillSpeed, cooldown);

        this.counterEffect = counterEffect;
    }

    public abstract void sumItem();

    @Override
    public void update(double time) {
        updateCanActive(time);
        updateActivating(time);
        if (request && canActive) {
            sumItem();
            owner.manaDec(manaCost);
            activeTime = time;
        }

        // Nếu hết anim mà không nhận được request => state = false

        if (activating) {
            owner.setState(counterEffect, true);
        } else if (owner.getAnimation().hasPlayedOnce()) {
            owner.setState(counterEffect, false);
        }

        request = false;
    }

    @Override
    public void render(Graphics2D g2d) {

    }

}
