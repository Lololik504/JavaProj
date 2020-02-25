package com.company;

import java.util.Random;

public class CapitalFactory implements AbstractFactory {

    @Override
    public House Generate(int P) {
        if (func(P) == 1) {
            return new CapitalHouse();
        } else
            return null;
    }

    private static int func(int P) {
        if (Math.random()*100 < P) {
            return 1;
        } else return 2;

    }
}

