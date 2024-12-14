package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;


class Day10Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day10Test.class);

    @Test
    void inputExample() {
        String input = "1";

        for (int step = 1; step < 6; ++step) {
            String next = Day10.lookAndSay(input);
            LOGGER.info("{} becomes {}", input, next);
            input = next;
        }

        assertThat(input).isEqualTo("312211");
    }

    @Test
    void inputPartOne() {
        String input = "3113322113";
        input = Day10.lookAndSay(input, 40);
        assertThat(input).hasSize(329356);
    }

    @Test
    void inputPartTwo() {
        String input = "3113322113";
        input = Day10.lookAndSay(input, 50);
        assertThat(input).hasSize(4666278);
    }
}
