package com.adventofcode.year2024;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day07Test {

    @Test
    void inputExample() {
        String input = """
                190: 10 19
                3267: 81 40 27
                83: 17 5
                156: 15 6
                7290: 6 8 6 15
                161011: 16 10 13
                192: 17 8 14
                21037: 9 7 18 13
                292: 11 6 16 20""";

        try (Scanner scanner = new Scanner(input)) {
            long result = Day07.partOne(scanner);
            assertThat(result).isEqualTo(3749);
        }

        try (Scanner scanner = new Scanner(input)) {
            long result = Day07.partTwo(scanner);
            assertThat(result).isEqualTo(11387L);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day07Test.class.getResourceAsStream("/2024/day/07/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day07.partOne(scanner)).isEqualTo(3351424677624L);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day07Test.class.getResourceAsStream("/2024/day/07/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day07.partTwo(scanner)).isEqualTo(204976636995111L);
        }
    }

}
