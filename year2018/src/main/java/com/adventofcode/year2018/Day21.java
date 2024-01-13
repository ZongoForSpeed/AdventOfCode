package com.adventofcode.year2018;

import com.adventofcode.common.OpCode;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public final class Day21 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21.class);

    private Day21() {
        // No-Op
    }

    /**
     * --- Day 21: Chronal Conversion ---
     *
     * You should have been watching where you were going, because as you wander
     * the new North Pole base, you trip and fall into a very deep hole!
     *
     * Just kidding. You're falling through time again.
     *
     * If you keep up your current pace, you should have resolved all of the
     * temporal anomalies by the next time the device activates. Since you have
     * very little interest in browsing history in 500-year increments for the
     * rest of your life, you need to find a way to get back to your present time.
     *
     * After a little research, you discover two important facts about the
     * behavior of the device:
     *
     * First, you discover that the device is hard-wired to always send you back
     * in time in 500-year increments. Changing this is probably not feasible.
     *
     * Second, you discover the activation system (your puzzle input) for the time
     * travel module. Currently, it appears to run forever without halting.
     *
     * If you can cause the activation system to halt at a specific moment, maybe
     * you can make the device send you so far back in time that you cause an
     * integer underflow in time itself and wrap around back to your current time!
     *
     * The device executes the program as specified in manual section one and
     * manual section two.
     *
     * Your goal is to figure out how the program works and cause it to halt. You
     * can only control register 0; every other register begins at 0 as usual.
     *
     * Because time travel is a dangerous activity, the activation system begins
     * with a few instructions which verify that bitwise AND (via bani) does a
     * numeric operation and not an operation as if the inputs were interpreted as
     * strings. If the test fails, it enters an infinite loop re-running the test
     * instead of allowing the program to execute normally. If the test passes,
     * the program continues, and assumes that all other bitwise operations (banr,
     * bori, and borr) also interpret their inputs as numbers. (Clearly, the Elves
     * who wrote this system were worried that someone might introduce a bug while
     * trying to emulate this system with a scripting language.)
     *
     * What is the lowest non-negative integer value for register 0 that causes
     * the program to halt after executing the fewest instructions? (Executing the
     * same instruction multiple times counts as multiple instructions executed.)
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static int run(Scanner scanner) {
            String s = scanner.nextLine();
            int ip = Integer.parseInt(s.split(" ")[1]);
            List<OpCode.Command> commands = OpCode.parseCommands(scanner);

            return runOpCode(ip, commands);
        }

        private static int runOpCode(int ip, List<OpCode.Command> commands) {
            IntList register = new IntArrayList(Collections.nCopies(6, 0));
            register.set(0, 1);
            while (true) {
                int current = register.getInt(ip);
                if (current >= commands.size()) {
                    register.set(ip, register.getInt(ip) - 1);
                    LOGGER.info("Register = {}", register);
                    break;
                }
                OpCode.Command command = commands.get(current);
                LOGGER.trace("Register before: {} -> {}", register, command);
                OpCode.executeInstruction(register, command.code(), command.a(), command.b(), command.c());
                if ("eqrr".equals(command.code())) {
                    LOGGER.info("Command: {}, register: {}", command, register);
                    return register.getInt(3);
                }
                LOGGER.trace("Register after: {}", register);
                register.set(ip, register.getInt(ip) + 1);
            }
            LOGGER.info("Register = {}", register);
            return -1;
        }

    }

    /**
     * --- Part Two ---
     *
     * In order to determine the timing window for your underflow exploit, you
     * also need an upper bound:
     *
     * What is the lowest non-negative integer value for register 0 that causes
     * the program to halt after executing the most instructions? (The program
     * must actually halt; running forever does not count as halting.)
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        public static int run(Scanner scanner) {
            String s = scanner.nextLine();
            int ip = Integer.parseInt(s.split(" ")[1]);
            List<OpCode.Command> commands = OpCode.parseCommands(scanner);

            return runOpCode(ip, commands);
        }

        private static int runOpCode(int ip, List<OpCode.Command> commands) {
            Set<Integer> seen = new HashSet<>();
            int last = 0;
            IntList register = new IntArrayList(Collections.nCopies(6, 0));
            register.set(0, 1);
            while (true) {
                int current = register.getInt(ip);
                if (current >= commands.size()) {
                    register.set(ip, register.getInt(ip) - 1);
                    LOGGER.info("Register = {}", register);
                    break;
                }
                OpCode.Command command = commands.get(current);
                LOGGER.trace("Register before: {} -> {}", register, command);
                OpCode.executeInstruction(register, command.code(), command.a(), command.b(), command.c());
                if ("eqrr".equals(command.code())) {
                    LOGGER.info("Command: {}, register: {}", command, register);

                    int value = register.getInt(3);
                    if (seen.add(value)) {
                        last = value;
                    } else {
                        return last;
                    }
                }
                LOGGER.trace("Register after: {}", register);
                register.set(ip, register.getInt(ip) + 1);
            }
            LOGGER.info("Register = {}", register);
            return -1;
        }

    }
}
