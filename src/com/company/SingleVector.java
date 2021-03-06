package com.company;

import java.awt.*;
import java.util.Vector;

public class SingleVector {
    private static SingleVector singleVector;
    private static Vector<House> vector = new Vector<>();

    private SingleVector() {
    }

    public static synchronized SingleVector getSingleVector() {
        if (singleVector == null)
            singleVector = new SingleVector();
        return singleVector;
    }

    public void RIP(Habitat habit) {
        try {
            for (House house : vector) {
                float BT = house.GetBornTime();
                float DT = house.GetDeathTime();
                double WT = habit.getWORK_TIME();
                if (BT + DT < WT) {
                    vector.remove(house);
                    habit.intSet.remove(house.GetId());
                    habit.BornTimeTree.remove(house.GetId());
                    habit.UpdateHashSet();
                    if (house instanceof CapitalHouse)
                        CapitalHouse.decrementCount();
                    else WoodenHouse.decrementCount();
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getStackTrace());
        }

    }

    public void add(House house) {
        vector.addElement(house);
    }

    public void clear() {
        vector.removeAllElements();
    }

    public void drawHouses(Graphics g) {
        if (vector != null) {
            for (House house : vector) {
                house.Draw(g);
            }
        }
    }
}
