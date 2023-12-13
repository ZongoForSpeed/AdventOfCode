package com.adventofcode.year2023;

import com.adventofcode.point.Point2D;
import com.adventofcode.point.map.CharMap;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;
import java.util.List;
import java.util.Scanner;

public final class Day11 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day11.class);

    private static Pair<BitSet, BitSet> findEmpty(CharMap galaxyMap) {
        List<Point2D> points = galaxyMap.points();
        int xMax = points.stream().mapToInt(Point2D::x).max().orElseThrow();
        int yMax = points.stream().mapToInt(Point2D::y).max().orElseThrow();
        BitSet xSet = new BitSet(xMax);
        BitSet ySet = new BitSet(yMax);
        xSet.set(0, xMax, true);
        ySet.set(0, yMax, true);

        for (ObjectCharPair<Point2D> entry : galaxyMap.entries()) {
            if (entry.rightChar() == '#') {
                xSet.set(entry.left().x(), false);
                ySet.set(entry.left().y(), false);
            }
        }

        LOGGER.info("xSet = {}", xSet);
        LOGGER.info("ySet = {}", ySet);
        return Pair.of(xSet, ySet);
    }

    private static CharMap readGalaxyMap(Scanner scanner) {
        CharMap galaxyMap = CharMap.read(scanner, c -> true);

        LOGGER.info("galaxyMap:\n{}", galaxyMap);
        return galaxyMap;
    }

    /**
     * --- Day 11: Cosmic Expansion ---
     *
     * You continue following signs for "Hot Springs" and eventually come across
     * an observatory. The Elf within turns out to be a researcher studying cosmic
     * expansion using the giant telescope here.
     *
     * He doesn't know anything about the missing machine parts; he's only
     * visiting for this research project. However, he confirms that the hot
     * springs are the next-closest area likely to have people; he'll even take
     * you straight there once he's done with today's observation analysis.
     *
     * Maybe you can help him with the analysis to speed things up?
     *
     * The researcher has collected a bunch of data and compiled the data into a
     * single giant image (your puzzle input). The image includes empty space (.)
     * and galaxies (#). For example:
     *
     * ...#......
     * .......#..
     * #.........
     * ..........
     * ......#...
     * .#........
     * .........#
     * ..........
     * .......#..
     * #...#.....
     *
     * The researcher is trying to figure out the sum of the lengths of the
     * shortest path between every pair of galaxies. However, there's a catch: the
     * universe expanded in the time it took the light from those galaxies to
     * reach the observatory.
     *
     * Due to something involving gravitational effects, only some space expands.
     * In fact, the result is that any rows or columns that contain no galaxies
     * should all actually be twice as big.
     *
     * In the above example, three columns and two rows contain no galaxies:
     *
     *    v  v  v
     *  ...#......
     *  .......#..
     *  #.........
     * >..........<
     *  ......#...
     *  .#........
     *  .........#
     * >..........<
     *  .......#..
     *  #...#.....
     *    ^  ^  ^
     *
     * These rows and columns need to be twice as big; the result of cosmic
     * expansion therefore looks like this:
     *
     * ....#........
     * .........#...
     * #............
     * .............
     * .............
     * ........#....
     * .#...........
     * ............#
     * .............
     * .............
     * .........#...
     * #....#.......
     *
     * Equipped with this expanded universe, the shortest path between every pair
     * of galaxies can be found. It can help to assign every galaxy a unique
     * number:
     *
     * ....1........
     * .........2...
     * 3............
     * .............
     * .............
     * ........4....
     * .5...........
     * ............6
     * .............
     * .............
     * .........7...
     * 8....9.......
     *
     * In these 9 galaxies, there are 36 pairs. Only count each pair once; order
     * within the pair doesn't matter. For each pair, find any shortest path
     * between the two galaxies using only steps that move up, down, left, or
     * right exactly one . or # at a time. (The shortest path between two galaxies
     * is allowed to pass through another galaxy.)
     *
     * For example, here is one of the shortest paths between galaxies 5 and 9:
     *
     * ....1........
     * .........2...
     * 3............
     * .............
     * .............
     * ........4....
     * .5...........
     * .##.........6
     * ..##.........
     * ...##........
     * ....##...7...
     * 8....9.......
     *
     * This path has length 9 because it takes a minimum of nine steps to get from
     * galaxy 5 to galaxy 9 (the eight locations marked # plus the step onto
     * galaxy 9 itself). Here are some other example shortest path lengths:
     *
     *     Between galaxy 1 and galaxy 7: 15
     *     Between galaxy 3 and galaxy 6: 17
     *     Between galaxy 8 and galaxy 9: 5
     *
     * In this example, after expanding the universe, the sum of the shortest path
     * between all 36 pairs of galaxies is 374.
     *
     * Expand the universe, then find the length of the shortest path between
     * every pair of galaxies. What is the sum of these lengths?
     */
    public static final class PartOne {
        private PartOne() {
            // No-Op
        }

        public static long computeGalaxyLength(Scanner scanner) {
            CharMap galaxyMap = readGalaxyMap(scanner);

            Pair<BitSet, BitSet> empty = findEmpty(galaxyMap);

            int[] xArray = empty.left().stream().toArray();
            for (int i = 0; i < xArray.length; i++) {
                galaxyMap.insertColumn(xArray[i] + i, '.');
            }

            int[] yArray = empty.right().stream().toArray();
            for (int i = 0; i < yArray.length; i++) {
                galaxyMap.insertLine(yArray[i] + i, '.');
            }

            LOGGER.info("galaxyMap:\n{}", galaxyMap);

            List<Point2D> galaxies = galaxyMap.entries().stream().filter(e -> e.rightChar() == '#').map(Pair::left).toList();

            long sumLength = 0;

            for (int i = 1; i < galaxies.size(); i++) {
                for (int j = 0; j < i; j++) {
                    sumLength += Point2D.manhattanDistance(galaxies.get(i), galaxies.get(j));
                }
            }
            return sumLength;
        }
    }

    /**
     * --- Part Two ---
     *
     * The galaxies are much older (and thus much farther apart) than the
     * researcher initially estimated.
     *
     * Now, instead of the expansion you did before, make each empty row or column
     * one million times larger. That is, each empty row should be replaced with
     * 1000000 empty rows, and each empty column should be replaced with 1000000
     * empty columns.
     *
     * (In the example above, if each empty row or column were merely 10 times
     * larger, the sum of the shortest paths between every pair of galaxies would
     * be 1030. If each empty row or column were merely 100 times larger, the sum
     * of the shortest paths between every pair of galaxies would be 8410.
     * However, your universe will need to expand far beyond these values.)
     *
     * Starting with the same initial image, expand the universe according to
     * these new rules, then find the length of the shortest path between every
     * pair of galaxies. What is the sum of these lengths?
     */
    public static final class PartTwo {
        private PartTwo() {
            // No-Op
        }

        public static long computeGalaxyLength(Scanner scanner, long cosmicExpansion) {
            CharMap galaxyMap = readGalaxyMap(scanner);

            var result = findEmpty(galaxyMap);

            IntArrayList xList = IntArrayList.toList(result.left().stream());
            IntArrayList yList = IntArrayList.toList(result.right().stream());
            List<Point2D> galaxies = galaxyMap.entries().stream().filter(e -> e.rightChar() == '#').map(Pair::left).toList();

            long sumLength = 0;
            for (int i = 1; i < galaxies.size(); i++) {
                for (int j = 0; j < i; j++) {
                    sumLength += distance(galaxies.get(i), galaxies.get(j), xList, yList, cosmicExpansion);
                }
            }
            return sumLength;
        }

        private static long distance(Point2D g1, Point2D g2, IntArrayList xList, IntArrayList yList, long cosmicExpansion) {
            long distance = Point2D.manhattanDistance(g1, g2);
            int xMin = Math.min(g1.x(), g2.x());
            int yMin = Math.min(g1.y(), g2.y());
            int xMax = Math.max(g1.x(), g2.x());
            int yMax = Math.max(g1.y(), g2.y());
            if (xMin < xMax) {
                long factor = xList.intStream().filter(x -> x > xMin && x < xMax).count();
                distance += factor * (cosmicExpansion - 1);
            }
            if (yMin < yMax) {
                long factor = yList.intStream().filter(y -> y > yMin && y < yMax).count();
                distance += factor * (cosmicExpansion - 1);
            }
            return distance;
        }


    }
}
