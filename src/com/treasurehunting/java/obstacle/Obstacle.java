package com.treasurehunting.java.obstacle;

import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.entity.Player;
import com.treasurehunting.java.graphics.Assets;
import com.treasurehunting.java.graphics.Sprite;
import com.treasurehunting.java.graphics.SpriteSheet;
import com.treasurehunting.java.math.Vector2f;
import com.treasurehunting.java.scene.PlayScene;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Obstacle extends GameObject {

    protected boolean IDLE_STATE = true;
    protected boolean FALLEN_STATE = false;

    public Obstacle(Sprite sprite, Vector2f origin, int width, int height) {
        super(sprite, origin, width, height);
    }

    public Obstacle(SpriteSheet spriteSheet, Vector2f origin, int width, int height) {
        super(spriteSheet, origin, width, height);

        setAnimation(Assets.IDLE, spriteSheet.getSpriteRow(0), 20);
    }

    public boolean getState(String state) {
        if (state.equals("IDLE")) { return IDLE_STATE; }
        else if (state.equals("FALLEN")) { return FALLEN_STATE; }
        else return super.getState(state);
    }

    public void setState(String state, boolean b) {
        if (state.equals("IDLE")) { IDLE_STATE = b; }
        else if (state.equals("FALLEN")) { FALLEN_STATE = b; }
        else super.setState(state, b);
    }

    public abstract void activeEvent(GameObject go);

    public void update(double time) {
        super.update(time);

        List<GameObject> eList = PlayScene.quadTree.retrieve(new ArrayList<>(), this);
        for (int i = 0; i < eList.size(); i++) {
            GameObject go = eList.get(i);
            if (bounds.collides(go.getBounds())) {
                activeEvent(go);
            }
        }

//        for (Map.Entry<Integer, List<GameObject>> entry : PlayScene.gameObjects.entrySet()) {
//            for (int i = 0; i < entry.getValue().size(); i++) {
//                if (bounds.collides(entry.getValue().get(i).getBounds())) {
//                    activeEvent(entry.getValue().get(i));
//                }
//            }
//        }
    }

}
