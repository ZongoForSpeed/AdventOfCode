package com.adventofcode.year2024;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test {

    @Test
    void testExample1() {
        String input = "0 1 10 99 999";

        try (Scanner scanner = new Scanner(input)) {
            long result = Day11.plutonianPebbles(scanner, 1);
            assertThat(result).isEqualTo(7);
        }
    }

    @Test
    void testExample2() {
        String input = "125 17";

        try (Scanner scanner = new Scanner(input)) {
            long result = Day11.plutonianPebbles(scanner, 6);
            assertThat(result).isEqualTo(22L);
        }

        try (Scanner scanner = new Scanner(input)) {
            long result = Day11.plutonianPebbles(scanner, 25);
            assertThat(result).isEqualTo(55312L);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day10Test.class.getResourceAsStream("/2024/day/11/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day11.plutonianPebbles(scanner, 25)).isEqualTo(185205L);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day10Test.class.getResourceAsStream("/2024/day/11/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day11.plutonianPebbles(scanner, 75)).isEqualTo(221280540398419L);
        }
    }
}
