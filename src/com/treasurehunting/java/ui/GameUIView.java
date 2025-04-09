package com.treasurehunting.java.ui;

import com.treasurehunting.java.GamePanel;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.Sprite;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.GameSceneManager;
import com.treasurehunting.java.utils.GameSettings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class GameUIView {

    public Map<Integer, Button> hubUI = new HashMap<>();
    public Map<Integer, Button> pauseUI = new HashMap<>();
    public Map<Integer, Button> gameOverUI = new HashMap<>();

    public GameUIView() {
        SpriteSheet.currentFont = new com.treasurehunting.java.graphics.Font(Assets.font, 10, 10);

        BufferedImage imgButton = Assets.buttonSS.getSubimage(35, 1621, 57, 22);
        BufferedImage imgHover = Assets.buttonSS.getSubimage(57, 1496, 17, 31);
        BufferedImage imgPressing = Assets.buttonSS.getSubimage(163, 1621, 57, 22);
        Button btnNewGame = new Button(
                "NEW GAME",
                imgButton,
                Assets.fontf.getFont("Pixel Game", 48),
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2 + 20),
                57,
                24
        );
        Button btnResume = new Button(
                "RESUME",
                imgButton,
                Assets.fontf.getFont("Pixel Game", 48),
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2 + 20),
                57,
                24
        );
        Button btnReplay = new Button(
                "REPLAY",
                imgButton,
                Assets.fontf.getFont("Pixel Game", 48),
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2 + 20),
                57,
                24
        );
        Button btnSettings = new Button(
                "SETTINGS",
                imgButton,
                Assets.fontf.getFont("Pixel Game", 48),
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2 + 100 + 20),
                57,
                24
        );
        Button btnExit = new Button(
                "EXIT",
                imgButton,
                Assets.fontf.getFont("Pixel Game", 48),
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2 + 200 + 20),
                32,
                24
        );

        btnNewGame.addHoverImage(
                btnNewGame.createPointerButton(
                        "",
                        imgHover,
                        Assets.fontf.getFont("Pixel Game", 48),
                        btnNewGame.getWidth() + 100,
                        btnNewGame.getHeight(),
                        57,
                        24
                )
        );
        btnResume.addHoverImage(
                btnResume.createPointerButton(
                        "",
                        imgHover,
                        Assets.fontf.getFont("Pixel Game", 48),
                        btnResume.getWidth() + 100,
                        btnResume.getHeight(),
                        57,
                        24
                )
        );
        btnReplay.addHoverImage(
                btnReplay.createPointerButton(
                        "",
                        imgHover,
                        Assets.fontf.getFont("Pixel Game", 48),
                        btnReplay.getWidth() + 100,
                        btnReplay.getHeight(),
                        57,
                        24
                )
        );
        btnSettings.addHoverImage(
                btnSettings.createPointerButton(
                        "",
                        imgHover,
                        Assets.fontf.getFont("Pixel Game", 48),
                        btnSettings.getWidth() + 100,
                        btnSettings.getHeight(),
                        57,
                        24
                )
        );
        btnExit.addHoverImage(
                btnExit.createPointerButton(
                        "",
                        imgHover,
                        Assets.fontf.getFont("Pixel Game", 48),
                        btnExit.getWidth() + 100,
                        btnExit.getHeight(),
                        57,
                        24
                )
        );

        btnNewGame.addPressingImage(
                btnNewGame.createButton(
                        "NEW GAME",
                        imgPressing,
                        Assets.fontf.getFont("Pixel Game", 48),
                        btnNewGame.getWidth(),
                        btnNewGame.getHeight(),
                        57,
                        16
                )
        );
        btnResume.addPressingImage(
                btnResume.createButton(
                        "RESUME",
                        imgPressing,
                        Assets.fontf.getFont("Pixel Game", 48),
                        btnResume.getWidth(),
                        btnResume.getHeight(),
                        57,
                        16
                )
        );
        btnReplay.addPressingImage(
                btnReplay.createButton(
                        "REPLAY",
                        imgPressing,
                        Assets.fontf.getFont("Pixel Game", 48),
                        btnReplay.getWidth(),
                        btnReplay.getHeight(),
                        57,
                        16
                )
        );
        btnSettings.addPressingImage(
                btnSettings.createButton(
                        "SETTINGS",
                        imgPressing,
                        Assets.fontf.getFont("Pixel Game", 48),
                        btnSettings.getWidth(),
                        btnSettings.getHeight(),
                        57,
                        16
                )
        );
        btnExit.addPressingImage(
                btnExit.createButton(
                        "EXIT",
                        imgPressing,
                        Assets.fontf.getFont("Pixel Game", 48),
                        btnExit.getWidth(),
                        btnExit.getHeight(),
                        32,
                        16
                )
        );

        btnNewGame.addEvent(e -> {
            GameSceneManager.add(GameSceneManager.PLAY);
            GameSceneManager.pop(GameSceneManager.HUB);
        });
        btnResume.addEvent(e -> {
            GameSceneManager.pop(GameSceneManager.PAUSE);
        });
        btnReplay.addEvent(e -> {
            GameSceneManager.pop(GameSceneManager.PLAY);
            GameSceneManager.add(GameSceneManager.PLAY);
            GameSceneManager.pop(GameSceneManager.GAMEOVER);
        });
        btnSettings.addEvent(e -> {

        });
        btnExit.addEvent(e -> {
            System.exit(0);
        });

        hubUI.put(0, btnNewGame);
        hubUI.put(1, btnSettings);
        hubUI.put(2, btnExit);

        pauseUI.put(0, btnResume);
        pauseUI.put(1, btnSettings);
        pauseUI.put(2, btnExit);

        gameOverUI.put(0, btnReplay);
        gameOverUI.put(1, btnSettings);
        gameOverUI.put(2, btnExit);
    }

    public void render(Graphics2D g2d) {
        if (GameSceneManager.isStateActive(GameSceneManager.GAMEOVER)) {
            for (Map.Entry<Integer, Button> entry : gameOverUI.entrySet()) { entry.getValue().render(g2d); }
            SpriteSheet.drawArray(g2d, "GAME OVER", new Vector2f((float)GameSettings.GAME_WIDTH / 2 - "GAME OVER".length() * (48 / 2), (float)GameSettings.GAME_HEIGHT / 2 - (48 / 2) - 150), 64,  64, 48);
        } else if (GameSceneManager.isStateActive(GameSceneManager.HUB)) {
            for (Map.Entry<Integer, Button> entry : hubUI.entrySet()) { entry.getValue().render(g2d); }

            g2d.setFont(Assets.fontf.getFont("Pixel Game", 128));
            g2d.setColor(new Color(76, 0, 0));
            String logo = "TREASURE";
            String logoBelow = "HUNTING";
            g2d.drawString(logo, GameSettings.GAME_WIDTH / 2 - (logo.length() / 2)*55, 200);
            g2d.drawString(logoBelow, GameSettings.GAME_WIDTH / 2 - (logoBelow.length() / 2)*55 - 12, 300);
            g2d.setColor(new Color(255, 246, 246));
            g2d.drawString(logo, GameSettings.GAME_WIDTH / 2 - (logo.length() / 2)*53, 200 - 10);
            g2d.drawString(logoBelow, GameSettings.GAME_WIDTH / 2 - (logoBelow.length() / 2)*53 - 12, 300 - 10);
//        g2d.setColor(Color.WHITE);
//        g2d.drawLine(0, Preferences.GAME_HEIGHT/2, Preferences.GAME_WIDTH, Preferences.GAME_HEIGHT/2);
//        g2d.drawLine(Preferences.GAME_WIDTH/2, 0, Preferences.GAME_WIDTH/2, Preferences.GAME_HEIGHT);
        } else if (GameSceneManager.isStateActive(GameSceneManager.PLAY)) {
            g2d.setFont(Assets.fontf.getFont("Pixel Game", 32));
            g2d.setColor(Color.white);
            String fps = GamePanel.oldFrameCount + " FPS";
            g2d.drawString(fps, GameSettings.GAME_WIDTH - 3*32, 32);
            String tps = GamePanel.oldTickCount + " TPS";
            g2d.drawString(tps, GameSettings.GAME_WIDTH - 3*32, 64);
        }
        if (GameSceneManager.isStateActive(GameSceneManager.PAUSE)) {
            for (Map.Entry<Integer, Button> entry : pauseUI.entrySet()) { entry.getValue().render(g2d); }
        }
    }

}
