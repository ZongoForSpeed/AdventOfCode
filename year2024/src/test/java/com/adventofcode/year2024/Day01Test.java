package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 1)
class Day01Test {

    @Test
    void inputExample() {
        var input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3""";

        try (var scanner = new Scanner(input)) {
            int diff = Day01.partOne(scanner);
            assertThat(diff).isEqualTo(11);
        }

        try (var scanner = new Scanner(input)) {
            long score = Day01.partTwo(scanner);

            assertThat(score).isEqualTo(31);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day01.partOne(scanner)).isEqualTo(1941353);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day01.partTwo(scanner)).isEqualTo(22539317L);
    }
}
