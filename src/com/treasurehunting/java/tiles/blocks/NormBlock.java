package com.treasurehunting.java.tiles.blocks;

import com.treasurehunting.java.graphics.Sprite;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;

import java.awt.*;

public class NormBlock extends Block {

    public NormBlock(Sprite sprite, Vector2f pos, int w, int h) {
        super(sprite, pos, w, h);

        sprite.setEffect(Sprite.effect.DARKNESS);
    }

    @Override
    public boolean update(AABB p) {
        return false;
    }

    @Override
    public boolean isInside(AABB p) { return false; }

    @Override
    public Sprite getImage() { return sprite; }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);
    }

}
