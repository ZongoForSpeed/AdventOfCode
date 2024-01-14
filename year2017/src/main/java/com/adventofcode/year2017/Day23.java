package com.adventofcode.year2017;

import com.adventofcode.common.maths.Prime;
import it.unimi.dsi.fastutil.longs.LongSet;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class Day23 {
    private Day23() {
        // No-Op
    }

    private static long getValue(Map<String, Long> register, String key) {
        if (NumberUtils.isParsable(key)) {
            return Long.parseLong(key);
        }
        return register.getOrDefault(key, 0L);
    }

    static List<String[]> readCommands(Scanner scanner) {
        List<String[]> commands = new ArrayList<>();
        while (scanner.hasNextLine()) {
            commands.add(scanner.nextLine().split(" "));
        }
        return commands;
    }

    /**
     * --- Day 23: Coprocessor Conflagration ---
     *
     * You decide to head directly to the CPU and fix the printer from there. As
     * you get close, you find an experimental coprocessor doing so much work that
     * the local programs are afraid it will halt and catch fire. This would cause
     * serious issues for the rest of the computer, so you head in and see what
     * you can do.
     *
     * The code it's running seems to be a variant of the kind you saw recently on
     * that tablet. The general functionality seems very similar, but some of the
     * instructions are different:
     *
     *   - set X Y sets register X to the value of Y.
     *   - sub X Y decreases register X by the value of Y.
     *   - mul X Y sets register X to the result of multiplying the value
     *     contained in register X by the value of Y.
     *   - jnz X Y jumps with an offset of the value of Y, but only if the value
     *     of X is not zero. (An offset of 2 skips the next instruction, an
     *     offset of -1 jumps to the previous instruction, and so on.)
     *
     * Only the instructions listed above are used. The eight registers here,
     * named a through h, all start at 0.
     *
     * The coprocessor is currently set to some kind of debug mode, which allows
     * for testing, but prevents it from doing any meaningful work.
     *
     * If you run the program (your puzzle input), how many times is the mul
     * instruction invoked?
     *
     * Your puzzle answer was 3025.
     */
    static int runProgram(List<String[]> commands, Map<String, Long> registers) {
        int position = 0;
        int mul = 0;
        while (position < commands.size()) {
            String[] command = commands.get(position);
            switch (command[0]) {
                case "set" -> {
                    long value = getValue(registers, command[2]);
                    registers.put(command[1], value);
                    ++position;
                }
                case "sub" -> {
                    long value = getValue(registers, command[2]);
                    registers.merge(command[1], value, (a, b) -> a - b);
                    ++position;
                }
                case "mul" -> {
                    ++mul;
                    long value = getValue(registers, command[2]);
                    registers.merge(command[1], value, (a, b) -> a * b);
                    ++position;
                }
                case "jnz" -> {
                    long value1 = getValue(registers, command[1]);
                    long value2 = getValue(registers, command[2]);
                    if (value1 != 0) {
                        position += value2;
                    } else {
                        ++position;
                    }
                }
                default -> throw new IllegalStateException("Unknown command :" + Arrays.toString(command));
            }
        }
        return mul;
    }

    /**
     * --- Part Two ---
     *
     * Now, it's time to fix the problem.
     *
     * The debug mode switch is wired directly to register a. You flip the switch,
     * which makes register a now start at 1 when the program is executed.
     *
     * Immediately, the coprocessor begins to overheat. Whoever wrote this program
     * obviously didn't choose a very efficient implementation. You'll need to
     * optimize the program if it has any hope of completing before Santa needs
     * that printer working.
     *
     * The coprocessor's ultimate goal is to determine the final value left in
     * register h once the program completes. Technically, if it had that... it
     * wouldn't even need to run the program.
     *
     * After setting register a to 1, if the program were to run to completion,
     * what value would be left in register h?
     *
     * Your puzzle answer was 915.
     */
    static long runProgramFast(int b, int c) {
        long h = 0;
        LongSet primes = LongSet.of(Prime.sieve(c));
        for (long d = b; d <= c; d += 17) {
            if (!primes.contains(d)) {
                h++;
            }
        }
        return h;
    }
}
