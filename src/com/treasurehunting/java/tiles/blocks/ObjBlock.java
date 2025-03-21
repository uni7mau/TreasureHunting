package com.treasurehunting.java.tiles.blocks;

import com.treasurehunting.java.graphics.Sprite;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;

import java.awt.*;

public class ObjBlock extends Block {

    public ObjBlock(Sprite sprite, Vector2f pos, int w, int h) {
        super(sprite, pos, w, h);
    }

    @Override
    public boolean update(AABB p) {
        return true;
    }

    @Override
    public boolean isInside(AABB p) {
        return false;
    }

    @Override
    public Sprite getImage() { return sprite; }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);
        g2d.setColor(Color.white);
        g2d.drawRect((int)pos.getWorldVar().x, (int)pos.getWorldVar().y, w, h);
    }

}
