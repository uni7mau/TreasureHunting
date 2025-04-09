package com.treasurehunting.java.entity;

import com.treasurehunting.java.GamePanel;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.Sprite;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.skills.*;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.tiles.TileMapObj;
import com.treasurehunting.java.tiles.blocks.NormBlock;
import com.treasurehunting.java.utils.GameSettings;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;
import com.treasurehunting.java.math.Vector2f;

import java.util.List;

// Big brain time: Adventurer doesn't have Walk while Shooting, so I decided to give player an ability that make she moves superfast

public class Player extends Entity {

    public boolean RELOADING_STATE = false;
    public boolean DASH_STATE = false;
    public boolean RUN_STATE = false;
    public boolean SHOOTING_STATE = false;
    public boolean THROWBOMB_STATE = false;
    public boolean CASTMAGIC_STATE = false;

    // 48 x 64
    public Player(Vector2f origin) {
        super(Assets.playerSSGunIdle, origin, 2*GameSettings.TILE_SIZE*48 / 64, 2*GameSettings.TILE_SIZE, "Player");

        addSpriteSheet(Assets.RUN, Assets.playerSSGunRun);
        addSpriteSheet(Assets.STANDSHOOTING, Assets.playerSSGunStandShooting);
        addSpriteSheet(Assets.RUNSHOOTING, Assets.playerSSGunRunShooting);
        addSpriteSheet(Assets.DASH, Assets.playerSSGunDash);
        addSpriteSheet(Assets.RELOADING, Assets.playerSSGunReloading);
        addSpriteSheet(Assets.DIE, Assets.playerSSGunDeath);

        bounds.setWidth(35);
        bounds.setHeight(20);
        bounds.setXOffset(30);
        bounds.setYOffset(70);

        sense.setRadius(700);
        sense.getPos().flag();

        health = 500;
        maxHealth = 500;

        skills.put(GameSettings.SKILL1, new Dash(this));
        skills.put(GameSettings.SKILL2, new Shooting(this));
        skills.put(GameSettings.SKILL3, new BombShooting(this));
        skills.put(GameSettings.SKILL4, new UltimateEnergyWave(this));
    }

