package com.adventofcode.year2024;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day02Test {

    @Test
    void inputExample() {
        String input = """
                7 6 4 2 1
                1 2 7 8 9
                9 7 6 2 1
                1 3 2 4 5
                8 6 4 4 1
                1 3 6 7 9""";

        try (Scanner scanner = new Scanner(input)) {
            int count = Day02.partOne(scanner);

            assertThat(count).isEqualTo(2);
        }

        try (Scanner scanner = new Scanner(input)) {
            int count = Day02.partTwo(scanner);

            assertThat(count).isEqualTo(4);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day02Test.class.getResourceAsStream("/2024/day/02/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day02.partOne(scanner)).isEqualTo(359);
        }
    }


    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day02Test.class.getResourceAsStream("/2024/day/02/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day02.partTwo(scanner)).isEqualTo(418);
        }
    }

}
