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

    public void registerKeybind(int KeyCode, Consumer<Game> consumer, String type) {
        /**
         * @Param type the input string whether `pressed` or `released`
         *
         *
         *
         *
         */
switch (type.toLowerCase()) {
            case "pressed":
                pressListeners.put(KeyCode, consumer);
                break;
            case "released":
                releaseListeners.put(KeyCode, consumer);
                break;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
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
