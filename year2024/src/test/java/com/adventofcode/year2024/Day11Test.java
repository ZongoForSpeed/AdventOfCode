package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 11)
class Day11Test {

    @Test
    void example1() {
        var input = "0 1 10 99 999";

        try (var scanner = new Scanner(input)) {
            long result = Day11.plutonianPebbles(scanner, 1);
            assertThat(result).isEqualTo(7);
        }
    }

    @Test
    void example2() {
        var input = "125 17";

        try (var scanner = new Scanner(input)) {
            long result = Day11.plutonianPebbles(scanner, 6);
            assertThat(result).isEqualTo(22L);
        }

        try (var scanner = new Scanner(input)) {
            long result = Day11.plutonianPebbles(scanner, 25);
            assertThat(result).isEqualTo(55312L);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day11.plutonianPebbles(scanner, 25)).isEqualTo(185205L);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day11.plutonianPebbles(scanner, 75)).isEqualTo(221280540398419L);

    }
}
