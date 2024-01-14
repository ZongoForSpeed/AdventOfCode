package com.adventofcode.common.utils;

@FunctionalInterface
public interface TriConsumer<K, V, S> {
    void accept(K k, V v, S s);
}

