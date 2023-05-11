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

            // Because the image is influenced by size, we cannot just use getX() and getY().
            graphic.drawImage(img,
                    getX() - (img.getWidth() / 2),
                    getY() - (img.getHeight() / 2),
                    null);
        }
    }

    @Override
    public void render(WrappedGraphic graphics, int x, int y) {
        Graphics graphic = graphics.getGraphic();
        if (sprite != null) {
            BufferedImage img = createGraphic(sprite.getTexture(), graphics);

            graphic.drawImage(img,
                    x - (img.getWidth() / 2),
                    y - (img.getHeight() / 2),
                    null);
        }
    }

    public void setTint(Color tint) {
        this.tint = tint;
    }

    public void setTint(int r, int g, int b) {
        this.tint = new Color(r, g, b, 255);
    }

    private BufferedImage applyTint(BufferedImage source) {
        BufferedImage img = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.drawImage(source, 0, 0, source.getWidth(), source.getHeight(), null);
        g.setComposite(AlphaComposite.SrcAtop);
        g.setColor(tint);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
        return img;
    }

    private int[] getnewImgResolution(BufferedImage source, int zoomFactor){
        // return Width and height of image after zooming
        int newImgWidth = source.getWidth() * zoomFactor;
        int newImgHeight = source.getHeight() * zoomFactor;

        return new int[]{newImgWidth, newImgHeight};
    }

    private int[] getrotatedResolution(int h, int w, double deg){
        // return Width and height of image
        double sin = Math.abs(Math.sin(Math.toRadians(deg))),
                cos = Math.abs(Math.cos(Math.toRadians(deg)));

        int newW = (int) Math.floor(w *cos + h*sin);
        int newH = (int) Math.floor(h*cos + w*sin);

        return new int[]{newW, newH};
    }

    public BufferedImage rotate(BufferedImage source, double deg){

        int[] newRotatedRes = getrotatedResolution(source.getHeight(), source.getWidth(), deg);

        int newW = newRotatedRes[0];
        int newH = newRotatedRes[1];

        BufferedImage img = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        System.out.println(getFacing());

        g.translate((newW-source.getWidth())/2, (newH-source.getHeight())/2);
        g.rotate(Math.toRadians(deg), source.getWidth()/2, source.getHeight()/2);
        g.drawRenderedImage(source, null);
        g.dispose();

        return img;
    }

    private BufferedImage createGraphic(BufferedImage source, WrappedGraphic graphics){

        int[] newImgRes = getnewImgResolution(source, graphics.getZoomFactor());

        int newImgWidth = newImgRes[0];
        int newImgHeight = newImgRes[1];

        BufferedImage scaled = new BufferedImage(newImgWidth, newImgHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaled.createGraphics();
        g.drawImage(source, 0, 0, newImgWidth, newImgHeight, null);
        BufferedImage rotated = rotate(scaled, getFacing());
        BufferedImage tinted = applyTint(rotated);
        g.dispose();

        return tinted;
    }
}
