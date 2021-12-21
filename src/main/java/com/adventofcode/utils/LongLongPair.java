package com.adventofcode.utils;

public record LongLongPair(long left, long right) {
    public static LongLongPair of(long left, long right) {
        return new LongLongPair(left, right);
    }
}
