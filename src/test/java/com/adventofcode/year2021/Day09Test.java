package com.adventofcode.year2021;

import com.adventofcode.map.IntegerMap;
import com.adventofcode.map.Point2D;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class Day09Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day09Test.class);

    List<Point2D> adjacent(Point2D p, int xMax, int yMax) {
        List<Point2D> points = new ArrayList<>();
        if (p.x() > 0) {
            points.add(Point2D.of(p.x() - 1, p.y()));
        }
        if (p.y() > 0) {
            points.add(Point2D.of(p.x(), p.y() - 1));
        }
        if (p.x() < xMax) {
            points.add(Point2D.of(p.x() + 1, p.y()));
        }
        if (p.y() < yMax) {
            points.add(Point2D.of(p.x(), p.y() + 1));
        }

        return points;
    }

    @Test
    void inputExample() {
        String input = """
                2199943210
                3987894921
                9856789892
                8767896789
                9899965678""";

        assertThat(smokeBasinRisk(new Scanner(input))).isEqualTo(15);
        assertThat(smokeBasin(new Scanner(input))).isEqualTo(1134);
    }

    private int smokeBasinRisk(Scanner scanner) {
        IntegerMap map = new IntegerMap(0, 0, -1);
        int j = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            char[] charArray = line.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                map.set(i, j, charArray[i] - '0');
            }
            ++j;
        }

        LOGGER.info("Map \n{}", map);

        List<Point2D> points = map.points();
        int xMax = points.stream().mapToInt(Point2D::x).max().orElseThrow();
        int yMax = points.stream().mapToInt(Point2D::y).max().orElseThrow();

        int risk = 0;
        for (Point2D point : points) {
            int height = map.get(point);
            if (adjacent(point, xMax, yMax).stream().map(map::get).allMatch(h -> h > height)) {
                LOGGER.debug("low points: {}, height: {}", point, height);
                risk += height + 1;
            }
        }
        return risk;
    }

    private int smokeBasin(Scanner scanner) {
        IntegerMap map = new IntegerMap(0, 0, -1);
        int j = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            char[] charArray = line.toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                map.set(i, j, charArray[i] - '0');
            }
            ++j;
        }

        LOGGER.info("Map \n{}", map);

        List<Point2D> points = map.points();
        int xMax = points.stream().mapToInt(Point2D::x).max().orElseThrow();
        int yMax = points.stream().mapToInt(Point2D::y).max().orElseThrow();

        Map<Point2D, List<Point2D>> adjacent = new HashMap<>();
        for (Point2D point : points) {
            adjacent.put(point, adjacent(point, xMax, yMax));
        }

        List<Point2D> lowPoints = new ArrayList<>();
        for (Point2D point : points) {
            int height = map.get(point);
            if (adjacent.get(point).stream().map(map::get).allMatch(h -> h > height)) {
                lowPoints.add(point);
            }
        }

        Map<Point2D, Integer> basins = new HashMap<>();
        for (Point2D lowPoint : lowPoints) {
            Queue<Point2D> queue = new ArrayDeque<>();
            Set<Point2D> basin = new HashSet<>();

            if (!queue.offer(lowPoint)) {
                throw new IllegalStateException("Cannot offer to queue");
            }

            while (!queue.isEmpty()) {
                Point2D point = queue.poll();
                if (basin.add(point)) {
                    adjacent.get(point).stream().filter(p -> map.get(p) < 9).forEach(queue::offer);
                }
            }

            basins.put(lowPoint, basin.size());

            LOGGER.info("Basin: {} -> {}", lowPoint, basin.size());
        }

        return basins.values().stream().sorted(Collections.reverseOrder()).limit(3).reduce(1, (a, b) -> a * b);
    }

    /**
     * --- Day 9: Smoke Basin ---
     *
     * These caves seem to be lava tubes. Parts are even still volcanically
     * active; small hydrothermal vents release smoke into the caves that slowly
     * settles like rain.
     *
     * If you can model how the smoke flows through the caves, you might be able
     * to avoid it and be that much safer. The submarine generates a heightmap of
     * the floor of the nearby caves for you (your puzzle input).
     *
     * Smoke flows to the lowest point of the area it's in. For example, consider
     * the following heightmap:
     *
     * 2199943210
     * 3987894921
     * 9856789892
     * 8767896789
     * 9899965678
     *
     * Each number corresponds to the height of a particular location, where 9 is
     * the highest and 0 is the lowest a location can be.
     *
     * Your first goal is to find the low points - the locations that are lower
     * than any of its adjacent locations. Most locations have four adjacent
     * locations (up, down, left, and right); locations on the edge or corner of
     * the map have three or two adjacent locations, respectively. (Diagonal
     * locations do not count as adjacent.)
     *
     * In the above example, there are four low points, all highlighted: two are
     * in the first row (a 1 and a 0), one is in the third row (a 5), and one is
     * in the bottom row (also a 5). All other locations on the heightmap have
     * some lower adjacent location, and so are not low points.
     *
     * The risk level of a low point is 1 plus its height. In the above example,
     * the risk levels of the low points are 2, 1, 6, and 6. The sum of the risk
     * levels of all low points in the heightmap is therefore 15.
     *
     * Find all of the low points on your heightmap. What is the sum of the risk
     * levels of all low points on your heightmap?
     *
     * Your puzzle answer was 524.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day09Test.class.getResourceAsStream("/2021/day/9/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(smokeBasinRisk(scanner)).isEqualTo(524);
        }
    }

    /**
     * --- Part Two ---
     *
     * Next, you need to find the largest basins so you know what areas are most
     * important to avoid.
     *
     * A basin is all locations that eventually flow downward to a single low
     * point. Therefore, every low point has a basin, although some basins are
     * very small. Locations of height 9 do not count as being in any basin, and
     * all other locations will always be part of exactly one basin.
     *
     * The size of a basin is the number of locations within the basin, including
     * the low point. The example above has four basins.
     *
     * The top-left basin, size 3:
     *
     * 2199943210
     * 3987894921
     * 9856789892
     * 8767896789
     * 9899965678
     *
     * The top-right basin, size 9:
     *
     * 2199943210
     * 3987894921
     * 9856789892
     * 8767896789
     * 9899965678
     *
     * The middle basin, size 14:
     *
     * 2199943210
     * 3987894921
     * 9856789892
     * 8767896789
     * 9899965678
     *
     * The bottom-right basin, size 9:
     *
     * 2199943210
     * 3987894921
     * 9856789892
     * 8767896789
     * 9899965678
     *
     * Find the three largest basins and multiply their sizes together. In the
     * above example, this is 9 * 14 * 9 = 1134.
     *
     * What do you get if you multiply together the sizes of the three largest
     * basins?
     *
     * Your puzzle answer was 1235430.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day09Test.class.getResourceAsStream("/2021/day/9/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(smokeBasin(scanner)).isEqualTo(1235430);
        }
    }
}
