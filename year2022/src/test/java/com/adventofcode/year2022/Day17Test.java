package com.adventofcode.year2022;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day17Test {

    @Test
    void testExample() {
        String input = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";

        assertThat(Day17.pyroclasticFlow(input, 10, true)).isEqualTo(17);

        assertThat(Day17.pyroclasticFlow(input, 2022, false)).isEqualTo(3068);
        assertThat(Day17.pyroclasticFlow(input, 1_000_000_000_000L, false)).isEqualTo(1514285714288L);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2022/day/17/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day17.pyroclasticFlow(scanner.nextLine(), 2022, false)).isEqualTo(3227);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2022/day/17/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day17.pyroclasticFlow(scanner.nextLine(), 1_000_000_000_000L, false)).isEqualTo(1597714285698L);
        }
    }

}
