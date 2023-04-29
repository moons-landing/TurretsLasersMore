package io.github.moonslanding.tlm.engine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SpriteCache {

    private static SpriteCache instance;
    private Map<String, Sprite> loadedSprites = new HashMap<>();
    private ClassLoader classLoader = getClass().getClassLoader();

    private SpriteCache() {

    }

    private static void initialize() {
        if(instance != null) return;
        instance = new SpriteCache();
    }

    public static Map<String, Sprite> getLoadedSprites() {
        initialize();
        return instance.loadedSprites;
    }

    public static Sprite loadSprite(String spriteName) {
        // Try to load a sprite from the cache.
        initialize();
        Map<String, Sprite> presentMap = getLoadedSprites();
        if (presentMap.containsKey(spriteName)) return presentMap.get(spriteName);
        System.out.println("loading new sprite: " + spriteName);
        try {
            loadFromResource(spriteName);
            return getLoadedSprites().get(spriteName);
        } catch (IOException e) {
            System.out.println(spriteName + " cannot be loaded!");
            return null;
        }
    }

    private static void loadFromResource(String spriteName) throws IOException {
        InputStream stream = instance.classLoader.getResourceAsStream("sprites/" + spriteName + ".png");
        if (stream == null) throw new FileNotFoundException("sprite asset: " + spriteName + " not found!");
        getLoadedSprites().put(spriteName, Sprite.fromInputStream(stream));
    }

}
