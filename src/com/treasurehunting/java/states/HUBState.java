package com.treasurehunting.java.states;

import com.treasurehunting.java.GamePanel;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.Fontf;
import com.treasurehunting.java.graphics.Sprite;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.ui.Button;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;
import com.treasurehunting.java.utils.Preferences;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HUBState extends GameState {

    private BufferedImage imgButton;
    private BufferedImage imgHover;
    private Button btnNewGame;
    private Button btnSettings;
    private Button btnExit;
    private Font font;

    public Sprite backgroundImg = Assets.backGroundImg.getSprite(0, 0);

    public HUBState(GameStateManager gsm) {
        super(gsm);

        imgButton = GameStateManager.button.getSubimage(35, 1621, 57, 22);
        imgHover = GameStateManager.button.getSubimage(163, 1621, 57, 22);


        font = new Font("Pixel Game", Font.PLAIN, 48);
        btnNewGame = new Button(
                "NEW GAME",
                imgButton,
                font,
                new Vector2f((float)Preferences.GAME_WIDTH / 2, (float)Preferences.GAME_HEIGHT / 2 + 20),
                50,
                20
        );
        btnSettings = new Button(
                "SETTINGS",
                imgButton,
                font,
                new Vector2f((float)Preferences.GAME_WIDTH / 2, (float)Preferences.GAME_HEIGHT / 2 + 100 + 20),
                50,
                20
        );
        btnExit = new Button(
                "EXIT",
                imgButton,
                font,
                new Vector2f((float)Preferences.GAME_WIDTH / 2, (float)Preferences.GAME_HEIGHT / 2 + 200 + 20),
                32,
                20
        );

        btnNewGame.addHoverImage(
                btnNewGame.createButton(
                        "NEW GAME",
                        imgHover,
                        font,
                        btnNewGame.getWidth(),
                        btnNewGame.getHeight(),
                        50,
                        16
                )
        );
        btnSettings.addHoverImage(
                btnSettings.createButton(
                        "SETTINGS",
                        imgHover,
                        font,
                        btnSettings.getWidth(),
                        btnSettings.getHeight(),
                        50,
                        16
                )
        );
        btnExit.addHoverImage(
                btnExit.createButton(
                        "EXIT",
                        imgHover,
                        font,
                        btnExit.getWidth(),
                        btnExit.getHeight(),
                        32,
                        16
                )
        );

        btnNewGame.addEvent(e -> {
            gsm.add(GameStateManager.PLAY);
            gsm.pop(GameStateManager.HUB);
        });
        btnSettings.addEvent(e -> {

        });
        btnExit.addEvent(e -> {
            System.exit(0);
        });
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        key.escape.tick();

        btnNewGame.input(mouse, key);
        btnSettings.input(mouse, key);
        btnExit.input(mouse, key);
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(backgroundImg.image, 0, 0, Preferences.GAME_WIDTH, Preferences.GAME_HEIGHT, null);

        btnNewGame.render(g2d);
        btnSettings.render(g2d);
        btnExit.render(g2d);

        g2d.setFont(GameStateManager.fontf.getFont("Pixel Game", 128));
        g2d.setColor(new Color(76, 0, 0));
        String logo = "TREASURE";
        String logoBelow = "HUNTING";
        g2d.drawString(logo, Preferences.GAME_WIDTH / 2 - (logo.length() / 2)*55, 200);
        g2d.drawString(logoBelow, Preferences.GAME_WIDTH / 2 - (logoBelow.length() / 2)*55 - 12, 300);
        g2d.setColor(new Color(255, 246, 246));
        g2d.drawString(logo, Preferences.GAME_WIDTH / 2 - (logo.length() / 2)*53, 200 - 10);
        g2d.drawString(logoBelow, Preferences.GAME_WIDTH / 2 - (logoBelow.length() / 2)*53 - 12, 300 - 10);

//        g2d.setColor(Color.WHITE);
//        g2d.drawLine(0, Preferences.GAME_HEIGHT/2, Preferences.GAME_WIDTH, Preferences.GAME_HEIGHT/2);
//        g2d.drawLine(Preferences.GAME_WIDTH/2, 0, Preferences.GAME_WIDTH/2, Preferences.GAME_HEIGHT);
    }

}
