package io.github.moonslanding.tlm.engine;

import io.github.moonslanding.tlm.engine.interfaces.IRenderable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class GameView extends JPanel implements Runnable {

    private final Game game;
    private Thread drawThread;
    private final int FPS = 60;

    private static Point mousePosition;

    public GameView(Game game, int width, int height) {
        super(true);
        this.game = game;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
        setFocusable(true);
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
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (mousePosition == null) {
                    mousePosition = new Point(e.getX(), e.getY());
                }
                mousePosition.x = e.getX();
                mousePosition.y = e.getY();
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


    public void stopDrawThread() {  
        drawThread.interrupt();
        drawThread = null;

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderOnCanvas(g);
        System.out.println(mousePosition);
    }

    public void renderOnCanvas(Graphics g) {
        Graphics2D graphics = (Graphics2D) g.create();
        game.getWorld().getObjects().forEach(gameObject -> {
            if (gameObject instanceof IRenderable) {
                WrappedGraphic wrappedGraphic = new WrappedGraphic(graphics, 3);
                ((IRenderable) gameObject).render(wrappedGraphic);
            }
        });
        graphics.dispose();
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
