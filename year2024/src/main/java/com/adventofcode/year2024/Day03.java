package com.adventofcode.year2024;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day03 {

    private static final Pattern MUL_PATTERN_PART_1 = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
    private static final Pattern MUL_PATTERN_PART_2 = Pattern.compile("mul\\((\\d+),(\\d+)\\)|don't\\(\\)|do\\(\\)");

    private Day03() {
        // No-Op
    }

    /**
     * --- Day 3: Mull It Over ---
     * <p>
     * "Our computers are having issues, so I have no idea if we have any Chief
     * Historians in stock! You're welcome to check the warehouse, though," says
     * the mildly flustered shopkeeper at the North Pole Toboggan Rental Shop. The
     * Historians head out to take a look.
     * <p>
     * The shopkeeper turns to you. "Any chance you can see why our computers are
     * having issues again?"
     * <p>
     * The computer appears to be trying to run a program, but its memory (your
     * puzzle input) is corrupted. All of the instructions have been jumbled up!
     * <p>
     * It seems like the goal of the program is just to multiply some numbers. It
     * does that with instructions like mul(X,Y), where X and Y are each 1-3 digit
     * numbers. For instance, mul(44,46) multiplies 44 by 46 to get a result of
     * 2024. Similarly, mul(123,4) would multiply 123 by 4.
     * <p>
     * However, because the program's memory has been corrupted, there are also
     * many invalid characters that should be ignored, even if they look like part
     * of a mul instruction. Sequences like mul(4*, mul(6,9!, ?(12,34), or
     * mul ( 2 , 4 ) do nothing.
     * <p>
     * For example, consider the following section of corrupted memory:
     * <p>
     * xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))
     * <p>
     * Only the four highlighted sections are real mul instructions. Adding up the
     * result of each instruction produces 161 (2*4 + 5*5 + 11*8 + 8*5).
     * <p>
     * Scan the corrupted memory for uncorrupted mul instructions. What do you get
     * if you add up all of the results of the multiplications?
     */
    public static long partOne(Scanner scanner) {
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        }
        Matcher matcher = MUL_PATTERN_PART_1.matcher(stringBuilder.toString());

        long results = 0;
        while (matcher.find()) {
            long a = Long.parseLong(matcher.group(1));
            long b = Long.parseLong(matcher.group(2));

            results += a * b;
        }
        return results;
    }

    /**
     * --- Part Two ---
     * <p>
     * As you scan through the corrupted memory, you notice that some of the
     * conditional statements are also still intact. If you handle some of the
     * uncorrupted conditional statements in the program, you might be able to get
     * an even more accurate result.
     * <p>
     * There are two new instructions you'll need to handle:
     * <p>
     * - The do() instruction enables future mul instructions.
     * - The don't() instruction disables future mul instructions.
     * <p>
     * Only the most recent do() or don't() instruction applies. At the beginning
     * of the program, mul instructions are enabled.
     * <p>
     * For example:
     * <p>
     * xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))
     * <p>
     * This corrupted memory is similar to the example from before, but this time
     * the mul(5,5) and mul(11,8) instructions are disabled because there is a
     * don't() instruction before them. The other mul instructions function
     * normally, including the one at the end that gets re-enabled by a do()
     * instruction.
     * <p>
     * This time, the sum of the results is 48 (2*4 + 8*5).
     * <p>
     * Handle the new instructions; what do you get if you add up all of the
     * results of just the enabled multiplications?
     */
    public static long partTwo(Scanner scanner) {
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine());
        }
        Matcher matcher = MUL_PATTERN_PART_2.matcher(stringBuilder.toString());

        long results = 0;

        boolean enableMul = true;
        while (matcher.find()) {
            String group = matcher.group();
            switch (group) {
                case "do()" -> enableMul = true;
                case "don't()" -> enableMul = false;
                default -> {
                    if (enableMul) {
                        long a = Long.parseLong(matcher.group(1));
                        long b = Long.parseLong(matcher.group(2));

                        results += a * b;
                    }
                }
            }
        }
        return results;
    }
}
