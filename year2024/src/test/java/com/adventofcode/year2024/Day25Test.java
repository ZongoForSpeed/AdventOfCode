package com.adventofcode.year2024;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2024, day = 25)
class Day25Test {

    @Test
    void inputExample() {
        var input = """
                #####
                .####
                .####
                .####
                .#.#.
                .#...
                .....
                
                #####
                ##.##
                .#.##
                ...##
                ...#.
                ...#.
                .....
                
                .....
                #....
                #....
                #...#
                #.#.#
                #.###
                #####
                
                .....
                .....
                #.#..
                ###..
                ###.#
                ###.#
                #####
                
                .....
                .....
                .....
                #....
                #.#..
                #.#.#
                #####""";

        try (var scanner = new Scanner(input)) {
            int count = Day25.partOne(scanner);
            assertThat(count).isEqualTo(3);
        }
    }

    @Test
    void partOne(Scanner scanner) throws Exception {
        int count = Day25.partOne(scanner);
        assertThat(count).isEqualTo(3155);
    }

    @Test
    void partTwo(Scanner scanner) throws Exception {
        Day25.partTwo(scanner);
    }

}
