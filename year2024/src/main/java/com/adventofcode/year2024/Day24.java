package com.adventofcode.year2024;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day24 {
    static final Logger LOGGER = LoggerFactory.getLogger(Day24.class);
    private static final Pattern VALUE_PATTERN = Pattern.compile("(\\w{3}): (\\d)");
    private static final Pattern OPERATION_PATTERN = Pattern.compile("(\\w{3}) (\\w+) (\\w{3}) -> (\\w{3})");

    private Day24() {
        // No-Op
    }

    /**
     * --- Day 24: Crossed Wires ---
     * <p>
     * You and The Historians arrive at the edge of a large grove somewhere in the
     * jungle. After the last incident, the Elves installed a small device that
     * monitors the fruit. While The Historians search the grove, one of them asks
     * if you can take a look at the monitoring device; apparently, it's been
     * malfunctioning recently.
     * <p>
     * The device seems to be trying to produce a number through some boolean
     * logic gates. Each gate has two inputs and one output. The gates all operate
     * on values that are either true (1) or false (0).
     * <p>
     * - AND gates output 1 if both inputs are 1; if either input is 0, these
     * gates output 0.
     * - OR gates output 1 if one or both inputs is 1; if both inputs are 0,
     * these gates output 0.
     * - XOR gates output 1 if the inputs are different; if the inputs are the
     * same, these gates output 0.
     * <p>
     * Gates wait until both inputs are received before producing output; wires
     * can carry 0, 1 or no value at all. There are no loops; once a gate has
     * determined its output, the output will not change until the whole system is
     * reset. Each wire is connected to at most one gate output, but can be
     * connected to many gate inputs.
     * <p>
     * Rather than risk getting shocked while tinkering with the live system, you
     * write down all of the gate connections and initial wire values (your puzzle
     * input) so you can consider them in relative safety. For example:
     * <p>
     * x00: 1
     * x01: 1
     * x02: 1
     * y00: 0
     * y01: 1
     * y02: 0
     * <p>
     * x00 AND y00 -> z00
     * x01 XOR y01 -> z01
     * x02 OR y02 -> z02
     * <p>
     * Because gates wait for input, some wires need to start with a value (as
     * inputs to the entire system). The first section specifies these values. For
     * example, x00: 1 means that the wire named x00 starts with the value 1 (as
     * if a gate is already outputting that value onto that wire).
     * <p>
     * The second section lists all of the gates and the wires connected to them.
     * For example, x00 AND y00 -> z00 describes an instance of an AND gate which
     * has wires x00 and y00 connected to its inputs and which will write its
     * output to wire z00.
     * <p>
     * In this example, simulating these gates eventually causes 0 to appear on
     * wire z00, 0 to appear on wire z01, and 1 to appear on wire z02.
     * <p>
     * Ultimately, the system is trying to produce a number by combining the bits
     * on all wires starting with z. z00 is the least significant bit, then z01,
     * then z02, and so on.
     * <p>
     * In this example, the three output bits form the binary number 100 which is
     * equal to the decimal number 4.
     * <p>
     * Here's a larger example:
     * <p>
     * x00: 1
     * x01: 0
     * x02: 1
     * x03: 1
     * x04: 0
     * y00: 1
     * y01: 1
     * y02: 1
     * y03: 1
     * y04: 1
     * <p>
     * ntg XOR fgs -> mjb
     * y02 OR x01 -> tnw
     * kwq OR kpj -> z05
     * x00 OR x03 -> fst
     * tgd XOR rvg -> z01
     * vdt OR tnw -> bfw
     * bfw AND frj -> z10
     * ffh OR nrd -> bqk
     * y00 AND y03 -> djm
     * y03 OR y00 -> psh
     * bqk OR frj -> z08
     * tnw OR fst -> frj
     * gnj AND tgd -> z11
     * bfw XOR mjb -> z00
     * x03 OR x00 -> vdt
     * gnj AND wpb -> z02
     * x04 AND y00 -> kjc
     * djm OR pbm -> qhw
     * nrd AND vdt -> hwm
     * kjc AND fst -> rvg
     * y04 OR y02 -> fgs
     * y01 AND x02 -> pbm
     * ntg OR kjc -> kwq
     * psh XOR fgs -> tgd
     * qhw XOR tgd -> z09
     * pbm OR djm -> kpj
     * x03 XOR y03 -> ffh
     * x00 XOR y04 -> ntg
     * bfw OR bqk -> z06
     * nrd XOR fgs -> wpb
     * frj XOR qhw -> z04
     * bqk OR frj -> z07
     * y03 OR x01 -> nrd
     * hwm AND bqk -> z03
     * tgd XOR rvg -> z12
     * tnw OR pbm -> gnj
     * <p>
     * After waiting for values on all wires starting with z, the wires in this
     * system have the following values:
     * <p>
     * bfw: 1
     * bqk: 1
     * djm: 1
     * ffh: 0
     * fgs: 1
     * frj: 1
     * fst: 1
     * gnj: 1
     * hwm: 1
     * kjc: 0
     * kpj: 1
     * kwq: 0
     * mjb: 1
     * nrd: 1
     * ntg: 0
     * pbm: 1
     * psh: 1
     * qhw: 1
     * rvg: 0
     * tgd: 0
     * tnw: 1
     * vdt: 1
     * wpb: 0
     * z00: 0
     * z01: 0
     * z02: 0
     * z03: 1
     * z04: 0
     * z05: 1
     * z06: 1
     * z07: 1
     * z08: 1
     * z09: 1
     * z10: 1
     * z11: 0
     * z12: 0
     * <p>
     * Combining the bits from all wires starting with z produces the binary
     * number 0011111101000. Converting this number to decimal produces 2024.
     * <p>
     * Simulate the system of gates and wires. What decimal number does it output
     * on the wires starting with z?
     */
    public static long partOne(Scanner scanner) {
        Map<String, Boolean> wires = crossedWires(scanner);
        return wires.entrySet().stream()
                .filter(e -> e.getKey().startsWith("z"))
                .sorted(Map.Entry.<String, Boolean>comparingByKey().reversed())
                .mapToLong(e -> e.getValue() ? 1 : 0)
                .reduce(0L, (a, b) -> (a << 1) + b);
    }

    public static Map<String, Boolean> crossedWires(Scanner scanner) {
        Map<String, Boolean> values = readValues(scanner);
        LOGGER.info("values: {}", values);

        Map<String, Gate> gates = readGates(scanner);
        LOGGER.info("gates: {}", gates);

        Map<String, List<String>> dependencies = new HashMap<>();
        for (Gate gate : gates.values()) {
            dependencies.computeIfAbsent(gate.input1(), ignore -> new ArrayList<>()).add(gate.output());
            dependencies.computeIfAbsent(gate.input2(), ignore -> new ArrayList<>()).add(gate.output());
        }

        Queue<Gate> gateQueue = new ArrayDeque<>();

        Map<String, Integer> counts = new HashMap<>();
        for (String variable : values.keySet()) {
            List<String> variables = dependencies.get(variable);
            if (variables == null) {
                continue;
            }
            for (String s : variables) {
                Integer count = counts.merge(s, 1, Integer::sum);
                if (count == 2) {
                    gateQueue.add(gates.get(s));
                }
            }
        }

        while (!gateQueue.isEmpty()) {
            Gate gate = gateQueue.poll();
            gate.apply(values);

            List<String> variables = dependencies.get(gate.output());
            if (variables == null) {
                continue;
            }
            for (String s : variables) {
                Integer count = counts.merge(s, 1, Integer::sum);
                if (count == 2) {
                    gateQueue.add(gates.get(s));
                }
            }
        }
        return values;
    }

    private static Map<String, Gate> readGates(Scanner scanner) {
        Map<String, Gate> gates = new HashMap<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = OPERATION_PATTERN.matcher(line);
            if (matcher.matches()) {
                String variable1 = matcher.group(1);
                Operation operation = Operation.valueOf(matcher.group(2));
                String variable2 = matcher.group(3);
                String variable3 = matcher.group(4);


                gates.put(variable3, new Gate(variable1, variable2, operation, variable3));
            } else {
                throw new IllegalStateException("Cannot parse line: " + line);
            }
        }
        return gates;
    }

    private static Map<String, Boolean> readValues(Scanner scanner) {
        Map<String, Boolean> values = new HashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (StringUtils.isBlank(line)) {
                break;
            }
            Matcher matcher = VALUE_PATTERN.matcher(line);
            if (matcher.matches()) {
                String variable = matcher.group(1);
                boolean value = "1".equals(matcher.group(2));

                values.put(variable, value);
            } else {
                throw new IllegalStateException("Cannot parse line: " + line);
            }
        }
        return values;
    }

    /**
     * --- Part Two ---
     * <p>
     * After inspecting the monitoring device more closely, you determine that the
     * system you're simulating is trying to add two binary numbers.
     * <p>
     * Specifically, it is treating the bits on wires starting with x as one
     * binary number, treating the bits on wires starting with y as a second
     * binary number, and then attempting to add those two numbers together. The
     * output of this operation is produced as a binary number on the wires
     * starting with z. (In all three cases, wire 00 is the least significant bit,
     * then 01, then 02, and so on.)
     * <p>
     * The initial values for the wires in your puzzle input represent just one
     * instance of a pair of numbers that sum to the wrong value. Ultimately, any
     * two binary numbers provided as input should be handled correctly. That is,
     * for any combination of bits on wires starting with x and wires starting
     * with y, the sum of the two numbers those bits represent should be produced
     * as a binary number on the wires starting with z.
     * <p>
     * For example, if you have an addition system with four x wires, four y
     * wires, and five z wires, you should be able to supply any four-bit number
     * on the x wires, any four-bit number on the y numbers, and eventually find
     * the sum of those two numbers as a five-bit number on the z wires. One of
     * the many ways you could provide numbers to such a system would be to pass
     * 11 on the x wires (1011 in binary) and 13 on the y wires (1101 in binary):
     * <p>
     * x00: 1
     * x01: 1
     * x02: 0
     * x03: 1
     * y00: 1
     * y01: 0
     * y02: 1
     * y03: 1
     * <p>
     * If the system were working correctly, then after all gates are finished
     * processing, you should find 24 (11+13) on the z wires as the five-bit
     * binary number 11000:
     * <p>
     * z00: 0
     * z01: 0
     * z02: 0
     * z03: 1
     * z04: 1
     * <p>
     * Unfortunately, your actual system needs to add numbers with many more bits
     * and therefore has many more wires.
     * <p>
     * Based on forensic analysis of scuff marks and scratches on the device, you
     * can tell that there are exactly four pairs of gates whose output wires have
     * been swapped. (A gate can only be in at most one such pair; no gate's
     * output was swapped multiple times.)
     * <p>
     * For example, the system below is supposed to find the bitwise AND of the
     * six-bit number on x00 through x05 and the six-bit number on y00 through y05
     * and then write the result as a six-bit number on z00 through z05:
     * <p>
     * x00: 0
     * x01: 1
     * x02: 0
     * x03: 1
     * x04: 0
     * x05: 1
     * y00: 0
     * y01: 0
     * y02: 1
     * y03: 1
     * y04: 0
     * y05: 1
     * <p>
     * x00 AND y00 -> z05
     * x01 AND y01 -> z02
     * x02 AND y02 -> z01
     * x03 AND y03 -> z03
     * x04 AND y04 -> z04
     * x05 AND y05 -> z00
     * <p>
     * However, in this example, two pairs of gates have had their output wires
     * swapped, causing the system to produce wrong answers. The first pair of
     * gates with swapped outputs is x00 AND y00 -> z05 and x05 AND y05 -> z00;
     * the second pair of gates is x01 AND y01 -> z02 and x02 AND y02 -> z01.
     * Correcting these two swaps results in this system that works as intended
     * for any set of initial values on wires that start with x or y:
     * <p>
     * x00 AND y00 -> z00
     * x01 AND y01 -> z01
     * x02 AND y02 -> z02
     * x03 AND y03 -> z03
     * x04 AND y04 -> z04
     * x05 AND y05 -> z05
     * <p>
     * In this example, two pairs of gates have outputs that are involved in a
     * swap. By sorting their output wires' names and joining them with commas, the
     * list of wires involved in swaps is z00,z01,z02,z05.
     * <p>
     * Of course, your actual system is much more complex than this, and the gates
     * that need their outputs swapped could be anywhere, not just attached to a
     * wire starting with z. If you were to determine that you need to swap output
     * wires aaa with eee, ooo with z99, bbb with ccc, and aoc with z24, your
     * answer would be aaa,aoc,bbb,ccc,eee,ooo,z24,z99.
     * <p>
     * Your system of gates and wires has four pairs of gates which need their
     * output wires swapped - eight wires in total. Determine which four pairs of
     * gates need their outputs swapped so that your system correctly performs
     * addition; what do you get if you sort the names of the eight wires involved
     * in a swap and then join those names with commas?
     */
    public static String partTwo(Scanner scanner) {
        var unused = readValues(scanner);

        Map<String, Gate> gates = readGates(scanner);
        LOGGER.trace("gates: {}", gates);

        List<Gate> xyAdds = new ArrayList<>();
        List<Gate> xyCarries = new ArrayList<>();
        List<Gate> zOuts = new ArrayList<>();
        List<Gate> ands = new ArrayList<>();
        List<Gate> carries = new ArrayList<>();

        for (Gate gate : gates.values()) {
            if (gate.input1('x') || gate.input1('y') || gate.input2('x') || gate.input2('y')) {
                if (gate.input1("x00") || gate.input1("y00")) {
                    continue;
                }
                switch (gate.operation()) {
                    case XOR -> xyAdds.add(gate);
                    case AND -> xyCarries.add(gate);
                    default -> {
                        // No-Op
                    }
                }
                continue;
            }
            switch (gate.operation()) {
                case AND -> ands.add(gate);
                case OR -> carries.add(gate);
                case XOR -> zOuts.add(gate);
            }
        }

        Set<String> wrong = new TreeSet<>();

        for (Gate xy : xyAdds) {
            if (xy.output('z') || zOuts.stream().noneMatch(xy::matches)) {
                wrong.add(xy.output());
            }
        }

        for (Gate xy : xyCarries) {
            if (xy.output('z') || carries.stream().noneMatch(xy::matches)) {
                wrong.add(xy.output());
            }
        }

        for (Gate z : zOuts) {
            if (!z.output('z')) {
                wrong.add(z.output());
            }
        }

        long bitCount = gates.values().stream().map(Gate::input1)
                .filter(x -> x.startsWith("x"))
                .count();
        String lastZ = "z%2d".formatted(bitCount + 1);

        for (Gate gate : carries) {
            if (gate.output('z') && !gate.output(lastZ)) {
                wrong.add(gate.output());
            }
        }

        for (Gate gate : ands) {
            if (gate.output('z')) {
                wrong.add(gate.output());
            }
        }

        LOGGER.info("lastZ: {}", lastZ);

        LOGGER.info("wrong: {}", wrong);
        return String.join(",", wrong);
    }

    enum Operation {
        AND,
        OR,
        XOR;

        boolean apply(boolean a, boolean b) {
            return switch (this) {
                case AND -> a && b;
                case OR -> a || b;
                case XOR -> a ^ b;
            };
        }
    }

    record Gate(String input1, String input2, Operation operation, String output) {
        void apply(Map<String, Boolean> values) {
            Boolean b1 = values.get(input1);
            Boolean b2 = values.get(input2);
            if (b1 == null || b2 == null) {
                throw new IllegalStateException("Found null value in " + this);
            }
            values.put(output, operation.apply(b1, b2));
        }

        boolean input1(char c) {
            return input1.charAt(0) == c;
        }

        boolean input2(char c) {
            return input2.charAt(0) == c;
        }

        boolean output(char c) {
            return output.charAt(0) == c;
        }

        boolean input1(String c) {
            return input1.equals(c);
        }

        boolean input2(String c) {
            return input2.equals(c);
        }

        boolean output(String c) {
            return output.equals(c);
        }

        boolean matches(Gate g) {
            return g.input1(output()) || g.input2(output());
        }
    }
}
