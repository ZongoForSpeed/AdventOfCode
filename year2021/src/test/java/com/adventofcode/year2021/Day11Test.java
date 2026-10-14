package com.adventofcode.year2021;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2021, day = 11)
class Day11Test {
    @Test
    void inputSimpleExample() {
        var input = """
                11111
                19991
                19191
                19991
                11111""";

        assertThat(Day11.dumboOctopusPartOne(new Scanner(input), 2)).isEqualTo(9);

    }

    @Test
    void inputExample() {
        var input = """
                5483143223
                2745854711
                5264556173
                6141336146
                6357385478
                4167524645
                2176841721
                6882881134
                4846848554
                5283751526""";

        assertThat(Day11.dumboOctopusPartOne(new Scanner(input), 10)).isEqualTo(204);
        assertThat(Day11.dumboOctopusPartOne(new Scanner(input), 100)).isEqualTo(1656);
        assertThat(Day11.dumboOctopusPartTwo(new Scanner(input))).isEqualTo(195);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day11.dumboOctopusPartOne(scanner, 100)).isEqualTo(1615);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day11.dumboOctopusPartTwo(scanner)).isEqualTo(249);
    }
}
