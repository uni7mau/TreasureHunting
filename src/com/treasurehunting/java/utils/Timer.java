package com.treasurehunting.java.utils;

abstract class Timer {

    protected double initialTime;
    protected double time;
    private boolean isRunning;

    public Timer(double value) {
        this.initialTime = value;
        this.isRunning = false;
    }

    public double getProgress() { return time / initialTime; }

    public boolean isRunning() { return isRunning; }

    public void start() {
        time = initialTime;
        if (!isRunning) {
            isRunning = true;
        }
    }

    public void stop() {
        if (isRunning) {
            isRunning = false;
        }
    }

    public void resume() { isRunning = true; }

    public void pause() { isRunning = false; }

    public abstract void tick(double deltaTime);

}