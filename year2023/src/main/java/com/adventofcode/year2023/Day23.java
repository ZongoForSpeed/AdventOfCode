package com.adventofcode.year2023;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntIntPair;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public final class Day23 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day23.class);

    private Day23() {
        // No-Op
    }

    public static Int2ObjectMap<List<IntIntPair>> collapse(Int2ObjectMap<IntList> neighbours) {
        Int2ObjectMap<List<IntIntPair>> result = new Int2ObjectOpenHashMap<>();
        for (Int2ObjectMap.Entry<IntList> entry : neighbours.int2ObjectEntrySet()) {
            int p = entry.getIntKey();
            List<IntIntPair> pairs = result.computeIfAbsent(p, ignore -> new ArrayList<>());
            for (Integer n : entry.getValue()) {
                pairs.add(collapse(neighbours, p, n));
            }
        }
        return result;
    }

    public static IntIntPair collapse(Int2ObjectMap<IntList> neighbours, int p, int n) {
        int d = 1;
        while (neighbours.get(n).size() == 2) {
            IntList moves = neighbours.get(n);
            int anInt = moves.getInt(0);
            if (anInt == p) {
                anInt = moves.getInt(1);
            }
            p = n;
            n = anInt;
            ++d;
        }

        return IntIntPair.of(n, d);
    }

    private static int findLongestPath(int current, int end, BitSet path, Int2ObjectMap<List<IntIntPair>> collapseGraph, int cost) {
        if (current == end) {
            return cost;
        }

        int longestPath = Integer.MIN_VALUE;
        for (IntIntPair move : collapseGraph.get(current)) {
            int index = move.firstInt();
            if (!path.get(index)) {
                path.set(index);
                int newPath = findLongestPath(index, end, path, collapseGraph, cost + move.secondInt());
                longestPath = Math.max(longestPath, newPath);
                path.clear(index);
            }
        }

        return longestPath;
    }

    private static int findLongestPath(int indexStart, int indexEnd, Int2ObjectMap<IntList> neighbours) {
        Int2ObjectMap<List<IntIntPair>> collapseGraph = collapse(neighbours);

        BitSet path = new BitSet();
        path.set(0);
        return findLongestPath(indexStart, indexEnd, path, collapseGraph, 0);
    }

    /**
     * --- Day 23: A Long Walk ---
     * <p>
     * The Elves resume water filtering operations! Clean water starts flowing
     * over the edge of Island Island.
     * <p>
     * They offer to help you go over the edge of Island Island, too! Just hold on
     * tight to one end of this impossibly long rope and they'll lower you down a
     * safe distance from the massive waterfall you just created.
     * <p>
     * As you finally reach Snow Island, you see that the water isn't really
     * reaching the ground: it's being absorbed by the air itself. It looks like
     * you'll finally have a little downtime while the moisture builds up to snow-
     * producing levels. Snow Island is pretty scenic, even without any snow; why
     * not take a walk?
     * <p>
     * There's a map of nearby hiking trails (your puzzle input) that indicates
     * paths (.), forest (#), and steep slopes (^, >, v, and <).
     * <p>
     * For example:
     * <p>
     * #.#####################
     * #.......#########...###
     * #######.#########.#.###
     * ###.....#.>.>.###.#.###
     * ###v#####.#v#.###.#.###
     * ###.>...#.#.#.....#...#
     * ###v###.#.#.#########.#
     * ###...#.#.#.......#...#
     * #####.#.#.#######.#.###
     * #.....#.#.#.......#...#
     * #.#####.#.#.#########v#
     * #.#...#...#...###...>.#
     * #.#.#v#######v###.###v#
     * #...#.>.#...>.>.#.###.#
     * #####v#.#.###v#.#.###.#
     * #.....#...#...#.#.#...#
     * #.#########.###.#.#.###
     * #...###...#...#...#.###
     * ###.###.#.###v#####v###
     * #...#...#.#.>.>.#.>.###
     * #.###.###.#.###.#.#v###
     * #.....###...###...#...#
     * #####################.#
     * <p>
     * You're currently on the single path tile in the top row; your goal is to
     * reach the single path tile in the bottom row. Because of all the mist from
     * the waterfall, the slopes are probably quite icy; if you step onto a slope
     * tile, your next step must be downhill (in the direction the arrow is
     * pointing). To make sure you have the most scenic hike possible, never step
     * onto the same tile twice. What is the longest hike you can take?
     * <p>
     * In the example above, the longest hike you can take is marked with O, and
     * your starting position is marked S:
     * <p>
     * #S#####################
     * #OOOOOOO#########...###
     * #######O#########.#.###
     * ###OOOOO#OOO>.###.#.###
     * ###O#####O#O#.###.#.###
     * ###OOOOO#O#O#.....#...#
     * ###v###O#O#O#########.#
     * ###...#O#O#OOOOOOO#...#
     * #####.#O#O#######O#.###
     * #.....#O#O#OOOOOOO#...#
     * #.#####O#O#O#########v#
     * #.#...#OOO#OOO###OOOOO#
     * #.#.#v#######O###O###O#
     * #...#.>.#...>OOO#O###O#
     * #####v#.#.###v#O#O###O#
     * #.....#...#...#O#O#OOO#
     * #.#########.###O#O#O###
     * #...###...#...#OOO#O###
     * ###.###.#.###v#####O###
     * #...#...#.#.>.>.#.>O###
     * #.###.###.#.###.#.#O###
     * #.....###...###...#OOO#
     * #####################O#
     * <p>
     * This hike contains 94 steps. (The other possible hikes you could have taken
     * were 90, 86, 82, 82, and 74 steps long.)
     * <p>
     * Find the longest hike you can take through the hiking trails listed on your
     * map. How many steps long is the longest hike?
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static int longestPathLength(Scanner scanner) {
            CharMap map = CharMap.read(scanner, c -> c != '#');
            LOGGER.info("map:\n{}", map);

            List<Point2D> points = new ArrayList<>(map.points());
            Map<Point2D, Integer> pointsToIndex = new HashMap<>();
            for (int i = 0; i < points.size(); i++) {
                pointsToIndex.put(points.get(i), i);
            }

            LOGGER.info("pointsToIndex = {}", pointsToIndex);

            LOGGER.info("first = {}, last = {}", points.getFirst(), points.getLast());

            Int2ObjectMap<IntList> neighbours = new Int2ObjectOpenHashMap<>();

            for (int i = 0; i < points.size(); i++) {
                Point2D point = points.get(i);
                char c = map.get(point);
                Stream<Direction> directions = switch (c) {
                    case '.' -> Arrays.stream(Direction.values());
                    case '>' -> Stream.of(Direction.RIGHT);
                    case '<' -> Stream.of(Direction.LEFT);
                    case 'v' -> Stream.of(Direction.DOWN);
                    case '^' -> Stream.of(Direction.UP);
                    default -> throw new IllegalStateException("Unknown direction '" + c + "'");
                };
                int[] moves = directions.map(point::move)
                        .map(pointsToIndex::get)
                        .filter(Objects::nonNull)
                        .mapToInt(idx -> idx)
                        .toArray();
                neighbours.computeIfAbsent(i, ignore -> new IntArrayList()).addElements(0, moves);
            }

            LOGGER.info("neighbours = {}", neighbours);

            int indexStart = 0;
            int indexEnd = points.size() - 1;

            return findLongestPath(indexStart, indexEnd, neighbours);
        }
    }

    /**
     * --- Part Two ---
     * <p>
     * As you reach the trailhead, you realize that the ground isn't as slippery
     * as you expected; you'll have no problem climbing up the steep slopes.
     * <p>
     * Now, treat all slopes as if they were normal paths (.). You still want to
     * make sure you have the most scenic hike possible, so continue to ensure
     * that you never step onto the same tile twice. What is the longest hike you
     * can take?
     * <p>
     * In the example above, this increases the longest hike to 154 steps:
     * <p>
     * #S#####################
     * #OOOOOOO#########OOO###
     * #######O#########O#O###
     * ###OOOOO#.>OOO###O#O###
     * ###O#####.#O#O###O#O###
     * ###O>...#.#O#OOOOO#OOO#
     * ###O###.#.#O#########O#
     * ###OOO#.#.#OOOOOOO#OOO#
     * #####O#.#.#######O#O###
     * #OOOOO#.#.#OOOOOOO#OOO#
     * #O#####.#.#O#########O#
     * #O#OOO#...#OOO###...>O#
     * #O#O#O#######O###.###O#
     * #OOO#O>.#...>O>.#.###O#
     * #####O#.#.###O#.#.###O#
     * #OOOOO#...#OOO#.#.#OOO#
     * #O#########O###.#.#O###
     * #OOO###OOO#OOO#...#O###
     * ###O###O#O###O#####O###
     * #OOO#OOO#O#OOO>.#.>O###
     * #O###O###O#O###.#.#O###
     * #OOOOO###OOO###...#OOO#
     * #####################O#
     * <p>
     * Find the longest hike you can take through the surprisingly dry hiking
     * trails listed on your map. How many steps long is the longest hike?
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        public static int longestPathLength(Scanner scanner) {
            CharMap map = CharMap.read(scanner, c -> c != '#');
            LOGGER.info("map:\n{}", map);

            List<Point2D> points = new ArrayList<>(map.points());
            Map<Point2D, Integer> pointsToIndex = new HashMap<>();
            for (int i = 0; i < points.size(); i++) {
                pointsToIndex.put(points.get(i), i);
            }

            Point2D first = points.getFirst();
            Point2D last = points.getLast();
            LOGGER.info("pointsToIndex = {}", pointsToIndex);

            LOGGER.info("first = {}, last = {}", first, last);

            Int2ObjectMap<IntList> neighbours = new Int2ObjectOpenHashMap<>();

            for (int i = 0; i < points.size(); i++) {
                Point2D point = points.get(i);
                char c = map.get(point);
                Stream<Direction> directions = switch (c) {
                    case '.', 'v', '>', '<', '^' -> Arrays.stream(Direction.values());
                    default -> throw new IllegalStateException("Unknown direction '" + c + "'");
                };
                int[] moves = directions.map(point::move)
                        .map(pointsToIndex::get)
                        .filter(Objects::nonNull)
                        .mapToInt(idx -> idx)
                        .toArray();
                neighbours.computeIfAbsent(i, ignore -> new IntArrayList()).addElements(0, moves);
            }

            LOGGER.info("neighbours = {}", neighbours);

            int indexStart = 0;
            int indexEnd = points.size() - 1;

            return findLongestPath(indexStart, indexEnd, neighbours);
        }
    }
}
