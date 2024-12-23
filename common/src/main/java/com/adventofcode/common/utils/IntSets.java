package com.adventofcode.common.utils;

import java.util.BitSet;

public final class IntSets {

    private IntSets() {
        // No-Op
    }

    public static BitSet of(final int... a) {
        BitSet set = new BitSet();
        for (int i : a) {
            set.set(i);
        }
        return set;
    }

    public static BitSet copy(BitSet a) {
        return (BitSet) a.clone();
    }

    public static BitSet intersection(BitSet a, BitSet b) {
        BitSet copy = copy(a);
        copy.and(b);
        return copy;
    }

    public static BitSet union(BitSet a, BitSet b) {
        BitSet copy = copy(a);
        copy.or(b);
        return copy;
    }

    public static BitSet union(BitSet a, int b) {
        BitSet copy = copy(a);
        copy.set(b);
        return copy;
    }

    public static BitSet empty() {
        return new BitSet();
    }

    public static BitSet range(int a, int b) {
        BitSet set = new BitSet();
        set.set(a, b);
        return set;
    }

}
