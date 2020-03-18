package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

abstract public class House implements IBehaviour {
    protected static int TotalID = 0;
    protected float bornTime;
    protected float deathTime;
    protected int x;
    protected int y;

    public abstract int GetId();
    public abstract float GetBornTime();
    public abstract float GetDeathTime();
    public static void resetCount(){TotalID = 0;};
    abstract public int GetHeight();
    abstract public int GetWidth();
    abstract public void SetX(int x);
    abstract public void SetY(int y);
    abstract public void Draw(Graphics g);
}
