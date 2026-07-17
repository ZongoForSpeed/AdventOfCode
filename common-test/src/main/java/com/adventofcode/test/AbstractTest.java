package com.adventofcode.test;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

public abstract class AbstractTest {

    private final int year;
    private final int day;

    protected AbstractTest(int year, int day) {
        this.year = year;
        this.day = day;
    }

    @Test
    void inputPartOne() throws Exception {
        try (InputStream is = AbstractTest.class.getResourceAsStream("/%4d/day/%02d/input".formatted(year, day));
             var scanner = new Scanner(Objects.requireNonNull(is), UTF_8)) {
            partOne(scanner);
        }
    }

    @Test
    void inputPartTwo() throws Exception {
        try (InputStream is = AbstractTest.class.getResourceAsStream("/%4d/day/%02d/input".formatted(year, day));
             var scanner = new Scanner(Objects.requireNonNull(is), UTF_8)) {
            partTwo(scanner);
        }
    }

    public abstract void partOne(Scanner scanner) throws Exception;

    public abstract void partTwo(Scanner scanner) throws Exception;
}
