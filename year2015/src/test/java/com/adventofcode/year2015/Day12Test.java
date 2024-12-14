package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day12Test extends AbstractTest {
    Day12Test() {
        super(2015, 12);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day12.computeSumPartOne(scanner)).isEqualTo(156366);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day12.computeSumPartTwo(scanner)).isEqualTo(96852);
    }
}
