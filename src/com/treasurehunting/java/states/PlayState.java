package com.treasurehunting.java.states;

import com.treasurehunting.java.GamePanel;
import com.treasurehunting.java.entity.Enemy;
import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.entity.enemy.*;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.bullets.Bomb;
import com.treasurehunting.java.obstacle.bullets.Bullet;
import com.treasurehunting.java.skills.Skill;
import com.treasurehunting.java.tiles.TileManager;
import com.treasurehunting.java.ui.PlayerUI;
import com.treasurehunting.java.utils.*;

import java.awt.*;
import java.util.ArrayList;

public class PlayState extends GameState {

    public static Player player;
    public static Vector2f map;
    public static Vector2f startMapPos;

    public static ArrayList<GameObject> gameObjects;
    public static TileManager tm;
    private static PlayerUI pui;
    public static ArrayList<Skill> activatingSkill = new ArrayList<>(); // ??

    public PlayState(GameStateManager gsm) {
        super(gsm);

        map = new Vector2f( 0, 8*Preferences.BLOCK_PIXEL - (int)(Preferences.GAME_HEIGHT / 2) );
        Vector2f.setWorldVar(map.x, map.y);
        startMapPos = new Vector2f(map);

        Vector2f playerPos = new Vector2f( 8*Preferences.BLOCK_PIXEL, 8*Preferences.BLOCK_PIXEL );
        Vector2f skeletonPos = new Vector2f( 12*Preferences.BLOCK_PIXEL, 13*Preferences.BLOCK_PIXEL );
        Vector2f toxicFruitPos = new Vector2f( 28*Preferences.BLOCK_PIXEL, 13*Preferences.BLOCK_PIXEL );
        Vector2f batPos = new Vector2f( 26*Preferences.BLOCK_PIXEL, 22*Preferences.BLOCK_PIXEL );
        Vector2f blueGolemBossPos = new Vector2f( 20*Preferences.BLOCK_PIXEL, 20*Preferences.BLOCK_PIXEL );

        tm = new TileManager(Assets.beginTileMap);

        player = new Player(
                Assets.playerSSGunIdle,
                playerPos,
                86,
                128
        );
        player.addSpriteSheet(Assets.RUN, Assets.playerSSGunRun);
        player.addSpriteSheet(Assets.STANDSHOOTING, Assets.playerSSGunStandShooting);
        player.addSpriteSheet(Assets.RUNSHOOTING, Assets.playerSSGunRunShooting);
        player.addSpriteSheet(Assets.DASH, Assets.playerSSGunDash);
        player.addSpriteSheet(Assets.RELOADING, Assets.playerSSGunReloading);
        player.addSpriteSheet(Assets.DIE, Assets.playerSSGunDeath);

        Enemy.player = player;

        pui = new PlayerUI(player);
        GameStateManager.cam.target(player);
        gameObjects = new ArrayList<>();

        //////////////
        //The logics here are stupid, need to be fixed soon
        Skeleton skeleton = new Skeleton(Assets.yellowSleketonSSIdle, skeletonPos, Assets.yellowSleketonSSIdle.getWidth(), Assets.yellowSleketonSSIdle.getHeight());
        skeleton.addSpriteSheet(Assets.WALK, Assets.yellowSleketonSSWalk);
        skeleton.addSpriteSheet(Assets.STRAIGHTATTACK, Assets.yellowSleketonSSSkill1);
        skeleton.addSpriteSheet(Assets.RANGEATTACK, Assets.yellowSleketonSSSkill2);
        skeleton.addSpriteSheet(Assets.INVINCIBLE, Assets.yellowSleketonSSHurt);
        skeleton.addSpriteSheet(Assets.DIE, Assets.yellowSleketonSSDie);

        ToxicFruit toxicFruit = new ToxicFruit(Assets.toxicFruitSSIdle, toxicFruitPos, Assets.toxicFruitSSIdle.getWidth(), Assets.toxicFruitSSIdle.getHeight());
        toxicFruit.addSpriteSheet(Assets.FLY, Assets.toxicFruitSSFly);
        toxicFruit.addSpriteSheet(Assets.SMASH, Assets.toxicFruitSSSkill1);
        toxicFruit.addSpriteSheet(Assets.INVINCIBLE, Assets.toxicFruitSSHurt);
        toxicFruit.addSpriteSheet(Assets.DIE, Assets.toxicFruitSSDie);

        Bat bat = new Bat(Assets.batSSIdleSleep, batPos, Assets.batSSIdleSleep.getWidth(), Assets.batSSIdleSleep.getHeight());
        bat.addSpriteSheet(Assets.FLY, Assets.batSSFly);
        bat.addSpriteSheet(Assets.WAKEUP, Assets.batSSWakeUp);
        bat.addSpriteSheet(Assets.RUN, Assets.batSSRun);
        bat.addSpriteSheet(Assets.RANGEATTACK, Assets.batSSSkill1);
        bat.addSpriteSheet(Assets.DASHSATTACK, Assets.batSSSkill2);
        bat.addSpriteSheet(Assets.INVINCIBLE, Assets.batSSHurt);
        bat.addSpriteSheet(Assets.DIE, Assets.batSSDie);

        BlueGolemBoss blueGolemBoss = new BlueGolemBoss( Assets.blueGolemSSIdle, blueGolemBossPos, Assets.blueGolemSSIdle.getWidth()*3, Assets.blueGolemSSIdle.getHeight()*3);
        blueGolemBoss.addSpriteSheet(Assets.WALK, Assets.blueGolemSSWalk);
        blueGolemBoss.addSpriteSheet(Assets.RANGEATTACK, Assets.blueGolemSSSkill1);
        blueGolemBoss.addSpriteSheet(Assets.INVINCIBLE, Assets.blueGolemSSHurt);
        blueGolemBoss.addSpriteSheet(Assets.DIE, Assets.blueGolemSSDie);

        gameObjects.add(skeleton);
        gameObjects.add(toxicFruit);
        gameObjects.add(bat);
        gameObjects.add(blueGolemBoss);

        /////////////

        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject tempGameObject = gameObjects.get(i);
            if (tempGameObject instanceof Enemy) {
                Enemy enemy = (Enemy) tempGameObject;
                enemy.addEnemy(player);
            }
        }
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        KeyHandler.keys.get(KeyHandler.ESCAPE).tick();
        KeyHandler.keys.get(KeyHandler.INVENTORY).tick();

