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

    @Override
    public void partOne(Scanner scanner) {
        int cardinality = Day23.PartOne.longestPathLength(scanner);
        Assertions.assertThat(cardinality).isEqualTo(2070);
    }

    @Override
    public void partTwo(Scanner scanner) {
        int cardinality = Day23.PartTwo.longestPathLength(scanner);
        Assertions.assertThat(cardinality).isEqualTo(6498);
    }

}
