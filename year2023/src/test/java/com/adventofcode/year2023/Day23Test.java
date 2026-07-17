package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day23Test extends AbstractTest {
    Day23Test() {
        super(2023, 23);
    }

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
            var cardinality = Day23.PartOne.longestPathLength(scanner);
            Assertions.assertThat(cardinality).isEqualTo(94);
        }

        {
            var scanner = new Scanner(input);
            var cardinality = Day23.PartTwo.longestPathLength(scanner);
            Assertions.assertThat(cardinality).isEqualTo(154);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        var cardinality = Day23.PartOne.longestPathLength(scanner);
        Assertions.assertThat(cardinality).isEqualTo(2070);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var cardinality = Day23.PartTwo.longestPathLength(scanner);
        Assertions.assertThat(cardinality).isEqualTo(6498);
    }

}
