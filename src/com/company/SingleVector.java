package com.company;

import java.util.Vector;

public class SingleVector {
    private static SingleVector singleVector;
    private static Vector<House> vector = new Vector<House>();

    public static SingleVector getSingleVector() {
        if (singleVector == null)
            singleVector = new SingleVector();
        return singleVector;
    }

    public static void add(House house){
        vector.add(house);
    }

    public static Vector<House> getVector(){
        return vector;
    }
}
