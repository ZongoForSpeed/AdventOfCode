package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day12Test {
    @Test
    void inputExample() {
        String input = """
                initial state: #..#.#..##......###...###
                               
                ...## => #
                ..#.. => #
                .#... => #
                .#.#. => #
                .#.## => #
                .##.. => #
                .#### => #
                #.#.# => #
                #.### => #
                ##.#. => #
                ##.## => #
                ###.. => #
                ###.# => #
                ####. => #""";
        Scanner scanner = new Scanner(input);

        assertThat(Day12.getPlants(scanner, 20)).isEqualTo(325);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day12Test.class.getResourceAsStream("/2018/day/12/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day12.getPlants(scanner, 20)).isEqualTo(2571);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day12Test.class.getResourceAsStream("/2018/day/12/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day12.getPlants(scanner, 50000000000L)).isEqualTo(3100000000655L);
        }
    }
}
