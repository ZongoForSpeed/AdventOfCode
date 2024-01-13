package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day18Test {

    @Test
    void example() {
        String input = """
                .#.#...|#.
                .....#|##|
                .|..|...#.
                ..|#.....#
                #.#|||#|#|
                ...#.||...
                .|....|...
                ||...#|.#|
                |.||||..|.
                ...#.|..|.""";

        long value = Day18.treesAndLumberyards(new Scanner(input), 10);
        assertThat(value).isEqualTo(1147);

    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day16Test.class.getResourceAsStream("/2018/day/18/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {

            long value = Day18.treesAndLumberyards(scanner, 10);
            assertThat(value).isEqualTo(519552);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day16Test.class.getResourceAsStream("/2018/day/18/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {

            long value = Day18.treesAndLumberyards(scanner, 1_000_000_000);
            assertThat(value).isEqualTo(165376);
        }
    }

}
