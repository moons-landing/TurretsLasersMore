package io.github.moonslanding.tlm.engine;

import io.github.moonslanding.tlm.engine.interfaces.IRenderable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpritedGameObject extends GameObject implements IRenderable {

    private final Sprite sprite;

    private Color tint = new Color(0, 0, 0, 0);


    public SpritedGameObject(int x, int y, String spriteName) {
        super(x, y, 0, 0);
        sprite = SpriteCache.loadSprite(spriteName);
        if (sprite != null) {
            this.setWidth(sprite.getWidth());
            this.setHeight(sprite.getHeight());
        }
    }

    @Override
    public void render(WrappedGraphic graphics) {
        Graphics graphic = graphics.getGraphic();
        if (sprite != null) {
            BufferedImage tintApplied = applyTint(sprite.getTexture());
            BufferedImage scaled = applyZoomFactor(tintApplied, graphics.getZoomFactor()); // apply zoom factor here
            graphic.drawImage(scaled, getX(), getY(), null);
        }
    }

    public void setTint(Color tint) {
        this.tint = tint;
    }

    public void setTint(int r, int g, int b) {
        this.tint = new Color(r, g, b, 255);
    }

    private BufferedImage applyTint(BufferedImage source) {

        BufferedImage tinted = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = tinted.createGraphics();
        g.drawImage(source, 0, 0, null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(tint);
        g.fillRect(0, 0,source.getWidth(), source.getHeight());
        g.dispose();
        return tinted;
    }

    private  BufferedImage applyZoomFactor(BufferedImage source, int zoomFactor){
        int newImgWidth = source.getWidth() * zoomFactor;
        int newImgHeight = source.getHeight() * zoomFactor;

        BufferedImage scaled = new BufferedImage(newImgWidth, newImgHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaled.createGraphics();
        g.drawImage(source, 0, 0, newImgWidth, newImgHeight, null);
        g.dispose();

        return scaled;
    }
}
