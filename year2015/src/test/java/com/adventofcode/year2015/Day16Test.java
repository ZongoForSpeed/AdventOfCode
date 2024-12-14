package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day16Test extends AbstractTest {
    Day16Test() {
        super(2015, 16);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day16.findAuntSuePartOne(scanner)).isEqualTo(103);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day16.findAuntSuePartTwo(scanner)).isEqualTo(405);
    }

}
