package com.adventofcode.year2019;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2019, day = 19)
class Day19Test {
    @Test
    void partOne(Scanner scanner) {
        assertThat(Day19.countPartOne(scanner.nextLine())).isEqualTo(166);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day19.countPartTwo(scanner.nextLine())).isEqualTo(3790981);
    }
}
