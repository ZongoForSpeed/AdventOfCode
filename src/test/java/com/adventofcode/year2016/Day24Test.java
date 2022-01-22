package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day24Test {
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

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day24Test.class.getResourceAsStream("/2016/day/24/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day24.computeMinSteps(scanner, false)).isEqualTo(470);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day24Test.class.getResourceAsStream("/2016/day/24/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day24.computeMinSteps(scanner, true)).isEqualTo(720);
        }
    }
}
