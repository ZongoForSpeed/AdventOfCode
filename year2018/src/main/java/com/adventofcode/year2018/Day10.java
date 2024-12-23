package com.adventofcode.year2018;

import com.adventofcode.common.graph.Connectivity;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.InfiniteBooleanMap;
import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day10 {
    private static final List<Point2D> NEIGHBOURS = List.of(
            Point2D.of(1, 1),
            Point2D.of(1, 0),
            Point2D.of(1, -1),
            Point2D.of(0, 1),
            Point2D.of(0, -1),
            Point2D.of(-1, 1),
            Point2D.of(-1, 0),
            Point2D.of(-1, -1)
    );
    private static final Logger LOGGER = LoggerFactory.getLogger(Day10.class);
    @SuppressWarnings("MisleadingEscapedSpace")
    private static final Pattern PATTERN = Pattern.compile("position=<\s*(-??\\d+),\s*(-??\\d+)> velocity=<\s*(-??\\d+),\s*(-??\\d+)>");

    private Day10() {
        // No-Op
    }

    static boolean adjacent(List<Pair<Point2D, Point2D>> stars) {
        Set<Point2D> points = stars.stream().map(Pair::left).collect(Collectors.toSet());

        Map<Point2D, List<Point2D>> graph = new HashMap<>();
        for (Point2D point : points) {
            List<Point2D> neighbors = NEIGHBOURS.stream().map(point::move).filter(points::contains).toList();
            if (neighbors.isEmpty()) {
                return false;
            }
            graph.put(point, neighbors);
        }

        Set<Point2D> visited = new HashSet<>();
        for (Point2D point : points) {
            if (!visited.contains(point)) {
                Set<Point2D> connected = Connectivity.connectedPoints(graph, point);
                visited.addAll(connected);

                if (connected.size() < 5) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * --- Day 10: The Stars Align ---
     * <p>
     * It's no use; your navigation system simply isn't capable of providing
     * walking directions in the arctic circle, and certainly not in 1018.
     * <p>
     * The Elves suggest an alternative. In times like these, North Pole rescue
     * operations will arrange points of light in the sky to guide missing Elves
     * back to base. Unfortunately, the message is easy to miss: the points move
     * slowly enough that it takes hours to align them, but have so much momentum
     * that they only stay aligned for a second. If you blink at the wrong time,
     * it might be hours before another message appears.
     * <p>
     * You can see these points of light floating in the distance, and record
     * their position in the sky and their velocity, the relative change in
     * position per second (your puzzle input). The coordinates are all given from
     * your perspective; given enough time, those positions and velocities will
     * move the points into a cohesive message!
     * <p>
     * Rather than wait, you decide to fast-forward the process and calculate what
     * the points will eventually spell.
     * <p>
     * For example, suppose you note the following points:
     * <p>
     * position=< 9,  1> velocity=< 0,  2>
     * position=< 7,  0> velocity=<-1,  0>
     * position=< 3, -2> velocity=<-1,  1>
     * position=< 6, 10> velocity=<-2, -1>
     * position=< 2, -4> velocity=< 2,  2>
     * position=<-6, 10> velocity=< 2, -2>
     * position=< 1,  8> velocity=< 1, -1>
     * position=< 1,  7> velocity=< 1,  0>
     * position=<-3, 11> velocity=< 1, -2>
     * position=< 7,  6> velocity=<-1, -1>
     * position=<-2,  3> velocity=< 1,  0>
     * position=<-4,  3> velocity=< 2,  0>
     * position=<10, -3> velocity=<-1,  1>
     * position=< 5, 11> velocity=< 1, -2>
     * position=< 4,  7> velocity=< 0, -1>
     * position=< 8, -2> velocity=< 0,  1>
     * position=<15,  0> velocity=<-2,  0>
     * position=< 1,  6> velocity=< 1,  0>
     * position=< 8,  9> velocity=< 0, -1>
     * position=< 3,  3> velocity=<-1,  1>
     * position=< 0,  5> velocity=< 0, -1>
     * position=<-2,  2> velocity=< 2,  0>
     * position=< 5, -2> velocity=< 1,  2>
     * position=< 1,  4> velocity=< 2,  1>
     * position=<-2,  7> velocity=< 2, -2>
     * position=< 3,  6> velocity=<-1, -1>
     * position=< 5,  0> velocity=< 1,  0>
     * position=<-6,  0> velocity=< 2,  0>
     * position=< 5,  9> velocity=< 1, -2>
     * position=<14,  7> velocity=<-2,  0>
     * position=<-3,  6> velocity=< 2, -1>
     * <p>
     * Each line represents one point. Positions are given as <X, Y> pairs: X
     * represents how far left (negative) or right (positive) the point appears,
     * while Y represents how far up (negative) or down (positive) the point
     * appears.
     * <p>
     * At 0 seconds, each point has the position given. Each second, each point's
     * velocity is added to its position. So, a point with velocity <1, -2> is
     * moving to the right, but is moving upward twice as quickly. If this point's
     * initial position were <3, 9>, after 3 seconds, its position would become
     * <6, 3>.
     * <p>
     * Over time, the points listed above would move like this:
     * <p>
     * Initially:
     * ........#.............
     * ................#.....
     * .........#.#..#.......
     * ......................
     * #..........#.#.......#
     * ...............#......
     * ....#.................
     * ..#.#....#............
     * .......#..............
     * ......#...............
     * ...#...#.#...#........
     * ....#..#..#.........#.
     * .......#..............
     * ...........#..#.......
     * #...........#.........
     * ...#.......#..........
     * <p>
     * After 1 second:
     * ......................
     * ......................
     * ..........#....#......
     * ........#.....#.......
     * ..#.........#......#..
     * ......................
     * ......#...............
     * ....##.........#......
     * ......#.#.............
     * .....##.##..#.........
     * ........#.#...........
     * ........#...#.....#...
     * ..#...........#.......
     * ....#.....#.#.........
     * ......................
     * ......................
     * <p>
     * After 2 seconds:
     * ......................
     * ......................
     * ......................
     * ..............#.......
     * ....#..#...####..#....
     * ......................
     * ........#....#........
     * ......#.#.............
     * .......#...#..........
     * .......#..#..#.#......
     * ....#....#.#..........
     * .....#...#...##.#.....
     * ........#.............
     * ......................
     * ......................
     * ......................
     * <p>
     * After 3 seconds:
     * ......................
     * ......................
     * ......................
     * ......................
     * ......#...#..###......
     * ......#...#...#.......
     * ......#...#...#.......
     * ......#####...#.......
     * ......#...#...#.......
     * ......#...#...#.......
     * ......#...#...#.......
     * ......#...#..###......
     * ......................
     * ......................
     * ......................
     * ......................
     * <p>
     * After 4 seconds:
     * ......................
     * ......................
     * ......................
     * ............#.........
     * ........##...#.#......
     * ......#.....#..#......
     * .....#..##.##.#.......
     * .......##.#....#......
     * ...........#....#.....
     * ..............#.......
     * ....#......#...#......
     * .....#.....##.........
     * ...............#......
     * ...............#......
     * ......................
     * ......................
     * <p>
     * After 3 seconds, the message appeared briefly: HI. Of course, your message
     * will be much longer and will take many more seconds to appear.
     * <p>
     * What message will eventually appear in the sky?
     * *
     * --- Part Two ---
     * <p>
     * Good thing you didn't have to wait, because that would have taken a long
     * time - much longer than the 3 seconds in the example above.
     * <p>
     * Impressed by your sub-hour communication capabilities, the Elves are
     * curious: exactly how many seconds would they have needed to wait for that
     * message to appear?
     */
    static Pair<String, Integer> decodeStars(Scanner scanner) {
        List<Pair<Point2D, Point2D>> stars = new ArrayList<>();

        while (scanner.hasNextLine()) {
            Matcher matcher = PATTERN.matcher(scanner.nextLine());
            if (matcher.matches()) {
                int x0 = Integer.parseInt(matcher.group(1));
                int y0 = Integer.parseInt(matcher.group(2));
                int dx = Integer.parseInt(matcher.group(3));
                int dy = Integer.parseInt(matcher.group(4));

                Point2D point2D = Point2D.of(x0, y0);
                Point2D v = Point2D.of(dx, dy);

                LOGGER.info("position = {}, velocity = {}", point2D, v);
                stars.add(Pair.of(point2D, v));
            }
        }

        for (int i = 1; ; ++i) {
            stars = stars.stream().map(p -> Pair.of(p.left().move(p.right()), p.right())).toList();

            if (adjacent(stars)) {
                String print = print(stars);
                LOGGER.info("After {} second(s): \n{}", i, print);
                return Pair.of(print, i);
            } else {
                LOGGER.trace("After {} second(s)", i);
            }
        }
    }

    private static String print(List<Pair<Point2D, Point2D>> stars) {
        InfiniteBooleanMap map = new InfiniteBooleanMap();
        stars.stream().map(Pair::left).forEach(p -> map.put(p, true));

        return map.toString();
    }
}
