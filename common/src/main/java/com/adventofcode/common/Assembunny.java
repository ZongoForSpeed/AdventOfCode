package com.adventofcode.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.IntPredicate;

public final class Assembunny {
    private static final Logger LOGGER = LoggerFactory.getLogger(Assembunny.class);

    private Assembunny() {
        // No-Op
    }

    private static int getValue(Map<String, Integer> register, String key) {
        if (NumberUtils.isParsable(key)) {
            return Integer.parseInt(key);
        }
        return register.getOrDefault(key, 0);
    }

    public static Map<String, Integer> run(List<String[]> commands, Map<String, Integer> initialize) {
        return run(commands, initialize, out -> {
            LOGGER.debug("Output: {}", out);
            return false;
        });
    }

    public static Map<String, Integer> run(List<String[]> commands, Map<String, Integer> initialize, IntPredicate output) {
        Map<String, Integer> register = new HashMap<>(initialize);

        mainLoop:
        for (int current = 0; current < commands.size(); ) {
            String[] command = commands.get(current);
            LOGGER.trace("Running command {} with registers {}", command, register);
            switch (command[0]) {
                case "cpy" -> {
                    if (StringUtils.isAlpha(command[2])) {
                        int value = getValue(register, command[1]);
                        register.put(command[2], value);
                    } else {
                        LOGGER.warn("Invalid command at {}: {}", current, command);
                    }
                    ++current;
                }
                case "inc" -> {
                    if (StringUtils.isAlpha(command[1])) {
                        register.merge(command[1], 1, Integer::sum);
                    } else {
                        LOGGER.warn("Invalid command at {}: {}", current, command);
                    }
                    ++current;
                }
                case "dec" -> {
                    if (StringUtils.isAlpha(command[1])) {
                        register.compute(command[1], (_, value) -> value == null ? -1 : value - 1);
                    } else {
                        LOGGER.warn("Invalid command at {}: {}", current, command);
                    }
                    ++current;
                }
                case "out" -> {
                    int value = getValue(register, command[1]);
                    if (output.test(value)) {
                        break mainLoop;
                    }
                    ++current;
                }
                case "jnz" -> {
                    int value = getValue(register, command[1]);
                    int jump = getValue(register, command[2]);
                    if (value != 0 && jump != 0) {
                        current += jump;
                    } else {
                        ++current;
                    }
                }
                case "tgl" -> {
                    int value = current + getValue(register, command[1]);
                    if (value < commands.size()) {
                        String[] strings = commands.get(value);
                        String[] instructions = Arrays.copyOf(strings, strings.length);

                        instructions[0] = switch (strings[0]) {
                            case "inc" -> "dec";
                            case "dec", "tgl", "out" -> "inc";
                            case "jnz" -> "cpy";
                            case "cpy" -> "jnz";
                            default -> throw new IllegalStateException("Unknown command:" + strings[0]);
                        };
                        LOGGER.info("Command change at {} from {} to {}", value, strings, instructions);
                        commands.set(value, instructions);
                    }
                    ++current;
                }
                default -> throw new IllegalStateException("Unknown command:" + Arrays.toString(command));
            }
        }

        LOGGER.trace("Register: {}", register);
        return register;
    }

    public static Map<String, Integer> run(Scanner scanner, Map<String, Integer> initialize) {
        List<String[]> commands = new ArrayList<>();
        while (scanner.hasNextLine()) {
            commands.add(scanner.nextLine().split(" "));
        }

        return run(commands, initialize);
    }

    public static Map<String, Integer> run(Scanner scanner) {
        return run(scanner, Collections.emptyMap());
    }
}
