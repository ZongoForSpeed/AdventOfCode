package com.adventofcode.year2024;

import com.adventofcode.common.maths.Arithmetic;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectArrayMap;
import it.unimi.dsi.fastutil.chars.Char2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;
import org.apache.commons.lang3.function.TriFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public final class Day08 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day08.class);

    private Day08() {
        // No-Op
    }

    private static Point2D symmetric(Point2D p1, Point2D p2) {
        return new Point2D(2 * p1.x() - p2.x(), 2 * p1.y() - p2.y());
    }

    /**
     * --- Day 8: Resonant Collinearity ---
     * <p>
     * You find yourselves on the roof of a top-secret Easter Bunny installation.
     * <p>
     * While The Historians do their thing, you take a look at the familiar huge
     * antenna. Much to your surprise, it seems to have been reconfigured to emit
     * a signal that makes people 0.1% more likely to buy Easter Bunny brand
     * Imitation Mediocre Chocolate as a Christmas gift! Unthinkable!
     * <p>
     * Scanning across the city, you find that there are actually many such
     * antennas. Each antenna is tuned to a specific frequency indicated by a
     * single lowercase letter, uppercase letter, or digit. You create a map (your
     * puzzle input) of these antennas. For example:
     * <p>
     * ............
     * ........0...
     * .....0......
     * .......0....
     * ....0.......
     * ......A.....
     * ............
     * ............
     * ........A...
     * .........A..
     * ............
     * ............
     * <p>
     * The signal only applies its nefarious effect at specific antinodes based on
     * the resonant frequencies of the antennas. In particular, an antinode occurs
     * at any point that is perfectly in line with two antennas of the same
     * frequency - but only when one of the antennas is twice as far away as the
     * other. This means that for any pair of antennas with the same frequency,
     * there are two antinodes, one on either side of them.
     * <p>
     * So, for these two antennas with frequency a, they create the two antinodes
     * marked with #:
     * <p>
     * ..........
     * ...#......
     * ..........
     * ....a.....
     * ..........
     * .....a....
     * ..........
     * ......#...
     * ..........
     * ..........
     * <p>
     * Adding a third antenna with the same frequency creates several more
     * antinodes. It would ideally add four antinodes, but two are off the right
     * side of the map, so instead it adds only two:
     * <p>
     * ..........
     * ...#......
     * #.........
     * ....a.....
     * ........a.
     * .....a....
     * ..#.......
     * ......#...
     * ..........
     * ..........
     * <p>
     * Antennas with different frequencies don't create antinodes; A and a count
     * as different frequencies. However, antinodes can occur at locations that
     * contain antennas. In this diagram, the lone antenna with frequency capital
     * A creates no antinodes but has a lowercase-a-frequency antinode at its
     * location:
     * <p>
     * ..........
     * ...#......
     * #.........
     * ....a.....
     * ........a.
     * .....a....
     * ..#.......
     * ......A...
     * ..........
     * ..........
     * <p>
     * The first example has antennas with two different frequencies, so the
     * antinodes they create look like this, plus an antinode overlapping the
     * topmost A-frequency antenna:
     * <p>
     * ......#....#
     * ...#....0...
     * ....#0....#.
     * ..#....0....
     * ....0....#..
     * .#....A.....
     * ...#........
     * #......#....
     * ........A...
     * .........A..
     * ..........#.
     * ..........#.
     * <p>
     * Because the topmost A-frequency antenna overlaps with a 0-frequency
     * antinode, there are 14 total unique locations that contain an antinode
     * within the bounds of the map.
     * <p>
     * Calculate the impact of the signal. How many unique locations within the
     * bounds of the map contain an antinode?
     */
    static Set<Point2D> partOne(Scanner scanner) {
        return findAntinodes(scanner, Day08::antinodesPartOne);
    }

    private static List<Point2D> antinodesPartOne(Point2D p1, Point2D p2, Set<Point2D> freeNodes) {
        Point2D symmetric = symmetric(p1, p2);
        if (freeNodes.contains(symmetric)) {
            return List.of(symmetric);
        } else {
            return List.of();
        }
    }

    /**
     * --- Part Two ---
     * <p>
     * Watching over your shoulder as you work, one of The Historians asks if you
     * took the effects of resonant harmonics into your calculations.
     * <p>
     * Whoops!
     * <p>
     * After updating your model, it turns out that an antinode occurs at any grid
     * position exactly in line with at least two antennas of the same frequency,
     * regardless of distance. This means that some of the new antinodes will
     * occur at the position of each antenna (unless that antenna is the only one
     * of its frequency).
     * <p>
     * So, these three T-frequency antennas now create many antinodes:
     * <p>
     * T....#....
     * ...T......
     * .T....#...
     * .........#
     * ..#.......
     * ..........
     * ...#......
     * ..........
     * ....#.....
     * ..........
     * <p>
     * In fact, the three T-frequency antennas are all exactly in line with two
     * antennas, so they are all also antinodes! This brings the total number of
     * antinodes in the above example to 9.
     * <p>
     * The original example now has 34 antinodes, including the antinodes that
     * appear on every antenna:
     * <p>
     * ##....#....#
     * .#.#....0...
     * ..#.#0....#.
     * ..##...0....
     * ....0....#..
     * .#...#A....#
     * ...#..#.....
     * #....#.#....
     * ..#.....A...
     * ....#....A..
     * .#........#.
     * ...#......##
     * <p>
     * Calculate the impact of the signal using this updated model. How many
     * unique locations within the bounds of the map contain an antinode?
     */
    static Set<Point2D> partTwo(Scanner scanner) {
        return findAntinodes(scanner, Day08::antinodesPartTwo);
    }

    private static List<Point2D> antinodesPartTwo(Point2D p1, Point2D p2, Set<Point2D> freeNodes) {
        Point2D diff = Point2D.of(p1.x() - p2.x(), p1.y() - p2.y());
        if (diff.x() == 0) {
            diff = Point2D.of(0, diff.y() > 0 ? 1 : -1);
        } else if (diff.y() == 0) {
            diff = Point2D.of(diff.x() > 0 ? 1 : -1, 0);
        } else {
            int gcd = Arithmetic.gcd(Math.abs(diff.x()), Math.abs(diff.y()));
            diff = Point2D.of(diff.x() / gcd, diff.y() / gcd);
        }

        List<Point2D> nodes = new ArrayList<>();

        Point2D move = p2.move(diff);

        while (freeNodes.contains(move)) {
            nodes.add(move);
            move = move.move(diff);
        }

        return nodes;
    }

    private static Set<Point2D> findAntinodes(Scanner scanner, TriFunction<Point2D, Point2D, Set<Point2D>, List<Point2D>> function) {
        CharMap map = CharMap.read(scanner, ignore -> true);

        LOGGER.debug("Map:\n{}", map);

        Char2ObjectMap<List<Point2D>> antennas = new Char2ObjectArrayMap<>();

        Set<Point2D> freeNodes = new HashSet<>();

        for (ObjectCharPair<Point2D> entry : map.entries()) {
            freeNodes.add(entry.left());
            if (entry.rightChar() != '.') {
                antennas.computeIfAbsent(entry.rightChar(), ignore -> new ArrayList<>()).add(entry.left());
            }
        }

        LOGGER.debug("Antennas: {}", antennas);

        Set<Point2D> antinodes = new HashSet<>();

        for (List<Point2D> value : antennas.values()) {
            for (int i = 0; i < value.size(); i++) {
                Point2D antenna1 = value.get(i);
                for (int j = 0; j < value.size(); ++j) {
                    if (i != j) {
                        Point2D antenna2 = value.get(j);
                        antinodes.addAll(function.apply(antenna1, antenna2, freeNodes));
                    }
                }
            }
        }

        // antinodes.forEach(n -> map.set(n, '#'));
        // LOGGER.debug("Map:\n{}", map);
        return antinodes;
    }
}
