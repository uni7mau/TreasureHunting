package com.treasurehunting.java.skills;

import com.treasurehunting.java.ui.AbilityButton;
import com.treasurehunting.java.ui.AbilityView;
import com.treasurehunting.java.utils.*;

import java.util.*;

public class AbilityController {

    private final AbilityModel model;
    private final AbilityView view;
    private final Queue<AbilityCommand> abilityCommandQueue = new LinkedList<>(); // TODO: change 1 curr skill to execute
//    private AbilityCommand currAbilityCommand = new AbilityCommand(null);
    private final CountdownTimer timer = new CountdownTimer(0);

    private AbilityController(AbilityView view, AbilityModel model) {
        this.view = view;
        this.model = model;

        connectView();
        connectModel();
    }

    private void connectView() {
        for (Map.Entry<Integer, AbilityButton> entry : view.getButtons().entrySet()) {
            entry.getValue().setOnButtonPressedListener( (int key) -> {
                if (timer.getProgress() < 0.25f || !timer.isRunning()) {
                    if (model.getAbilities().get(key) != null &&
                            model.getAbilities().get(key).createCommand() != null
//                            model.getAbilities().get(key).getData().isCanActive()
                    ) {
                        abilityCommandQueue.add(model.getAbilities().get(key).createCommand());
                    }
                }
            });
        }
    }

    private void connectModel() {
    }

    public void input() { // TODO: put keyhandler, mousehandler here
        for (Map.Entry<Integer, AbilityButton> entry : view.getButtons().entrySet()) {
            if (
                    MouseHandler.getButton() == 1 && entry.getValue().getBounds().inside(MouseHandler.getX(), MouseHandler.getY()) ||
                    KeyHandler.keys.get(entry.getValue().keyCode).down
            ) {
                entry.getValue().action();
            }
        }
    }

    public void update(double deltaTime) {
        timer.tick(deltaTime);
        view.updateRadial(timer.getProgress());
        if (!timer.isRunning() && !abilityCommandQueue.isEmpty()) {
            AbilityCommand cmd = abilityCommandQueue.poll();
            cmd.execute();
            timer.reset(cmd.getDuration());
            timer.start();
        }
        abilityCommandQueue.clear();
    }

    public static class Builder {

        private final AbilityModel model = new AbilityModel();

        public Builder withAbilities(HashMap<Integer, Skill> datas) {
            for (Map.Entry<Integer, Skill> entry : datas.entrySet()) {
                model.abilities.add(entry.getKey(), new Ability(entry.getValue()));
            }
            return this;
        }

        public AbilityController build(AbilityView view) {
            return new AbilityController(view, model);
        }

    }

}

