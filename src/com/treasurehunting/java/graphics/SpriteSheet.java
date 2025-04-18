package com.treasurehunting.java.graphics;

import com.treasurehunting.java.math.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteSheet {

    private Sprite SPRITESHEET = null;
    private Sprite[][] spriteArray;
    public int w;
    public int h;
    private int wSprite;
    private int hSprite;
    private String file;

    public static Font currentFont;

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

    private BufferedImage loadSprite(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
        } catch (Exception e) {
            System.out.println("ERROR: could not load file: " + file);
        }

        return sprite;
    }

    public void loadSpriteArray() {
        spriteArray = new Sprite[hSprite][wSprite];

        for (int y = 0; y < hSprite; y++) {
            for (int x = 0; x < wSprite; x++) {
                spriteArray[y][x] = getSprite(x, y);
            }
        }
    }

    public void setEffect(Sprite.effect e) {
        SPRITESHEET.setEffect(e);
    }

    public Sprite getSprite(int x, int y) {
        return SPRITESHEET.getSubimage(x*w, y*h, w, h);
    }

    public Sprite getNewSprite(int x, int y) {
        return SPRITESHEET.getNewSubimage(x*w, y*h, w, h);
    }

    public Sprite getSprite(int x, int y, int w, int h) { return SPRITESHEET.getSubimage(x*w, y*h, w, h); }

    public BufferedImage getSubimage(int x, int y, int w, int h) {
        return SPRITESHEET.image.getSubimage(x, y, w, h);
    }

    public Sprite getSpriteImage(int x, int y, int w, int h) {
        return SPRITESHEET.getNewSubimage(x, y, w, h);
    }

    public Sprite[] getSpriteRow(int i) {
        return spriteArray[i];
    }

    public Sprite[][] getSpriteArray() { return spriteArray; }

    public static void drawArray(Graphics2D g2d, String word, Vector2f pos, int size) {
        drawArray(g2d, currentFont, word, pos, size, size, size, 0);
    }

    public static void drawArray(Graphics2D g2d, String word, Vector2f pos, int size, int xOffset) {
        drawArray(g2d, currentFont, word, pos, size, size, xOffset, 0);
    }

    public static void drawArray(Graphics2D g2d, String word, Vector2f pos, int width, int height, int xOffset) {
        drawArray(g2d, currentFont, word, pos, width, height, xOffset, 0);
    }

    public static void drawArray(Graphics2D g2d, Font f, String word, Vector2f pos, int size, int xOffset) {
        drawArray(g2d, f, word, pos, size, size, xOffset, 0);
    }

    public static void drawArray(Graphics2D g2d, Font f, String word, Vector2f pos, int width, int height, int xOffset, int yOffset) {
        float x = pos.x;
        float y = pos.y;

        currentFont = f;

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != 32) {
                g2d.drawImage(f.getLetter(word.charAt(i)), (int) x, (int) y, width, height, null);
            }
            x += xOffset;
            y += yOffset;
        }
    }

}
