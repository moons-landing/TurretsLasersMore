package io.github.moonslanding.tlm.engine;

import java.util.ArrayList;
import java.util.List;

public class GameWorld {

    private int width, height;
    private List<GameObject> objects = new ArrayList<>();

    public GameWorld(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addObject(GameObject object) {
        if (objects.contains(object)) return;
        objects.add(object);
    }

    public List<GameObject> getObjects() {
        return objects;
    }
}
