package com.adventofcode.year2016;

import com.adventofcode.map.Direction;
import com.adventofcode.map.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public final class Day01 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day01.class);

    private Day01() {
        // No-Op
    }

    /**
     * --- Day 1: No Time for a Taxicab ---
     *
     * Santa's sleigh uses a very high-precision clock to guide its movements, and
     * the clock's oscillator is regulated by stars. Unfortunately, the stars have
     * been stolen... by the Easter Bunny. To save Christmas, Santa needs you to
     * retrieve all fifty stars by December 25th.
     *
     * Collect stars by solving puzzles. Two puzzles will be made available on
     * each day in the Advent calendar; the second puzzle is unlocked when you
     * complete the first. Each puzzle grants one star. Good luck!
     *
     * You're airdropped near Easter Bunny Headquarters in a city somewhere.
     * "Near", unfortunately, is as close as you can get - the instructions on the
     * Easter Bunny Recruiting Document the Elves intercepted start here, and
     * nobody had time to work them out further.
     *
     * The Document indicates that you should start at the given coordinates
     * (where you just landed) and face North. Then, follow the provided sequence:
     * either turn left (L) or right (R) 90 degrees, then walk forward the given
     * number of blocks, ending at a new intersection.
     *
     * There's no time to follow such ridiculous instructions on foot, though,
     * so you take a moment and work out the destination. Given that you can only
     * walk on the street grid of the city, how far is the shortest path to the
     * destination?
     *
     * For example:
     *
     *   - Following R2, L3 leaves you 2 blocks East and 3 blocks North, or 5
     *     blocks away.
     *   - R2, R2, R2 leaves you 2 blocks due South of your starting position,
     *     which is 2 blocks away.
     *   - R5, L5, R5, R3 leaves you 12 blocks away.
     *
     * How many blocks away is Easter Bunny HQ?
     *
     * Your puzzle answer was 234.
     */
    public static int computeDistancePartOne(String input) {
        Point2D position = Point2D.of(0, 0);
        Direction direction = Direction.NORTH;
        for (String s : input.split(", ")) {
            direction = switch (s.charAt(0)) {
                case 'L' -> direction.left();
                case 'R' -> direction.right();
                default -> throw new IllegalStateException("Unknown input: " + s);
            };
            int d = Integer.parseInt(s.substring(1));
            position = position.move(direction, d);
        }

        LOGGER.info("Final position: {}", position);
        return Math.abs(position.x()) + Math.abs(position.y());
    }

    /**
     * --- Part Two ---
     *
     * Then, you notice the instructions continue on the back of the Recruiting
     * Document. Easter Bunny HQ is actually at the first location you visit
     * twice.
     *
     * For example, if your instructions are R8, R4, R4, R8, the first location
     * you visit twice is 4 blocks away, due East.
     *
     * How many blocks away is the first location you visit twice?
     *
     * Your puzzle answer was 113.
     */
    public static int computeDistancePartTwo(String input) {
        Set<Point2D> visited = new HashSet<>();
        Point2D position = Point2D.of(0, 0);
        visited.add(position);
        Direction direction = Direction.NORTH;
        mainLoop:
        for (String s : input.split(", ")) {
            direction = switch (s.charAt(0)) {
                case 'L' -> direction.left();
                case 'R' -> direction.right();
                default -> throw new IllegalStateException("Unknown input: " + s);
            };
            int d = Integer.parseInt(s.substring(1));
            while (d-- > 0) {
                position = position.move(direction);
                if (!visited.add(position)) {
                    break mainLoop;
                }
            }
        }

        LOGGER.info("Final position: {}", position);
        return Math.abs(position.x()) + Math.abs(position.y());
    }
}
