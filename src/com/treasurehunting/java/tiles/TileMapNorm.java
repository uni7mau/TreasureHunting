package com.treasurehunting.java.tiles;

import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.states.GameStateManager;
import com.treasurehunting.java.tiles.blocks.Block;
import com.treasurehunting.java.tiles.blocks.NormBlock;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.Preferences;

import java.awt.*;

public class TileMapNorm extends TileMap {

    public Block[] blocks;

    private int hQty;

    private int tileWidth;
    private int tileHeight;

    public TileMapNorm(String data, SpriteSheet sprite, int wQty, int hQty, int tileWidth, int tileHeight, int tileColumns) {
        blocks = new Block[wQty * hQty];

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        this.hQty = hQty;

        String[] block = data.split(",");

        for(int i = 0; i < (wQty * hQty); i++) {
            int temp = Integer.parseInt(block[i].replaceAll("\\s+",""));
            if(temp != 0) {
                blocks[i] = new NormBlock(
                        sprite.getNewSprite( (int)( (temp-1)%tileColumns ), (int)( (temp-1)/tileColumns ) ),
                        new Vector2f( (int)(i%wQty) * tileWidth, (int)(i/hQty) * tileHeight ),
                        tileWidth,
                        tileHeight
                );
            }
        }
    }

    @Override
    public Block[] getBlocks() { return blocks; }

    @Override
    public void render(Graphics2D g2d) {
        int x = (int)( (GameStateManager.cam.getPos().x) / tileWidth );
        int y = (int)( (GameStateManager.cam.getPos().y) / tileHeight );

        // Render từ góc trên bên trái - 1 cho đến góc dưới bên phải + 1
        // width: 1524 / 64 = 23.81
        // height: 768 / 64 = 12
        // Nếu camera lấy nửa block thì render cả block bị chia đó
        // Chiều ngang bị lẻ, vậy thì làm tròn lên render 24 block
        // Vì camera có trường hợp render lẻ nên cần
        // Ví dụ: camera-pos: 1,5 -> 5.7, camera-size: 4,2 với map-width là 8, block-size là 1 thì render block 1-2, 2-3, 3-4, 4-5, 5-6
        // tổng là 5 block, tức render theo vị trí như sau: [1 -> 5]
        // Công thức hóa: [(int)camera-pos/block-size -> (int)camera-size/block-size + 1]
        for (int i = x; i <= 1 + x + (int)(Preferences.GAME_WIDTH / Preferences.BLOCK_PIXEL); i++) {
            for (int j = y; j <= 1 + y + (int)(Preferences.GAME_HEIGHT / Preferences.BLOCK_PIXEL); j++) {
                if (i + (j * hQty) > -1 && i + (j * hQty) < blocks.length && blocks[i + (j * hQty)] != null) {
                    blocks[i + (j * hQty)].render(g2d);
                }
            }
        }
    }

}
