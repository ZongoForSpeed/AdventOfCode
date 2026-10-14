package com.adventofcode.year2018;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2018, day = 18)
class Day18Test {
    @Test
    void example() {
        var input = """
                .#.#...|#.
                .....#|##|
                .|..|...#.
                ..|#.....#
                #.#|||#|#|
                ...#.||...
                .|....|...
                ||...#|.#|
                |.||||..|.
                ...#.|..|.""";

        long value = Day18.treesAndLumberyards(new Scanner(input), 10);
        assertThat(value).isEqualTo(1147);

    }

    @Test
    void partOne(Scanner scanner) {
        long value = Day18.treesAndLumberyards(scanner, 10);
        assertThat(value).isEqualTo(519552);
    }

    @Test
    void partTwo(Scanner scanner) {
        long value = Day18.treesAndLumberyards(scanner, 1_000_000_000);
        assertThat(value).isEqualTo(165376);
    }

}
