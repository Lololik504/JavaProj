package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class CapitalHouse extends House {
    private static Image img;
    private static int count;
    CapitalHouse() {
        x = 0;
        y = 0;
    }

    static{
        SetImage();
    }

    public static void incrementCount(){
        count++;
    }

    static public void SetImage (){
        try {
            img = ImageIO.read(new File("src/pictures/capital/1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getCount(){
        return count;
    }

    CapitalHouse(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static void clearCount(){
        count = 0;
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
