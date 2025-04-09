package com.treasurehunting.java.skills;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.ui.Button;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class Skill {

    protected BufferedImage icon;
    protected String name;
    protected int dmg;
    protected int manaCost;
    protected int cooldown;

    protected boolean request = false;
    protected int skillSpeed;
    protected double activeTime;
    protected boolean canActive = true;
    protected boolean activating = false;

    protected ArrayList<Entity> targets;
    protected Entity owner;

    public Skill(Entity owner, String skillName, int dmg, int manaCost, int skillSpeed, int cooldown) {
        this.owner = owner;
        this.name = skillName;
        this.dmg = dmg;
        this.manaCost = manaCost;
        this.cooldown = cooldown;
        this.skillSpeed = skillSpeed;

        targets = new ArrayList<>();
    }

    public void setTargetEnemy(Entity entity) {
        targets.add(entity);
    }

    public String getName() { return name; }
    public BufferedImage getIcon() { return icon; }
    public int getDuration() { return skillSpeed; }
    public int getCooldown() { return cooldown; }

    public void updateActivating(double time) {
        if ((activeTime / 1000000) < (time / 1000000) - skillSpeed) {
            activating = false;
        } else {
            activating = true;
        }
    }

    public void updateCanActive(double time) {
        if ((activeTime / 1000000) + cooldown > (time / 1000000) || owner.getMana() < manaCost) {
            canActive = false;
        } else {
            canActive = true;
        }
    }

    public boolean isActivating() { return activating; }

    public boolean isCanActive() { return canActive; }

    public void gainSignal() { request = true; }
    public void stopSignal() { request = false; }

    public abstract void update(double time);
    public abstract void render(Graphics2D g2d);

}
