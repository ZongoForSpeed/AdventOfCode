package com.adventofcode.year2023;

import com.google.common.base.Splitter;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class Day12 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12.class);

    private Day12() {
        // No-Op
    }

    /**
     * --- Day 12: Hot Springs ---
     * <p>
     * You finally reach the hot springs! You can see steam rising from secluded
     * areas attached to the primary, ornate building.
     * <p>
     * As you turn to enter, the researcher stops you. "Wait - I thought you were
     * looking for the hot springs, weren't you?" You indicate that this
     * definitely looks like hot springs to you.
     * <p>
     * "Oh, sorry, common mistake! This is actually the onsen! The hot springs are
     * next door."
     * <p>
     * You look in the direction the researcher is pointing and suddenly notice
     * the massive metal helixes towering overhead. "This way!"
     * <p>
     * It only takes you a few more steps to reach the main gate of the massive
     * fenced-off area containing the springs. You go through the gate and into a
     * small administrative building.
     * <p>
     * "Hello! What brings you to the hot springs today? Sorry they're not very
     * hot right now; we're having a lava shortage at the moment." You ask about
     * the missing machine parts for Desert Island.
     * <p>
     * "Oh, all of Gear Island is currently offline! Nothing is being manufactured
     * at the moment, not until we get more lava to heat our forges. And our
     * springs. The springs aren't very springy unless they're hot!"
     * <p>
     * "Say, could you go up and see why the lava stopped flowing? The springs are
     * too cold for normal operation, but we should be able to find one springy
     * enough to launch you up there!"
     * <p>
     * There's just one problem - many of the springs have fallen into disrepair,
     * so they're not actually sure which springs would even be safe to use! Worse
     * yet, their condition records of which springs are damaged (your puzzle
     * input) are also damaged! You'll need to help them repair the damaged
     * records.
     * <p>
     * In the giant field just outside, the springs are arranged into rows. For
     * each row, the condition records show every spring and whether it is
     * operational (.) or damaged (#). This is the part of the condition records
     * that is itself damaged; for some springs, it is simply unknown (?) whether
     * the spring is operational or damaged.
     * <p>
     * However, the engineer that produced the condition records also duplicated
     * some of this information in a different format! After the list of springs
     * for a given row, the size of each contiguous group of damaged springs is
     * listed in the order those groups appear in the row. This list always
     * accounts for every damaged spring, and each number is the entire size of
     * its contiguous group (that is, groups are always separated by at least one
     * operational spring: #### would always be 4, never 2,2).
     * <p>
     * So, condition records with no unknown spring conditions might look like
     * this:
     * <p>
     * #.#.### 1,1,3
     * .#...#....###. 1,1,3
     * .#.###.#.###### 1,3,1,6
     * ####.#...#... 4,1,1
     * #....######..#####. 1,6,5
     * .###.##....# 3,2,1
     * <p>
     * However, the condition records are partially damaged; some of the springs'
     * conditions are actually unknown (?). For example:
     * <p>
     * ???.### 1,1,3
     * .??..??...?##. 1,1,3
     * ?#?#?#?#?#?#?#? 1,3,1,6
     * ????.#...#... 4,1,1
     * ????.######..#####. 1,6,5
     * ?###???????? 3,2,1
     * <p>
     * Equipped with this information, it is your job to figure out how many
     * different arrangements of operational and broken springs fit the given
     * criteria in each row.
     * <p>
     * In the first line (???.### 1,1,3), there is exactly one way separate groups
     * of one, one, and three broken springs (in that order) can appear in that
     * row: the first three unknown springs must be broken, then operational, then
     * broken (#.#), making the whole row #.#.###.
     * <p>
     * The second line is more interesting: .??..??...?##. 1,1,3 could be a total
     * of four different arrangements. The last ? must always be broken (to
     * satisfy the final contiguous group of three broken springs), and each ??
     * must hide exactly one of the two broken springs. (Neither ?? could be both
     * broken springs or they would form a single contiguous group of two; if that
     * were true, the numbers afterward would have been 2,3 instead.) Since each
     * ?? can either be #. or .#, there are four possible arrangements of springs.
     * <p>
     * The last line is actually consistent with ten different arrangements!
     * Because the first number is 3, the first and second ? must both be . (if
     * either were #, the first number would have to be 4 or higher). However, the
     * remaining run of unknown spring conditions have many different ways they
     * could hold groups of two and one broken springs:
     * <p>
     * ?###???????? 3,2,1
     * .###.##.#...
     * .###.##..#..
     * .###.##...#.
     * .###.##....#
     * .###..##.#..
     * .###..##..#.
     * .###..##...#
     * .###...##.#.
     * .###...##..#
     * .###....##.#
     * <p>
     * In this example, the number of possible arrangements for each row is:
     * <p>
     * - ???.### 1,1,3 - 1 arrangement
     * - .??..??...?##. 1,1,3 - 4 arrangements
     * - ?#?#?#?#?#?#?#? 1,3,1,6 - 1 arrangement
     * - ????.#...#... 4,1,1 - 1 arrangement
     * - ????.######..#####. 1,6,5 - 4 arrangements
     * - ?###???????? 3,2,1 - 10 arrangements
     * <p>
     * Adding all of the possible arrangement counts together produces a total of
     * 21 arrangements.
     * <p>
     * For each row, count all of the different arrangements of operational and
     * broken springs that meet the given criteria. What is the sum of those
     * counts?
     */
    public static final class PartOne {

        enum Position {
            OPERATIONAL,
            DAMAGED,
            UNKNOWN;
        }

        public static int countArrangements(Scanner scanner) {
            int count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                List<String> split = Splitter.on(' ').splitToList(line);
                String pattern = split.get(0);
                int[] springs = Arrays.stream(split.get(1).split(",")).mapToInt(Integer::parseInt).toArray();

                int countArrangements = countArrangements(pattern, springs);
                count += countArrangements;
                LOGGER.info("'{}' -> {}", line, countArrangements);
            }
            return count;
        }

        static int countArrangements(String pattern, int[] springs) {
            List<Position> positionList = new ArrayList<>();
            IntList unknownPosition = new IntArrayList();
            int position = 0;
            int existingSprings = 0;
            for (int i = 0; i < pattern.length(); i++) {
                char c = pattern.charAt(i);
                switch (c) {
                    case '.' -> positionList.add(Position.OPERATIONAL);
                    case '#' -> {
                        positionList.add(Position.DAMAGED);
                        ++existingSprings;
                    }
                    case '?' -> {
                        positionList.add(Position.UNKNOWN);
                        unknownPosition.add(position);
                    }
                    default -> throw new IllegalStateException();
                }
                ++position;
            }

            int unknownCount = unknownPosition.size();

            int totalDamagedSprings = Arrays.stream(springs).sum();

            long maxBitSet = 1L << unknownCount;

            LOGGER.info("unknownCount = {}", unknownCount);

            int missingDamagedSprings = totalDamagedSprings - existingSprings;

            int countArrangements = 0;
            for (long bitSet = 0; bitSet < maxBitSet; ++bitSet) {
                BitSet set = BitSet.valueOf(new long[]{bitSet});
                if (set.cardinality() == missingDamagedSprings
                    && testArrangements(positionList, unknownPosition, set, springs)) {
                    ++countArrangements;
                }
            }

            return countArrangements;
        }

        static boolean testArrangements(List<Position> positionList, IntList unknownPosition, BitSet bitSet, int[] damagedSprings) {
            List<Position> newPositionList = new ArrayList<>(positionList);
            bitSet.stream().forEach(i -> newPositionList.set(unknownPosition.getInt(i), Position.DAMAGED));

            IntList newDamagedSprings = new IntArrayList();
            int countDamagedSprings = 0;
            for (Position position : newPositionList) {
                if (position == Position.DAMAGED) {
                    countDamagedSprings++;
                } else if (countDamagedSprings > 0) {
                    newDamagedSprings.add(countDamagedSprings);
                    countDamagedSprings = 0;
                }
            }
            if (countDamagedSprings > 0) {
                newDamagedSprings.add(countDamagedSprings);
            }

            return Arrays.equals(newDamagedSprings.toIntArray(), damagedSprings);
        }
    }

    /**
     * --- Part Two ---
     * <p>
     * As you look out at the field of springs, you feel like there are way more
     * springs than the condition records list. When you examine the records, you
     * discover that they were actually folded up this whole time!
     * <p>
     * To unfold the records, on each row, replace the list of spring conditions
     * with five copies of itself (separated by ?) and replace the list of
     * contiguous groups of damaged springs with five copies of itself (separated
     * by ,).
     * <p>
     * So, this row:
     * <p>
     * .# 1
     * <p>
     * Would become:
     * <p>
     * .#?.#?.#?.#?.# 1,1,1,1,1
     * <p>
     * The first line of the above example would become:
     * <p>
     * ???.###????.###????.###????.###????.### 1,1,3,1,1,3,1,1,3,1,1,3,1,1,3
     * <p>
     * In the above example, after unfolding, the number of possible arrangements
     * for some rows is now much larger:
     * <p>
     * ???.### 1,1,3 - 1 arrangement
     * .??..??...?##. 1,1,3 - 16384 arrangements
     * ?#?#?#?#?#?#?#? 1,3,1,6 - 1 arrangement
     * ????.#...#... 4,1,1 - 16 arrangements
     * ????.######..#####. 1,6,5 - 2500 arrangements
     * ?###???????? 3,2,1 - 506250 arrangements
     * <p>
     * After unfolding, adding all of the possible arrangement counts together
     * produces 525152.
     * <p>
     * Unfold your condition records; what is the new sum of possible arrangement counts?
     */
    public static final class PartTwo {

        public static long countArrangements(Scanner scanner, int copy) {
            long count = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                List<String> split = Splitter.on(' ').splitToList(line);
                String pattern = split.get(0);
                int[] springs = Arrays.stream(split.get(1).split(",")).mapToInt(Integer::parseInt).toArray();

                long countArrangements = countArrangements(pattern, springs, copy);
                count += countArrangements;
                LOGGER.info("'{}' -> {}", line, countArrangements);
            }
            return count;
        }

        private static long countArrangements(String springs, int[] groups, int copy) {
            IntList newGroups = new IntArrayList();
            List<String> newSprings = new ArrayList<>();
            for (int c = 0; c < copy; ++c) {
                newGroups.addAll(IntList.of(groups));
                newSprings.add(springs);
            }

            Map<CacheKey, Long> cache = new HashMap<>();

            return countArrangements(cache, String.join("?", newSprings), newGroups.toIntArray(), 0, 0);
        }

        private record CacheKey(int p, int g) {
        }

        private static long countArrangements(Map<CacheKey, Long> cache, String pattern, int[] groups, int p, int g) {
            CacheKey key = new CacheKey(p, g);
            Long result = cache.get(key);
            if (result != null) {
                return result;
            }

            if (pattern.length() == p) {
                if (g == groups.length) {
                    result = 1L;
                } else {
                    result = 0L;
                }
            } else {
                switch (pattern.charAt(p)) {
                    case '?' -> result = countArrangements(cache, pattern, groups, p + 1, g)
                                         + countArrangementsDamaged(cache, pattern, groups, p, g);
                    case '.' -> result = countArrangements(cache, pattern, groups, p + 1, g);
                    case '#' -> result = countArrangementsDamaged(cache, pattern, groups, p, g);
                    default -> throw new IllegalStateException();
                }
            }

            cache.put(key, result);
            return result;
        }

        private static Long countArrangementsDamaged(Map<CacheKey, Long> cache, String pattern, int[] groups, int p, int g) {
            if (groups.length == g) {
                return 0L;
            } else if (pattern.length() < p + groups[g]) {
                return 0L;
            } else if (stringContains(pattern, p, groups[g] + p, '.')) {
                return 0L;
            } else if (groups.length - g == 1) {
                return countArrangements(cache, pattern, groups, p + groups[g], g + 1);
            } else if (pattern.length() < p + groups[g] + 1 || pattern.charAt(p + groups[g]) == '#') {
                return 0L;
            } else {
                return countArrangements(cache, pattern, groups, p + groups[g] + 1, g + 1);
            }
        }

        private static boolean stringContains(String pattern, int start, int end, char find) {
            for (int i = start; i < end; ++i) {
                if (pattern.charAt(i) == find) {
                    return true;
                }
            }
            return false;
        }

    }
}
