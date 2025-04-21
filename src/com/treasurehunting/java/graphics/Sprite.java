package com.treasurehunting.java.graphics;

import com.treasurehunting.java.math.Matrix;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Sprite {

    public BufferedImage image;

    private int[] pixels;
    private int[] ogPixels;
    private double rotatedDeg = 0;

    private int w;
    private int h;

    public enum effect {NORMAL, SEPIA, REDISH, GRAYSCALE, NEGATIVE, DECAY, DARKNESS};

    private float[][] id = {{1.0f, 0.0f, 0.0f},
                            {0.0f, 1.0f, 0.0f},
                            {0.0f, 0.0f, 1.0f},
                            {0.0f, 0.0f, 0.0f}};

    private float[][] negative = {{1.0f, 0.0f, 0.0f},
                                  {0.0f, 1.0f, 0.0f},
                                  {0.0f, 0.0f, 1.0f},
                                  {0.0f, 0.0f, 0.0f}};

    private float[][] decay = {{0.000f, 0.333f, 0.333f},
                               {0.333f, 0.000f, 0.333f},
                               {0.333f, 0.333f, 0.000f},
                               {0.000f, 0.000f, 0.000f}};

    private float[][] sepia = {{0.393f, 0.349f, 0.272f},
                               {0.769f, 0.686f, 0.534f},
                               {0.189f, 0.168f, 0.131f},
                               {0.000f, 0.000f, 0.000f}};

    private float[][] redish = {{1.0f, 0.0f, 0.0f},
                                {0.0f, 0.3f, 0.0f},
                                {0.0f, 0.0f, 0.3f},
                                {0.0f, 0.0f, 0.0f}};

    private float[][] grayscale = {{0.333f, 0.333f, 0.333f},
                                   {0.333f, 0.333f, 0.333f},
                                   {0.333f, 0.333f, 0.333f},
                                   {0.000f, 0.000f, 0.000f}};

    private float[][] darkness = {
            {0.2f, 0.0f, 0.0f},
            {0.0f, 0.2f, 0.0f},
            {0.0f, 0.0f, 0.2f},
            {0.0f, 0.0f, 0.0f}
    };

    private float[][] currentEffect = id;

    public Sprite(int w, int h) {
        this.w = w;
        this. h = h;
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    }

    public Sprite(BufferedImage image) {
        this.image = image;
        this.w = image.getWidth();
        this.h = image.getHeight();
        ogPixels = image.getRGB(0, 0, w, h, ogPixels, 0, w);
        pixels = ogPixels;
    }

    public int getWidth() { return w; }

    public int getHeight() { return h; }

    public void saveColors() {
        pixels = image.getRGB(0, 0, w, h, pixels, 0, w);
        currentEffect = id;
    }

    public void restoreColors() {
        image.setRGB(0, 0, w, h, pixels, 0, w);
    }

    public void restoreDefault() {
        image.setRGB(0, 0, w, h, ogPixels, 0, w);
    }

    // in #FFFFFF format
    public Color hexToColor(String color) {
        return new Color(
                Integer.valueOf(color.substring(1, 3), 16),
                Integer.valueOf(color.substring(3, 5), 16),
                Integer.valueOf(color.substring(5, 7), 16));
    }

    public void setContrast(float value) {
        float[][] effect = id;
        float contrast = (259*(value+255))/(255*(259-value));
        for (int i = 0; i < 3; i++) {
            effect[i][i] = contrast;
            effect[3][i] = 128*(1-contrast);
        }

        addEffect(effect);
    }

    public void setBrightness(float value) {
        float[][] effect = id;
        for (int i = 0; i < 3; i++) {
            effect[3][i] = value;
        }

        addEffect(effect);
    }

    public void setEffect(effect e) {
        float[][] effect;
        switch (e) {
            case SEPIA: effect = sepia;
                break;
            case REDISH: effect = redish;
                break;
            case GRAYSCALE: effect = grayscale;
                break;
            case NEGATIVE: effect = negative;
                break;
            case DECAY: effect = decay;
                break;
            case DARKNESS: effect = darkness;
                break;
            default: effect = id;
        }

        if (effect != currentEffect) {
            addEffect(effect);
        }
    }

    private void addEffect(float[][] effect) {
        float[][] rgb = new float[1][4];
        float[][] xrgb;
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int p = pixels[x + y*w];

                int a = (p >> 24) & 0xff;

                rgb[0][0] = (p >> 16) & 0xff;
                rgb[0][1] = (p >> 8) & 0xff;
                rgb[0][2] = (p) & 0xff;
                rgb[0][3] = 1f;

                xrgb = Matrix.multiply(rgb, effect);

                for (int i = 0; i < 3; i++) {
                    if (xrgb[0][i] > 255) rgb[0][i] = 255;
                    else if (xrgb[0][i] < 0) rgb[0][i] = 0;
                    else rgb[0][i] = xrgb[0][i];
                }

                p = (a<<24) | ((int) rgb[0][0]<<16) | ((int) rgb[0][1]<<8) | (int) rgb[0][2];
                image.setRGB(x, y, p);
            }
        }
        currentEffect = effect;
    }

    public double getRotatedDeg() {
        return rotatedDeg;
    }

    public Sprite getSubImage(int x, int y, int w, int h) {
        return new Sprite(image.getSubimage(x, y, w, h));
    }

    public Sprite getNewSubImage(int x, int y, int w, int h) {
        BufferedImage temp = image.getSubimage(x, y, w, h);
        BufferedImage newImage = new BufferedImage(image.getColorModel(), image.getRaster().createCompatibleWritableRaster(w, h), image.isAlphaPremultiplied(), null);
        temp.copyData(newImage.getRaster());

        return new Sprite(newImage);
    }

    public Sprite getNewSubImage() {
        return getNewSubImage(0, 0, this.w, this.h);
    }

    public void setRotatedImage(double angleDegrees) {
        double centerX = image.getWidth() / 2.0;
        double centerY = image.getHeight() / 2.0;
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angleDegrees), centerX, centerY);
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D g2d = (Graphics2D) result.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        g2d.dispose();

        image = result;
        rotatedDeg = angleDegrees;

        saveColors();
    }

    public void setResizeImage(int width, int height) {
        Image temp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = result.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(temp, 0, 0, null);
        g2d.dispose();

        image = result;
        w = result.getWidth();
        h = result.getHeight();
    }

}