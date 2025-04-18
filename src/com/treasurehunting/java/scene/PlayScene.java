package com.treasurehunting.java.scene;

import com.treasurehunting.java.bundle.EntityBundle;
import com.treasurehunting.java.bundle.ObstacleBundle;
import com.treasurehunting.java.entity.Enemy;
import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.obstacle.Mana;
import com.treasurehunting.java.obstacle.Obstacle;
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
        tm = new TileManager(Assets.currMapID);
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
        Player player = (Player) gameObjects.get(Assets.playerTileID).get(currPlayer);
        pui = new PlayerUI(player);
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
        gameObjects.putAll(ObstacleBundle.initialize(tm.getMapID()));

        map.setOri(
                Math.max(0, player.getPos().x + player.getBounds().getXOffset() + player.getBounds().getWidth() / 2 - GameSettings.GAME_WIDTH / 2),
                Math.max(0, player.getPos().y + player.getBounds().getYOffset() + player.getBounds().getHeight() / 2 - GameSettings.GAME_HEIGHT / 2)
        );
        cam.getPos().setOri(map.oriX, map.oriY);

        map.resetOri();
        cam.getPos().resetOri();

        cam.target(player);
        if (Assets.currMapID == 0) { ScoreSave.playTime = System.nanoTime(); }
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        if (!GameSceneManager.isStateActive(GameSceneManager.PAUSE) && !GameSceneManager.isStateActive(GameSceneManager.GAMEOVER) && !GameSceneManager.isStateActive(GameSceneManager.WIN)) {
            Player player = (Player) gameObjects.get(Assets.playerTileID).get(currPlayer);
            player.input(mouse, key);
            cam.input(mouse, key);
            pui.input(mouse, key);
        }
    }

    @Override
    public void update(double time) {
        if (!GameSceneManager.isStateActive(GameSceneManager.PAUSE)) {
            for (Map.Entry<Integer, List<GameObject>> entry : gameObjects.entrySet()) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (entry.getValue().get(i) instanceof Enemy enemy) {
                        if (!gameObjects.get(Assets.playerTileID).isEmpty()) {
                            enemy.update(time, (Player) gameObjects.get(Assets.playerTileID).get(currPlayer));
                        } else {
                            enemy.update(time);
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

        boolean hasRemovedSomething;
        do {
            hasRemovedSomething = false;
            for (Map.Entry<Integer, List<GameObject>> entry : gameObjects.entrySet()) {
                Iterator<GameObject> iterator = entry.getValue().iterator();
                while (iterator.hasNext()) {
                    GameObject obj = iterator.next();
                    if (obj.getState("DIE")) {
                        if (obj instanceof Enemy) {
                            enemyCount--;
                            if (enemyCount == 0) {
                                GameSceneManager.add(GameSceneManager.WIN);
                            }
                        }
                        if (obj instanceof Player) {
                            if (!GameSceneManager.isStateActive(GameSceneManager.GAMEOVER)) {
                                GameSceneManager.add(GameSceneManager.GAMEOVER);
                            }
                        }
                        iterator.remove();
                        hasRemovedSomething = true;

                        if (obj instanceof Entity) {
                            if (!(tobeAdded.containsKey(Assets.manaTileID))) { tobeAdded.put(Assets.manaTileID, new ArrayList<>()); }
                            tobeAdded.get(Assets.manaTileID).add(new Mana(
                                    new Vector2f(
                                            obj.getBounds().getPos().x + obj.getBounds().getXOffset() + (float) obj.getBounds().getWidth() / 2 - (float) Mana.w / 2,
                                            obj.getBounds().getPos().y + obj.getBounds().getYOffset() + (float) obj.getBounds().getHeight() / 2 - (float) Mana.h / 2
                                    )
                            ));
                        }
                    }

                }
            }
        } while (hasRemovedSomething);

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
                if (entry.getValue().get(i) instanceof Obstacle) {
                    if (cam.getBounds().collides(entry.getValue().get(i).getBounds())) {
                        entry.getValue().get(i).render(g2d);
                    }
                }
            }
        }

        for (Map.Entry<Integer, List<GameObject>> entry : gameObjects.entrySet()) {
            for (int i = 0; i < entry.getValue().size(); i++) {
                if (entry.getValue().get(i) instanceof Entity) {
                    if (cam.getBounds().collides(entry.getValue().get(i).getBounds())) {
                        entry.getValue().get(i).render(g2d);
                    }
                }
            }
        }

        pui.render(g2d);
        cam.render(g2d);

        g2d.setColor(Color.cyan);
        g2d.setFont(Assets.fontf.getFont("Pixel Game", 32));
        g2d.drawString(enemyCount + "", 50, GameSettings.GAME_HEIGHT / 2);
    }

}