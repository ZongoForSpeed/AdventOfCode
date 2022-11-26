package com.adventofcode;

import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class OpCode {

    private static final Pattern PATTERN_BEFORE = Pattern.compile("Before: \\[(\\d*), (\\d*), (\\d*), (\\d*)]");
    private static final Pattern PATTERN_AFTER = Pattern.compile("After:  \\[(\\d*), (\\d*), (\\d*), (\\d*)]");

    private OpCode() {
        // No-Op
    }

    public static void executeInstruction(IntList register, String opcode, int a, int b, int c) {
        switch (opcode) {
            case "addr" -> {
                OptionalInt valueA = getRegister(register, a);
                OptionalInt valueB = getRegister(register, b);
                if (valueA.isPresent() && valueB.isPresent()) {
                    setRegister(register, c, valueA.getAsInt() + valueB.getAsInt());
                }
            }
            case "addi" -> {
                OptionalInt valueA = getRegister(register, a);
                if (valueA.isPresent()) {
                    setRegister(register, c, valueA.getAsInt() + b);
                }
            }
            case "mulr" -> {
                OptionalInt valueA = getRegister(register, a);
                OptionalInt valueB = getRegister(register, b);
                if (valueA.isPresent() && valueB.isPresent()) {
                    setRegister(register, c, valueA.getAsInt() * valueB.getAsInt());
                }
            }
            case "muli" -> {
                OptionalInt valueA = getRegister(register, a);
                if (valueA.isPresent()) {
                    setRegister(register, c, valueA.getAsInt() * b);
                }
            }
            case "banr" -> {
                OptionalInt valueA = getRegister(register, a);
                OptionalInt valueB = getRegister(register, b);
                if (valueA.isPresent() && valueB.isPresent()) {
                    setRegister(register, c, valueA.getAsInt() & valueB.getAsInt());
                }
            }
            case "bani" -> {
                OptionalInt valueA = getRegister(register, a);
                if (valueA.isPresent()) {
                    setRegister(register, c, valueA.getAsInt() & b);
                }
            }
            case "borr" -> {
                OptionalInt valueA = getRegister(register, a);
                OptionalInt valueB = getRegister(register, b);
                if (valueA.isPresent() && valueB.isPresent()) {
                    setRegister(register, c, valueA.getAsInt() | valueB.getAsInt());
                }
            }
            case "bori" -> {
                OptionalInt valueA = getRegister(register, a);
                if (valueA.isPresent()) {
                    setRegister(register, c, valueA.getAsInt() | b);
                }
            }
            case "setr" -> {
                OptionalInt valueA = getRegister(register, a);
                if (valueA.isPresent()) {
                    setRegister(register, c, valueA.getAsInt());
                }
            }
            case "seti" -> register.set(c, a);
            case "gtir" -> {
                OptionalInt valueB = getRegister(register, b);
                if (valueB.isPresent()) {
                    setRegister(register, c, a > valueB.getAsInt() ? 1 : 0);
                }
            }
            case "gtri" -> {
                OptionalInt valueA = getRegister(register, a);
                if (valueA.isPresent()) {
                    setRegister(register, c, valueA.getAsInt() > b ? 1 : 0);
                }
            }
            case "gtrr" -> {
                OptionalInt valueA = getRegister(register, a);
                OptionalInt valueB = getRegister(register, b);
                if (valueA.isPresent() && valueB.isPresent()) {
                    setRegister(register, c, valueA.getAsInt() > valueB.getAsInt() ? 1 : 0);
                }
            }
            case "eqir" -> {
                OptionalInt valueB = getRegister(register, b);
                if (valueB.isPresent()) {
                    setRegister(register, c, a == valueB.getAsInt() ? 1 : 0);
                }
            }
            case "eqri" -> {
                OptionalInt valueA = getRegister(register, a);
                if (valueA.isPresent()) {
                    setRegister(register, c, valueA.getAsInt() == b ? 1 : 0);
                }
            }
            case "eqrr" -> {
                OptionalInt valueA = getRegister(register, a);
                OptionalInt valueB = getRegister(register, b);
                if (valueA.isPresent() && valueB.isPresent()) {
                    setRegister(register, c, valueA.getAsInt() == valueB.getAsInt() ? 1 : 0);
                }
            }
            default -> throw new IllegalStateException("Unknown opcode '" + opcode + "'");
        }
    }

    private static OptionalInt getRegister(IntList register, int position) {
        if (position < 0 || position >= register.size()) {
            return OptionalInt.empty();
        }

        return OptionalInt.of(register.getInt(position));
    }

    private static void setRegister(IntList register, int position, int value) {
        if (position >= register.size()) {
            register.addAll(Collections.nCopies(position - register.size() + 1, 0));
        }
        register.set(position, value);
    }

    public static List<Triple<IntList, IntList, int[]>> readCommands(Scanner scanner) {
        IntList registerBefore = null;
        IntList registerAfter = null;
        int[] command = null;

        List<Triple<IntList, IntList, int[]>> commands = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (StringUtils.isBlank(line)) {
                if (registerAfter == null || registerBefore == null) {
                    break;
                }
                commands.add(Triple.of(registerBefore, registerAfter, command));
                registerBefore = null;
                registerAfter = null;
                continue;
            }

            Matcher matcher = PATTERN_BEFORE.matcher(line);
            if (matcher.matches()) {
                int value1 = Integer.parseInt(matcher.group(1));
                int value2 = Integer.parseInt(matcher.group(2));
                int value3 = Integer.parseInt(matcher.group(3));
                int value4 = Integer.parseInt(matcher.group(4));

                registerBefore = IntList.of(value1, value2, value3, value4);
                continue;
            }
            matcher = PATTERN_AFTER.matcher(line);
            if (matcher.matches()) {
                int value1 = Integer.parseInt(matcher.group(1));
                int value2 = Integer.parseInt(matcher.group(2));
                int value3 = Integer.parseInt(matcher.group(3));
                int value4 = Integer.parseInt(matcher.group(4));

                registerAfter = IntList.of(value1, value2, value3, value4);
                continue;
            }
            command = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).toArray();
        }
        return commands;
    }

}
