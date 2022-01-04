package com.adventofcode.year2016;

import com.adventofcode.map.Direction;
import com.adventofcode.map.Point2D;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class Day01Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day01Test.class);

    private static long computeDistancePartOne(String input) {
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

    private static long computeDistancePartTwo(String input) {
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


    @Test
    void inputExample() {
        assertThat(computeDistancePartOne("R2, L3")).isEqualTo(5);
        assertThat(computeDistancePartOne("R2, R2, R2")).isEqualTo(2);
        assertThat(computeDistancePartOne("R5, L5, R5, R3")).isEqualTo(12);

        assertThat(computeDistancePartTwo("R8, R4, R4, R8")).isEqualTo(4);
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
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2016/day/1/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(computeDistancePartOne(scanner.nextLine())).isEqualTo(234);
        }
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
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day01Test.class.getResourceAsStream("/2016/day/1/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(computeDistancePartTwo(scanner.nextLine())).isEqualTo(113);
        }
    }

}
