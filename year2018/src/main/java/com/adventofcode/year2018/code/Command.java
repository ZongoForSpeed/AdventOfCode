package com.adventofcode.year2018.code;

public record Command(String code, int a, int b, int c) {
    public static Command of(String code, int a, int b, int c) {
        return new Command(code, a, b, c);
    }
}
