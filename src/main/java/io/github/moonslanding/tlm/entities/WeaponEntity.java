package io.github.moonslanding.tlm.entities;

import io.github.moonslanding.tlm.engine.GameObject;
import io.github.moonslanding.tlm.engine.GameWorld;
import io.github.moonslanding.tlm.engine.SpritedGameObject;
import io.github.moonslanding.tlm.weapons.Weapon;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class WeaponEntity extends SpritedGameObject {

    private final Weapon weapon;
    private final GameObject owner;
    private final Timer aiTimer = new Timer();
    private ObjectArrayList<GameObject> aimCandidates;

    public WeaponEntity(GameObject owner, Weapon weapon) {
        super(owner.getX(), owner.getY(), weapon.getSpriteName());
        this.weapon = weapon;
        this.owner = owner;
        if (this.owner instanceof PlayerShip) {
            aimCandidates = new ObjectArrayList<>();
            this.setTint(Color.CYAN);
        } else if (this.owner instanceof EnemyShip) {
            this.setTint(Color.YELLOW);
        }
    }

    public GameObject getOwner() {
        return owner;
    }

    public void destroy() {
        aiTimer.cancel();
    }

    public void startAI(GameWorld world, WeaponEntity self) {
        if (owner instanceof PlayerShip) {
            aiTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    while (!aimRandomly(world)) {
                    }
                    weapon.shoot(world, self);
                }
            }, 1L, (1 + weapon.getCooldown()) * 1000L);
        } else {
            aiTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    aimAtPlayer(world);
                    weapon.shoot(world, self);
                }
            }, 10L, (2 + weapon.getCooldown()) * 1000L);
        }
    }

    private void aimAtPlayer(GameWorld world) {
        world.getObjects().forEach(o -> {
            if (!(o instanceof PlayerShip)) return;
            if (Math.sqrt
                    (Math.abs(o.getX() - owner.getX()) ^ 2 +
                            (Math.abs(o.getY() - owner.getY()) ^ 2)) < 100) {
                setFacing(Math.toDegrees(Math.atan2(o.getY() - owner.getY(), o.getX() - owner.getX())));
            }
        });
    }

    private boolean aimRandomly(GameWorld world) {
        aimCandidates.clear();
        if (owner instanceof PlayerShip) {
            world.getObjects().forEach(
                    o -> {
                        if (!(o instanceof EnemyShip)) return;
                        if (Math.sqrt
                                (Math.abs(o.getX() - owner.getX()) ^ 2 +
                                        (Math.abs(o.getY() - owner.getY()) ^ 2)) < 100) {
                            aimCandidates.add(o);
                        }
                    }
            );
            if (aimCandidates.size() == 0) return false;
            GameObject aimAt = aimCandidates.get(ThreadLocalRandom.current().nextInt(aimCandidates.size()));
            setFacing(Math.toDegrees(Math.atan2(aimAt.getY() - owner.getY(), aimAt.getX() - owner.getX())));
            return true;
        }
        return false;
    }

}