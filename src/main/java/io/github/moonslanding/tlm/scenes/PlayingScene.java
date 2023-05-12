package io.github.moonslanding.tlm.scenes;

import io.github.moonslanding.tlm.TLMWorld;
import io.github.moonslanding.tlm.camera.FollowedGameView;
import io.github.moonslanding.tlm.engine.Game;
import io.github.moonslanding.tlm.engine.GameScene;
import io.github.moonslanding.tlm.entities.PlayerShip;
import io.github.moonslanding.tlm.entities.WeaponPickupEntity;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayingScene extends FollowedGameView {

    private boolean movingLeft, movingRight, movingUp, movingDown;
    private Game game;
    private GameScene scene;
    private PlayerShip playerShip;
    private long startTime;

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
                if (!game.getWorld().getObjects().contains(playerShip)) return;
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
        }, 10, 1000/(49+playerShip.getLevel()));
 
    }

    private void registerBindings() {
        // followScene.registerMouse(MouseEvent.MOUSE_MOVED, (g) -> {
        //     player.setFacingto(gui.getMouseX(), gui.getMouseY());
        // }, GameScene.MouseEventType.MOVED);
        scene.registerMouse(MouseEvent.MOUSE_MOVED, (g) -> playerShip.setFacingto((int) getPlayerPositionInView().getX(), (int) getPlayerPositionInView().getY(), getMouseX(), getMouseY()), GameScene.MouseEventType.MOVED);
        scene.registerKeybind(KeyEvent.VK_W, (g) -> movingUp = true, GameScene.KeybindEventType.PRESSED);
        scene.registerKeybind(KeyEvent.VK_W, (g) -> movingUp = false, GameScene.KeybindEventType.RELEASED);
        scene.registerKeybind(KeyEvent.VK_A, (g) -> movingLeft = true, GameScene.KeybindEventType.PRESSED);
        scene.registerKeybind(KeyEvent.VK_A, (g) -> movingLeft = false, GameScene.KeybindEventType.RELEASED);
        scene.registerKeybind(KeyEvent.VK_S, (g) -> movingDown = true, GameScene.KeybindEventType.PRESSED);
        scene.registerKeybind(KeyEvent.VK_S, (g) -> movingDown = false, GameScene.KeybindEventType.RELEASED);
        scene.registerKeybind(KeyEvent.VK_D, (g) -> movingRight = true, GameScene.KeybindEventType.PRESSED);
        scene.registerKeybind(KeyEvent.VK_D, (g) -> movingRight = false, GameScene.KeybindEventType.RELEASED);
        scene.registerKeybind(KeyEvent.VK_SPACE, (g) -> {
            if (playerShip.getResources() < 100) return;
            playerShip.setResources(0);
            playerShip.callSupply(game.getWorld());
        }, GameScene.KeybindEventType.PRESSED);
    }

    @Override
    public void renderOnCanvas(Graphics g) {
        super.renderOnCanvas(g);
        g.setColor(Color.white);
        g.drawString("Hull: " + playerShip.getHullCount() + "/10", 30, 35);
        g.drawString("Resources: " + playerShip.getResources() + "/100", 30, 50);
        g.drawString("Level: " + playerShip.getLevel(), 30, 65);

        String coords = "Coords: " + playerShip.getX() + "," + playerShip.getY();
        String score = "Score: " + ((TLMWorld) game.getWorld()).getCurrentScore();
        FontMetrics metrics = g.getFontMetrics();
        g.drawString(coords, getWidth() - 30 - metrics.stringWidth(coords), 35);
        g.drawString(score, getWidth() - 30 - metrics.stringWidth(score), 50);

        if (playerShip.getResources() >= 100 && !((TLMWorld) game.getWorld()).isGameOver()) {
            String message = "Press Space to Call in Supply.";
            g.drawString(message, (getWidth() - metrics.stringWidth(message)) / 2, getHeight() - 35);
        }

        if (((TLMWorld) game.getWorld()).isGameOver()) {
            String finalScore = "Final " + score;
            g.drawString(finalScore, (getWidth() - metrics.stringWidth(finalScore)) / 2, getHeight() - 35);
        }

        g.drawLine(getMouseX() - 5, getMouseY(), getMouseX() + 5, getMouseY());
        g.drawLine(getMouseX(), getMouseY() - 5, getMouseX(), getMouseY() + 5);
    }
}
