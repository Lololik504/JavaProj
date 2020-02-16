package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

abstract public class House extends JComponent implements IBehaviour {
    protected int x;
    protected int y;
    protected Image img;

    public static void SetImage(){};
    abstract public int GetHeight();
    abstract public int GetWidth();
    abstract public void SetX(int x);
    abstract public void SetY(int y);
}
