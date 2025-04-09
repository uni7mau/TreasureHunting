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
        int xt = (int) ( (e.getBounds().getPos().x + ax) + e.getBounds().getXOffset() + (float) e.getBounds().getWidth() / 2) / GameSettings.TILE_SIZE;
        int yt = (int) ( (e.getBounds().getPos().y + ay) + e.getBounds().getYOffset() + (float) e.getBounds().getHeight() / 2) / GameSettings.TILE_SIZE;

        tileId = (xt + (yt * TileMapObj.hQty));

        if (tileId > TileMapObj.hQty * TileMapObj.wQty) tileId = (TileMapObj.hQty * TileMapObj.wQty) - 2;

        return false;
    }

    public boolean collisionTile(float ax, float ay) {
        if (TileMapObj.event_blocks != null) {
            int nextXt1 = (int) (( (e.getPos().x + ax) + e.getBounds().getXOffset()) / GameSettings.TILE_SIZE);
            int nextYt1 = (int) (( (e.getPos().y + ay) + e.getBounds().getYOffset()) / GameSettings.TILE_SIZE);
            // Block RightDown
            int nextXt2 = (int) (( (e.getPos().x + ax) + e.getBounds().getXOffset() + (float) e.getBounds().getWidth() ) / GameSettings.TILE_SIZE);
            int nextYt2 = (int) (( (e.getPos().y + ay) + e.getBounds().getYOffset() + (float) e.getBounds().getHeight() ) / GameSettings.TILE_SIZE);
            // Block Right
            int nextXt3 = (int) (( (e.getPos().x + ax) + e.getBounds().getXOffset() + (float) e.getBounds().getWidth() ) / GameSettings.TILE_SIZE);
            int nextYt3 = (int) (( (e.getPos().y + ay) + e.getBounds().getYOffset()) / GameSettings.TILE_SIZE);
            // Block LeftDown
            int nextXt4 = (int) (( (e.getPos().x + ax) + e.getBounds().getXOffset()) / GameSettings.TILE_SIZE);
            int nextYt4 = (int) (( (e.getPos().y + ay) + e.getBounds().getYOffset() + (float) e.getBounds().getHeight() ) / GameSettings.TILE_SIZE);

            if (nextXt1 <= 0 || nextYt1 <= 0 || nextXt2 <= 0 || nextYt2 <= 0 || nextXt3 <= 0 || nextYt3 <= 0 || nextXt4 <= 0 || nextYt4 <= 0 ||
                    nextXt1 >= TileMapObj.wQty || nextYt1 >= TileMapObj.hQty || nextXt2 >= TileMapObj.wQty || nextYt2 >= TileMapObj.hQty || nextXt3 >= TileMapObj.wQty || nextYt3 >= TileMapObj.hQty || nextXt4 >= TileMapObj.wQty || nextYt4 >= TileMapObj.hQty) {
                return true;
            }

            Block currBlock = TileMapObj.event_blocks[nextXt1 + (nextYt1 * TileMapObj.hQty)];
            Block rightBlock = TileMapObj.event_blocks[nextXt2 + (nextYt2 * TileMapObj.hQty)];
            Block rightDownBlock = TileMapObj.event_blocks[nextXt3 + (nextYt3 * TileMapObj.hQty)];
            Block leftDownBlock = TileMapObj.event_blocks[nextXt4 + (nextYt4 * TileMapObj.hQty)];

            if (currBlock instanceof HoleBlock && rightBlock instanceof HoleBlock && rightDownBlock instanceof HoleBlock && leftDownBlock instanceof HoleBlock) {
                e.setState("FALLEN", true);

                return false;
            }

            if (currBlock != null ) {
                return currBlock.update(e.getBounds());
            }
            if (rightBlock != null ) {
                return rightBlock.update(e.getBounds());
            }
            if (leftDownBlock != null ) {
                return leftDownBlock.update(e.getBounds());
            }
            if (rightDownBlock != null ) {
                return rightDownBlock.update(e.getBounds());
            }
        }

        return false;
    }

}
