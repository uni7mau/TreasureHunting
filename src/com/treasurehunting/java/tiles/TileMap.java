package com.treasurehunting.java.tiles;

import com.treasurehunting.java.tiles.blocks.Block;

import java.awt.*;

public abstract class TileMap {

    public abstract Block[] getBlocks();
    public abstract void render(Graphics2D g2d);

}
