package com.treasurehunting.java.skills;

public class Ability {

    private final Skill data;

    public Ability(Skill data) {
        this.data = data;
    }

    public AbilityCommand createCommand() { return new AbilityCommand(data); }

    public Skill getData() {
        return data;
    }

}
