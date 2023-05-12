package io.github.moonslanding.tlm.weapons;

import io.github.moonslanding.tlm.engine.Game;
import io.github.moonslanding.tlm.engine.GameObject;
import io.github.moonslanding.tlm.engine.Sprite;
import io.github.moonslanding.tlm.engine.SpriteCache;

public abstract class Weapon {

    private final String name;
    private final Sprite sprite;

    private int damage;
    private int cooldown;

    public Weapon(String name, String spriteName, int damage, int cooldown) {
        this.name = name;
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

    public abstract void shoot(Game game, GameObject source);

}
