package io.github.moonslanding.tlm.engine;

import io.github.moonslanding.tlm.engine.interfaces.IRenderable;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GameView extends Canvas implements Runnable {

    private final Game game;
    private Thread drawThread;
    private final int FPS = 60;

    public GameView(Game game, int width, int height) {
        this.game = game;
        setPreferredSize(new Dimension(width, height));
        this.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e) {
                game.getCurrentScene().onKeyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                game.getCurrentScene().onKeyReleased(e);
            }
        });
    }

    public GameView(Game game) {
        this(game, 800, 600);
    }

    public void startDrawThread() {
        drawThread = new Thread(this);
        drawThread.start();
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

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(drawThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                repaint();
                delta--;
            }
        }
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
