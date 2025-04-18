package com.treasurehunting.java.ui;

import com.treasurehunting.java.entity.Entity;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class HealthBar {

    private ArrayList<BufferedImage> barSprites;
    private ArrayList<BufferedImage> heartSprites;

    private BufferedImage currBarSprite;
    private BufferedImage currHeartSprite;

    private Entity owner;

    private Vector2f pos;
    private int scale;

    public HealthBar(Entity owner, Vector2f pos, int scale) {
        barSprites = new ArrayList<>();
        heartSprites = new ArrayList<>();

        SpriteSheet healthSS = Assets.healthBarSS;
        barSprites.add(healthSS.getSubimage(0, 0, 120, 30)); // Sprites[0] = 100% hp;
        barSprites.add(healthSS.getSubimage(120, 0, 120, 30)); // Sprites[1] = 50% hp;
        barSprites.add(healthSS.getSubimage(240, 0 , 120, 30)); // Sprites[2] = 30% hp;
        barSprites.add(healthSS.getSubimage(360, 0, 120, 30)); // Sprites[3] = 10% hp;
        barSprites.add(healthSS.getSubimage(480, 0, 120, 30)); // Sprites[4] = 0% hp;

        heartSprites.add(healthSS.getSubimage(0, 30, 120, 30)); // Sprites[0] = 100% hp;
        heartSprites.add(healthSS.getSubimage(120, 30, 120, 30)); // Sprites[1] = 50% hp;
        heartSprites.add(healthSS.getSubimage(240, 30 , 120, 30)); // Sprites[2] = 30% hp;
        heartSprites.add(healthSS.getSubimage(360, 30, 120, 30)); // Sprites[3] = 10% hp;
        heartSprites.add(healthSS.getSubimage(480, 30, 120, 30)); // Sprites[4] = 0% hp;

        this.owner = owner;
        this.pos = pos;
        this.scale = scale;

        currBarSprite = barSprites.get(0);
        currHeartSprite = heartSprites.get(0);
    }

    public void update(double time) {
        if (owner.getHealthPercent() >= 0.7f) {
            currBarSprite = barSprites.get(0);
            currHeartSprite = heartSprites.get(0);
        } else if (owner.getHealthPercent() >= 0.5f) {
            currBarSprite = barSprites.get(1);
            currHeartSprite = heartSprites.get(1);
        } else if (owner.getHealthPercent() >= 0.2f) {
            currBarSprite = barSprites.get(2);
            currHeartSprite = heartSprites.get(2);
        } else if (owner.getHealthPercent() > 0f) {
            currBarSprite = barSprites.get(3);
            currHeartSprite = heartSprites.get(3);
        } else {
            currBarSprite = barSprites.get(4);
            currHeartSprite = heartSprites.get(4);
        }
        currHeartSprite = currHeartSprite.getSubimage(0, 0, 22 + (int) ((currHeartSprite.getWidth() - 22 - 12)*owner.getHealthPercent()), currHeartSprite.getHeight());
    }

    public void input(MouseHandler mouse, KeyHandler key) {

    }

    public void render(Graphics2D g2d) {
        g2d.drawImage(currBarSprite, (int)pos.x, (int)pos.y, currBarSprite.getWidth()*scale, currBarSprite.getHeight()*scale, null);
        g2d.drawImage(currHeartSprite, (int)pos.x , (int)pos.y, currHeartSprite.getWidth()*scale, currHeartSprite.getHeight()*scale, null);
        g2d.setFont(Assets.fontf.getFont("MeatMadness", 16));
        g2d.setColor(Color.white);
        String hpIn4 = String.valueOf(owner.getHealth() + "/" + owner.getMaxHealth());
        g2d.drawString(
                 hpIn4,
                pos.x + (float) (currBarSprite.getWidth() * scale) / 2 - ((float) hpIn4.length() / 2)*4,
                pos.y + (float) (currBarSprite.getHeight() * scale) / 2 + 8
        );
    }

}