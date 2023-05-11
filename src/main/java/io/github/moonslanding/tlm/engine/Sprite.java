package io.github.moonslanding.tlm.engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Sprite {

    private int width, height;
    private BufferedImage image;

    private Sprite() {

    }

    public static Sprite fromInputStream(InputStream stream) throws IOException {
        Sprite sprite = new Sprite();
        sprite.image = ImageIO.read(stream);
        sprite.width = sprite.image.getWidth();
        sprite.height = sprite.image.getHeight();

        return sprite;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getTexture() {
        return image;
    }
}
