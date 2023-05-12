package io.github.moonslanding.tlm.managers;

import io.github.moonslanding.tlm.entities.ProjectileEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class ProjectileManager {

    private static ProjectileManager instance;
    ObjectArrayList<ProjectileEntity> playerProjectiles = new ObjectArrayList<>();
    ObjectArrayList<ProjectileEntity> enemyProjectiles = new ObjectArrayList<>();

    private ProjectileManager() {
        int STARTING_PROJECTILES = 10000;
        for (int i = 0; i < STARTING_PROJECTILES; i++) {
            playerProjectiles.add(new ProjectileEntity(ProjectileEntity.ProjectileSide.PLAYER));
        }
        for (int i = 0; i < STARTING_PROJECTILES; i++) {
            enemyProjectiles.add(new ProjectileEntity(ProjectileEntity.ProjectileSide.ENEMY));
        }
    }

    public static ProjectileManager getInstance() {
        if (instance == null) instance = new ProjectileManager();
        return instance;
    }

    public ProjectileEntity getProjectile(ProjectileEntity.ProjectileSide side) {
        if (side == ProjectileEntity.ProjectileSide.PLAYER) {
            ProjectileEntity proj = playerProjectiles.get(0);
            if (!proj.isAlive()) {
                playerProjectiles.remove(proj);
                playerProjectiles.add(proj);
            } else {
                // No more free projectiles
                for (int i = 0; i < 100; i++) {
                    playerProjectiles.add(0, new ProjectileEntity(ProjectileEntity.ProjectileSide.PLAYER));
                }
                proj = getProjectile(side);
            }
            return proj;
        } else if (side == ProjectileEntity.ProjectileSide.ENEMY) {
            ProjectileEntity proj = enemyProjectiles.get(0);
            if (!proj.isAlive()) {
                enemyProjectiles.remove(proj);
                enemyProjectiles.add(proj);
            } else {
                // No more free projectiles
                for (int i = 0; i < 100; i++) {
                    enemyProjectiles.add(0, new ProjectileEntity(ProjectileEntity.ProjectileSide.ENEMY));
                }
                proj = getProjectile(side);
            }
            return proj;
        }
        return null;
    }
}
