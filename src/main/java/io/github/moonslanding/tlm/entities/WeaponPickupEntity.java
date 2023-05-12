package io.github.moonslanding.tlm.entities;

import io.github.moonslanding.tlm.engine.GameObject;
import io.github.moonslanding.tlm.engine.WrappedGraphic;
import io.github.moonslanding.tlm.engine.interfaces.IRenderable;

import java.awt.*;

public class WeaponPickupEntity extends GameObject implements IRenderable {

    public WeaponPickupEntity() {
        super(-1, -1, 5, 5);
    }

    @Override
    public void render(WrappedGraphic graphics) {}

    @Override
    public void render(WrappedGraphic graphics, int x, int y) {
        if (getX() < 0 && getY() < 0) return;
        Graphics g = graphics.getGraphic();
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(Color.MAGENTA);
        g2d.fillRect(x - 2, y - 2, getWidth(), getHeight());
        g2d.dispose();
    }

}
