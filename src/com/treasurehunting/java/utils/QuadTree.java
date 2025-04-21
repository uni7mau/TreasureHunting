package com.treasurehunting.java.utils;

import com.treasurehunting.java.entity.GameObject;
import com.treasurehunting.java.math.AABB;
import com.treasurehunting.java.math.Vector2f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuadTree {

    private static final int MAX_OBJECTS = 1000;
    private static final int MAX_LEVELS  = 250;

    private int level;
    private List<GameObject> objects;
    private AABB bounds;
    private QuadTree[] nodes;

    public QuadTree(int level, AABB bounds) {
        this.level   = level;
        this.bounds  = bounds;
        this.objects = new ArrayList<>();
        this.nodes   = new QuadTree[4];
    }

    // Xoá sạch toàn bộ node và objects, dùng mỗi frame trước khi rebuild
    public void clear() {
        objects.clear();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    // Chia node thành 4 nhánh con
    private void split() {
        int subW = bounds.getWidth()  / 2;
        int subH = bounds.getHeight() / 2;
        float x = bounds.getPos().x;
        float y = bounds.getPos().y;

        nodes[0] = new QuadTree(level + 1, new AABB(new Vector2f(x + subW, y), subW, subH)); // top-right
        nodes[1] = new QuadTree(level + 1, new AABB(new Vector2f(x, y), subW, subH)); // top-left
        nodes[2] = new QuadTree(level + 1, new AABB(new Vector2f(x, y + subH), subW, subH)); // bottom-left
        nodes[3] = new QuadTree(level + 1, new AABB(new Vector2f(x + subW, y + subH), subW, subH)); // bottom-right
    }

    // Xác định node con chứa hoàn toàn bounds của GameObject. Trả về -1 nếu không vừa với bất kỳ quadrant nào.
    private int getIndex(GameObject go) {
        AABB box = go.getBounds();
        double verticalMid = bounds.getPos().x + bounds.getWidth()  / 2;
        double horizontalMid = bounds.getPos().y + bounds.getHeight() / 2;

        boolean top = (box.getPos().y + box.getHeight() < horizontalMid);
        boolean bottom = (box.getPos().y > horizontalMid);

        if (box.getPos().x + box.getWidth() < verticalMid) {
            if (top) return 1; // top-left
            if (bottom) return 2; // bottom-left
        } else if (box.getPos().x > verticalMid) {
            if (top) return 0; // top-right
            if (bottom) return 3; // bottom-right
        }

        return -1;
    }

    // Chèn một GameObject vào quad tree
    public void insert(GameObject go) {
        if (nodes[0] != null) {
            int index = getIndex(go);
            if (index != -1) {
                nodes[index].insert(go);
                return;
            }
        }

        objects.add(go);

        if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) split();

            Iterator<GameObject> it = objects.iterator();
            while (it.hasNext()) {
                GameObject o = it.next();
                int idx = getIndex(o);
                if (idx != -1) {
                    nodes[idx].insert(o);
                    it.remove();
                }
            }
        }
    }

    // Lấy danh sách các GameObject có thể va chạm với go (tức những object trong cùng node hoặc parent node)
    public List<GameObject> retrieve(List<GameObject> returnObjects, GameObject go) {
        int index = getIndex(go);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnObjects, go);
        }
        returnObjects.addAll(objects);

        return returnObjects;
    }

}
