package com.adventofcode.year2015;

import com.adventofcode.test.AbstractTest;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day06Test extends AbstractTest {
    Day06Test() {
        super(2015, 6);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day06.computeLights(scanner)).isEqualTo(377891);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day06.computeBrightness(scanner)).isEqualTo(14110788);
    }
}
