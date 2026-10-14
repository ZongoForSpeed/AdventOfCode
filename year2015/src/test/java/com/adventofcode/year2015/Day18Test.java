package com.adventofcode.year2015;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;


@AdventOfCode(year = 2015, day = 18)
class Day18Test {
    @Test
    void inputExample() {
        var input = """
                .#.#.#
                ...##.
                #....#
                ..#...
                #.#..#
                ####..""";

        Assertions.assertThat(Day18.getLightsPartOne(new Scanner(input), 4, 6, 6)).isEqualTo(4);
        Assertions.assertThat(Day18.getLightsPartTwo(new Scanner(input), 5, 6, 6)).isEqualTo(17);
    }

    @Test
    void partOne(Scanner scanner) {
        Assertions.assertThat(Day18.getLightsPartOne(scanner, 100, 100, 100)).isEqualTo(768);
    }

    @Test
    void partTwo(Scanner scanner) {
        Assertions.assertThat(Day18.getLightsPartTwo(scanner, 100, 100, 100)).isEqualTo(781);
    }
}
