package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class CapitalHouse extends House {
    private int id;
    private static Image img;
    private static int count;
    CapitalHouse() {
        x = 0;
        y = 0;
    }

    static{
        SetImage();
    }

    CapitalHouse(double LT, double DT) {
        TotalID++;
        id = TotalID;
        this.bornTime = (float)LT;
        this.deathTime = (float)DT;
    }

    public static void incrementCount(){
        count++;
    }
    public static void decrementCount(){
        count--;
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

    @Override
    public int GetId() {
        return id;
    }

    @Override
    public float GetBornTime() {
        return bornTime;
    }

    @Override
    public float GetDeathTime() {
        return deathTime;
    }
}
