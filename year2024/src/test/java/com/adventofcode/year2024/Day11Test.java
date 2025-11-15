package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day11Test extends AbstractTest {

    protected Day11Test() {
        super(2024, 11);
    }

    @Test
    void example1() {
        String input = "0 1 10 99 999";

        try (Scanner scanner = new Scanner(input)) {
            long result = Day11.plutonianPebbles(scanner, 1);
            assertThat(result).isEqualTo(7);
        }
    }

    @Test
    void example2() {
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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day11.plutonianPebbles(scanner, 25)).isEqualTo(185205L);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day11.plutonianPebbles(scanner, 75)).isEqualTo(221280540398419L);

    }
}
