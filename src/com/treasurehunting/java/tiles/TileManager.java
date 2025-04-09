package com.treasurehunting.java.tiles;

import com.treasurehunting.java.bundle.EntityBundle;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.tiles.blocks.NormBlock;
import com.treasurehunting.java.utils.GameSettings;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class TileManager {

    public static ArrayList<TileMap> tm;

    private SpriteSheet spriteSheet;
    private int blockWidth;
    private int blockHeight;
    private int blockWidthQty;
    private int blockHeightQty;

    private int mapID;
    private int columns;

    public TileManager() {
        tm = new ArrayList<>();
    }

    public TileManager(int mapID) {
        this();
        this.mapID = mapID;
        addTileMap(Assets.takeMapURL(mapID), GameSettings.TILE_SIZE, GameSettings.TILE_SIZE);
    }

    public TileManager(int mapID, int blockWidth, int blockHeight) {
        this();
        this.mapID = mapID;
        addTileMap(Assets.takeMapURL(mapID), blockWidth, blockHeight);
    }

    public int getBlockWidth() { return blockWidth; }
    public int getBlockHeight() { return blockHeight; }
    public int getMapID() { return mapID; }
    public int getColumns() { return columns; }

    private void addTileMap(String path, int blockWidth, int blockHeight) {
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
        String imagePath;

        int wQty = 0;
        int hQty = 0;
        int tileWidth;
        int tileHeight;
        int tileColumns;
        int layers;

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document doc = builder.parse(new File(getClass().getClassLoader().getResource(path).toURI()));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("tileset");
            Node node = list.item(0); //Tileset đầu tiên chứa các thông số cần thiết
            Element eElement = (Element)node;

            imagePath = eElement.getAttribute("name");
            tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
            tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
            tileColumns =  Integer.parseInt(eElement.getAttribute("columns"));

            this.columns = tileColumns;
            spriteSheet = new SpriteSheet("com/treasurehunting/assets/tile/" + imagePath + ".png", tileWidth, tileHeight);

            list = doc.getElementsByTagName("layer");
            layers = list.getLength();
            String[] data = new String[layers];

            for (int i = 0; i < layers; i++) {
                node = list.item(i);
                eElement = (Element) node;
                if (i == 0) {
                    wQty = Integer.parseInt(eElement.getAttribute("width"));
                    hQty = Integer.parseInt(eElement.getAttribute("height"));
                }

                data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();

                //Mặc định layer đầu tiên là layer tương tác, các layer cao hơn thì xếp chồng để hiển thị
                //Xếp layers từ thấp lên cao bằng cách hiển thị cao trước thấp sau, i càng lớn thì càng gần mắt người chơi
                //Is there any ways to make the player can interact with varies block at different layer ?
                if (i == layers - 1) {
                    EntityBundle.entityTileLoad(data[i], wQty, hQty, blockWidth, blockHeight, tileColumns);
                } else if (i == 0) {
                    tm.add(new TileMapObj(data[i], spriteSheet, wQty, hQty, blockWidth, blockHeight, tileColumns));
                } else {
                    tm.add(new TileMapNorm(data[i], spriteSheet, wQty, hQty, blockWidth, blockHeight, tileColumns));
                }
            }
            //Giới hạn khung hình cho camera: "Sprite Block Qty" * "Display Size". Ex: camWidthLimit = 50 x 64 = 3200, ...
            PlayScene.cam.setLimit(wQty*GameSettings.TILE_SIZE, hQty*GameSettings.TILE_SIZE);
        } catch(Exception e) {
            System.out.println("ERROR - TILEMANAGER: can not read tilemap:");
            e.printStackTrace();
            System.exit(0);
        }

        this.blockWidthQty = wQty;
        this.blockHeightQty = hQty;
    }

    public List<NormBlock> getNormalTile(int id) { // higher than surface layer + interact layer
        int normMap = Assets.normMapIndex;
        if (tm.size() < 2) normMap = 0;

        List<NormBlock> blocks = new ArrayList<>();

        for (int k = normMap; k < tm.size(); k++) {
            for (int x = 1; x >= -1; x--) {
                for (int y = 1; y >= -1; y--) {
                    if (id + (y + x * blockWidthQty) < 0 || id + (y + x * blockHeightQty) > (blockWidthQty * blockHeightQty) - 2) continue;
                    blocks.add((NormBlock)tm.get(k).getBlocks()[id + (y + x * blockHeightQty)]);
                }
            }
        }

        return blocks;
    }

    public boolean checkInFog(AABB bounds) {
        int normMap = Assets.normMapIndex;
        if (tm.size() < 2) normMap = 0;

        int xt1 = (int) ( bounds.getPos().x + bounds.getXOffset()) / GameSettings.TILE_SIZE;
        int yt1 = (int) ( bounds.getPos().y + bounds.getYOffset()) / GameSettings.TILE_SIZE;
        int xt2 = (int) ( bounds.getPos().x + bounds.getXOffset() + (float) bounds.getWidth() / 2) / GameSettings.TILE_SIZE;
        int yt2 = (int) ( bounds.getPos().y + bounds.getYOffset()) / GameSettings.TILE_SIZE;
        int xt3 = (int) ( bounds.getPos().x + bounds.getXOffset() + (float) bounds.getWidth() / 2) / GameSettings.TILE_SIZE;
        int yt3 = (int) ( bounds.getPos().y + bounds.getYOffset() + (float) bounds.getHeight() / 2) / GameSettings.TILE_SIZE;
        int xt4 = (int) ( bounds.getPos().x + bounds.getXOffset()) / GameSettings.TILE_SIZE;
        int yt4 = (int) ( bounds.getPos().y + bounds.getYOffset() + (float) bounds.getHeight() / 2) / GameSettings.TILE_SIZE;

        int idA = xt1 + yt1*blockWidthQty; // leftup
        int idB = xt2 + yt2*blockWidthQty; // rightup
        int idC = xt3 + yt3*blockWidthQty; // leftdown
        int idD = xt4 + yt4*blockWidthQty; // rightdown

        int cnt = 0;
        if (idA >= 0 && idA <= (blockWidthQty * blockHeightQty) - 2) {
            cnt += tm.get(normMap).getBlocks()[idA] != null && tm.get(normMap).getBlocks()[idA].hasFog ? 1 : 0;
        }
        if (idB >= 0 && idB <= (blockWidthQty * blockHeightQty) - 2) {
            cnt += tm.get(normMap).getBlocks()[idB] != null && tm.get(normMap).getBlocks()[idB].hasFog ? 1 : 0;
        }
        if (idC >= 0 && idC <= (blockWidthQty * blockHeightQty) - 2) {
            cnt += tm.get(normMap).getBlocks()[idC] != null && tm.get(normMap).getBlocks()[idC].hasFog ? 1 : 0;
        }
        if (idD >= 0 && idD <= (blockWidthQty * blockHeightQty) - 2) {
            cnt += tm.get(normMap).getBlocks()[idD] != null && tm.get(normMap).getBlocks()[idD].hasFog ? 1 : 0;
        }

        return cnt >= 2;
    }

    public void render(Graphics2D g2d) {
        for (int i = 0; i < tm.size(); i++) {
            tm.get(i).render(g2d);
        }
    }

}