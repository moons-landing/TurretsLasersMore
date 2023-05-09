package io.github.moonslanding.tlm.camera;

import io.github.moonslanding.tlm.engine.*;

import java.awt.*;

public class FollowedGameView extends GameView {

    private final GameWorld world;
    private final SpritedGameObject pivotObject;
    private Point gvThreshold;

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
        g2d.setColor(Color.white);
        g2d.drawString("" + pivotObject.getX() + "," + pivotObject.getY() + "", 50, 50);
        renderPivotObject(g);
        g2d.dispose();
    }

    private void renderPivotObject(Graphics g) {
        int zoom = 1;
        Point pivotCoords = new Point(pivotObject.getX(), pivotObject.getY());
        Graphics2D g2d = (Graphics2D) g.create();
        WrappedGraphic wg = new WrappedGraphic(g2d, zoom);

        if (pivotCoords.x >= gvThreshold.x
                && pivotCoords.y >= gvThreshold.y
                && pivotCoords.x <= world.getWidth() - gvThreshold.x
                && pivotCoords.y <= world.getHeight() - gvThreshold.y
        ) {
            pivotObject.render(wg, gvThreshold.x, gvThreshold.y);
        } else {
            // TODO: Calculate x and y for relative position of player rendering.
            // Note: render should only fire once.
        }

        // TODO: Make other objects render within FoV.
        g2d.dispose();
    }

    private Point getCenterOfCanvas() {
        return new Point(this.getPreferredSize().width / 2, this.getPreferredSize().height / 2);
    }

    private class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
