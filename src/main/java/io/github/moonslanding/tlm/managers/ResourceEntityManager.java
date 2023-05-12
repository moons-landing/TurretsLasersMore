package io.github.moonslanding.tlm.managers;

import io.github.moonslanding.tlm.entities.ResourcePickupEntity;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

public class ResourceEntityManager {

    ObjectArrayList<ResourcePickupEntity> entities = new ObjectArrayList<>();

    private ResourceEntityManager() {
        int STARTING_AMOUNT = 10000;
        for (int i = 0; i < STARTING_AMOUNT; i++) {
            entities.add(new ResourcePickupEntity(0));
        }
    }

    private static ResourceEntityManager instance;

    public static ResourceEntityManager getInstance() {
        if (instance == null) instance = new ResourceEntityManager();
        return instance;
    }

    public ResourcePickupEntity getEntity() {
        ResourcePickupEntity e = entities.get(0);
        if (!e.isAlive()) {
            e.setAlive(true);
            entities.remove(e);
            entities.add(e);
        } else {
            for (int i = 0; i < 50; i++) {
                entities.add(0, new ResourcePickupEntity(0));
            }
            e = getEntity();
        }
        return e;
    }
}
