package io.github.moonslanding.tlm.engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class GameScene {
    Game game;

    Map<Integer, Consumer<Game>> pressListeners = new HashMap<>();
    Map<Integer, Consumer<Game>> releaseListeners = new HashMap<>();

    Map<Integer, Consumer<Game>> mouseListeners = new HashMap<>();

    public GameScene(Game game) {
        this.game = game;
    }

    public void registerKeybind(int KeyCode, Consumer<Game> consumer, KeybindEventType eventType) {
        switch (eventType) {
            case PRESSED -> pressListeners.put(KeyCode, consumer);
            case RELEASED -> releaseListeners.put(KeyCode, consumer);
        }
    }

    public void registerMouse(int MouseCode, Consumer<Game> consumer, MouseEventType eventType) {
        if (Objects.requireNonNull(eventType) == MouseEventType.MOVED) {
            mouseListeners.put(MouseCode, consumer);
        }
    }

    public void onKeyPressed(KeyEvent e) {
        if (pressListeners.containsKey(e.getKeyCode())) {
            pressListeners.get(e.getKeyCode()).accept(game);
        }
    }

    public void onKeyReleased(KeyEvent e) {
        if (releaseListeners.containsKey(e.getKeyCode())) {
            releaseListeners.get(e.getKeyCode()).accept(game);
        }
    }

    public void onMouseMoved(MouseEvent e) {
        if (mouseListeners.containsKey(e.getID())) {
            mouseListeners.get(e.getID()).accept(game);
        }
    }

    public enum KeybindEventType {
        PRESSED,
        RELEASED
    }

    public enum MouseEventType {
        MOVED
    }
}
