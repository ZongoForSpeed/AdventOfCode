package com.adventofcode.year2017;

import com.adventofcode.test.AbstractTest;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.function.IntConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;


class Day08Test extends AbstractTest {
    Day08Test() {
        super(2017, 8);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Day08Test.class);
    private static final Pattern PATTERN = Pattern.compile("(\\w+) (inc|dec) (-?\\d+) if (\\w+) (.+) (-?\\d+)");

    private static boolean accept(Map<String, Integer> registers, String register, String operator, int value) {
        int variable = registers.getOrDefault(register, 0);
        return switch (operator) {
            case ">" -> variable > value;
            case "<" -> variable < value;
            case "==" -> variable == value;
            case "!=" -> variable != value;
            case ">=" -> variable >= value;
            case "<=" -> variable <= value;
            default -> throw new IllegalStateException("Unknown operation '" + operator + "'");
        };
    }

    private static void command(Map<String, Integer> registers, String register, String operator, int value) {
        switch (operator) {
            case "inc" -> registers.merge(register, value, Integer::sum);
            case "dec" -> registers.merge(register, -value, Integer::sum);
            default -> throw new IllegalStateException("Unknown operation '" + operator + "'");
        }
    }

    @CanIgnoreReturnValue
    private static Map<String, Integer> executeProgram(Scanner scanner, IntConsumer consumer) {
        Map<String, Integer> registers = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                if (accept(registers, matcher.group(4), matcher.group(5), Integer.parseInt(matcher.group(6)))) {
                    command(registers, matcher.group(1), matcher.group(2), Integer.parseInt(matcher.group(3)));
                }
                LOGGER.debug("After line '{}': {}", line, registers);
                registers.values().stream().mapToInt(i -> i).forEach(consumer);
            } else {
                throw new IllegalStateException("Cannot parse line: " + line);
            }
        }
        LOGGER.info("Registers at the end: {}", registers);
        return registers;
    }

    /**
     * --- Day 8: I Heard You Like Registers ---
     * <p>
     * You receive a signal directly from the CPU. Because of your recent
     * assistance with jump instructions, it would like you to compute the result
     * of a series of unusual register instructions.
     * <p>
     * Each instruction consists of several parts: the register to modify, whether
     * to increase or decrease that register's value, the amount by which to
     * increase or decrease it, and a condition. If the condition fails, skip the
     * instruction without modifying the register. The registers all start at 0.
     * The instructions look like this:
     * <p>
     * b inc 5 if a > 1
     * a inc 1 if b < 5
     * c dec -10 if a >= 1
     * c inc -20 if c == 10
     * <p>
     * These instructions would be processed as follows:
     * <p>
     * - Because a starts at 0, it is not greater than 1, and so b is not
     * modified.
     * - a is increased by 1 (to 1) because b is less than 5 (it is 0).
     * - c is decreased by -10 (to 10) because a is now greater than or equal
     * to 1 (it is 1).
     * - c is increased by -20 (to -10) because c is equal to 10.
     * <p>
     * After this process, the largest value in any register is 1.
     * <p>
     * You might also encounter <= (less than or equal to) or != (not equal to).
     * However, the CPU doesn't have the bandwidth to tell you what all the
     * registers are named, and leaves that to you to determine.
     * <p>
     * What is the largest value in any register after completing the instructions
     * in your puzzle input?
     * <p>
     * Your puzzle answer was 4448.
     */
    private static OptionalInt maxRegistersPartOne(Scanner scanner) {
        Map<String, Integer> registers = executeProgram(scanner, _ -> {
        });
        return registers.values().stream().mapToInt(t -> t).max();
    }

    /**
     * --- Part Two ---
     * <p>
     * To be safe, the CPU also needs to know the highest value held in any
     * register during this process so that it can decide how much memory to
     * allocate to these operations. For example, in the above instructions, the
     * highest value ever held was 10 (in register c after the third instruction
     * was evaluated).
     * <p>
     * Your puzzle answer was 6582.
     */
    private static OptionalInt maxRegistersPartTwo(Scanner scanner) {
        IntList values = new IntArrayList();
        executeProgram(scanner, values::add);
        return values.intStream().max();
    }

    @Test
    void inputExample() {
        String input = """
                b inc 5 if a > 1
                a inc 1 if b < 5
                c dec -10 if a >= 1
                c inc -20 if c == 10""";

        Map<String, Integer> registers = executeProgram(new Scanner(input), _ -> {
        });
        assertThat(registers).hasSize(2).containsEntry("a", 1).containsEntry("c", -10);

        OptionalInt maxRegister = maxRegistersPartTwo(new Scanner(input));
        assertThat(maxRegister).isPresent().hasValue(10);
    }

    @Override
    public void partOne(Scanner scanner) {
        assertThat(maxRegistersPartOne(scanner)).isPresent().hasValue(4448);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(maxRegistersPartTwo(scanner)).isPresent().hasValue(6582);
    }

}
