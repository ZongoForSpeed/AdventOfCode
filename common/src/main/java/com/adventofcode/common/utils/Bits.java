package com.adventofcode.common.utils;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

import java.util.stream.IntStream;

public final class Bits {
    private Bits() {
        // No-Op
    }

    public static IntList convertBitSet(long bitset) {
        IntList chips = new IntArrayList();
        for (long remaining = bitset; remaining != 0; remaining &= remaining - 1) {
            chips.add(Long.numberOfTrailingZeros(remaining));
        }
        return chips;
    }

    public static long toBitSet(IntList list) {
        return list.intStream().mapToLong(i -> 1L << i).reduce(0L, (a, b) -> a | b);
    }

    public static long toBitSet(IntStream stream) {
        return stream.mapToLong(i -> 1L << i).reduce(0L, (a, b) -> a | b);
    }

    public static boolean contains(long bitset, int value) {
        long mask = 1L << value;
        return (bitset & mask) == mask;
    }

    public static long add(long bitset, int value) {
        long mask = 1L << value;
        return bitset | mask;
    }

    public static long remove(long bitset, int value) {
        long mask = 1L << value;
        return bitset & ~mask;
    }

}
