package com.treasurehunting.java.utils;

// Stopwatch Timer
public class StopwatchTimer extends Timer {

    public StopwatchTimer() { super(0); }

    @Override
    public void tick(double deltaTime) {
        if (isRunning()) {
            time += deltaTime;
        }
    }

    public void reset() { time = 0; }

    public double getTime() { return time; }

}
