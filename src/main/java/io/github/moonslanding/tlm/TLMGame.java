package io.github.moonslanding.tlm;

import io.github.moonslanding.tlm.camera.FollowedGameView;
import io.github.moonslanding.tlm.engine.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TLMGame {

    private static final Sprite playerShipSprite = SpriteCache.loadSprite("player_ship");
    private static GameWorld world = new GameWorld(10000, 10000);
    private static Game game = new Game(world);
    private static GameView gui;
    private static JFrame window = new JFrame();

    public static void main(String[] args) {
        gui = new GameView(game);

        window.add(gui);
        window.pack();

        window.setTitle("Turrets, Lasers and More");
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);

        gui.startDrawThread();
        testFollow(game);
    }

    @Deprecated
    private static void testSprites(Game game) {
        game.getWorld().addObject(
                new SpritedGameObject(
                        (game.getWorld().getWidth() / 2) - (playerShipSprite.getWidth() / 2),
                        (game.getWorld().getHeight() / 2) - (playerShipSprite.getHeight() / 2),
                        "player_ship")
        );

        // Tinting Test
        SpritedGameObject yellowShip = new SpritedGameObject(
                (game.getWorld().getWidth() / 2) - (playerShipSprite.getWidth() / 2) - 30,
                (game.getWorld().getHeight() / 2) - (playerShipSprite.getHeight() / 2),
                "player_ship"
        );
        yellowShip.setTint(Color.YELLOW);
        game.getWorld().addObject(yellowShip);

        SpritedGameObject cyanShip = new SpritedGameObject(
                (game.getWorld().getWidth() / 2) - (playerShipSprite.getWidth() / 2) + 30,
                (game.getWorld().getHeight() / 2) - (playerShipSprite.getHeight() / 2),
                "player_ship"
        );
        cyanShip.setTint(Color.CYAN);
        game.getWorld().addObject(cyanShip);

        // Demo Scene
        GameScene demoScene = new GameScene(game);

        demoScene.registerKeybind(KeyEvent.VK_W, (e) -> {
            System.out.println("W Pressed");
        }, GameScene.KeybindEventType.PRESSED);

        demoScene.registerKeybind(KeyEvent.VK_W, (e) -> {
            System.out.println("W Released");
        }, GameScene.KeybindEventType.RELEASED);

        game.setCurrentScene(demoScene);
    }

    private static void testFollow(Game game) {
        SpritedGameObject player = new SpritedGameObject(
                world.getWidth() / 2,
                world.getHeight() / 2,
                "player_ship"
        );
        world.addObject(player);
        GameScene followScene = new GameScene(game);
        followScene.registerKeybind(KeyEvent.VK_W, (g) -> {
            player.move(0, -100);
        }, GameScene.KeybindEventType.PRESSED);
        followScene.registerKeybind(KeyEvent.VK_S, (g) -> {
            player.move(0, 100);
        }, GameScene.KeybindEventType.PRESSED);
        followScene.registerKeybind(KeyEvent.VK_A, (g) -> {
            player.move(-100, 0);
        }, GameScene.KeybindEventType.PRESSED);
        followScene.registerKeybind(KeyEvent.VK_D, (g) -> {
            player.move(100, 0);
        }, GameScene.KeybindEventType.PRESSED);
        game.setCurrentScene(followScene);

        changeView(new FollowedGameView(game, player));
    }

    private static void changeView(GameView view) {
        gui.stopDrawThread();
        gui = view;
        gui.revalidate();
        window.add(gui);
        window.pack();
        gui.startDrawThread();
    }



}