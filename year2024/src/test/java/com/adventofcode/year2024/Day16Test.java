package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day16Test extends AbstractTest {

    Day16Test() {
        super(2024, 16);
    }

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
            var score = Day16.partOne(scanner);
            assertThat(score).isEqualTo(7036);
        }

        try (var scanner = new Scanner(input)) {
            var score = Day16.partTwo(scanner);
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
            var score = Day16.partOne(scanner);
            assertThat(score).isEqualTo(11048);
        }

        try (var scanner = new Scanner(input)) {
            var score = Day16.partTwo(scanner);
            assertThat(score).isEqualTo(64);
        }
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        var score = Day16.partOne(scanner);
        assertThat(score).isEqualTo(72400);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        var score = Day16.partTwo(scanner);
        assertThat(score).isEqualTo(435);
    }

}
