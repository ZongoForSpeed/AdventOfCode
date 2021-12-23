package com.adventofcode.maths;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.util.Collections;

public final class Digits {
    private Digits() {
        // No-Op
    }

    public static IntList digits(int n) {
        IntList d = new IntArrayList();
        while (n > 0) {
            d.add(n % 10);
            n /= 10;
        }

        Collections.reverse(d);
        return d;
    }
}