        if (!gsm.isStateActive(GameStateManager.PAUSE)) {
            if (GameStateManager.cam.getTarget() == player) {
                player.input(mouse, key);
            }
            GameStateManager.cam.input(mouse, key);
            pui.input(mouse, key);
        }

        if (KeyHandler.keys.get(KeyHandler.ESCAPE).clicked) {
            if (gsm.isStateActive(GameStateManager.PAUSE)) {
                gsm.pop(GameStateManager.PAUSE);
            } else if (gsm.isStateActive(GameStateManager.MENU)) {
                gsm.pop(GameStateManager.MENU);
            } else {
                gsm.add(GameStateManager.PAUSE);
            }
        }

        if (KeyHandler.keys.get(KeyHandler.INVENTORY).clicked) {
            if (gsm.isStateActive(GameStateManager.MENU)) {
                gsm.pop(GameStateManager.MENU);
            } else {
                gsm.add(GameStateManager.MENU);
            }
        }
    }

    @Override
    public void update(double time) {
        Vector2f.setWorldVar(map.x, map.y);

        if (!gsm.isStateActive(GameStateManager.PAUSE) && !gsm.isStateActive(GameStateManager.MENU)) {
            player.update(time);
            pui.update(time);
            GameStateManager.cam.update();
            if (player.getState("DIE") && player.getAnimation().getCurrFrame() == player.getSpriteSheet(Assets.DIE).getSpriteRow(player.getCurrDirection()).length - 1) {
                gsm.add(GameStateManager.GAMEOVER);
                gsm.pop(GameStateManager.PLAY);
            }

            for (int i = 0; i < gameObjects.size(); i++){
                GameObject tempGameObject = gameObjects.get(i);
                if (tempGameObject instanceof Enemy enemy) {
                    if (enemy.getState("DIE") && enemy.getAnimation().getCurrFrame() == enemy.getSpriteSheet(Assets.DIE).getSpriteRow(enemy.getCurrDirection()).length - 1) gameObjects.remove(enemy);
                    else enemy.update(time);
                } else if (tempGameObject instanceof Bullet bullet) {
                    if (bullet.getState("DESTROY") && bullet.getAnimation().hasPlayedOnce()) gameObjects.remove(bullet);
                    else bullet.update(time);
                } else if (tempGameObject instanceof Bomb bomb) {
                    if (bomb.getState("DESTROY") && bomb.getAnimation().hasPlayedOnce()) gameObjects.remove(bomb);
                    else bomb.update(time);
                }
            }

            for (int i = 0; i < activatingSkill.size(); i++) { activatingSkill.get(i).update(time); }
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        tm.render(g2d);

        for (int i = 0; i < gameObjects.size(); i++) { gameObjects.get(i).render(g2d); }
        for (int i = 0; i < activatingSkill.size(); i++) { activatingSkill.get(i).render(g2d); }

        g2d.setFont(GameStateManager.fontf.getFont("MeatMadness", 32));
        g2d.setColor(Color.white);
        String fps = GamePanel.oldFrameCount + " FPS";
        g2d.drawString(fps, Preferences.GAME_WIDTH - 6*32, 32);
        String tps = GamePanel.oldTickCount + " TPS";
        g2d.drawString(tps, Preferences.GAME_WIDTH - 6*32, 64);

        player.render(g2d);
        pui.render(g2d);
        GameStateManager.cam.render(g2d);
    }

}