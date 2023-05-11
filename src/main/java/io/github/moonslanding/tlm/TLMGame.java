package io.github.moonslanding.tlm;

import io.github.moonslanding.tlm.camera.FollowedGameView;
import io.github.moonslanding.tlm.engine.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class TLMGame {

    private static final Sprite playerShipSprite = SpriteCache.loadSprite("player_ship");
    private static GameWorld world = new GameWorld(2000,2000 );
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

    private static void testFollow(Game game) {
        SpritedGameObject player = new SpritedGameObject(
                world.getWidth() / 2,
                world.getHeight() / 2,
                "player_ship"
        );


        world.addObject(player);
        player.setTint(Color.CYAN);

        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            world.addObject(new SpritedGameObject(
                    rand.nextInt(0, world.getWidth()),
                    rand.nextInt(0, world.getHeight()),
                    "player_ship"
            ));
        }

        GameScene followScene = new GameScene(game);
        followScene.registerKeybind(KeyEvent.VK_Q, (g) -> {
            System.out.println("Q Presses");
            // yellowShip.rotate(................)
            // rotate in counterclockwise direction
            yellowShip.setFacing(yellowShip.getFacing()-10);
        }, GameScene.KeybindEventType.PRESSED);
        followScene.registerKeybind(KeyEvent.VK_E, (g) -> {
            System.out.println("E Presses");
            // yellowShip.rotate(.................)
            // rotate in clockwise direction
            yellowShip.setFacing(yellowShip.getFacing()+10);
        }, GameScene.KeybindEventType.PRESSED);
        followScene.registerKeybind(KeyEvent.VK_W, (g) -> {
            player.move(0, -10);
        }, GameScene.KeybindEventType.PRESSED);
        followScene.registerKeybind(KeyEvent.VK_S, (g) -> {
            player.move(0, 10);
        }, GameScene.KeybindEventType.PRESSED);
        followScene.registerKeybind(KeyEvent.VK_A, (g) -> {
            player.move(-10, 0);
        }, GameScene.KeybindEventType.PRESSED);
        followScene.registerKeybind(KeyEvent.VK_D, (g) -> {
            player.move(10, 0);
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