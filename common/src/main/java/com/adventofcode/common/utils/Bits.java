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
        for (int id = 0; id < Long.SIZE; ++id) {
            long mask = 1L << id;
            if (mask > bitset) {
                break;
            }
            if ((mask & bitset) == mask) {
                chips.add(id);
            }
        }
        return chips;
    }

    public static long toBitSet(IntList list) {
        return list.intStream().mapToLong(i -> 1L << i).sum();
    }

    public static long toBitSet(IntStream stream) {
        return stream.mapToLong(i -> 1L << i).sum();
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
        return (bitset | mask) - mask;
    }

}
