package com.adventofcode.year2022;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.chars.CharStack;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day05 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day05.class);
    private static final Pattern PATTERN = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    private Day05() {
        // No-Op
    }

    /**
     * --- Day 5: Supply Stacks ---
     * <p>
     * The expedition can depart as soon as the final supplies have been unloaded
     * from the ships. Supplies are stored in stacks of marked crates, but because
     * the needed supplies are buried under many other crates, the crates need to
     * be rearranged.
     * <p>
     * The ship has a giant cargo crane capable of moving crates between stacks.
     * To ensure none of the crates get crushed or fall over, the crane operator
     * will rearrange them in a series of carefully-planned steps. After the
     * crates are rearranged, the desired crates will be at the top of each stack.
     * <p>
     * The Elves don't want to interrupt the crane operator during this delicate
     * procedure, but they forgot to ask her which crate will end up where, and
     * they want to be ready to unload them as soon as possible so they can
     * embark.
     * <p>
     * They do, however, have a drawing of the starting stacks of crates and the
     * rearrangement procedure (your puzzle input). For example:
     * <p>
     * [D]
     * [N] [C]
     * [Z] [M] [P]
     * 1   2   3
     * <p>
     * move 1 from 2 to 1
     * move 3 from 1 to 3
     * move 2 from 2 to 1
     * move 1 from 1 to 2
     * <p>
     * In this example, there are three stacks of crates. Stack 1 contains two
     * crates: crate Z is on the bottom, and crate N is on top. Stack 2 contains
     * three crates; from bottom to top, they are crates M, C, and D. Finally,
     * stack 3 contains a single crate, P.
     * <p>
     * Then, the rearrangement procedure is given. In each step of the procedure,
     * a quantity of crates is moved from one stack to a different stack. In the
     * first step of the above rearrangement procedure, one crate is moved from
     * stack 2 to stack 1, resulting in this configuration:
     * <p>
     * [D]
     * [N] [C]
     * [Z] [M] [P]
     * 1   2   3
     * <p>
     * In the second step, three crates are moved from stack 1 to stack 3. Crates
     * are moved one at a time, so the first crate to be moved (D) ends up below
     * the second and third crates:
     * <p>
     * [Z]
     * [N]
     * [C] [D]
     * [M] [P]
     * 1   2   3
     * <p>
     * Then, both crates are moved from stack 2 to stack 1. Again, because crates
     * are moved one at a time, crate C ends up below crate M:
     * <p>
     * [Z]
     * [N]
     * [M]     [D]
     * [C]     [P]
     * 1   2   3
     * <p>
     * Finally, one crate is moved from stack 1 to stack 2:
     * <p>
     * [Z]
     * [N]
     * [D]
     * [C] [M] [P]
     * 1   2   3
     * <p>
     * The Elves just need to know which crate will end up on top of each stack;
     * in this example, the top crates are C in stack 1, M in stack 2, and Z in
     * stack 3, so you should combine these together and give the Elves the
     * message CMZ.
     * <p>
     * After the rearrangement procedure completes, what crate ends up on top of
     * each stack?
     */
    static final class PartOne {

        private PartOne() {
            // No-Op
        }

        static String supplyStacks(Scanner scanner) {
            Int2ObjectMap<CharStack> stacks = new Int2ObjectOpenHashMap<>();
            List<Move> moves = new ArrayList<>();

            readInput(scanner, stacks, moves);

            for (Move move : moves) {
                CharStack from = stacks.get(move.from());
                CharStack to = stacks.get(move.to());
                for (int c = 0; c < move.count(); ++c) {
                    to.push(from.popChar());
                }
            }

            LOGGER.info("stacks = {}", stacks);

            return findTopCrate(stacks);
        }

    }

    /**
     * --- Part Two ---
     * <p>
     * As you watch the crane operator expertly rearrange the crates, you notice
     * the process isn't following your prediction.
     * <p>
     * Some mud was covering the writing on the side of the crane, and you quickly
     * wipe it away. The crane isn't a CrateMover 9000 - it's a CrateMover 9001.
     * <p>
     * The CrateMover 9001 is notable for many new and exciting features: air
     * conditioning, leather seats, an extra cup holder, and the ability to pick
     * up and move multiple crates at once.
     * <p>
     * Again considering the example above, the crates begin in the same
     * configuration:
     * <p>
     * [D]
     * [N] [C]
     * [Z] [M] [P]
     * 1   2   3
     * <p>
     * Moving a single crate from stack 2 to stack 1 behaves the same as before:
     * <p>
     * [D]
     * [N] [C]
     * [Z] [M] [P]
     * 1   2   3
     * <p>
     * However, the action of moving three crates from stack 1 to stack 3 means
     * that those three moved crates stay in the same order, resulting in this new
     * configuration:
     * <p>
     * [D]
     * [N]
     * [C] [Z]
     * [M] [P]
     * 1   2   3
     * <p>
     * Next, as both crates are moved from stack 2 to stack 1, they retain their
     * order as well:
     * <p>
     * [D]
     * [N]
     * [C]     [Z]
     * [M]     [P]
     * 1   2   3
     * <p>
     * Finally, a single crate is still moved from stack 1 to stack 2, but now
     * it's crate C that gets moved:
     * <p>
     * [D]
     * [N]
     * [Z]
     * [M] [C] [P]
     * 1   2   3
     * <p>
     * In this example, the CrateMover 9001 has put the crates in a totally
     * different order: MCD.
     * <p>
     * Before the rearrangement process finishes, update your simulation so that
     * the Elves know where they should stand to be ready to unload the final
     * supplies. After the rearrangement procedure completes, what crate ends up
     * on top of each stack?
     */
    static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        static String supplyStacks(Scanner scanner) {
            Int2ObjectMap<CharStack> stacks = new Int2ObjectOpenHashMap<>();
            List<Move> moves = new ArrayList<>();

            readInput(scanner, stacks, moves);

            for (Move move : moves) {
                CharStack from = stacks.get(move.from());
                CharStack to = stacks.get(move.to());
                CharStack local = new CharArrayList();
                for (int c = 0; c < move.count(); ++c) {
                    char crate = from.popChar();
                    local.push(crate);
                }

                while (!local.isEmpty()) {
                    char crate = local.popChar();
                    to.push(crate);
                }
            }

            LOGGER.info("stacks = {}", stacks);
            return findTopCrate(stacks);
        }
    }

    record Move(int count, int from, int to) {

    }

    private static void readInput(Scanner scanner, Int2ObjectMap<CharStack> stacks, List<Move> moves) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (StringUtils.isBlank(line)) {
                break;
            }
            if (line.contains("[")) {
                for (int i = 1; i <= line.length(); i += 4) {
                    char c = line.charAt(i);
                    if (c != ' ') {
                        int stack = (i / 4) + 1;
                        stacks.computeIfAbsent(stack, ignore -> new CharArrayList()).push(c);
                    }
                }
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                int count = Integer.parseInt(matcher.group(1));
                int from = Integer.parseInt(matcher.group(2));
                int to = Integer.parseInt(matcher.group(3));

                LOGGER.trace("count={}, from={}, to={}", count, from, to);
                moves.add(new Move(count, from, to));
            } else {
                throw new IllegalStateException("Cannot parse line: " + line);
            }
        }

        stacks.values().forEach(s -> Collections.reverse((CharList) s));

        LOGGER.info("stacks = {}", stacks);
        LOGGER.trace("moves = {}", moves);
    }

    private static String findTopCrate(Int2ObjectMap<CharStack> stacks) {
        String topCrate = stacks.int2ObjectEntrySet().stream()
                .map(e -> Pair.of(e.getIntKey(), e.getValue().topChar()))
                .sorted(Comparator.comparingInt(Pair::left))
                .map(Pair::right)
                .map(Object::toString)
                .collect(Collectors.joining());

        LOGGER.info("topCrate = {}", topCrate);
        return topCrate;
    }

}
