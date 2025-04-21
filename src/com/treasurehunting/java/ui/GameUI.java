package com.treasurehunting.java.ui;

import com.treasurehunting.java.GamePanel;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.Sprite;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.GameSceneManager;
import com.treasurehunting.java.utils.GameSettings;
import com.treasurehunting.java.utils.ScoreSave;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameUI {

    public Map<Integer, Button> hubUI = new HashMap<>();

    public Sprite pauseBoardBg;
    public Map<Integer, Button> pauseUI = new HashMap<>();

    public Map<Integer, Button> gameOverUI = new HashMap<>();

    public Map<Integer, Button> winUI = new HashMap<>();

    public int w;
    public int h;

    public int xOffset;
    public int yOffset;

    public GameUI() {
        pauseBoardBg = Assets.pauseBoardSS.getSprite(0, 0);
        pauseBoardBg.setResizeImage(pauseBoardBg.getWidth() * 2, pauseBoardBg.getHeight() * 2);
        w = pauseBoardBg.getWidth();
        h = pauseBoardBg.getHeight();

        xOffset = 25;
        yOffset = GameSettings.GAME_HEIGHT / 2 - h / 2 + 50;

        Sprite imgButton = Assets.buttonSS.getSpriteImage(35, 1621, 57, 22);
        Sprite imgHover = Assets.buttonSS.getSpriteImage(57, 1496, 17, 31);
        Sprite imgPressing = Assets.buttonSS.getSpriteImage(163, 1621, 57, 22);

        Font applyFont = Assets.fontf.getFont("Pixel Game", 32);
        int btnWidth = 57; // gap stand on two sides of the btn
        int btnHeight = 20; // gap stand on top and bottom
        int btnHeightDown = 16; // ??
        int gapHeight = 40;

        // Btn height base on first btn
        Button btnNewGame = new Button(
                "NEW GAME",
                imgButton,
                applyFont,
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2),
                btnWidth,
                btnHeight
        );
        Button btnHUBSettings = new Button(
                "SETTINGS",
                imgButton,
                applyFont,
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2 + btnNewGame.getHeight() + gapHeight),
                btnWidth,
                btnHeight
        );
        Button btnHUBExit = new Button(
                "EXIT",
                imgButton,
                applyFont,
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2 + btnNewGame.getHeight() + gapHeight + btnHUBSettings.getHeight() + gapHeight),
                btnWidth,
                btnHeight
        );
        Button btnReplay = new Button(
                "REPLAY",
                imgButton,
                applyFont,
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2),
                btnWidth,
                btnHeight
        );
        Button btnGOSettings = new Button(
                "SETTINGS",
                imgButton,
                applyFont,
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2 + btnReplay.getHeight() + gapHeight),
                btnWidth,
                btnHeight
        );
        Button btnGOExit = new Button(
                "EXIT",
                imgButton,
                applyFont,
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2 + btnReplay.getHeight() + gapHeight + btnGOSettings.getHeight() + gapHeight),
                btnWidth,
                btnHeight
        );
        Button btnResume = new Button(
                "RESUME",
                imgButton,
                applyFont,
                new Vector2f((float) pauseBoardBg.getWidth() / 2 + xOffset, (float) pauseBoardBg.getHeight() / 2 - (float) btnNewGame.getHeight() / 2 + yOffset - gapHeight - btnNewGame.getHeight() + 100),
                btnWidth,
                btnHeight
        );
        Button btnSettings = new Button(
                "SETTINGS",
                imgButton,
                applyFont,
                new Vector2f((float) pauseBoardBg.getWidth() / 2 + xOffset, (float) pauseBoardBg.getHeight() / 2 - (float) btnNewGame.getHeight() / 2 + yOffset + 100),
                btnWidth,
                btnHeight
        );
        Button btnExit = new Button(
                "EXIT",
                imgButton,
                applyFont,
                new Vector2f((float) pauseBoardBg.getWidth() / 2 + xOffset, (float) pauseBoardBg.getHeight() / 2 + (float) btnNewGame.getHeight() / 2 + yOffset + gapHeight + 100),
                btnWidth,
                btnHeight
        );
        Button btnWNewGame = new Button(
                "NEW GAME",
                imgButton,
                applyFont,
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2),
                btnWidth,
                btnHeight
        );
        Button btnWSettings = new Button(
                "SETTINGS",
                imgButton,
                applyFont,
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2 + btnWNewGame.getHeight() + gapHeight),
                btnWidth,
                btnHeight
        );
        Button btnWExit = new Button(
                "EXIT",
                imgButton,
                applyFont,
                new Vector2f((float) GameSettings.GAME_WIDTH / 2, (float) GameSettings.GAME_HEIGHT / 2 + btnWNewGame.getHeight() + gapHeight + btnGOSettings.getHeight() + gapHeight),
                btnWidth,
                btnHeight
        );

        btnNewGame.addHoverImage(
                btnNewGame.createPointerButton(
                        "",
                        imgHover,
                        applyFont,
                        btnNewGame.getWidth() + 100,
                        btnNewGame.getHeight(),
                        btnWidth,
                        btnHeight
                )
        );
        btnHUBSettings.addHoverImage(
                btnHUBSettings.createPointerButton(
                        "",
                        imgHover,
                        applyFont,
                        btnHUBSettings.getWidth() + 100,
                        btnHUBSettings.getHeight(),
                        btnWidth,
                        btnHeight
                )
        );
        btnHUBExit.addHoverImage(
                btnHUBExit.createPointerButton(
                        "",
                        imgHover,
                        applyFont,
                        btnHUBExit.getWidth() + 100,
                        btnHUBExit.getHeight(),
                        btnWidth,
                        btnHeight
                )
        );
        btnResume.addHoverImage(
                btnResume.createPointerButton(
                        "",
                        imgHover,
                        applyFont,
                        btnResume.getWidth() + 100,
                        btnResume.getHeight(),
                        btnWidth,
                        btnHeight
                )
        );
        btnReplay.addHoverImage(
                btnReplay.createPointerButton(
                        "",
                        imgHover,
                        applyFont,
                        btnReplay.getWidth() + 100,
                        btnReplay.getHeight(),
                        btnWidth,
                        btnHeight
                )
        );
        btnSettings.addHoverImage(
                btnSettings.createPointerButton(
                        "",
                        imgHover,
                        applyFont,
                        btnSettings.getWidth() + 100,
                        btnSettings.getHeight(),
                        btnWidth,
                        btnHeight
                )
        );
        btnExit.addHoverImage(
                btnExit.createPointerButton(
                        "",
                        imgHover,
                        applyFont,
                        btnExit.getWidth() + 100,
                        btnExit.getHeight(),
                        btnWidth,
                        btnHeight
                )
        );
        btnGOSettings.addHoverImage(
                btnGOSettings.createPointerButton(
                        "",
                        imgHover,
                        applyFont,
                        btnGOSettings.getWidth() + 100,
                        btnGOSettings.getHeight(),
                        btnWidth,
                        btnHeight
                )
        );
        btnGOExit.addHoverImage(
                btnGOExit.createPointerButton(
                        "",
                        imgHover,
                        applyFont,
                        btnExit.getWidth() + 100,
                        btnExit.getHeight(),
                        btnWidth,
                        btnHeight
                )
        );
        btnWNewGame.addHoverImage(
                btnWNewGame.createPointerButton(
                        "",
                        imgHover,
                        applyFont,
                        btnWNewGame.getWidth() + 100,
                        btnWNewGame.getHeight(),
                        btnWidth,
                        btnHeight
                )
        );
        btnWSettings.addHoverImage(
                btnWSettings.createPointerButton(
                        "",
                        imgHover,
                        applyFont,
                        btnWSettings.getWidth() + 100,
                        btnWSettings.getHeight(),
                        btnWidth,
                        btnHeight
                )
        );
        btnWExit.addHoverImage(
                btnWExit.createPointerButton(
                        "",
                        imgHover,
                        applyFont,
                        btnWExit.getWidth() + 100,
                        btnWExit.getHeight(),
                        btnWidth,
                        btnHeight
                )
        );

        btnNewGame.addPressingImage(
                btnNewGame.createButton(
                        "NEW GAME",
                        imgPressing,
                        applyFont,
                        btnNewGame.getWidth(),
                        btnNewGame.getHeight(),
                        btnWidth,
                        btnHeightDown
                )
        );
        btnHUBSettings.addPressingImage(
                btnHUBSettings.createButton(
                        "SETTINGS",
                        imgPressing,
                        applyFont,
                        btnHUBSettings.getWidth(),
                        btnHUBSettings.getHeight(),
                        btnWidth,
                        btnHeightDown
                )
        );
        btnHUBExit.addPressingImage(
                btnHUBExit.createButton(
                        "EXIT",
                        imgPressing,
                        applyFont,
                        btnHUBExit.getWidth(),
                        btnHUBExit.getHeight(),
                        btnWidth,
                        btnHeightDown
                )
        );
        btnResume.addPressingImage(
                btnResume.createButton(
                        "RESUME",
                        imgPressing,
                        applyFont,
                        btnResume.getWidth(),
                        btnResume.getHeight(),
                        btnWidth,
                        btnHeightDown
                )
        );
        btnReplay.addPressingImage(
                btnReplay.createButton(
                        "REPLAY",
                        imgPressing,
                        applyFont,
                        btnReplay.getWidth(),
                        btnReplay.getHeight(),
                        btnWidth,
                        btnHeightDown
                )
        );
        btnSettings.addPressingImage(
                btnSettings.createButton(
                        "SETTINGS",
                        imgPressing,
                        applyFont,
                        btnSettings.getWidth(),
                        btnSettings.getHeight(),
                        btnWidth,
                        btnHeightDown
                )
        );
        btnExit.addPressingImage(
                btnExit.createButton(
                        "EXIT",
                        imgPressing,
                        applyFont,
                        btnExit.getWidth(),
                        btnExit.getHeight(),
                        btnWidth,
                        btnHeightDown
                )
        );
        btnGOSettings.addPressingImage(
                btnGOSettings.createButton(
                        "SETTINGS",
                        imgPressing,
                        applyFont,
                        btnGOSettings.getWidth(),
                        btnGOSettings.getHeight(),
                        btnWidth,
                        btnHeightDown
                )
        );
        btnGOExit.addPressingImage(
                btnGOExit.createButton(
                        "EXIT",
                        imgPressing,
                        applyFont,
                        btnGOExit.getWidth(),
                        btnGOExit.getHeight(),
                        btnWidth,
                        btnHeightDown
                )
        );
        btnWNewGame.addPressingImage(
                btnWNewGame.createButton(
                        "NEW GAME",
                        imgPressing,
                        applyFont,
                        btnWNewGame.getWidth(),
                        btnWNewGame.getHeight(),
                        btnWidth,
                        btnHeightDown
                )
        );
        btnWSettings.addPressingImage(
                btnWSettings.createButton(
                        "SETTINGS",
                        imgPressing,
                        applyFont,
                        btnWSettings.getWidth(),
                        btnWSettings.getHeight(),
                        btnWidth,
                        btnHeightDown
                )
        );
        btnWExit.addPressingImage(
                btnWExit.createButton(
                        "EXIT",
                        imgPressing,
                        applyFont,
                        btnWExit.getWidth(),
                        btnWExit.getHeight(),
                        btnWidth,
                        btnHeightDown
                )
        );

        btnNewGame.addEvent(e -> {
            GameSceneManager.pop(GameSceneManager.HUB);
            GameSceneManager.add(GameSceneManager.PLAY);
        });
        btnHUBSettings.addEvent(e -> {

        });
        btnHUBExit.addEvent(e -> {
            System.exit(0);
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
        btnGOSettings.addEvent( e -> {

        });
        btnGOExit.addEvent(e-> {
            System.exit(0);
        });
        btnWNewGame.addEvent(e -> {
            GameSceneManager.pop(GameSceneManager.WIN);
            GameSceneManager.pop(GameSceneManager.PLAY);
            Assets.returnFirstMap();
            GameSceneManager.add(GameSceneManager.PLAY);
        });
        btnWSettings.addEvent( e -> {

        });
        btnWExit.addEvent(e-> {
            System.exit(0);
        });


        hubUI.put(0, btnNewGame);
        hubUI.put(1, btnHUBSettings);
        hubUI.put(2, btnHUBExit);

        pauseUI.put(0, btnResume);
        pauseUI.put(1, btnSettings);
        pauseUI.put(2, btnExit);

        gameOverUI.put(0, btnReplay);
        gameOverUI.put(1, btnGOSettings);
        gameOverUI.put(2, btnGOExit);

        winUI.put(0, btnWNewGame);
        winUI.put(1, btnWSettings);
        winUI.put(2, btnWExit);
    }

    public void render(Graphics2D g2d) {
        if (GameSceneManager.isSceneActive(GameSceneManager.PLAY)) {
            g2d.setFont(Assets.fontf.getFont("Pixel Game", 32));
            g2d.setColor(Color.white);
            String fps = GamePanel.oldFrameCount + " FPS";
            g2d.drawString(fps, GameSettings.GAME_WIDTH - 3*32, 32);
            String tps = GamePanel.oldTickCount + " TPS";
            g2d.drawString(tps, GameSettings.GAME_WIDTH - 3*32, 64);
        }
        if (GameSceneManager.isSceneActive(GameSceneManager.WIN)) {
            String score = "SCORE: " + ScoreSave.getScore();
            String highestStr = "HIGHEST SCORE: " + ScoreSave.getHighScore();
            g2d.setColor(new Color(253, 253, 253));
            g2d.setFont(Assets.fontf.getFont("Pixel Game", 128));
            FontMetrics met = g2d.getFontMetrics(Assets.fontf.getFont("Pixel Game", 128));
            int w = met.stringWidth(highestStr);
            int h = met.getHeight();
            g2d.drawString(
                    highestStr,
                    (float) GameSettings.GAME_WIDTH / 2 - w / 2,
                    (float)GameSettings.GAME_HEIGHT / 2 - (48 / 2) - 150 + h - 10
            );
            w = met.stringWidth(score);
            g2d.drawString(
                    score,
                    (float) GameSettings.GAME_WIDTH / 2 - w / 2,
                    (float)GameSettings.GAME_HEIGHT / 2 - (48 / 2) - 150
            );
            for (Map.Entry<Integer, Button> entry : winUI.entrySet()) { entry.getValue().render(g2d); }
        } else if (GameSceneManager.isSceneActive(GameSceneManager.GAMEOVER)) {
            String lastWord = "GAME OVER";
            g2d.setColor(new Color(135, 0, 0));
            g2d.setFont(Assets.fontf.getFont("Pixel Game", 128));
            FontMetrics met = g2d.getFontMetrics(Assets.fontf.getFont("Pixel Game", 128));
            int w = met.stringWidth(lastWord);
            g2d.drawString(
                    lastWord,
                    (float) GameSettings.GAME_WIDTH / 2 - w / 2,
                    (float) GameSettings.GAME_HEIGHT / 2 - (48 / 2) - 150
            );
            for (Map.Entry<Integer, Button> entry : gameOverUI.entrySet()) { entry.getValue().render(g2d); }
        } else if (GameSceneManager.isSceneActive(GameSceneManager.HUB)) {
            String logo = "TREASURE";
            String logoBelow = "HUNTING";
            g2d.setColor(new Color(76, 0, 0));
            g2d.setFont(Assets.fontf.getFont("Pixel Game", 128));
            FontMetrics met = g2d.getFontMetrics(Assets.fontf.getFont("Pixel Game", 128));

            int w = met.stringWidth(logo);
            int h = 110;
            g2d.drawString(
                    logo,
                    GameSettings.GAME_WIDTH / 2 - w / 2,
                    200
            );
            g2d.drawString(
                    logoBelow,
                    GameSettings.GAME_WIDTH / 2 - w / 2 + 20,
                    200 + h
            );
            g2d.setColor(new Color(255, 246, 246));
            w = met.stringWidth(logoBelow);
            g2d.drawString(
                    logo,
                    GameSettings.GAME_WIDTH / 2 - w / 2 - 15,
                    200 - 10
            );
            g2d.drawString(
                    logoBelow,
                    GameSettings.GAME_WIDTH / 2 - w / 2 + 5,
                    200 + h - 10
            );

            for (Map.Entry<Integer, Button> entry : hubUI.entrySet()) { entry.getValue().render(g2d); }
        }
        if (GameSceneManager.isSceneActive(GameSceneManager.PAUSE) && !GameSceneManager.isSceneActive(GameSceneManager.GAMEOVER) && !GameSceneManager.isSceneActive(GameSceneManager.WIN)) {
            g2d.drawImage(pauseBoardBg.image, xOffset, yOffset, w, h, null);
            for (Map.Entry<Integer, Button> entry : pauseUI.entrySet()) { entry.getValue().render(g2d); }
        }
    }

}
