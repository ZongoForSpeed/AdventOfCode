package com.adventofcode.year2016;

import com.adventofcode.graph.AStar;
import com.adventofcode.point.Direction;
import com.adventofcode.point.Point2D;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public final class Day13 {
    private static boolean isOpenSpace(Point2D p, int favoriteNumber) {
        long x = p.x();
        long y = p.y();
        long d = x * x + 3 * x + 2 * x * y + y + y * y + favoriteNumber;
        return Long.bitCount(d) % 2 == 0;
    }

    static List<Point2D> neighbours(Point2D p, int favoriteNumber) {
        return Arrays.stream(Direction.values())
                .map(p::move)
                .filter(o -> o.x() >= 0 && o.y() >= 0)
                .filter(o -> isOpenSpace(o, favoriteNumber))
                .toList();
    }

    /**
     * --- Day 13: A Maze of Twisty Little Cubicles ---
     *
     * You arrive at the first floor of this new building to discover a much less
     * welcoming environment than the shiny atrium of the last one. Instead, you
     * are in a maze of twisty little cubicles, all alike.
     *
     * Every location in this area is addressed by a pair of non-negative integers
     * (x,y). Each such coordinate is either a wall or an open space. You can't
     * move diagonally. The cube maze starts at 0,0 and seems to extend infinitely
     * toward positive x and y; negative values are invalid, as they represent a
     * location outside the building. You are in a small waiting area at 1,1.
     *
     * While it seems chaotic, a nearby morale-boosting poster explains, the
     * layout is actually quite logical. You can determine whether a given x,y
     * coordinate will be a wall or an open space using a simple system:
     *
     *   - Find x*x + 3*x + 2*x*y + y + y*y.
     *   - Add the office designer's favorite number (your puzzle input).
     *   - Find the binary representation of that sum; count the number of bits
     *     that are 1.
     *       - If the number of bits that are 1 is even, it's an open space.
     *       - If the number of bits that are 1 is odd, it's a wall.
     *
     * For example, if the office designer's favorite number were 10, drawing
     * walls as # and open spaces as ., the corner of the building containing 0,0
     * would look like this:
     *
     *   0123456789
     * 0 .#.####.##
     * 1 ..#..#...#
     * 2 #....##...
     * 3 ###.#.###.
     * 4 .##..#..#.
     * 5 ..##....#.
     * 6 #...##.###
     *
     * Now, suppose you wanted to reach 7,4. The shortest route you could take is
     * marked as O:
     *
     *   0123456789
     * 0 .#.####.##
     * 1 .O#..#...#
     * 2 #OOO.##...
     * 3 ###O#.###.
     * 4 .##OO#OO#.
     * 5 ..##OOO.#.
     * 6 #...##.###
     *
     * Thus, reaching 7,4 would take a minimum of 11 steps (starting from your
     * current location, 1,1).
     *
     * What is the fewest number of steps required for you to reach 31,39?
     *
     * Your puzzle input is 1364.
     *
     * Your puzzle answer was 86.
     */
    static long findPath(int favoriteNumber, Point2D start, Point2D end) {
        return AStar.algorithm(p -> neighbours(p, favoriteNumber), (a, b) -> 1L, start, end);
    }

    /**
     * --- Part Two ---
     *
     * How many locations (distinct x,y coordinates, including your starting
     * location) can you reach in at most 50 steps?
     *
     * Your puzzle answer was 127.
     */
    public static long algorithm(int favoriteNumber, Point2D start, long distance) {
        Set<Point2D> closedList = new HashSet<>();
        Queue<Node<Point2D>> queue = new PriorityQueue<>(Comparator.comparingLong(Node::cost));
        queue.add(new Node<>(start, 0L));
        while (!queue.isEmpty()) {
            Node<Point2D> node = queue.poll();
            if (closedList.add(node.vertex())) {
                Collection<Point2D> moves = neighbours(node.vertex(), favoriteNumber);
                for (Point2D move : moves) {
                    if (!closedList.contains(move)) {
                        long cost = node.cost() + 1;
                        if (cost <= distance) {
                            queue.add(new Node<>(move, cost));
                        }
                    }
                }
            }
        }

        return closedList.size();
    }

    private record Node<E>(E vertex, long cost) {
    }
}
