package com.adventofcode.year2024;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day01Test {

    @Test
    void inputExample() {
        String input = """
                3   4
                4   3
                2   5
                1   3
                3   9
                3   3""";

        try (Scanner scanner = new Scanner(input)) {
            int diff = Day01.partOne(scanner);
            assertThat(diff).isEqualTo(11);
        }

        try (Scanner scanner = new Scanner(input)) {
            long score = Day01.partTwo(scanner);

            assertThat(score).isEqualTo(31);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day01Test.class.getResourceAsStream("/2024/day/01/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day01.partOne(scanner)).isEqualTo(1941353);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day01Test.class.getResourceAsStream("/2024/day/01/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day01.partTwo(scanner)).isEqualTo(1941353);
        }
    }


}
