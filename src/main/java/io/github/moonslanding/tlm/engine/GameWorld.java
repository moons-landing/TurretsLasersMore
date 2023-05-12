package io.github.moonslanding.tlm.engine;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;

public class GameWorld {

    private final int width;
    private final int height;
    private final ObjectArrayList<GameObject> objects = new ObjectArrayList<>();

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

    public void removeObject(GameObject object) {
        if (!objects.contains(object)) return;
        objects.remove(object);
    }

    public void clearWorld() {
        objects.clear();
    }

    public List<GameObject> getObjects() {
        return objects;
    }
}
