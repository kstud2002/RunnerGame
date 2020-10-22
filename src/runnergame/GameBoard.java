/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runnergame;

/**
 *
 * @author kians
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel implements ActionListener {

    private Timer timer;
    List<Obstacle> obst = new ArrayList<>();
    List<Rectangle2D> rect = new ArrayList<>();
    private int r = new Random().nextInt(5);
    private Player player;
    private final int DELAY = 10;
    int i = 1;
    int distance = 500;
    private int points;
    private int highscore;
    private int speed;
    private int flipcount = 0;
    private boolean flip;
    private final JLabel lblPoints = new JLabel("Points: " + points);
    private final JLabel lblHighscore = new JLabel("Highscore: " + highscore);
    private final JLabel lblGameOver = new JLabel("GAME OVER");

    public GameBoard() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setBackground(Color.white);
        setFocusable(true);

        player = new Player();
        obst.add(new Obstacle(50));
        obst.add(new Obstacle(100));
        obst.add(new Obstacle(150));
        obst.add(new Obstacle(75));
        obst.add(new Obstacle(125));

        add(lblPoints);
        add(lblHighscore);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh
                = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setRenderingHints(rh);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.black);

        lblPoints.setText("Points: " + points);
        lblPoints.setLocation(10, 10);
        lblHighscore.setText("Highscore: " + highscore);
        lblHighscore.setLocation(10, 30);
        if (player.jump) {
            g2d.drawImage(player.getImage(), (int) player.getX(),
                    (int) player.getY(), this);
        } else if (flip) {
            g2d.drawImage(player.getImage(), (int) player.getX(),
                    (int) player.getY(), this);
        } else {
            g2d.drawImage(player.getImage(), (int) player.getX() + player.getWidth(),
                    (int) player.getY(), -player.getWidth(), player.getHeight(), null);
        }

        for (Rectangle2D re : rect) {
            g2d.draw(re);
            g2d.fill(re);
        }

        if (i == distance) {
            r = new Random().nextInt(5);
            distance = new Random().nextInt(100) + 200;
            rect.add(new Rectangle2D.Double(obst.get(r).getX(), obst.get(r).getY(),
                    obst.get(r).getWidth(), obst.get(r).getHeight()));
            i = 0;
        } else {
            i++;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!player.gameOver) {
            lblGameOver.setVisible(false);
            step();
        } else {
            rect.clear();
            if (points >= highscore) {
                highscore = points;
            }
            points = 0;
            distance = 500;

        }

    }

    private void step() {

        player.move();

        if (flipcount == 20) {
            flip = !flip;
            flipcount = 0;
        } else {
            flipcount++;
        }
        for (Rectangle2D re : rect) {

            if (re.getBounds().intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
                player.gameOver = true;
                add(lblGameOver);
                lblHighscore.setLocation(300, 30);
                lblGameOver.setVisible(true);
            }
            re.setRect(re.getX() - speed, re.getY(), re.getWidth(), re.getHeight());

        }
        if (points >= 9000) {
            speed = 8;
        } else if (points >= 6000) {
            speed = 7;
        } else if (points >= 3000) {
            speed = 6;
        } else {
            speed = 5;
        }

        addPoints();

        removeObstacles();

        repaint();
    }

    private void addPoints() {
        points++;
    }

    private void removeObstacles() {
        try {
            for (Rectangle2D re : rect) {

                if (re.getX() < -100) {
                    rect.remove(re);
                }

            }
        } catch (ConcurrentModificationException e) {
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }
}
