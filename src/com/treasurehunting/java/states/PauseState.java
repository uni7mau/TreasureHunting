package com.treasurehunting.java.states;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.ui.Button;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;
import com.treasurehunting.java.utils.Preferences;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PauseState extends GameState {

//    private SettingsUI settingsUI;
    private Button btnResume;
    private Button btnSettings;
    private Button btnExit;
    private Font font;

    public PauseState(GameStateManager gsm) {
        super(gsm);

        BufferedImage imgButton = Assets.buttonSS.getSubimage(35, 1621, 57, 22);
        BufferedImage imgPressing = Assets.buttonSS.getSubimage(163, 1621, 57, 22);

        font = new Font("Pixel Game", Font.PLAIN, 48);
        btnResume = new Button(
                "RESUME",
                imgButton,
                font,
                new Vector2f((float)Preferences.GAME_WIDTH / 2, (float)Preferences.GAME_HEIGHT / 2 - 100),
                32,
                24
        );
        btnSettings = new Button(
                "SETTINGS",
                imgButton,
                font,
                new Vector2f((float)Preferences.GAME_WIDTH / 2, (float)Preferences.GAME_HEIGHT / 2),
                32,
                24
        );
        btnExit = new Button(
                "EXIT",
                imgButton,
                font,
                new Vector2f((float)Preferences.GAME_WIDTH / 2, (float)Preferences.GAME_HEIGHT / 2 + 100),
                32,
                24
        );

        btnResume.addHoverImage(
                btnResume.createButton(
                        "RESUME",
                        imgButton,
                        font,
                        btnResume.getWidth(),
                        btnResume.getHeight(),
                        32,
                        24
                )
        );
        btnSettings.addHoverImage(
                btnSettings.createButton(
                        "SETTINGS",
                        imgButton,
                        font,
                        btnSettings.getWidth(),
                        btnSettings.getHeight(),
                        32,
                        24
                )
        );
        btnExit.addHoverImage(
                btnExit.createButton(
                        "EXIT",
                        imgButton,
                        font,
                        btnExit.getWidth(),
                        btnExit.getHeight(),
                        32,
                        24
                )
        );

        btnResume.addPressingImage(
                btnResume.createButton(
                        "RESUME",
                        imgPressing,
                        font,
                        btnResume.getWidth(),
                        btnResume.getHeight(),
                        32,
                        16
                )
        );
        btnSettings.addPressingImage(
                btnSettings.createButton(
                        "SETTINGS",
                        imgPressing,
                        font,
                        btnSettings.getWidth(),
                        btnSettings.getHeight(),
                        32,
                        16
                )
        );
        btnExit.addPressingImage(
                btnExit.createButton(
                        "EXIT",
                        imgPressing,
                        font,
                        btnExit.getWidth(),
                        btnExit.getHeight(),
                        32,
                        16
                )
        );

        btnResume.addEvent(e -> {
            gsm.pop(GameStateManager.PAUSE);
        });
        btnSettings.addEvent(e -> {





        });
        btnExit.addEvent(e -> {
            System.exit(0);
        });
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        btnResume.input(mouse, key);
        btnSettings.input(mouse, key);
        btnExit.input(mouse, key);
//        settingsUI.input(mouse, key);
    }

    @Override
    public void update(double time) {
//        settingsUI.update(time);
    }

    @Override
    public void render(Graphics2D g2d) {
        btnResume.render(g2d);
        btnSettings.render(g2d);
        btnExit.render(g2d);
//        settingsUI.render(g2d);
    }

}
