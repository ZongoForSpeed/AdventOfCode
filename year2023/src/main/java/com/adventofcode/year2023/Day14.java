package com.adventofcode.year2023;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public final class Day14 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day14.class);

    private Day14() {
        // No-Op
    }

    private enum TiltDirection {
        NORTH(Direction.UP) {
            @Override
            public int projection(Point2D p) {
                return p.y();
            }
        },
        WEST(Direction.LEFT) {
            @Override
            public int projection(Point2D p) {
                return p.x();
            }
        },
        SOUTH(Direction.DOWN) {
            @Override
            public int projection(Point2D p) {
                return -p.y();
            }
        },
        EAST(Direction.RIGHT) {
            @Override
            public int projection(Point2D p) {
                return -p.x();
            }
        };

        private final Direction direction;

        TiltDirection(Direction direction) {
            this.direction = direction;
        }

        public abstract int projection(Point2D p);

        public Direction getDirection() {
            return direction;
        }
    }


    private static CharMap tiltReflectorDish(CharMap reflectorDish, TiltDirection direction) {
        CharMap tiltedDish = new CharMap(reflectorDish);
        Map<Integer, List<Point2D>> roundedRocks = new TreeMap<>();
        tiltedDish.entries().stream()
                .filter(e -> e.rightChar() == 'O')
                .map(Pair::left)
                .forEach(p -> roundedRocks.computeIfAbsent(direction.projection(p), _ -> new ArrayList<>()).add(p));

        int xMax = tiltedDish.xMax();
        int yMax = tiltedDish.yMax();

        LOGGER.trace("roundedRocks: {}", roundedRocks);

        for (Map.Entry<Integer, List<Point2D>> entry : roundedRocks.entrySet()) {
            LOGGER.trace("entry : {}", entry);

            List<Point2D> points = entry.getValue();
            for (Point2D point : points) {
                Point2D newPosition = tiltRock(tiltedDish, point, xMax, yMax, direction.getDirection());

                tiltedDish.reset(point);
                tiltedDish.set(newPosition, 'O');
            }
        }

        LOGGER.trace("tiltedDish:\n{}", tiltedDish);
        return tiltedDish;
    }

    private static int computeTotalLoad(CharMap tiltedDish) {
        int yMax = tiltedDish.yMax();
        List<Point2D> newRoundedRocks = tiltedDish.entries().stream().filter(e -> e.rightChar() == 'O').map(Pair::left).toList();

        int totalLoad = 0;
        for (Point2D roundedRock : newRoundedRocks) {
            int load = yMax + 1 - roundedRock.y();
            LOGGER.trace("{} ==> load = {}", roundedRock, load);
            totalLoad += load;
        }
        return totalLoad;
    }

    private static Point2D tiltRock(CharMap dish, Point2D rock, int xMax, int yMax, Direction direction) {
        while (true) {
            Point2D move = rock.move(direction);
            if (move.x() < 0 || move.y() < 0 || move.x() > xMax || move.y() > yMax || dish.get(move) != ' ') {
                return rock;
            } else {
                rock = move;
            }
        }
    }

    /**
     * --- Day 14: Parabolic Reflector Dish ---
     *
     * You reach the place where all of the mirrors were pointing: a massive
     * parabolic reflector dish attached to the side of another large mountain.
     *
     * The dish is made up of many small mirrors, but while the mirrors themselves
     * are roughly in the shape of a parabolic reflector dish, each individual
     * mirror seems to be pointing in slightly the wrong direction. If the dish is
     * meant to focus light, all it's doing right now is sending it in a vague
     * direction.
     *
     * This system must be what provides the energy for the lava! If you focus the
     * reflector dish, maybe you can go where it's pointing and use the light to
     * fix the lava production.
     *
     * Upon closer inspection, the individual mirrors each appear to be connected
     * via an elaborate system of ropes and pulleys to a large metal platform
     * below the dish. The platform is covered in large rocks of various shapes.
     * Depending on their position, the weight of the rocks deforms the platform,
     * and the shape of the platform controls which ropes move and ultimately the
     * focus of the dish.
     *
     * In short: if you move the rocks, you can focus the dish. The platform even
     * has a control panel on the side that lets you tilt it in one of four
     * directions! The rounded rocks (O) will roll when the platform is tilted,
     * while the cube-shaped rocks (#) will stay in place. You note the positions
     * of all of the empty spaces (.) and rocks (your puzzle input). For example:
     *
     * O....#....
     * O.OO#....#
     * .....##...
     * OO.#O....O
     * .O.....O#.
     * O.#..O.#.#
     * ..O..#O..O
     * .......O..
     * #....###..
     * #OO..#....
     *
     * Start by tilting the lever so all of the rocks will slide north as far as
     * they will go:
     *
     * OOOO.#.O..
     * OO..#....#
     * OO..O##..O
     * O..#.OO...
     * ........#.
     * ..#....#.#
     * ..O..#.O.O
     * ..O.......
     * #....###..
     * #....#....
     *
     * You notice that the support beams along the north side of the platform are
     * damaged; to ensure the platform doesn't collapse, you should calculate the
     * total load on the north support beams.
     *
     * The amount of load caused by a single rounded rock (O) is equal to the
     * number of rows from the rock to the south edge of the platform, including
     * the row the rock is on. (Cube-shaped rocks (#) don't contribute to load.)
     * So, the amount of load caused by each rock in each row is as follows:
     *
     * OOOO.#.O.. 10
     * OO..#....#  9
     * OO..O##..O  8
     * O..#.OO...  7
     * ........#.  6
     * ..#....#.#  5
     * ..O..#.O.O  4
     * ..O.......  3
     * #....###..  2
     * #....#....  1
     *
     * The total load is the sum of the load caused by all of the rounded rocks.
     * In this example, the total load is 136.
     *
     * Tilt the platform so that the rounded rocks all roll north. Afterward, what
     * is the total load on the north support beams?
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static int findTotalLoad(Scanner scanner) {
            CharMap reflectorDish = CharMap.read(scanner, c -> c != '.');
            LOGGER.info("reflectorDish:\n{}", reflectorDish);

            CharMap tiltedDish = tiltReflectorDish(reflectorDish, TiltDirection.NORTH);

            return computeTotalLoad(tiltedDish);
        }
    }

    /**
     * --- Part Two ---
     *
     * The parabolic reflector dish deforms, but not in a way that focuses the
     * beam. To do that, you'll need to move the rocks to the edges of the
     * platform. Fortunately, a button on the side of the control panel labeled
     * "spin cycle" attempts to do just that!
     *
     * Each cycle tilts the platform four times so that the rounded rocks roll
     * north, then west, then south, then east. After each tilt, the rounded rocks
     * roll as far as they can before the platform tilts in the next direction.
     * After one cycle, the platform will have finished rolling the rounded rocks
     * in those four directions in that order.
     *
     * Here's what happens in the example above after each of the first few
     * cycles:
     *
     * After 1 cycle:
     * .....#....
     * ....#...O#
     * ...OO##...
     * .OO#......
     * .....OOO#.
     * .O#...O#.#
     * ....O#....
     * ......OOOO
     * #...O###..
     * #..OO#....
     *
     * After 2 cycles:
     * .....#....
     * ....#...O#
     * .....##...
     * ..O#......
     * .....OOO#.
     * .O#...O#.#
     * ....O#...O
     * .......OOO
     * #..OO###..
     * #.OOO#...O
     *
     * After 3 cycles:
     * .....#....
     * ....#...O#
     * .....##...
     * ..O#......
     * .....OOO#.
     * .O#...O#.#
     * ....O#...O
     * .......OOO
     * #...O###.O
     * #.OOO#...O
     *
     * This process should work if you leave it running long enough, but you're
     * still worried about the north support beams. To make sure they'll survive
     * for a while, you need to calculate the total load on the north support
     * beams after 1000000000 cycles.
     *
     * In the above example, after 1000000000 cycles, the total load on the north
     * support beams is 64.
     *
     * Run the spin cycle for 1000000000 cycles. Afterward, what is the total load
     * on the north support beams?
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        private static final List<TiltDirection> CYCLE = List.of(
                TiltDirection.NORTH,
                TiltDirection.WEST,
                TiltDirection.SOUTH,
                TiltDirection.EAST
        );

        private static CharMap cycleReflectorDish(CharMap reflectorDish) {
            for (TiltDirection direction : CYCLE) {
                reflectorDish = tiltReflectorDish(reflectorDish, direction);
            }

            return reflectorDish;
        }

        public static int findTotalLoad(Scanner scanner, int iteration) {
            CharMap reflectorDish = CharMap.read(scanner, c -> c != '.');

            boolean foundCycle = false;
            Map<String, Integer> seen = new HashMap<>();
            int i = 1;
            while (i <= iteration) {
                reflectorDish = cycleReflectorDish(reflectorDish);
                LOGGER.trace("After {} cycle(s):\n{}", i, reflectorDish);
                if (!foundCycle) {
                    String hash = reflectorDish.toString();
                    Integer previous = seen.put(hash, i);
                    if (previous != null) {
                        int diff = i - previous;
                        foundCycle = true;
                        LOGGER.info("Diff : {} : {} / {}", diff, i, previous);
                        while (i + diff < iteration) {
                            i += diff;
                        }
                        LOGGER.info("New iteration value = {}", iteration);
                    }
                }
                ++i;
            }

            LOGGER.trace("After {} cycle(s):\n{}", i, reflectorDish);

            return computeTotalLoad(reflectorDish);
        }
    }
}
