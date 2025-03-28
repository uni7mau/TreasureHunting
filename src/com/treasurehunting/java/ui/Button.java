package com.treasurehunting.java.ui;

import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.states.GameStateManager;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Button {

    private BufferedImage image;
    private BufferedImage hoverImage;
    private BufferedImage pressingImage;
    private BufferedImage pressedImage;

    private Vector2f iPos;

    private AABB bounds;
    private boolean hovering = false;
    private boolean pressing = false;
    private ArrayList<ClickedEvent> events;
    private boolean clicked = false;
    private boolean pressed = false;
    private boolean canHover = true;

    // ******************************************** ICON CUSTOM POS *******************************************

    public Button(BufferedImage icon, BufferedImage image, Vector2f pos, int width, int height, int iconSize) {
        this.image = createIconButton(icon, image, width + iconSize, height + iconSize, iconSize);
        this.iPos = pos;
        this.bounds = new AABB(iPos, this.image.getWidth(), this.image.getHeight());

        events = new ArrayList<>();
        this.canHover = false;
    }

    private BufferedImage createIconButton(BufferedImage icon, BufferedImage image, int width, int height, int iconsize) {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        if (image.getWidth() != width || image.getHeight() != height) {
            image = resizeImage(image, width, height);
        }

        if (icon.getWidth() != width - iconsize || icon.getHeight() != height - iconsize) {
            icon = resizeImage(icon, width - iconsize, height - iconsize);
        }

        Graphics g = result.getGraphics();
        g.drawImage(image, 0, 0, width, height, null);

        g.drawImage(icon,
                image.getWidth() / 2 - icon.getWidth() / 2,
                image.getHeight() / 2 - icon.getHeight() / 2,
                icon.getWidth(), icon.getHeight(), null);

        g.dispose();

        return result;
    }

    // ******************************************** LABEL TTF CUSTOM MIDDLE POS *******************************************

    public Button(String label, BufferedImage image, Font font, Vector2f pos, int buttonWidth, int buttonHeight) {
        GameStateManager.g2d.setFont(font);
        FontMetrics met = GameStateManager.g2d.getFontMetrics(font);
        int height = met.getHeight();
        int width = met.stringWidth(label);

        this.image = createButton(label, image, font, width + buttonWidth, height + buttonHeight, buttonWidth, buttonHeight);
        this.iPos = new Vector2f(pos.x - this.image.getWidth() / 2, pos.y - this.image.getHeight() / 2);
        this.bounds = new AABB(iPos, this.image.getWidth(), this.image.getHeight());

        events = new ArrayList<ClickedEvent>();
        this.canHover = false;
    }

    public BufferedImage createButton(String label, BufferedImage image, Font font, int width, int height, int buttonWidth, int buttonHeight) {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        if (image.getWidth() != width || image.getHeight() != height) {
            image = resizeImage(image, width, height);
        }

        Graphics g = result.getGraphics();
        g.drawImage(image, 0, 0, width, height, null);

        g.setFont(font);
        g.drawString(label, buttonWidth / 2, (height - buttonHeight));

        g.dispose();

        return result;
    }

    private BufferedImage resizeImage(BufferedImage image, int width, int height) {
        Image temp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();

        g.drawImage(temp, 0, 0, null);
        g.dispose();

        return result;
    }

    // ******************************************** END ************************************************************

    public void addHoverImage(BufferedImage image) {
        this.hoverImage = image;
        this.canHover = true;
    }

    public void addPressedImage(BufferedImage image) {
        this.pressedImage = image;
    }

    public void addPressingImage(BufferedImage image) { pressingImage = image; }

    public void addEvent(ClickedEvent e) { events.add(e); }

    public int getWidth() { return (int) bounds.getWidth(); }
    public int getHeight() { return (int) bounds.getHeight(); }
    public Vector2f getPos() { return bounds.getPos(); }

    public void input(MouseHandler mouse, KeyHandler key) {
        if (bounds.inside(mouse.getX(), mouse.getY())) {
            hovering = true;
            if (mouse.getButton() == 1) {
                if (!clicked) {
                    clicked = true;
                    pressing = true;
                }
            } else if (mouse.getButton() == -1) {
                if (clicked) {
                    pressed = true;
                    pressing = false;
                    for (int i = 0; i < events.size(); i++) {
                        events.get(i).action(1);
                    }
                    clicked = false;
                }
            } else {
                pressed = false;
            }
        } else {
            if (canHover && hovering) {
                hovering = false;
            }
            pressing = false;
        }
    }

    public void update(double time) {
    }

    public void render(Graphics2D g2d) {
        if (pressingImage != null && pressing) {
            g2d.drawImage(pressingImage, (int) iPos.x + bounds.getWidth() / 2 - pressingImage.getWidth() / 2, (int) iPos.y + bounds.getHeight() / 2 - pressingImage.getHeight() / 2, pressingImage.getWidth(), pressingImage.getHeight(), null);
        } else {
            g2d.drawImage(image, (int) iPos.x, (int) iPos.y, bounds.getWidth(), bounds.getHeight(), null);
            if (canHover && hoverImage != null && hovering) {
                g2d.drawImage(hoverImage, (int) iPos.x + bounds.getWidth() / 2 - hoverImage.getWidth() / 2, (int) iPos.y + bounds.getHeight() / 2 - hoverImage.getHeight() / 2, hoverImage.getWidth(), hoverImage.getHeight(), null);
            }
            if (pressedImage != null && pressed) {
                g2d.drawImage(pressedImage, (int) iPos.x + bounds.getWidth() / 2 - pressedImage.getWidth() / 2, (int) iPos.y + bounds.getHeight() / 2 - pressedImage.getHeight() / 2, pressedImage.getWidth(), pressedImage.getHeight(), null);
            }
        }

//        g2d.drawRect((int) iPos.x + bounds.getWidth() / 2 - hoverImage.getWidth() / 2, (int) iPos.y + bounds.getHeight() / 2 - hoverImage.getHeight() / 2, hoverImage.getWidth(), hoverImage.getHeight());
    }

    public interface ClickedEvent {
        void action(int mouseButton);
    }

}
