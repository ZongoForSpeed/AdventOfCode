package com.adventofcode.year2024;

import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongComparators;
import it.unimi.dsi.fastutil.longs.LongList;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public final class Day17 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day17.class);

    private static final Pattern REGISTER_PATTERN = Pattern.compile("Register (\\w): (-?\\d+)");
    private static final Pattern PROGRAM_PATTERN = Pattern.compile("Program: (\\S+)");

    private Day17() {
        // No-Op
    }

    private static ChronospatialComputer readProgram(Scanner scanner) {
        long registerA = 0;
        long registerB = 0;
        long registerC = 0;

        IntList program = null;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (StringUtils.isBlank(line)) {
                continue;
            }

            Matcher matcher = REGISTER_PATTERN.matcher(line);
            if (matcher.matches()) {
                String register = matcher.group(1);
                int value = Integer.parseInt(matcher.group(2));

                switch (register) {
                    case "A" -> registerA = value;
                    case "B" -> registerB = value;
                    case "C" -> registerC = value;
                    default -> throw new IllegalStateException("Unknown register: " + register);
                }
            }
            matcher = PROGRAM_PATTERN.matcher(line);
            if (matcher.matches()) {
                String group = matcher.group(1);
                program = IntArrayList.toList(Splitter.on(',').splitToStream(group).mapToInt(Integer::parseInt));
            }
        }

        if (program == null) {
            throw new IllegalStateException("program is null");
        }

        LOGGER.info("A={}, B={}, C={}", registerA, registerB, registerC);
        LOGGER.info("program: {}", program);
        return new ChronospatialComputer(program, registerA, registerB, registerC);
    }

    /**
     * --- Day 17: Chronospatial Computer ---
     * <p>
     * The Historians push the button on their strange device, but this time, you
     * all just feel like you're falling.
     * <p>
     * "Situation critical", the device announces in a familiar voice.
     * "Bootstrapping process failed. Initializing debugger...."
     * <p>
     * The small handheld device suddenly unfolds into an entire computer! The
     * Historians look around nervously before one of them tosses it to you.
     * <p>
     * This seems to be a 3-bit computer: its program is a list of 3-bit numbers
     * (0 through 7), like 0,1,2,3. The computer also has three registers named A,
     * B, and C, but these registers aren't limited to 3 bits and can instead hold
     * any integer.
     * <p>
     * The computer knows eight instructions, each identified by a 3-bit number
     * (called the instruction's opcode). Each instruction also reads the 3-bit
     * number after it as an input; this is called its operand.
     * <p>
     * A number called the instruction pointer identifies the position in the
     * program from which the next opcode will be read; it starts at 0, pointing
     * at the first 3-bit number in the program. Except for jump instructions, the
     * instruction pointer increases by 2 after each instruction is processed (to
     * move past the instruction's opcode and its operand). If the computer tries
     * to read an opcode past the end of the program, it instead halts.
     * <p>
     * So, the program 0,1,2,3 would run the instruction whose opcode is 0 and
     * pass it the operand 1, then run the instruction having opcode 2 and pass it
     * the operand 3, then halt.
     * <p>
     * There are two types of operands; each instruction specifies the type of its
     * operand. The value of a literal operand is the operand itself. For example,
     * the value of the literal operand 7 is the number 7. The value of a combo
     * operand can be found as follows:
     * <p>
     * - Combo operands 0 through 3 represent literal values 0 through 3.
     * - Combo operand 4 represents the value of register A.
     * - Combo operand 5 represents the value of register B.
     * - Combo operand 6 represents the value of register C.
     * - Combo operand 7 is reserved and will not appear in valid programs.
     * <p>
     * The eight instructions are as follows:
     * <p>
     * The adv instruction (opcode 0) performs division. The numerator is the
     * value in the A register. The denominator is found by raising 2 to the power
     * of the instruction's combo operand. (So, an operand of 2 would divide A by
     * 4 (2^2); an operand of 5 would divide A by 2^B.) The result of the division
     * operation is truncated to an integer and then written to the A register.
     * <p>
     * The bxl instruction (opcode 1) calculates the bitwise XOR of register B and
     * the instruction's literal operand, then stores the result in register B.
     * <p>
     * The bst instruction (opcode 2) calculates the value of its combo operand
     * modulo 8 (thereby keeping only its lowest 3 bits), then writes that value
     * to the B register.
     * <p>
     * The jnz instruction (opcode 3) does nothing if the A register is 0.
     * However, if the A register is not zero, it jumps by setting the instruction
     * pointer to the value of its literal operand; if this instruction jumps, the
     * instruction pointer is not increased by 2 after this instruction.
     * <p>
     * The bxc instruction (opcode 4) calculates the bitwise XOR of register B and
     * register C, then stores the result in register B. (For legacy reasons, this
     * instruction reads an operand but ignores it.)
     * <p>
     * The out instruction (opcode 5) calculates the value of its combo operand
     * modulo 8, then outputs that value. (If a program outputs multiple values,
     * they are separated by commas.)
     * <p>
     * The bdv instruction (opcode 6) works exactly like the adv instruction
     * except that the result is stored in the B register. (The numerator is still
     * read from the A register.)
     * <p>
     * The cdv instruction (opcode 7) works exactly like the adv instruction
     * except that the result is stored in the C register. (The numerator is still
     * read from the A register.)
     * <p>
     * Here are some examples of instruction operation:
     * <p>
     * - If register C contains 9, the program 2,6 would set register B to 1.
     * - If register A contains 10, the program 5,0,5,1,5,4 would output 0,1,2.
     * - If register A contains 2024, the program 0,1,5,4,3,0 would output
     * 4,2,5,6,7,7,7,7,3,1,0 and leave 0 in register A.
     * - If register B contains 29, the program 1,7 would set register B to 26.
     * - If register B contains 2024 and register C contains 43690, the program
     * 4,0 would set register B to 44354.
     * <p>
     * The Historians' strange device has finished initializing its debugger and
     * is displaying some information about the program it is trying to run (your
     * puzzle input). For example:
     * <p>
     * Register A: 729
     * Register B: 0
     * Register C: 0
     * <p>
     * Program: 0,1,5,4,3,0
     * <p>
     * Your first task is to determine what the program is trying to output. To do
     * this, initialize the registers to the given values, then run the given
     * program, collecting any output produced by out instructions. (Always join
     * the values produced by out instructions with commas.) After the above
     * program halts, its final output will be 4,6,3,5,6,3,5,2,1,0.
     * <p>
     * Using the information provided by the debugger, initialize the registers to
     * the given values, then run the program. Once it halts, what do you get if
     * you use commas to join the values it output into a single string?
     */
    public static String partOne(Scanner scanner) {
        ChronospatialComputer program = readProgram(scanner);

        IntList out = program.runProgram();

        return out.intStream().mapToObj(String::valueOf).collect(Collectors.joining(","));
    }

    /**
     * --- Part Two ---
     * <p>
     * Digging deeper in the device's manual, you discover the problem: this
     * program is supposed to output another copy of the program! Unfortunately,
     * the value in register A seems to have been corrupted. You'll need to find a
     * new value to which you can initialize register A so that the program's
     * output instructions produce an exact copy of the program itself.
     * <p>
     * For example:
     * <p>
     * Register A: 2024
     * Register B: 0
     * Register C: 0
     * <p>
     * Program: 0,3,5,4,3,0
     * <p>
     * This program outputs a copy of itself if register A is instead initialized
     * to 117440. (The original initial value of register A, 2024, is ignored.)
     * <p>
     * What is the lowest positive initial value for register A that causes the
     * program to output a copy of itself?
     */
    static long partTwoBruteForce(Scanner scanner) {
        ChronospatialComputer program = readProgram(scanner);

        for (int i = 0; ; ++i) {
            IntList out = program.runProgram(i);
            if (out.size() == program.program().size() && ListUtils.isEqualList(out, program.program())) {
                return i;
            }
        }
    }

    public static long partTwo(Scanner scanner) {
        ChronospatialComputer program = readProgram(scanner);

        LongList result = new LongArrayList();
        LongList input = LongArrayList.toList(LongStream.range(0, 8));

        for (int j = 1; j <= program.program().size(); ++j) {
            LongList nextInput = new LongArrayList();
            for (long i : input) {
                IntList out = program.runProgram(i);
                if (ListUtils.isEqualList(program.program().subList(program.program().size() - j, program.program().size()), out)) {
                    if (out.size() == program.program().size()) {
                        result.add(i);
                    }
                    LOGGER.debug("i={} ==> {}", i, out);
                    for (int k = 0; k < 8; ++k) {
                        nextInput.add(i * 8L + k);
                    }
                }
            }
            input = nextInput;
        }

        result.sort(LongComparators.NATURAL_COMPARATOR);
        return result.getFirst();
    }

    private record ChronospatialComputer(IntList program, long registerA, long registerB, long registerC) {
        private IntList runProgram() {
            return runProgram(registerA);
        }

        private IntList runProgram(long regA) {
            long regB = registerB;
            long regC = registerC;
            IntList out = new IntArrayList();

            int instructionPointer = 0;
            while (instructionPointer < program.size()) {
                int instruction = program.getInt(instructionPointer);
                int operand = program.getInt(instructionPointer + 1);
                long combo = switch (operand) {
                    case 0, 1, 2, 3 -> operand;
                    case 4 -> regA;
                    case 5 -> regB;
                    case 6 -> regC;
                    default -> throw new IllegalStateException("Invalid operand " + operand);
                };
                LOGGER.trace("instruction: {}, operand: {}, combo: {}", instruction, operand, combo);

                switch (instruction) {
                    case 0 -> { // adv
                        regA /= (1L << combo);
                        instructionPointer += 2;
                    }
                    case 1 -> { // bxl
                        regB ^= operand;
                        instructionPointer += 2;
                    }
                    case 2 -> { // bst
                        regB = combo % 8;
                        instructionPointer += 2;
                    }
                    case 3 -> { // jnz
                        if (regA == 0) {
                            instructionPointer += 2;
                        } else {
                            LOGGER.trace("instruction pointer jump to {}", combo);
                            instructionPointer = (int) combo;
                        }
                    }
                    case 4 -> { // bxc
                        regB ^= regC;
                        instructionPointer += 2;
                    }
                    case 5 -> { // out
                        int output = (int) (combo % 8);
                        LOGGER.trace("Out << {}", output);
                        out.add(output);
                        instructionPointer += 2;
                    }
                    case 6 -> { // bdv
                        regB = regA / (1L << combo);
                        instructionPointer += 2;
                    }
                    case 7 -> { // cdv
                        regC = regA / (1L << combo);
                        instructionPointer += 2;
                    }
                    default -> throw new IllegalStateException("Unknown command " + instruction);
                }
                LOGGER.trace("A={}, B={}, C={}", regA, regB, regC);
            }

            LOGGER.trace("Out: {}", out);
            return out;
        }

    }
}
