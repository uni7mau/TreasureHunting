package com.treasurehunting.java.ui;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.Preferences;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AbilityView {

    private HashMap<Integer, AbilityButton> buttons = new HashMap<>();

    public AbilityView() {
        buttons.put(KeyHandler.SKILL1, new AbilityButton(
                Assets.playerSkillIcon.getSubimage(0, 0, 20, 20),
                new Vector2f(Preferences.GAME_WIDTH / 2 - 25 - 10 - 50 - 10 - 50, Preferences.GAME_HEIGHT - 100),
                50,
                50,
                KeyHandler.SKILL1
        ));
        buttons.put(KeyHandler.SKILL2, new AbilityButton(
                Assets.playerSkillIcon.getSubimage(20, 0, 20, 20),
                new Vector2f(Preferences.GAME_WIDTH / 2 - 25 - 10 - 50, Preferences.GAME_HEIGHT - 100),
                50,
                50,
                KeyHandler.SKILL2
        ));
        buttons.put(KeyHandler.SKILL3, new AbilityButton(
                Assets.playerSkillIcon.getSubimage(40, 0, 20, 20),
                new Vector2f(Preferences.GAME_WIDTH / 2 - 25, Preferences.GAME_HEIGHT - 100),
                50,
                50,
                KeyHandler.SKILL3
        ));
        buttons.put(KeyHandler.SKILL4, new AbilityButton(
                Assets.playerSkillIcon.getSubimage(60, 0, 20, 20),
                new Vector2f(Preferences.GAME_WIDTH / 2 + 25 + 10, Preferences.GAME_HEIGHT - 100),
                50,
                50,
                KeyHandler.SKILL4
        ));
        buttons.put(KeyHandler.SKILL5, new AbilityButton(
                Assets.playerSkillIcon.getSubimage(80, 0, 20, 20),
                new Vector2f(Preferences.GAME_WIDTH / 2 + 25 + 10 + 50 + 10, Preferences.GAME_HEIGHT - 100),
                50,
                50,
                KeyHandler.SKILL5
        ));
    }

    public HashMap<Integer, AbilityButton> getButtons() { return buttons; }

    public void updateRadial(double progress) {
        for (Map.Entry<Integer, AbilityButton> item : buttons.entrySet()) {
            item.getValue().updateRadialFill(progress);
        }
    }

    public void render(Graphics2D g2d) {
        for (Map.Entry<Integer, AbilityButton> item : buttons.entrySet()) {
            item.getValue().render(g2d);
        }
    }

}
