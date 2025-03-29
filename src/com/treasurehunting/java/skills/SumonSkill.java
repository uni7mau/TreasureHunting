package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class SumonSkill extends Skill {

    protected String counterEffect = "";

    public SumonSkill(Entity owner, String skillName, int dmg, int manaCost, int skillSpeed, int cooldown, String counterEffect, BufferedImage icon) {
        super(owner, skillName, dmg, manaCost, skillSpeed, cooldown, icon);

        this.counterEffect = counterEffect;
    }

    public abstract void summItem();

    @Override
    public void update(double time) {
        updateCanActive(time);
        updateActivating(time);
        if (request && canActive && owner.getMana() >= manaCost) {
            owner.setState(counterEffect, true);
            summItem();
            owner.manaDec(manaCost);
            activeTime = time;
        } else if (owner.getAnimation().hasPlayedOnce()) {
            owner.setState(counterEffect, false);
        }
        request = false;
    }

    @Override
    public void render(Graphics2D g2d) {

    }

}
