package io.github.moonslanding.tlm.entities;

import io.github.moonslanding.tlm.engine.Game;
import io.github.moonslanding.tlm.engine.SpritedGameObject;
import io.github.moonslanding.tlm.managers.ProjectileManager;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerShip extends SpritedGameObject {

    private int level = 1;
    private int hullCount = 10;
    private int speed = 1;

    // Attributes for basic turret
    private int baseDamage = 5;
    private double fireDelay = 1.5;

    public PlayerShip(int spawnX, int spawnY) {
        super(spawnX, spawnY, "player_ship");
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHullCount() {
        return hullCount;
    }

    public void setHullCount(int hullCount) {
        this.hullCount = hullCount;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public double getFireDelay() {
        return fireDelay;
    }

    public void setFireDelay(double fireDelay) {
        this.fireDelay = fireDelay;
    }

    public void startFiring(Game game) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ProjectileEntity proj = ProjectileManager.getInstance().getProjectile(ProjectileEntity.ProjectileSide.PLAYER);
                proj.relocate(getX(), getY());
                proj.setFacing(getFacing());
                proj.setMaxAliveTime(60);
                proj.setVelocity(5);
                game.getWorld().addObject(proj);
                proj.setAlive(true);
            }
        }, 10L, (long) (1000 / getFireDelay()));
    }

}