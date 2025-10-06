package com.adventofcode.common.memory;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import jakarta.annotation.Nullable;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.StringJoiner;

public class LongMemory implements Memory<Long> {
    private final BitSet bitSet;
    private long[] mem;

    public LongMemory(int capacity) {
        mem = new long[capacity];
        bitSet = new BitSet(10);
    }

    @Override
    public int size() {
        return bitSet.cardinality();
    }

    @Override
    public boolean isEmpty() {
        return bitSet.isEmpty();
    }

    @Override
    public boolean containsKey(int key) {
        checkKey(key);

        return key < mem.length && bitSet.get(key);
    }

    private void checkKey(int key) {
        if (key < 0) {
            throw new IllegalStateException("Negative key are not allowed: " + key);
        }
    }

    @Override
    @Nullable
    public Long get(int key) {
        checkKey(key);

        if (key < mem.length && bitSet.get(key)) {
            return mem[key];
        }

        return null;
    }

    @Override
    @Nullable
    public Long put(int key, Long value) {
        if (value == null) {
            throw new IllegalStateException("Null value are not allowed");
        }
        return put(key, value.longValue());
    }

    @Override
    public void clear() {
        bitSet.clear();
        mem = new long[10];
    }

    @Override
    public int[] keySet() {
        return bitSet.stream().toArray();
    }

    @Override
    public Collection<Long> values() {
        return bitSet.stream().mapToLong(i -> mem[i]).boxed().toList();
    }

    @Nullable
    @CanIgnoreReturnValue
    public Long put(int key, long value) {
        checkKey(key);

        if (key >= mem.length) {
            grow(key);
        }

        Long last = get(key);
        mem[key] = value;
        bitSet.set(key);
        return last;
    }

    private void grow(int minCapacity) {
        int oldLength = mem.length;
        int newLength = Math.max(oldLength * 2, minCapacity + 1);

        mem = Arrays.copyOf(mem, newLength);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("), (", "(", ")");
        for (int i : keySet()) {
            joiner.add(i + " -> " + get(i));
        }

        return "{ " + joiner + " }";
    }
}
