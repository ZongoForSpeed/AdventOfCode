package com.adventofcode.year2023;

import com.adventofcode.maths.Arithmetic;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day08 {
    private static final Pattern PATTERN = Pattern.compile("(\\w+) = \\((\\w+), (\\w+)\\)");
    private static final Logger LOGGER = LoggerFactory.getLogger(Day08.class);

    private Day08() {
        // No-Op
    }

    /**
     * --- Day 8: Haunted Wasteland ---
     *
     * You're still riding a camel across Desert Island when you spot a sandstorm
     * quickly approaching. When you turn to warn the Elf, she disappears before
     * your eyes! To be fair, she had just finished warning you about ghosts a few
     * minutes ago.
     *
     * One of the camel's pouches is labeled "maps" - sure enough, it's full of
     * documents (your puzzle input) about how to navigate the desert. At least,
     * you're pretty sure that's what they are; one of the documents contains a
     * list of left/right instructions, and the rest of the documents seem to
     * describe some kind of network of labeled nodes.
     *
     * It seems like you're meant to use the left/right instructions to navigate
     * the network. Perhaps if you have the camel follow the same instructions,
     * you can escape the haunted wasteland!
     *
     * After examining the maps for a bit, two nodes stick out: AAA and ZZZ. You
     * feel like AAA is where you are now, and you have to follow the left/right
     * instructions until you reach ZZZ.
     *
     * This format defines each node of the network individually. For example:
     *
     * RL
     *
     * AAA = (BBB, CCC)
     * BBB = (DDD, EEE)
     * CCC = (ZZZ, GGG)
     * DDD = (DDD, DDD)
     * EEE = (EEE, EEE)
     * GGG = (GGG, GGG)
     * ZZZ = (ZZZ, ZZZ)
     *
     * Starting with AAA, you need to look up the next element based on the next
     * left/right instruction in your input. In this example, start with AAA and
     * go right (R) by choosing the right element of AAA, CCC. Then, L means to
     * choose the left element of CCC, ZZZ. By following the left/right
     * instructions, you reach ZZZ in 2 steps.
     *
     * Of course, you might not find ZZZ right away. If you run out of left/right
     * instructions, repeat the whole sequence of instructions as necessary: RL
     * really means RLRLRLRLRLRLRLRL... and so on. For example, here is a
     * situation that takes 6 steps to reach ZZZ:
     *
     * LLR
     *
     * AAA = (BBB, BBB)
     * BBB = (AAA, ZZZ)
     * ZZZ = (ZZZ, ZZZ)
     *
     * Starting at AAA, follow the left/right instructions. How many steps are
     * required to reach ZZZ?
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static int countSteps(Scanner scanner) {
            Instructions instructions = readInstructions(scanner);

            String current = "AAA";
            int count = 0;
            while (!"ZZZ".equals(current)) {
                current = instructions.next(current, count);
                ++count;
            }
            return count;
        }
    }

    /**
     * --- Part Two ---
     *
     * The sandstorm is upon you and you aren't any closer to escaping the
     * wasteland. You had the camel follow the instructions, but you've barely
     * left your starting position. It's going to take significantly more steps to
     * escape!
     *
     * What if the map isn't for people - what if the map is for ghosts? Are
     * ghosts even bound by the laws of spacetime? Only one way to find out.
     *
     * After examining the maps a bit longer, your attention is drawn to a curious
     * fact: the number of nodes with names ending in A is equal to the number
     * ending in Z! If you were a ghost, you'd probably just start at every node
     * that ends with A and follow all of the paths at the same time until they
     * all simultaneously end up at nodes that end with Z.
     *
     * For example:
     *
     * LR
     *
     * 11A = (11B, XXX)
     * 11B = (XXX, 11Z)
     * 11Z = (11B, XXX)
     * 22A = (22B, XXX)
     * 22B = (22C, 22C)
     * 22C = (22Z, 22Z)
     * 22Z = (22B, 22B)
     * XXX = (XXX, XXX)
     *
     * Here, there are two starting nodes, 11A and 22A (because they both end with
     * A). As you follow each left/right instruction, use that instruction to
     * simultaneously navigate away from both nodes you're currently on. Repeat
     * this process until all of the nodes you're currently on end with Z. (If
     * only some of the nodes you're on end with Z, they act like any other node
     * and you continue as normal.) In this example, you would proceed as follows:
     *
     *   - Step 0: You are at 11A and 22A.
     *   - Step 1: You choose all of the left paths, leading you to 11B and 22B.
     *   - Step 2: You choose all of the right paths, leading you to 11Z and 22C.
     *   - Step 3: You choose all of the left paths, leading you to 11B and 22Z.
     *   - Step 4: You choose all of the right paths, leading you to 11Z and 22B.
     *   - Step 5: You choose all of the left paths, leading you to 11B and 22C.
     *   - Step 6: You choose all of the right paths, leading you to 11Z and 22Z.
     *
     * So, in this example, you end up entirely on nodes that end in Z after 6
     * steps.
     *
     * Simultaneously start on every node that ends with A. How many steps does it
     * take before you're only on nodes that end with Z?
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        public static long countSteps(Scanner scanner) {
            Instructions instructions = readInstructions(scanner);

            return instructions.map().keySet().stream()
                    .filter(s -> s.endsWith("A"))
                    .map(current -> findCycle(instructions, current))
                    .mapToLong(IntIntPair::firstInt)
                    .reduce(1L, Arithmetic::lcm);
        }

        private static IntIntPair findCycle(Instructions instructions, String start) {
            int count = 0;
            String current = start;

            Map<String, Integer> positions = new HashMap<>();

            while (true) {
                if (current.endsWith("Z")) {
                    Integer previous = positions.put(current, count);
                    if (previous != null) {
                        IntIntPair cycle = IntIntPair.of(previous, count - previous);
                        LOGGER.info("{} ==> {}", current, cycle);
                        return cycle;
                    }
                }
                current = instructions.next(current, count);
                ++count;

            }
        }

    }


    private static Instructions readInstructions(Scanner scanner) {
        String instructions = scanner.nextLine();
        scanner.nextLine();

        Map<String, Pair<String, String>> map = new HashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                String source = matcher.group(1);
                String left = matcher.group(2);
                String right = matcher.group(3);
                map.put(source, Pair.of(left, right));
            } else {
                throw new IllegalStateException("Cannot read line: " + line);
            }
        }

        LOGGER.info("instructions = {}, map = {}", instructions, map);
        return new Instructions(instructions, map);
    }

    private record Instructions(String instructions, Map<String, Pair<String, String>> map) {
        String next(String current, int count) {
            char c = instructions.charAt(count % instructions.length());
            return switch (c) {
                case 'L' -> map.get(current).getLeft();
                case 'R' -> map.get(current).getRight();
                default -> throw new IllegalStateException("Unknown instruction : " + c);
            };
        }
    }
}
