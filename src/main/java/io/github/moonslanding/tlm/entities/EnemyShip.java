package io.github.moonslanding.tlm.entities;

import io.github.moonslanding.tlm.engine.SpritedGameObject;

public class EnemyShip extends SpritedGameObject {

    private int health;
    private int speed;

    public EnemyShip(int spawnX, int spawnY, String spriteName) {
        super(spawnX, spawnY, spriteName);
    }

}