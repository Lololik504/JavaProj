package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class WoodenHouse extends House {
    private static Image img;
    public static int count;
    WoodenHouse() {
        x = 0;
        y = 0;
    }

    WoodenHouse(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, x, y, null);
    }

    public static void SetImage() {
        try {
            img = ImageIO.read(new File("src/pictures/wooden/1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override

    public int GetWidth() {
        return img.getWidth(null);
    }

    @Override
    public void SetX(int x) {
        this.x = x;
    }

    @Override
    public void SetY(int y) {
        this.y = y;
    }

    @Override
    public int GetHeight() {
        return img.getHeight(null);
    }
}
