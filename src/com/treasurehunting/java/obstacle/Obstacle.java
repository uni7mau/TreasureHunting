package com.treasurehunting.java.obstacle;

import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;

import java.awt.*;

public abstract class Obstacle extends GameObject {

    protected boolean IDLE_STATE = true;
    protected boolean MOVE_STATE = false;
    protected boolean FALLEN_STATE = false;
    protected boolean ATTACKING_STATE = false;
    protected boolean DEFENCE_STATE = false;
    protected boolean DESTROY_STATE = false;
    protected boolean INVINCIBLE_STATE = false;
    protected boolean BEINGHIT_STATE = false;

    // Display things
    protected int dmgTaken = 0;
    protected boolean dmgDisplaying = false;
    protected double dmgGainTime;
    protected int dmgDisplayDuration = 1500;
    protected int invincibleDuration = 1000;
    protected double invincibleTime = 0;

    public Obstacle(SpriteSheet spriteSheet, Vector2f origin, int width, int height) {
        super(spriteSheet, origin, width, height);
    }

    public boolean getState(String state) {
        if (state.equals("IDLE")) { return IDLE_STATE; }
        if (state.equals("MOVE")) { return MOVE_STATE; }
        if (state.equals("FALLEN")) { return FALLEN_STATE; }
        if (state.equals("ATTACK")) { return ATTACKING_STATE; }
        if (state.equals("DEFENCE")) { return DEFENCE_STATE; }
        if (state.equals("DESTROY")) { return DESTROY_STATE; }
        if (state.equals("INVINCIBLE")) { return INVINCIBLE_STATE; }
        if (state.equals("HIT")) { return BEINGHIT_STATE; }
        return false;
    }

    public void setState(String state, boolean b) {
        if (state.equals("IDLE")) { IDLE_STATE = b; }
        if (state.equals("MOVE")) { MOVE_STATE = b; }
        if (state.equals("FALLEN")) { FALLEN_STATE = b; }
        if (state.equals("ATTACK")) { ATTACKING_STATE = b; }
        if (state.equals("DEFENCE")) { DEFENCE_STATE = b; }
        if (state.equals("DESTROY")) { DESTROY_STATE = b; }
        if (state.equals("INVINCIBLE")) { INVINCIBLE_STATE = b; }
        if (state.equals("HIT")) { BEINGHIT_STATE = b; }
    }

    public abstract void animate();

    public void update(double time) {
        animate();
        anim.update();

        if (dmgDisplaying) {
            if ((dmgGainTime / 1000000) + dmgDisplayDuration < (time / 1000000)) {
                dmgDisplaying = false;
                dmgTaken = 0;
            }
        }
    }

    public abstract void render(Graphics2D g2d);

}
