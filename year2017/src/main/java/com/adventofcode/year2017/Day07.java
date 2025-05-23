package com.adventofcode.year2017;

import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import org.apache.commons.collections4.SetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day07 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day07.class);
    private static final Pattern PATTERN = Pattern.compile("(\\w+) \\((\\d+)\\)");

    private Day07() {
        // No-Op
    }

    private static final Pattern PROGRAM_PATTERN = Pattern.compile("^(.*) -> (.*)$");

    private static Map<Program, List<Program>> readPrograms(Scanner scanner) {
        Map<String, Program> programs = new HashMap<>();
        Map<String, List<String>> map = new HashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PROGRAM_PATTERN.matcher(line);
            if (matcher.find()) {
                Program program = Program.of(matcher.group(1));
                programs.put(program.name(), program);
                map.put(program.name(), Splitter.on(", ").splitToList(matcher.group(2)));
            } else {
                Program program = Program.of(line);
                programs.put(program.name(), program);
            }
        }

        Map<Program, List<Program>> graph = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            Program program = programs.get(entry.getKey());
            List<Program> list = entry.getValue().stream().map(programs::get).toList();
            graph.put(program, list);
        }
        return graph;
    }

    private static int weight(Map<Program, List<Program>> graph, Program node) {
        List<Program> programs = graph.get(node);
        if (programs == null) {
            return node.weight();
        }

        int weight = programs.stream().mapToInt(p -> weight(graph, p)).sum();
        return weight + node.weight();
    }

    private static OptionalInt balanceWeight(Map<Program, List<Program>> graph, Program node) {
        List<Program> programs = graph.get(node);
        if (programs == null) {
            return OptionalInt.empty();
        }

        Map<Program, Integer> collect = programs.stream().collect(Collectors.toMap(p -> p, p -> weight(graph, p)));
        Int2IntMap counts = new Int2IntOpenHashMap();
        collect.values().forEach(v -> counts.mergeInt(v, 1, Integer::sum));

        Int2IntMap values = new Int2IntOpenHashMap();
        counts.int2IntEntrySet().forEach(e -> values.put(e.getIntValue(), e.getIntKey()));
        LOGGER.debug("balanceWeight for {}: {}", node, values);
        if (values.size() == 1) {
            return OptionalInt.empty();
        }

        int first = values.get(1);
        Program program = collect.entrySet().stream().filter(e -> e.getValue() == first).map(Map.Entry::getKey).findFirst().orElseThrow();
        OptionalInt balanceWeight = balanceWeight(graph, program);
        if (balanceWeight.isPresent()) {
            return balanceWeight;
        }

        int objectiveWeight = collect.entrySet().stream().filter(e -> !program.equals(e.getKey())).mapToInt(Map.Entry::getValue).findFirst().orElseThrow();
        Integer currentWeight = Objects.requireNonNull(collect.get(program), "Cannot find current weight of program");

        return OptionalInt.of(program.weight() + objectiveWeight - currentWeight);

    }

    /**
     * --- Part Two ---
     * <p>
     * The programs explain the situation: they can't get down. Rather, they could
     * get down, if they weren't expending all of their energy trying to keep the
     * tower balanced. Apparently, one program has the wrong weight, and until
     * it's fixed, they're stuck here.
     * <p>
     * For any program holding a disc, each program standing on that disc forms a
     * sub-tower. Each of those sub-towers are supposed to be the same weight, or
     * the disc itself isn't balanced. The weight of a tower is the sum of the
     * weights of the programs in that tower.
     * <p>
     * In the example above, this means that for ugml's disc to be balanced, gyxo,
     * ebii, and jptl must all have the same weight, and they do: 61.
     * <p>
     * However, for tknk to be balanced, each of the programs standing on its disc
     * and all programs above it must each match. This means that the following
     * sums must all be the same:
     * <p>
     * - ugml + (gyxo + ebii + jptl) = 68 + (61 + 61 + 61) = 251
     * - padx + (pbga + havc + qoyq) = 45 + (66 + 66 + 66) = 243
     * - fwft + (ktlj + cntj + xhth) = 72 + (57 + 57 + 57) = 243
     * <p>
     * As you can see, tknk's disc is unbalanced: ugml's stack is heavier than the
     * other two. Even though the nodes above ugml are balanced, ugml itself is
     * too heavy: it needs to be 8 units lighter for its stack to weigh 243 and
     * keep the towers balanced. If this change were made, its weight would be 60.
     * <p>
     * Given that exactly one program is the wrong weight, what would its weight
     * need to be to balance the entire tower?
     * <p>
     * Your puzzle answer was 362.
     */
    static OptionalInt balanceWeight(Scanner scanner) {
        Map<Program, List<Program>> graph = readPrograms(scanner);

        Set<Program> difference = getBottomProgram(graph);
        Program bottom = difference.stream().findFirst().orElseThrow();
        int weight = weight(graph, bottom);
        LOGGER.info("Weight {} = {}", bottom, weight);

        OptionalInt balanceWeight = balanceWeight(graph, bottom);

        LOGGER.info("balanceWeight {} = {}", bottom, balanceWeight);
        return balanceWeight;
    }

    private static Set<Program> getBottomProgram(Map<Program, List<Program>> graph) {
        Set<Program> leftNodes = graph.keySet();
        Set<Program> rightNodes = graph.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());

        LOGGER.trace("leftNodes: {}", leftNodes);
        LOGGER.trace("rightNodes: {}", rightNodes);

        Set<Program> difference = SetUtils.difference(leftNodes, rightNodes);
        difference = Set.copyOf(difference);
        LOGGER.info("difference: {}", difference);
        return difference;
    }

    /**
     * --- Day 7: Recursive Circus ---
     * <p>
     * Wandering further through the circuits of the computer, you come upon a
     * tower of programs that have gotten themselves into a bit of trouble. A
     * recursive algorithm has gotten out of hand, and now they're balanced
     * precariously in a large tower.
     * <p>
     * One program at the bottom supports the entire tower. It's holding a large
     * disc, and on the disc are balanced several more sub-towers. At the bottom
     * of these sub-towers, standing on the bottom disc, are other programs, each
     * holding their own disc, and so on. At the very tops of these sub-sub-
     * sub-...-towers, many programs stand simply keeping the disc below them
     * balanced but with no disc of their own.
     * <p>
     * You offer to help, but first you need to understand the structure of these
     * towers. You ask each program to yell out their name, their weight, and (if
     * they're holding a disc) the names of the programs immediately above them
     * balancing on that disc. You write this information down (your puzzle
     * input). Unfortunately, in their panic, they don't do this in an orderly
     * fashion; by the time you're done, you're not sure which program gave which
     * information.
     * <p>
     * For example, if your list is the following:
     * <p>
     * pbga (66)
     * xhth (57)
     * ebii (61)
     * havc (66)
     * ktlj (57)
     * fwft (72) -> ktlj, cntj, xhth
     * qoyq (66)
     * padx (45) -> pbga, havc, qoyq
     * tknk (41) -> ugml, padx, fwft
     * jptl (61)
     * ugml (68) -> gyxo, ebii, jptl
     * gyxo (61)
     * cntj (57)
     * <p>
     * ...then you would be able to recreate the structure of the towers that
     * looks like this:
     * <p>
     * gyxo
     * /
     * ugml - ebii
     * /      \
     * |         jptl
     * |
     * |         pbga
     * /        /
     * tknk --- padx - havc
     * \        \
     * |         qoyq
     * |
     * |         ktlj
     * \      /
     * fwft - cntj
     * \
     * xhth
     * <p>
     * In this example, tknk is at the bottom of the tower (the bottom program),
     * and is holding up ugml, padx, and fwft. Those programs are, in turn,
     * holding up other programs; in this example, none of those programs are
     * holding up any other programs, and are all the tops of their own towers.
     * (The actual tower balancing in front of you is much larger.)
     * <p>
     * Before you're ready to help them, you need to make sure your information is
     * correct. What is the name of the bottom program?
     * <p>
     * Your puzzle answer was vvsvez.
     */
    static Set<String> getBottomProgram(Scanner scanner) {
        Map<Program, List<Program>> graph = readPrograms(scanner);

        Set<Program> difference = getBottomProgram(graph);
        return difference.stream().map(Program::name).collect(Collectors.toSet());
    }

    record Program(String name, int weight) {
        public static Program of(String name, int weight) {
            return new Program(name, weight);
        }

        public static Program of(String input) {
            Matcher matcher = PATTERN.matcher(input);
            if (matcher.matches()) {
                return of(matcher.group(1), Integer.parseInt(matcher.group(2)));
            } else {
                throw new IllegalStateException("Cannot parse: " + input);
            }
        }
    }
}
