package com.adventofcode.common.utils;

import com.google.errorprone.annotations.Immutable;

@Immutable
public record LongPair(long left, long right) {
    public static LongPair of(long left, long right) {
        return new LongPair(left, right);
    }

    @Override
    public String toString() {
        return "(" + this.left() + ',' + this.right() + ')';
    }
}
