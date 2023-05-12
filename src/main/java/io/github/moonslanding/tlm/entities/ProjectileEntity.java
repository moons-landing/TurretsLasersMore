package io.github.moonslanding.tlm.entities;

import io.github.moonslanding.tlm.engine.GameObject;
import io.github.moonslanding.tlm.engine.WrappedGraphic;
import io.github.moonslanding.tlm.engine.interfaces.IRenderable;

import java.awt.*;

public class ProjectileEntity extends GameObject implements IRenderable {

    private ProjectileSide side;

    public ProjectileEntity(ProjectileSide side) {
        super(-1, -1, 1, 1);
        this.side = side;
    }

    private boolean alive;
    private int aliveTime;
    private int velocity;

    @Override
    public void render(WrappedGraphic graphics) {}

    @Override
    public void render(WrappedGraphic graphics, int x, int y) {
        if (!alive) return;
        Graphics g = graphics.getGraphic();
        Graphics2D g2d = (Graphics2D) g.create();
        if (side == ProjectileSide.PLAYER) g2d.setColor(Color.CYAN);
        if (side == ProjectileSide.ENEMY) g2d.setColor(Color.YELLOW);
        g2d.fillRect(x, y, 1, 1);
    }

    public enum ProjectileSide {
        PLAYER, ENEMY
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getAliveTime() {
        return aliveTime;
    }

    public void setAliveTime(int aliveTime) {
        this.aliveTime = aliveTime;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}
