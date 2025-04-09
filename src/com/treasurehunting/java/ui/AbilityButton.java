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
    public double time;

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
        radialImg.setValue(90);

        bounds = new AABB(pos, width, height);
        this.keyCode = keyCode;
    }

    public void updateRadialFill(double progress) {
        if (radialImg != null) {
            radialImg.setValue((int) (progress * 360));
        }
    }

    public void updateCooldownTime(double time) {

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

        if (time >= 0) {
            g2d.setFont(Assets.fontf.getFont("Pixel Game", 48));
            FontMetrics met = g2d.getFontMetrics(g2d.getFont());
            int width = met.stringWidth((int) time+"");
            int height = met.getHeight();
            g2d.drawString((int) time+"", width, height);
        }
    }

    public interface OnButtonPressedListener {
        void onButtonPressed(int index);
    }

}
