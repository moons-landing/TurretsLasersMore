package io.github.moonslanding.tlm;

import io.github.moonslanding.tlm.engine.Game;
import io.github.moonslanding.tlm.engine.GameView;
import io.github.moonslanding.tlm.entities.PlayerShip;
import io.github.moonslanding.tlm.scenes.PlayingScene;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TLMGame {

    private static final TLMWorld world = new TLMWorld(2000, 2000);
    private static final Game game = new Game(world);
    private static final JFrame window = new JFrame();
    private static GameView gui;

    public static void main(String[] args) {
        gui = new GameView(game);

        window.add(gui);
        window.pack();

        window.setTitle("Turrets, Lasers and More");
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setCursor(window.getToolkit().createCustomCursor(
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new Point(),
                null
        ));

        gui.startDrawThread();
        start(game);
    }

    private static void start(Game game) {
        PlayerShip player = new PlayerShip(game.getWorld().getWidth() / 2, game.getWorld().getHeight() / 2);
        game.getWorld().addObject(player);

        world.startSpawningEnemies(player);

        PlayingScene playingScene = new PlayingScene(game, player);
        changeView(playingScene);
        game.setCurrentScene(playingScene.getControlScene());
        player.startFiring(game.getWorld());
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