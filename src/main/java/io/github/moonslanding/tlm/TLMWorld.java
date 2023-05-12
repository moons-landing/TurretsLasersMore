package io.github.moonslanding.tlm;

import io.github.moonslanding.tlm.engine.GameWorld;
import io.github.moonslanding.tlm.entities.ProjectileEntity;

import java.util.Timer;
import java.util.TimerTask;

public class TLMWorld extends GameWorld {

    public TLMWorld(int width, int height) {
        super(width, height);
        startUpdating();
    }

    private void startUpdating() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                processProjectiles();
            }
        }, 10L, 1000/60);
    }

    private void processProjectiles() {
        getObjects().forEach(o -> {
            if (o instanceof ProjectileEntity proj) {
                if (!proj.isAlive()) return;
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

}
