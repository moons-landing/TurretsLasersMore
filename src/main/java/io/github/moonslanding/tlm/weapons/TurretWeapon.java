package io.github.moonslanding.tlm.weapons;

import io.github.moonslanding.tlm.engine.Game;
import io.github.moonslanding.tlm.engine.GameObject;
import io.github.moonslanding.tlm.engine.GameWorld;
import io.github.moonslanding.tlm.entities.PlayerShip;
import io.github.moonslanding.tlm.entities.ProjectileEntity;
import io.github.moonslanding.tlm.entities.WeaponEntity;
import io.github.moonslanding.tlm.managers.ProjectileManager;

public class TurretWeapon extends Weapon {

    public TurretWeapon() {
        super("Turret", "turret", 10, 0);
    }

    @Override
    public void shoot(GameWorld world, GameObject source) {
        if (source instanceof WeaponEntity w) {
            ProjectileEntity proj;
            if (w.getOwner() instanceof PlayerShip) {
                proj = ProjectileManager.getInstance().getProjectile(ProjectileEntity.ProjectileSide.PLAYER);
            } else {
                proj = ProjectileManager.getInstance().getProjectile(ProjectileEntity.ProjectileSide.ENEMY);
            }
            proj.relocate(source.getX(), source.getY());
            proj.setFacing(source.getFacing());
            proj.setMaxAliveTime(80);
            proj.setVelocity(3);
            proj.setDamage(this.getDamage());
            world.addObject(proj);
            proj.setAlive(true);
        }
    }
}
