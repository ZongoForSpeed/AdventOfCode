package com.adventofcode.year2019;

import com.adventofcode.test.AbstractTest;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day19Test extends AbstractTest {
    Day19Test() {
        super(2019, 19);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day19.countPartOne(scanner.nextLine())).isEqualTo(166);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day19.countPartTwo(scanner.nextLine())).isEqualTo(3790981);
    }
}
