package com.treasurehunting.java.gameobjects.entities;

import com.treasurehunting.java.gameobjects.GameObject;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.skills.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Entity extends GameObject {

    protected boolean IDLE_STATE = true;
    protected boolean MOVE_STATE = false;
    protected boolean ATTACK_STATE = false;
    protected boolean FALLEN_STATE = false;

    // Default/self stats
    protected int mana = 0;
    protected int atk = 10;
    protected int def = 5;
    protected int maxMana = 0;
    protected int maxAtk = 10;
    protected int maxDef = 5;
    protected double manaPercent = 1;

    protected HashMap<Integer, Skill> skills;

    public Entity(SpriteSheet spriteSheet, Vector2f pos, int width, int height, String name) {
        super(spriteSheet, pos, width, height);

        setAnimation(Assets.IDLE, spriteSheet.getSpriteRow(currDirection), 10);

        this.name = name;

        skills = new HashMap<>();
    }

    public boolean getState(String state) {
        if (state.equals("IDLE")) { return IDLE_STATE; }
        else if (state.equals("MOVE")) { return MOVE_STATE; }
        else if (state.equals("ATTACK")) { return ATTACK_STATE; }
        else if (state.equals("FALLEN")) { return FALLEN_STATE; }
        else return super.getState(state);
    }

    public int getMana() { return mana; }
    public int getAtk() { return atk; }
    public int getDef() { return def; }
    public int getMaxMana() { return maxMana; }
    public int getMaxAtk() { return maxAtk; }
    public int getMaxDef() { return maxDef; }
    public double getHealthPercent() { return healthPercent; }
    public double getManaPercent() { return manaPercent; }

    public void setState(String state, boolean b) {
        if (state.equals("IDLE")) { IDLE_STATE = b; }
        else if (state.equals("MOVE")) { MOVE_STATE = b; }
        else if (state.equals("ATTACK")) { ATTACK_STATE = b; }
        else if (state.equals("FALLEN")) { FALLEN_STATE = b; }
        else super.setState(state, b);
    }

    public abstract void freeze();

    public void manaDec(int manaConsume) {
        if (mana - manaConsume < 0) mana = 0;
        else if (mana - manaConsume > maxMana) mana = maxMana;
        else mana -= manaConsume;
        manaPercent = (float)mana / maxMana;
    }

    public HashMap<Integer, Skill> getSkills() { return skills; }
    public void addSkills(int i, Skill skill) { skills.put(i, skill); }
    public void removeSkill(int i) { skills.remove(i); }
    public void addEnemy(Entity target) {
        for (Map.Entry<Integer, Skill> entry : skills.entrySet()) {
            entry.getValue().setTargetEnemy(target);
        }
    }

    protected void loadPos() {
        if (!FALLEN_STATE) {
            List<GameObject> eList = PlayScene.quadTree.retrieve(new ArrayList<>(), this);
            if (!tileCollision.collisionTile(dx + bonusDx, 0) && bounds.checkGoThrough(dx + bonusDx, 0, eList)) {
                pos.x += dx + bonusDx;
                bounds.getPos().x += dx + bonusDx;
                sense.getPos().x += dx + bonusDx;
                blockedX = false;
            } else {
                blockedX = true;
            }
            if (!tileCollision.collisionTile(0, dy + bonusDy) && bounds.checkGoThrough(0, dy + bonusDy, eList)) {
                pos.y += dy + bonusDy;
                bounds.getPos().y += dy + bonusDy;
                sense.getPos().y += dy + bonusDy;
                blockedY = false;
            } else {
                blockedY = true;
            }
            bonusDx = 0;
            bonusDy = 0;

            tileCollision.normalTile(dx, 0);
            tileCollision.normalTile(0, dy);
        } else {
            blockedX = true;
            blockedY = true;
            if (FALLEN_STATE) {
                resetPosition();
                dx = 0;
                dy = 0;
                FALLEN_STATE = false;
            }
        }
    }

    @Override
    public void update(double time) {
        super.update(time);

        if (!skills.isEmpty()) {
            for (Map.Entry<Integer, Skill> item : skills.entrySet()) {
                item.getValue().update(time);
            }
        }
    }

}