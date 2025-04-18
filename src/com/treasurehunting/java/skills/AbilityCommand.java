package com.treasurehunting.java.skills;

import com.treasurehunting.java.utils.ICommand;

public class AbilityCommand implements ICommand {

    private final Skill data;

    public AbilityCommand(Skill data) { this.data = data; }

    public int getDuration() { return data.getDuration(); }

    public int getCooldown() {
        return data.getCooldown();
    }

    @Override
    public void execute() {
        if (data != null) {
            data.gainSignal();
        }
    }

}
