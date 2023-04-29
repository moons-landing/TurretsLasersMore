package io.github.moonslanding.tlm.engine;

public class Game {

    private GameWorld world;

    public Game(GameWorld world) {
        this.world = world;
    }

    public Game() {
        this(new GameWorld(800, 600));
    }

    public GameWorld getWorld() {
        return world;
    }

    public void setWorld(GameWorld world) {
        this.world = world;
    }

}
