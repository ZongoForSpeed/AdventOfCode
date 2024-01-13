package com.adventofcode.utils;

public record BooleanPair(boolean left, boolean right) {
    public static BooleanPair of(boolean left, boolean right) {
        return new BooleanPair(left, right);
    }
}
