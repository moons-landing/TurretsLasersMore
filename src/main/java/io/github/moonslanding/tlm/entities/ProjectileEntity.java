package io.github.moonslanding.tlm.entities;

import io.github.moonslanding.tlm.engine.GameObject;
import io.github.moonslanding.tlm.engine.WrappedGraphic;
import io.github.moonslanding.tlm.engine.interfaces.IRenderable;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class ProjectileEntity extends GameObject implements IRenderable {

    public ProjectileSide getSide() {
        return side;
    }

    private ProjectileSide side;

    public ProjectileEntity(ProjectileSide side) {
        super(-1, -1, 3, 3);
        this.side = side;
    }

    private boolean alive;
    private int aliveTime;
    private int maxAliveTime;
    private int velocity;
    private int damage;

    @Override
    public void render(WrappedGraphic graphics) {}

    @Override
    public void render(WrappedGraphic graphics, int x, int y) {
        if (!alive) return;
        Graphics g = graphics.getGraphic();
        Graphics2D g2d = (Graphics2D) g.create();
        if (side == ProjectileSide.PLAYER) g2d.setColor(Color.CYAN);
        if (side == ProjectileSide.ENEMY) g2d.setColor(Color.ORANGE);
        g2d.fillRect(x - 1, y - 1, getWidth(), getHeight());
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

    public void incrementAliveTime() {
        aliveTime++;
    }

    public int getMaxAliveTime() {
        return maxAliveTime;
    }

    public void setMaxAliveTime(int maxAliveTime) {
        this.maxAliveTime = maxAliveTime;
        this.aliveTime = 0;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}
