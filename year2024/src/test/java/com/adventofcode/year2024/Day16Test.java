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
        String input = """
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

        try (Scanner scanner = new Scanner(input)) {
            long score = Day16.partOne(scanner);
            assertThat(score).isEqualTo(7036);
        }

        try (Scanner scanner = new Scanner(input)) {
            long score = Day16.partTwo(scanner);
            assertThat(score).isEqualTo(45);
        }
    }

    @Test
    void inputExample2() {
        String input = """
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

        try (Scanner scanner = new Scanner(input)) {
            long score = Day16.partOne(scanner);
            assertThat(score).isEqualTo(11048);
        }

        try (Scanner scanner = new Scanner(input)) {
            long score = Day16.partTwo(scanner);
            assertThat(score).isEqualTo(64);
        }
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        long score = Day16.partOne(scanner);
        assertThat(score).isEqualTo(72400);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        long score = Day16.partTwo(scanner);
        assertThat(score).isEqualTo(435);
    }

}
