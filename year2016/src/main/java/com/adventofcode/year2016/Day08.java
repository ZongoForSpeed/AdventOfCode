package com.adventofcode.year2016;

import com.adventofcode.point.map.BooleanMap;
import com.adventofcode.point.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public final class Day08 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day08.class);
    private static final Pattern PATTERN_RECT = Pattern.compile("rect (\\d+)x(\\d+)");
    private static final Pattern PATTERN_ROW = Pattern.compile("rotate row y=(\\d+) by (\\d+)");
    private static final Pattern PATTERN_COLUMN = Pattern.compile("rotate column x=(\\d+) by (\\d+)");

    private Day08() {
        // No-Op
    }

    /**
     * --- Day 8: Two-Factor Authentication ---
     *
     * You come across a door implementing what you can only assume is an
     * implementation of two-factor authentication after a long game of
     * requirements telephone.
     *
     * To get past the door, you first swipe a keycard (no problem; there was one
     * on a nearby desk). Then, it displays a code on a little screen, and you
     * type that code on a keypad. Then, presumably, the door unlocks.
     *
     * Unfortunately, the screen has been smashed. After a few minutes, you've
     * taken everything apart and figured out how it works. Now you just have to
     * work out what the screen would have displayed.
     *
     * The magnetic strip on the card you swiped encodes a series of instructions
     * for the screen; these instructions are your puzzle input. The screen is 50
     * pixels wide and 6 pixels tall, all of which start off, and is capable of
     * three somewhat peculiar operations:
     *
     *   - rect AxB turns on all of the pixels in a rectangle at the top-left of
     *     the screen which is A wide and B tall.
     *   - rotate row y=A by B shifts all of the pixels in row A (0 is the top
     *     row) right by B pixels. Pixels that would fall off the right end
     *     appear at the left end of the row.
     *   - rotate column x=A by B shifts all of the pixels in column A (0 is the
     *     left column) down by B pixels. Pixels that would fall off the bottom
     *     appear at the top of the column.
     *
     * For example, here is a simple sequence on a smaller screen:
     *
     *   - rect 3x2 creates a small rectangle in the top-left corner:
     *
     * ###....
     * ###....
     * .......
     *
     *   - rotate column x=1 by 1 rotates the second column down by one pixel:
     *
     * #.#....
     * ###....
     * .#.....
     *
     *   - rotate row y=0 by 4 rotates the top row right by four pixels:
     *
     * ....#.#
     * ###....
     * .#.....
     *
     *   - rotate column x=1 by 1 again rotates the second column down by one
     *     pixel, causing the bottom pixel to wrap back to the top:
     *
     * .#..#.#
     * #.#....
     * .#.....
     *
     * As you can see, this display technology is extremely powerful, and will soon dominate the tiny-code-displaying-screen market. That's what the advertisement on the back of the display tries to convince you, anyway.
     *
     * There seems to be an intermediate check of the voltage used by the display: after you swipe your card, if the screen did work, how many pixels should be lit?
     *
     * Your puzzle answer was 121.
     */
    static long cardinality(Scanner scanner) {
        BooleanMap booleanMap = buildScreen(scanner, 50, 6);
        return booleanMap.cardinality();
    }

    /**
     * --- Part Two ---
     *
     * You notice that the screen is only capable of displaying capital letters; in the font it uses, each letter is 5 pixels wide and 6 tall.
     *
     * After you swipe your card, what code is the screen trying to display?
     *
     * Your puzzle answer was RURUCEOEIL.
     */
    static BooleanMap buildScreen(Scanner scanner, int xMax, int yMax) {
        BooleanMap booleanMap = new BooleanMap(xMax - 1, yMax - 1);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN_RECT.matcher(line);
            if (matcher.matches()) {
                int wide = Integer.parseInt(matcher.group(1));
                int tall = Integer.parseInt(matcher.group(2));
                IntStream.range(0, wide).forEach(x -> IntStream.range(0, tall).forEach(y -> booleanMap.set(x, y)));
                LOGGER.info("After '{}':\n{}", line, booleanMap);
                continue;
            }

            matcher = PATTERN_COLUMN.matcher(line);
            if (matcher.matches()) {
                int column = Integer.parseInt(matcher.group(1));
                int shift = Integer.parseInt(matcher.group(2));
                List<Point2D> points = booleanMap.points().filter(p -> p.x() == column).toList();
                List<Point2D> shiftedPoints = points.stream().map(p -> Point2D.of(p.x(), (p.y() + shift) % yMax)).toList();
                points.forEach(booleanMap::reset);
                LOGGER.info("Points : {}", points);
                shiftedPoints.forEach(booleanMap::set);
                LOGGER.info("Shifted points : {}", shiftedPoints);
                LOGGER.info("After '{}':\n{}", line, booleanMap);
                continue;
            }

            matcher = PATTERN_ROW.matcher(line);
            if (matcher.matches()) {
                int row = Integer.parseInt(matcher.group(1));
                int shift = Integer.parseInt(matcher.group(2));
                List<Point2D> points = booleanMap.points().filter(p -> p.y() == row).toList();
                List<Point2D> shiftedPoints = points.stream().map(p -> Point2D.of((p.x() + shift) % xMax, p.y())).toList();
                points.forEach(booleanMap::reset);
                LOGGER.info("Points : {}", points);
                shiftedPoints.forEach(booleanMap::set);
                LOGGER.info("Shifted points : {}", shiftedPoints);
                LOGGER.info("After '{}':\n{}", line, booleanMap);
            } else {
                LOGGER.error("Cannot parse line '{}'", line);
            }
        }
        LOGGER.info("Map :\n{}", booleanMap);
        return booleanMap;
    }
}
