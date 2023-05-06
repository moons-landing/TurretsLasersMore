package io.github.moonslanding.tlm.engine;

import io.github.moonslanding.tlm.engine.interfaces.IRenderable;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameView extends Canvas {

    private final Game game;

    public GameView(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(game.getWorld().getWidth(), game.getWorld().getHeight()));
        this.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                game.getCurrentScene().onKeyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e){
            game.getCurrentScene().onKeyReleased(e);
        }});
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        game.getWorld().getObjects().forEach(gameObject -> {
            if (gameObject instanceof IRenderable) {
                Graphics graphics = this.getGraphics();
                WrappedGraphic wrappedGraphic = new WrappedGraphic(graphics, 3);
                ((IRenderable) gameObject).render(wrappedGraphic);
            }
        });
    }

//    public  void addKeyListener(new KeyAdapter(){
//        @Override
//        public void keyPressed(KeyEvent e) {
//            game.getCurrentScene().onKeyPressed(e);
//        }
//
//        @Override
//        public void keyReleased(KeyEvent e){
//            game.getCurrentScene().onKeyReleased(e);
//        }
    }
    })
}
