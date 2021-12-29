package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


class Day10Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day10Test.class);

    private static String lookAndSay(String string) {
        Character previous = null;
        int count = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (previous == null) {
                previous = c;
                count++;
            } else if (previous == c) {
                ++count;
            } else {
                stringBuilder.append(count).append(previous);
                previous = c;
                count = 1;
            }
        }
        stringBuilder.append(count).append(previous);
        return stringBuilder.toString();
    }

    private static String lookAndSay(String input, int steps) {
        for (int step = 1; step <= steps; ++step) {
            String next = lookAndSay(input);
            LOGGER.trace("{} becomes {}", input, next);
            input = next;
        }
        return input;
    }

    @Test
    void inputExample() {
        String input = "1";

        for (int step = 1; step < 6; ++step) {
            String next = lookAndSay(input);
            LOGGER.info("{} becomes {}", input, next);
            input = next;
        }

        assertThat(input).isEqualTo("312211");
    }

    /**
     * --- Day 10: Elves Look, Elves Say ---
     *
     * Today, the Elves are playing a game called look-and-say. They take turns
     * making sequences by reading aloud the previous sequence and using that
     * reading as the next sequence. For example, 211 is read as "one two, two
     * ones", which becomes 1221 (1 2, 2 1s).
     *
     * Look-and-say sequences are generated iteratively, using the previous value
     * as input for the next step. For each step, take the previous value, and
     * replace each run of digits (like 111) with the number of digits (3)
     * followed by the digit itself (1).
     *
     * For example:
     *
     *   - 1 becomes 11 (1 copy of digit 1).
     *   - 11 becomes 21 (2 copies of digit 1).
     *   - 21 becomes 1211 (one 2 followed by one 1).
     *   - 1211 becomes 111221 (one 1, one 2, and two 1s).
     *   - 111221 becomes 312211 (three 1s, two 2s, and one 1).
     *
     * Starting with the digits in your puzzle input, apply this process 40 times.
     * What is the length of the result?
     *
     * Your puzzle answer was 329356.
     */
    @Test
    void inputPartOne() throws IOException {
        String input = "3113322113";
        input = lookAndSay(input, 40);
        assertThat(input).hasSize(329356);
    }

    /**
     * --- Part Two ---
     *
     * Neat, right? You might also enjoy hearing John Conway talking about
     * this sequence (that's Conway of Conway's Game of Life fame).
     *
     * Now, starting again with the digits in your puzzle input, apply this
     * process 50 times. What is the length of the new result?
     *
     * Your puzzle answer was 4666278.
     */
    @Test
    void inputPartTwo() throws IOException {
        String input = "3113322113";
        input = lookAndSay(input, 50);
        assertThat(input).hasSize(4666278);
    }
}
