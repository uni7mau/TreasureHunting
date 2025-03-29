package com.treasurehunting.java.states;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.ui.Button;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;
import com.treasurehunting.java.utils.Preferences;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GameOverState extends GameState {

    private String lastWords = "GAME OVER";
    private BufferedImage imgButton;
    private BufferedImage imgHover;
    private Button btnReset;
    private Button btnQuit;
    private Font font;

    public GameOverState(GameStateManager gsm) {
        super(gsm);

        imgButton = Assets.buttonSS.getSubimage(0, 0, 121, 26);
        imgHover = Assets.buttonSS.getSubimage(0, 29, 122, 28);

        font = new Font("MeatMadness", Font.PLAIN, 48);
        btnReset = new Button(
                "RESTART",
                imgButton,
                font,
                new Vector2f((float)Preferences.GAME_WIDTH / 2, (float)Preferences.GAME_HEIGHT / 2 - 48),
                32,
                16
        );
        btnQuit = new Button(
                "QUIT",
                imgButton,
                font,
                new Vector2f((float)Preferences.GAME_WIDTH / 2, (float)Preferences.GAME_HEIGHT / 2 + 48),
                32,
                16
        );

        btnReset.addHoverImage(
                btnReset.createButton(
                        "RESTART",
                        imgHover,
                        font,
                        btnReset.getWidth(),
                        btnReset.getHeight(),
                        32,
                        20
                )
        );
        btnQuit.addHoverImage(
                btnQuit.createButton(
                        "QUIT",
                        imgHover,
                        font,
                        btnQuit.getWidth(),
                        btnQuit.getHeight(),
                        32,
                        20
                )
        );

        btnReset.addEvent(e -> {
            gsm.add(GameStateManager.PLAY);
            gsm.pop(GameStateManager.GAMEOVER);
        });
        btnQuit.addEvent(e -> {
            System.exit(0);
        });
    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {
        KeyHandler.keys.get(KeyHandler.ESCAPE).tick();

        btnReset.input(mouse, key);
        btnQuit.input(mouse, key);

        if (KeyHandler.keys.get(KeyHandler.ESCAPE).clicked) {
            System.exit(0);
        }
    }

    @Override
    public void update(double time) {  }

    @Override
    public void render(Graphics2D g2d) {
        SpriteSheet.drawArray(g2d, lastWords, new Vector2f((float)Preferences.GAME_WIDTH / 2 - lastWords.length() * (48 / 2), (float)Preferences.GAME_HEIGHT / 2 - (48 / 2) - 150), 64,  64, 48);
        btnReset.render(g2d);
        btnQuit.render(g2d);
    }

}
