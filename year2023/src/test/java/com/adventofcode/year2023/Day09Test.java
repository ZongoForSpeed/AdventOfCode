package com.adventofcode.year2023;

import com.adventofcode.test.AdventOfCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

@AdventOfCode(year = 2023, day = 9)
class Day09Test {

    @Test
    void inputExample() {
        var input = """
                0 3 6 9 12 15
                1 3 6 10 15 21
                10 13 16 21 30 45""";

        {
            var scanner = new Scanner(input);
            int sum = Day09.PartOne.extrapolatedValues(scanner);
            Assertions.assertThat(sum).isEqualTo(114);
        }

        {
            var scanner = new Scanner(input);
            int sum = Day09.PartTwo.extrapolatedValues(scanner);
            Assertions.assertThat(sum).isEqualTo(2);
        }
    }

    @Test
    void partOne(Scanner scanner) {
        int sum = Day09.PartOne.extrapolatedValues(scanner);
        Assertions.assertThat(sum).isEqualTo(1819125966);
    }

    @Test
    void partTwo(Scanner scanner) {
        int sum = Day09.PartTwo.extrapolatedValues(scanner);
        Assertions.assertThat(sum).isEqualTo(1140);
    }
}
