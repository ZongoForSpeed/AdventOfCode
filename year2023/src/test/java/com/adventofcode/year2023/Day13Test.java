package com.adventofcode.year2023;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

class Day13Test extends AbstractTest {
    Day13Test() {
        super(2023, 13);
    }

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

            var total = Day13.PartOne.findReflection(scanner);

            Assertions.assertThat(total).isEqualTo(405);
        }

        {
            var scanner = new Scanner(input);

            var total = Day13.PartTwo.findSmudge(scanner);

            Assertions.assertThat(total).isEqualTo(400);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        var reflection = Day13.PartOne.findReflection(scanner);
        Assertions.assertThat(reflection).isEqualTo(34993);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var reflection = Day13.PartTwo.findSmudge(scanner);
        Assertions.assertThat(reflection).isEqualTo(29341);
    }
}
