package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test extends AbstractTest {

    Day15Test() {
        super(2024, 15);
    }

    @Test
    void example1() {
        String input = """
                ########
                #..O.O.#
                ##@.O..#
                #...O..#
                #.#.O..#
                #...O..#
                #......#
                ########
                
                <^^>>>vv<v>>v<<""";

        try (Scanner scanner = new Scanner(input)) {
            int gps = Day15.partOne(scanner, true);
            assertThat(gps).isEqualTo(2028);
        }

        try (Scanner scanner = new Scanner(input)) {
            int gps = Day15.partTwo(scanner, true);
            assertThat(gps).isEqualTo(1751);
        }
    }


    @Test
    void example2() {
        String input = """
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

        try (Scanner scanner = new Scanner(input)) {
            int gps = Day15.partOne(scanner, true);
            assertThat(gps).isEqualTo(10092);
        }

        try (Scanner scanner = new Scanner(input)) {
            int gps = Day15.partTwo(scanner, true);
            assertThat(gps).isEqualTo(9021);
        }
    }

    @Test
    void example3() {
        String input = """
                #######
                #...#.#
                #.....#
                #..OO@#
                #..O..#
                #.....#
                #######
                
                <vv<<^^<<^^""";

        try (Scanner scanner = new Scanner(input)) {
            int gps = Day15.partTwo(scanner, false);
            assertThat(gps).isEqualTo(618);
        }
    }

    @Override
    public void partOne(Scanner scanner) throws Exception {
        int gps = Day15.partOne(scanner, false);
        assertThat(gps).isEqualTo(1426855);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        int gps = Day15.partTwo(scanner, false);
        assertThat(gps).isEqualTo(1404917);
    }

}
