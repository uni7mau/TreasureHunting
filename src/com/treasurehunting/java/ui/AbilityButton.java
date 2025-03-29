package com.treasurehunting.java.ui;

import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AbilityButton {

    public JProgressBar radialImg;
    public BufferedImage abilityIcon;
    public AABB bounds;

    public int keyCode;
    public KeyHandler.Key key;

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
        key = KeyHandler.keys.get(keyCode);
    }

    public void updateRadialFill(double progress) {
        if (radialImg != null) {
            radialImg.setValue((int) (progress * 360));
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
    }

    public interface OnButtonPressedListener {
        void onButtonPressed(int index);
    }

}
