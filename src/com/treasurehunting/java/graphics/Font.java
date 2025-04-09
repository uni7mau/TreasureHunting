package com.treasurehunting.java.graphics;

import com.treasurehunting.java.utils.GameSettings;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Font {

    public BufferedImage FONTSHEET = null;
    private BufferedImage[][] spriteArray;
    public int w;
    public int h;
    private int wLetter;
    private int hLetter;

    public Font(String file) {
        w = GameSettings.GAME_WIDTH;
        h = GameSettings.GAME_HEIGHT;

        FONTSHEET = loadFont(file);

        wLetter = FONTSHEET.getWidth()/w;
        hLetter = FONTSHEET.getHeight()/h;
        loadFontArray();
    }

    public Font(String file, int w, int h) {
        this.w = w;
        this.h = h;

        FONTSHEET = loadFont(file);

        wLetter = FONTSHEET.getWidth()/w;
        hLetter = FONTSHEET.getHeight()/h;
        loadFontArray();
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public void setWidth(int i) {
        w = i;
        wLetter = FONTSHEET.getWidth()/w;
    }

    public void setHeight(int i) {
        h = i;
        hLetter = FONTSHEET.getHeight()/h;
    }

    public int getWidth() { return w; }

    public int getHeight() { return h; }

    private BufferedImage loadFont(String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(file)));
        } catch (Exception e) {
            System.out.println("ERROR: Could not load file: " + file);
        }

        return sprite;
    }

    public void loadFontArray() {
        spriteArray = new BufferedImage[wLetter][hLetter];

        for (int x = 0; x < wLetter; x++) {
            for (int y = 0; y < hLetter; y++) {
                spriteArray[x][y] = getLetter(x, y);
            }
        }
    }

    public BufferedImage getFontSheet() { return FONTSHEET; }

    public BufferedImage getLetter(int x, int y) {
        return FONTSHEET.getSubimage(x*w, y*h, w, h);
    }

    public BufferedImage getLetter(char letter) {
        int value = letter;

        int x = value%wLetter;
        int y = value/wLetter;

        return spriteArray[x][y];
    }

}