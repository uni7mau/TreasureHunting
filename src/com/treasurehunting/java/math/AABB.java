package com.treasurehunting.java.math;

import com.treasurehunting.java.entity.GameObject;

import java.util.List;

public class AABB {

    private String type;
    private Vector2f pos;
    private float xOffset;
    private float yOffset;
    private float w;
    private float h;
    private float r;

    // Rectangle AABB
    public AABB(Vector2f pos, int w, int h) {
        this.pos = pos;
        this.w = w;
        this.h = h;

        type = "Rectangle";
    }

    // Circle AABB
    public AABB(Vector2f pos, float r) {
        this.pos = pos;
        this.r = r;
        this.w = r / 2;
        this.h = r / 2;

        type = "Circle";
    }

    public Vector2f getPos() { return pos; }
    public float getXOffset() { return xOffset; }
    public float getYOffset() { return yOffset; }
    public int getWidth() { return (int) w; }
    public int getHeight() { return (int) h; }
    public float getRadius() { return r; }
    public String getType() { return type; }

    public void setBox(Vector2f pos, int w, int h) {
        this.pos = pos;
        this.w = w;
        this.h = h;
    }

    public void setCircle(Vector2f pos, int r) {
        this.pos = pos;
        this.r = r;
    }

    public void setXOffset(float f) { xOffset = f; }
    public void setYOffset(float f) { yOffset = f; }
    public void setWidth(float f) { w = f; }
    public void setHeight(float f) { h = f; }
    public void setRadius(float f) {
        pos.x = pos.x + r / 2 - f / 2;
        pos.y = pos.y + r / 2 - f / 2;
        w = f / 2;
        h = f / 2;
        r = f;
    }

    public boolean collides(AABB bBox) {
       if (type.equals("Rectangle") && bBox.getType().equals("Circle")) {
            return bBox.colCircleBox(this); // big brain time
       } else if (type.equals("Circle") && bBox.getType().equals("Circle")) {
           return colCircle(bBox);
       } else if (type.equals("Circle") && bBox.getType().equals("Rectangle")) {
           return colCircleBox(bBox);
       }
       return rectCollides(0, 0, bBox);
    }

    // rect * mrect
    public boolean checkGoThrough(float dx, float dy, List<GameObject> gos) {
        for (int i = 0; i < gos.size(); i++) {
            GameObject go = gos.get(i);
            if (go.isPhysicBody() && rectCollides(dx, dy, go.getBounds())) {
                return false;
            }
        }

        return true;
    }

    // rect x rect
    public boolean rectCollides(float dx, float dy, AABB bBox) {
        float ax = pos.x + xOffset + (this.w / 2) + dx;
        float ay = pos.y + yOffset + (this.h / 2) + dy;
        float bx = bBox.getPos().x + bBox.getXOffset() + (bBox.getWidth() / 2);
        float by = bBox.getPos().y + bBox.getYOffset() + (bBox.getHeight() / 2);

        if (Math.abs(ax - bx) < (this.w / 2) + (bBox.getWidth() / 2)) {
            if (Math.abs(ay - by) < (this.h / 2) + (bBox.getHeight() / 2)) {
                return true;
            }
        }

        return false;
    }

    // dot in rect
    public boolean inside(int xp, int yp) {
        if (xp == -1 || yp == - 1) return false;

        int wTemp = (int) this.w;
        int hTemp = (int) this.h;
        int x = (int) this.pos.x;
        int y = (int) this.pos.y;

        if (xp < x || yp < y) {
            return false;
        }

        wTemp += x;
        hTemp += y;
        return ((wTemp < x || wTemp > xp) && (hTemp < y || hTemp > yp));
    }

    // circle x circle
    public boolean colCircle(AABB circle) {
        float totalRadius = r + circle.getRadius();
        totalRadius *= totalRadius;

        float dx = (pos.x + circle.getPos().x);
        float dy = (pos.y + circle.getPos().y);

        return totalRadius < (dx * dx) + (dy * dy);
    }

    // circle x rect
    public boolean colCircleBox(AABB aBox) {
        float dx = Math.max(aBox.getPos().x + aBox.getXOffset(), Math.min(pos.x + (r / 2), aBox.getPos().x + aBox.getXOffset() + aBox.getWidth()));
        float dy = Math.max(aBox.getPos().y + aBox.getYOffset(), Math.min(pos.y + (r / 2), aBox.getPos().y + aBox.getYOffset() + aBox.getHeight()));

        dx = pos.x + (r/2) - dx;
        dy = pos.y + (r/2) - dy;

        if (Math.sqrt(dx * dx + dy * dy) < r/2) {
            return true;
        }

        return false;
    }

    public AABB merge(AABB other) {
        float minX = Math.min(pos.x, other.getPos().x);
        float minY = Math.min(pos.y, other.getPos().y);

        int maxW = (int) Math.max(w, other.getWidth());
        int maxH = (int) Math.max(h, other.getHeight());

        Vector2f pos = new Vector2f(minX, minY);
        return new AABB(pos, maxW, maxH);
    }

}
