package com.adventofcode.year2021;

import com.adventofcode.point.map.IntegerMap;
import com.adventofcode.point.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day05 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day05.class);

    private Day05() {
        // No-Op
    }

    /**
     * --- Day 5: Hydrothermal Venture ---
     *
     * You come across a field of hydrothermal vents on the ocean floor! These
     * vents constantly produce large, opaque clouds, so it would be best to avoid
     * them if possible.
     *
     * They tend to form in lines; the submarine helpfully produces a list of
     * nearby lines of vents (your puzzle input) for you to review. For example:
     *
     * 0,9 -> 5,9
     * 8,0 -> 0,8
     * 9,4 -> 3,4
     * 2,2 -> 2,1
     * 7,0 -> 7,4
     * 6,4 -> 2,0
     * 0,9 -> 2,9
     * 3,4 -> 1,4
     * 0,0 -> 8,8
     * 5,5 -> 8,2
     *
     * Each line of vents is given as a line segment in the format x1,y1 -> x2,y2
     * where x1,y1 are the coordinates of one end the line segment and x2,y2 are
     * the coordinates of the other end. These line segments include the points at
     * both ends. In other words:
     *
     *   - An entry like 1,1 -> 1,3 covers points 1,1, 1,2, and 1,3.
     *   - An entry like 9,7 -> 7,7 covers points 9,7, 8,7, and 7,7.
     *
     * For now, only consider horizontal and vertical lines: lines where either
     * x1 = x2 or y1 = y2.
     *
     * So, the horizontal and vertical lines from the above list would produce the
     * following diagram:
     *
     * .......1..
     * ..1....1..
     * ..1....1..
     * .......1..
     * .112111211
     * ..........
     * ..........
     * ..........
     * ..........
     * 222111....
     *
     * In this diagram, the top left corner is 0,0 and the bottom right corner is
     * 9,9. Each position is shown as the number of lines which cover that point
     * or . if no line covers that point. The top-left pair of 1s, for example,
     * comes from 2,2 -> 2,1; the very bottom row is formed by the overlapping
     * lines 0,9 -> 5,9 and 0,9 -> 2,9.
     *
     * To avoid the most dangerous areas, you need to determine the number of
     * points where at least two lines overlap. In the above example, this is
     * anywhere in the diagram with a 2 or larger - a total of 5 points.
     *
     * Consider only horizontal and vertical lines. At how many points do at least
     * two lines overlap?
     *
     * Your puzzle answer was 6572.
     */
    public static int countOverlapsPartOne(Scanner scanner) {
        IntegerMap map = new IntegerMap(10, 10, 0);
        Pattern pattern = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                int x1 = Integer.parseInt(matcher.group(1));
                int y1 = Integer.parseInt(matcher.group(2));
                int x2 = Integer.parseInt(matcher.group(3));
                int y2 = Integer.parseInt(matcher.group(4));
                // only consider horizontal and vertical lines
                if (x1 != x2 && y1 != y2) {
                    LOGGER.info("ignoring {},{} -> {},{}", x1, y1, x2, y2);

                    continue;
                }
                LOGGER.info("{},{} -> {},{}", x1, y1, x2, y2);
                int xMin = Math.min(x1, x2);
                int yMin = Math.min(y1, y2);
                int xMax = Math.max(x1, x2);
                int yMax = Math.max(y1, y2);
                for (int x = xMin; x <= xMax; ++x) {
                    for (int y = yMin; y <= yMax; ++y) {
                        map.increment(x, y, 1);
                    }
                }
            }
        }

        LOGGER.trace("Map \n{}", map);
        int counter = 0;
        for (Point2D point : map.points()) {
            if (map.get(point) > 1) {
                counter++;
            }
        }
        LOGGER.info("Counter: {}", counter);
        return counter;
    }

    /**
     * --- Part Two ---
     *
     * Unfortunately, considering only horizontal and vertical lines doesn't give
     * you the full picture; you need to also consider diagonal lines.
     *
     * Because of the limits of the hydrothermal vent mapping system, the lines in
     * your list will only ever be horizontal, vertical, or a diagonal line at
     * exactly 45 degrees. In other words:
     *
     *   - An entry like 1,1 -> 3,3 covers points 1,1, 2,2, and 3,3.
     *   - An entry like 9,7 -> 7,9 covers points 9,7, 8,8, and 7,9.
     *
     * Considering all lines from the above example would now produce the
     * following diagram:
     *
     * 1.1....11.
     * .111...2..
     * ..2.1.111.
     * ...1.2.2..
     * .112313211
     * ...1.2....
     * ..1...1...
     * .1.....1..
     * 1.......1.
     * 222111....
     *
     * You still need to determine the number of points where at least two lines
     * overlap. In the above example, this is still anywhere in the diagram with a
     * 2 or larger - now a total of 12 points.
     *
     * Consider all of the lines. At how many points do at least two lines
     * overlap?
     *
     * Your puzzle answer was 21466.
     */
    public static int countOverlapsPartTwo(Scanner scanner) {
        IntegerMap map = new IntegerMap(10, 10, 0);
        Pattern pattern = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                int x1 = Integer.parseInt(matcher.group(1));
                int y1 = Integer.parseInt(matcher.group(2));
                int x2 = Integer.parseInt(matcher.group(3));
                int y2 = Integer.parseInt(matcher.group(4));
                LOGGER.info("{},{} -> {},{}", x1, y1, x2, y2);

                int dX = -Integer.compare(x1, x2);
                int dY = -Integer.compare(y1, y2);

                if (dX == 0) {
                    for (int y = y1; y != y2 + dY; y += dY) {
                        map.increment(x1, y, 1);
                    }
                } else if (dY == 0) {
                    for (int x = x1; x != x2 + dX; x += dX) {
                        map.increment(x, y1, 1);
                    }
                } else {
                    for (int x = x1, y = y1; x != x2 + dX && y != y2 + dY; x += dX, y += dY) {
                        map.increment(x, y, 1);
                    }
                }

            }
        }

        LOGGER.trace("Map \n{}", map);
        int counter = 0;
        for (Point2D point : map.points()) {
            if (map.get(point) > 1) {
                counter++;
            }
        }
        LOGGER.info("Counter: {}", counter);
        return counter;
    }
}
