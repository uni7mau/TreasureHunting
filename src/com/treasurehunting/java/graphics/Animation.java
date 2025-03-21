package com.treasurehunting.java.graphics;

public class Animation {

    private Sprite[] frames;
    private int[] states;
    private int currFrame;
    private int numFrames;

    private int count;
    private int delay;

    private int timesPlayed = 0;

    public Animation() {
        states = new int[32];
    }

    public int getCurrFrame() { return currFrame; }

    public void setFrames(int state, Sprite[] frames) {
        this.frames = frames;
        currFrame = 0;
        count = 0;
        timesPlayed = 0;
        delay = 2;
        if (states[state] == 0) {
            numFrames = frames.length;
        } else {
            numFrames = states[state];
        }
    }

    public void setDelay(int i) { delay = i; }

    public void setNumFrames(int i, int state) { states[state] = i; }

    public void update() {
        if (delay == -1) return;

        count++;
        if (count == delay) {
            currFrame++;
            count = 0;
        }

        if (currFrame == numFrames) {
            currFrame = 0;
            timesPlayed++;
        }
    }

    public int getDelay() { return delay; }

    public Sprite getImage() { return frames[currFrame]; }

    public boolean hasPlayedOnce() { return timesPlayed > 0; }

}
