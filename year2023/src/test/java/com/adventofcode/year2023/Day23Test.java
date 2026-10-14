package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 23)
class Day23Test {
    @Test
    void inputExample() {
        var input = """
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
            var scanner = new Scanner(input);
            int cardinality = Day23.PartOne.longestPathLength(scanner);
            Assertions.assertThat(cardinality).isEqualTo(94);
        }

        {
            var scanner = new Scanner(input);
            int cardinality = Day23.PartTwo.longestPathLength(scanner);
            Assertions.assertThat(cardinality).isEqualTo(154);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        int cardinality = Day23.PartOne.longestPathLength(scanner);
        Assertions.assertThat(cardinality).isEqualTo(2070);
    }

    @Test
    void partTwo(Scanner scanner) {
        int cardinality = Day23.PartTwo.longestPathLength(scanner);
        Assertions.assertThat(cardinality).isEqualTo(6498);
    }

}
