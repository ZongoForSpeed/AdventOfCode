package com.adventofcode.year2017;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Day16 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16.class);

    private Day16() {
        // No-Op
    }

    /**
     * --- Day 16: Permutation Promenade ---
     *
     * You come upon a very unusual sight; a group of programs here appear to be
     * dancing.
     *
     * There are sixteen programs in total, named a through p. They start by
     * standing in a line: a stands in position 0, b stands in position 1, and so
     * on until p, which stands in position 15.
     *
     * The programs' dance consists of a sequence of dance moves:
     *
     *   - Spin, written sX, makes X programs move from the end to the front, but
     *     maintain their order otherwise. (For example, s3 on abcde produces
     *     cdeab).
     *   - Exchange, written xA/B, makes the programs at positions A and B swap
     *     places.
     *   - Partner, written pA/B, makes the programs named A and B swap places.
     *
     * For example, with only five programs standing in a line (abcde), they could
     * do the following dance:
     *
     *   - s1, a spin of size 1: eabcd.
     *   - x3/4, swapping the last two programs: eabdc.
     *   - pe/b, swapping programs e and b: baedc.
     *
     * After finishing their dance, the programs end up in order baedc.
     *
     * You watch the dance for a while and record their dance moves (your puzzle
     * input). In what order are the programs standing after their dance?
     *
     * Your puzzle answer was dcmlhejnifpokgba.
     *
     * --- Part Two ---
     *
     * Now that you're starting to get a feel for the dance moves, you turn your
     * attention to the dance as a whole.
     *
     * Keeping the positions they ended up in from their previous dance, the
     * programs perform it again and again: including the first dance, a total of
     * one billion (1000000000) times.
     *
     * In the example above, their second dance would begin with the order baedc,
     * and use the same dance moves:
     *
     *   - s1, a spin of size 1: cbaed.
     *   - x3/4, swapping the last two programs: cbade.
     *   - pe/b, swapping programs e and b: ceadb.
     *
     * In what order are the programs standing after their billion dances?
     *
     * Your puzzle answer was 316.
     */
    static String danceMoves(String input, int length, int iteration) {
        LinkedList<Character> programs = new LinkedList<>();
        for (int c = 0; c < length; c++) {
            programs.add((char) (c + 'a'));
        }

        List<DanceMove> moves = new ArrayList<>();
        String[] split = input.split(",");
        for (String command : split) {
            switch (command.charAt(0)) {
                case 's' -> moves.add(Spin.of(command));
                case 'x' -> moves.add(Exchange.of(command));
                case 'p' -> moves.add(Partner.of(command));
                default -> throw new IllegalStateException("Unknown type of command: " + command);
            }
        }

        Map<String, Integer> cache = new HashMap<>();

        boolean foundCycle = false;
        for (int i = 0; i < iteration; ++i) {
            moves.forEach(m -> m.dance(programs));

            if (!foundCycle) {
                String s = toString(programs);
                Integer previous = cache.put(s, i);
                if (previous != null) {
                    int diff = i - previous;
                    foundCycle = true;
                    LOGGER.info("Diff : {} : {} / {}", diff, i, previous);
                    iteration = i + iteration % diff;
                    LOGGER.info("New iteration value = {}", iteration);
                }
            }
        }

        return toString(programs);
    }

    private static String toString(LinkedList<Character> programs) {
        StringBuilder sb = new StringBuilder();
        programs.forEach(sb::append);
        return sb.toString();
    }

    interface DanceMove {
        void dance(LinkedList<Character> programs);
    }

    record Spin(int x) implements DanceMove {
        public static Spin of(String command) {
            int x = Integer.parseInt(command.substring(1));
            return new Spin(x);
        }

        @Override
        public void dance(LinkedList<Character> programs) {
            for (int i = 0; i < x; ++i) {
                Character last = programs.removeLast();
                programs.addFirst(last);
            }
        }
    }

    record Exchange(int first, int second) implements DanceMove {
        public static Exchange of(String command) {
            int[] array = Arrays.stream(command.substring(1).split("/"))
                    .mapToInt(Integer::parseInt)
                    .toArray();
            return new Exchange(array[0], array[1]);
        }

        @Override
        public void dance(LinkedList<Character> programs) {
            Character firstChar = programs.get(first);
            Character secondChar = programs.get(second);
            programs.set(first, secondChar);
            programs.set(second, firstChar);
        }
    }

    record Partner(char first, char second) implements DanceMove {
        public static Partner of(String command) {
            char first = command.charAt(1);
            char second = command.charAt(3);
            return new Partner(first, second);
        }

        @Override
        public void dance(LinkedList<Character> programs) {
            programs.replaceAll(this::replace);
        }

        private Character replace(Character c) {
            if (c == first) {
                return second;
            } else if (c == second) {
                return first;
            } else {
                return c;
            }
        }
    }
}
