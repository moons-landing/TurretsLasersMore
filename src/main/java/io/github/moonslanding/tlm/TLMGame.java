package io.github.moonslanding.tlm;

import io.github.moonslanding.tlm.engine.*;

import javax.swing.*;
import java.awt.*;

public class TLMGame {

    private static final Sprite playerShipSprite = SpriteCache.loadSprite("player_ship");

    public static void main(String[] args) {
        Game game = new Game();
        GameView gui = new GameView(game);
        JFrame window = new JFrame();

        testSprites(game);

        window.add(gui);
        window.pack();

        window.setTitle("Turrets, Lasers and More");
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

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
    }

}