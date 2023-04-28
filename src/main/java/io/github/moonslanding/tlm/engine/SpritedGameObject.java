package io.github.moonslanding.tlm.engine;

public class SpritedGameObject extends GameObject {

    public SpritedGameObject(int x, int y, String spriteName) {
        super(x, y, 0, 0);
        Sprite sprite = SpriteCache.loadSprite(spriteName);
        if (sprite != null) {
            this.setWidth(sprite.getWidth());
            this.setHeight(sprite.getHeight());
        }
    }

}
