package com.company;

import java.util.Random;

public class ConcreteFactory implements AbstractFactory {

    @Override
    public House Generate(int P) {
        if (func(P) == 1) {
            return new WoodenHouse();
        } else
            return null;
    }

    private static int func(int P) {
        Random r = new Random();
        int i = r.nextInt(100);
        if (i < P) {
            return 1;
        } else if (i >= P && i < 100) {
            return 2;
        } else return 0;
    }
}

