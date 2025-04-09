package com.treasurehunting.java.entity;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.Obstacle;
import com.treasurehunting.java.skills.Skill;
import com.treasurehunting.java.utils.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class Entity extends GameObject {

    protected boolean IDLE_STATE = true;
    protected boolean MOVE_STATE = false;
    protected boolean ATTACK_STATE = false;
    protected boolean FALLEN_STATE = false;

    protected boolean up = false;
    protected boolean down = false;
    protected boolean left = false;
    protected boolean right = false;

    // Default/self stats
    protected int mana = 1000;
    protected int atk = 10;
    protected int def = 5;
    protected float elemDmgBonus = 0f;
    protected float elemResBonus = 0f;
    protected int maxHealth = 100;
    protected int maxMana = 1000;
    protected int maxAtk = 10;
    protected int maxDef = 5;
    protected float maxElemDmgBonus = 0f;
    protected float maxElemResBonus = 0f;
    protected float res = 0f;
    protected double healthPercent = 1;
    protected double manaPercent = 1;

    protected TileCollision tileCollision;
    protected HashMap<Integer, Skill> skills;
    protected ArrayList<GameObject> obstacle; // TODO: dev this function

    public Entity(SpriteSheet spriteSheet, Vector2f pos, int width, int height, String name) {
        super(spriteSheet, pos, width, height);

        setAnimation(Assets.IDLE, spriteSheet.getSpriteRow(currDirection), 10);

        this.name = name;

        tileCollision = new TileCollision(this);
        skills = new HashMap<>();
        obstacle = new ArrayList<>();
    }

    public boolean getState(String state) {
        if (state.equals("IDLE")) { return IDLE_STATE; }
        else if (state.equals("MOVE")) { return MOVE_STATE; }
        else if (state.equals("ATTACK")) { return ATTACK_STATE; }
        else if (state.equals("FALLEN")) { return FALLEN_STATE; }
        else return super.getState(state);
    }

    public int getHealth() { return health; }
    public int getMana() { return mana; }
    public int getAtk() { return atk; }
    public int getDef() { return def; }
    public float getElemDmgBonus() { return elemDmgBonus; }
    public float getElemResBonus() { return elemResBonus; }
    public int getMaxHealth() { return maxHealth; }
    public int getMaxMana() { return maxMana; }
    public int getMaxAtk() { return maxAtk; }
    public int getMaxDef() { return maxDef; }
    public float getMaxElemDmgBonus() { return maxElemDmgBonus; }
    public float getMaxElemResBonus() { return maxElemResBonus; }
    public float getRes() { return res; }
    public double getHealthPercent() { return healthPercent; }
    public double getManaPercent() { return manaPercent; }

    public float getDeacc() { return deacc; }
    public float getAcc() { return acc; }
    public float getMaxSpeed() { return maxSpeed; }
    public float getForce() { return force; }

    public void setState(String state, boolean b) {
        if (state.equals("IDLE")) { IDLE_STATE = b; }
        else if (state.equals("MOVE")) { MOVE_STATE = b; }
        else if (state.equals("ATTACK")) { ATTACK_STATE = b; }
        else if (state.equals("FALLEN")) { FALLEN_STATE = b; }
        else super.setState(state, b);
    }

    public void healthDec(int dmgTaken, float force, int direct) {
        if (!INVINCIBLE_STATE) {
            health = Math.max(0, health - dmgTaken);
            this.dmgTaken = dmgTaken;
            dmgDisplaying = true;
            dmgGainTime = System.nanoTime();
            INVINCIBLE_STATE = true;
            invincibleTime = System.nanoTime();

            addForce(force, direct);

            healthPercent = (double)health / maxHealth;
        }
    }

    public void manaDec(int manaConsume) {
        mana = Math.max(0, mana - manaConsume);
        manaPercent = (float)mana / maxMana;
    }

    public TileCollision getTc() { return tileCollision; }
    public HashMap<Integer, Skill> getSkills() { return skills; }
    public void addObstacle(Obstacle ob) { obstacle.add(ob); }
    public void removeObstacle(Obstacle ob) { obstacle.remove(ob); }
    public void addSkills(int i, Skill skill) { skills.put(i, skill); }
    public void removeSkill(int i) { skills.remove(i); }
    public void addEnemy(Entity target) {
        for (Map.Entry<Integer, Skill> entry : skills.entrySet()) {
            entry.getValue().setTargetEnemy(target);
        }
    }

    public void setMaxSpeed(float f) { maxSpeed = f; }
    public void setAcc(float f) { acc = f; }
    public void setDeacc(float f) { deacc = f; }

    // animate -> move
    public abstract void animate();

    // Animate -> move
    public void move() {
        if (up) {
            currDirection = Assets.UP;
            dy -= acc;
            if (dy < -maxSpeed) dy = -maxSpeed;
        } else {
            if (dy < 0) {
                dy += deacc;
                if (dy > 0) dy = 0;
            }
        }

        if (down) {
            currDirection = Assets.DOWN;
            dy += acc;
            if (dy > maxSpeed) dy = maxSpeed;
        } else {
            if (dy > 0) {
                dy -= deacc;
                if (dy < 0) dy = 0;
            }
        }

        if (left) {
            currDirection = Assets.LEFT;
            dx -= acc;
            if (dx < -maxSpeed) dx = -maxSpeed;
        } else {
            if (dx < 0) {
                dx += deacc;
                if (dx > 0) dx = 0;
            }
        }

        if (right) {
            currDirection = Assets.RIGHT;
            dx += acc;
            if (dx > maxSpeed) dx = maxSpeed;
        } else {
            if (dx > 0) {
                dx -= deacc;
                if (dx < 0) dx = 0;
            }
        }
        if (right && up) {
            currDirection = Assets.RIGHTUP;
        } else if (right && down) {
            currDirection = Assets.RIGHTDOWN;
        } if (left && down) {
            currDirection = Assets.LEFTDOWN;
        } if (left && up) {
            currDirection = Assets.LEFTUP;
        }
    }

    @Override
    public void update(double time) {
        super.update(time);

        animate();
        anim.update();
        move();
        if (!FALLEN_STATE) {
            for (Map.Entry<Integer, Skill> item : skills.entrySet()) {
                if (item.getValue() != null) {
                    item.getValue().update(time);
                }
            }
        }

        if (dmgDisplaying) {
            if ((dmgGainTime / 1000000) + dmgDisplayDuration < (time / 1000000)) {
                dmgDisplaying = false;
                dmgTaken = 0;
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);

        for (Map.Entry<Integer, Skill> item : skills.entrySet()) {
            Skill skill = skills.get(item.getKey());
            if (skill.isActivating()) {
                skill.render(g2d);
            }
        }
    }

}