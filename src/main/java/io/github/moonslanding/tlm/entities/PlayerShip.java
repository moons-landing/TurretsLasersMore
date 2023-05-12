package io.github.moonslanding.tlm.entities;

import io.github.moonslanding.tlm.engine.GameWorld;
import io.github.moonslanding.tlm.engine.SpritedGameObject;
import io.github.moonslanding.tlm.managers.ProjectileManager;
import io.github.moonslanding.tlm.weapons.Weapon;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayerShip extends SpritedGameObject {

    private int level = 1;
    private int hullCount = 10;

    // Attributes for basic turret
    private int baseDamage = 5;
    private double fireSpeed = 1.5;

    private int resources = 0;

    private ObjectArrayList<WeaponEntity> weapons = new ObjectArrayList<>();

    public PlayerShip(int spawnX, int spawnY) {
        super(spawnX, spawnY, "player_ship");
        setTint(Color.CYAN);
        setWidth(getWidth() - 2);
        setHeight(getHeight() - 2);
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

    public int getBaseDamage() {
        return baseDamage;
    }

    public double getFireSpeed() {
        return fireSpeed;
    }

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    private Timer firing = new Timer();

    public void startFiring(GameWorld world) {
        firing.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ProjectileEntity proj = ProjectileManager.getInstance().getProjectile(ProjectileEntity.ProjectileSide.PLAYER);
                proj.relocate(getX(), getY());
                proj.setFacing(getFacing());
                proj.setMaxAliveTime(60);
                proj.setVelocity(3);
                proj.setDamage(getBaseDamage());
                world.addObject(proj);
                proj.setAlive(true);
            }
        }, 10L, (long) (1000 / getFireSpeed()));
    }

    public void addWeaponEntity(GameWorld world, Weapon weapon) {
        WeaponEntity entity = new WeaponEntity(this, weapon);
        weapons.add(entity);
        world.addObject(entity);
        updateWeaponsPosition();
        entity.startAI(world, entity);
    }

    public void callSupply(GameWorld world) {
        Random rand = new Random();
        WeaponPickupEntity e = new WeaponPickupEntity();
        int x = rand.nextInt(getX() - 200, getX() + 201);
        int y = rand.nextInt(getY() - 200, getY() + 201);
        if (x < 0) x = 0;
        if (x > world.getWidth()) x = world.getWidth();
        if (y < 0) y = 0;
        if (y > world.getHeight()) y = world.getHeight();
        e.relocate(x, y);
        e.setAlive(true);
        world.addObject(e);
    }

    private void updateWeaponsPosition() {
        if (weapons.size() == 0) return;
        for (int i = 0; i < weapons.size(); i++) {
            weapons.get(i).relocate(
                    (int) ((Math.sin((double) i / weapons.size() * 2 * Math.PI) * (getWidth() * 2)) + getX()),
                    (int) ((Math.cos((double) i / weapons.size() * 2 * Math.PI) * (getHeight() * 2)) + getY())
            );
        }
    }

    public void destroy(GameWorld world) {
        firing.cancel();
        if (weapons.size() == 0) return;
        for (WeaponEntity weapon : weapons) {
            world.removeObject(weapon);
            weapon.destroy();
        }
        world.removeObject(this);
    }

    @Override
    public void move(int dx, int dy) {
        super.move(dx, dy);
        if (weapons.size() == 0) return;
        for (WeaponEntity weapon : weapons) {
            weapon.move(dx, dy);
        }
    }
}