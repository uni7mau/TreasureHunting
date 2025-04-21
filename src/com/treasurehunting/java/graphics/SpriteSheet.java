package com.treasurehunting.java.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SpriteSheet {

    private Sprite SPRITESHEET = null;
    private Sprite[][] spriteArray;
    private int w;
    private int h;
    private int wSprite;
    private int hSprite;
    private String file;

    public SpriteSheet(String file) {
        this.file = file;
        w = 32;
        h = 32;

        SPRITESHEET = new Sprite(loadSprite(file));

        wSprite = SPRITESHEET.image.getWidth() / w;
        hSprite = SPRITESHEET.image.getHeight() / h;
        loadSpriteArray();
    }

    public SpriteSheet(String file, int w, int h) {
        this.file = file;
        this.w = w;
        this.h = h;

        SPRITESHEET = new Sprite(loadSprite(file));

        wSprite = SPRITESHEET.image.getWidth() / w;
        hSprite = SPRITESHEET.image.getHeight() / h;
        loadSpriteArray();
    }

    //For sprite sheets that contain many different type off enemy, go, ...
    public SpriteSheet(Sprite sprite, int w, int h) {
        this.w = w;
        this.h = h;

        SPRITESHEET = sprite;

        wSprite = SPRITESHEET.image.getWidth() / w;
        hSprite = SPRITESHEET.image.getHeight() / h;
        loadSpriteArray();

    }

    private BufferedImage loadSprite(String file) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("ERROR: could not load file: " + file);
        }

        return img;
    }

    private void loadSpriteArray() {
        spriteArray = new Sprite[hSprite][wSprite];

        for (int y = 0; y < hSprite; y++) {
            for (int x = 0; x < wSprite; x++) {
                spriteArray[y][x] = getSprite(x, y);
            }
        }
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int i) {
        w = i;
        wSprite = SPRITESHEET.image.getWidth() / w;
    }

    public void setHeight(int i) {
        h = i;
        hSprite = SPRITESHEET.image.getHeight() / h;
    }

    public int getWidth() { return w; }
    public int getHeight() { return h; }
    public int getRows() { return hSprite; }
    public int getCols() { return wSprite; }
    public String getFilename() { return file; }

    public void setEffect(Sprite.effect e) {
        SPRITESHEET.setEffect(e);
    }

    public Sprite getSprite(int x, int y) {
        return SPRITESHEET.getSubImage(x*w, y*h, w, h);
    }

    public Sprite getNewSprite(int x, int y) {
        return SPRITESHEET.getNewSubImage(x*w, y*h, w, h);
    }

    public Sprite getSprite(int x, int y, int w, int h) { return SPRITESHEET.getSubImage(x*w, y*h, w, h); }

    public BufferedImage getSubimage(int x, int y, int w, int h) {
        return SPRITESHEET.image.getSubimage(x, y, w, h);
    }

    public Sprite getSpriteImage(int x, int y, int w, int h) {
        return SPRITESHEET.getNewSubImage(x, y, w, h);
    }

    public Sprite[] getSpriteRow(int i) {
        return spriteArray[i];
    }

    public Sprite[][] getSpriteArray() { return spriteArray; }

}
