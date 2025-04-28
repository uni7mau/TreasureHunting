package com.treasurehunting.java.utils;

import com.treasurehunting.java.gameobjects.entities.Entity;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.GameSceneManager;
import com.treasurehunting.java.scene.PlayScene;

import java.awt.*;

public class Camera {

    private AABB collisionCam;
    private Entity e;

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    private float dx;
    private float dy;
    private float maxSpeed = 8f;
    private float acc = 3f;
    private float deacc = 0.3f;

    public static int widthLimit;
    public static int heightLimit;

    public Camera(AABB collisionCam) { this.collisionCam = collisionCam; }

    public float getAcc() { return acc; }

    public float getDeacc() { return deacc; }

    public float getMaxSpeed() { return maxSpeed; }

    public Entity getTarget() { return e; }

    public Vector2f getPos() {
        return collisionCam.getPos();
    }

    public AABB getBounds() { return collisionCam; }

    public static void setLimit(int widthLimit, int heightLimit) {
        Camera.widthLimit = widthLimit;
        Camera.heightLimit = heightLimit;
    }

    public void update() {
        if (!GameSceneManager.isSceneActive(GameSceneManager.GAMEOVER) && !GameSceneManager.isSceneActive(GameSceneManager.WIN)) {
            move();
            if (!e.blockedX) {
                if (collisionCam.getPos().x + dx > 0
                        && collisionCam.getPos().getWorldVar().x + dx < Vector2f.getWorldVarX(widthLimit - collisionCam.getWidth())) {
                    PlayScene.map.x += dx;
                    collisionCam.getPos().setX(collisionCam.getPos().x + dx);
                }
            }
            if (!e.blockedY) {
                if (collisionCam.getPos().y + dy > 0
                        && collisionCam.getPos().getWorldVar().y + dy < Vector2f.getWorldVarY(heightLimit - collisionCam.getHeight())) {
                    PlayScene.map.y += dy;
                    collisionCam.getPos().setY(collisionCam.getPos().y + dy);
                }
            }
        }
    }

    private void move() {
        if (up) {
            dy -= acc;
            if (dy < -maxSpeed) {
                dy = -maxSpeed;
            }
        } else {
            if (dy < 0) {
                dy += deacc;
                if (dy > 0) {
                    dy = 0;
                }
            }
        }

        if (down) {
            dy += acc;
            if (dy > maxSpeed) {
                dy = maxSpeed;
            }
        } else {
            if (dy > 0) {
                dy -= deacc;
                if (dy < 0) {
                    dy = 0;
                }
            }
        }

        if (left) {
            dx -= acc;
            if (dx < -maxSpeed) {
                dx = -maxSpeed;
            }
        } else {
            if (dx < 0) {
                dx += deacc;
                if (dx > 0) {
                    dx = 0;
                }
            }
        }

        if (right) {
            dx += acc;
            if (dx > maxSpeed) {
                dx = maxSpeed;
            }
        } else {
            if (dx > 0) {
                dx -= deacc;
                if (dx < 0) {
                    dx = 0;
                }
            }
        }

        // Normalize speed nếu đi chéo
        float totalSpeed = (float) Math.sqrt(dx * dx + dy * dy);
        float max = maxSpeed;

        if (totalSpeed > max) {
            float scale = max / totalSpeed;
            dx *= scale;
            dy *= scale;
        }
    }

    public void target(Entity e) {
        this.e = e;
        acc = e.getAcc();
        deacc = e.getDeacc();
        maxSpeed = e.getMaxSpeed();
    }

    public void setMaxSpeed(float maxSpeed) { this.maxSpeed = maxSpeed; }

    public void input(MouseHandler mouse, KeyHandler key) {
        if (e == null) {
            if (key.getKeys().get(GameSettings.UP).down) {
                up = true;
            } else {
                up = false;
            }
            if (key.getKeys().get(GameSettings.DOWN).down) {
                down = true;
            } else {
                down = false;
            }
            if (key.getKeys().get(GameSettings.LEFT).down) {
                left = true;
            } else {
                left = false;
            }
            if (key.getKeys().get(GameSettings.RIGHT).down) {
                right = true;
            } else {
                right = false;
            }
        } else {
            int protectedSpace = 30;
            // Căn 1 khoảng a x b pixel tại tâm để không bị rung camera nhưng gây lệch khi vừa di chuyển xong
            if (!e.blockedY) {
                if (collisionCam.getPos().y + (float) collisionCam.getHeight() / 2 + dy > e.getPos().y + (float) e.getHeight() / 2 + e.getDy() + protectedSpace) {
                    up = true;
                    down = false;
                } else if (collisionCam.getPos().y + (float) collisionCam.getHeight() / 2 + dy < e.getPos().y + (float) e.getHeight() / 2 + e.getDy() - protectedSpace) {
                    down = true;
                    up = false;
                } else {
                    dy = 0;
                    up = false;
                    down = false;
                }
            }

            if (!e.blockedX) {
                if (collisionCam.getPos().x + (float) collisionCam.getWidth() / 2  + dx > e.getPos().x + (float) e.getWidth() / 2 + e.getDx() + protectedSpace) {
                    left = true;
                    right = false;
                } else if (collisionCam.getPos().x + (float) collisionCam.getWidth() / 2 + dx < e.getPos().x + (float) e.getWidth() / 2 + e.getDx() - protectedSpace) {
                    right = true;
                    left = false;
                } else {
                    dx = 0;
                    right = false;
                    left = false;
                }
            }
        }
    }

    public void render(Graphics2D g2d) {
        g2d.setColor(Color.blue);
        g2d.drawRect((int) collisionCam.getPos().getWorldVar().x, (int) collisionCam.getPos().getWorldVar().y, collisionCam.getWidth(), collisionCam.getHeight());
    }

}
