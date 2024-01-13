package com.adventofcode.utils;

public record IntegerPair(int left, int right) {
    public static IntegerPair of(int left, int right) {
        return new IntegerPair(left, right);
    }

    @Override
    public String toString() {
        return "(" + this.left() + ',' + this.right() + ')';
    }
}
