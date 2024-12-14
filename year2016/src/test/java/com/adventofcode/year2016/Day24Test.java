package com.adventofcode.year2016;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day24Test extends AbstractTest {
    Day24Test() {
        super(2016, 24);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Day24Test.class);

    @Test
    void inputExample() {
        String input = """
                ###########
                #0.1.....2#
                #.#######.#
                #4.......3#
                ###########""";

        Scanner scanner = new Scanner(input);
        long minSteps = Day24.computeMinSteps(scanner, false);

        LOGGER.info("minSteps: {}", minSteps);
        assertThat(minSteps).isEqualTo(14);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day24.computeMinSteps(scanner, false)).isEqualTo(470);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day24.computeMinSteps(scanner, true)).isEqualTo(720);
    }
}