    private void resetPosition() {
        PlayScene.map.resetOri();
        pos.resetOri();
        bounds.getPos().resetOri();
        sense.getPos().resetOri();

        PlayScene.cam.getPos().resetOri();

        setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(Assets.RIGHT), 10);
    }

    @Override
    public boolean getState(String state) {
        if (state.equals("RELOADING")) return RELOADING_STATE;
        else if (state.equals("DASH")) return DASH_STATE;
        else if (state.equals("RUN")) return RUN_STATE;
        else if (state.equals("SHOOTING")) return SHOOTING_STATE;
        else if (state.equals("THROWBOMB")) return THROWBOMB_STATE;
        else if (state.equals("CASTMAGIC")) return CASTMAGIC_STATE;
        else return super.getState(state);
    }

    @Override
    public void setState(String state, boolean b) {
        if (state.equals("RELOADING")) RELOADING_STATE = b;
        else if (state.equals("DASH")) DASH_STATE = b;
        else if (state.equals("RUN")) RUN_STATE = b;
        else if (state.equals("SHOOTING")) SHOOTING_STATE = b;
        else if (state.equals("THROWBOMB")) THROWBOMB_STATE = b;
        else if (state.equals("CASTMAGIC")) CASTMAGIC_STATE = b;
        else super.setState(state, b);
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        if (!FALLEN_STATE) {
            if (key.getKeys().get(GameSettings.UP).down) {
                up = true;
                RUN_STATE = true;
            } else up = false;
            if (key.getKeys().get(GameSettings.DOWN).down) {
                down = true;
                RUN_STATE = true;
            } else down = false;
            if (key.getKeys().get(GameSettings.LEFT).down) {
                left = true;
                RUN_STATE = true;
            } else left = false;
            if (key.getKeys().get(GameSettings.RIGHT).down) {
                right = true;
                RUN_STATE = true;
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
            right = false;
            left = false;
            RUN_STATE = false;
        }
    }

    @Override
    public void animate() {
        if (health == 0) {
            if (currAnimation != Assets.DIE) {
                setAnimation(Assets.DIE, spriteSheets.get(Assets.DIE).getSpriteRow(currDirection), 5);
            }
        } else if (DASH_STATE) {
            if (currAnimation != Assets.DASH) {
                setAnimation(Assets.DASH, spriteSheets.get(Assets.DASH).getSpriteRow(currDirection), 5);
            }
        } else if (RELOADING_STATE) {
            if (currAnimation != Assets.RELOADING) {
                setAnimation(Assets.RELOADING, spriteSheets.get(Assets.RELOADING).getSpriteRow(currDirection), 7);
            }
        } else if (RUN_STATE && SHOOTING_STATE) {
            if (currAnimation != Assets.RUNSHOOTING) {
                setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(currDirection), 5);
            } else {
                if (right && up && currDirection != Assets.RIGHTUP) {
                    setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(Assets.RIGHTUP), 5);
                } else if (right && down && currDirection != Assets.RIGHTDOWN) {
                    setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(Assets.RIGHTDOWN), 5);
                } else if (left && down && currDirection != Assets.LEFTDOWN) {
                    setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(Assets.LEFTDOWN), 5);
                } else if (left && up && currDirection != Assets.LEFTUP) {
                    setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(Assets.LEFTUP), 5);
                } else if (down && currDirection != Assets.DOWN && !left && !right) {
                    setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(Assets.DOWN), 5);
                } else if (left && currDirection != Assets.LEFT && !up && !down) {
                    setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(Assets.LEFT), 5);
                } else if (up && currDirection != Assets.UP && !left && !right) {
                    setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(Assets.UP), 5);
                } else if (right && currDirection != Assets.RIGHT && !up && !down) {
                    setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(Assets.RIGHT), 5);
                }
            }
        } else if (SHOOTING_STATE) {
            if (currAnimation != Assets.STANDSHOOTING) {
                setAnimation(Assets.STANDSHOOTING, spriteSheets.get(Assets.STANDSHOOTING).getSpriteRow(currDirection), 5);
            }
        } else if (RUN_STATE && THROWBOMB_STATE) {
            if (currAnimation != Assets.RUNSHOOTING) {
                setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(currDirection), 5);
            }
        } else if (THROWBOMB_STATE) {
            if (currAnimation != Assets.STANDSHOOTING) {
                setAnimation(Assets.STANDSHOOTING, spriteSheets.get(Assets.STANDSHOOTING).getSpriteRow(currDirection), 5);
            }
        } else if (RUN_STATE && CASTMAGIC_STATE) {
            if (currAnimation != Assets.RUNSHOOTING) {
                setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(currDirection), 5);
            }
        } else if (CASTMAGIC_STATE) {
            if (currAnimation != Assets.STANDSHOOTING) {
                setAnimation(Assets.STANDSHOOTING, spriteSheets.get(Assets.STANDSHOOTING).getSpriteRow(currDirection), 5);
            }
        } else if (RUN_STATE) {
            if (right && up) {
                if (currAnimation != Assets.RUN || (currAnimation == Assets.RUN && currDirection != Assets.RIGHTUP)) {
                    setAnimation(Assets.RUN, spriteSheets.get(Assets.RUN).getSpriteRow(Assets.RIGHTUP), 5);
                }
            } else if (right && down) {
                if (currAnimation != Assets.RUN || (currAnimation == Assets.RUN && currDirection != Assets.RIGHTDOWN)) {
                    setAnimation(Assets.RUN, spriteSheets.get(Assets.RUN).getSpriteRow(Assets.RIGHTDOWN), 5);
                }
            } else if (left && down) {
                if (currAnimation != Assets.RUN || (currAnimation == Assets.RUN && currDirection != Assets.LEFTDOWN)) {
                    setAnimation(Assets.RUN, spriteSheets.get(Assets.RUN).getSpriteRow(Assets.LEFTDOWN), 5);
                }
            } else if (left && up) {
                if (currAnimation != Assets.RUN || (currAnimation == Assets.RUN && currDirection != Assets.LEFTUP)) {
                    setAnimation(Assets.RUN, spriteSheets.get(Assets.RUN).getSpriteRow(Assets.LEFTUP), 5);
                }
            } else if (down) {
                if (currAnimation != Assets.RUN || (currAnimation == Assets.RUN && currDirection != Assets.DOWN)) {
                    setAnimation(Assets.RUN, spriteSheets.get(Assets.RUN).getSpriteRow(Assets.DOWN), 5);
                }
            } else if (left) {
                if (currAnimation != Assets.RUN || (currAnimation == Assets.RUN && currDirection != Assets.LEFT)) {
                    setAnimation(Assets.RUN, spriteSheets.get(Assets.RUN).getSpriteRow(Assets.LEFT), 5);
                }
            } else if (up) {
                if (currAnimation != Assets.RUN || (currAnimation == Assets.RUN && currDirection != Assets.UP)) {
                    setAnimation(Assets.RUN, spriteSheets.get(Assets.RUN).getSpriteRow(Assets.UP), 5);
                }
            } else if (right) {
                if (currAnimation != Assets.RUN || (currAnimation == Assets.RUN && currDirection != Assets.RIGHT)) {
                    setAnimation(Assets.RUN, spriteSheets.get(Assets.RUN).getSpriteRow(Assets.RIGHT), 5);
                }
            }
        } else if (currAnimation != Assets.IDLE) {
            setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(currDirection), 5);
        }
    }

    @Override
    public void update(double time) {
        super.update(time);

        if (INVINCIBLE_STATE && (GamePanel.tickCount == 0 || GamePanel.tickCount == 15 || GamePanel.tickCount == 30 || GamePanel.tickCount == 45 || GamePanel.tickCount == 60)) {
            anim.getImage().setEffect(Sprite.effect.REDISH);
        } else {
            anim.getImage().setEffect(Sprite.effect.NORMAL);
        }

        if (dx == 0 && dy == 0) { RUN_STATE = false; }

        if (!FALLEN_STATE && !RELOADING_STATE) {
            if (!tileCollision.collisionTile(dx + bonusDx, 0)) {
                pos.x += dx + bonusDx;
                bounds.getPos().x += dx + bonusDx;
                sense.getPos().x += dx + bonusDx;
                blockedX = false;
            } else {
                blockedX = true;
            }
            if (!tileCollision.collisionTile(0, dy + bonusDy)) {
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

        List<NormBlock> blocks = PlayScene.tm.getNormalTile(tileCollision.getTile());
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) != null) {
                blocks.get(i).getImage().restoreDefault();
                blocks.get(i).hasFog = false;
            }
        }
    }

}
