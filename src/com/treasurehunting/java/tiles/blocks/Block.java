package com.treasurehunting.java.tiles.blocks;

import com.treasurehunting.java.graphics.Sprite;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;

import java.awt.*;

public abstract class Block {

    protected int w;
    protected int h;

    public Sprite sprite;
    public Vector2f pos;

    public boolean hasFog = true;

    public Block(Sprite sprite, Vector2f pos, int w, int h) {
        this.sprite = sprite;
        this.pos = pos;
        this.w = w;
        this.h = h;
    }

    public int getWidth() { return w; }
    public int getHeight() { return h; }

    public abstract boolean update(AABB p);
    public abstract boolean isInside(AABB p);

    public abstract Sprite getImage();
    public Vector2f getPos() { return pos; }

    public void render(Graphics2D g2d) {
        g2d.drawImage(sprite.image, (int)pos.getWorldVar().x, (int)pos.getWorldVar().y, w, h, null);
    }

}
