package com.treasurehunting.java.bundle;

import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.entity.enemy.Bat;
import com.treasurehunting.java.entity.enemy.BlueGolem;
import com.treasurehunting.java.entity.enemy.Skeleton;
import com.treasurehunting.java.entity.enemy.ToxicFruit;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;

import java.util.*;

public class EntityBundle {

    public static Map<Integer, List<Vector2f>> entityPos = new HashMap<>();

    public static void entityTileLoad(String data, int wQty, int hQty, int tileWidth, int tileHeight, int tileColumns) {
        entityPos.clear();

        String[] block = data.split(",");

        for (int i = 0; i < (wQty * hQty); i++) {
            int tileID = Integer.parseInt(block[i].replaceAll("\\s+",""));
            if (tileID != 0) {
                if (!entityPos.containsKey(tileID)) { entityPos.put(tileID, new ArrayList<>()); }
                entityPos.get(tileID).add(new Vector2f((int) (i % wQty) * tileWidth, (int) (i / hQty) * tileHeight));
            }
        }

    }

    public static Map<Integer, List<GameObject>> initialize(int mapID) {
        Map<Integer, List<GameObject>> res = new HashMap<>();

        if (mapID == 0) {
            for (Map.Entry<Integer, List<Vector2f>> entry : entityPos.entrySet()) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (entry.getKey() == Assets.playerTileID) {
                        if (!res.containsKey(Assets.playerTileID)) { res.put(Assets.playerTileID, new ArrayList<>()); }
                        res.get(Assets.playerTileID).add(new Player(entry.getValue().get(i)));
                    } else if (entry.getKey() == Assets.blueGolemTileID) {
                        if (!res.containsKey(Assets.blueGolemTileID)) { res.put(Assets.blueGolemTileID, new ArrayList<>()); }
                        res.get(Assets.blueGolemTileID).add(new BlueGolem(entry.getValue().get(i)));
                    } else if (entry.getKey() == Assets.batTileID) {
                        if (!res.containsKey(Assets.batTileID)) { res.put(Assets.batTileID, new ArrayList<>()); }
                        res.get(Assets.batTileID).add(new Bat(entry.getValue().get(i)));
                    }
                }
            }
        } else if (mapID == 1) {
            for (Map.Entry<Integer, List<Vector2f>> entry : entityPos.entrySet()) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    res.get(Assets.skeletonTileID).add(new Skeleton(entry.getValue().get(i)));
                }
            }
        } else if (mapID == 2) {
            for (Map.Entry<Integer, List<Vector2f>> entry : entityPos.entrySet()) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    res.get(Assets.toxicFruitTileID).add(new ToxicFruit(entry.getValue().get(i)));
                }
            }
        }

        return res;
    }

}
