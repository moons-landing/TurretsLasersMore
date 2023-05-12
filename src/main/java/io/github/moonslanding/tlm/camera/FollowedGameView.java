package io.github.moonslanding.tlm.camera;

import io.github.moonslanding.tlm.TLMGame;
import io.github.moonslanding.tlm.engine.*;
import io.github.moonslanding.tlm.engine.interfaces.IRenderable;

import java.awt.*;

public class FollowedGameView extends GameView {

    private final GameWorld world;
    private final SpritedGameObject pivotObject;
    private Point gvThreshold;
    private Point playerPositionInView = new Point(0,0);

    public FollowedGameView(Game game, int width, int height, SpritedGameObject pivotObject) {
        super(game, width, height);
        this.pivotObject = pivotObject;
        if(game.getWorld() == null) {
            throw new NullPointerException("Game doesn't have an initialized GameWorld or is null");
        }
        world = game.getWorld();
        gvThreshold = getCenterOfCanvas();
    }

    public FollowedGameView(Game game, SpritedGameObject pivotObject) {
        super(game);
        this.pivotObject = pivotObject;
        if(game.getWorld() == null) {
            throw new NullPointerException("Game doesn't have an initialized GameWorld or is null");
        }
        world = game.getWorld();
        gvThreshold = getCenterOfCanvas();
    }

    @Override
    public void renderOnCanvas(Graphics g) {
        // TODO: Make the canvas follow the pivotObject and as it gets closer to a wall, lease the object.
        Graphics2D g2d = (Graphics2D) g.create();
        renderAll(g);
        g2d.dispose();
    }

    private void renderAll(Graphics g) {
        int zoom = 1;
        Point pivotCoords = new Point(pivotObject.getX(), pivotObject.getY());
        Graphics2D g2d = (Graphics2D) g.create();
        WrappedGraphic wg = new WrappedGraphic(g2d, zoom);

        int x, y;
        Point viewCenter = new Point(pivotCoords.x, pivotCoords.y);

        boolean withinX = (pivotCoords.x >= gvThreshold.x
                && pivotCoords.x <= world.getWidth() - gvThreshold.x);
        boolean withinY = (pivotCoords.y >= gvThreshold.y
                && pivotCoords.y <= world.getHeight() - gvThreshold.y);

        if (withinX) {
            x = gvThreshold.x;
        } else {
            if (pivotCoords.x < gvThreshold.x) {
                x = (((pivotCoords.x) * gvThreshold.x) / gvThreshold.x);
                viewCenter.x = minLimitedBoundary(pivotCoords.x, gvThreshold.x, gvThreshold.x);
            } else {
                x = (((pivotCoords.x - (world.getWidth() - gvThreshold.x))
                        * (getWidth() - gvThreshold.x))
                        / (world.getWidth() - (world.getWidth() - gvThreshold.x))) + gvThreshold.x;
                viewCenter.x = maxLimitedBoundary(pivotCoords.x, gvThreshold.x, world.getWidth() - gvThreshold.x);
            }
        }

        if (withinY) {
            y = gvThreshold.y;
        } else {
            if (pivotCoords.y < gvThreshold.y) {
                y = (((pivotCoords.y) * gvThreshold.y) / gvThreshold.y);
                viewCenter.y = minLimitedBoundary(pivotCoords.y, gvThreshold.y, gvThreshold.y);
            } else {
                y = (((pivotCoords.y - (world.getHeight() - gvThreshold.y))
                        * (getHeight() - gvThreshold.y))
                        / (world.getHeight() - (world.getHeight() - gvThreshold.y))) + gvThreshold.y;
                viewCenter.y = maxLimitedBoundary(pivotCoords.y, gvThreshold.y, world.getHeight() - gvThreshold.y);
            }
        }

        pivotObject.render(wg, x, y);
        playerPositionInView.x = x;
        playerPositionInView.y = y;
        renderObjectsInFoV(g, 50, viewCenter);
        g2d.dispose();
    }

    private void renderObjectsInFoV(Graphics g, int maxOffset, Point viewCenter) {
        int zoom = 1;
        Graphics2D g2d = (Graphics2D) g.create();
        WrappedGraphic wg = new WrappedGraphic(g2d, zoom);

        world.getObjects().forEach((o) -> {
            if (o == pivotObject) return;
            if (o.getX() < viewCenter.x - (gvThreshold.x + maxOffset)
            || o.getX() > viewCenter.x + (gvThreshold.x + maxOffset)
            || o.getY() < viewCenter.y - (gvThreshold.y + maxOffset)
            || o.getY() > viewCenter.y + (gvThreshold.y + maxOffset)) return;
            if (o instanceof IRenderable) {
                Point objectCoords = new Point(o.getX(), o.getY());
                int dx = objectCoords.x - viewCenter.x;
                int dy = objectCoords.y - viewCenter.y;
                ((IRenderable) o).render(wg, gvThreshold.x + dx, gvThreshold.y + dy);
            }
        });
        g2d.dispose();
    }

    private int minLimitedBoundary(int center, int offset, int min) {
        if (center < offset) return min;
        return center - offset;
    }

    private int maxLimitedBoundary(int center, int offset, int max) {
        if (center > (max - offset)) return max;
        return center + offset;
    }

    private Point getCenterOfCanvas() {
        return new Point(this.getPreferredSize().width / 2, this.getPreferredSize().height / 2);
    }

    public Point getPlayerPositionInView() {
        return playerPositionInView;
    }
}
