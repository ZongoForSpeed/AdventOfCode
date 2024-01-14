package com.adventofcode.year2018;

import com.adventofcode.common.point.Point3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day23 {

    private Day23() {
        // No-Op
    }

    private static final List<Point3D> DIRECTIONS = List.of(
            Point3D.of(0, 0, 0),
            Point3D.of(0, 0, 1),
            Point3D.of(0, 1, 0),
            Point3D.of(0, 1, 1),
            Point3D.of(1, 0, 0),
            Point3D.of(1, 0, 1),
            Point3D.of(1, 1, 0),
            Point3D.of(1, 1, 1)
    );
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23.class);
    private static final Pattern NANOBOTS_PATTERN = Pattern.compile("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(-?\\d+)");

    private static List<Nanobot> readNanobots(Scanner scanner) {
        List<Nanobot> bots = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = NANOBOTS_PATTERN.matcher(line);
            if (matcher.matches()) {
                int x = Integer.parseInt(matcher.group(1));
                int y = Integer.parseInt(matcher.group(2));
                int z = Integer.parseInt(matcher.group(3));
                int r = Integer.parseInt(matcher.group(4));
                bots.add(new Nanobot(Point3D.of(x, y, z), r));
            }
        }

        LOGGER.info("Bots = {}", bots);
        return bots;
    }

    enum Coordinates {
        X {
            @Override
            long apply(Point3D p) {
                return p.x();
            }
        },
        Y {
            @Override
            long apply(Point3D p) {
                return p.y();
            }
        },
        Z {
            @Override
            long apply(Point3D p) {
                return p.z();
            }
        };

        abstract long apply(Point3D p);
    }

    /**
     * --- Day 23: Experimental Emergency Teleportation ---
     *
     * Using your torch to search the darkness of the rocky cavern, you finally
     * locate the man's friend: a small reindeer.
     *
     * You're not sure how it got so far in this cave. It looks sick - too sick to
     * walk - and too heavy for you to carry all the way back. Sleighs won't be
     * invented for another 1500 years, of course.
     *
     * The only option is experimental emergency teleportation.
     *
     * You hit the "experimental emergency teleportation" button on the device and
     * push I accept the risk on no fewer than 18 different warning messages.
     * Immediately, the device deploys hundreds of tiny nanobots which fly around
     * the cavern, apparently assembling themselves into a very specific
     * formation. The device lists the X,Y,Z position (pos) for each nanobot as
     * well as its signal radius (r) on its tiny screen (your puzzle input).
     *
     * Each nanobot can transmit signals to any integer coordinate which is a
     * distance away from it less than or equal to its signal radius (as measured
     * by Manhattan distance). Coordinates a distance away of less than or equal
     * to a nanobot's signal radius are said to be in range of that nanobot.
     *
     * Before you start the teleportation process, you should determine which
     * nanobot is the strongest (that is, which has the largest signal radius) and
     * then, for that nanobot, the total number of nanobots that are in range of
     * it, including itself.
     *
     * For example, given the following nanobots:
     *
     * pos=<0,0,0>, r=4
     * pos=<1,0,0>, r=1
     * pos=<4,0,0>, r=3
     * pos=<0,2,0>, r=1
     * pos=<0,5,0>, r=3
     * pos=<0,0,3>, r=1
     * pos=<1,1,1>, r=1
     * pos=<1,1,2>, r=1
     * pos=<1,3,1>, r=1
     *
     * The strongest nanobot is the first one (position 0,0,0) because its signal
     * radius, 4 is the largest. Using that nanobot's location and signal radius,
     * the following nanobots are in or out of range:
     *
     *   - The nanobot at 0,0,0 is distance 0 away, and so it is in range.
     *   - The nanobot at 1,0,0 is distance 1 away, and so it is in range.
     *   - The nanobot at 4,0,0 is distance 4 away, and so it is in range.
     *   - The nanobot at 0,2,0 is distance 2 away, and so it is in range.
     *   - The nanobot at 0,5,0 is distance 5 away, and so it is not in range.
     *   - The nanobot at 0,0,3 is distance 3 away, and so it is in range.
     *   - The nanobot at 1,1,1 is distance 3 away, and so it is in range.
     *   - The nanobot at 1,1,2 is distance 4 away, and so it is in range.
     *   - The nanobot at 1,3,1 is distance 5 away, and so it is not in range.
     *
     * In this example, in total, 7 nanobots are in range of the nanobot with the
     * largest signal radius.
     *
     * Find the nanobot with the largest signal radius. How many nanobots are in
     * range of its signals?
     */
    public static final class PartOne {
        private PartOne() {
            // No-Op
        }

        public static long countNanobots(Scanner scanner) {
            List<Nanobot> bots = readNanobots(scanner);

            long best = 0;
            for (Nanobot bot : bots) {
                long count = bots.stream().filter(bot::inRadius).count();
                if (count > best) {
                    best = count;
                }
            }
            return best;
        }
    }

    /**
     * --- Part Two ---
     *
     * Now, you just need to figure out where to position yourself so that you're
     * actually teleported when the nanobots activate.
     *
     * To increase the probability of success, you need to find the coordinate
     * which puts you in range of the largest number of nanobots. If there are
     * multiple, choose one closest to your position (0,0,0, measured by manhattan
     * distance).
     *
     * For example, given the following nanobot formation:
     *
     * pos=<10,12,12>, r=2
     * pos=<12,14,12>, r=2
     * pos=<16,12,12>, r=4
     * pos=<14,14,14>, r=6
     * pos=<50,50,50>, r=200
     * pos=<10,10,10>, r=5
     *
     * Many coordinates are in range of some of the nanobots in this formation.
     * However, only the coordinate 12,12,12 is in range of the most nanobots: it
     * is in range of the first five, but is not in range of the nanobot at
     * 10,10,10. (All other coordinates are in range of fewer than five nanobots.)
     * This coordinate's distance from 0,0,0 is 36.
     *
     * Find the coordinates that are in range of the largest number of nanobots.
     * What is the shortest manhattan distance between any of those points and
     * 0,0,0?
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        public static int bestPositionDistance(Scanner scanner) {
            List<Nanobot> nanobots = readNanobots(scanner);

            long maxCoordinate = 0;
            for (Nanobot nanobot : nanobots) {
                for (Coordinates coordinates : Coordinates.values()) {
                    maxCoordinate = Math.max(maxCoordinate, Math.abs(coordinates.apply(nanobot.position())) + nanobot.radius());
                }
            }

            LOGGER.info("maxCoordinate = {}", maxCoordinate);
            int boxSize = 1;
            while (boxSize <= maxCoordinate) {
                boxSize <<= 1;
            }

            Box initialBox = new Box(Point3D.of(-boxSize, -boxSize, -boxSize), Point3D.of(boxSize, boxSize, boxSize));

            PriorityQueue<Work> queue = new PriorityQueue<>(Comparator.comparingInt(Work::intersectCount).reversed());
            queue.add(new Work(nanobots.size(), 2 * boxSize, 3 * boxSize, initialBox));

            while (!queue.isEmpty()) {
                Work current = queue.poll();
                int count = current.intersectCount();
                int size = current.size();
                int distance = current.distance();
                Box box = current.box();

                if (size == 1) {
                    LOGGER.info("Found closest at {} dist {} ({} bots in range)", box, distance, count);
                    return distance;
                }

                int newSize = size / 2;
                for (Point3D direction : DIRECTIONS) {
                    Point3D newBox0 = Point3D.of(
                            box.a.x() + newSize * direction.x(),
                            box.a.y() + newSize * direction.y(),
                            box.a.z() + newSize * direction.z()
                    );
                    Point3D newBox1 = Point3D.of(
                            newBox0.x() + newSize,
                            newBox0.y() + newSize,
                            newBox0.z() + newSize
                    );
                    Box newbox = new Box(newBox0, newBox1);
                    int newCount = newbox.intersectCount(nanobots);
                    LOGGER.trace("newbox = {}, newCount = {}", newbox, newCount);
                    queue.add(new Work(newCount, newSize, Point3D.manhattanDistance(newBox0, Point3D.ORIGIN), newbox));
                }
            }

            throw new IllegalStateException("Cannot find best position");
        }

        record Work(int intersectCount, int size, int distance, Box box) {
        }
    }

    record Nanobot(Point3D position, long radius) {
        boolean inRadius(Nanobot bot) {
            return Point3D.manhattanDistance(position, bot.position) <= radius;
        }

        int x() {
            return position.x();
        }

        int y() {
            return position.y();
        }

        int z() {
            return position.z();
        }
    }

    record Box(Point3D a, Point3D b) {
        boolean intersect(Nanobot bot) {
            long d = 0;
            for (Coordinates coordinates : Coordinates.values()) {
                long low = coordinates.apply(a);
                long high = coordinates.apply(b) - 1;
                d += Math.abs(coordinates.apply(bot.position) - low)
                     + Math.abs(coordinates.apply(bot.position) - high);
                d -= (high - low);
            }
            d /= 2;
            return d <= bot.radius;
        }

        int intersectCount(List<Nanobot> nanobots) {
            return (int) nanobots.stream().filter(this::intersect).count();
        }
    }
}
