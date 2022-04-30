package com.adventofcode.year2018;

import com.adventofcode.point.Point2D;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

public final class Day06 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day06.class);

    private Day06() {
        // No-Op
    }

    private static Optional<Point2D> minDistance(List<Point2D> points, Point2D p) {
        Int2ObjectMap<List<Point2D>> distance = new Int2ObjectOpenHashMap<>();
        for (Point2D point : points) {
            int manhattanDistance = Point2D.manhattanDistance(point, p);
            distance.computeIfAbsent(manhattanDistance, ignore -> new ArrayList<>()).add(point);
        }
        int min = Collections.min(distance.keySet());
        List<Point2D> list = distance.get(min);
        if (list.size() == 1) {
            return Optional.of(list.get(0));
        } else {
            return Optional.empty();
        }
    }

    /**
     * --- Day 6: Chronal Coordinates ---
     *
     * The device on your wrist beeps several times, and once again you feel like
     * you're falling.
     *
     * "Situation critical," the device announces. "Destination indeterminate.
     * Chronal interference detected. Please specify new target coordinates."
     *
     * The device then produces a list of coordinates (your puzzle input). Are
     * they places it thinks are safe or dangerous? It recommends you check manual
     * page 729. The Elves did not give you a manual.
     *
     * If they're dangerous, maybe you can minimize the danger by finding the
     * coordinate that gives the largest distance from the other points.
     *
     * Using only the Manhattan distance, determine the area around each
     * coordinate by counting the number of integer X,Y locations that are closest
     * to that coordinate (and aren't tied in distance to any other coordinate).
     *
     * Your goal is to find the size of the largest area that isn't infinite. For
     * example, consider the following list of coordinates:
     *
     * 1, 1
     * 1, 6
     * 8, 3
     * 3, 4
     * 5, 5
     * 8, 9
     *
     * If we name these coordinates A through F, we can draw them on a grid,
     * putting 0,0 at the top left:
     *
     * ..........
     * .A........
     * ..........
     * ........C.
     * ...D......
     * .....E....
     * .B........
     * ..........
     * ..........
     * ........F.
     *
     * This view is partial - the actual grid extends infinitely in all
     * directions. Using the Manhattan distance, each location's closest
     * coordinate can be determined, shown here in lowercase:
     *
     * aaaaa.cccc
     * aAaaa.cccc
     * aaaddecccc
     * aadddeccCc
     * ..dDdeeccc
     * bb.deEeecc
     * bBb.eeee..
     * bbb.eeefff
     * bbb.eeffff
     * bbb.ffffFf
     *
     * Locations shown as . are equally far from two or more coordinates, and so
     * they don't count as being closest to any.
     *
     * In this example, the areas of coordinates A, B, C, and F are infinite -
     * while not shown here, their areas extend forever outside the visible grid.
     * However, the areas of coordinates D and E are finite: D is closest to 9
     * locations, and E is closest to 17 (both including the coordinate's location
     * itself). Therefore, in this example, the size of the largest area is 17.
     *
     * What is the size of the largest area that isn't infinite?
     */
    static int maxArea(Scanner scanner) {
        List<Point2D> points = readPoints(scanner);

        LOGGER.debug("Points: {}", points);
        int xMin = points.stream().mapToInt(Point2D::x).min().orElseThrow() - 1;
        int xMax = points.stream().mapToInt(Point2D::x).max().orElseThrow() + 1;
        int yMin = points.stream().mapToInt(Point2D::y).min().orElseThrow() - 1;
        int yMax = points.stream().mapToInt(Point2D::y).max().orElseThrow() + 1;

        Map<Point2D, Integer> areas = new HashMap<>();

        for (int x = xMin; x <= xMax; ++x) {
            for (int y = yMin; y <= yMax; ++y) {
                Point2D p = Point2D.of(x, y);
                minDistance(points, p).ifPresent(k -> areas.merge(k, 1, Integer::sum));
            }
        }

        LOGGER.trace("Areas: {}", areas);

        Set<Point2D> infiniteAreas = new HashSet<>();

        for (int x = xMin - 1; x <= xMax + 1; ++x) {
            Optional<Point2D> min = minDistance(points, Point2D.of(x, yMin - 1));
            min.ifPresent(infiniteAreas::add);
        }

        for (int x = xMin - 1; x <= xMax + 1; ++x) {
            Optional<Point2D> min = minDistance(points, Point2D.of(x, yMax + 1));
            min.ifPresent(infiniteAreas::add);
        }

        for (int y = yMin - 1; y <= yMax + 1; ++y) {
            Optional<Point2D> min = minDistance(points, Point2D.of(xMin - 1, y));
            min.ifPresent(infiniteAreas::add);
        }

        for (int y = yMin - 1; y <= yMax + 1; ++y) {
            Optional<Point2D> min = minDistance(points, Point2D.of(xMax + 1, y));
            min.ifPresent(infiniteAreas::add);
        }

        LOGGER.info("infiniteAreas: {}", infiniteAreas);

        infiniteAreas.forEach(areas::remove);

        LOGGER.info("Areas: {}", areas);

        return Collections.max(areas.values());
    }

    /**
     * --- Part Two ---
     *
     * On the other hand, if the coordinates are safe, maybe the best you can do
     * is try to find a region near as many coordinates as possible.
     *
     * For example, suppose you want the sum of the Manhattan distance to all of
     * the coordinates to be less than 32. For each location, add up the distances
     * to all of the given coordinates; if the total of those distances is less
     * than 32, that location is within the desired region. Using the same
     * coordinates as above, the resulting region looks like this:
     *
     * ..........
     * .A........
     * ..........
     * ...###..C.
     * ..#D###...
     * ..###E#...
     * .B.###....
     * ..........
     * ..........
     * ........F.
     *
     * In particular, consider the highlighted location 4,3 located at the top
     * middle of the region. Its calculation is as follows, where abs() is the
     * absolute value function:
     *
     *   - Distance to coordinate A: abs(4-1) + abs(3-1) =  5
     *   - Distance to coordinate B: abs(4-1) + abs(3-6) =  6
     *   - Distance to coordinate C: abs(4-8) + abs(3-3) =  4
     *   - Distance to coordinate D: abs(4-3) + abs(3-4) =  2
     *   - Distance to coordinate E: abs(4-5) + abs(3-5) =  3
     *   - Distance to coordinate F: abs(4-8) + abs(3-9) = 10
     *   - Total distance: 5 + 6 + 4 + 2 + 3 + 10 = 30
     *
     * Because the total distance to all coordinates (30) is less than 32, the
     * location is within the region.
     *
     * This region, which also includes coordinates D and E, has a total size of
     * 16.
     *
     * Your actual region will need to be much larger than this example, though,
     * instead including all locations with a total distance of less than 10000.
     *
     * What is the size of the region containing all locations which have a total
     * distance to all given coordinates of less than 10000?
     */
    static int region(Scanner scanner, int distance) {
        List<Point2D> points = readPoints(scanner);

        LOGGER.debug("Points: {}", points);
        int xMin = points.stream().mapToInt(Point2D::x).min().orElseThrow() - 1;
        int xMax = points.stream().mapToInt(Point2D::x).max().orElseThrow() + 1;
        int yMin = points.stream().mapToInt(Point2D::y).min().orElseThrow() - 1;
        int yMax = points.stream().mapToInt(Point2D::y).max().orElseThrow() + 1;

        Set<Point2D> region = new HashSet<>();

        for (int x = xMin; x <= xMax; ++x) {
            for (int y = yMin; y <= yMax; ++y) {
                Point2D p = Point2D.of(x, y);
                int sum = points.stream().mapToInt(a -> Point2D.manhattanDistance(a, p)).sum();
                if (sum <= distance) {
                    region.add(p);
                }
            }
        }

        return region.size();
    }

    private static List<Point2D> readPoints(Scanner scanner) {
        List<Point2D> points = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int[] array = Arrays.stream(line.split(", ")).mapToInt(Integer::parseInt).toArray();
            points.add(Point2D.of(array[0], array[1]));
        }

        return points;
    }
}
