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

            BufferedImage img = createGraphic(sprite.getTexture(), graphics);
            graphic.drawImage(img, getX(), getY(), null);
        }
    }

    public void setTint(Color tint) {
        this.tint = tint;
    }

    public void setTint(int r, int g, int b) {
        this.tint = new Color(r, g, b, 255);
    }

    private void applyTint(BufferedImage source, Graphics2D g) {

        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(tint);
        g.fillRect(0, 0,source.getWidth(), source.getHeight());
    }

    private int[] getnewImgResolution(BufferedImage source, int zoomFactor){
        // return Width and height of image after zooming
        int newImgWidth = source.getWidth() * zoomFactor;
        int newImgHeight = source.getHeight() * zoomFactor;

        return new int[]{newImgWidth, newImgHeight};
    }

    private BufferedImage createGraphic(BufferedImage source, WrappedGraphic graphics){

        int[] newImgRes = getnewImgResolution(source, graphics.getZoomFactor());

        int newImgWidth = newImgRes[0];
        int newImgHeight = newImgRes[1];

        BufferedImage result = new BufferedImage(newImgWidth, newImgHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();
        g.drawImage(source, 0, 0, newImgWidth, newImgHeight, null);
        applyTint(result, g);
        g.dispose();

        return result;
    }
}
