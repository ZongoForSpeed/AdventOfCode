package com.adventofcode.year2018;

import com.adventofcode.point.Point2D;
import com.adventofcode.point.map.CharMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public final class Day18 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day18.class);
    private static final List<Point2D> ADJACENT =
            List.of(Point2D.of(-1, -1),
                    Point2D.of(-1, 0),
                    Point2D.of(-1, 1),
                    Point2D.of(0, -1),
                    Point2D.of(0, 1),
                    Point2D.of(1, -1),
                    Point2D.of(1, 0),
                    Point2D.of(1, 1)
            );

    private Day18() {
        // No-Op
    }

    /**
     * --- Day 18: Settlers of The North Pole ---
     *
     * On the outskirts of the North Pole base construction project, many Elves
     * are collecting lumber.
     *
     * The lumber collection area is 50 acres by 50 acres; each acre can be either
     * open ground (.), trees (|), or a lumberyard (#). You take a scan of the
     * area (your puzzle input).
     *
     * Strange magic is at work here: each minute, the landscape looks entirely
     * different. In exactly one minute, an open acre can fill with trees, a
     * wooded acre can be converted to a lumberyard, or a lumberyard can be
     * cleared to open ground (the lumber having been sent to other projects).
     *
     * The change to each acre is based entirely on the contents of that acre as
     * well as the number of open, wooded, or lumberyard acres adjacent to it at
     * the start of each minute. Here, "adjacent" means any of the eight acres
     * surrounding that acre. (Acres on the edges of the lumber collection area
     * might have fewer than eight adjacent acres; the missing acres aren't
     * counted.)
     *
     * In particular:
     *
     *   - An open acre will become filled with trees if three or more adjacent
     *     acres contained trees. Otherwise, nothing happens.
     *   - An acre filled with trees will become a lumberyard if three or more
     *     adjacent acres were lumberyards. Otherwise, nothing happens.
     *   - An acre containing a lumberyard will remain a lumberyard if it was
     *     adjacent to at least one other lumberyard and at least one acre
     *     containing trees. Otherwise, it becomes open.
     *
     * These changes happen across all acres simultaneously, each of them using
     * the state of all acres at the beginning of the minute and changing to their
     * new form by the end of that same minute. Changes that happen during the
     * minute don't affect each other.
     *
     * For example, suppose the lumber collection area is instead only 10 by 10
     * acres with this initial configuration:
     *
     * Initial state:
     * .#.#...|#.
     * .....#|##|
     * .|..|...#.
     * ..|#.....#
     * #.#|||#|#|
     * ...#.||...
     * .|....|...
     * ||...#|.#|
     * |.||||..|.
     * ...#.|..|.
     *
     * After 1 minute:
     * .......##.
     * ......|###
     * .|..|...#.
     * ..|#||...#
     * ..##||.|#|
     * ...#||||..
     * ||...|||..
     * |||||.||.|
     * ||||||||||
     * ....||..|.
     *
     * After 2 minutes:
     * .......#..
     * ......|#..
     * .|.|||....
     * ..##|||..#
     * ..###|||#|
     * ...#|||||.
     * |||||||||.
     * ||||||||||
     * ||||||||||
     * .|||||||||
     *
     * After 3 minutes:
     * .......#..
     * ....|||#..
     * .|.||||...
     * ..###|||.#
     * ...##|||#|
     * .||##|||||
     * ||||||||||
     * ||||||||||
     * ||||||||||
     * ||||||||||
     *
     * After 4 minutes:
     * .....|.#..
     * ...||||#..
     * .|.#||||..
     * ..###||||#
     * ...###||#|
     * |||##|||||
     * ||||||||||
     * ||||||||||
     * ||||||||||
     * ||||||||||
     *
     * After 5 minutes:
     * ....|||#..
     * ...||||#..
     * .|.##||||.
     * ..####|||#
     * .|.###||#|
     * |||###||||
     * ||||||||||
     * ||||||||||
     * ||||||||||
     * ||||||||||
     *
     * After 6 minutes:
     * ...||||#..
     * ...||||#..
     * .|.###|||.
     * ..#.##|||#
     * |||#.##|#|
     * |||###||||
     * ||||#|||||
     * ||||||||||
     * ||||||||||
     * ||||||||||
     *
     * After 7 minutes:
     * ...||||#..
     * ..||#|##..
     * .|.####||.
     * ||#..##||#
     * ||##.##|#|
     * |||####|||
     * |||###||||
     * ||||||||||
     * ||||||||||
     * ||||||||||
     *
     * After 8 minutes:
     * ..||||##..
     * ..|#####..
     * |||#####|.
     * ||#...##|#
     * ||##..###|
     * ||##.###||
     * |||####|||
     * ||||#|||||
     * ||||||||||
     * ||||||||||
     *
     * After 9 minutes:
     * ..||###...
     * .||#####..
     * ||##...##.
     * ||#....###
     * |##....##|
     * ||##..###|
     * ||######||
     * |||###||||
     * ||||||||||
     * ||||||||||
     *
     * After 10 minutes:
     * .||##.....
     * ||###.....
     * ||##......
     * |##.....##
     * |##.....##
     * |##....##|
     * ||##.####|
     * ||#####|||
     * ||||#|||||
     * ||||||||||
     *
     * After 10 minutes, there are 37 wooded acres and 31 lumberyards. Multiplying
     * the number of wooded acres by the number of lumberyards gives the total
     * resource value after ten minutes: 37 * 31 = 1147.
     *
     * What will the total resource value of the lumber collection area be after
     * 10 minutes?
     *
     * --- Part Two ---
     *
     * This important natural resource will need to last for at least thousands of
     * years. Are the Elves collecting this lumber sustainably?
     *
     * What will the total resource value of the lumber collection area be after
     * 1000000000 minutes?
     */
    static long treesAndLumberyards(Scanner scanner, int minutes) {
        CharMap map = CharMap.read(scanner, (ignore) -> true);

        LOGGER.info("Initial state:\n{}", map);

        List<Point2D> points = map.points();
        int maxX = points.stream().mapToInt(Point2D::x).max().orElseThrow();
        int maxY = points.stream().mapToInt(Point2D::y).max().orElseThrow();


        Map<Point2D, List<Point2D>> adjacent = new HashMap<>();
        for (int x = 0; x <= maxX; ++x) {
            for (int y = 0; y <= maxY; ++y) {
                Point2D point = Point2D.of(x, y);
                List<Point2D> list = ADJACENT.stream().map(point::move).filter(p -> p.x() >= 0 && p.x() <= maxX && p.y() >= 0 && p.y() <= maxY).toList();
                adjacent.put(point, list);
            }
        }

        Map<String, Integer> states = new HashMap<>();

        boolean searchCycle = true;

        for (int m = 1; m <= minutes; ++m) {
            map = nextState(map, adjacent);
            LOGGER.trace("After {} minute(s):\n{}", m, map);

            if (searchCycle) {
                String s = map.toString();
                Integer integer = states.get(s);
                if (integer == null) {
                    states.put(s, m);
                } else {
                    searchCycle = false;
                    int cycleLength = m - integer;
                    LOGGER.info("Found cycles of length = {}", cycleLength);
                    while (m + cycleLength < minutes) {
                        m += cycleLength;
                    }
                }
            }

        }

        return resourceValue(map);
    }

    private static long resourceValue(CharMap map) {
        long trees = 0;
        long lumberyards = 0;

        List<Point2D> points = map.points();
        for (Point2D point : points) {
            char acre = map.get(point);
            if (acre == '|') {
                trees++;
            } else if (acre == '#') {
                lumberyards++;
            }
        }

        return trees * lumberyards;
    }

    private static CharMap nextState(CharMap map, Map<Point2D, List<Point2D>> adjacent) {
        CharMap nextMap = new CharMap(0, 0, '.');
        for (Map.Entry<Point2D, List<Point2D>> entry : adjacent.entrySet()) {
            Point2D point = entry.getKey();
            List<Character> adjacentAcres = entry.getValue().stream().map(map::get).toList();
            char c = map.get(point);
            switch (c) {
                case '.' -> {
                    if (adjacentAcres.stream().filter(a -> a == '|').count() >= 3) {
                        nextMap.set(point, '|');
                    } else {
                        nextMap.set(point, '.');
                    }
                }
                case '|' -> {
                    if (adjacentAcres.stream().filter(a -> a == '#').count() >= 3) {
                        nextMap.set(point, '#');
                    } else {
                        nextMap.set(point, '|');
                    }
                }
                case '#' -> {
                    if (adjacentAcres.stream().anyMatch(a -> a == '#')
                        && adjacentAcres.stream().anyMatch(a -> a == '|')) {
                        nextMap.set(point, '#');
                    } else {
                        nextMap.set(point, '.');
                    }
                }
                default -> throw new IllegalStateException("Unknown acre type: '" + c + "'");
            }
        }
        return nextMap;
    }
}
