package io.github.moonslanding.tlm.scenes;

import io.github.moonslanding.tlm.camera.FollowedGameView;
import io.github.moonslanding.tlm.engine.Game;
import io.github.moonslanding.tlm.engine.GameScene;
import io.github.moonslanding.tlm.entities.PlayerShip;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class PlayingScene extends FollowedGameView {

    private boolean movingLeft, movingRight, movingUp, movingDown, rotateClockwise, rotateCounterclockwise;
    private Game game;
    private GameScene scene;
    private PlayerShip playerShip;

    public PlayingScene(Game game, PlayerShip player) {
        super(game, player);
        this.game = game;
        scene = new GameScene(game);
        playerShip = player;
        registerBindings();
        registerMovement();
    }

    public GameScene getControlScene() {
        return scene;
    }

    private void registerMovement() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!movingRight && movingLeft
                        && !(playerShip.getX() == 0)) {
                    playerShip.move(-1, 0);
                }
                if (movingRight && !movingLeft
                        && !(playerShip.getX() == game.getWorld().getWidth())) {
                    playerShip.move(1, 0);
                }
                if (!movingDown && movingUp
                        && !(playerShip.getY() == 0)) {
                    playerShip.move(0, -1);
                }
                if (movingDown && !movingUp
                        && !(playerShip.getY() == game.getWorld().getHeight())) {
                    playerShip.move(0, 1);
                }
            }
        }, 10, 1000/(49+playerShip.getSpeed()));
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (rotateClockwise && !rotateCounterclockwise) {
                    if (playerShip.getFacing() + 2 >= 360) playerShip.setFacing(0);
                    else playerShip.setFacing(playerShip.getFacing() + 1);
                }
                if (rotateCounterclockwise && !rotateClockwise) {
                    if (playerShip.getFacing() - 2 <= -1) playerShip.setFacing(359);
                    else playerShip.setFacing(playerShip.getFacing() - 1);
                }
            }
        }, 10, 5);
    }

    private void registerBindings() {
        scene.registerKeybind(KeyEvent.VK_W, (g) -> movingUp = true, GameScene.KeybindEventType.PRESSED);
        scene.registerKeybind(KeyEvent.VK_W, (g) -> movingUp = false, GameScene.KeybindEventType.RELEASED);
        scene.registerKeybind(KeyEvent.VK_A, (g) -> movingLeft = true, GameScene.KeybindEventType.PRESSED);
        scene.registerKeybind(KeyEvent.VK_A, (g) -> movingLeft = false, GameScene.KeybindEventType.RELEASED);
        scene.registerKeybind(KeyEvent.VK_S, (g) -> movingDown = true, GameScene.KeybindEventType.PRESSED);
        scene.registerKeybind(KeyEvent.VK_S, (g) -> movingDown = false, GameScene.KeybindEventType.RELEASED);
        scene.registerKeybind(KeyEvent.VK_D, (g) -> movingRight = true, GameScene.KeybindEventType.PRESSED);
        scene.registerKeybind(KeyEvent.VK_D, (g) -> movingRight = false, GameScene.KeybindEventType.RELEASED);

        // Temporary rotation solution with keybinds.
        scene.registerKeybind(KeyEvent.VK_Q, (g) -> rotateCounterclockwise = true, GameScene.KeybindEventType.PRESSED);
        scene.registerKeybind(KeyEvent.VK_Q, (g) -> rotateCounterclockwise = false, GameScene.KeybindEventType.RELEASED);
        scene.registerKeybind(KeyEvent.VK_E, (g) -> rotateClockwise = true, GameScene.KeybindEventType.PRESSED);
        scene.registerKeybind(KeyEvent.VK_E, (g) -> rotateClockwise = false, GameScene.KeybindEventType.RELEASED);
    }

    @Override
    public void renderOnCanvas(Graphics g) {
        super.renderOnCanvas(g);
        g.setColor(Color.white);
        g.drawString(playerShip.getX() + "," + playerShip.getY(), 50, 50);
    }
}
