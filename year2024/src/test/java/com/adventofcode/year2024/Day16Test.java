package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 16)
class Day16Test {

    @Test
    void inputExample1() {
        var input = """
                ###############
                #.......#....E#
                #.#.###.#.###.#
                #.....#.#...#.#
                #.###.#####.#.#
                #.#.#.......#.#
                #.#.#####.###.#
                #...........#.#
                ###.#.#####.#.#
                #...#.....#.#.#
                #.#.#.###.#.#.#
                #.....#...#.#.#
                #.###.#.#.#.#.#
                #S..#.....#...#
                ###############""";

        try (var scanner = new Scanner(input)) {
            long score = Day16.partOne(scanner);
            assertThat(score).isEqualTo(7036);
        }

        try (var scanner = new Scanner(input)) {
            long score = Day16.partTwo(scanner);
            assertThat(score).isEqualTo(45);
        }
    }

    @Test
    void inputExample2() {
        var input = """
                #################
                #...#...#...#..E#
                #.#.#.#.#.#.#.#.#
                #.#.#.#...#...#.#
                #.#.#.#.###.#.#.#
                #...#.#.#.....#.#
                #.#.#.#.#.#####.#
                #.#...#.#.#.....#
                #.#.#####.#.###.#
                #.#.#.......#...#
                #.#.###.#####.###
                #.#.#...#.....#.#
                #.#.#.#####.###.#
                #.#.#.........#.#
                #.#.#.#########.#
                #S#.............#
                #################""";

        try (var scanner = new Scanner(input)) {
            long score = Day16.partOne(scanner);
            assertThat(score).isEqualTo(11048);
        }

        try (var scanner = new Scanner(input)) {
            long score = Day16.partTwo(scanner);
            assertThat(score).isEqualTo(64);
        }
    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        long score = Day16.partOne(scanner);
        assertThat(score).isEqualTo(72400);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        long score = Day16.partTwo(scanner);
        assertThat(score).isEqualTo(435);
    }

}
