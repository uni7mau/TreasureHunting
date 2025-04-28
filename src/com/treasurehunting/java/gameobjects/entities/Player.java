package com.treasurehunting.java.gameobjects.entities;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.gameobjects.obstacles.Obstacle;
import com.treasurehunting.java.scene.GameSceneManager;
import com.treasurehunting.java.skills.*;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.skills.abilities.BombShooting;
import com.treasurehunting.java.skills.abilities.Dash;
import com.treasurehunting.java.skills.abilities.Shooting;
import com.treasurehunting.java.skills.abilities.UltimateEnergyWave;
import com.treasurehunting.java.tiles.blocks.NormBlock;
import com.treasurehunting.java.utils.GameSettings;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;
import com.treasurehunting.java.math.Vector2f;

import java.awt.*;
import java.util.List;

public class Player extends Entity {

    private boolean DASH_STATE = false;
    private boolean RUN_STATE = false;
    private boolean SHOOTING_STATE = false;
    private boolean THROWBOMB_STATE = false;
    private boolean CASTMAGIC_STATE = false;

    // 48 x 64
    public Player(Vector2f origin) {
        super(Assets.playerSSGunIdle, origin, 2* Assets.TILE_SIZE*48 / 64, 2* Assets.TILE_SIZE, "Player");

        addSpriteSheet(Assets.RUN, Assets.playerSSGunRun);
        addSpriteSheet(Assets.STANDSHOOTING, Assets.playerSSGunStandShooting);
        addSpriteSheet(Assets.RUNSHOOTING, Assets.playerSSGunRunShooting);
        addSpriteSheet(Assets.DASH, Assets.playerSSGunDash);
        addSpriteSheet(Assets.DIE, Assets.playerSSGunDeath);

        bounds.setWidth((float)width/3);
        bounds.setHeight((float)height/5);
        bounds.setXOffset((float)width/2 - (float)width/5 + 2);
        bounds.setYOffset((float)height/2);

        sense.setRadius(700);
        sense.getPos().flag();

        health = 500;
        maxHealth = 500;
        mana = 1000;
        maxMana = 1000;

        hasInvincibleAnim = false;
        invincibleDuration = 1000;

        skills.put(GameSettings.SKILL1, new Dash(this));
        skills.put(GameSettings.SKILL2, new Shooting(this));
        skills.put(GameSettings.SKILL3, new BombShooting(this));
        skills.put(GameSettings.SKILL4, new UltimateEnergyWave(this));

        SumonSkill tmp = (SumonSkill) skills.get(GameSettings.SKILL4);
        tmp.setBufferTime(300);
    }

    public void resetPosition() {
        PlayScene.map.resetOri();
        pos.resetOri();
        bounds.getPos().resetOri();
        sense.getPos().resetOri();

        PlayScene.cam.getPos().resetOri();

        setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(Assets.RIGHT), 10);
    }

    public void gainInfor(Obstacle obs) {
        PlayScene.pui.showInfor(obs);
    }

    @Override
    public boolean getState(String state) {
        if (state.equals("DASH")) return DASH_STATE;
        else if (state.equals("RUN")) return RUN_STATE;
        else if (state.equals("SHOOTING")) return SHOOTING_STATE;
        else if (state.equals("THROWBOMB")) return THROWBOMB_STATE;
        else if (state.equals("CASTMAGIC")) return CASTMAGIC_STATE;
        else return super.getState(state);
    }

    @Override
    public void setState(String state, boolean b) {
        if (state.equals("DASH")) DASH_STATE = b;
        else if (state.equals("RUN")) RUN_STATE = b;
        else if (state.equals("SHOOTING")) SHOOTING_STATE = b;
        else if (state.equals("THROWBOMB")) THROWBOMB_STATE = b;
        else if (state.equals("CASTMAGIC")) CASTMAGIC_STATE = b;
        else super.setState(state, b);
    }

    @Override
    public void freeze() {
        dx = 0;
        dy = 0;
        setAbsoluteAnimation(Assets.STANDSHOOTING, spriteSheets.get(Assets.STANDSHOOTING).getSpriteRow(currDirection), 5);
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

    // input -> move -> animate -> updateAnim -> skill -> render
    @Override
    public void animate() {
        if (health == 0) {
            setAbsoluteAnimation(Assets.DIE, spriteSheets.get(Assets.DIE).getSpriteRow(currDirection), 5);
        } else if (DASH_STATE) { // Dash SpriteSheet is excess 1 frame at the head so it seems a bit buggy
            setAbsoluteAnimation(Assets.DASH, spriteSheets.get(Assets.DASH).getSpriteRow(currDirection), 5);
        } else if (RUN_STATE && SHOOTING_STATE) {
            setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(currDirection), 5);
        } else if (SHOOTING_STATE) {
            setAnimation(Assets.STANDSHOOTING, spriteSheets.get(Assets.STANDSHOOTING).getSpriteRow(currDirection), 5);
        } else if (RUN_STATE && THROWBOMB_STATE) {
            setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(currDirection), 5);
        } else if (THROWBOMB_STATE) {
            setAnimation(Assets.STANDSHOOTING, spriteSheets.get(Assets.STANDSHOOTING).getSpriteRow(currDirection), 5);
        } else if (RUN_STATE && CASTMAGIC_STATE) {
            setAnimation(Assets.RUNSHOOTING, spriteSheets.get(Assets.RUNSHOOTING).getSpriteRow(currDirection), 5);
        } else if (CASTMAGIC_STATE) {
            setAnimation(Assets.STANDSHOOTING, spriteSheets.get(Assets.STANDSHOOTING).getSpriteRow(currDirection), 5);
        } else if (RUN_STATE) {
            setAnimation(Assets.RUN, spriteSheets.get(Assets.RUN).getSpriteRow(currDirection), 5);
        } else {
            setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(currDirection), 5);
        }
    }

    @Override
    public void update(double time) {
        super.update(time);

        if (dx == 0 && dy == 0) { RUN_STATE = false; }

        if (!GameSceneManager.isSceneActive(GameSceneManager.WIN)) {
            loadPos();
        }

        List<NormBlock> blocks = PlayScene.tm.getNormalTile(tileCollision.getTile());
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) != null) {
                blocks.get(i).getImage().restoreColors();
                blocks.get(i).hasFog = false;
            }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);

        drawName(g2d, "Pixel Game", 16, "#468B6E");
    }

}
