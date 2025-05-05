package com.adventofcode.common.memory;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;

public class ObjectMemory<V> implements Memory<V> {
    private final BitSet bitSet;
    private Object[] mem;

    public ObjectMemory() {
        this(10);
    }

    public ObjectMemory(int capacity) {
        mem = new Object[capacity];
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

    private void checkKey(int key) {
        if (key < 0) {
            throw new IllegalStateException("Negative key are not allowed: " + key);
        }
    }

    @Override
    public boolean containsKey(int key) {
        checkKey(key);

        return key < mem.length && bitSet.get(key);
    }

    @Override
    @Nullable
    @SuppressWarnings("unchecked")
    public V get(int key) {
        checkKey(key);

        if (key < mem.length && bitSet.get(key)) {
            return (V) mem[key];
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Nonnull
    public V getNonNull(int key) {
        checkKey(key);

        if (key < mem.length && bitSet.get(key)) {
            return (V) mem[key];
        }

        throw new IllegalStateException("Cannot find key: " + key);
    }

    @Override
    @Nullable
    public V put(int key, V value) {
        checkKey(key);

        if (key >= mem.length) {
            grow(key);
        }

        V last = get(key);
        mem[key] = value;
        bitSet.set(key);
        return last;
    }

    @Override
    public void clear() {
        bitSet.clear();
        mem = new Object[10];
    }

    @Override
    public int[] keySet() {
        return bitSet.stream().toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<V> values() {
        return bitSet.stream().mapToObj(i -> (V) mem[i]).toList();

    }

    private void grow(int minCapacity) {
        int oldLength = mem.length;
        int newLength = Math.max(oldLength * 2, minCapacity + 1);

        mem = Arrays.copyOf(mem, newLength);
    }
}
