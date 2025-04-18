package com.treasurehunting.java.scene;

import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;
import com.treasurehunting.java.utils.ScoreSave;

import java.awt.*;

public class WinScene extends GameScene {

    public WinScene() {
        ScoreSave.gainScore(Math.max(0, 120 - (int) ((double) System.nanoTime() / 1000000000 - ScoreSave.playTime / 1000000000)));
    }

    @Override
    public void update(double time) {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

    }

    @Override
    public void render(Graphics2D g2d) {

    }

}
