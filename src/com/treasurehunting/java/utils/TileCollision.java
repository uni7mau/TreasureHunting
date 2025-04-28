package com.treasurehunting.java.utils;

import com.treasurehunting.java.gameobjects.GameObject;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.scene.PlayScene;
import com.treasurehunting.java.tiles.TileMapObj;
import com.treasurehunting.java.tiles.blocks.Block;
import com.treasurehunting.java.tiles.blocks.HoleBlock;

public class TileCollision {

    private GameObject owner;
    private int tileId;

    public TileCollision(GameObject go) { owner = go; }

    public int getTile() { return tileId; }

    public boolean normalTile(float ax, float ay) {
        int xt = (int) ( (owner.getBounds().getPos().x + ax) + owner.getBounds().getXOffset() + (float) owner.getBounds().getWidth() / 2) / Assets.TILE_SIZE;
        int yt = (int) ( (owner.getBounds().getPos().y + ay) + owner.getBounds().getYOffset() + (float) owner.getBounds().getHeight() / 2) / Assets.TILE_SIZE;

        tileId = (xt + (yt * TileMapObj.hQty));

        if (tileId > TileMapObj.hQty * TileMapObj.wQty) tileId = (TileMapObj.hQty * TileMapObj.wQty) - 2;

        return false;
    }

    public int destroyTile(float ax, float ay) {
        // Lấy tọa độ bounds của owner
        float left   = owner.getPos().x + ax + owner.getBounds().getXOffset();
        float top    = owner.getPos().y + ay + owner.getBounds().getYOffset();
        float right  = left + owner.getBounds().getWidth();
        float bottom = top + owner.getBounds().getHeight();

        // Đổi thành tọa độ tile
        int tileLeft   = (int) (left / Assets.TILE_SIZE);
        int tileTop    = (int) (top / Assets.TILE_SIZE);
        int tileRight  = (int) (right / Assets.TILE_SIZE);
        int tileBottom = (int) (bottom / Assets.TILE_SIZE);

        int destroyed = 0;

        for (int ty = tileTop; ty <= tileBottom; ty++) {
            for (int tx = tileLeft; tx <= tileRight; tx++) {
                int index = tx + (ty * TileMapObj.hQty);

                if (index < 0 || index >= TileMapObj.event_blocks.length) continue;

                Block block = TileMapObj.event_blocks[index];

                if (block == null || block instanceof HoleBlock) continue;

                PlayScene.tm.destroyLink(index);
                destroyed++;
            }
        }

        return destroyed;
    }


    public boolean collisionTile(float ax, float ay) {
        if (!(TileMapObj.event_blocks == null)) {
            if (
                    (owner.getPos().x + ax + owner.getBounds().getXOffset() < 0) ||
                            (owner.getPos().y + ay + owner.getBounds().getYOffset() < 0) ||
                            (owner.getPos().x + ax + owner.getBounds().getXOffset() + (float) owner.getBounds().getWidth() < 0) ||
                            (owner.getPos().y + ay + owner.getBounds().getYOffset() + (float) owner.getBounds().getHeight() < 0) ||

                            (owner.getPos().x + ax + owner.getBounds().getXOffset() + (float) owner.getBounds().getWidth() > TileMapObj.wQty* Assets.TILE_SIZE) ||
                            (owner.getPos().y + ay + owner.getBounds().getYOffset() > TileMapObj.hQty* Assets.TILE_SIZE) ||
                            (owner.getPos().x + ax + owner.getBounds().getXOffset() > TileMapObj.wQty* Assets.TILE_SIZE) ||
                            (owner.getPos().y + ay + owner.getBounds().getYOffset() + (float) owner.getBounds().getHeight() > TileMapObj.hQty* Assets.TILE_SIZE)
            ) {
                return true;
            }

            // Block Left
            int nextXt1 = (int) (( (owner.getPos().x + ax) + owner.getBounds().getXOffset()) / Assets.TILE_SIZE);
            int nextYt1 = (int) (( (owner.getPos().y + ay) + owner.getBounds().getYOffset()) / Assets.TILE_SIZE);
            // Block RightDown
            int nextXt2 = (int) (( (owner.getPos().x + ax) + owner.getBounds().getXOffset() + (float) owner.getBounds().getWidth() ) / Assets.TILE_SIZE);
            int nextYt2 = (int) (( (owner.getPos().y + ay) + owner.getBounds().getYOffset() + (float) owner.getBounds().getHeight() ) / Assets.TILE_SIZE);
            // Block Right
            int nextXt3 = (int) (( (owner.getPos().x + ax) + owner.getBounds().getXOffset() + (float) owner.getBounds().getWidth() ) / Assets.TILE_SIZE);
            int nextYt3 = (int) (( (owner.getPos().y + ay) + owner.getBounds().getYOffset()) / Assets.TILE_SIZE);
            // Block LeftDown
            int nextXt4 = (int) (( (owner.getPos().x + ax) + owner.getBounds().getXOffset()) / Assets.TILE_SIZE);
            int nextYt4 = (int) (( (owner.getPos().y + ay) + owner.getBounds().getYOffset() + (float) owner.getBounds().getHeight() ) / Assets.TILE_SIZE);

            // Vùng giao tại cạnh trái và cạnh trên là x = 0 hoặc y = 0, từ -48 -> 48 dù có chia cho 48 thì nó vẫn tính là 0
            // => cách này không dùng được
//            if (nextXt1 < 0 || nextYt1 < 0 || nextXt2 < 0 || nextYt2 < 0 || nextXt3 < 0 || nextYt3 < 0 || nextXt4 < 0 || nextYt4 < 0 ||
//                    nextXt1 >= TileMapObj.wQty || nextYt1 >= TileMapObj.hQty || nextXt2 >= TileMapObj.wQty || nextYt2 >= TileMapObj.hQty || nextXt3 >= TileMapObj.wQty || nextYt3 >= TileMapObj.hQty || nextXt4 >= TileMapObj.wQty || nextYt4 >= TileMapObj.hQty) {
//                return true;
//            }

            Block leftBlock = TileMapObj.event_blocks[nextXt1 + (nextYt1 * TileMapObj.hQty)];
            Block rightDownBlock = TileMapObj.event_blocks[nextXt2 + (nextYt2 * TileMapObj.hQty)];
            Block rightBlock = TileMapObj.event_blocks[nextXt3 + (nextYt3 * TileMapObj.hQty)];
            Block leftDownBlock = TileMapObj.event_blocks[nextXt4 + (nextYt4 * TileMapObj.hQty)];

            if (leftBlock instanceof HoleBlock && rightBlock instanceof HoleBlock && rightDownBlock instanceof HoleBlock && leftDownBlock instanceof HoleBlock) {
                if (!owner.getState("FLY")) {
                    owner.setState("FALLEN", true);
                }

                return false;
            }

            boolean finalDecision = false;
            if (leftBlock != null ) { finalDecision = leftBlock.update(owner.getBounds()) || finalDecision;  }
            if (rightBlock != null ) { finalDecision = rightBlock.update(owner.getBounds()) || finalDecision; }
            if (leftDownBlock != null ) { finalDecision = leftDownBlock.update(owner.getBounds()) || finalDecision; }
            if (rightDownBlock != null ) { finalDecision = rightDownBlock.update(owner.getBounds()) || finalDecision; }

            return finalDecision;
        }

        return false;
    }

}
