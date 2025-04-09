package com.treasurehunting.java.entity.enemy;

import com.treasurehunting.java.entity.Enemy;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.skills.RangeAttack;
import com.treasurehunting.java.skills.StaminaSkill;
import com.treasurehunting.java.utils.GameSettings;

public class Bat extends Enemy {

    public boolean SLEEP_STATE = true;
    public boolean WAKEUP_STATE = false;
    public boolean FLY_STATE = false;
    public boolean RUN_STATE = false;
    public boolean RANGEATTACK_STATE = false;
    public boolean DASH_STATE = false;

    public Bat(Vector2f pos) {
        super(Assets.batSSIdleSleep, pos, GameSettings.TILE_SIZE, GameSettings.TILE_SIZE, "BAT");

        addSpriteSheet(Assets.FLY, Assets.batSSFly);
        addSpriteSheet(Assets.WAKEUP, Assets.batSSWakeUp);
        addSpriteSheet(Assets.RUN, Assets.batSSRun);
        addSpriteSheet(Assets.RANGEATTACK, Assets.batSSSkill1);
        addSpriteSheet(Assets.DASHSATTACK, Assets.batSSSkill2);
        addSpriteSheet(Assets.INVINCIBLE, Assets.batSSHurt);
        addSpriteSheet(Assets.DIE, Assets.batSSDie);

        anim.setActiveFrame(5, Assets.RANGEATTACK);

        bounds.setWidth((float)width/4);
        bounds.setHeight((float)height/2 - (float)height/4);
        bounds.setXOffset((float)width/2 - (float)width/8);
        bounds.setYOffset((float) height / 4);

        sense.setRadius(700);
        sense.getPos().flag();

        atk = 50;
        acc = 1f;
        deacc = 2f;
        maxSpeed = 1.5f;

        skills.put(0, new RangeAttack(this));
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
        } else if (WAKEUP_STATE) {
            if (currAnimation != Assets.WAKEUP) {
                setAbsoluteAnimation(Assets.WAKEUP, spriteSheets.get(Assets.WAKEUP).getSpriteRow(currDirection), 7);
            }
        } else if (RANGEATTACK_STATE) {
            if (currAnimation != Assets.RANGEATTACK) {
                setAbsoluteAnimation(Assets.RANGEATTACK, spriteSheets.get(Assets.RANGEATTACK).getSpriteRow(currDirection), 3);
            }
        } else if (DASH_STATE && ATTACK_STATE) {
            if (currAnimation != Assets.DASHSATTACK) {
                setAbsoluteAnimation(Assets.DASHSATTACK, spriteSheets.get(Assets.DASHSATTACK).getSpriteRow(currDirection), 5);
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
        if (state.equals("SLEEP")) { return SLEEP_STATE; }
        if (state.equals("WAKEUP")) { return WAKEUP_STATE; }
        if (state.equals("FLY")) { return FLY_STATE; }
        if (state.equals("RUN")) { return RUN_STATE; }
        if (state.equals("RANGEATTACK")) { return RANGEATTACK_STATE; }
        if (state.equals("DASH")) { return DASH_STATE; }
        return super.getState(state);
    }

    @Override
    public void setState(String state, boolean b) {
        if (state.equals("SLEEP")) { SLEEP_STATE = b; }
        if (state.equals("WAKEUP")) { WAKEUP_STATE = b; }
        if (state.equals("FLY")) { FLY_STATE = b; }
        if (state.equals("RUN")) { RUN_STATE = b; }
        if (state.equals("RANGEATTACK")) { RANGEATTACK_STATE = b; }
        if (state.equals("DASH")) { DASH_STATE = b; }
        else super.setState(state, b);
    }

    @Override
    public void chase(Player player) {
        AABB playerBounds = player.getBounds();
        if (sense.colCircleBox(playerBounds) && !player.getState("INVINCIBLE") && !WAKEUP_STATE && !PlayScene.tm.checkInFog(bounds)) {
            if (pos.y + bounds.getYOffset() + (float) bounds.getHeight() / 2 > player.getPos().y + player.getBounds().getYOffset() + (float) player.getBounds().getHeight() / 2 + 5) {
                up = true;
                FLY_STATE = true;
            } else up = false;
            if (pos.y + bounds.getYOffset() + (float) bounds.getHeight() / 2 < player.getPos().y + player.getBounds().getYOffset() + (float) player.getBounds().getHeight() / 2 - 5) {
                down = true;
                FLY_STATE = true;
            } else down = false;
            if (pos.x + bounds.getXOffset() + (float) bounds.getWidth() / 2 > player.getPos().x + player.getBounds().getXOffset() + (float) player.getBounds().getWidth() / 2 + 5) {
                left = true;
                FLY_STATE = true;
            } else left = false;
            if (pos.x + bounds.getXOffset() + (float) bounds.getWidth() / 2 < player.getPos().x + player.getBounds().getXOffset() + (float) player.getBounds().getWidth() / 2 - 5) {
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
    public void update(double time, Player player) {
        super.update(time, player);

        if (WAKEUP_STATE && anim.hasPlayedOnce()) {
            WAKEUP_STATE = false;
        }

        if (SLEEP_STATE) {
            if (sense.colCircleBox(player.getBounds()) && !player.getState("INVINCIBLE") && !PlayScene.tm.checkInFog(bounds)) {
                spriteSheets.put(Assets.IDLE, spriteSheets.get(Assets.FLY));
                SLEEP_STATE = false;
                WAKEUP_STATE = true;
                bounds.setYOffset(bounds.getYOffset() + 15);
            }
        }
    }

}
