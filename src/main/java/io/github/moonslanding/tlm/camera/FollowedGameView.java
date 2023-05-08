package io.github.moonslanding.tlm.camera;

import io.github.moonslanding.tlm.engine.Game;
import io.github.moonslanding.tlm.engine.GameObject;
import io.github.moonslanding.tlm.engine.GameView;
import io.github.moonslanding.tlm.engine.GameWorld;

import java.awt.*;

public class FollowedGameView extends GameView {

    private final GameWorld world;
    private final GameObject pivotObject;

    public FollowedGameView(Game game, int width, int height, GameObject pivotObject) {
        super(game, width, height);
        this.pivotObject = pivotObject;
        if(game.getWorld() == null) {
            throw new NullPointerException("Game doesn't have an initialized GameWorld or is null");
        }
        world = game.getWorld();
    }

    public FollowedGameView(Game game, GameObject pivotObject) {
        super(game);
        this.pivotObject = pivotObject;
        if(game.getWorld() == null) {
            throw new NullPointerException("Game doesn't have an initialized GameWorld or is null");
        }
        world = game.getWorld();
    }

    @Override
    public void renderOnCanvas(Graphics g) {
        //super.renderOnCanvas(g);
        // TODO: Make the canvas follow the pivotObject and as it gets closer to a wall, lease the object.
    }
}
