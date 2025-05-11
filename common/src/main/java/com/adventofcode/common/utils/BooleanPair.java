package com.adventofcode.common.utils;

import com.google.errorprone.annotations.Immutable;

@Immutable
public record BooleanPair(boolean left, boolean right) {
    public static BooleanPair of(boolean left, boolean right) {
        return new BooleanPair(left, right);
    }
}
