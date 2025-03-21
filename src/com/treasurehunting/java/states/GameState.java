package com.treasurehunting.java.states;

import com.treasurehunting.java.utils.KeyHandler;
import com.treasurehunting.java.utils.MouseHandler;

import java.awt.*;

abstract class GameState {

    protected GameStateManager gsm;

    public GameState(GameStateManager gsm) { this.gsm = gsm; }

    public abstract void update(double time);
    public abstract void input(MouseHandler mouse, KeyHandler key);
    public abstract void render(Graphics2D g2d);

}
