package com.adventofcode.utils;

public record CharPair(char left, char right){
    public static CharPair of(char left, char right) {
        return new CharPair(left, right);
    }

    @Override
    public String toString() {
        return "(" + this.left() + ',' + this.right() + ')';
    }
}
