package com.adventofcode.year2015;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;

public final class Day07 {
    private Day07() {
        // No-Op
    }

    public static int getWireValue(Map<String, Integer> cache, Map<String, String> commands, String wire) {
        if (NumberUtils.isParsable(wire)) {
            return Integer.parseInt(wire);
        }

        Integer value = cache.get(wire);
        if (value != null) {
            return value;
        }

        String command = commands.get(wire);
        if (command == null) {
            throw new IllegalStateException("Unknown wire: " + wire);
        }
        String[] in = command.split(" ");
        switch (in.length) {
            case 1:
                value = getWireValue(cache, commands, in[0]);
                break;
            case 2:
                if ("NOT".equals(in[0])) {
                    value = 65535 - getWireValue(cache, commands, in[1]);
                } else {
                    throw new IllegalStateException("Unknown command type: " + command);
                }
                break;
            case 3:
                switch (in[1]) {
                    case "AND" -> {
                        int v1 = getWireValue(cache, commands, in[0]);
                        int v2 = getWireValue(cache, commands, in[2]);
                        value = v1 & v2;
                    }
                    case "OR" -> {
                        int v1 = getWireValue(cache, commands, in[0]);
                        int v2 = getWireValue(cache, commands, in[2]);
                        value = v1 | v2;
                    }
                    case "LSHIFT" -> {
                        int v1 = getWireValue(cache, commands, in[0]);
                        value = v1 << Integer.parseInt(in[2]);
                    }
                    case "RSHIFT" -> {
                        int v1 = getWireValue(cache, commands, in[0]);
                        value = v1 >> Integer.parseInt(in[2]);
                    }
                    default -> throw new IllegalStateException("Unknown command type: " + command);
                }
                break;
            default:
                throw new IllegalStateException("Unknown command type: " + command);
        }

        cache.put(wire, value);
        return value;
    }

    public static Map<String, Integer> runAssembly(Scanner scanner, Consumer<Map<String, String>> override) {
        Map<String, String> commands = new HashMap<>();

        while (scanner.hasNextLine()) {
            String[] split = scanner.nextLine().split(" -> ");
            commands.put(split[1], split[0]);
        }

        if (override != null) {
            override.accept(commands);
        }

        Set<String> keys = Set.copyOf(commands.keySet());

        Map<String, Integer> cache = new HashMap<>();
        for (String key : keys) {
            getWireValue(cache, commands, key);
        }

        return cache;
    }

    /**
     * --- Day 7: Some Assembly Required ---
     *
     * This year, Santa brought little Bobby Tables a set of wires and
     * bitwise logic gates! Unfortunately, little Bobby is a little under the
     * recommended age range, and he needs help assembling the circuit.
     *
     * Each wire has an identifier (some lowercase letters) and can carry a 16-bit
     * signal (a number from 0 to 65535). A signal is provided to each wire by a
     * gate, another wire, or some specific value. Each wire can only get a signal
     * from one source, but can provide its signal to multiple destinations. A
     * gate provides no signal until all of its inputs have a signal.
     *
     * The included instructions booklet describes how to connect the parts
     * together: x AND y -> z means to connect wires x and y to an AND gate, and
     * then connect its output to wire z.
     *
     * For example:
     *
     *   - 123 -> x means that the signal 123 is provided to wire x.
     *   - x AND y -> z means that the bitwise AND of wire x and wire y is
     *     provided to wire z.
     *   - p LSHIFT 2 -> q means that the value from wire p is left-shifted by 2
     *     and then provided to wire q.
     *   - NOT e -> f means that the bitwise complement of the value from wire e
     *     is provided to wire f.
     *
     * Other possible gates include OR (bitwise OR) and RSHIFT (right-shift). If,
     * for some reason, you'd like to emulate the circuit instead, almost all
     * programming languages (for example, C, JavaScript, or Python) provide
     * operators for these gates.
     *
     * For example, here is a simple circuit:
     *
     * 123 -> x
     * 456 -> y
     * x AND y -> d
     * x OR y -> e
     * x LSHIFT 2 -> f
     * y RSHIFT 2 -> g
     * NOT x -> h
     * NOT y -> i
     *
     * After it is run, these are the signals on the wires:
     *
     * d: 72
     * e: 507
     * f: 492
     * g: 114
     * h: 65412
     * i: 65079
     * x: 123
     * y: 456
     *
     * In little Bobby's kit's instructions booklet (provided as your puzzle
     * input), what signal is ultimately provided to wire a?
     *
     * Your puzzle answer was 46065.
     */
    public static Map<String, Integer> runAssemblyPartOne(Scanner scanner) {
        return runAssembly(scanner, null);
    }

    /**
     * --- Part Two ---
     *
     * Now, take the signal you got on wire a, override wire b to that signal, and
     * reset the other wires (including wire a). What new signal is ultimately
     * provided to wire a?
     *
     * Your puzzle answer was 14134.
     */
    public static Map<String, Integer> runAssemblyPartTwo(Scanner scanner) {
        return runAssembly(scanner, m -> m.put("b", "46065"));
    }
}
