package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 20)
class Day20Test {

    @Test
    void inputExample() {
        var input = """
                ###############
                #...#...#.....#
                #.#.#.#.#.###.#
                #S#...#.#.#...#
                #######.#.#.###
                #######.#.#...#
                #######.#.###.#
                ###..E#...#...#
                ###.#######.###
                #...###...#...#
                #.#####.#.###.#
                #.#...#.#.#...#
                #.#.#.#.#.#.###
                #...#...#...###
                ###############""";

        try (var scanner = new Scanner(input)) {
            int countCheats = Day20.partOne(scanner, 20);
            assertThat(countCheats).isEqualTo(5);
        }

        try (var scanner = new Scanner(input)) {
            int countCheats = Day20.partTwo(scanner, 50);
            assertThat(countCheats).isEqualTo(285);
        }
    }


    @Test
    void partOne(Scanner scanner) throws Exception {
        int countCheats = Day20.partOne(scanner, 100);
        assertThat(countCheats).isEqualTo(1502);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        int countCheats = Day20.partTwo(scanner, 100);
        assertThat(countCheats).isEqualTo(1028136);
    }
}
