package com.adventofcode.year2015;

public final class Day01 {
    private Day01() {
        // No-Op
    }

    /**
     * --- Day 1: Not Quite Lisp ---
     * <p>
     * Santa was hoping for a white Christmas, but his weather machine's "snow"
     * function is powered by stars, and he's fresh out! To save Christmas, he
     * needs you to collect fifty stars by December 25th.
     * <p>
     * Collect stars by helping Santa solve puzzles. Two puzzles will be made
     * available on each day in the Advent calendar; the second puzzle is unlocked
     * when you complete the first. Each puzzle grants one star. Good luck!
     * <p>
     * Here's an easy puzzle to warm you up.
     * <p>
     * Santa is trying to deliver presents in a large apartment building, but he
     * can't find the right floor - the directions he got are a little confusing.
     * He starts on the ground floor (floor 0) and then follows the instructions
     * one character at a time.
     * <p>
     * An opening parenthesis, (, means he should go up one floor, and a closing
     * parenthesis, ), means he should go down one floor.
     * <p>
     * The apartment building is very tall, and the basement is very deep; he will
     * never find the top or bottom floors.
     * <p>
     * For example:
     * <p>
     * - (()) and ()() both result in floor 0.
     * - ((( and (()(()( both result in floor 3.
     * - ))((((( also results in floor 3.
     * - ()) and ))( both result in floor -1 (the first basement level).
     * - ))) and )())()) both result in floor -3.
     * <p>
     * To what floor do the instructions take Santa?
     * <p>
     * Your puzzle answer was 232.
     */
    public static int countFloor(String input) {
        int floor = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '(') {
                floor++;
            } else if (c == ')') {
                floor--;
            }
        }

        return floor;
    }

    /**
     * --- Part Two ---
     * <p>
     * Now, given the same instructions, find the position of the first character
     * that causes him to enter the basement (floor -1). The first character in
     * the instructions has position 1, the second character has position 2, and
     * so on.
     * <p>
     * For example:
     * <p>
     * - ) causes him to enter the basement at character position 1.
     * - ()()) causes him to enter the basement at character position 5.
     * <p>
     * What is the position of the character that causes Santa to first enter the
     * basement?
     * <p>
     * Your puzzle answer was 1783.
     */
    public static int findBasement(String input) {
        int floor = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                floor++;
            } else if (input.charAt(i) == ')') {
                floor--;
            }
            if (floor == -1) {
                return i + 1;
            }
        }

        return -1;
    }
}
