package com.company;

import java.util.Random;

public class CapitalFactory implements AbstractFactory {

    @Override
    public House Generate(int P, double LT) {
        if (func(P) == 1) {
            double DT = Math.random()*5+5;
            return new CapitalHouse(LT, DT);
        } else
            return null;
    }

    private static int func(int P) {
        if (Math.random()*100 < P) {
            return 1;
        } else return 2;

    }
}

