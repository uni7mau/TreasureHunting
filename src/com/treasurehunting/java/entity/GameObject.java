package com.treasurehunting.java.entity;

import com.treasurehunting.java.GamePanel;
import com.treasurehunting.java.graphics.Animation;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.Sprite;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.GameSceneManager;
import com.treasurehunting.java.utils.TileCollision;

import java.awt.*;
import java.util.HashMap;

public abstract class GameObject {

    protected Sprite sprite;
    protected HashMap<Integer, SpriteSheet> spriteSheets;
    protected Animation anim;

    protected AABB bounds;
    protected AABB sense;
    protected Vector2f pos;
    protected int width;
    protected int height;

    protected boolean INVINCIBLE_STATE = false;
    protected boolean DIE_STATE = false;

    // Default/self stats
    protected String name = "";
    protected int health = 1;
    protected int maxHealth = 1;
    protected double healthPercent = 1;
    protected float res = 0f;

    // Controls
    protected boolean up = false;
    protected boolean down = false;
    protected boolean left = false;
    protected boolean right = false;

    protected float dx;
    protected float dy;
    protected float bonusDx = 0;
    protected float bonusDy = 0;

    // Display things
    protected int dmgTaken = 0;
    protected boolean dmgDisplaying = false;
    protected double dmgGainTime;
    protected int dmgDisplayDuration = 1500;
    protected int invincibleDuration = 500;
    protected double invincibleTime = 0;

    // Physical things
    protected boolean physicBody = false;
    protected boolean hasAnim = true;
    protected boolean hasInvincibleAnim = true;

    protected float maxSpeed = 5f;
    protected float acc = 0.5f;
    protected float deacc = 0.3f;
    protected float force = 15f;

    protected float maxSpeedBonus = 0;
    protected float accBonus = 0;
    protected float deaccBonus = 0;

    public boolean blockedX = false;
    public boolean blockedY = false;

    protected int currAnimation = Assets.IDLE;
    protected int currDirection = Assets.RIGHT;

    protected TileCollision tileCollision;

    public GameObject(Sprite sprite, Vector2f pos, int width, int height) {
        this.sprite = sprite;
        this.pos = pos;
        this.width = width;
        this.height = height;

        bounds = new AABB(new Vector2f(pos.x, pos.y), width, height);
        sense = new AABB(new Vector2f(pos.x + width / 2 - 500 / 2, pos.y + height / 2 - 500 / 2), 500);
        anim = new Animation();
        tileCollision = new TileCollision(this);
    }

    public GameObject(SpriteSheet startSpriteSheet, Vector2f pos, int width, int height) {
        spriteSheets = new HashMap<>();
        // IDLE anim first
        spriteSheets.put(0, startSpriteSheet);
        this.pos = pos;
        this.width = width;
        this.height = height;

        bounds = new AABB(new Vector2f(pos.x, pos.y), width, height);
        sense = new AABB(new Vector2f(pos.x + width / 2 - 500 / 2, pos.y + height / 2 - 500 / 2), 500);
        anim = new Animation();
        tileCollision = new TileCollision(this);
    }

