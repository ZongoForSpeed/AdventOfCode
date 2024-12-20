package com.adventofcode.year2024;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day20Test extends AbstractTest {

    Day20Test() {
        super(2024, 20);
    }

    @Test
    void inputExample() {
        String input = """
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

        try (Scanner scanner = new Scanner(input)) {
            int countCheats = Day20.partOne(scanner, 20);
            assertThat(countCheats).isEqualTo(5);
        }

        try (Scanner scanner = new Scanner(input)) {
            int countCheats = Day20.partTwo(scanner, 50);
            assertThat(countCheats).isEqualTo(285);
        }
    }


    @Override
    public void partOne(Scanner scanner) throws Exception {
        int countCheats = Day20.partOne(scanner, 100);
        assertThat(countCheats).isEqualTo(1502);
    }

    @Override
    public void partTwo(Scanner scanner) throws Exception {
        int countCheats = Day20.partTwo(scanner, 100);
        assertThat(countCheats).isEqualTo(1028136);
    }
}
