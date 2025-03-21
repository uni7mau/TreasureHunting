package com.treasurehunting.java.tiles.blocks;

import com.treasurehunting.java.graphics.Sprite;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;

import java.awt.*;

public class HoleBlock extends Block {

    public HoleBlock(Sprite sprite, Vector2f pos, int w, int h) {
        super(sprite, pos, w, h);
    }

    @Override
    public boolean update(AABB p) { return false; }

    @Override
    public boolean isInside(AABB p) {
        return (
                p.getPos().x + p.getXOffset() >= pos.x &&
                        p.getPos().y + p.getYOffset() >= pos.y &&
                        p.getPos().x + p.getXOffset() + p.getWidth() <= pos.x + w &&
                        p.getPos().y + p.getYOffset() + p.getHeight() <= pos.y + h
        );
    }

    @Override
    public Sprite getImage() {
        return sprite;
    }

    @Override
    public void render(Graphics2D g2d) {
        super.render(g2d);

        g2d.setColor(Color.magenta);
        g2d.drawRect((int)pos.getWorldVar().x, (int)pos.getWorldVar().y, w, h);
    }

}
