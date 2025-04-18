package com.treasurehunting.java.tiles;

import com.treasurehunting.java.tiles.blocks.Block;

import java.awt.*;

public abstract class TileMap {

    public static final long FLIP_HORIZONTAL = 0x80000000L;
    public static final long FLIP_VERTICAL = 0x40000000L;
    public static final long FLIP_DIAGONAL = 0x20000000L;

    public static long decode_gid_takeID(long gid) {
        long GID_MASK = ~(FLIP_HORIZONTAL | FLIP_VERTICAL | FLIP_DIAGONAL);

        return gid & GID_MASK;
    }

    public static TileData decode_gid_takeDEG(long gid) {
        long tileId = decode_gid_takeID(gid);
        boolean flipH = (gid & FLIP_HORIZONTAL) != 0;
        boolean flipV = (gid & FLIP_VERTICAL) != 0;
        boolean flipD = (gid & FLIP_DIAGONAL) != 0;

        int rotation = 0;
        if (flipD) {
            if (flipH && flipV) {
                rotation = 90;
            } else if (flipH) {
                rotation = 270;
            } else if (flipV) {
                rotation = 90;
            } else {
                rotation = 270;
            }
        } else {
            if (flipH && flipV) {
                rotation = 180;
            }
        }

        return new TileData(tileId, flipH, flipV, flipD, rotation);
    }

    public abstract Block[] getBlocks();
    public abstract void render(Graphics2D g2d);

    public static class TileData {
        public long tileId;
        public boolean flipH;
        public boolean flipV;
        public boolean flipD;
        public int rotation;

        public TileData(long tileId, boolean flipH, boolean flipV, boolean flipD, int rotation) {
            this.tileId = tileId;
            this.flipH = flipH;
            this.flipV = flipV;
            this.flipD = flipD;
            this.rotation = rotation;
        }
    }

}
