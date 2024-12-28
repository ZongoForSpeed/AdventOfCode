package com.adventofcode.year2024;

import com.adventofcode.common.graph.AStar;
import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day18 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day18.class);
    private static final Pattern PATTERN = Pattern.compile("(\\d+),(\\d+)");

    private Day18() {
        // No-Op
    }

    /**
     * --- Day 18: RAM Run ---
     * <p>
     * You and The Historians look a lot more pixelated than you remember. You're
     * inside a computer at the North Pole!
     * <p>
     * Just as you're about to check out your surroundings, a program runs up to
     * you. "This region of memory isn't safe! The User misunderstood what a
     * pushdown automaton is and their algorithm is pushing whole bytes down on
     * top of us! Run!"
     * <p>
     * The algorithm is fast - it's going to cause a byte to fall into your memory
     * space once every nanosecond! Fortunately, you're faster, and by quickly
     * scanning the algorithm, you create a list of which bytes will fall (your
     * puzzle input) in the order they'll land in your memory space.
     * <p>
     * Your memory space is a two-dimensional grid with coordinates that range
     * from 0 to 70 both horizontally and vertically. However, for the sake of
     * example, suppose you're on a smaller grid with coordinates that range from
     * 0 to 6 and the following list of incoming byte positions:
     * <p>
     * 5,4
     * 4,2
     * 4,5
     * 3,0
     * 2,1
     * 6,3
     * 2,4
     * 1,5
     * 0,6
     * 3,3
     * 2,6
     * 5,1
     * 1,2
     * 5,5
     * 2,5
     * 6,5
     * 1,4
     * 0,4
     * 6,4
     * 1,1
     * 6,1
     * 1,0
     * 0,5
     * 1,6
     * 2,0
     * <p>
     * Each byte position is given as an X,Y coordinate, where X is the distance
     * from the left edge of your memory space and Y is the distance from the top
     * edge of your memory space.
     * <p>
     * You and The Historians are currently in the top left corner of the memory
     * space (at 0,0) and need to reach the exit in the bottom right corner (at
     * 70,70 in your memory space, but at 6,6 in this example). You'll need to
     * simulate the falling bytes to plan out where it will be safe to run; for
     * now, simulate just the first few bytes falling into your memory space.
     * <p>
     * As bytes fall into your memory space, they make that coordinate corrupted.
     * Corrupted memory coordinates cannot be entered by you or The Historians, so
     * you'll need to plan your route carefully. You also cannot leave the
     * boundaries of the memory space; your only hope is to reach the exit.
     * <p>
     * In the above example, if you were to draw the memory space after the first
     * 12 bytes have fallen (using . for safe and # for corrupted), it would look
     * like this:
     * <p>
     * ...#...
     * ..#..#.
     * ....#..
     * ...#..#
     * ..#..#.
     * .#..#..
     * #.#....
     * <p>
     * You can take steps up, down, left, or right. After just 12 bytes have
     * corrupted locations in your memory space, the shortest path from the top left corner to the exit would take 22 steps. Here (marked with O) is one such path:
     * <p>
     * OO.#OOO
     * .O#OO#O
     * .OOO#OO
     * ...#OO#
     * ..#OO#.
     * .#.O#..
     * #.#OOOO
     * <p>
     * Simulate the first kilobyte (1024 bytes) falling onto your memory space.
     * Afterward, what is the minimum number of steps needed to reach the exit?
     */
    public static long partOne(Scanner scanner, int size, int bytes) {
        List<Point2D> corruptedMemory = readCorruptedMemory(scanner);

        Set<Point2D> accessible = new HashSet<>();
        for (int x = 0; x <= size; ++x) {
            for (int y = 0; y <= size; ++y) {
                accessible.add(Point2D.of(x, y));
            }
        }

        corruptedMemory.stream().limit(bytes).forEach(accessible::remove);

        Point2D end = Point2D.of(size, size);
        Point2D start = Point2D.of(0, 0);

        return new PathFinder(accessible, start, end).findPath();
    }

    /**
     * --- Part Two ---
     * <p>
     * The Historians aren't as used to moving around in this pixelated universe
     * as you are. You're afraid they're not going to be fast enough to make it to
     * the exit before the path is completely blocked.
     * <p>
     * To determine how fast everyone needs to go, you need to determine the first
     * byte that will cut off the path to the exit.
     * <p>
     * In the above example, after the byte at 1,1 falls, there is still a path to
     * the exit:
     * <p>
     * O..#OOO
     * O##OO#O
     * O#OO#OO
     * OOO#OO#
     * ###OO##
     * .##O###
     * #.#OOOO
     * <p>
     * However, after adding the very next byte (at 6,1), there is no longer a path to
     * the exit:
     * <p>
     * ...#...
     * .##..##
     * .#..#..
     * ...#..#
     * ###..##
     * .##.###
     * #.#....
     * <p>
     * So, in this example, the coordinates of the first byte that prevents the
     * exit from being reachable are 6,1.
     * <p>
     * Simulate more of the bytes that are about to corrupt your memory space.
     * What are the coordinates of the first byte that will prevent the exit from
     * being reachable from your starting position? (Provide the answer as two
     * integers separated by a comma with no other characters.)
     */
    public static String partTwo(Scanner scanner, int size) {
        List<Point2D> corruptedMemory = readCorruptedMemory(scanner);

        Set<Point2D> accessible = new HashSet<>();
        for (int x = 0; x <= size; ++x) {
            for (int y = 0; y <= size; ++y) {
                accessible.add(Point2D.of(x, y));
            }
        }

        Point2D start = Point2D.of(0, 0);
        Point2D end = Point2D.of(size, size);

        PathFinder pathFinder = new PathFinder(accessible, start, end);

        for (Point2D memory : corruptedMemory) {
            pathFinder.remove(memory);
            long path = pathFinder.findPath();
            LOGGER.trace("After memory={}, path={}", memory, path);

            if (path == Long.MAX_VALUE) {
                return memory.x() + "," + memory.y();
            }
        }

        return null;
    }

    private static List<Point2D> readCorruptedMemory(Scanner scanner) {
        List<Point2D> corruptedMemory = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                corruptedMemory.add(Point2D.of(x, y));
            }
        }
        return corruptedMemory;
    }

    private static final class PathFinder extends AStar<Point2D> {

        private final Set<Point2D> accessible;
        private final Point2D start;
        private final Point2D end;

        private PathFinder(Set<Point2D> accessible, Point2D start, Point2D end) {
            this.accessible = accessible;
            this.start = start;
            this.end = end;
        }

        private boolean remove(Point2D memory) {
            return accessible.remove(memory);
        }

        @Override
        public Iterable<Move<Point2D>> next(Point2D node) {
            return Arrays.stream(Direction.values())
                    .map(node::move)
                    .filter(accessible::contains)
                    .map(AStar.Move::of)
                    .toList();
        }

        @Override
        public long heuristic(Point2D node) {
            return Point2D.manhattanDistance(node, end);
        }

        private long findPath() {
            return algorithm(start, end);
        }

    }

}
