package com.company;

import java.awt.*;
import java.util.Vector;

public class SingleVector {
    private static SingleVector singleVector;
    private static Vector<House> vector = new Vector<>();

    private SingleVector(){}

    public static synchronized SingleVector getSingleVector() {
        if (singleVector == null)
            singleVector = new SingleVector();
        return singleVector;
    }

    public void add(House house){
        vector.addElement(house);
    }

    public void clear(){
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
