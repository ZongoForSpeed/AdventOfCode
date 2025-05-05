package com.adventofcode.year2016;

import com.adventofcode.common.graph.Dijkstra;
import com.adventofcode.common.maths.Permutations;
import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public final class Day24 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24.class);

    private Day24() {
        // No-Op
    }

    /**
     * --- Day 24: Air Duct Spelunking ---
     *
     * You've finally met your match; the doors that provide access to the roof
     * are locked tight, and all of the controls and related electronics are
     * inaccessible. You simply can't reach them.
     *
     * The robot that cleans the air ducts, however, can.
     *
     * It's not a very fast little robot, but you reconfigure it to be able to
     * interface with some of the exposed wires that have been routed through the
     * HVAC system. If you can direct it to each of those locations, you should be
     * able to bypass the security controls.
     *
     * You extract the duct layout for this area from some blueprints you acquired
     * and create a map with the relevant locations marked (your puzzle input). 0
     * is your current location, from which the cleaning robot embarks; the other
     * numbers are (in no particular order) the locations the robot needs to visit
     * at least once each. Walls are marked as #, and open passages are marked as
     * .. Numbers behave like open passages.
     *
     * For example, suppose you have a map like the following:
     *
     * ###########
     * #0.1.....2#
     * #.#######.#
     * #4.......3#
     * ###########
     *
     * To reach all of the points of interest as quickly as possible, you would
     * have the robot take the following path:
     *
     * 0 to 4 (2 steps)
     * 4 to 1 (4 steps; it can't move diagonally)
     * 1 to 2 (6 steps)
     * 2 to 3 (2 steps)
     *
     * Since the robot isn't very fast, you need to find it the shortest route.
     * This path is the fewest steps (in the above example, a total of 14)
     * required to start at 0 and then visit every other location at least once.
     *
     * Given your actual map, and starting from location 0, what is the fewest
     * number of steps required to visit every non-0 number marked on the map at
     * least once?
     *
     * Your puzzle answer was 470.
     *
     * --- Part Two ---
     *
     * Of course, if you leave the cleaning robot somewhere weird, someone is
     * bound to notice.
     *
     * What is the fewest number of steps required to start at 0, visit every non-
     * 0 number marked on the map at least once, and then return to 0?
     *
     * Your puzzle answer was 1034.
     */
    static long computeMinSteps(Scanner scanner, boolean addOrigin) {
        CharMap map = CharMap.read(scanner, ignore -> true);
        LOGGER.debug("Map is: \n{}", map);

        Set<Point2D> points = map.points().stream().filter(p -> map.get(p) != '#').collect(Collectors.toSet());
        LOGGER.trace("points: {}", points);

        Int2ObjectMap<Point2D> neededPoints = new Int2ObjectOpenHashMap<>();
        for (Point2D point : points) {
            char c = map.get(point);
            if (c >= '0' && c <= '9') {
                neededPoints.put(c - '0', point);
            }
        }
        LOGGER.info("needed points: {}", neededPoints);

        int max = neededPoints.keySet().intStream().max().orElse(0);

        Map<Point2D, List<Pair<Point2D, Integer>>> graph = new HashMap<>();
        for (Point2D point : points) {
            List<Pair<Point2D, Integer>> neighbours = Arrays.stream(Direction.values()).map(point::move).filter(points::contains).map(p -> Pair.of(p, 1)).toList();
            graph.put(point, neighbours);
        }

        int[][] fullDistances = new int[max + 1][max + 1];

        Dijkstra<Point2D> dijkstra = new Dijkstra<>(graph);
        for (Int2ObjectMap.Entry<Point2D> entry : neededPoints.int2ObjectEntrySet()) {
            int key = entry.getIntKey();
            LOGGER.info("Searching for paths stating at {}", key);
            Object2IntMap<Point2D> distances = dijkstra.computeDistance(entry.getValue());
            for (Int2ObjectMap.Entry<Point2D> dEntry : neededPoints.int2ObjectEntrySet()) {
                fullDistances[key][dEntry.getIntKey()] = distances.getInt(dEntry.getValue());
            }
        }

        LOGGER.info("full distances: {}", (Object) fullDistances);

        int[] dots = neededPoints.keySet().intStream().filter(i -> i != 0).toArray();

        long minSteps = Permutations.of(dots)
                .mapToLong(d -> computeStep(fullDistances, d, addOrigin))
                .min()
                .orElseThrow();
        Day24.LOGGER.info("minSteps: {}", minSteps);
        return minSteps;
    }

    private static long computeStep(int[][] fullDistances, IntList order, boolean addOrigin) {
        int steps = 0;
        int previous = 0;
        for (int dot : order) {
            steps += fullDistances[previous][dot];
            previous = dot;
        }

        if (addOrigin) {
            steps += fullDistances[previous][0];
        }

        return steps;
    }
}
