package com.treasurehunting.java.gameobjects.entities.enemies;

import com.treasurehunting.java.gameobjects.entities.Enemy;
import com.treasurehunting.java.gameobjects.entities.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.skills.abilities.SmashAttack;
import com.treasurehunting.java.skills.StaminaSkill;

import java.awt.*;

public class ToxicFruit extends Enemy {

    private boolean FLY_STATE = false;
    private boolean SMASH_STATE = false;

    public ToxicFruit(Vector2f pos) {
        super(Assets.toxicFruitSSIdle, pos, Assets.TILE_SIZE, Assets.TILE_SIZE, "Toxic Fruit");

        addSpriteSheet(Assets.FLY, Assets.toxicFruitSSFly);
        addSpriteSheet(Assets.SMASH, Assets.toxicFruitSSSkill1);
        addSpriteSheet(Assets.INVINCIBLE, Assets.toxicFruitSSHurt);
        addSpriteSheet(Assets.DIE, Assets.toxicFruitSSDie);

        anim.setActiveFrame(12, Assets.SMASH);

        bounds.setWidth((float)width/2);
        bounds.setHeight((float)height/2);
        bounds.setXOffset((float)width/2 - (float)width/4);
        bounds.setYOffset((float)height/2);

        sense.setRadius(500);
        sense.getPos().flag();

        health = 50;
        maxHealth = 50;
        atk = 200;
        acc = 1f;
        deacc = 2f;
        maxSpeed = 3f;

        skills.put(0, new SmashAttack(this));
    }

    @Override
    public void animate() {
        if (health == 0) {
            setAbsoluteAnimation(Assets.DIE, spriteSheets.get(Assets.DIE).getSpriteRow(currDirection), 5);
        } else if (INVINCIBLE_STATE) {
            setAbsoluteAnimation(Assets.INVINCIBLE, spriteSheets.get(Assets.INVINCIBLE).getSpriteRow(currDirection), 3);
        } else if (SMASH_STATE) {
            setAbsoluteAnimation(Assets.SMASH, spriteSheets.get(Assets.SMASH).getSpriteRow(currDirection), 5);
        } else if (FLY_STATE) {
            setAnimation(Assets.FLY, spriteSheets.get(Assets.FLY).getSpriteRow(currDirection), 5);
        } else if (currAnimation != Assets.IDLE) {
            setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(currDirection), 5);
        }
    }

    @Override
    public boolean getState(String state) {
        if (state.equals("SMASH")) { return SMASH_STATE; }
        if (state.equals("FLY")) { return FLY_STATE; } 
        return super.getState(state);
    }

    @Override
    public void setState(String state, boolean b) {
        if (state.equals("SMASH")) { SMASH_STATE = b; }
        else if (state.equals("FLY")) { FLY_STATE = b; }
        else super.setState(state, b);
    }

    @Override
    public void chase(Player player) {
        AABB playerBounds = player.getBounds();
        if (sense.collides(playerBounds) && !player.getState("INVINCIBLE") && !SMASH_STATE) {
            if (pos.y + bounds.getYOffset() + (float) bounds.getHeight() / 2 > player.getPos().y + player.getBounds().getYOffset() + (float) player.getBounds().getHeight() / 2 + player.getBounds().getHeight()) {
                up = true;
                FLY_STATE = true;
            } else up = false;
            if (pos.y + bounds.getYOffset() + (float) bounds.getHeight() / 2 < player.getPos().y + player.getBounds().getYOffset() + (float) player.getBounds().getHeight() / 2 - player.getBounds().getHeight()) {
                down = true;
                FLY_STATE = true;
            } else down = false;
            if (pos.x + bounds.getXOffset() + (float) bounds.getWidth() / 2 > player.getPos().x + player.getBounds().getXOffset() + (float) player.getBounds().getWidth() / 2 + player.getBounds().getWidth()) {
                left = true;
                FLY_STATE = true;
            } else left = false;
            if (pos.x + bounds.getXOffset() + (float) bounds.getWidth() / 2 < player.getPos().x + player.getBounds().getXOffset() + (float) player.getBounds().getWidth() / 2 - player.getBounds().getWidth()) {
                right = true;
                FLY_STATE = true;
            } else right = false;

            if (up && down) {
                up = false;
                down = false;
            }
            if (right && left) {
                right = false;
                left = false;
            }
        } else {
            up = false;
            down = false;
            left = false;
            right = false;
            FLY_STATE = false;
        }
    }

    @Override
    public void attack(Player player) {
        if (!PlayScene.tm.checkInFog(bounds)) {
            for (int i = 0; i < skills.size(); i++) {
                if (skills.get(i) instanceof StaminaSkill staSkill) {
                    if (staSkill.getHitBound().collides(player.getBounds())) {
                        staSkill.gainSignal();
                    } else {
                        staSkill.stopSignal();
                    }
                }
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);

        if (!PlayScene.tm.checkInFog(bounds)) {
            drawName(g2d, "Pixel Game", 16, "#65D73A");
        }
    }

}
