package com.treasurehunting.java.scene;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.Sprite;
import com.treasurehunting.java.utils.GameSettings;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;

import java.awt.*;

public class HUBScene extends GameScene {

    public static Sprite backgroundImg = Assets.backGroundSS.getSprite(0, 0);

    @Override
    public void update(double time) {

    }

    @Override
    public void input(MouseHandler mouse, KeyHandler key) {

    }

    @Override
    public void render(Graphics2D g2d) {
        g2d.drawImage(backgroundImg.image, 0, 0, GameSettings.GAME_WIDTH, GameSettings.GAME_HEIGHT, null);
    }

}
