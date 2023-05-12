package io.github.moonslanding.tlm.entities;

import io.github.moonslanding.tlm.engine.Game;
import io.github.moonslanding.tlm.engine.GameObject;
import io.github.moonslanding.tlm.engine.SpritedGameObject;
import io.github.moonslanding.tlm.weapons.Weapon;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class WeaponEntity extends SpritedGameObject {

    private Weapon weapon;
    private GameObject owner;
    private ObjectArrayList<GameObject> aimCandidates;

    public WeaponEntity(GameObject owner, Weapon weapon, String spriteName) {
        super(owner.getX(), owner.getY(), spriteName);
        this.weapon = weapon;
        this.owner = owner;
        if (this.owner instanceof PlayerShip) {
            aimCandidates = new ObjectArrayList<>();
        }
    }

    public GameObject getOwner() {
        return owner;
    }

    public void startAI(Game game, WeaponEntity self) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                while (!aimRandomly(game)) {}
                weapon.shoot(game, self);
            }
        }, 1L, (1 + weapon.getCooldown()) * 1000L);
    }

    private boolean aimRandomly(Game game) {
        aimCandidates.clear();
        if (owner instanceof PlayerShip) {
            game.getWorld().getObjects().forEach(
                    o -> {
                        if (Math.sqrt
                                (Math.abs(o.getX() - owner.getX())^2 +
                                        (Math.abs(o.getY() - owner.getY())^2)) < 100) {
                            aimCandidates.add(o);
                        }
                    }
            );
            GameObject aimAt = aimCandidates.get(ThreadLocalRandom.current().nextInt(aimCandidates.size()));
            setFacing(Math.toDegrees(Math.atan2(aimAt.getY() - owner.getY(), aimAt.getX() - owner.getX())));
            return true;
        }
        return false;
    }

}