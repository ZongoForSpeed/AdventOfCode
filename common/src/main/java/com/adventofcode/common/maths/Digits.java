package com.adventofcode.common.maths;

import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharList;

import java.util.Collections;

public final class Digits {

    private Digits() {
        // No-Op
    }

    public static CharList digits(int n) {
        checkNonNegative(n);
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

    public static CharList digits(long n) {
        checkNonNegative(n);
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

    public static int numberDigits(long n) {
        checkNonNegative(n);
        var d = 1;
        while (n >= 10) {
            n /= 10;
            ++d;
        }

        return d;
    }

    private static void checkNonNegative(long n) {
        if (n < 0) {
            throw new IllegalArgumentException("Negative numbers are not supported: " + n);
        }
    }
}
