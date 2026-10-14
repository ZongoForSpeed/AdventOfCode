package com.adventofcode.year2017;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2017, day = 5)
class Day05Test {
    @Test
    void inputExample() {
        var input = """
                0
                3
                0
                1
                -3""";

        assertThat(Day05.countStepsPartOne(new Scanner(input))).isEqualTo(5);
        assertThat(Day05.countStepsPartTwo(new Scanner(input))).isEqualTo(10);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day05.countStepsPartOne(scanner)).isEqualTo(378980);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day05.countStepsPartTwo(scanner)).isEqualTo(26889114);
    }

}
