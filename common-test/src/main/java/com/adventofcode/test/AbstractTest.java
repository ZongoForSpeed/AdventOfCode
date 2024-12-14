package com.adventofcode.test;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

public abstract class AbstractTest {

    private final int year;
    private final int day;

    protected AbstractTest(int year, int day) {
        this.year = year;
        this.day = day;
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = AbstractTest.class.getResourceAsStream("/%4d/day/%02d/input".formatted(year, day));
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            partOne(scanner);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = AbstractTest.class.getResourceAsStream("/%4d/day/%02d/input".formatted(year, day));
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            partTwo(scanner);
        }
    }

    public abstract void partOne(Scanner scanner);

    public abstract void partTwo(Scanner scanner);
}
