package io.github.moonslanding.tlm.engine;

import io.github.moonslanding.tlm.engine.interfaces.IRenderable;

import java.awt.*;


public class GameView extends Canvas {

    private Game game;

    public GameView(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(game.getWorld().getWidth(), game.getWorld().getHeight()));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        game.getWorld().getObjects().forEach(gameObject -> {
            if (gameObject instanceof IRenderable) {
                ((IRenderable) gameObject).render(this.getGraphics());
            }
        });
    }
}
