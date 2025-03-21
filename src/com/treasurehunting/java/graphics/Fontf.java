package com.treasurehunting.java.graphics;

import java.awt.*;
import java.awt.Font;
import java.io.IOException;
import java.util.HashMap;

public class Fontf {

    private HashMap<String, Font> fonts;

    public Fontf() { fonts = new HashMap<>(); }

    public void loadFont(String path, String name, int size) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(path));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            Font font = new Font(name, Font.PLAIN, size);
            fonts.put(name, font);
        } catch (IOException | FontFormatException e) {
            System.out.println("ERROR: ttfFont - can't load font " + path + "...");
            e.printStackTrace();
        }
    }

    public Font getFont(String name) {
        return fonts.get(name);
    }

}
