package com.adventofcode.year2023;

import com.adventofcode.point.Direction;
import com.adventofcode.point.Point2D;
import com.adventofcode.point.map.IntegerMap;
import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public final class Day17 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day17.class);

    private Day17() {
        // No-Op
    }

    /**
     * --- Day 17: Clumsy Crucible ---
     *
     * The lava starts flowing rapidly once the Lava Production Facility is
     * operational. As you leave, the reindeer offers you a parachute, allowing
     * you to quickly reach Gear Island.
     *
     * As you descend, your bird's-eye view of Gear Island reveals why you had
     * trouble finding anyone on your way up: half of Gear Island is empty, but
     * the half below you is a giant factory city!
     *
     * You land near the gradually-filling pool of lava at the base of your new
     * lavafall. Lavaducts will eventually carry the lava throughout the city, but
     * to make use of it immediately, Elves are loading it into large crucibles on
     * wheels.
     *
     * The crucibles are top-heavy and pushed by hand. Unfortunately, the
     * crucibles become very difficult to steer at high speeds, and so it can be
     * hard to go in a straight line for very long.
     *
     * To get Desert Island the machine parts it needs as soon as possible, you'll
     * need to find the best way to get the crucible from the lava pool to the
     * machine parts factory. To do this, you need to minimize heat loss while
     * choosing a route that doesn't require the crucible to go in a straight line
     * for too long.
     *
     * Fortunately, the Elves here have a map (your puzzle input) that uses
     * traffic patterns, ambient temperature, and hundreds of other parameters to
     * calculate exactly how much heat loss can be expected for a crucible
     * entering any particular city block.
     *
     * For example:
     *
     * 2413432311323
     * 3215453535623
     * 3255245654254
     * 3446585845452
     * 4546657867536
     * 1438598798454
     * 4457876987766
     * 3637877979653
     * 4654967986887
     * 4564679986453
     * 1224686865563
     * 2546548887735
     * 4322674655533
     *
     * Each city block is marked by a single digit that represents the amount of
     * heat loss if the crucible enters that block. The starting point, the lava
     * pool, is the top-left city block; the destination, the machine parts
     * factory, is the bottom-right city block. (Because you already start in the
     * top-left block, you don't incur that block's heat loss unless you leave
     * that block and then return to it.)
     *
     * Because it is difficult to keep the top-heavy crucible going in a straight
     * line for very long, it can move at most three blocks in a single direction
     * before it must turn 90 degrees left or right. The crucible also can't
     * reverse direction; after entering each city block, it may only turn left,
     * continue straight, or turn right.
     *
     * One way to minimize heat loss is this path:
     *
     * 2>>34^>>>1323
     * 32v>>>35v5623
     * 32552456v>>54
     * 3446585845v52
     * 4546657867v>6
     * 14385987984v4
     * 44578769877v6
     * 36378779796v>
     * 465496798688v
     * 456467998645v
     * 12246868655<v
     * 25465488877v5
     * 43226746555v>
     *
     * This path never moves more than three consecutive blocks in the same
     * direction and incurs a heat loss of only 102.
     *
     * Directing the crucible from the lava pool to the machine parts factory, but
     * not moving more than three consecutive blocks in the same direction, what
     * is the least heat loss it can incur?
     */
    public static final class PartOne {
        private PartOne() {
            // No-Op
        }

        public static int clumsyCrucible(Scanner scanner) {
            return minimizeHeatLoss(scanner, 1, 3);
        }
    }

    /**
     * --- Part Two ---
     *
     * The crucibles of lava simply aren't large enough to provide an adequate
     * supply of lava to the machine parts factory. Instead, the Elves are going
     * to upgrade to ultra crucibles.
     *
     * Ultra crucibles are even more difficult to steer than normal crucibles. Not
     * only do they have trouble going in a straight line, but they also have
     * trouble turning!
     *
     * Once an ultra crucible starts moving in a direction, it needs to move a
     * minimum of four blocks in that direction before it can turn (or even before
     * it can stop at the end). However, it will eventually start to get wobbly:
     * an ultra crucible can move a maximum of ten consecutive blocks without
     * turning.
     *
     * In the above example, an ultra crucible could follow this path to minimize
     * heat loss:
     *
     * 2>>>>>>>>1323
     * 32154535v5623
     * 32552456v4254
     * 34465858v5452
     * 45466578v>>>>
     * 143859879845v
     * 445787698776v
     * 363787797965v
     * 465496798688v
     * 456467998645v
     * 122468686556v
     * 254654888773v
     * 432267465553v
     *
     * In the above example, an ultra crucible would incur the minimum possible
     * heat loss of 94.
     *
     * Here's another example:
     *
     * 111111111111
     * 999999999991
     * 999999999991
     * 999999999991
     * 999999999991
     *
     * Sadly, an ultra crucible would need to take an unfortunate path like this
     * one:
     *
     * 1>>>>>>>1111
     * 9999999v9991
     * 9999999v9991
     * 9999999v9991
     * 9999999v>>>>
     *
     * This route causes the ultra crucible to incur the minimum possible heat
     * loss of 71.
     *
     * Directing the ultra crucible from the lava pool to the machine parts
     * factory, what is the least heat loss it can incur?
     */
    public static final class PartTwo {
        private PartTwo() {
            // No-Op
        }

        public static int ultraCrucible(Scanner scanner) {
            return minimizeHeatLoss(scanner, 4, 10);
        }
    }

    private static int minimizeHeatLoss(Scanner scanner, int minMove, int maxMove) {
        IntegerMap lavafall = IntegerMap.read(scanner);
        LOGGER.info("lavafall:\n{}", lavafall);

        int xMax = lavafall.xMax();
        int yMax = lavafall.yMax();

        Point2D start = Point2D.of(0, 0);
        Point2D end = Point2D.of(xMax, yMax);

        Set<Pair<Point2D, Direction>> closedList = new HashSet<>();
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(State::heuristic));

        queue.add(new State(start, Direction.RIGHT, 0, Point2D.manhattanDistance(start, end), List.of()));
        queue.add(new State(start, Direction.DOWN, 0, Point2D.manhattanDistance(start, end), List.of()));

        while (!queue.isEmpty()) {
            State current = queue.poll();
            LOGGER.trace("current = {}", current);
            Point2D position = current.position();
            if (end.equals(position)) {
                LOGGER.info("current = {}", current);
                return current.path();
            }
            Direction direction = current.direction();

            if (closedList.add(Pair.of(position, direction))) {
                List<State> next = current.nextStates(lavafall, minMove, maxMove, xMax, yMax, end);

                queue.addAll(next);
            }
        }
        throw new IllegalStateException();
    }

    record State(Point2D position, Direction direction, int path, int heuristic,
                 List<Pair<Direction, Integer>> realPath) {

        private List<State> nextStates(IntegerMap lavafall, int minMove, int maxMove, int xMax, int yMax, Point2D end) {
            Point2D position = position();
            Direction direction = direction();
            List<State> next = new ArrayList<>();
            int cost = path();
            for (int move = 1; move <= maxMove; ++move) {
                Point2D moved = position.move(direction, move);
                if (moved.x() < 0 || moved.x() > xMax || moved.y() < 0 || moved.y() > yMax) {
                    continue;
                }
                int lava = lavafall.get(moved);
                cost += lava;

                if (move < minMove) {
                    continue;
                }

                List<Pair<Direction, Integer>> realPath = new ArrayList<>(realPath());
                realPath.add(Pair.of(direction, move));

                int heuristic = cost + Point2D.manhattanDistance(moved, end);
                next.add(new State(moved, direction.left(), cost, heuristic, realPath));
                next.add(new State(moved, direction.right(), cost, heuristic, realPath));
            }
            return next;
        }

    }
}
