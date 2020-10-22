/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package runnergame;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author kians
 */
public class Obstacle {

    private Image image;
    private int x = 800;
    private int y;
    private final int w = 30;
    private final int h;

    public Obstacle(int heigth) {
        this.h = heigth;
        loadImage();
    }

    private void loadImage() {

//        ImageIcon ii = new ImageIcon("src/resources/Player_1.png");
//        image = ii.getImage();
//
//        w = image.getWidth(null);
//        h = image.getHeight(null);
    }

    public void move() {
        x -= 5;
    }

    public int getX() {

        return x;
    }

    public float getY() {
        y = 350 - h;
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
}
