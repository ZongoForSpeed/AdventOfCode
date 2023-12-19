package com.adventofcode.year2023;

import com.adventofcode.point.Point2D;
import com.adventofcode.point.map.CharMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;

public final class Day13 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day13.class);

    private Day13() {
        // No-Op
    }

    /**
     * --- Day 13: Point of Incidence ---
     *
     * With your help, the hot springs team locates an appropriate spring which
     * launches you neatly and precisely up to the edge of Lava Island.
     *
     * There's just one problem: you don't see any lava.
     *
     * You do see a lot of ash and igneous rock; there are even what look like
     * gray mountains scattered around. After a while, you make your way to a
     * nearby cluster of mountains only to discover that the valley between them
     * is completely full of large mirrors. Most of the mirrors seem to be aligned
     * in a consistent way; perhaps you should head in that direction?
     *
     * As you move through the valley of mirrors, you find that several of them
     * have fallen from the large metal frames keeping them in place. The mirrors
     * are extremely flat and shiny, and many of the fallen mirrors have lodged
     * into the ash at strange angles. Because the terrain is all one color, it's
     * hard to tell where it's safe to walk or where you're about to run into a
     * mirror.
     *
     * You note down the patterns of ash (.) and rocks (#) that you see as you
     * walk (your puzzle input); perhaps by carefully analyzing these patterns,
     * you can figure out where the mirrors are!
     *
     * For example:
     *
     * #.##..##.
     * ..#.##.#.
     * ##......#
     * ##......#
     * ..#.##.#.
     * ..##..##.
     * #.#.##.#.
     *
     * #...##..#
     * #....#..#
     * ..##..###
     * #####.##.
     * #####.##.
     * ..##..###
     * #....#..#
     *
     * To find the reflection in each pattern, you need to find a perfect
     * reflection across either a horizontal line between two rows or across a
     * vertical line between two columns.
     *
     * In the first pattern, the reflection is across a vertical line between two
     * columns; arrows on each of the two columns point at the line between the
     * columns:
     *
     * 123456789
     *     ><
     * #.##..##.
     * ..#.##.#.
     * ##......#
     * ##......#
     * ..#.##.#.
     * ..##..##.
     * #.#.##.#.
     *     ><
     * 123456789
     *
     * In this pattern, the line of reflection is the vertical line between
     * columns 5 and 6. Because the vertical line is not perfectly in the middle
     * of the pattern, part of the pattern (column 1) has nowhere to reflect onto
     * and can be ignored; every other column has a reflected column within the
     * pattern and must match exactly: column 2 matches column 9, column 3 matches
     * 8, 4 matches 7, and 5 matches 6.
     *
     * The second pattern reflects across a horizontal line instead:
     *
     * 1 #...##..# 1
     * 2 #....#..# 2
     * 3 ..##..### 3
     * 4v#####.##.v4
     * 5^#####.##.^5
     * 6 ..##..### 6
     * 7 #....#..# 7
     *
     * This pattern reflects across the horizontal line between rows 4 and 5. Row
     * 1 would reflect with a hypothetical row 8, but since that's not in the
     * pattern, row 1 doesn't need to match anything. The remaining rows match:
     * row 2 matches row 7, row 3 matches row 6, and row 4 matches row 5.
     *
     * To summarize your pattern notes, add up the number of columns to the left
     * of each vertical line of reflection; to that, also add 100 multiplied by
     * the number of rows above each horizontal line of reflection. In the above
     * example, the first pattern's vertical line has 5 columns to its left and
     * the second pattern's horizontal line has 4 rows above it, a total of 405.
     *
     * Find the line of reflection in each of the patterns in your notes. What
     * number do you get after summarizing all of your notes?
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static long findReflection(Scanner scanner) {
            long total = 0;
            while (scanner.hasNextLine()) {
                CharMap map = CharMap.read(scanner, c -> c != '.', true);
                LOGGER.trace("map:\n{}", map);

                OptionalInt verticalReflection = findVerticalReflection(map);
                LOGGER.info("verticalReflection: {}", verticalReflection);

                if (verticalReflection.isPresent()) {
                    total += verticalReflection.getAsInt();
                }

                OptionalInt horizontalReflection = findHorizontalReflection(map);
                LOGGER.info("horizontalReflection: {}", horizontalReflection);
                if (horizontalReflection.isPresent()) {
                    total += 100L * horizontalReflection.getAsInt();
                }
            }
            return total;
        }

        private static OptionalInt findVerticalReflection(CharMap map) {
            List<Point2D> points = map.points();
            int xMax = points.stream().mapToInt(Point2D::x).max().orElseThrow();
            long[] rows = new long[xMax + 1];
            points.forEach(p -> rows[p.x()] += 1L << p.y());

            LOGGER.info("rows : {}", Arrays.toString(rows));
            return findReflection(rows);
        }

        private static OptionalInt findHorizontalReflection(CharMap map) {
            List<Point2D> points = map.points();
            int yMax = points.stream().mapToInt(Point2D::y).max().orElseThrow();
            long[] lines = new long[yMax + 1];
            // Map<Integer, List<Integer>> lines = new HashMap<>();
            points.forEach(p -> lines[p.y()] += 1L << p.x());

            LOGGER.info("lines : {}", Arrays.toString(lines));
            return findReflection(lines);
        }

        private static OptionalInt findReflection(long[] masks) {
            for (int i = 0; i < masks.length - 1; i++) {
                boolean test = true;
                for (int j = i, k = i + 1; j >= 0 && k < masks.length; --j, ++k) {
                    if (masks[j] != masks[k]) {
                        test = false;
                        break;
                    }
                }
                if (test) {
                    return OptionalInt.of(i + 1);
                }
            }

            return OptionalInt.empty();
        }
    }

    /**
     * --- Part Two ---
     *
     * You resume walking through the valley of mirrors and - SMACK! - run
     * directly into one. Hopefully nobody was watching, because that must have
     * been pretty embarrassing.
     *
     * Upon closer inspection, you discover that every mirror has exactly one
     * smudge: exactly one . or # should be the opposite type.
     *
     * In each pattern, you'll need to locate and fix the smudge that causes a
     * different reflection line to be valid. (The old reflection line won't
     * necessarily continue being valid after the smudge is fixed.)
     *
     * Here's the above example again:
     *
     * #.##..##.
     * ..#.##.#.
     * ##......#
     * ##......#
     * ..#.##.#.
     * ..##..##.
     * #.#.##.#.
     *
     * #...##..#
     * #....#..#
     * ..##..###
     * #####.##.
     * #####.##.
     * ..##..###
     * #....#..#
     *
     * The first pattern's smudge is in the top-left corner. If the top-left #
     * were instead ., it would have a different, horizontal line of reflection:
     *
     * 1 ..##..##. 1
     * 2 ..#.##.#. 2
     * 3v##......#v3
     * 4^##......#^4
     * 5 ..#.##.#. 5
     * 6 ..##..##. 6
     * 7 #.#.##.#. 7
     *
     * With the smudge in the top-left corner repaired, a new horizontal line of
     * reflection between rows 3 and 4 now exists. Row 7 has no corresponding
     * reflected row and can be ignored, but every other row matches exactly: row
     * 1 matches row 6, row 2 matches row 5, and row 3 matches row 4.
     *
     * In the second pattern, the smudge can be fixed by changing the fifth symbol
     * on row 2 from . to #:
     *
     * 1v#...##..#v1
     * 2^#...##..#^2
     * 3 ..##..### 3
     * 4 #####.##. 4
     * 5 #####.##. 5
     * 6 ..##..### 6
     * 7 #....#..# 7
     *
     * Now, the pattern has a different horizontal line of reflection between rows
     * 1 and 2.
     *
     * Summarize your notes as before, but instead use the new different
     * reflection lines. In this example, the first pattern's new horizontal line
     * has 3 rows above it and the second pattern's new horizontal line has 1 row
     * above it, summarizing to the value 400.
     *
     * In each pattern, fix the smudge and find the different line of reflection.
     * What number do you get after summarizing the new reflection line in each
     * pattern in your notes?
     */
    public static final class PartTwo {

        public static long findSmudge(Scanner scanner) {
            long total = 0;
            while (scanner.hasNextLine()) {
                CharMap map = CharMap.read(scanner, c -> c != '.', true);
                LOGGER.trace("map:\n{}", map);

                OptionalInt verticalSmudge = findVerticalSmudge(map);
                LOGGER.info("verticalSmudge: {}", verticalSmudge);

                if (verticalSmudge.isPresent()) {
                    total += verticalSmudge.getAsInt();
                }

                OptionalInt horizontalSmudge = findHorizontalSmudge(map);
                LOGGER.info("horizontalSmudge: {}", horizontalSmudge);
                if (horizontalSmudge.isPresent()) {
                    total += 100L * horizontalSmudge.getAsInt();
                }
            }
            return total;
        }

        public static OptionalInt findVerticalSmudge(CharMap map) {
            List<Point2D> points = map.points();
            int xMax = points.stream().mapToInt(Point2D::x).max().orElseThrow();
            long[] rows = new long[xMax + 1];
            points.forEach(p -> rows[p.x()] += 1L << p.y());

            LOGGER.info("rows : {}", Arrays.toString(rows));
            return findSmudge(rows);
        }

        public static OptionalInt findHorizontalSmudge(CharMap map) {
            List<Point2D> points = map.points();
            int yMax = points.stream().mapToInt(Point2D::y).max().orElseThrow();
            long[] lines = new long[yMax + 1];
            // Map<Integer, List<Integer>> lines = new HashMap<>();
            points.forEach(p -> lines[p.y()] += 1L << p.x());

            LOGGER.info("lines : {}", Arrays.toString(lines));
            return findSmudge(lines);
        }

        public static OptionalInt findSmudge(long[] masks) {
            for (int i = 0; i < masks.length - 1; i++) {
                int countSmudge = 0;
                boolean test = true;
                for (int j = i, k = i + 1; j >= 0 && k < masks.length; --j, ++k) {
                    long xor = Long.bitCount(masks[j] ^ masks[k]);
                    if (xor > 1) {
                        test = false;
                        break;
                    }
                    if (xor == 1) {
                        countSmudge++;
                    }
                }
                if (test && countSmudge == 1) {
                    return OptionalInt.of(i + 1);
                }
            }

            return OptionalInt.empty();
        }

    }
}
