package com.adventofcode.year2017;

import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.BooleanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public final class Day21 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day21.class);
    private static final List<Point2D> POINTS = List.of(
            Point2D.of(0, 0), Point2D.of(1, 0), Point2D.of(2, 0),
            Point2D.of(0, 1), Point2D.of(1, 1), Point2D.of(2, 1),
            Point2D.of(0, 2), Point2D.of(1, 2), Point2D.of(2, 2)
    );

    private Day21() {
        // No-Op
    }

    private static BooleanMap readBooleanMap(String input) {
        BooleanMap map = new BooleanMap(0, 0, '/');
        String[] split = input.split("/");
        for (int i = 0; i < split.length; i++) {
            char[] charArray = split[i].toCharArray();
            for (int j = 0; j < charArray.length; j++) {
                map.set(j, i, charArray[j] == '#');
            }
        }

        return map;
    }

    /**
     * --- Day 21: Fractal Art ---
     * <p>
     * You find a program trying to generate some art. It uses a strange process
     * that involves repeatedly enhancing the detail of an image through a set of
     * rules.
     * <p>
     * The image consists of a two-dimensional square grid of pixels that are
     * either on (#) or off (.). The program always begins with this pattern:
     * <p>
     * .#.
     * ..#
     * ###
     * <p>
     * Because the pattern is both 3 pixels wide and 3 pixels tall, it is said to
     * have a size of 3.
     * <p>
     * Then, the program repeats the following process:
     * <p>
     * - If the size is evenly divisible by 2, break the pixels up into 2x2
     * squares, and convert each 2x2 square into a 3x3 square by following
     * the corresponding enhancement rule.
     * - Otherwise, the size is evenly divisible by 3; break the pixels up into
     * 3x3 squares, and convert each 3x3 square into a 4x4 square by
     * following the corresponding enhancement rule.
     * <p>
     * Because each square of pixels is replaced by a larger one, the image gains
     * pixels and so its size increases.
     * <p>
     * The artist's book of enhancement rules is nearby (your puzzle input);
     * however, it seems to be missing rules. The artist explains that sometimes,
     * one must rotate or flip the input pattern to find a match. (Never rotate or
     * flip the output pattern, though.) Each pattern is written concisely: rows
     * are listed as single units, ordered top-down, and separated by slashes. For
     * example, the following rules correspond to the adjacent patterns:
     * <p>
     * ../.#  =  ..
     * .#
     * <p>
     * .#.
     * .#./..#/###  =  ..#
     * ###
     * <p>
     * #..#
     * #..#/..../#..#/.##.  =  ....
     * #..#
     * .##.
     * <p>
     * When searching for a rule to use, rotate and flip the pattern as necessary.
     * For example, all of the following patterns match the same rule:
     * <p>
     * .#.   .#.   #..   ###
     * ..#   #..   #.#   ..#
     * ###   ###   ##.   .#.
     * <p>
     * Suppose the book contained the following two rules:
     * <p>
     * ../.# => ##./#../...
     * .#./..#/### => #..#/..../..../#..#
     * <p>
     * As before, the program begins with this pattern:
     * <p>
     * .#.
     * ..#
     * ###
     * <p>
     * The size of the grid (3) is not divisible by 2, but it is divisible by 3.
     * It divides evenly into a single square; the square matches the second rule,
     * which produces:
     * <p>
     * #..#
     * ....
     * ....
     * #..#
     * <p>
     * The size of this enhanced grid (4) is evenly divisible by 2, so that rule
     * is used. It divides evenly into four squares:
     * <p>
     * #.|.#
     * ..|..
     * --+--
     * ..|..
     * #.|.#
     * <p>
     * Each of these squares matches the same rule (../.# => ##./#../...), three
     * of which require some flipping and rotation to line up with the rule. The
     * output for the rule is the same in all four cases:
     * <p>
     * ##.|##.
     * #..|#..
     * ...|...
     * ---+---
     * ##.|##.
     * #..|#..
     * ...|...
     * <p>
     * Finally, the squares are joined into a new grid:
     * <p>
     * ##.##.
     * #..#..
     * ......
     * ##.##.
     * #..#..
     * ......
     * <p>
     * Thus, after 2 iterations, the grid contains 12 pixels that are on.
     * <p>
     * How many pixels stay on after 5 iterations?
     * <p>
     * --- Part Two ---
     * <p>
     * How many pixels stay on after 18 iterations?
     */
    public static BooleanMap buildFractal(Scanner scanner, int steps) {
        Map<Key, BooleanMap> mapping2 = new HashMap<>();
        Map<Key, BooleanMap> mapping3 = new HashMap<>();

        while (scanner.hasNextLine()) {
            String[] split = scanner.nextLine().split(" => ");
            BooleanMap first = readBooleanMap(split[0]);
            BooleanMap second = readBooleanMap(split[1]);

            if (first.xMax() + 1 == 2) {
                for (int i = 0; i < 4; ++i) {
                    mapping2.put(Key.of(first), second);
                    mapping2.put(Key.of(first.flipX()), second);
                    mapping2.put(Key.of(first.flipY()), second);
                    first = first.rotate();
                }
            } else {
                for (int i = 0; i < 4; ++i) {
                    mapping3.put(Key.of(first), second);
                    mapping3.put(Key.of(first.flipX()), second);
                    mapping3.put(Key.of(first.flipY()), second);
                    first = first.rotate();
                }
            }
        }

        BooleanMap fractal = BooleanMap.read("""
                .#.
                ..#
                ###""", c -> c == '#');

        LOGGER.trace("Before all steps:\n{}", fractal);

        for (int step = 1; step <= steps; ++step) {
            int size = fractal.xMax() + 1;
            int pattern;
            int newPattern;
            Map<Key, BooleanMap> mapping;
            int newSize;
            if (size % 2 == 0) {
                newSize = (size / 2) * 3;
                pattern = 2;
                newPattern = 3;
                mapping = mapping2;
            } else {
                newSize = (size / 3) * 4;
                pattern = 3;
                newPattern = 4;
                mapping = mapping3;
            }

            BooleanMap newFractal = new BooleanMap(newSize - 1, newSize - 1);
            for (int i = 0; i < size / pattern; ++i) {
                for (int j = 0; j < size / pattern; ++j) {
                    BooleanMap subMap = fractal.subMap(i * pattern, (i + 1) * pattern, j * pattern, (j + 1) * pattern);
                    BooleanMap matching = mapping.get(Key.of(subMap));
                    if (matching != null) {
                        Point2D offset = Point2D.of(i * newPattern, j * newPattern);
                        matching.points().map(offset::move).forEach(newFractal::set);
                    }
                }
            }

            fractal = newFractal;
            LOGGER.trace("After step {}:\n{}", step, fractal);
        }
        return fractal;
    }

    record Key(BooleanMap map, long hash) {
        private static Key of(BooleanMap map) {
            long hash = POINTS.stream().mapToLong(b -> map.get(b) ? 1 : 0).reduce(0L, (a, b) -> (a << 1) + b);
            return new Key(map, hash);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Key key)) return false;
            return hash == key.hash;
        }

        @Override
        public int hashCode() {
            return Long.hashCode(hash);
        }
    }
}
