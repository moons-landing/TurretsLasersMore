package io.github.moonslanding.tlm.engine;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GameScene {
    Game game;

    Map<Integer, Consumer<Game>> pressListeners = new HashMap<>();
    Map<Integer, Consumer<Game>> releaseListeners = new HashMap<>();

    public GameScene(Game game) {
        this.game = game;
    }

    public enum KeybindEventType {
        PRESSED,
        RELEASED
    }

    public void registerKeybind(int keyCode, Consumer<Game> consumer, KeybindEventType type) {
        /**
         * @Param type the input string whether `pressed` or `released`
         *
         *
         *
         *
         */
        switch (type) {
            case PRESSED -> pressListeners.put(keyCode, consumer);
            case RELEASED -> releaseListeners.put(keyCode, consumer);
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    public void onKeyPressed(KeyEvent e) {
        System.out.println(e);
        if (pressListeners.containsKey(e.getKeyCode())) {
            pressListeners.get(e.getKeyCode()).accept(game);
        }
    }

    public void onKeyReleased(KeyEvent e) {
        if (releaseListeners.containsKey(e.getKeyCode())) {
            releaseListeners.get(e.getKeyCode()).accept(game);
        }
    }
}
