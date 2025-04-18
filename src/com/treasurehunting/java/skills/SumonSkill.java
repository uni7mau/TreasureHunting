package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;

import java.awt.*;

public abstract class SumonSkill extends Skill {

    protected String counterEffect = "";
    protected int bufferTime = 0;
    protected boolean canSum = false;
    protected boolean summon = false;

    public SumonSkill(Entity owner, String skillName, int dmg, int manaCost, int skillSpeed, int cooldown, String counterEffect) {
        super(owner, skillName, dmg, manaCost, skillSpeed, cooldown);

        this.counterEffect = counterEffect;
    }

    public void setBufferTime(int time) {
        this.bufferTime = time;
    }

    public abstract void sumItem();

    public void updateCanSum(double time) {
        if (activeTime / 1000000 + bufferTime < time / 1000000) {
            canSum = true;
        } else {
            canSum = false;
        }
    }

    @Override
    public void update(double time) {
        updateCanActive(time);
        updateActivating(time);
        if (request && canActive) {
            summon = true;
            owner.manaDec(manaCost);
            activeTime = System.nanoTime();
        }
        updateCanSum(time);

        if (summon && canSum) {
            sumItem();
            summon = false;
        }

        if (bufferTime != 0 && summon) {
            owner.freeze();
        }

        if (activating) {
            owner.setState(counterEffect, true);
        } else if (owner.getState(counterEffect) && owner.getAnimation().hasPlayedOnce() && !request) {
            owner.setState(counterEffect, false);
        }

        request = false;
    }

    @Override
    public void render(Graphics2D g2d) {

    }

}
