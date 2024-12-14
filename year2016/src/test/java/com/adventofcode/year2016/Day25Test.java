package com.adventofcode.year2016;

import com.adventofcode.test.AbstractTest;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day25Test extends AbstractTest {

    Day25Test() {
        super(2016, 25);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day25.findClockSignal(scanner)).isEqualTo(189);
    }

    @Override
    public void partTwo(Scanner scanner) {
        // No-Op
    }
}
