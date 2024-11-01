package com.adventofcode.year2023;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Scanner;

class Day23Test {

    @Test
    void inputExample() {
        String input = """
                #.#####################
                #.......#########...###
                #######.#########.#.###
                ###.....#.>.>.###.#.###
                ###v#####.#v#.###.#.###
                ###.>...#.#.#.....#...#
                ###v###.#.#.#########.#
                ###...#.#.#.......#...#
                #####.#.#.#######.#.###
                #.....#.#.#.......#...#
                #.#####.#.#.#########v#
                #.#...#...#...###...>.#
                #.#.#v#######v###.###v#
                #...#.>.#...>.>.#.###.#
                #####v#.#.###v#.#.###.#
                #.....#...#...#.#.#...#
                #.#########.###.#.#.###
                #...###...#...#...#.###
                ###.###.#.###v#####v###
                #...#...#.#.>.>.#.>.###
                #.###.###.#.###.#.#v###
                #.....###...###...#...#
                #####################.#""";

        {
            Scanner scanner = new Scanner(input);
            int cardinality = Day23.PartOne.longestPathLength(scanner);
            Assertions.assertThat(cardinality).isEqualTo(94);
        }

        {
            Scanner scanner = new Scanner(input);
            int cardinality = Day23.PartTwo.longestPathLength(scanner);
            Assertions.assertThat(cardinality).isEqualTo(154);
        }
    }


    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day23Test.class.getResourceAsStream("/2023/day/23/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            int cardinality = Day23.PartOne.longestPathLength(scanner);
            Assertions.assertThat(cardinality).isEqualTo(2070);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day23Test.class.getResourceAsStream("/2023/day/23/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream), StandardCharsets.UTF_8)) {
            int cardinality = Day23.PartTwo.longestPathLength(scanner);
            Assertions.assertThat(cardinality).isEqualTo(6498);
        }
    }

}
