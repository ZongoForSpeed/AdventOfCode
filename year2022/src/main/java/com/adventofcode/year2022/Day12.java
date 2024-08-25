package com.adventofcode.year2022;

import com.adventofcode.common.graph.AStar;
import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public final class Day12 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day12.class);

    private Day12() {
        // No-Op
    }

    /**
     * --- Day 12: Hill Climbing Algorithm ---
     *
     * You try contacting the Elves using your handheld device, but the river
     * you're following must be too low to get a decent signal.
     *
     * You ask the device for a heightmap of the surrounding area (your puzzle
     * input). The heightmap shows the local area from above broken into a grid;
     * the elevation of each square of the grid is given by a single lowercase
     * letter, where a is the lowest elevation, b is the next-lowest, and so on up
     * to the highest elevation, z.
     *
     * Also included on the heightmap are marks for your current position (S) and
     * the location that should get the best signal (E). Your current position (S)
     * has elevation a, and the location that should get the best signal (E) has
     * elevation z.
     *
     * You'd like to reach E, but to save energy, you should do it in as few steps
     * as possible. During each step, you can move exactly one square up, down,
     * left, or right. To avoid needing to get out your climbing gear, the
     * elevation of the destination square can be at most one higher than the
     * elevation of your current square; that is, if your current elevation is m,
     * you could step to elevation n, but not to elevation o. (This also means
     * that the elevation of the destination square can be much lower than the
     * elevation of your current square.)
     *
     * For example:
     *
     * Sabqponm
     * abcryxxl
     * accszExk
     * acctuvwj
     * abdefghi
     *
     * Here, you start in the top-left corner; your goal is near the middle. You
     * could start by moving down or right, but eventually you'll need to head
     * toward the e at the bottom. From there, you can spiral around to the goal:
     *
     * v..v<<<<
     * >v.vv<<^
     * .>vv>E^^
     * ..v>>>^^
     * ..>>>>>^
     *
     * In the above diagram, the symbols indicate whether the path exits each
     * square moving up (^), down (v), left (<), or right (>). The location that
     * should get the best signal is still E, and . marks unvisited squares.
     *
     * This path reaches the goal in 31 steps, the fewest possible.
     *
     * What is the fewest steps required to move from your current position to the
     * location that should get the best signal?
     */
    static long partOne(Scanner scanner) {
        CharMap map = CharMap.read(scanner, ignore -> true);

        LOGGER.info("map =\n{}", map);

        Set<Point2D> points = new HashSet<>(map.points());

        Point2D start = points.stream().filter(p -> map.get(p) == 'S').findFirst().orElseThrow();
        Point2D end = points.stream().filter(p -> map.get(p) == 'E').findFirst().orElseThrow();

        map.set(start, 'a');
        map.set(end, 'z');

        Map<Point2D, Collection<Point2D>> graph = new HashMap<>();
        for (Point2D point : points) {
            char elevation = map.get(point);
            List<Point2D> next = Arrays.stream(Direction.values())
                    .map(point::move)
                    .filter(points::contains)
                    .filter(p -> map.get(p) <= elevation + 1)
                    .toList();

            graph.put(point, next);
        }

        LOGGER.info("graph = {}", graph);
        return AStar.algorithm(graph::get, (a, b) -> 1L, start, end);
    }

    /**
     * --- Part Two ---
     *
     * As you walk up the hill, you suspect that the Elves will want to turn this
     * into a hiking trail. The beginning isn't very scenic, though; perhaps you
     * can find a better starting point.
     *
     * To maximize exercise while hiking, the trail should start as low as
     * possible: elevation a. The goal is still the square marked E. However, the
     * trail should still be direct, taking the fewest steps to reach its goal.
     * So, you'll need to find the shortest path from any square at elevation a to
     * the square marked E.
     *
     * Again consider the example from above:
     *
     * Sabqponm
     * abcryxxl
     * accszExk
     * acctuvwj
     * abdefghi
     *
     * Now, there are six choices for starting position (five marked a, plus the
     * square marked S that counts as being at elevation a). If you start at the
     * bottom-left square, you can reach the goal most quickly:
     *
     * ...v<<<<
     * ...vv<<^
     * ...v>E^^
     * .>v>>>^^
     * >^>>>>>^
     *
     * This path reaches the goal in only 29 steps, the fewest possible.
     *
     * What is the fewest steps required to move starting from any square with
     * elevation a to the location that should get the best signal?
     */
    static long partTwo(Scanner scanner) {
        CharMap map = CharMap.read(scanner, ignore -> true);

        LOGGER.info("map =\n{}", map);

        Set<Point2D> points = new HashSet<>(map.points());

        Point2D start = points.stream().filter(p -> map.get(p) == 'S').findFirst().orElseThrow();
        Point2D end = points.stream().filter(p -> map.get(p) == 'E').findFirst().orElseThrow();

        map.set(start, 'a');
        map.set(end, 'z');

        Map<Point2D, Collection<Point2D>> graph = new HashMap<>();
        for (Point2D point : points) {
            char elevation = map.get(point);
            List<Point2D> next = Arrays.stream(Direction.values())
                    .map(point::move)
                    .filter(points::contains)
                    .filter(p -> map.get(p) <= elevation + 1)
                    .toList();

            graph.put(point, next);
        }

        LOGGER.info("graph = {}", graph);

        List<Point2D> starts = points.stream().filter(p -> map.get(p) == 'a').toList();
        LongList distances = new LongArrayList();
        for (Point2D d : starts) {
            long algorithm = AStar.algorithm(graph::get, (a, b) -> 1L, d, end);
            distances.add(algorithm);
        }

        return distances.longStream().min().orElseThrow();
    }
}
