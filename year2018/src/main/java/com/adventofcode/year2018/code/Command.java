package com.adventofcode.year2018.code;

public record Command(String code, int a, int b, int c) {
    public static Command of(String code, String a, String b, String c) {
        return new Command(code, Integer.parseInt(a), Integer.parseInt(b), Integer.parseInt(c));
    }
}
