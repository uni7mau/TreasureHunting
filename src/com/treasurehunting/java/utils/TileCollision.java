package com.treasurehunting.java.utils;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.tiles.TileMapObj;
import com.treasurehunting.java.tiles.blocks.Block;
import com.treasurehunting.java.tiles.blocks.HoleBlock;

public class TileCollision {

    private Entity e;
    private int tileId;

    public TileCollision(Entity e) { this.e = e; }

    public int getTile() { return tileId; }

    public boolean normalTile(float ax, float ay) {
        int xt = (int) ( (e.getBounds().getPos().x + ax) + e.getBounds().getXOffset() + (float) e.getBounds().getWidth() / 2) / Preferences.BLOCK_PIXEL;
        int yt = (int) ( (e.getBounds().getPos().y + ay) + e.getBounds().getYOffset() + (float) e.getBounds().getHeight() / 2) / Preferences.BLOCK_PIXEL;

        tileId = (xt + (yt * TileMapObj.hQty));

        if (tileId > TileMapObj.hQty * TileMapObj.wQty) tileId = (TileMapObj.hQty * TileMapObj.wQty) - 2;

        return false;
    }

    public boolean collisionTile(float ax, float ay) {
        if (TileMapObj.event_blocks != null) {
            for (int c = 0; c < 4; c++) {
                int xt = (int) ( (e.getPos().x + ax) + (c % 2) * e.getBounds().getWidth() + e.getBounds().getXOffset()) / Preferences.BLOCK_PIXEL;
                int yt = (int) ( (e.getPos().y + ay) + (c / 2) * e.getBounds().getHeight() + e.getBounds().getYOffset()) / Preferences.BLOCK_PIXEL;

                if (xt <= 0 || yt <= 0
                        || xt + (yt * TileMapObj.hQty) < 0
                        || xt + (yt * TileMapObj.hQty) > (TileMapObj.hQty * TileMapObj.wQty) - 2) {
                    return true;
                }

                if (TileMapObj.event_blocks[xt + (yt * TileMapObj.hQty)] instanceof Block) {
                    Block block = TileMapObj.event_blocks[xt + (yt * TileMapObj.hQty)];
                    if (block instanceof HoleBlock) {
                        return collisionHole(ax, ay);
                    }
                    return block.update(e.getBounds());
                }
            }
        }

        return false;
    }

    public boolean collisionHole(float ax, float ay) {
        int nextXt1 = (int) (( (e.getPos().x + ax) + e.getBounds().getXOffset()) / Preferences.BLOCK_PIXEL);
        int nextYt1 = (int) (( (e.getPos().y + ay) + e.getBounds().getYOffset()) / Preferences.BLOCK_PIXEL);
        // Block RightDown
        int nextXt2 = (int) (( (e.getPos().x + ax) + e.getBounds().getXOffset() + (float) e.getBounds().getWidth() ) / Preferences.BLOCK_PIXEL);
        int nextYt2 = (int) (( (e.getPos().y + ay) + e.getBounds().getYOffset() + (float) e.getBounds().getHeight() ) / Preferences.BLOCK_PIXEL);
        // Block Right
        int nextXt3 = (int) (( (e.getPos().x + ax) + e.getBounds().getXOffset() + (float) e.getBounds().getWidth() ) / Preferences.BLOCK_PIXEL);
        int nextYt3 = (int) (( (e.getPos().y + ay) + e.getBounds().getYOffset()) / Preferences.BLOCK_PIXEL);
        // Block LeftDown
        int nextXt4 = (int) (( (e.getPos().x + ax) + e.getBounds().getXOffset()) / Preferences.BLOCK_PIXEL);
        int nextYt4 = (int) (( (e.getPos().y + ay) + e.getBounds().getYOffset() + (float) e.getBounds().getHeight() ) / Preferences.BLOCK_PIXEL);

        Block currBlock = TileMapObj.event_blocks[nextXt1 + (nextYt1 * TileMapObj.hQty)];
        Block rightBlock = TileMapObj.event_blocks[nextXt2 + (nextYt2 * TileMapObj.hQty)];
        Block rightDownBlock = TileMapObj.event_blocks[nextXt3 + (nextYt3 * TileMapObj.hQty)];
        Block leftDownBlock = TileMapObj.event_blocks[nextXt4 + (nextYt4 * TileMapObj.hQty)];
        if (currBlock instanceof HoleBlock && rightBlock instanceof HoleBlock && rightDownBlock instanceof HoleBlock && leftDownBlock instanceof HoleBlock) {
            e.setState("FALLEN", true);
        }

        return false;
    }

}
