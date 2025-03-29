package com.treasurehunting.java.skills;

public class AbilityCommand implements ICommand {

    private final Skill data;

    public AbilityCommand(Skill data) { this.data = data; }

    public int getDuration() { return data.getDuration(); }

    public int getCooldown() {
        return data.getCooldown();
    }

    public Boolean checkNull() { return data == null; }

    @Override
    public void execute() { if (data != null) data.gainSignal(); }

}
