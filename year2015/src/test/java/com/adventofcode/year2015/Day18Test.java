package com.adventofcode.year2015;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;


class Day18Test {

    @Test
    void inputExample() {
        String input = """
                .#.#.#
                ...##.
                #....#
                ..#...
                #.#..#
                ####..""";

        Assertions.assertThat(Day18.getLightsPartOne(new Scanner(input), 4, 6, 6)).isEqualTo(4);
        Assertions.assertThat(Day18.getLightsPartTwo(new Scanner(input), 5, 6, 6)).isEqualTo(17);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day18Test.class.getResourceAsStream("/2015/day/18/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            Assertions.assertThat(Day18.getLightsPartOne(scanner, 100, 100, 100)).isEqualTo(768);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day18Test.class.getResourceAsStream("/2015/day/18/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            Assertions.assertThat(Day18.getLightsPartTwo(scanner, 100, 100, 100)).isEqualTo(781);
        }
    }
}
