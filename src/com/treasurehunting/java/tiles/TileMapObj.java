package com.treasurehunting.java.tiles;

import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.tiles.blocks.*;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.GameSettings;

import java.awt.*;

public class TileMapObj extends TileMap {

    public static Block[] event_blocks;

    public static int wQty;
    public static int hQty;

    private int tileWidth;
    private int tileHeight;

    public TileMapObj(String data, SpriteSheet sprite, int wQty, int hQty, int tileWidth, int tileHeight, int tileColumns) {
        event_blocks = new Block[wQty * hQty];

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        TileMapObj.wQty = wQty;
        TileMapObj.hQty = hQty;

        String[] block = data.split(",");

        for (int i = 0; i < (wQty * hQty); i++) {
            long tileId = Long.parseLong(block[i].replaceAll("\\s+",""));
            Block tempBlock = null;
            if (tileId != 0) {
                if (tileId == 8) {
                    tempBlock = new HoleBlock(
                            sprite.getSprite((int) ((tileId - 1) % tileColumns), (int) ((tileId - 1) / tileColumns)),
                            new Vector2f((int) (i % wQty) * tileWidth, (int) (i / hQty) * tileHeight),
                            tileWidth,
                            tileHeight
                    );
                } else {
                    tempBlock = new ObjBlock(
                            new Vector2f((int) (i % wQty) * tileWidth, (int) (i / hQty) * tileHeight),
                            tileWidth,
                            tileHeight
                    );
                }
            }
            event_blocks[i] = tempBlock;
        }
    }

    @Override
    public Block[] getBlocks() { return event_blocks; }

    @Override
    public void render(Graphics2D g2d) {
        int x = (int)( (PlayScene.cam.getPos().x) / tileWidth );
        int y = (int)( (PlayScene.cam.getPos().y) / tileHeight );

        for (int i = x; i <= 1 + x + (int)(GameSettings.GAME_WIDTH / GameSettings.TILE_SIZE); i++) {
            for (int j = y; j <= 1 + y + (int)(GameSettings.GAME_HEIGHT / GameSettings.TILE_SIZE); j++) {
                if (i + (j * hQty) > -1 && i + (j * hQty) < event_blocks.length && event_blocks[i + (j * hQty)] != null) {
                    event_blocks[i + (j * hQty)].render(g2d);
                }
            }
        }
    }

}