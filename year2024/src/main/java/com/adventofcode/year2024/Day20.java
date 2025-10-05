package com.adventofcode.year2024;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public final class Day20 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day20.class);

    private Day20() {
        // No-Op
    }

    private static int raceCondition(Scanner scanner, int limit, int length) {
        List<Point2D> path = findPath(scanner);

        Int2IntMap cheats = new Int2IntOpenHashMap();

        for (int i = 0; i < path.size(); i++) {
            Point2D a = path.get(i);
            for (int j = i + 2; j < path.size(); ++j) {
                Point2D b = path.get(j);
                int distance = Point2D.manhattanDistance(a, b);
                if (distance <= length) {
                    int save = j - i - distance;
                    if (save >= limit) {
                        LOGGER.trace("Cheat for {} to {} saves {} picoseconds", a, b, save);
                        cheats.mergeInt(save, 1, Integer::sum);
                    }
                }
            }
        }

        LOGGER.info("cheats: {}", cheats);
        return cheats.values().intStream().sum();
    }

    private static List<Point2D> findPath(Scanner scanner) {
        CharMap maze = CharMap.read(scanner, _ -> true);

        LOGGER.info("maze:\n{}", maze);

        Point2D start = null;
        Point2D end = null;
        Set<Point2D> tracks = new HashSet<>();
        for (ObjectCharPair<Point2D> entry : maze.entries()) {
            switch (entry.rightChar()) {
                case 'S' -> start = entry.left();
                case 'E' -> end = entry.left();
                case '.' -> tracks.add(entry.left());
                default -> {
                    // No-Op
                }
            }
        }

        if (start == null || end == null) {
            throw new IllegalStateException("Cannot find start or end");
        }

        List<Point2D> path = new ArrayList<>();
        path.add(start);
        Point2D current = start;
        while (!tracks.isEmpty()) {
            List<Point2D> neighbours = Arrays.stream(Direction.values()).map(current::move).filter(tracks::contains).toList();
            switch (neighbours.size()) {
                case 0 -> throw new IllegalStateException("Cannot find path following: " + current);
                case 1 -> {
                    Point2D first = neighbours.getFirst();
                    tracks.remove(first);
                    path.add(first);
                    current = first;
                }
                default -> throw new IllegalStateException("Found branch at: " + current);
            }
        }
        path.add(end);
        LOGGER.trace("path: {}", path);
        return path;
    }

    /**
     * --- Day 20: Race Condition ---
     * <p>
     * The Historians are quite pixelated again. This time, a massive, black
     * building looms over you - you're right outside the CPU!
     * <p>
     * While The Historians get to work, a nearby program sees that you're idle
     * and challenges you to a race. Apparently, you've arrived just in time for
     * the frequently-held race condition festival!
     * <p>
     * The race takes place on a particularly long and twisting code path;
     * programs compete to see who can finish in the fewest picoseconds. The
     * winner even gets their very own mutex!
     * <p>
     * They hand you a map of the racetrack (your puzzle input). For example:
     * <p>
     * ###############
     * #...#...#.....#
     * #.#.#.#.#.###.#
     * #S#...#.#.#...#
     * #######.#.#.###
     * #######.#.#...#
     * #######.#.###.#
     * ###..E#...#...#
     * ###.#######.###
     * #...###...#...#
     * #.#####.#.###.#
     * #.#...#.#.#...#
     * #.#.#.#.#.#.###
     * #...#...#...###
     * ###############
     * <p>
     * The map consists of track (.) - including the start (S) and end (E)
     * positions (both of which also count as track) - and walls (#).
     * <p>
     * When a program runs through the racetrack, it starts at the start position.
     * Then, it is allowed to move up, down, left, or right; each such move takes
     * 1 picosecond. The goal is to reach the end position as quickly as possible.
     * In this example racetrack, the fastest time is 84 picoseconds.
     * <p>
     * Because there is only a single path from the start to the end and the
     * programs all go the same speed, the races used to be pretty boring. To make
     * things more interesting, they introduced a new rule to the races: programs
     * are allowed to cheat.
     * <p>
     * The rules for cheating are very strict. Exactly once during a race, a
     * program may disable collision for up to 2 picoseconds. This allows the
     * program to pass through walls as if they were regular track. At the end of
     * the cheat, the program must be back on normal track again; otherwise, it
     * will receive a segmentation fault and get disqualified.
     * <p>
     * So, a program could complete the course in 72 picoseconds (saving 12
     * picoseconds) by cheating for the two moves marked 1 and 2:
     * <p>
     * ###############
     * #...#...12....#
     * #.#.#.#.#.###.#
     * #S#...#.#.#...#
     * #######.#.#.###
     * #######.#.#...#
     * #######.#.###.#
     * ###..E#...#...#
     * ###.#######.###
     * #...###...#...#
     * #.#####.#.###.#
     * #.#...#.#.#...#
     * #.#.#.#.#.#.###
     * #...#...#...###
     * ###############
     * <p>
     * Or, a program could complete the course in 64 picoseconds (saving 20
     * picoseconds) by cheating for the two moves marked 1 and 2:
     * <p>
     * ###############
     * #...#...#.....#
     * #.#.#.#.#.###.#
     * #S#...#.#.#...#
     * #######.#.#.###
     * #######.#.#...#
     * #######.#.###.#
     * ###..E#...12..#
     * ###.#######.###
     * #...###...#...#
     * #.#####.#.###.#
     * #.#...#.#.#...#
     * #.#.#.#.#.#.###
     * #...#...#...###
     * ###############
     * <p>
     * This cheat saves 38 picoseconds:
     * <p>
     * ###############
     * #...#...#.....#
     * #.#.#.#.#.###.#
     * #S#...#.#.#...#
     * #######.#.#.###
     * #######.#.#...#
     * #######.#.###.#
     * ###..E#...#...#
     * ###.####1##.###
     * #...###.2.#...#
     * #.#####.#.###.#
     * #.#...#.#.#...#
     * #.#.#.#.#.#.###
     * #...#...#...###
     * ###############
     * <p>
     * This cheat saves 64 picoseconds and takes the program directly to the end:
     * <p>
     * ###############
     * #...#...#.....#
     * #.#.#.#.#.###.#
     * #S#...#.#.#...#
     * #######.#.#.###
     * #######.#.#...#
     * #######.#.###.#
     * ###..21...#...#
     * ###.#######.###
     * #...###...#...#
     * #.#####.#.###.#
     * #.#...#.#.#...#
     * #.#.#.#.#.#.###
     * #...#...#...###
     * ###############
     * <p>
     * Each cheat has a distinct start position (the position where the cheat is
     * activated, just before the first move that is allowed to go through walls)
     * and end position; cheats are uniquely identified by their start position
     * and end position.
     * <p>
     * In this example, the total number of cheats (grouped by the amount of time
     * they save) are as follows:
     * <p>
     * - There are 14 cheats that save 2 picoseconds.
     * - There are 14 cheats that save 4 picoseconds.
     * - There are 2 cheats that save 6 picoseconds.
     * - There are 4 cheats that save 8 picoseconds.
     * - There are 2 cheats that save 10 picoseconds.
     * - There are 3 cheats that save 12 picoseconds.
     * - There is one cheat that saves 20 picoseconds.
     * - There is one cheat that saves 36 picoseconds.
     * - There is one cheat that saves 38 picoseconds.
     * - There is one cheat that saves 40 picoseconds.
     * - There is one cheat that saves 64 picoseconds.
     * <p>
     * You aren't sure what the conditions of the racetrack will be like, so to
     * give yourself as many options as possible, you'll need a list of the best
     * cheats. How many cheats would save you at least 100 picoseconds?
     */
    public static int partOne(Scanner scanner, int limit) {
        return raceCondition(scanner, limit, 2);
    }

    /**
     * --- Part Two ---
     * <p>
     * The programs seem perplexed by your list of cheats. Apparently, the two-
     * picosecond cheating rule was deprecated several milliseconds ago! The
     * latest version of the cheating rule permits a single cheat that instead
     * lasts at most 20 picoseconds.
     * <p>
     * Now, in addition to all the cheats that were possible in just two
     * picoseconds, many more cheats are possible. This six-picosecond cheat saves
     * 76 picoseconds:
     * <p>
     * ###############
     * #...#...#.....#
     * #.#.#.#.#.###.#
     * #S#...#.#.#...#
     * #1#####.#.#.###
     * #2#####.#.#...#
     * #3#####.#.###.#
     * #456.E#...#...#
     * ###.#######.###
     * #...###...#...#
     * #.#####.#.###.#
     * #.#...#.#.#...#
     * #.#.#.#.#.#.###
     * #...#...#...###
     * ###############
     * <p>
     * Because this cheat has the same start and end positions as the one above,
     * it's the same cheat, even though the path taken during the cheat is
     * different:
     * <p>
     * ###############
     * #...#...#.....#
     * #.#.#.#.#.###.#
     * #S12..#.#.#...#
     * ###3###.#.#.###
     * ###4###.#.#...#
     * ###5###.#.###.#
     * ###6.E#...#...#
     * ###.#######.###
     * #...###...#...#
     * #.#####.#.###.#
     * #.#...#.#.#...#
     * #.#.#.#.#.#.###
     * #...#...#...###
     * ###############
     * <p>
     * Cheats don't need to use all 20 picoseconds; cheats can last any amount of
     * time up to and including 20 picoseconds (but can still only end when the
     * program is on normal track). Any cheat time not used is lost; it can't be
     * saved for another cheat later.
     * <p>
     * You'll still need a list of the best cheats, but now there are even more to choose between. Here are the quantities of cheats in this example that save 50 picoseconds or more:
     * <p>
     * - There are 32 cheats that save 50 picoseconds.
     * - There are 31 cheats that save 52 picoseconds.
     * - There are 29 cheats that save 54 picoseconds.
     * - There are 39 cheats that save 56 picoseconds.
     * - There are 25 cheats that save 58 picoseconds.
     * - There are 23 cheats that save 60 picoseconds.
     * - There are 20 cheats that save 62 picoseconds.
     * - There are 19 cheats that save 64 picoseconds.
     * - There are 12 cheats that save 66 picoseconds.
     * - There are 14 cheats that save 68 picoseconds.
     * - There are 12 cheats that save 70 picoseconds.
     * - There are 22 cheats that save 72 picoseconds.
     * - There are 4 cheats that save 74 picoseconds.
     * - There are 3 cheats that save 76 picoseconds.
     * <p>
     * Find the best cheats using the updated cheating rules. How many cheats
     * would save you at least 100 picoseconds?
     */
    public static int partTwo(Scanner scanner, int limit) {
        return raceCondition(scanner, limit, 20);
    }
}
