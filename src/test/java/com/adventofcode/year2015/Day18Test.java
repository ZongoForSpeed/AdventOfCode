package com.adventofcode.year2015;

import com.adventofcode.map.BooleanMap;
import com.adventofcode.map.CharMap;
import com.adventofcode.map.Point2D;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;


class Day18Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day18Test.class);
    private static final List<Point2D> NEIGHBORS = List.of(
            Point2D.of(-1, -1),
            Point2D.of(0, -1),
            Point2D.of(1, -1),
            Point2D.of(-1, 0),
            Point2D.of(1, 0),
            Point2D.of(-1, 1),
            Point2D.of(0, 1),
            Point2D.of(1, 1)
    );

    private static List<Point2D> neighbors(Point2D point, int xMax, int yMax) {
        return NEIGHBORS.stream().map(point::move)
                .filter(p -> p.x() >= 0 && p.y() >= 0)
                .filter(p -> p.x() < xMax && p.y() < yMax)
                .toList();
    }

    private static BooleanMap nextStep(BooleanMap map, int xMax, int yMax, Set<Point2D> corners) {
        BooleanMap next = new BooleanMap();
        for (int x = 0; x < xMax; ++x) {
            for (int y = 0; y < yMax; ++y) {
                Point2D point = Point2D.of(x, y);
                if (corners.contains(point)) {
                    next.set(point);
                    continue;
                }
                long count = neighbors(point, xMax, yMax).stream().filter(map::get).count();
                if (map.get(point)) {
                    if (count == 2 || count == 3) {
                        next.set(point);
                    }
                } else {
                    if (count == 3) {
                        next.set(point);
                    }
                }
            }
        }

        return next;
    }

    private static long getLightsPartOne(Scanner scanner, int steps, int xMax, int yMax) {
        return getLights(scanner, steps, xMax, yMax, Collections.emptySet());
    }

    private static long getLightsPartTwo(Scanner scanner, int steps, int xMax, int yMax) {
        return getLights(scanner, steps, xMax, yMax, Set.of(
                Point2D.of(0, 0),
                Point2D.of(xMax - 1, 0),
                Point2D.of(0, yMax - 1),
                Point2D.of(xMax - 1, yMax - 1)
        ));
    }

    private static long getLights(Scanner scanner, int steps, int xMax, int yMax, Set<Point2D> corners) {
        BooleanMap map = BooleanMap.read(scanner, c -> c == '#');

        corners.forEach(map::set);

        LOGGER.info("Initial state:\n{}", map);

        for (int step = 1; step <= steps; ++step) {
            map = nextStep(map, xMax, yMax, corners);
            LOGGER.info("After {} steps:\n{}", step, map);
        }

        return map.cardinality();
    }

    @Test
    void inputExample() {
        String input = """
                .#.#.#
                ...##.
                #....#
                ..#...
                #.#..#
                ####..""";

        Assertions.assertThat(getLightsPartOne(new Scanner(input), 4, 6, 6)).isEqualTo(4);
        Assertions.assertThat(getLightsPartTwo(new Scanner(input), 5, 6, 6)).isEqualTo(17);
    }

    /**
     * --- Day 18: Like a GIF For Your Yard ---
     *
     * After the million lights incident, the fire code has gotten stricter: now,
     * at most ten thousand lights are allowed. You arrange them in a 100x100
     * grid.
     *
     * Never one to let you down, Santa again mails you instructions on the ideal
     * lighting configuration. With so few lights, he says, you'll have to resort
     * to animation.
     *
     * Start by setting your lights to the included initial configuration (your
     * puzzle input). A # means "on", and a . means "off".
     *
     * Then, animate your grid in steps, where each step decides the next
     * configuration based on the current one. Each light's next state (either on
     * or off) depends on its current state and the current states of the eight
     * lights adjacent to it (including diagonals). Lights on the edge of the grid
     * might have fewer than eight neighbors; the missing ones always count as
     * "off".
     *
     * For example, in a simplified 6x6 grid, the light marked A has the neighbors
     * numbered 1 through 8, and the light marked B, which is on an edge, only has
     * the neighbors marked 1 through 5:
     *
     * 1B5...
     * 234...
     * ......
     * ..123.
     * ..8A4.
     * ..765.
     *
     * The state a light should have next is based on its current state (on or
     * off) plus the number of neighbors that are on:
     *
     *   - A light which is on stays on when 2 or 3 neighbors are on, and turns
     *     off otherwise.
     *   - A light which is off turns on if exactly 3 neighbors are on, and stays
     *     off otherwise.
     *
     * All of the lights update simultaneously; they all consider the same current
     * state before moving to the next.
     *
     * Here's a few steps from an example configuration of another 6x6 grid:
     *
     * Initial state:
     * .#.#.#
     * ...##.
     * #....#
     * ..#...
     * #.#..#
     * ####..
     *
     * After 1 step:
     * ..##..
     * ..##.#
     * ...##.
     * ......
     * #.....
     * #.##..
     *
     * After 2 steps:
     * ..###.
     * ......
     * ..###.
     * ......
     * .#....
     * .#....
     *
     * After 3 steps:
     * ...#..
     * ......
     * ...#..
     * ..##..
     * ......
     * ......
     *
     * After 4 steps:
     * ......
     * ......
     * ..##..
     * ..##..
     * ......
     * ......
     *
     * After 4 steps, this example has four lights on.
     *
     * In your grid of 100x100 lights, given your initial configuration, how many
     * lights are on after 100 steps?
     *
     * Your puzzle answer was 768.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day18Test.class.getResourceAsStream("/2015/day/18/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            Assertions.assertThat(getLightsPartOne(scanner, 100, 100, 100)).isEqualTo(768);
        }
    }

    /**
     * --- Part Two ---
     *
     * You flip the instructions over; Santa goes on to point out that this is all
     * just an implementation of Conway's Game of Life. At least, it was, until
     * you notice that something's wrong with the grid of lights you bought: four
     * lights, one in each corner, are stuck on and can't be turned off. The
     * example above will actually run like this:
     *
     * Initial state:
     * ##.#.#
     * ...##.
     * #....#
     * ..#...
     * #.#..#
     * ####.#
     *
     * After 1 step:
     * #.##.#
     * ####.#
     * ...##.
     * ......
     * #...#.
     * #.####
     *
     * After 2 steps:
     * #..#.#
     * #....#
     * .#.##.
     * ...##.
     * .#..##
     * ##.###
     *
     * After 3 steps:
     * #...##
     * ####.#
     * ..##.#
     * ......
     * ##....
     * ####.#
     *
     * After 4 steps:
     * #.####
     * #....#
     * ...#..
     * .##...
     * #.....
     * #.#..#
     *
     * After 5 steps:
     * ##.###
     * .##..#
     * .##...
     * .##...
     * #.#...
     * ##...#
     *
     * After 5 steps, this example now has 17 lights on.
     *
     * In your grid of 100x100 lights, given your initial configuration, but with
     * the four corners always in the on state, how many lights are on after 100
     * steps?
     *
     * Your puzzle answer was 781.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day18Test.class.getResourceAsStream("/2015/day/18/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            Assertions.assertThat(getLightsPartTwo(scanner, 100, 100, 100)).isEqualTo(781);
        }
    }
}
