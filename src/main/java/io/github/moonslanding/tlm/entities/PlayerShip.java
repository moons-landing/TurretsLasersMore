package io.github.moonslanding.tlm.entities;

import io.github.moonslanding.tlm.engine.SpritedGameObject;

public class PlayerShip extends SpritedGameObject {

    private int hullCount = 10;
    private int speed = 1;

    // Attributes for basic turret
    private int baseDamage = 5;
    private int fireSpeed = 3;

    public PlayerShip(int spawnX, int spawnY) {
        super(spawnX, spawnY, "player_ship");
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

    public int getFireSpeed() {
        return fireSpeed;
    }

    public void setFireSpeed(int fireSpeed) {
        this.fireSpeed = fireSpeed;
    }

}