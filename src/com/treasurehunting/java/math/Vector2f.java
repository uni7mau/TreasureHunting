package com.treasurehunting.java.math;

public class Vector2f {

    public float oriX, oriY;
    public float x, y;

    public static float worldX;
    public static float worldY;

    public Vector2f() {
        x = 0;
        y = 0;

        oriX = 0;
        oriY = 0;
    }

    public Vector2f(Vector2f pos) {
        x = pos.x;
        y = pos.y;

        oriX = x;
        oriY = y;
    }

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;

        oriX = x;
        oriY = y;
    }

    public static double dist(Vector2f start, Vector2f end) {
        Vector2f posA = new Vector2f(start);
        Vector2f posB = new Vector2f(end);
        return Math.sqrt(Math.pow(posB.x - posA.x, 2) + Math.pow(posB.y - posA.y, 2));
    }

    public void addX(float i) { x += i; }

    public void addY(float i) { y += i; }

    public void flag() { oriX = x; oriY = y; }

    public void resetOri() { x = oriX; y = oriY; }

    public void setOri(float x, float y) { this.oriX = x; this.oriY = y;  }

    public void setX(float i) { x = i; }

    public void setY(float i) { y = i; }

    public void setAbsoluteVector(float x, float y) {
        this.x = x;
        this.y = y;

        worldX = x;
        worldY = y;
    }

    public void setVector(Vector2f vect) {
        this.x = vect.x;
        this.y = vect.y;
    }

    public void setVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static void setWorldVar(float x, float y) {
        worldX = x;
        worldY = y;
    }

    public static float getWorldVarX(float x) {
        return x - worldX;
    }

    public static float getWorldVarY(float y) {
        return y - worldY;
    }

    public Vector2f getWorldVar() {
        return new Vector2f(x - worldX, y - worldY);
    }

    public Vector2f getCamVar() {
        return new Vector2f(x + worldX, y + worldY);
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

}
