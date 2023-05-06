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
switch (type) {
            case "pressed":
                pressListeners.get(KeyCode).accept(this.game);
                break;
            case "released":
                releaseListeners.get(KeyCode).accept(this.game);
                break;
            default:
                throw new IllegalArgumentException("Invalid type: " + type);
        }
    }

    public void handleKeyPress(KeyEvent e) {
        if (pressListeners.containsKey(e.getKeyCode())) {
            pressListeners.get(e.getKeyCode()).accept(game);
        }
    }
}
