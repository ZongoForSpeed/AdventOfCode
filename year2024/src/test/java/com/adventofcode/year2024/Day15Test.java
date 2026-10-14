package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 15)
class Day15Test {

    @Test
    void example1() {
        var input = """
                ########
                #..O.O.#
                ##@.O..#
                #...O..#
                #.#.O..#
                #...O..#
                #......#
                ########
                
                <^^>>>vv<v>>v<<""";

        try (var scanner = new Scanner(input)) {
            int gps = Day15.partOne(scanner, true);
            assertThat(gps).isEqualTo(2028);
        }

        try (var scanner = new Scanner(input)) {
            int gps = Day15.partTwo(scanner, true);
            assertThat(gps).isEqualTo(1751);
        }
    }


    @Test
    void example2() {
        var input = """
                ##########
                #..O..O.O#
                #......O.#
                #.OO..O.O#
                #..O@..O.#
                #O#..O...#
                #O..O..O.#
                #.OO.O.OO#
                #....O...#
                ##########
                
                <vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^
                vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v
                ><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<
                <<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^
                ^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><
                ^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^
                >^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^
                <><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>
                ^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>
                v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^""";

        try (var scanner = new Scanner(input)) {
            int gps = Day15.partOne(scanner, true);
            assertThat(gps).isEqualTo(10092);
        }

        try (var scanner = new Scanner(input)) {
            int gps = Day15.partTwo(scanner, true);
            assertThat(gps).isEqualTo(9021);
        }
    }

    @Test
    void example3() {
        var input = """
                #######
                #...#.#
                #.....#
                #..OO@#
                #..O..#
                #.....#
                #######
                
                <vv<<^^<<^^""";

        try (var scanner = new Scanner(input)) {
            int gps = Day15.partTwo(scanner, false);
            assertThat(gps).isEqualTo(618);
        }
    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        int gps = Day15.partOne(scanner, false);
        assertThat(gps).isEqualTo(1426855);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        int gps = Day15.partTwo(scanner, false);
        assertThat(gps).isEqualTo(1404917);
    }

}
