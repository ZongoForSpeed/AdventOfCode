package com.adventofcode.year2024;

import com.adventofcode.common.maths.Arithmetic;
import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;
import java.util.Scanner;
import java.util.function.BiPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day07 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day07.class);
    private static final Pattern PATTERN = Pattern.compile("^(\\d+): (.+)$");
    private static final int[] POWER_TENS = new int[]{
            10,         //
            100,        //
            1000,       //
            10000,      //
            100000,     //
            1000000,    //
            10000000,   //
            100000000   //
    };

    private Day07() {
        // No-Op
    }

    /**
     * --- Day 7: Bridge Repair ---
     * <p>
     * The Historians take you to a familiar rope bridge over a river in the
     * middle of a jungle. The Chief isn't on this side of the bridge, though;
     * maybe he's on the other side?
     * <p>
     * When you go to cross the bridge, you notice a group of engineers trying to
     * repair it. (Apparently, it breaks pretty frequently.) You won't be able to
     * cross until it's fixed.
     * <p>
     * You ask how long it'll take; the engineers tell you that it only needs
     * final calibrations, but some young elephants were playing nearby and stole
     * all the operators from their calibration equations! They could finish the
     * calibrations if only someone could determine which test values could
     * possibly be produced by placing any combination of operators into their
     * calibration equations (your puzzle input).
     * <p>
     * For example:
     * <p>
     * 190: 10 19
     * 3267: 81 40 27
     * 83: 17 5
     * 156: 15 6
     * 7290: 6 8 6 15
     * 161011: 16 10 13
     * 192: 17 8 14
     * 21037: 9 7 18 13
     * 292: 11 6 16 20
     * <p>
     * Each line represents a single equation. The test value appears before the
     * colon on each line; it is your job to determine whether the remaining
     * numbers can be combined with operators to produce the test value.
     * <p>
     * Operators are always evaluated left-to-right, not according to precedence
     * rules. Furthermore, numbers in the equations cannot be rearranged. Glancing
     * into the jungle, you can see elephants holding two different types of
     * operators: add (+) and multiply (*).
     * <p>
     * Only three of the above equations can be made true by inserting operators:
     * <p>
     * - 190: 10 19 has only one position that accepts an operator: between 10
     * and 19. Choosing + would give 29, but choosing * would give the test
     * value (10 * 19 = 190).
     * - 3267: 81 40 27 has two positions for operators. Of the four possible
     * configurations of the operators, two cause the right side to match the
     * test value: 81 + 40 * 27 and 81 * 40 + 27 both equal 3267 (when
     * evaluated left-to-right)!
     * - 292: 11 6 16 20 can be solved in exactly one way: 11 + 6 * 16 + 20.
     * <p>
     * The engineers just need the total calibration result, which is the sum of the test values from just the equations that could possibly be true. In the above example, the sum of the test values for the three equations listed above is 3749.
     * <p>
     * Determine which equations could possibly be true. What is their total calibration result?
     */
    static long partOne(Scanner scanner) {
        return checkBridge(scanner, Day07::checkBridgePartOne);
    }

    private static long checkBridge(Scanner scanner, BiPredicate<Long, IntList> checker) {
        long result = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.find()) {
                long objective = Long.parseLong(matcher.group(1));
                IntList numbers = IntArrayList.toList(Splitter.on(' ').splitToStream(matcher.group(2)).mapToInt(Integer::parseInt));
                LOGGER.info("{} --> {}", objective, numbers);
                if (checker.test(objective, numbers)) {
                    result += objective;
                }
            } else {
                LOGGER.error("Cannot parse line '{}'", line);
            }
        }
        return result;
    }

    private static boolean checkBridgePartOne(long objective, IntList numbers) {
        int size = numbers.size();
        long maskLimit = (1L << size) - 1;
        for (long mask = 0; mask <= maskLimit; ++mask) {
            BitSet bitSet = BitSet.valueOf(new long[]{mask});
            long value = numbers.getInt(0);
            for (int i = 1; i < numbers.size(); ++i) {
                if (bitSet.get(i - 1)) {
                    value += numbers.getInt(i);
                } else {
                    value *= numbers.getInt(i);
                }
            }
            if (value == objective) {
                return true;
            }
        }
        return false;
    }

    static boolean checkBridgePartTwo(long objective, IntList numbers) {
        int size = numbers.size();
        long maskLimit = Arithmetic.power(3L, size) - 1;
        for (long mask = 0; mask <= maskLimit; ++mask) {
            long localMask = mask;
            long value = numbers.getInt(0);
            for (int i = 1; i < numbers.size(); ++i) {
                switch ((int) (localMask % 3)) {
                    case 0 -> value += numbers.getInt(i);
                    case 1 -> value *= numbers.getInt(i);
                    case 2 -> value = concat(value, numbers.getInt(i));
                }
                localMask /= 3;
            }
            if (value == objective) {
                return true;
            }
        }
        return false;
    }

    private static long concat(long a, int b) {
        for (int ten : POWER_TENS) {
            if (b < ten) {
                return a * ten + b;
            }
        }
        return Long.parseLong(a + Integer.toString(b));
    }

    /**
     * --- Part Two ---
     * <p>
     * The engineers seem concerned; the total calibration result you gave them is
     * nowhere close to being within safety tolerances. Just then, you spot your
     * mistake: some well-hidden elephants are holding a third type of operator.
     * <p>
     * The concatenation operator (||) combines the digits from its left and right
     * inputs into a single number. For example, 12 || 345 would become 12345. All
     * operators are still evaluated left-to-right.
     * <p>
     * Now, apart from the three equations that could be made true using only
     * addition and multiplication, the above example has three more equations
     * that can be made true by inserting operators:
     * <p>
     * - 156: 15 6 can be made true through a single concatenation:
     * 15 || 6 = 156.
     * - 7290: 6 8 6 15 can be made true using 6 * 8 || 6 * 15.
     * - 192: 17 8 14 can be made true using 17 || 8 + 14.
     * <p>
     * Adding up all six test values (the three that could be made before using
     * only + and * plus the new three that can now be made by also using ||)
     * produces the new total calibration result of 11387.
     * <p>
     * Using your new knowledge of elephant hiding spots, determine which
     * equations could possibly be true. What is their total calibration result?
     */
    static long partTwo(Scanner scanner) {
        return checkBridge(scanner, Day07::checkBridgePartTwo);
    }
}
