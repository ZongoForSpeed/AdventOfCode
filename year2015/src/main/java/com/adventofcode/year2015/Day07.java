package com.adventofcode.year2015;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day07 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day07.class);
    private static final Pattern ASSEMBLY_PATTERN = Pattern.compile("^(.*) -> (.*)$");

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
        List<String> in = Splitter.on(' ').splitToList(command);
        switch (in.size()) {
            case 1:
                value = getWireValue(cache, commands, in.getFirst());
                break;
            case 2:
                if ("NOT".equals(in.getFirst())) {
                    value = 65535 - getWireValue(cache, commands, in.get(1));
                } else {
                    throw new IllegalStateException("Unknown command type: " + command);
                }
                break;
            case 3:
                switch (in.get(1)) {
                    case "AND" -> {
                        int v1 = getWireValue(cache, commands, in.getFirst());
                        int v2 = getWireValue(cache, commands, in.get(2));
                        value = v1 & v2;
                    }
                    case "OR" -> {
                        int v1 = getWireValue(cache, commands, in.getFirst());
                        int v2 = getWireValue(cache, commands, in.get(2));
                        value = v1 | v2;
                    }
                    case "LSHIFT" -> {
                        int v1 = getWireValue(cache, commands, in.getFirst());
                        value = v1 << Integer.parseInt(in.get(2));
                    }
                    case "RSHIFT" -> {
                        int v1 = getWireValue(cache, commands, in.getFirst());
                        value = v1 >> Integer.parseInt(in.get(2));
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
            String line = scanner.nextLine();
            Matcher matcher = ASSEMBLY_PATTERN.matcher(line);
            if (matcher.find()) {
                commands.put(matcher.group(2), matcher.group(1));
            } else {
                LOGGER.error("Cannot parse line '{}'", line);
            }
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
     * <p>
     * This year, Santa brought little Bobby Tables a set of wires and
     * bitwise logic gates! Unfortunately, little Bobby is a little under the
     * recommended age range, and he needs help assembling the circuit.
     * <p>
     * Each wire has an identifier (some lowercase letters) and can carry a 16-bit
     * signal (a number from 0 to 65535). A signal is provided to each wire by a
     * gate, another wire, or some specific value. Each wire can only get a signal
     * from one source, but can provide its signal to multiple destinations. A
     * gate provides no signal until all of its inputs have a signal.
     * <p>
     * The included instructions booklet describes how to connect the parts
     * together: x AND y -> z means to connect wires x and y to an AND gate, and
     * then connect its output to wire z.
     * <p>
     * For example:
     * <p>
     * - 123 -> x means that the signal 123 is provided to wire x.
     * - x AND y -> z means that the bitwise AND of wire x and wire y is
     * provided to wire z.
     * - p LSHIFT 2 -> q means that the value from wire p is left-shifted by 2
     * and then provided to wire q.
     * - NOT e -> f means that the bitwise complement of the value from wire e
     * is provided to wire f.
     * <p>
     * Other possible gates include OR (bitwise OR) and RSHIFT (right-shift). If,
     * for some reason, you'd like to emulate the circuit instead, almost all
     * programming languages (for example, C, JavaScript, or Python) provide
     * operators for these gates.
     * <p>
     * For example, here is a simple circuit:
     * <p>
     * 123 -> x
     * 456 -> y
     * x AND y -> d
     * x OR y -> e
     * x LSHIFT 2 -> f
     * y RSHIFT 2 -> g
     * NOT x -> h
     * NOT y -> i
     * <p>
     * After it is run, these are the signals on the wires:
     * <p>
     * d: 72
     * e: 507
     * f: 492
     * g: 114
     * h: 65412
     * i: 65079
     * x: 123
     * y: 456
     * <p>
     * In little Bobby's kit's instructions booklet (provided as your puzzle
     * input), what signal is ultimately provided to wire a?
     * <p>
     * Your puzzle answer was 46065.
     */
    public static Map<String, Integer> runAssemblyPartOne(Scanner scanner) {
        return runAssembly(scanner, null);
    }

    /**
     * --- Part Two ---
     * <p>
     * Now, take the signal you got on wire a, override wire b to that signal, and
     * reset the other wires (including wire a). What new signal is ultimately
     * provided to wire a?
     * <p>
     * Your puzzle answer was 14134.
     */
    public static Map<String, Integer> runAssemblyPartTwo(Scanner scanner) {
        return runAssembly(scanner, m -> m.put("b", "46065"));
    }
}
