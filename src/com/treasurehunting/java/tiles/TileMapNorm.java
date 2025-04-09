package com.treasurehunting.java.tiles;

import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.tiles.blocks.Block;
import com.treasurehunting.java.tiles.blocks.NormBlock;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.GameSettings;

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

        for (int i = 0; i < (wQty * hQty); i++) {
            long tileId = Long.parseLong(block[i].replaceAll("\\s+",""));
            if (tileId != 0) {
                blocks[i] = new NormBlock(
                        sprite.getNewSprite( (int)( (tileId-1)%tileColumns ), (int)( (tileId-1)/tileColumns ) ),
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
        int x = (int)( (PlayScene.cam.getPos().x) / tileWidth );
        int y = (int)( (PlayScene.cam.getPos().y) / tileHeight );

        // Render từ góc trên bên trái - 1 cho đến góc dưới bên phải + 1
        // width: 1524 / 64 = 23.81
        // height: 768 / 64 = 12
        // Nếu camera lấy nửa block thì render cả block bị chia đó
        // Chiều ngang bị lẻ, vậy thì làm tròn lên render 24 block
        // Vì camera có trường hợp render lẻ nên cần
        // Ví dụ: camera-pos: 1,5 -> 5.7, camera-size: 4,2 với map-width là 8, block-size là 1 thì render block 1-2, 2-3, 3-4, 4-5, 5-6
        // tổng là 5 block, tức render theo vị trí như sau: [1 -> 5]
        // Công thức hóa: [(int)camera-pos/block-size -> (int)camera-size/block-size + 1]
        for (int i = x; i <= 1 + x + (int)(GameSettings.GAME_WIDTH / GameSettings.TILE_SIZE); i++) {
            for (int j = y; j <= 1 + y + (int)(GameSettings.GAME_HEIGHT / GameSettings.TILE_SIZE); j++) {
                if (i + (j * hQty) > -1 && i + (j * hQty) < blocks.length && blocks[i + (j * hQty)] != null) {
                    blocks[i + (j * hQty)].render(g2d);
                }
            }
        }
    }

}
