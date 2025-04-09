package com.treasurehunting.java.entity.enemy;

import com.treasurehunting.java.entity.Enemy;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.skills.SmashAttack;
import com.treasurehunting.java.skills.StaminaSkill;
import com.treasurehunting.java.utils.GameSettings;

public class ToxicFruit extends Enemy {
    
    public boolean FLY_STATE = false;
    public boolean SMASH_STATE = false;

    public ToxicFruit(Vector2f pos) {
        super(Assets.toxicFruitSSIdle, pos, GameSettings.TILE_SIZE, GameSettings.TILE_SIZE, "Toxic Fruit");

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

        atk = 200;
        acc = 1f;
        deacc = 2f;
        maxSpeed = 2f;

        skills.put(0, new SmashAttack(this));
    }

    @Override
    public void animate() {
        if (DIE_STATE) {
            if (currAnimation != Assets.DIE) {
                setAbsoluteAnimation(Assets.DIE, spriteSheets.get(Assets.DIE).getSpriteRow(currDirection), 5);
            }
        } else if (INVINCIBLE_STATE) {
            if (currAnimation != Assets.INVINCIBLE) {
                setAbsoluteAnimation(Assets.INVINCIBLE, spriteSheets.get(Assets.INVINCIBLE).getSpriteRow(currDirection), 3);
            }
        } else if (SMASH_STATE) {
            if (currAnimation != Assets.SMASH) {
                setAbsoluteAnimation(Assets.SMASH, spriteSheets.get(Assets.SMASH).getSpriteRow(currDirection), 5);
            }
        } else if (FLY_STATE) {
            if (right && up) {
                if (currAnimation != Assets.FLY || anim.getDelay() == -1 || (currAnimation == Assets.FLY && currDirection != Assets.RIGHTUP)) {
                    setAbsoluteAnimation(Assets.FLY, spriteSheets.get(Assets.FLY).getSpriteRow(Assets.RIGHTUP), 5);
                }
            } else if (right && down) {
                if (currAnimation != Assets.FLY || anim.getDelay() == -1 || (currAnimation == Assets.FLY && currDirection != Assets.RIGHTDOWN)) {
                    setAbsoluteAnimation(Assets.FLY, spriteSheets.get(Assets.FLY).getSpriteRow(Assets.RIGHTDOWN), 5);
                }
            } else if (left && down) {
                if (currAnimation != Assets.FLY || anim.getDelay() == -1 || (currAnimation == Assets.FLY && currDirection != Assets.LEFTDOWN)) {
                    setAbsoluteAnimation(Assets.FLY, spriteSheets.get(Assets.FLY).getSpriteRow(Assets.LEFTDOWN), 5);
                }
            } else if (left && up) {
                if (currAnimation != Assets.FLY || anim.getDelay() == -1 || (currAnimation == Assets.FLY && currDirection != Assets.LEFTUP)) {
                    setAbsoluteAnimation(Assets.FLY, spriteSheets.get(Assets.FLY).getSpriteRow(Assets.LEFTUP), 5);
                }
            } else if (down) {
                if (currAnimation != Assets.FLY || anim.getDelay() == -1 || (currAnimation == Assets.FLY && currDirection != Assets.DOWN)) {
                    setAbsoluteAnimation(Assets.FLY, spriteSheets.get(Assets.FLY).getSpriteRow(Assets.DOWN), 5);
                }
            } else if (left) {
                if (currAnimation != Assets.FLY || anim.getDelay() == -1 || (currAnimation == Assets.FLY && currDirection != Assets.LEFT)) {
                    setAbsoluteAnimation(Assets.FLY, spriteSheets.get(Assets.FLY).getSpriteRow(Assets.LEFT), 5);
                }
            } else if (up) {
                if (currAnimation != Assets.FLY || anim.getDelay() == -1 || (currAnimation == Assets.FLY && currDirection != Assets.UP)) {
                    setAbsoluteAnimation(Assets.FLY, spriteSheets.get(Assets.FLY).getSpriteRow(Assets.UP), 5);
                }
            } else if (right) {
                if (currAnimation != Assets.FLY || anim.getDelay() == -1 || (currAnimation == Assets.FLY && currDirection != Assets.RIGHT)) {
                    setAbsoluteAnimation(Assets.FLY, spriteSheets.get(Assets.FLY).getSpriteRow(Assets.RIGHT), 5);
                }
            }
        } else if (currAnimation != Assets.IDLE) {
            setAbsoluteAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(currDirection), 5);
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
        if (sense.colCircleBox(playerBounds) && !player.getState("INVINCIBLE") && !SMASH_STATE && !PlayScene.tm.checkInFog(bounds)) {
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

}
