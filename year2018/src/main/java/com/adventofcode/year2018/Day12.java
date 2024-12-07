package com.adventofcode.year2018;

import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.Int2CharArrayMap;
import it.unimi.dsi.fastutil.ints.Int2CharMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public final class Day12 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day12.class);

    private Day12() {
        // No-Op
    }

    static class Plants {

        private final Int2CharMap map;

        Plants(Plants op) {
            map = new Int2CharArrayMap(op.map);
        }

        Plants(String state) {
            map = new Int2CharArrayMap();
            char[] charArray = state.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                map.put(i, charArray[i]);
            }
        }

        char get(int position) {
            return map.getOrDefault(position, '.');
        }

        String substring(int position) {
            StringBuilder sb = new StringBuilder();
            IntStream.rangeClosed(position - 2, position + 2).mapToObj(this::get).forEach(sb::append);
            return sb.toString();
        }

        void put(int position, char value) {
            map.put(position, value);
        }

        int minPosition() {
            return map.keySet().intStream().min().orElse(0);
        }

        int maxPosition() {
            return map.keySet().intStream().max().orElse(0);
        }

        @Override
        public String toString() {
            IntSet keySet = map.keySet();
            StringBuilder sb = new StringBuilder();
            IntStream.rangeClosed(keySet.intStream().min().orElse(0), keySet.intStream().max().orElse(0))
                    .mapToObj(this::get).forEach(sb::append);
            return sb.toString();
        }

        public int pots() {
            return map.keySet().intStream().filter(i -> map.get(i) == '#').sum();
        }

        public long[] keys() {
            return map.keySet().intStream().filter(i -> map.get(i) == '#').mapToLong(i -> i).sorted().toArray();
        }

        public void trim() {
            int[] keys = map.keySet().intStream().sorted().toArray();
            for (int position : keys) {
                if (map.get(position) == '.') {
                    map.remove(position);
                } else {
                    break;
                }
            }

            for (int i = keys.length - 1; i >= 0; i--) {
                int position = keys[i];
                if (map.get(position) == '.') {
                    map.remove(position);
                } else {
                    break;
                }
            }
        }

    }

    private static final Pattern PLANT_PATTERN = Pattern.compile("^(.*) => (.*)");

    /**
     * --- Day 12: Subterranean Sustainability ---
     * <p>
     * The year 518 is significantly more underground than your history books
     * implied. Either that, or you've arrived in a vast cavern network under the
     * North Pole.
     * <p>
     * After exploring a little, you discover a long tunnel that contains a row of
     * small pots as far as you can see to your left and right. A few of them
     * contain plants - someone is trying to grow things in these geothermally-
     * heated caves.
     * <p>
     * The pots are numbered, with 0 in front of you. To the left, the pots are
     * numbered -1, -2, -3, and so on; to the right, 1, 2, 3.... Your puzzle input
     * contains a list of pots from 0 to the right and whether they do (#) or do
     * not (.) currently contain a plant, the initial state. (No other pots
     * currently contain plants.) For example, an initial state of #..##....
     * indicates that pots 0, 3, and 4 currently contain plants.
     * <p>
     * Your puzzle input also contains some notes you find on a nearby table:
     * someone has been trying to figure out how these plants spread to nearby
     * pots. Based on the notes, for each generation of plants, a given pot has or
     * does not have a plant based on whether that pot (and the two pots on either
     * side of it) had a plant in the last generation. These are written as
     * LLCRR => N, where L are pots to the left, C is the current pot being
     * considered, R are the pots to the right, and N is whether the current pot
     * will have a plant in the next generation. For example:
     * <p>
     * - A note like ..#.. => . means that a pot that contains a plant but with
     * no plants within two pots of it will not have a plant in it during the
     * next generation.
     * - A note like ##.## => . means that an empty pot with two plants on each
     * side of it will remain empty in the next generation.
     * - A note like .##.# => # means that a pot has a plant in a given
     * generation if, in the previous generation, there were plants in that
     * pot, the one immediately to the left, and the one two pots to the
     * right, but not in the ones immediately to the right and two to the
     * left.
     * <p>
     * It's not clear what these plants are for, but you're sure it's important,
     * so you'd like to make sure the current configuration of plants is
     * sustainable by determining what will happen after 20 generations.
     * <p>
     * For example, given the following input:
     * <p>
     * initial state: #..#.#..##......###...###
     * <p>
     * ...## => #
     * ..#.. => #
     * .#... => #
     * .#.#. => #
     * .#.## => #
     * .##.. => #
     * .#### => #
     * #.#.# => #
     * #.### => #
     * ##.#. => #
     * ##.## => #
     * ###.. => #
     * ###.# => #
     * ####. => #
     * <p>
     * For brevity, in this example, only the combinations which do produce a
     * plant are listed. (Your input includes all possible combinations.) Then,
     * the next 20 generations will look like this:
     * <p>
     * 1         2         3
     * 0         0         0         0
     * 0: ...#..#.#..##......###...###...........
     * 1: ...#...#....#.....#..#..#..#...........
     * 2: ...##..##...##....#..#..#..##..........
     * 3: ..#.#...#..#.#....#..#..#...#..........
     * 4: ...#.#..#...#.#...#..#..##..##.........
     * 5: ....#...##...#.#..#..#...#...#.........
     * 6: ....##.#.#....#...#..##..##..##........
     * 7: ...#..###.#...##..#...#...#...#........
     * 8: ...#....##.#.#.#..##..##..##..##.......
     * 9: ...##..#..#####....#...#...#...#.......
     * 10: ..#.#..#...#.##....##..##..##..##......
     * 11: ...#...##...#.#...#.#...#...#...#......
     * 12: ...##.#.#....#.#...#.#..##..##..##.....
     * 13: ..#..###.#....#.#...#....#...#...#.....
     * 14: ..#....##.#....#.#..##...##..##..##....
     * 15: ..##..#..#.#....#....#..#.#...#...#....
     * 16: .#.#..#...#.#...##...#...#.#..##..##...
     * 17: ..#...##...#.#.#.#...##...#....#...#...
     * 18: ..##.#.#....#####.#.#.#...##...##..##..
     * 19: .#..###.#..#.#.#######.#.#.#..#.#...#..
     * 20: .#....##....#####...#######....#.#..##.
     * <p>
     * The generation is shown along the left, where 0 is the initial state. The
     * pot numbers are shown along the top, where 0 labels the center pot,
     * negative-numbered pots extend to the left, and positive pots extend toward
     * the right. Remember, the initial state begins at pot 0, which is not the
     * leftmost pot used in this example.
     * <p>
     * After one generation, only seven plants remain. The one in pot 0 matched
     * the rule looking for ..#.., the one in pot 4 matched the rule looking for
     * .#.#., pot 9 matched .##.., and so on.
     * <p>
     * In this example, after 20 generations, the pots shown as # contain plants,
     * the furthest left of which is pot -2, and the furthest right of which is
     * pot 34. Adding up all the numbers of plant-containing pots after the 20th
     * generation produces 325.
     * <p>
     * After 20 generations, what is the sum of the numbers of all pots which
     * contain a plant?
     * <p>
     * --- Part Two ---
     * <p>
     * You realize that 20 generations aren't enough. After all, these plants will
     * need to last another 1500 years to even reach your timeline, not to mention
     * your future.
     * <p>
     * After fifty billion (50000000000) generations, what is the sum of the
     * numbers of all pots which contain a plant?
     */
    public static long getPlants(Scanner scanner, long generations) {
        Plants state = null;
        Map<String, Character> moves = new HashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (StringUtils.isBlank(line)) {
                continue;
            }

            if (line.startsWith("initial state: ")) {
                state = new Plants(line.substring("initial state: ".length()));
                LOGGER.info("initial state: '{}'", state);
            } else {
                Matcher matcher = PLANT_PATTERN.matcher(line);
                if (matcher.find()) {
                    String left = matcher.group(1);
                    String right = matcher.group(2);
                    LOGGER.info("'{}' => '{}'", left, right);
                    moves.put(left, right.charAt(0));
                } else {
                    LOGGER.error("Cannot parse line '{}'", line);
                }
            }
        }

        if (state == null) {
            throw new IllegalStateException("Null initial state !");
        }

        LOGGER.info("Moves: {}", moves);

        Map<String, Pair<Plants, Long>> cache = new HashMap<>();

        for (long i = 0; i < generations; ++i) {
            Plants nextState = new Plants(state);

            int minPosition = state.minPosition();
            int maxPosition = state.maxPosition();

            for (int position = minPosition - 5; position <= maxPosition + 5; ++position) {
                String substring = state.substring(position);
                nextState.put(position, moves.getOrDefault(substring, '.'));
            }
            nextState.trim();
            state = new Plants(nextState);
            LOGGER.info("{}: {} ==> {}", i + 1, state, state.pots());
            String hash = state.toString();
            Pair<Plants, Long> pair = cache.get(hash);
            if (pair == null) {
                cache.put(hash, Pair.of(state, i + 1));
            } else {
                long[] keys = state.keys();
                long iShift = i + 1 - pair.right();
                long pShift = (keys[0] - pair.left().keys()[0]) * iShift * (generations - i - 1);
                for (int j = 0; j < keys.length; j++) {
                    keys[j] += pShift;
                }

                LOGGER.info("Keys = {}", keys);
                return Arrays.stream(keys).sum();
            }
        }
        LOGGER.info("Pots: {}", state.pots());
        return state.pots();
    }
}
