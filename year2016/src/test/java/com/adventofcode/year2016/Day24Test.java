package com.adventofcode.year2016;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 24)
class Day24Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24Test.class);

    @Test
    void inputExample() {
        var input = """
                ###########
                #0.1.....2#
                #.#######.#
                #4.......3#
                ###########""";

        var scanner = new Scanner(input);
        long minSteps = Day24.computeMinSteps(scanner, false);

        LOGGER.info("minSteps: {}", minSteps);
        assertThat(minSteps).isEqualTo(14);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day24.computeMinSteps(scanner, false)).isEqualTo(470);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day24.computeMinSteps(scanner, true)).isEqualTo(720);
    }
}
