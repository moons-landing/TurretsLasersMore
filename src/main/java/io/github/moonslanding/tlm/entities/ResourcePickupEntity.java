package io.github.moonslanding.tlm.entities;

import io.github.moonslanding.tlm.engine.GameObject;
import io.github.moonslanding.tlm.engine.WrappedGraphic;
import io.github.moonslanding.tlm.engine.interfaces.IRenderable;

import java.awt.*;

public class ResourcePickupEntity extends GameObject implements IRenderable {

    private int amount;
    private boolean alive;

    public ResourcePickupEntity(int amount) {
        super(-1, -1, 3, 3);
        this.amount = amount;
    }

    @Override
    public void render(WrappedGraphic graphics) {
    }

    @Override
    public void render(WrappedGraphic graphics, int x, int y) {
        if (!alive) return;
        Graphics g = graphics.getGraphic();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x - 1, y - 1, getWidth(), getHeight());
        g2d.dispose();
    }

    public int getResourceAmount() {
        return amount;
    }

    public void setResourceAmount(int amount) {
        this.amount = amount;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
