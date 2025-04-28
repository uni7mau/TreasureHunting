package com.treasurehunting.java.skills;

import com.treasurehunting.java.ui.AbilityButton;
import com.treasurehunting.java.ui.AbilityBar;
import com.treasurehunting.java.utils.*;

import java.util.*;

public class AbilityController {

    private final AbilityModel model;
    private final AbilityBar view;
    private final Queue<AbilityCommand> abilityCommandQueue = new LinkedList<>();

    private double activeTime = 0;
    private int currDuration = 0;
    private boolean canActive = true;
    private boolean activating = false;

    private AbilityController(AbilityBar view, AbilityModel model) {
        this.view = view;
        this.model = model;

        connectView();
        connectModel();
    }

    private void connectView() {
        for (Map.Entry<Integer, AbilityButton> entry : view.getButtons().entrySet()) {
            entry.getValue().setOnButtonPressedListener( (int key) -> {
                if (canActive) {
                    if (model.getAbilities().get(key) != null && model.getAbilities().get(key).getIsCanActive()
                            && model.getAbilities().get(key).createCommand() != null) {
                        abilityCommandQueue.add(model.getAbilities().get(key).createCommand());
                        view.getButtons().get(key).activeTime = System.nanoTime();
                    }
                }
            });
        }
    }

    private void connectModel() {
        for (Map.Entry<Integer, AbilityButton> buttonEntry : view.getButtons().entrySet()) {
            if (model.getAbilities().containsKey(buttonEntry.getValue().keyCode)) {
                buttonEntry.getValue().cooldown = model.getAbilities().get(buttonEntry.getValue().keyCode).getCooldown();
            }
        }
    }

    public void updateCanActive(double time) {
        if ((activeTime / 1000000) + currDuration > (time / 1000000)) {
            canActive = false;
        } else {
            canActive = true;
        }
    }

    public void updateActivating(double time) {
        if ((activeTime / 1000000) < (time / 1000000) - currDuration) {
            activating = false;
        } else {
            activating = true;
        }
    }

    public void input(MouseHandler mouse, KeyHandler key) {
        for (Map.Entry<Integer, AbilityButton> entry : view.getButtons().entrySet()) {
            if (
                    mouse.getButton() == 1 && entry.getValue().getBounds().inside(mouse.getX(), mouse.getY()) ||
                    key.getKeys().get(entry.getValue().keyCode).down
            ) {
                entry.getValue().action();
            }
        }
    }

    public void update(double time) {
        updateCanActive(time);
        updateActivating(time);

        view.updateRadial(1 - (time / 1000000 - activeTime / 1000000) / currDuration);

        for (Map.Entry<Integer, AbilityButton> buttonEntry : view.getButtons().entrySet()) {
            if (model.getAbilities().containsKey(buttonEntry.getValue().keyCode)) {
                if (model.getAbilities().get(buttonEntry.getValue().keyCode).getIsCanActive()) { // Iscanactive: đang không trong thời gian cooldown
                    buttonEntry.getValue().resetCP(); //  progress về lại 100% và currcooldown về lại giá trị gốc
                }
                buttonEntry.getValue().updateCooldownTime(time);
            }
        }

        if (canActive && !abilityCommandQueue.isEmpty()) {
            AbilityCommand cmd = abilityCommandQueue.poll();
            cmd.execute();
            activeTime = time;
            currDuration = cmd.getDuration();
        }
        abilityCommandQueue.clear();
    }

    public static class Builder {

        private final AbilityModel model = new AbilityModel();

        public Builder withAbilities(HashMap<Integer, Skill> datas) {
            for (Map.Entry<Integer, Skill> entry : datas.entrySet()) {
                model.abilities.put(entry.getKey(), new Ability(entry.getValue()));
            }
            return this;
        }

        public AbilityController build(AbilityBar view) {
            return new AbilityController(view, model);
        }

    }

}
