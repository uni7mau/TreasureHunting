package com.treasurehunting.java.entity.enemies;

import com.treasurehunting.java.entity.Enemy;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.skills.RangeAttack;
import com.treasurehunting.java.skills.StaminaSkill;

import java.awt.*;

// 90x64

public class Skeleton extends Enemy {

    private boolean STRAIGHTATTACK_STATE = false;
    private boolean RANGEATTACK_STATE = false;

    static float scale = 1.5f;
    
    public Skeleton(Vector2f pos) {
        super(Assets.yellowSleketonSSIdle, pos, (int) (scale* Assets.TILE_SIZE), (int) (scale* Assets.TILE_SIZE*64 / 90), "Skeleton");
        addSpriteSheet(Assets.WALK, Assets.yellowSleketonSSWalk);
        addSpriteSheet(Assets.STRAIGHTATTACK, Assets.yellowSleketonSSSkill1);
        addSpriteSheet(Assets.RANGEATTACK, Assets.yellowSleketonSSSkill2);
        addSpriteSheet(Assets.INVINCIBLE, Assets.yellowSleketonSSHurt);
        addSpriteSheet(Assets.DIE, Assets.yellowSleketonSSDie);

        anim.setActiveFrame(4, Assets.RANGEATTACK);

        bounds.setWidth((float)width/2);
        bounds.setHeight((float)height/2);
        bounds.setXOffset((float)width/2 - (float)width/4);
        bounds.setYOffset((float)height/2);

        sense.setRadius(350);
        sense.getPos().flag();

        health = 100;
        maxHealth = 100;
        atk = 50;
        acc = 1f;
        deacc = 2f;
        maxSpeed = 2f;

        skills.put(0, new RangeAttack(this, 2));
    }

    @Override
    public void animate() {
        if (health == 0) {
            setAbsoluteAnimation(Assets.DIE, spriteSheets.get(Assets.DIE).getSpriteRow(currDirection), 5);
        } else if (INVINCIBLE_STATE) {
            setAbsoluteAnimation(Assets.INVINCIBLE, spriteSheets.get(Assets.INVINCIBLE).getSpriteRow(currDirection), 3);
        } else if (STRAIGHTATTACK_STATE) {
            setAbsoluteAnimation(Assets.STRAIGHTATTACK, spriteSheets.get(Assets.STRAIGHTATTACK).getSpriteRow(currDirection), 5);
        } else if (RANGEATTACK_STATE) {
            setAbsoluteAnimation(Assets.RANGEATTACK, spriteSheets.get(Assets.RANGEATTACK).getSpriteRow(currDirection), 2);
        } else if (MOVE_STATE) {
            setAnimation(Assets.WALK, spriteSheets.get(Assets.WALK).getSpriteRow(currDirection), 5);
        } else {
            setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(currDirection), 5);
        }
    }

    @Override
    public boolean getState(String state) {
        if (state.equals("STRAIGHTATTACK")) { return STRAIGHTATTACK_STATE; }
        else if (state.equals("RANGEATTACK")) { return RANGEATTACK_STATE; }
        else return super.getState(state);
    }

    @Override
    public void setState(String state, boolean b) {
        if (state.equals("STRAIGHTATTACK")) { STRAIGHTATTACK_STATE = b; }
        else if (state.equals("RANGEATTACK")) { RANGEATTACK_STATE = b; }
        else super.setState(state, b);
    }

    @Override
    public void chase(Player player) {
        AABB playerBounds = player.getBounds();
        if (sense.collides(playerBounds) && !player.getState("INVINCIBLE") && !RANGEATTACK_STATE) {
            if (pos.y + bounds.getYOffset() + (float) bounds.getHeight() / 2 > player.getPos().y + player.getBounds().getYOffset() + (float) player.getBounds().getHeight() / 2 + player.getBounds().getHeight() + bounds.getHeight() / 2) {
                up = true;
                MOVE_STATE = true;
            } else up = false;
            if (pos.y + bounds.getYOffset() + (float) bounds.getHeight() / 2 < player.getPos().y + player.getBounds().getYOffset() + (float) player.getBounds().getHeight() / 2 - player.getBounds().getHeight() - bounds.getHeight() / 2) {
                down = true;
                MOVE_STATE = true;
            } else down = false;
            if (pos.x + bounds.getXOffset() + (float) bounds.getWidth() / 2 > player.getPos().x + player.getBounds().getXOffset() + (float) player.getBounds().getWidth() / 2 + player.getBounds().getWidth() + bounds.getWidth() / 2) {
                left = true;
                MOVE_STATE = true;
            } else left = false;
            if (pos.x + bounds.getXOffset() + (float) bounds.getWidth() / 2 < player.getPos().x + player.getBounds().getXOffset() + (float) player.getBounds().getWidth() / 2 - player.getBounds().getWidth() - bounds.getWidth() / 2) {
                right = true;
                MOVE_STATE = true;
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
            MOVE_STATE = false;
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
            drawName(g2d, "Pixel Game", 16, "#FF9F3D");
        }
    }

}
