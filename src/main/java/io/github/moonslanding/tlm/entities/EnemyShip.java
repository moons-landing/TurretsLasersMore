package io.github.moonslanding.tlm.entities;

import io.github.moonslanding.tlm.engine.GameWorld;
import io.github.moonslanding.tlm.engine.SpritedGameObject;
import io.github.moonslanding.tlm.managers.ResourceEntityManager;
import io.github.moonslanding.tlm.weapons.Weapon;

import java.awt.*;
import java.util.Random;

public class EnemyShip extends SpritedGameObject {

    private final PlayerShip target;
    private int health = 10;
    private double speed = 1.5;
    private WeaponEntity weapon = null;

    public EnemyShip(int spawnX, int spawnY, String spriteName, PlayerShip target) {
        super(spawnX, spawnY, spriteName);
        setTint(Color.YELLOW);
        this.target = target;
        setWidth(getWidth() + 2);
        setHeight(getHeight() + 2);
    }

    public WeaponEntity getWeapon() {
        return weapon;
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        if (weapon == null) return;
        weapon.move(dx, dy);
    }

    public void moveTowardsTarget() {
        setFacingto(getX(), getY(), target.getX(), target.getY());
        if (Math.sqrt(Math.abs(target.getX() - getX()) ^ 2 + Math.abs(target.getY() - getY()) ^ 2) > 10)
            move(
                    (int) (getSpeed() * Math.cos(Math.toRadians(getFacing()))),
                    (int) (getSpeed() * Math.sin(Math.toRadians(getFacing())))
            );
    }

    public void destroy(GameWorld world) {
        if (weapon != null) {
            weapon.destroy();
        }
        world.removeObject(weapon);
        world.removeObject(this);
        Random rand = new Random();
        ResourcePickupEntity res = ResourceEntityManager.getInstance().getEntity();
        res.relocate(this.getX(), this.getY());
        res.setResourceAmount(rand.nextInt(5, 11));
        world.addObject(res);
        res.setAlive(true);
    }

    public void setWeapon(GameWorld world, Weapon w) {
        weapon = new WeaponEntity(this, w);
        world.addObject(weapon);
        weapon.startAI(world, weapon);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}