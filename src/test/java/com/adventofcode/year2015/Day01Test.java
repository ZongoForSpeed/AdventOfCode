package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day01Test {
    private static int countFloor(String input) {
        int floor = 0;
        for (char c : input.toCharArray()) {
            switch (c) {
                case '(' -> floor++;
                case ')' -> floor--;
            }
        }

        return floor;
    }

    private int findBasement(String input) {
        int floor = 0;

        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            switch (charArray[i]) {
                case '(' -> floor++;
                case ')' -> floor--;
            }
            if (floor == -1) {
                return i + 1;
            }
        }

        return -1;
    }

    @Test
    void inputExample() {
        assertThat(countFloor("(())")).isZero();
        assertThat(countFloor("()()")).isZero();
        assertThat(countFloor("(((")).isEqualTo(3);
        assertThat(countFloor("(()(()(")).isEqualTo(3);
        assertThat(countFloor("))(((((")).isEqualTo(3);
        assertThat(countFloor("())")).isEqualTo(-1);
        assertThat(countFloor("))(")).isEqualTo(-1);
        assertThat(countFloor(")))")).isEqualTo(-3);
        assertThat(countFloor(")())())")).isEqualTo(-3);

        assertThat(findBasement(")")).isEqualTo(1);
        assertThat(findBasement("()()))")).isEqualTo(5);
    }

    /**
     * --- Day 1: Not Quite Lisp ---
     *
     * Santa was hoping for a white Christmas, but his weather machine's "snow"
     * function is powered by stars, and he's fresh out! To save Christmas, he
     * needs you to collect fifty stars by December 25th.
     *
     * Collect stars by helping Santa solve puzzles. Two puzzles will be made
     * available on each day in the Advent calendar; the second puzzle is unlocked
     * when you complete the first. Each puzzle grants one star. Good luck!
     *
     * Here's an easy puzzle to warm you up.
     *
     * Santa is trying to deliver presents in a large apartment building, but he
     * can't find the right floor - the directions he got are a little confusing.
     * He starts on the ground floor (floor 0) and then follows the instructions
     * one character at a time.
     *
     * An opening parenthesis, (, means he should go up one floor, and a closing
     * parenthesis, ), means he should go down one floor.
     *
     * The apartment building is very tall, and the basement is very deep; he will
     * never find the top or bottom floors.
     *
     * For example:
     *
     *   - (()) and ()() both result in floor 0.
     *   - ((( and (()(()( both result in floor 3.
     *   - ))((((( also results in floor 3.
     *   - ()) and ))( both result in floor -1 (the first basement level).
     *   - ))) and )())()) both result in floor -3.
     *
     * To what floor do the instructions take Santa?
     *
     * Your puzzle answer was 232.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2015/day/1/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(countFloor(scanner.nextLine())).isEqualTo(232);
        }
    }

    /**
     * --- Part Two ---
     *
     * Now, given the same instructions, find the position of the first character
     * that causes him to enter the basement (floor -1). The first character in
     * the instructions has position 1, the second character has position 2, and
     * so on.
     *
     * For example:
     *
     *   - ) causes him to enter the basement at character position 1.
     *   - ()()) causes him to enter the basement at character position 5.
     *
     * What is the position of the character that causes Santa to first enter the
     * basement?
     *
     * Your puzzle answer was 1783.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2015/day/1/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(findBasement(scanner.nextLine())).isEqualTo(1783);
        }
    }

}
