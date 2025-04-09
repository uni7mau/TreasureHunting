package com.treasurehunting.java.graphics;

public class Animation {

    private Sprite[] frames;
    private final int[] frameLimit;
    private final int[] activeFrame;
    private int currFrame;
    private int numFrames;

    private int count;
    private int delay;

    private int timesPlayed = 0;

    public Animation() {
        frameLimit = new int[18];
        activeFrame = new int[18];
    }

    public boolean checkActiveFrame(int state) { return currFrame == activeFrame[state]; }

    public int getCurrFrame() { return currFrame; }

    public void setFrames(int state, Sprite[] frames) {
        if (currFrame > frames.length) currFrame = 0;
        this.frames = frames;
        count = 0;
        timesPlayed = 0;
        delay = 2;
        if (frameLimit[state] == 0) {
            numFrames = frames.length;
        } else {
            numFrames = frameLimit[state];
        }
    }

    public void setAbsoluteFrames(int state, Sprite[] frames) {
        currFrame = 0;
        this.frames = frames;
        count = 0;
        timesPlayed = 0;
        delay = 2;
        if (frameLimit[state] == 0) {
            numFrames = frames.length;
        } else {
            numFrames = frameLimit[state];
        }
    }

    public void setDelay(int i) { delay = i; }

    public void setNumFrames(int index, int state) { frameLimit[state] = index; }

    public void setActiveFrame(int index, int state) { activeFrame[state] = index; }

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
