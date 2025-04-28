package com.treasurehunting.java.ui;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.graphics.Animation;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;

import java.awt.*;

public class ManaBar {

    private SpriteSheet barSprite;
    private Animation energyAnim;
    private Entity owner;
    private Vector2f pos;
    private int scale;

    public ManaBar(Entity owner, Vector2f pos, int scale) {
        barSprite = Assets.manaBarSS;
        energyAnim = new Animation();
        energyAnim.setFrames(0, barSprite.getSpriteRow(3), 10);

        this.owner = owner;
        this.pos = pos;
        this.scale = scale;
    }

    public void update(double time) {
        energyAnim.update();
        if (owner.getManaPercent() >= 0.7f) {

        } else if (owner.getManaPercent() >= 0.5f) {

        } else if (owner.getManaPercent() >= 0.2f) {

        } else if (owner.getManaPercent() > 0f) {

        } else {

        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {

    }

    public void render(Graphics2D g2d) {
        g2d.drawImage(barSprite.getSprite(0, 0).image, (int)pos.x , (int)pos.y, barSprite.getSprite(0, 0).image.getWidth()*scale, barSprite.getSprite(0, 0).image.getHeight()*scale, null);
        g2d.drawImage(
                energyAnim.getImage().image.getSubimage(0, 0, 16 + (int) ((energyAnim.getImage().image.getWidth() - 16 - 2)*owner.getManaPercent()), energyAnim.getImage().image.getHeight()),
                (int)pos.x,
                (int)pos.y,
                (16 + (int) ((energyAnim.getImage().image.getWidth() - 16 - 2)*owner.getManaPercent()))*scale,
                energyAnim.getImage().image.getHeight()*scale,
                null
        );
        g2d.setFont(Assets.fontf.getFont("MeatMadness", 16));
        g2d.setColor(Color.WHITE);
        String mpIn4 = String.valueOf(owner.getMana() + "/" + owner.getMaxMana());
        g2d.drawString(
                mpIn4,
                pos.x + (float) (barSprite.getSprite(0, 0).image.getWidth() * scale) / 2 - ((float) mpIn4.length() / 2)*4,
                pos.y + (float) (barSprite.getSprite(0, 0).image.getHeight() * scale) / 2 + 5
        );
    }

}
