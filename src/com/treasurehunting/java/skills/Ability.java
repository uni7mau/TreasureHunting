package com.treasurehunting.java.skills;

public class Ability {

    private final Skill data;

    public Ability(Skill data) { this.data = data; }

    public AbilityCommand createCommand() { return new AbilityCommand(data); }

    public Skill getData() { return data; }

    public double getDuration() { return data != null ? data.getDuration() : 0; }

    public double getCooldown() { return data != null ? data.getCooldown() : 0; }

    public boolean getIsCanActive() { return data != null && data.isCanActive(); }

}
