package io.github.moonslanding.tlm.weapons;

import io.github.moonslanding.tlm.engine.GameObject;
import io.github.moonslanding.tlm.engine.GameWorld;
import io.github.moonslanding.tlm.engine.Sprite;
import io.github.moonslanding.tlm.engine.SpriteCache;

public abstract class Weapon {

    private final String name;
    private final String spriteName;
    private final Sprite sprite;

    private int damage;
    private int cooldown;

    public Weapon(String name, String spriteName, int damage, int cooldown) {
        this.name = name;
        this.spriteName = spriteName;
        sprite = SpriteCache.loadSprite(spriteName);
        this.damage = damage;
        this.cooldown = cooldown;
    }

    public String getName() {
        return name;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public String getSpriteName() {
        return spriteName;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public abstract void shoot(GameWorld world, GameObject source);

}
