package io.github.moonslanding.tlm;

import io.github.moonslanding.tlm.engine.*;
import io.github.moonslanding.tlm.entities.EnemyShip;
import io.github.moonslanding.tlm.entities.PlayerShip;
import io.github.moonslanding.tlm.entities.WeaponEntity;
import io.github.moonslanding.tlm.scenes.PlayingScene;
import io.github.moonslanding.tlm.weapons.FlakCannonWeapon;
import io.github.moonslanding.tlm.weapons.RailgunWeapon;
import io.github.moonslanding.tlm.weapons.TurretWeapon;
import io.github.moonslanding.tlm.weapons.Weapon;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TLMGame {

    private static TLMWorld world = new TLMWorld(2000,2000 );
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
        window.setCursor(window.getToolkit().createCustomCursor(
                new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB),
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