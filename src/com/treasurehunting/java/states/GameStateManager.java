package com.treasurehunting.java.states;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.Fontf;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.utils.Camera;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.graphics.Font;
import com.treasurehunting.java.utils.Preferences;

import java.awt.*;

public class GameStateManager {

    private static GameState[] states = new GameState[5];

    public static final int PLAY = 0;
    public static final int MENU = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;
    public static final int HUB = 4;

    public static Font font;
    public static Fontf fontf;
    public static SpriteSheet ui;
    public static SpriteSheet button;
    public static Camera cam;

    public static Graphics2D g2d;

    public GameStateManager(Graphics2D g2d) {
        GameStateManager.g2d = g2d;

        //Should have better ways to do this?
        font = new Font(Assets.font, 10, 10);
        SpriteSheet.currentFont = font;

        fontf = new Fontf();
        fontf.loadFont(Assets.pixelCandyFont, "Pixel Game", 128);
        fontf.addSize("Pixel Game", java.awt.Font.PLAIN, 140);
        fontf.loadFont(Assets.meatMadnessFont, "MeatMadness", 32);
        fontf.loadFont(Assets.gravityBoldFont, "GravityBold8", 8);

        ui = new SpriteSheet(Assets.uiSS, 64, 64);
        button = new SpriteSheet(Assets.buttonSS, 704, 2160);

        add(HUB);
    }

    public static boolean isStateActive(int state) {
        return states[state] != null;
    }

    public void add(int state) {
        if (states[state] != null) { return; }

        if (state == PLAY) {
            cam = new Camera(
                    new AABB(
                            new Vector2f( 0, 8 * Preferences.BLOCK_PIXEL - (int)(Preferences.GAME_HEIGHT / 2) ),
                            Preferences.GAME_WIDTH,
                            Preferences.GAME_HEIGHT
                    )
            );
            states[PLAY] = new PlayState(this);
        } else if (state == MENU) {
            states[MENU] = new MenuState(this);
        } else if (state == PAUSE) {
            states[PAUSE] = new PauseState(this);
        } else if (state == GAMEOVER) {
            states[GAMEOVER] = new GameOverState(this);
        } else if (state == HUB) {
            states[HUB] = new HUBState(this);
        }
    }

    public void pop(int state) {
        states[state] = null;
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].input(mouse, key);
            }
        }
    }

    public void update(double time) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].update(time);
            }
        }
    }

    public void render(Graphics2D g2d) {
        for (int i = 0; i < states.length; i++) {
            if (states[i] != null) {
                states[i].render(g2d);
            }
        }
    }

}