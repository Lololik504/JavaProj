package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class CapitalHouse extends House {
    private static BufferedImage img;
    public static int count;
    CapitalHouse() {
        x = 0;
        y = 0;
    }

    static public void SetImage (){
        try {
            img = ImageIO.read(new File("src/pictures/capital/1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    CapitalHouse(int x, int y) {
        this.x = x;
        this.y = y;
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
