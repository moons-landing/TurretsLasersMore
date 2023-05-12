package io.github.moonslanding.tlm;

import io.github.moonslanding.tlm.engine.GameObject;
import io.github.moonslanding.tlm.engine.GameWorld;
import io.github.moonslanding.tlm.entities.*;
import io.github.moonslanding.tlm.weapons.FlakCannonWeapon;
import io.github.moonslanding.tlm.weapons.RailgunWeapon;
import io.github.moonslanding.tlm.weapons.TurretWeapon;
import io.github.moonslanding.tlm.weapons.Weapon;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TLMWorld extends GameWorld {


    private int enemyThreshold = 50;
    private int enemyCount = 0;
    private long startTime;

    public long getCurrentScore() {
        return currentScore;
    }

    private long currentScore;
    private boolean gameOver;

    private ObjectArrayList<Weapon> weapons = new ObjectArrayList<>(new Weapon[]{
            new TurretWeapon(),
            new RailgunWeapon(),
            new FlakCannonWeapon()
    });

    public TLMWorld(int width, int height) {
        super(width, height);
        startUpdating();
    }

    private void startUpdating() {
        startTime = System.currentTimeMillis();
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkAlive(TLMWorld.this);
                processProjectiles();
                enemyMove();
                collisionCheck();
                if (!gameOver) currentScore = (System.currentTimeMillis() - startTime) / 1000;
                if (gameOver) {
                    while (true);
                }
            }
        }, 10L, 1000/60);
    }

    private void processProjectiles() {
        getObjects().forEach(o -> {
            if (o == null) return;
            if (o instanceof ProjectileEntity proj) {
                if (!proj.isAlive()) { removeObject(proj); return; }
                if (proj.isAlive() && proj.getMaxAliveTime() <= proj.getAliveTime()) {
                    proj.setAlive(false);
                }
                if (proj.isAlive() && (proj.getX() < 0 || proj.getX() > getWidth() || proj.getY() < 0 || proj.getY() > getHeight())) proj.setAlive(false);
                if (proj.isAlive() && proj.getMaxAliveTime() > proj.getAliveTime()) {
                    proj.move((int) (proj.getVelocity() * Math.cos(Math.toRadians(proj.getFacing()))),
                            (int) (proj.getVelocity() * Math.sin(Math.toRadians(proj.getFacing()))));
                    proj.incrementAliveTime();
                }
            }
        });
    }

    private void enemyMove() {
        getObjects().forEach(o -> {
            if (o == null) return;
            if (o instanceof EnemyShip ship) {
                ship.moveTowardsTarget();
            }
        });
    }

    private void checkAlive(GameWorld world) {
        getObjects().forEach(o -> {
            if (o == null) return;
            if (o instanceof EnemyShip enemyShip) {
                if (enemyShip.getHealth() <= 0) {
                    enemyShip.destroy(world);
                    enemyCount--;
                }
            }
            if (o instanceof PlayerShip playerShip) {
                if (playerShip.getHullCount() <= 0) {
                    world.removeObject(playerShip);
                    playerShip.destroy(world);
                    // Game over.
                    world.getObjects().forEach(e -> {
                        if (e instanceof EnemyShip ship) {
                            if (ship.getWeapon() != null) {
                                ship.getWeapon().destroy();
                            }
                        }
                    });
                    gameOver = true;
                }
            }
        });
    }

    private void collisionCheck() {
        getObjects().forEach(o -> {
            if (o == null) return;
            if (o instanceof PlayerShip playerShip) {
                intersecting(playerShip).forEach(e -> {
                    if (e instanceof ProjectileEntity proj && proj.isAlive() && proj.getSide() == ProjectileEntity.ProjectileSide.ENEMY) {
                        proj.setAlive(false);
                        playerShip.setHullCount(playerShip.getHullCount() - 1);
                        removeObject(proj);
                    }
                    if (e instanceof ResourcePickupEntity res && res.isAlive() && 100 - playerShip.getResources() > 0) {
                        res.setAlive(false);
                        playerShip.setResources(Math.min(playerShip.getResources() + res.getResourceAmount(), 100));
                        removeObject(res);
                    }
                    if (e instanceof WeaponPickupEntity wpe && wpe.isAlive()) {
                        wpe.setAlive(false);
                        Random rand = new Random();
                        playerShip.addWeaponEntity(this, weapons.get(rand.nextInt(weapons.size())));
                        playerShip.setLevel(playerShip.getLevel() + 1);
                        playerShip.setHullCount(10);
                        enemyThreshold += 20;
                        removeObject(wpe);
                    }
                });
            }
            if (o instanceof EnemyShip enemyShip) {
                intersecting(enemyShip).forEach(e -> {
                    if (e instanceof ProjectileEntity proj && proj.isAlive() && proj.getSide() == ProjectileEntity.ProjectileSide.PLAYER) {
                        proj.setAlive(false);
                        enemyShip.setHealth(enemyShip.getHealth() - proj.getDamage());
                        removeObject(proj);
                    }
                });
            }
        });
    }

    Random rand = new Random();

    public void startSpawningEnemies(PlayerShip player) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (enemyCount >= enemyThreshold) return;
                EnemyShip ship = new EnemyShip(rand.nextInt(getWidth() + 1), rand.nextInt(getHeight() + 1), "enemy_ship", player);
                ship.setHealth(ship.getHealth() * player.getLevel());
                if (rand.nextDouble() > 0.5) {
                    ship.setWeapon(TLMWorld.this, weapons.get(rand.nextInt(weapons.size())));
                }
                addObject(ship);
                enemyCount++;
            }
        }, 100L, 2500L);
    }

    private ObjectArrayList<GameObject> intersecting(GameObject object) {
        ObjectArrayList<GameObject> res = new ObjectArrayList<>();
        getObjects().forEach(o -> {
            if (o == null) return;
            if (o.getX() >= object.getX() && o.getX() <= object.getX() + object.getWidth() &&
                    o.getY() >= object.getY() && o.getY() <= object.getY() + object.getHeight()) {
                res.add(o);
            }
        });
        return res;
    }

    public boolean isGameOver() {
        return gameOver;
    }

}
