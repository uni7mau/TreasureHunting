package com.treasurehunting.java.entity.enemy;

import com.treasurehunting.java.entity.Enemy;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.skills.RangeAttack;
import com.treasurehunting.java.skills.StaminaSkill;

public class Skeleton extends Enemy {

    public static boolean STRAIGHTATTACK_STATE = false;
    public static boolean RANGEATTACK_STATE = false;
    
    public Skeleton(SpriteSheet spriteSheet, Vector2f pos, int width, int height) {
        super(spriteSheet, pos, width, height, "Skeleton");

        bounds.setWidth((float)width/2);
        bounds.setHeight((float)height/2);
        bounds.setXOffset((float)width/2 - (float)width/4);
        bounds.setYOffset((float)height/2);

        sense.setRadius(350);
        sense.getPos().flag();

        atk = 50;
        acc = 1f;
        deacc = 2f;
        maxSpeed = 2f;

        right = true;

        skills.put(0, new RangeAttack(this));
    }

    @Override
    public void animate() {
        if (DIE_STATE) {
            if (currAnimation != Assets.DIE) {
                setAnimation(Assets.DIE, spriteSheets.get(Assets.DIE).getSpriteRow(currDirection), 5);
            }
        } else if (INVINCIBLE_STATE) {
            if (currAnimation != Assets.INVINCIBLE) {
                setAnimation(Assets.INVINCIBLE, spriteSheets.get(Assets.INVINCIBLE).getSpriteRow(currDirection), 3);
            }
        } else if (STRAIGHTATTACK_STATE) {
            if (currAnimation != Assets.STRAIGHTATTACK) {
                setAnimation(Assets.STRAIGHTATTACK, spriteSheets.get(Assets.STRAIGHTATTACK).getSpriteRow(currDirection), 5);
            }
        } else if (RANGEATTACK_STATE) {
            if (currAnimation != Assets.RANGEATTACK) {
                setAnimation(Assets.RANGEATTACK, spriteSheets.get(Assets.RANGEATTACK).getSpriteRow(currDirection), 2);
            }
        } else if (MOVE_STATE) {
            if (right && up) {
                if (currAnimation != Assets.WALK || anim.getDelay() == -1 || (currAnimation == Assets.WALK && currDirection != Assets.RIGHTUP)) {
                    setAnimation(Assets.WALK, spriteSheets.get(Assets.WALK).getSpriteRow(Assets.RIGHTUP), 5);
                }
            } else if (right && down) {
                if (currAnimation != Assets.WALK || anim.getDelay() == -1 || (currAnimation == Assets.WALK && currDirection != Assets.RIGHTDOWN)) {
                    setAnimation(Assets.WALK, spriteSheets.get(Assets.WALK).getSpriteRow(Assets.RIGHTDOWN), 5);
                }
            } else if (left && down) {
                if (currAnimation != Assets.WALK || anim.getDelay() == -1 || (currAnimation == Assets.WALK && currDirection != Assets.LEFTDOWN)) {
                    setAnimation(Assets.WALK, spriteSheets.get(Assets.WALK).getSpriteRow(Assets.LEFTDOWN), 5);
                }
            } else if (left && up) {
                if (currAnimation != Assets.WALK || anim.getDelay() == -1 || (currAnimation == Assets.WALK && currDirection != Assets.LEFTUP)) {
                    setAnimation(Assets.WALK, spriteSheets.get(Assets.WALK).getSpriteRow(Assets.LEFTUP), 5);
                }
            } else if (down) {
                if (currAnimation != Assets.WALK || anim.getDelay() == -1 || (currAnimation == Assets.WALK && currDirection != Assets.DOWN)) {
                    setAnimation(Assets.WALK, spriteSheets.get(Assets.WALK).getSpriteRow(Assets.DOWN), 5);
                }
            } else if (left) {
                if (currAnimation != Assets.WALK || anim.getDelay() == -1 || (currAnimation == Assets.WALK && currDirection != Assets.LEFT)) {
                    setAnimation(Assets.WALK, spriteSheets.get(Assets.WALK).getSpriteRow(Assets.LEFT), 5);
                }
            } else if (up) {
                if (currAnimation != Assets.WALK || anim.getDelay() == -1 || (currAnimation == Assets.WALK && currDirection != Assets.UP)) {
                    setAnimation(Assets.WALK, spriteSheets.get(Assets.WALK).getSpriteRow(Assets.UP), 5);
                }
            } else if (right) {
                if (currAnimation != Assets.WALK || anim.getDelay() == -1 || (currAnimation == Assets.WALK && currDirection != Assets.RIGHT)) {
                    setAnimation(Assets.WALK, spriteSheets.get(Assets.WALK).getSpriteRow(Assets.RIGHT), 5);
                }
            }
        } else if (currAnimation != Assets.IDLE) {
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
        if (sense.colCircleBox(playerBounds) && !player.getState("INVINCIBLE") && !RANGEATTACK_STATE) {
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
        for (int i = 0; i < skills.size(); i++) {
            if (skills.get(i) instanceof StaminaSkill staSkill) {
                if (staSkill.getHitBound().colCircleBox(player.getBounds())) {
                    staSkill.gainSignal();
                } else {
                    staSkill.stopSignal();
                }
            }
        }
    }

}
