package com.adventofcode.year2017;

import com.adventofcode.map.CharMap;
import com.adventofcode.map.Direction;
import com.adventofcode.map.Point2D;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public final class Day19 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day19.class);

    private Day19() {
        // No-Op
    }

    private static Pair<String, Integer> findPackets(Scanner scanner) {
        CharMap map = CharMap.read(scanner, c -> c != ' ');
        LOGGER.trace("Map:\n{}", map);

        Set<Point2D> points = new HashSet<>(map.points());
        int xMin = points.stream().mapToInt(Point2D::x).min().orElseThrow();
        int xMax = points.stream().mapToInt(Point2D::x).max().orElseThrow();
        int yMin = points.stream().mapToInt(Point2D::y).min().orElseThrow();
        int yMax = points.stream().mapToInt(Point2D::y).max().orElseThrow();

        Point2D start = Stream.concat(
                        points.stream().filter(p -> p.x() == xMin || p.x() == xMax).filter(p -> map.get(p) == '-'),
                        points.stream().filter(p -> p.y() == yMin || p.y() == yMax).filter(p -> map.get(p) == '|'))
                .findFirst().orElseThrow();

        StringBuilder stringBuilder = new StringBuilder();

        int count = 0;
        Set<Point2D> visited = new HashSet<>();

        Direction direction;
        if (start.x() == xMin) {
            direction = Direction.EAST;
        } else if (start.x() == xMax) {
            direction = Direction.WEST;
        } else if (start.y() == yMin) {
            direction = Direction.SOUTH;
        } else if (start.y() == yMax) {
            direction = Direction.NORTH;
        } else {
            throw new IllegalStateException("Cannot start from " + start);
        }

        Point2D current = start;
        while (points.contains(current)) {
            LOGGER.trace("Current point: {}", current);
            visited.add(current);
            count++;
            char c = map.get(current);
            if (c == '+') {
                Optional<Point2D> first = Optional.empty();
                for (Direction d : Direction.values()) {
                    Point2D p = current.move(d);
                    if (points.contains(p) && !visited.contains(p)) {
                        direction = d;
                        first = Optional.of(p);
                        break;
                    }
                }
                if (first.isEmpty()) {
                    LOGGER.warn("Cannot find next point after {}", current);
                    break;
                }
                current = first.get();
            } else if (c == '-' || c == '|') {
                current = current.move(direction);
            } else {
                stringBuilder.append(c);
                current = current.move(direction);
            }
        }

        LOGGER.info("Result = {}", stringBuilder);

        return Pair.of(stringBuilder.toString(), count);
    }

    /**
     * --- Day 19: A Series of Tubes ---
     *
     * Somehow, a network packet got lost and ended up here. It's trying to follow
     * a routing diagram (your puzzle input), but it's confused about where to go.
     *
     * Its starting point is just off the top of the diagram. Lines (drawn with |,
     * -, and +) show the path it needs to take, starting by going down onto the
     * only line connected to the top of the diagram. It needs to follow this path
     * until it reaches the end (located somewhere within the diagram) and stop
     * there.
     *
     * Sometimes, the lines cross over each other; in these cases, it needs to
     * continue going the same direction, and only turn left or right when there's
     * no other option. In addition, someone has left letters on the line; these
     * also don't change its direction, but it can use them to keep track of where
     * it's been. For example:
     *
     *      |
     *      |  +--+
     *      A  |  C
     *  F---|----E|--+
     *      |  |  |  D
     *      +B-+  +--+
     *
     * Given this diagram, the packet needs to take the following path:
     *
     *   - Starting at the only line touching the top of the diagram, it must go
     *     down, pass through A, and continue onward to the first +.
     *   - Travel right, up, and right, passing through B in the process.
     *   - Continue down (collecting C), right, and up (collecting D).
     *   - Finally, go all the way left through E and stopping at F.
     *
     * Following the path to the end, the letters it sees on its path are ABCDEF.
     *
     * The little packet looks up at you, hoping you can help it find the way.
     * What letters will it see (in the order it would see them) if it follows the
     * path? (The routing diagram is very wide; make sure you view it without line
     * wrapping.)
     *
     * Your puzzle answer was XYFDJNRCQA.
     */
    public static String findPacketsPartOne(Scanner scanner) {
        return findPackets(scanner).getLeft();
    }

    /**
     * --- Part Two ---
     *
     * The packet is curious how many steps it needs to go.
     *
     * For example, using the same routing diagram from the example above...
     *
     *      |
     *      |  +--+
     *      A  |  C
     *  F---|--|-E---+
     *      |  |  |  D
     *      +B-+  +--+
     *
     * ...the packet would go:
     *
     *   - 6 steps down (including the first line at the top of the diagram).
     *   - 3 steps right.
     *   - 4 steps up.
     *   - 3 steps right.
     *   - 4 steps down.
     *   - 3 steps right.
     *   - 2 steps up.
     *   - 13 steps left (including the F it stops on).
     *
     * This would result in a total of 38 steps.
     *
     * How many steps does the packet need to go?
     */
    public static int findPacketsPartTwo(Scanner scanner) {
        return findPackets(scanner).getRight();
    }
}
