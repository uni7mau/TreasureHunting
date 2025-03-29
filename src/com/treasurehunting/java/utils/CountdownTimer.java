package com.treasurehunting.java.utils;

// Countdown Timer
public class CountdownTimer extends Timer {

    public CountdownTimer(float value) { super(value); }

    @Override
    public void tick(double deltaTime) {
        if (isRunning() && time > 0) { time -= deltaTime; }

        if (isRunning() && time <= 0) { stop(); }
    }

    public boolean isFinished() { return time <= 0; }

    public void reset() { time = initialTime; }

    public void reset(int newTime) {
        initialTime = newTime;
        reset();
    }

}
