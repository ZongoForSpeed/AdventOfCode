package com.adventofcode.year2018;

import com.adventofcode.common.OpCode;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public final class Day16 {
    private static final List<String> INSTRUCTIONS = List.of(
            "addr",
            "addi",
            "mulr",
            "muli",
            "banr",
            "bani",
            "borr",
            "bori",
            "setr",
            "seti",
            "gtir",
            "gtri",
            "gtrr",
            "eqir",
            "eqri",
            "eqrr"
    );
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16.class);

    private Day16() {
        // No-Op
    }

    /**
     * --- Day 16: Chronal Classification ---
     *
     * As you see the Elves defend their hot chocolate successfully, you go back
     * to falling through time. This is going to become a problem.
     *
     * If you're ever going to return to your own time, you need to understand how
     * this device on your wrist works. You have a little while before you reach
     * your next destination, and with a bit of trial and error, you manage to
     * pull up a programming manual on the device's tiny screen.
     *
     * According to the manual, the device has four registers (numbered 0 through
     * 3) that can be manipulated by instructions containing one of 16 opcodes.
     * The registers start with the value 0.
     *
     * Every instruction consists of four values: an opcode, two inputs (named A
     * and B), and an output (named C), in that order. The opcode specifies the
     * behavior of the instruction and how the inputs are interpreted. The output,
     * C, is always treated as a register.
     *
     * In the opcode descriptions below, if something says "value A", it means to
     * take the number given as A literally. (This is also called an "immediate"
     * value.) If something says "register A", it means to use the number given as
     * A to read from (or write to) the register with that number. So, if the
     * opcode addi adds register A and value B, storing the result in register C,
     * and the instruction addi 0 7 3 is encountered, it would add 7 to the value
     * contained by register 0 and store the sum in register 3, never modifying
     * registers 0, 1, or 2 in the process.
     *
     * Many opcodes are similar except for how they interpret their arguments. The
     * opcodes fall into seven general categories:
     *
     * Addition:
     *
     * - addr (add register) stores into register C the result of adding
     * register A and register B.
     * - addi (add immediate) stores into register C the result of adding
     * register A and value B.
     *
     * Multiplication:
     *
     * - mulr (multiply register) stores into register C the result of
     * multiplying register A and register B.
     * - muli (multiply immediate) stores into register C the result of
     * multiplying register A and value B.
     *
     * Bitwise AND:
     *
     * - banr (bitwise AND register) stores into register C the result of the
     * bitwise AND of register A and register B.
     * - bani (bitwise AND immediate) stores into register C the result of the
     * bitwise AND of register A and value B.
     *
     * Bitwise OR:
     *
     * - borr (bitwise OR register) stores into register C the result of the bitwise OR of register A and register B.
     * - bori (bitwise OR immediate) stores into register C the result of the bitwise OR of register A and value B.
     *
     * Assignment:
     *
     * - setr (set register) copies the contents of register A into register C.
     * (Input B is ignored.)
     * - seti (set immediate) stores value A into register C. (Input B is
     * ignored.)
     *
     * Greater-than testing:
     *
     * - gtir (greater-than immediate/register) sets register C to 1 if value A
     * is greater than register B. Otherwise, register C is set to 0.
     * - gtri (greater-than register/immediate) sets register C to 1 if
     * register A is greater than value B. Otherwise, register C is set to 0.
     * - gtrr (greater-than register/register) sets register C to 1 if register
     * A is greater than register B. Otherwise, register C is set to 0.
     *
     * Equality testing:
     *
     * - eqir (equal immediate/register) sets register C to 1 if value A is
     * equal to register B. Otherwise, register C is set to 0.
     * - eqri (equal register/immediate) sets register C to 1 if register A is
     * equal to value B. Otherwise, register C is set to 0.
     * - eqrr (equal register/register) sets register C to 1 if register A is
     * equal to register B. Otherwise, register C is set to 0.
     *
     * Unfortunately, while the manual gives the name of each opcode, it doesn't
     * seem to indicate the number. However, you can monitor the CPU to see the
     * contents of the registers before and after instructions are executed to try
     * to work them out. Each opcode has a number from 0 through 15, but the
     * manual doesn't say which is which. For example, suppose you capture the
     * following sample:
     *
     * Before: [3, 2, 1, 1]
     * 9 2 1 2
     * After:  [3, 2, 2, 1]
     *
     * This sample shows the effect of the instruction 9 2 1 2 on the registers.
     * Before the instruction is executed, register 0 has value 3, register 1 has
     * value 2, and registers 2 and 3 have value 1. After the instruction is
     * executed, register 2's value becomes 2.
     *
     * The instruction itself, 9 2 1 2, means that opcode 9 was executed with A=2,
     * B=1, and C=2. Opcode 9 could be any of the 16 opcodes listed above, but
     * only three of them behave in a way that would cause the result shown in the
     * sample:
     *
     * - Opcode 9 could be mulr: register 2 (which has a value of 1) times
     * register 1 (which has a value of 2) produces 2, which matches the
     * value stored in the output register, register 2.
     * - Opcode 9 could be addi: register 2 (which has a value of 1) plus value
     * 1 produces 2, which matches the value stored in the output register,
     * register 2.
     * - Opcode 9 could be seti: value 2 matches the value stored in the output
     * register, register 2; the number given for B is irrelevant.
     *
     * None of the other opcodes produce the result captured in the sample.
     * Because of this, the sample above behaves like three opcodes.
     *
     * You collect many of these samples (the first section of your puzzle input).
     * The manual also includes a small test program (the second section of your
     * puzzle input) - you can ignore it for now.
     *
     * Ignoring the opcode numbers, how many samples in your puzzle input behave
     * like three or more opcodes?
     */
    public static int chronalClassificationPartOne(Scanner scanner) {
        List<Triple<IntList, IntList, int[]>> commands = OpCode.readCommands(scanner);
        LOGGER.info("Commands: {}", commands);

        List<int[]> inputs = readInputs(scanner);
        LOGGER.info("Inputs: {}", inputs);

        int count = 0;
        for (Triple<IntList, IntList, int[]> triple : commands) {
            Set<String> testInstructions = testInstructions(triple.getLeft(), triple.getMiddle(), triple.getRight());
            LOGGER.info("testInstructions: {}", testInstructions);
            if (testInstructions.size() >= 3) {
                count++;
            }
        }

        return count;
    }

    /**
     * --- Part Two ---
     *
     * Using the samples you collected, work out the number of each opcode and
     * execute the test program (the second section of your puzzle input).
     *
     * What value is contained in register 0 after executing the test program?
     */
    public static int chronalClassificationPartTwo(Scanner scanner) {
        List<Triple<IntList, IntList, int[]>> commands = OpCode.readCommands(scanner);
        LOGGER.trace("Commands: {}", commands);

        List<int[]> inputs = readInputs(scanner);
        LOGGER.trace("Inputs: {}", inputs);

        Map<Integer, Set<String>> testMapping = new HashMap<>();
        commands.stream().map(Triple::getRight).mapToInt(t -> t[0]).forEach(i -> testMapping.put(i, new HashSet<>(INSTRUCTIONS)));
        for (Triple<IntList, IntList, int[]> triple : commands) {
            int[] command = triple.getRight();
            Set<String> testInstructions = testInstructions(triple.getLeft(), triple.getMiddle(), command);
            LOGGER.debug("testInstructions: {} ==> {}", command[0], testInstructions);
            if (testInstructions.size() == 1) {
                testMapping.put(command[0], testInstructions);
            } else {
                testMapping.compute(command[0], (ignore, instructions) -> {
                    if (instructions == null) {
                        return testInstructions;
                    } else {
                        return new HashSet<>(SetUtils.intersection(instructions, testInstructions));
                    }
                });
            }
        }

        LOGGER.debug("Test mapping: {}", testMapping);
        while (testMapping.values().stream().anyMatch(m -> m.size() > 1)) {
            List<Map.Entry<Integer, Set<String>>> list = testMapping.entrySet().stream().filter(e -> e.getValue().size() == 1).toList();
            Set<Integer> keys = list.stream().map(Map.Entry::getKey).collect(Collectors.toSet());
            Set<String> values = list.stream().map(Map.Entry::getValue).flatMap(Collection::stream).collect(Collectors.toSet());
            for (Map.Entry<Integer, Set<String>> entry : testMapping.entrySet()) {
                if (!keys.contains(entry.getKey())) {
                    entry.getValue().removeAll(values);
                }
            }
        }

        Map<Integer, String> mapping = testMapping.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().iterator().next()));

        LOGGER.info("Mapping: {}", mapping);

        IntList register = new IntArrayList(Collections.nCopies(100, 0));
        for (int[] command : inputs) {
            String opcode = Objects.requireNonNull(mapping.get(command[0]), "Cannot find opcode for command " + Arrays.toString(command));
            OpCode.executeInstruction(register, opcode, command[1], command[2], command[3]);
        }

        LOGGER.info("Register: {}", register);

        return register.getInt(0);
    }

    private static List<int[]> readInputs(Scanner scanner) {
        List<int[]> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (StringUtils.isNotBlank(line)) {
                inputs.add(Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray());
            }
        }
        return inputs;
    }

    private static Set<String> testInstructions(IntList registerBefore, IntList registerAfter, int[] command) {
        Set<String> result = new HashSet<>();

        for (String instruction : INSTRUCTIONS) {
            IntList register = new IntArrayList(registerBefore);
            OpCode.executeInstruction(register, instruction, command[1], command[2], command[3]);
            if (ListUtils.isEqualList(register, registerAfter)) {
                result.add(instruction);
            }
        }

        return result;
    }

}
