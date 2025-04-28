package com.treasurehunting.java.bundle;

import com.treasurehunting.java.gameobjects.GameObject;
import com.treasurehunting.java.gameobjects.obstacles.affects.*;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObstacleBundle {

    public static Map<Integer, List<Vector2f>> obsPos = new HashMap<>();

    public static void obsTileLoad(String data, int wQty, int hQty, int tileWidth, int tileHeight) {
        obsPos.clear();

        String[] block = data.split(",");

        for (int i = 0; i < (wQty * hQty); i++) {
            int tileID = Integer.parseInt(block[i].replaceAll("\\s+",""));
            if (tileID != 0) {
                if (!obsPos.containsKey(tileID)) { obsPos.put(tileID, new ArrayList<>()); }
                obsPos.get(tileID).add(new Vector2f((int) (i % wQty) * tileWidth, (int) (i / hQty) * tileHeight));
            }
        }

    }

    public static Map<Integer, List<GameObject>> initialize(int mapID) {
        Map<Integer, List<GameObject>> res = new HashMap<>();

        if (mapID == Assets.takeMapID(Assets.dungeonMap)) {
            for (Map.Entry<Integer, List<Vector2f>> entry : obsPos.entrySet()) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (entry.getKey() == Assets.chestTileID) {
                        if (!res.containsKey(Assets.chestTileID)) { res.put(Assets.chestTileID, new ArrayList<>()); }
                        res.get(Assets.chestTileID).add(new Chest(entry.getValue().get(i)));
                    } else if (entry.getKey() == Assets.boxTileID) {
                        if (!res.containsKey(Assets.boxTileID)) { res.put(Assets.boxTileID, new ArrayList<>()); }
                        res.get(Assets.boxTileID).add(new Box(entry.getValue().get(i)));
                    } else if (entry.getKey() == Assets.portalTileID) {
                        if (!res.containsKey(Assets.portalTileID)) { res.put(Assets.portalTileID, new ArrayList<>()); }
                        res.get(Assets.portalTileID).add(new Portal(entry.getValue().get(i)));
                    }
                }
            }
        } else if (mapID == Assets.takeMapID(Assets.graveMazeMap)) {
            for (Map.Entry<Integer, List<Vector2f>> entry : obsPos.entrySet()) {
                for (int i = 0; i < entry.getValue().size(); i++) {
                    if (entry.getKey() == Assets.chestTileID) {
                        if (!res.containsKey(Assets.chestTileID)) { res.put(Assets.chestTileID, new ArrayList<>()); }
                        res.get(Assets.chestTileID).add(new Chest(entry.getValue().get(i)));
                    } else if (entry.getKey() == Assets.spiderNestTileID) {
                        if (!res.containsKey(Assets.spiderNestTileID)) { res.put(Assets.spiderNestTileID, new ArrayList<>()); }
                        res.get(Assets.spiderNestTileID).add(new SpiderNest(entry.getValue().get(i)));
                    } else if (entry.getKey() == Assets.trapTileID) {
                        if (!res.containsKey(Assets.trapTileID)) { res.put(Assets.trapTileID, new ArrayList<>()); }
                        res.get(Assets.trapTileID).add(new Trap(entry.getValue().get(i)));
                    } else if (entry.getKey() == Assets.portalTileID) {
                        if (!res.containsKey(Assets.portalTileID)) { res.put(Assets.portalTileID, new ArrayList<>()); }
                        res.get(Assets.portalTileID).add(new Portal(entry.getValue().get(i)));
                    }
                }
            }
        }

        return res;
    }

}
