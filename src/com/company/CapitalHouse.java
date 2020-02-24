package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class CapitalHouse extends House {
    private static Image img;
    public static int count;
    CapitalHouse() {
        x = 0;
        y = 0;
//        x = r.nextInt(900 - img.getWidth(null));
//        y = r.nextInt(600 - img.getHeight(null));
    }

    CapitalHouse(int x, int y) {
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
            img = ImageIO.read(new File("src/pictures/capital/1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    public void Draw(Graphics g) {
        g.drawImage(img,x,y,null);
    }

    public int GetHeight() {
        return img.getHeight(null);
    }


}
