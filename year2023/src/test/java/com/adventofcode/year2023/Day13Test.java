package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 13)
class Day13Test {
    @Test
    void inputExample() {
        var input = """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.
                
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#""";

        {
            var scanner = new Scanner(input);

            long total = Day13.PartOne.findReflection(scanner);

            Assertions.assertThat(total).isEqualTo(405);
        }

        {
            var scanner = new Scanner(input);

            long total = Day13.PartTwo.findSmudge(scanner);

            Assertions.assertThat(total).isEqualTo(400);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        long reflection = Day13.PartOne.findReflection(scanner);
        Assertions.assertThat(reflection).isEqualTo(34993);
    }

    @Test
    void partTwo(Scanner scanner) {
        long reflection = Day13.PartTwo.findSmudge(scanner);
        Assertions.assertThat(reflection).isEqualTo(29341);
    }
}
