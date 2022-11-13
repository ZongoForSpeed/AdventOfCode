package com.adventofcode.maths;

import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.util.Collections;

public final class Digits {
    private Digits() {
        // No-Op
    }

    public static CharList digits(int n) {
        if (n == 0) {
            return CharList.of((char) 0);
        }
        CharList d = new CharArrayList();
        while (n > 0) {
            d.add((char) (n % 10));
            n /= 10;
        }

        Collections.reverse(d);
        return d;
    }
}