    public boolean getState(String state) {
        if (state.equals("INVINCIBLE")) return INVINCIBLE_STATE;
        else if (state.equals("DIE")) return DIE_STATE;
        else return false;
    }
    public Sprite getSprite() { return sprite; }
    public SpriteSheet getSpriteSheet(int key) { return spriteSheets.get(key); }
    public Animation getAnimation() { return anim; }
    public int getCurrAnimation() { return currAnimation; }
    public int getCurrDirection() { return currDirection; }
    public AABB getSense() { return sense; }
    public AABB getBounds() { return bounds; }
    public Vector2f getPos() { return pos; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public float getDx() { return dx; }
    public float getDy() { return dy; }
    public String getName() { return name; }
    public TileCollision getTc() { return tileCollision; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public float getRes() { return res; }

    public boolean isPhysicBody() { return physicBody; }
    public float getDeacc() { return deacc; }
    public float getAcc() { return acc; }
    public float getMaxSpeed() { return maxSpeed; }
    public float getForce() { return force; }

    public int getDirection(GameObject go) {
        float distX = (go.getPos().x + go.getBounds().getXOffset() + (float) go.getBounds().getWidth() / 2) - (getPos().x + bounds.getXOffset() + (float) bounds.getWidth() / 2);
        float distY = (go.getPos().y + go.getBounds().getYOffset() + (float) go.getBounds().getHeight() / 2) - (getPos().y + bounds.getYOffset() + (float) bounds.getHeight() / 2);

        if (distX == 0 && distY == 0) return 8; // cùng vị trí

        double angle = Math.toDegrees(Math.atan2(distY, distX));

        if (angle < 0) angle += 360; // quy về [0, 360)

        if (angle >= 337.5 || angle < 22.5) return 6; // Right
        if (angle >= 22.5 && angle < 67.5) return 7;  // Right-Down
        if (angle >= 67.5 && angle < 112.5) return 0; // Down
        if (angle >= 112.5 && angle < 157.5) return 1; // Left-Down
        if (angle >= 157.5 && angle < 202.5) return 2; // Left
        if (angle >= 202.5 && angle < 247.5) return 3; // Left-Up
        if (angle >= 247.5 && angle < 292.5) return 4; // Up
        if (angle >= 292.5 && angle < 337.5) return 5; // Right-Up

        return 8;
    }


    public void setState(String state, boolean b) {
        if (state.equals("INVINCIBLE")) { INVINCIBLE_STATE = b; }
        else if (state.equals("DIE")) { DIE_STATE = b; }
    }
    public void setName(String name) { this.name = name; }
    public void setSprite(Sprite sprite) { this.sprite = sprite; }
    public void setAnimation(int state, Sprite[] frames, int delay) {
        currAnimation = state;
        anim.setFrames(state, frames, delay);
    }

    public void setAbsoluteAnimation(int state, Sprite[] frames, int delay) {
        if (currAnimation != state) {
            currAnimation = state;
            anim.setAbsoluteFrames(state, frames, delay);
        }
    }

    public void healthDec(int dmgTaken, float force, int direct) {
        if (!INVINCIBLE_STATE) {
            INVINCIBLE_STATE = true;
            health = Math.max(0, health - dmgTaken);
            this.dmgTaken = dmgTaken;
            dmgDisplaying = true;
            dmgGainTime = System.nanoTime();
            invincibleTime = System.nanoTime();

            addForce(force, direct);

            healthPercent = (double)health / maxHealth;
        }
    }

    public void setMaxSpeed(float f) { maxSpeed = f; }
    public void setAcc(float f) { acc = f; }
    public void setDeacc(float f) { deacc = f; }

    public void addSpriteSheet(int key, SpriteSheet spriteSheet) { spriteSheets.put(key, spriteSheet); }

    public void setPos(Vector2f pos) {
        this.pos.setVector(pos);
        this.bounds = new AABB(pos, width, height);
    }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }

    public void slowdown(float percent) { maxSpeedBonus = -maxSpeed*percent; }

    public void addForce(float force, int direct) {
        if (direct == Assets.DOWN) {  // down
            bonusDy = (float) force;
        } else if (direct == Assets.LEFTDOWN) { // leftdown
            bonusDx = -(float)( Math.cos(45)*force );
            bonusDy = (float)( Math.cos(45)*force );
        } else if (direct == Assets.LEFT) { // left
            bonusDx = -(float)force;
        } else if (direct == Assets.LEFTUP) { // leftup
            bonusDx = -(float)( Math.cos(45)*force );
            bonusDy = -(float)( Math.cos(45)*force );
        } else if (direct == Assets.UP) { // up
            bonusDy = -(float)force;
        } else if (direct == Assets.RIGHTUP) { // rightup
            bonusDx = (float)( Math.cos(45)*force );
            bonusDy = -(float)( Math.cos(45)*force );
        } else if (direct == Assets.RIGHT) { // right
            bonusDx = (float)force;
        } else if (direct == Assets.RIGHTDOWN) { // rightdown
            bonusDx = (float)( Math.cos(45)*force );
            bonusDy = (float)( Math.cos(45)*force );
        }
    }

    public abstract void animate();

    public void move() {
        if (up) {
            currDirection = Assets.UP;
            dy -= (acc + accBonus);
            if (dy < -(maxSpeed + maxSpeedBonus)) dy = -(maxSpeed + maxSpeedBonus);
        } else {
            if (dy < 0) {
                dy += (deacc + deaccBonus);
                if (dy > 0) dy = 0;
            }
        }

        if (down) {
            currDirection = Assets.DOWN;
            dy += (acc + accBonus);
            if (dy > (maxSpeed + maxSpeedBonus)) dy = (maxSpeed + maxSpeedBonus);
        } else {
            if (dy > 0) {
                dy -= (deacc + deaccBonus);
                if (dy < 0) dy = 0;
            }
        }

        if (left) {
            currDirection = Assets.LEFT;
            dx -= (acc + accBonus);
            if (dx < -(maxSpeed + maxSpeedBonus)) dx = -(maxSpeed + maxSpeedBonus);
        } else {
            if (dx < 0) {
                dx += (deacc + deaccBonus);
                if (dx > 0) dx = 0;
            }
        }

        if (right) {
            currDirection = Assets.RIGHT;
            dx += (acc + accBonus);
            if (dx > (maxSpeed + maxSpeedBonus)) dx = (maxSpeed + maxSpeedBonus);
        } else {
            if (dx > 0) {
                dx -= (deacc + deaccBonus);
                if (dx < 0) dx = 0;
            }
        }

        maxSpeedBonus = 0;
        accBonus = 0;
        deaccBonus = 0;

        if (right && up) {
            currDirection = Assets.RIGHTUP;
        } else if (right && down) {
            currDirection = Assets.RIGHTDOWN;
        } if (left && down) {
            currDirection = Assets.LEFTDOWN;
        } if (left && up) {
            currDirection = Assets.LEFTUP;
        }

        // Normalize speed nếu đi chéo
        float totalSpeed = (float) Math.sqrt(dx * dx + dy * dy);
        float max = maxSpeed + maxSpeedBonus;

        if (totalSpeed > max) {
            float scale = max / totalSpeed;
            dx *= scale;
            dy *= scale;
        }
    }

    protected void resetPosition() {
        pos.resetOri();
        bounds.getPos().resetOri();
        sense.getPos().resetOri();

        setAnimation(Assets.IDLE, spriteSheets.get(Assets.IDLE).getSpriteRow(Assets.RIGHT), 10);
    }

    public void update(double time) {
        if (!GameSceneManager.isSceneActive(GameSceneManager.WIN)) {
            if (hasAnim) {
                move();
                animate();
                anim.update();

                if (health == 0 && anim.hasPlayedOnce()) {
                    DIE_STATE = true;
                }

                if (!hasInvincibleAnim) {
                    if (INVINCIBLE_STATE && (GamePanel.tickCount == 0 || GamePanel.tickCount == 15 || GamePanel.tickCount == 30 || GamePanel.tickCount == 45 || GamePanel.tickCount == 60)) {
                        anim.getImage().setEffect(Sprite.effect.REDISH);
                    } else {
                        anim.getImage().setEffect(Sprite.effect.NORMAL);
                    }
                }
            } else {
                if (health == 0) {
                    DIE_STATE = true;
                }
            }

            if (dmgDisplaying) {
                if ((dmgGainTime / 1000000) + dmgDisplayDuration < (time / 1000000)) {
                    dmgDisplaying = false;
                    dmgTaken = 0;
                }
            }

            if (INVINCIBLE_STATE) {
                if ((invincibleTime / 1000000) + invincibleDuration < (time / 1000000)) {
                    INVINCIBLE_STATE = false;
                }
            }
        }
    }

    protected void drawName(Graphics2D g2d, String font, int size, String color) {
        // Upper name
        g2d.setColor(Color.decode(color));
        g2d.setFont(Assets.fontf.getFont(font, size));
        FontMetrics met = g2d.getFontMetrics(Assets.fontf.getFont(font, size));
        int w = met.stringWidth(name);
        g2d.drawString(
                name,
                (int) (pos.getWorldVar().x + bounds.getXOffset() + (float) bounds.getWidth() / 2 - (float) w / 2),
                (int) (pos.getWorldVar().y + bounds.getYOffset() - (float) anim.getImage().getHeight() / 2)
        );
    }

    public void render(Graphics2D g2d) {
//        // Img range
//        g2d.setColor(Color.yellow);
//        g2d.drawRect(
//                (int)pos.getWorldVar().x,
//                (int)pos.getWorldVar().y,
//                width,
//                height
//        );

        // Range information
        g2d.setColor(Color.green);
        g2d.drawRect(
                (int)( bounds.getPos().getWorldVar().x + bounds.getXOffset() ),
                (int)( bounds.getPos().getWorldVar().y + bounds.getYOffset() ),
                bounds.getWidth(),
                bounds.getHeight()
        );
        g2d.drawOval(
                (int)( sense.getPos().getWorldVar().x ),
                (int)( sense.getPos().getWorldVar().y ),
                (int)sense.getRadius(),
                (int)sense.getRadius()
        );

//      // World position
//        g2d.setColor(Color.WHITE);
//        g2d.drawLine(0, (int)pos.getWorldVar().y, GameSettings.GAME_WIDTH, (int)pos.getWorldVar().y);
//        g2d.drawLine((int)pos.getWorldVar().x, 0, (int)pos.getWorldVar().x, GameSettings.GAME_HEIGHT);

//        // Norm position
//        g2d.setColor(Color.cyan);
//        g2d.drawLine(0, (int)pos.y, Preferences.GAME_WIDTH, (int)pos.y);
//        g2d.drawLine((int)pos.x, 0, (int)pos.x, Preferences.GAME_HEIGHT);

        // Upper dmg taken
        if (dmgDisplaying && dmgTaken != 0) {
            g2d.setFont(Assets.fontf.getFont("GravityBold8", 8));
            g2d.setColor(Color.white);
            String dmgStr = String.valueOf(dmgTaken);
            g2d.drawString(
                    dmgStr,
                    (int) (pos.getWorldVar().x + bounds.getXOffset() - 30),
                    (int) (pos.getWorldVar().y + bounds.getYOffset() - 30)
            );
        } else {
            dmgTaken = 0;
        }
        if (hasAnim) {
            g2d.drawImage(anim.getImage().image, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, width, height, null);
        } else {
            g2d.drawImage(sprite.image, (int) pos.getWorldVar().x, (int) pos.getWorldVar().y, width, height, null);
        }
    }

}
