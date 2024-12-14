package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;


class Day18Test extends AbstractTest {
    Day18Test() {
        super(2015, 18);
    }

    @Test
    void inputExample() {
        String input = """
                .#.#.#
                ...##.
                #....#
                ..#...
                #.#..#
                ####..""";

        Assertions.assertThat(Day18.getLightsPartOne(new Scanner(input), 4, 6, 6)).isEqualTo(4);
        Assertions.assertThat(Day18.getLightsPartTwo(new Scanner(input), 5, 6, 6)).isEqualTo(17);
    }

    @Override
    public void partOne(Scanner scanner) {
        Assertions.assertThat(Day18.getLightsPartOne(scanner, 100, 100, 100)).isEqualTo(768);
    }

    @Override
    public void partTwo(Scanner scanner) {
        Assertions.assertThat(Day18.getLightsPartTwo(scanner, 100, 100, 100)).isEqualTo(781);
    }
}
