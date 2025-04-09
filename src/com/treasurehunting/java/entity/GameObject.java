package com.treasurehunting.java.entity;

import com.treasurehunting.java.graphics.Animation;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.Sprite;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.utils.GameSettings;

import java.awt.*;
import java.util.HashMap;

public abstract class GameObject {

    protected Sprite sprite;
    protected HashMap<Integer, SpriteSheet> spriteSheets;
    protected Animation anim;

    protected AABB bounds;
    protected AABB sense;
    protected AABB hitBox;
    protected Vector2f pos;
    protected int width;
    protected int height;
    protected int scale;

    protected boolean INVINCIBLE_STATE = false;
    protected boolean DIE_STATE = false;

    // Default/self stats
    protected String name = "";
    protected int health = 100;

    protected float dx;
    protected float dy;
    protected float bonusDx = 0;
    protected float bonusDy = 0;

    // Display things
    protected int dmgTaken = 0;
    protected boolean dmgDisplaying = false;
    protected double dmgGainTime;
    protected int dmgDisplayDuration = 1500;
    protected int invincibleDuration = 300;
    protected double invincibleTime = 0;

    // Physical things
    protected float maxSpeed = 5f;
    protected float acc = 0.5f;
    protected float deacc = 0.3f;
    protected float force = 15f;

    public boolean blockedX = false;
    public boolean blockedY = false;

    protected int currAnimation = Assets.IDLE;
    protected int currDirection = Assets.RIGHT;

    public GameObject(Sprite sprite, Vector2f pos, int width, int height) {
        this.sprite = sprite;
        this.pos = pos;
        this.width = width;
        this.height = height;
        scale = Math.max(width, height) / GameSettings.TILE_SIZE;

        bounds = new AABB(new Vector2f(pos.x, pos.y), width, height);
        sense = new AABB(new Vector2f(pos.x + width / 2 - 500 / 2, pos.y + height / 2 - 500 / 2), 500);
        hitBox = new AABB(new Vector2f(pos.x, pos.y), 600);
        hitBox.setRadius((float) (Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / 2));
        anim = new Animation();
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
        hitBox = new AABB(new Vector2f(pos.x, pos.y), 600);
        hitBox.setRadius((float) (Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) / 2));
        anim = new Animation();
    }

    public boolean getState(String state) {
        if (state.equals("INVINCIBLE")) return INVINCIBLE_STATE;
        if (state.equals("DIE")) return DIE_STATE;
        return false;
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

    public void setState(String state, boolean b) {
        if (state.equals("INVINCIBLE")) { INVINCIBLE_STATE = b; }
        if (state.equals("DIE")) { DIE_STATE = b; }
    }
    public void setName(String name) { this.name = name; }
    public void setSprite(Sprite sprite) { this.sprite = sprite; }
    public void setAnimation(int state, Sprite[] frames, int delay) {
        currAnimation = state;
        anim.setFrames(state, frames);
        anim.setDelay(delay);
    }
    public void setAbsoluteAnimation(int state, Sprite[] frames, int delay) {
        currAnimation = state;
        anim.setAbsoluteFrames(state, frames);
        anim.setDelay(delay);
    }
    public void addSpriteSheet(int key, SpriteSheet spriteSheet) { spriteSheets.put(key, spriteSheet); }

    public void setPos(Vector2f pos) {
        this.pos.setVector(pos);
        this.bounds = new AABB(pos, width, height);
    }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }

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

    public void update(double time) {
        if (health == 0 && anim.getCurrFrame() == spriteSheets.get(Assets.DIE).getSpriteRow(currDirection).length - 1) {
            DIE_STATE = true;
        }
        if (INVINCIBLE_STATE) {
            if ((invincibleTime / 1000000) + invincibleDuration < (time / 1000000)) {
                INVINCIBLE_STATE = false;
            }
        }
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

        // Upper name
        // TODO: Tách ra cho các enemy hiện riêng
        g2d.setFont(Assets.fontf.getFont("GravityBold8", 8));
        g2d.setColor(Color.CYAN);
        g2d.drawString(
                name,
                (int)( pos.getWorldVar().x + (float)width / 2 - (float)((name.length()/2)*8) + 3 ),
                (int)( pos.getWorldVar().y + bounds.getYOffset() + bounds.getHeight() - GameSettings.TILE_SIZE - 4)
        );

        // Upper dmg taken
        if (dmgDisplaying && dmgTaken != 0) {
            g2d.setFont(Assets.fontf.getFont("GravityBold8", 8));
            g2d.setColor(Color.RED);
            String dmgStr = String.valueOf(dmgTaken);
            g2d.drawString(
                    dmgStr,
                    (int)( (pos.getWorldVar().x + bounds.getXOffset()) - (float)(((dmgStr.length()-1)/2)*4) ),
                    (int)( pos.getWorldVar().y - (float)(height/2) + 30 )
            );
            dmgTaken = 0;
        }

//      // World position
//        g2d.setColor(Color.WHITE);
//        g2d.drawLine(0, (int)pos.getWorldVar().y, GameSettings.GAME_WIDTH, (int)pos.getWorldVar().y);
//        g2d.drawLine((int)pos.getWorldVar().x, 0, (int)pos.getWorldVar().x, GameSettings.GAME_HEIGHT);

//        // Norm position
//        g2d.setColor(Color.cyan);
//        g2d.drawLine(0, (int)pos.y, Preferences.GAME_WIDTH, (int)pos.y);
//        g2d.drawLine((int)pos.x, 0, (int)pos.x, Preferences.GAME_HEIGHT);

        g2d.drawImage(anim.getImage().image, (int)pos.getWorldVar().x, (int)pos.getWorldVar().y, width, height, null);
    }

}
