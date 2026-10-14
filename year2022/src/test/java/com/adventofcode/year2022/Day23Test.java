package com.adventofcode.year2022;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2022, day = 23)
class Day23Test {

    @Test
    void partOne(Scanner scanner) throws Exception {
        int freeSpace = Day23.partOne(scanner);
        assertThat(freeSpace).isEqualTo(4000);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        int rounds = Day23.partTwo(scanner);
        assertThat(rounds).isEqualTo(1040);
    }

    @Test
    void inputExample1() {
        var input = """
                .....
                ..##.
                ..#..
                .....
                ..##.
                .....""";

        try (var scanner = new Scanner(input)) {
            assertThat(Day23.partOne(scanner)).isEqualTo(25);
        }
    }

    @Test
    void inputExample2() {
        var input = """
                ..............
                ..............
                .......#......
                .....###.#....
                ...#...#.#....
                ....#...##....
                ...#.###......
                ...##.#.##....
                ....#..#......
                ..............
                ..............
                ..............""";

        try (var scanner = new Scanner(input)) {
            int freeSpace = Day23.partOne(scanner);
            assertThat(freeSpace).isEqualTo(110);
        }

        try (var scanner = new Scanner(input)) {
            int rounds = Day23.partTwo(scanner);
            assertThat(rounds).isEqualTo(20);
        }
    }

}
