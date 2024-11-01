package com.adventofcode.year2015;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Day10 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day10.class);

    private Day10() {
        // No-Op
    }

    public static String lookAndSay(String string) {
        Character previous = null;
        int count = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
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

    /**
     * --- Day 10: Elves Look, Elves Say ---
     * <p>
     * Today, the Elves are playing a game called look-and-say. They take turns
     * making sequences by reading aloud the previous sequence and using that
     * reading as the next sequence. For example, 211 is read as "one two, two
     * ones", which becomes 1221 (1 2, 2 1s).
     * <p>
     * Look-and-say sequences are generated iteratively, using the previous value
     * as input for the next step. For each step, take the previous value, and
     * replace each run of digits (like 111) with the number of digits (3)
     * followed by the digit itself (1).
     * <p>
     * For example:
     * <p>
     * - 1 becomes 11 (1 copy of digit 1).
     * - 11 becomes 21 (2 copies of digit 1).
     * - 21 becomes 1211 (one 2 followed by one 1).
     * - 1211 becomes 111221 (one 1, one 2, and two 1s).
     * - 111221 becomes 312211 (three 1s, two 2s, and one 1).
     * <p>
     * Starting with the digits in your puzzle input, apply this process 40 times.
     * What is the length of the result?
     * <p>
     * Your puzzle answer was 329356.
     * <p>
     * --- Part Two ---
     * <p>
     * Neat, right? You might also enjoy hearing John Conway talking about
     * this sequence (that's Conway of Conway's Game of Life fame).
     * <p>
     * Now, starting again with the digits in your puzzle input, apply this
     * process 50 times. What is the length of the new result?
     * <p>
     * Your puzzle answer was 4666278.
     */
    public static String lookAndSay(String input, int steps) {
        for (int step = 1; step <= steps; ++step) {
            String next = lookAndSay(input);
            LOGGER.trace("{} becomes {}", input, next);
            input = next;
        }
        return input;
    }
}
