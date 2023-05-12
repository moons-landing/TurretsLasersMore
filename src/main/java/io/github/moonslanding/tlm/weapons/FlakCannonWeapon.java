package io.github.moonslanding.tlm.weapons;

import io.github.moonslanding.tlm.engine.Game;
import io.github.moonslanding.tlm.engine.GameObject;
import io.github.moonslanding.tlm.engine.GameWorld;
import io.github.moonslanding.tlm.entities.PlayerShip;
import io.github.moonslanding.tlm.entities.ProjectileEntity;
import io.github.moonslanding.tlm.entities.WeaponEntity;
import io.github.moonslanding.tlm.managers.ProjectileManager;

import java.util.Timer;

public class FlakCannonWeapon extends Weapon {

    public FlakCannonWeapon() {
        super("FlakCannon", "flak_cannon", 10, 1);
    }

    @Override
    public void shoot(GameWorld world, GameObject source) {
        if (source instanceof WeaponEntity w) {
            for (int i = -3; i <= 3; i++) {
                ProjectileEntity proj;
                if (w.getOwner() instanceof PlayerShip) {
                    proj = ProjectileManager.getInstance().getProjectile(ProjectileEntity.ProjectileSide.PLAYER);
                } else {
                    proj = ProjectileManager.getInstance().getProjectile(ProjectileEntity.ProjectileSide.ENEMY);
                }
                proj.relocate(source.getX(), source.getY());
                proj.setFacing(source.getFacing() - (i * 30));
                proj.setMaxAliveTime(40);
                proj.setVelocity(3);
                proj.setDamage(this.getDamage());
                world.addObject(proj);
                proj.setAlive(true);
            }
        }
    }
}
