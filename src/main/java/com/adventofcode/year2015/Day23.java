package com.adventofcode.year2015;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Day23 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23.class);

    private Day23() {
        // No-Op
    }

    /**
     * --- Day 23: Opening the Turing Lock ---
     *
     * Little Jane Marie just got her very first computer for Christmas from some
     * unknown benefactor. It comes with instructions and an example program, but
     * the computer itself seems to be malfunctioning. She's curious what the
     * program does, and would like you to help her run it.
     *
     * The manual explains that the computer supports two registers and six
     * instructions (truly, it goes on to remind the reader, a state-of-the-art
     * technology). The registers are named a and b, can hold any
     * non-negative integer, and begin with a value of 0. The instructions are as
     * follows:
     *
     *   - hlf r sets register r to half its current value, then continues with
     *     the next instruction.
     *   - tpl r sets register r to triple its current value, then continues with
     *     the next instruction.
     *   - inc r increments register r, adding 1 to it, then continues with the
     *     next instruction.
     *   - jmp offset is a jump; it continues with the instruction offset away
     *     relative to itself.
     *   - jie r, offset is like jmp, but only jumps if register r is even ("jump
     *     if even").
     *   - jio r, offset is like jmp, but only jumps if register r is 1 ("jump if
     *     one", not odd).
     *
     * All three jump instructions work with an offset relative to that
     * instruction. The offset is always written with a prefix + or - to indicate
     * the direction of the jump (forward or backward, respectively). For example,
     * jmp +1 would simply continue with the next instruction, while jmp +0 would
     * continuously jump back to itself forever.
     *
     * The program exits when it tries to run an instruction beyond the ones
     * defined.
     *
     * For example, this program sets a to 2, because the jio instruction causes
     * it to skip the tpl instruction:
     *
     * inc a
     * jio a, +2
     * tpl a
     * inc a
     *
     * What is the value in register b when the program in your puzzle input is finished executing?
     *
     * Your puzzle answer was 255.
     */
    /**
     * --- Part Two ---
     *
     * The unknown benefactor is very thankful for releasi-- er, helping little
     * Jane Marie with her computer. Definitely not to distract you, what is the
     * value in register b after the program is finished executing if register a
     * starts as 1 instead?
     *
     * Your puzzle answer was 1289.
     */
    public static Map<Character, Long> runInstructions(Scanner scanner, int initialValue) {
        List<String> instructions = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            instructions.add(line);
        }

        LOGGER.info("Instructions: {}", instructions);
        int currentLine = 0;
        Map<Character, AtomicLong> registers = new HashMap<>();
        registers.put('a', new AtomicLong(initialValue));
        registers.put('b', new AtomicLong(0));
        while (currentLine < instructions.size()) {
            String instruction = instructions.get(currentLine);
            String[] split = instruction.split(" ");
            String command = split[0];
            switch (command) {
                case "hlf" -> {
                    registers.get(split[1].charAt(0)).updateAndGet(l -> l / 2);
                    currentLine++;
                }
                case "tpl" -> {
                    registers.get(split[1].charAt(0)).updateAndGet(l -> l * 3);
                    currentLine++;
                }
                case "inc" -> {
                    registers.get(split[1].charAt(0)).incrementAndGet();
                    currentLine++;
                }
                case "jmp" -> currentLine += Integer.parseInt(split[1]);
                case "jie" -> {
                    AtomicLong register = registers.get(split[1].charAt(0));
                    if (register.get() % 2 == 0) {
                        currentLine += Integer.parseInt(split[2]);
                    } else {
                        currentLine++;
                    }
                }
                case "jio" -> {
                    AtomicLong register = registers.get(split[1].charAt(0));
                    if (register.get() == 1) {
                        currentLine += Integer.parseInt(split[2]);
                    } else {
                        currentLine++;
                    }
                }
                default -> throw new IllegalStateException("Unknown command: " + instruction);
            }
            LOGGER.debug("Registers after instruction '{}': {}", instruction, registers);
        }
        LOGGER.info("Registers: {}", registers);
        return registers.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get()));
    }
}
