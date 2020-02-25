package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

abstract public class House implements IBehaviour {
    protected int x;
    protected int y;

    abstract public int GetHeight();
    abstract public int GetWidth();
    abstract public void SetX(int x);
    abstract public void SetY(int y);
    abstract public void Draw(Graphics g);
}
