package com.treasurehunting.java.graphics;

import java.awt.*;
import java.awt.Font;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Fontf {

    private Map<String, Map<Integer, Font>> fonts;

    public Fontf() {
        fonts = new HashMap<>();
    }

    public void loadFont(String path, String name, int size) {
        try {
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(path));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            fonts.putIfAbsent(name, new HashMap<>());
            fonts.get(name).put(size, new Font(name, Font.PLAIN, size));
        } catch (IOException | FontFormatException e) {
            System.out.println("ERROR: ttfFont - can't load font " + path + "...");
            e.printStackTrace();
        }
    }

    public void addSize(String name, int type, int size) {
        if (fonts.containsKey(name)) {
            fonts.get(name).put(size, new Font(name, type, size));
        }
    }

    public Font getFont(String name, int size) {
        if (fonts.containsKey(name) && fonts.get(name).containsKey(size)) {
            return fonts.get(name).get(size);
        }
        return null;
    }

}
