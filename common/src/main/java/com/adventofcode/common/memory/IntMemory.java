package com.adventofcode.common.memory;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.StringJoiner;

public class IntMemory implements Memory<Integer> {
    private final BitSet bitSet;
    private int[] mem;

    public IntMemory(int capacity) {
        mem = new int[capacity];
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

    public Integer get(int key) {
        checkKey(key);

        if (key < mem.length && bitSet.get(key)) {
            return mem[key];
        }

        return null;
    }

    @Override
    public Integer put(int key, Integer value) {
        if (value == null) {
            throw new IllegalStateException("Null value are not allowed");
        }
        return put(key, value.intValue());
    }

    @Override
    public void clear() {
        bitSet.clear();
        mem = new int[10];
    }

    @Override
    public int[] keySet() {
        return bitSet.stream().toArray();
    }

    @Override
    public Collection<Integer> values() {
        return bitSet.stream().map(i -> mem[i]).boxed().toList();
    }

    public Integer put(int key, int value) {
        checkKey(key);

        if (key >= mem.length) {
            grow(key);
        }

        Integer last = get(key);
        mem[key] = value;
        bitSet.set(key);
        return last;
    }

    public Integer increment(int key, int value) {
        checkKey(key);

        if (key >= mem.length) {
            grow(key);
        }

        Integer last = get(key);
        mem[key] += value;
        bitSet.set(key);
        return last;
    }

    private void grow(int minCapacity) {
        int oldLength = mem.length;
        int newLength = Math.max(oldLength * 2, minCapacity + 1);

        mem = Arrays.copyOf(mem, newLength);
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner("), (", "(", ")");
        for (int i : keySet()) {
            joiner.add(i + " -> " + get(i));
        }

        return "{ " + joiner + " }";
    }
}
