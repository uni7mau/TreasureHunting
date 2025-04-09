package com.treasurehunting.java.scene;

import com.treasurehunting.java.bundle.EntityBundle;
import com.treasurehunting.java.entity.Enemy;
import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.Obstacle;
import com.treasurehunting.java.obstacle.bullets.Bomb;
import com.treasurehunting.java.obstacle.bullets.Bullet;
import com.treasurehunting.java.tiles.TileManager;
import com.treasurehunting.java.ui.PlayerUI;
import com.treasurehunting.java.utils.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PlayScene extends GameScene {

    public static Vector2f map;
    public static Camera cam;
    public static TileManager tm;
    public static Map<Integer, List<GameObject>> gameObjects;
    public static Map<Integer, List<GameObject>> tobeAdded;
    public static PlayerUI pui;
    public static int enemyCount = 0;

    public static int currPlayer = 0;

    public PlayScene() {
        tm = new TileManager(Assets.takeMapID(Assets.dungeonMap));
        map = new Vector2f(0, 0);
        Vector2f.setWorldVar(map.x, map.y);
        cam = new Camera(
                new AABB(
                        new Vector2f(map),
                        GameSettings.GAME_WIDTH,
                        GameSettings.GAME_HEIGHT
                )
        );

        gameObjects = new HashMap<>();
        tobeAdded = new HashMap<>();
        gameObjects = EntityBundle.initialize(tm.getMapID());
        Player player = (Player) gameObjects.get(Assets.playerTileID).get(currPlayer); // Just 1 player, but this logic seems weird
        for (Map.Entry<Integer, List<GameObject>> entry : gameObjects.entrySet()) {
            if (entry.getKey() != Assets.playerTileID) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    Enemy enemy = (Enemy) entry.getValue().get(i);
                    player.addEnemy(enemy);
                    enemy.addEnemy(player);
                    enemyCount++;
                }
            }
        }
        cam.target(player);
        pui = new PlayerUI(player);
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        key.getKeys().get(GameSettings.ESCAPE).tick();

        if (!GameSceneManager.isStateActive(GameSceneManager.PAUSE) && !GameSceneManager.isStateActive(GameSceneManager.GAMEOVER)) {
            Player player = (Player) gameObjects.get(Assets.playerTileID).get(currPlayer);
            player.input(mouse, key);
            cam.input(mouse, key);
            pui.input(mouse, key);
        }

        if (key.getKeys().get(GameSettings.ESCAPE).clicked) {
            if (GameSceneManager.isStateActive(GameSceneManager.PAUSE)) {
                GameSceneManager.pop(GameSceneManager.PAUSE);
            } else {
                GameSceneManager.add(GameSceneManager.PAUSE);
            }
        }
    }

    @Override
    public void update(double time) {
        boolean hasRemovedSomething;
        do {
            hasRemovedSomething = false;

            for (Map.Entry<Integer, List<GameObject>> entry : gameObjects.entrySet()) {
                Iterator<GameObject> iterator = entry.getValue().iterator();

                while (iterator.hasNext()) {
                    GameObject obj = iterator.next();

                    if (obj instanceof Enemy enemy && enemy.getState("DIE")) {
                        iterator.remove();
                        enemyCount--;
                        hasRemovedSomething = true;
                    } else if (obj instanceof Obstacle obs && obs.getState("DESTROY")) {
                        iterator.remove();
                        hasRemovedSomething = true;
                    } else if (obj instanceof Player player && player.getState("DIE")) {
                        iterator.remove();
                        hasRemovedSomething = true;

                        if (!GameSceneManager.isStateActive(GameSceneManager.GAMEOVER)) {
                            GameSceneManager.add(GameSceneManager.GAMEOVER);
                        }
                    }
                }
            }
        } while (hasRemovedSomething);

        if (!GameSceneManager.isStateActive(GameSceneManager.PAUSE)) {
            for (Map.Entry<Integer, List<GameObject>> entry : gameObjects.entrySet()) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (PlayScene.cam.getBounds().collides(entry.getValue().get(i).getBounds())) {
                        if (entry.getValue().get(i) instanceof Enemy enemy) {
                            if (!gameObjects.get(Assets.playerTileID).isEmpty()) {
                                enemy.update(time, (Player) gameObjects.get(Assets.playerTileID).get(currPlayer));
                            }
                        } else if (entry.getValue().get(i) instanceof Obstacle obs) {
                            obs.update(time);
                        } else if (entry.getValue().get(i) instanceof Player player) {
                            player.update(time);
                            Vector2f.setWorldVar(map.x, map.y);
                            pui.update(time);
                            cam.update();
                        }
                    }
                }
            }
        }

        if (!tobeAdded.isEmpty()) {
            for (Map.Entry<Integer, List<GameObject>> entry : tobeAdded.entrySet()) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (!gameObjects.containsKey(entry.getKey())) gameObjects.put(entry.getKey(), new ArrayList<>());
                    gameObjects.get(entry.getKey()).add(entry.getValue().get(i));
                }
            }
            tobeAdded.clear();
        }
    }

    @Override
    public void render(Graphics2D g2d) {
        tm.render(g2d);

        for (Map.Entry<Integer, List<GameObject>> entry : gameObjects.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                entry.getValue().get(i).render(g2d);
            }
        }

        pui.render(g2d);
        cam.render(g2d);

        g2d.setColor(Color.cyan);
        g2d.drawString(enemyCount+"", 50, 500);
    }

}