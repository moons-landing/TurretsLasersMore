package io.github.moonslanding.tlm.engine;

public class Game {

    private GameWorld world;
    private GameScene currentScene;

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


    public GameScene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(GameScene scene) {
        // do some stuff here
        this.currentScene = scene;
    }

}
