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
import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Player {

    private final float x = 200;
    private float y = 300;
    private float vy = -15.0f;
    private final float g = 0.5f;
    private int w;
    private int h;
    private Image image;
    public boolean jump = false;
    public boolean gameOver = false;

    public Player() {

        loadImage();
    }

    private void loadImage() {

        ImageIcon ii = new ImageIcon("src/resources/Smettcat.png");
        image = ii.getImage();

        w = image.getWidth(null);
        h = image.getHeight(null);
    }

    public void move() {
        if (jump && y <= 300) {
            y += vy;
            vy += g;
        }
        if (y == 300) {
            jump = false;
            vy = -15.0f;
        }
    }

    public float getX() {

        return x;
    }

    public float getY() {

        return y;
    }

    public int getWidth() {

        return w;
    }

    public int getHeight() {

        return h;
    }

    public Image getImage() {

        return image;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            if (gameOver) {
                gameOver = false;
            } else if (y >= 300) {
                jump = true;
            }

        }

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {

        }

    }
}
