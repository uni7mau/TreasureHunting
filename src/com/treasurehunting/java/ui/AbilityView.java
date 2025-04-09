package com.treasurehunting.java.ui;

import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.GameSettings;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class AbilityView {

    private HashMap<Integer, AbilityButton> buttons = new HashMap<>();

    public AbilityView() {
        buttons.put(GameSettings.SKILL1, new AbilityButton(
                Assets.playerSkillIcon.getSubimage(43, 0, 43, 44),
                new Vector2f(GameSettings.GAME_WIDTH / 2 - 25 - 10 - 50 - 10 - 50, GameSettings.GAME_HEIGHT - 100),
                50,
                50,
                GameSettings.SKILL1
        ));
        buttons.put(GameSettings.SKILL2, new AbilityButton(
                Assets.playerSkillIcon.getSubimage(129, 0, 43, 44),
                new Vector2f(GameSettings.GAME_WIDTH / 2 - 25 - 10 - 50, GameSettings.GAME_HEIGHT - 100),
                50,
                50,
                GameSettings.SKILL2
        ));
        buttons.put(GameSettings.SKILL3, new AbilityButton(
                Assets.playerSkillIcon.getSubimage(172, 0, 43, 44),
                new Vector2f(GameSettings.GAME_WIDTH / 2 - 25, GameSettings.GAME_HEIGHT - 100),
                50,
                50,
                GameSettings.SKILL3
        ));
        buttons.put(GameSettings.SKILL4, new AbilityButton(
                Assets.playerSkillIcon.getSubimage(0, 44, 43, 44),
                new Vector2f(GameSettings.GAME_WIDTH / 2 + 25 + 10, GameSettings.GAME_HEIGHT - 100),
                50,
                50,
                GameSettings.SKILL4
        ));
        buttons.put(GameSettings.SKILL5, new AbilityButton(
                Assets.playerSkillIcon.getSubimage(0, 0, 43, 44),
                new Vector2f(GameSettings.GAME_WIDTH / 2 + 25 + 10 + 50 + 10, GameSettings.GAME_HEIGHT - 100),
                50,
                50,
                GameSettings.SKILL5
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
