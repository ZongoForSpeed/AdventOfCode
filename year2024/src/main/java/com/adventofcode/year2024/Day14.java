package com.adventofcode.year2024;

import com.adventofcode.common.graph.Connectivity;
import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import jakarta.annotation.Nonnull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Day14 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day14.class);
    private static final Pattern PATTERN = Pattern.compile("p=(-?\\d+),(-?\\d+) v=(-?\\d+),(-?\\d+)");

    private Day14() {
        // No-Op
    }

    /**
     * --- Day 14: Restroom Redoubt ---
     * <p>
     * One of The Historians needs to use the bathroom; fortunately, you know
     * there's a bathroom near an unvisited location on their list, and so you're
     * all quickly teleported directly to the lobby of Easter Bunny Headquarters.
     * <p>
     * Unfortunately, EBHQ seems to have "improved" bathroom security again after
     * your last visit. The area outside the bathroom is swarming with robots!
     * <p>
     * To get The Historian safely to the bathroom, you'll need a way to predict
     * where the robots will be in the future. Fortunately, they all seem to be
     * moving on the tile floor in predictable straight lines.
     * <p>
     * You make a list (your puzzle input) of all of the robots' current positions
     * (p) and velocities (v), one robot per line. For example:
     * <p>
     * p=0,4 v=3,-3
     * p=6,3 v=-1,-3
     * p=10,3 v=-1,2
     * p=2,0 v=2,-1
     * p=0,0 v=1,3
     * p=3,0 v=-2,-2
     * p=7,6 v=-1,-3
     * p=3,0 v=-1,-2
     * p=9,3 v=2,3
     * p=7,3 v=-1,2
     * p=2,4 v=2,-3
     * p=9,5 v=-3,-3
     * <p>
     * Each robot's position is given as p=x,y where x represents the number of
     * tiles the robot is from the left wall and y represents the number of tiles
     * from the top wall (when viewed from above). So, a position of p=0,0 means
     * the robot is all the way in the top-left corner.
     * <p>
     * Each robot's velocity is given as v=x,y where x and y are given in tiles
     * per second. Positive x means the robot is moving to the right, and positive
     * y means the robot is moving down. So, a velocity of v=1,-2 means that each
     * second, the robot moves 1 tile to the right and 2 tiles up.
     * <p>
     * The robots outside the actual bathroom are in a space which is 101 tiles
     * wide and 103 tiles tall (when viewed from above). However, in this example,
     * the robots are in a space which is only 11 tiles wide and 7 tiles tall.
     * <p>
     * The robots are good at navigating over/under each other (due to a
     * combination of springs, extendable legs, and quadcopters), so they can
     * share the same tile and don't interact with each other. Visually, the
     * number of robots on each tile in this example looks like this:
     * <p>
     * 1.12.......
     * ...........
     * ...........
     * ......11.11
     * 1.1........
     * .........1.
     * .......1...
     * <p>
     * These robots have a unique feature for maximum bathroom security: they can
     * teleport. When a robot would run into an edge of the space they're in, they
     * instead teleport to the other side, effectively wrapping around the edges.
     * Here is what robot p=2,4 v=2,-3 does for the first few seconds:
     * <p>
     * Initial state:
     * ...........
     * ...........
     * ...........
     * ...........
     * ..1........
     * ...........
     * ...........
     * <p>
     * After 1 second:
     * ...........
     * ....1......
     * ...........
     * ...........
     * ...........
     * ...........
     * ...........
     * <p>
     * After 2 seconds:
     * ...........
     * ...........
     * ...........
     * ...........
     * ...........
     * ......1....
     * ...........
     * <p>
     * After 3 seconds:
     * ...........
     * ...........
     * ........1..
     * ...........
     * ...........
     * ...........
     * ...........
     * <p>
     * After 4 seconds:
     * ...........
     * ...........
     * ...........
     * ...........
     * ...........
     * ...........
     * ..........1
     * <p>
     * After 5 seconds:
     * ...........
     * ...........
     * ...........
     * .1.........
     * ...........
     * ...........
     * ...........
     * <p>
     * The Historian can't wait much longer, so you don't have to simulate the
     * robots for very long. Where will the robots be after 100 seconds?
     * <p>
     * In the above example, the number of robots on each tile after 100 seconds
     * has elapsed looks like this:
     * <p>
     * ......2..1.
     * ...........
     * 1..........
     * .11........
     * .....1.....
     * ...12......
     * .1....1....
     * <p>
     * To determine the safest area, count the number of robots in each quadrant
     * after 100 seconds. Robots that are exactly in the middle (horizontally or
     * vertically) don't count as being in any quadrant, so the only relevant
     * robots are:
     * <p>
     * ..... 2..1.
     * ..... .....
     * 1.... .....
     * <p>
     * ..... .....
     * ...12 .....
     * .1... 1....
     * <p>
     * In this example, the quadrants contain 1, 3, 4, and 1 robot. Multiplying
     * these together gives a total safety factor of 12.
     * <p>
     * Predict the motion of the robots in your list within a space which is 101
     * tiles wide and 103 tiles tall. What will the safety factor be after exactly
     * 100 seconds have elapsed?
     */
    public static long partOne(Scanner scanner, int xMax, int yMax, int seconds, boolean debug) {
        List<Robot> robots = readInput(scanner);

        if (debug) {
            LOGGER.info("Initial state:");
            printGrid(robots, xMax, yMax);
        }

        for (int second = 1; second <= seconds; ++second) {
            robots = robots.stream().map(r -> r.move(xMax, yMax)).toList();
            if (debug) {
                LOGGER.info("After {} seconds:\n", second);
                printGrid(robots, xMax, yMax);
            }
        }

        int quadrantX = xMax / 2;
        int quadrantY = yMax / 2;

        Map<Point2D, Integer> quadrants = new HashMap<>();
        for (Robot robot : robots) {
            int x = Integer.compare(robot.p.x(), quadrantX);
            int y = Integer.compare(robot.p.y(), quadrantY);
            if (x != 0 && y != 0) {
                quadrants.merge(new Point2D(x, y), 1, Integer::sum);
            }
        }

        LOGGER.info("quadrants: {}", quadrants);

        return quadrants.values().stream().mapToLong(i -> i).reduce(1, (a, b) -> a * b);
    }

    /**
     * --- Part Two ---
     * <p>
     * During the bathroom break, someone notices that these robots seem awfully
     * similar to ones built and used at the North Pole. If they're the same type
     * of robots, they should have a hard-coded Easter egg: very rarely, most of
     * the robots should arrange themselves into a picture of a Christmas tree.
     * <p>
     * What is the fewest number of seconds that must elapse for the robots to
     * display the Easter egg?
     */
    public static long partTwo(Scanner scanner, int xMax, int yMax) {
        List<Robot> robots = readInput(scanner);

        for (int second = 1; ; ++second) {
            robots = robots.stream().map(r -> r.move(xMax, yMax)).toList();
            Map<Point2D, Set<Point2D>> regions = findRegions(robots);
            long count = regions.values().stream().mapToInt(Set::size).filter(s -> s > 10).count();
            if (count >= 2) {
                LOGGER.info("After {} seconds:\n", second);
                printGrid(robots, xMax, yMax);
                return second;
            } else if (second % 1000 == 0) {
                LOGGER.info("Nothing found after {} seconds", second);
            }
        }
    }

    private static List<Robot> readInput(Scanner scanner) {
        List<Robot> robots = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                int vx = Integer.parseInt(matcher.group(3));
                int vy = Integer.parseInt(matcher.group(4));

                Robot robot = new Robot(new Point2D(x, y), new Point2D(vx, vy));

                robots.add(robot);
            }
        }

        LOGGER.info("robots = {}", robots);
        return robots;
    }

    private static void printGrid(List<Robot> robots, int xMax, int yMax) {
        Map<Point2D, Integer> positions = new HashMap<>();
        for (Robot robot : robots) {
            positions.merge(robot.p, 1, Integer::sum);
        }

        CharMap map = new CharMap(xMax, yMax, '.');
        for (Map.Entry<Point2D, Integer> entry : positions.entrySet()) {
            map.set(entry.getKey(), (char) (entry.getValue() + '0'));
        }

        LOGGER.info("\n{}", map);
    }

    private static Map<Point2D, Set<Point2D>> findRegions(List<Robot> robots) {
        Set<Point2D> points = robots.stream().map(Robot::p).collect(Collectors.toSet());
        Map<Point2D, List<Point2D>> graph = new HashMap<>();
        for (Point2D point : points) {
            List<Point2D> neighbors = Arrays.stream(Direction.values()).map(point::move).filter(points::contains).toList();
            graph.put(point, neighbors);
        }

        return Connectivity.findRegion(points, graph);
    }

    record Robot(Point2D p, Point2D v) {
        Robot move(int xMax, int yMax) {
            Point2D move = p.moveLoop(v, xMax, yMax);
            return new Robot(move, v);
        }
    }
}
