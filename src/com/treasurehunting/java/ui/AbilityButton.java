package com.treasurehunting.java.ui;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AbilityButton {

    public JProgressBar radialImg;
    public BufferedImage abilityIcon;
    public AABB bounds;

    public double cooldown = 0;
    public double activeTime = 0;
    public double currCooldown = 0;
    public double cooldownProgress = 1f;

    public int keyCode;

    private OnButtonPressedListener onButtonPressedListener;

    public void setOnButtonPressedListener(OnButtonPressedListener listener) { this.onButtonPressedListener = listener; }

    public AABB getBounds() { return bounds; }

    public AbilityButton(BufferedImage abilityIcon, Vector2f pos, int width, int height, int keyCode) {
        this.abilityIcon = abilityIcon;
        radialImg = new JProgressBar();
        radialImg.setBounds((int) pos.x, (int) pos.y, width, height);
        radialImg.setMinimum(0);
        radialImg.setMaximum(360);
        radialImg.setValue(0);

        bounds = new AABB(pos, width, height);
        this.keyCode = keyCode;
    }

    public void updateRadialFill(double progress) {
        if (radialImg != null) {
            radialImg.setValue((int) (progress * 360));
        }
    }

    public void updateCooldownTime(double time) {
        cooldownProgress = Math.max(0, (time / 1000000 - activeTime / 1000000) / cooldown);
        currCooldown = cooldown*(1 - cooldownProgress);
    }

    public void resetCP() {
        if (cooldownProgress < 0) {
            cooldownProgress = 1;
            currCooldown = cooldown;
        }
    }

    public void action() {
        if (onButtonPressedListener != null) {
            onButtonPressedListener.onButtonPressed(keyCode);
        }
    }

    public void render(Graphics2D g2d) {
        g2d.drawImage(abilityIcon, (int)bounds.getPos().x, (int)bounds.getPos().y, bounds.getWidth() , bounds.getHeight(), null);

        g2d.setColor(new Color(155, 155, 155, 150));
        g2d.fillArc((int) bounds.getPos().x, (int) bounds.getPos().y, radialImg.getWidth(), radialImg.getHeight(), 90, radialImg.getValue());

        if (currCooldown > 0) {
            g2d.setFont(Assets.fontf.getFont("Pixel Game", 32));
            FontMetrics met = g2d.getFontMetrics(g2d.getFont());
            int width = met.stringWidth(String.format("%.1f", currCooldown / 1000));
            g2d.drawString(String.format("%.1f", currCooldown / 1000), bounds.getPos().x + (float) bounds.getWidth() / 2 - ((float) width) / 2, bounds.getPos().y);
        }
    }

    public interface OnButtonPressedListener {
        void onButtonPressed(int index);
    }

}
