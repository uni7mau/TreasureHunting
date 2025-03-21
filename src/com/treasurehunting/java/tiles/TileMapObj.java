package com.treasurehunting.java.tiles;

import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.states.GameStateManager;
import com.treasurehunting.java.tiles.blocks.Block;
import com.treasurehunting.java.tiles.blocks.HoleBlock;
import com.treasurehunting.java.tiles.blocks.ObjBlock;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.Preferences;

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
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
            if (temp != 0) {
                Block tempBlock;
                if (temp == 172) {
                    // TODO: find edge and connect them to form one polygon
                    tempBlock = new HoleBlock(
                            sprite.getSprite( (int)( (temp-1)%tileColumns ), (int)( (temp-1)/tileColumns ) ),
                            new Vector2f( (int)(i%wQty) * tileWidth, (int)(i/hQty) * tileHeight ),
                            tileWidth,
                            tileHeight
                    );
                } else {
                    tempBlock = new ObjBlock(
                            sprite.getSprite( (int)( (temp-1)%tileColumns ), (int)( (temp-1)/tileColumns ) ),
                            new Vector2f( (int)(i%wQty) * tileWidth, (int)(i/hQty) * tileHeight ),
                            tileWidth,
                            tileHeight
                    );
                }
                event_blocks[i] = tempBlock;
            }
        }
    }

    @Override
    public Block[] getBlocks() { return event_blocks; }

    @Override
    public void render(Graphics2D g2d) {
        int x = (int)( (GameStateManager.cam.getPos().x) / tileWidth );
        int y = (int)( (GameStateManager.cam.getPos().y) / tileHeight );

        for (int i = x; i <= 1 + x + (int)(Preferences.GAME_WIDTH / Preferences.BLOCK_PIXEL); i++) {
            for (int j = y; j <= 1 + y + (int)(Preferences.GAME_HEIGHT / Preferences.BLOCK_PIXEL); j++) {
                if (i + (j * hQty) > -1 && i + (j * hQty) < event_blocks.length && event_blocks[i + (j * hQty)] != null) {
                    event_blocks[i + (j * hQty)].render(g2d);
                }
            }
        }
    }

}