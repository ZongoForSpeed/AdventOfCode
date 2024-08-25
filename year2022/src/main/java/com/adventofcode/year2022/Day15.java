package com.adventofcode.year2022;

import com.adventofcode.common.point.Point2D;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day15 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day15.class);
    private static final Pattern SENSOR_PATTERN = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

    private Day15() {
        // No-Op
    }

    /**
     * --- Day 15: Beacon Exclusion Zone ---
     *
     * You feel the ground rumble again as the distress signal leads you to a
     * large network of subterranean tunnels. You don't have time to search them
     * all, but you don't need to: your pack contains a set of deployable sensors
     * that you imagine were originally built to locate lost Elves.
     *
     * The sensors aren't very powerful, but that's okay; your handheld device
     * indicates that you're close enough to the source of the distress signal to
     * use them. You pull the emergency sensor system out of your pack, hit the
     * big button on top, and the sensors zoom off down the tunnels.
     *
     * Once a sensor finds a spot it thinks will give it a good reading, it
     * attaches itself to a hard surface and begins monitoring for the nearest
     * signal source beacon. Sensors and beacons always exist at integer
     * coordinates. Each sensor knows its own position and can determine the
     * position of a beacon precisely; however, sensors can only lock on to the
     * one beacon closest to the sensor as measured by the Manhattan distance.
     * (There is never a tie where two beacons are the same distance to a sensor.)
     *
     * It doesn't take long for the sensors to report back their positions and
     * closest beacons (your puzzle input). For example:
     *
     * Sensor at x=2, y=18: closest beacon is at x=-2, y=15
     * Sensor at x=9, y=16: closest beacon is at x=10, y=16
     * Sensor at x=13, y=2: closest beacon is at x=15, y=3
     * Sensor at x=12, y=14: closest beacon is at x=10, y=16
     * Sensor at x=10, y=20: closest beacon is at x=10, y=16
     * Sensor at x=14, y=17: closest beacon is at x=10, y=16
     * Sensor at x=8, y=7: closest beacon is at x=2, y=10
     * Sensor at x=2, y=0: closest beacon is at x=2, y=10
     * Sensor at x=0, y=11: closest beacon is at x=2, y=10
     * Sensor at x=20, y=14: closest beacon is at x=25, y=17
     * Sensor at x=17, y=20: closest beacon is at x=21, y=22
     * Sensor at x=16, y=7: closest beacon is at x=15, y=3
     * Sensor at x=14, y=3: closest beacon is at x=15, y=3
     * Sensor at x=20, y=1: closest beacon is at x=15, y=3
     *
     * So, consider the sensor at 2,18; the closest beacon to it is at -2,15. For
     * the sensor at 9,16, the closest beacon to it is at 10,16.
     *
     * Drawing sensors as S and beacons as B, the above arrangement of sensors and
     * beacons looks like this:
     *
     *                1    1    2    2
     *      0    5    0    5    0    5
     *  0 ....S.......................
     *  1 ......................S.....
     *  2 ...............S............
     *  3 ................SB..........
     *  4 ............................
     *  5 ............................
     *  6 ............................
     *  7 ..........S.......S.........
     *  8 ............................
     *  9 ............................
     * 10 ....B.......................
     * 11 ..S.........................
     * 12 ............................
     * 13 ............................
     * 14 ..............S.......S.....
     * 15 B...........................
     * 16 ...........SB...............
     * 17 ................S..........B
     * 18 ....S.......................
     * 19 ............................
     * 20 ............S......S........
     * 21 ............................
     * 22 .......................B....
     *
     * This isn't necessarily a comprehensive map of all beacons in the area,
     * though. Because each sensor only identifies its closest beacon, if a sensor
     * detects a beacon, you know there are no other beacons that close or closer
     * to that sensor. There could still be beacons that just happen to not be the
     * closest beacon to any sensor. Consider the sensor at 8,7:
     *
     *                1    1    2    2
     *      0    5    0    5    0    5
     * -2 ..........#.................
     * -1 .........###................
     *  0 ....S...#####...............
     *  1 .......#######........S.....
     *  2 ......#########S............
     *  3 .....###########SB..........
     *  4 ....#############...........
     *  5 ...###############..........
     *  6 ..#################.........
     *  7 .#########S#######S#........
     *  8 ..#################.........
     *  9 ...###############..........
     * 10 ....B############...........
     * 11 ..S..###########............
     * 12 ......#########.............
     * 13 .......#######..............
     * 14 ........#####.S.......S.....
     * 15 B........###................
     * 16 ..........#SB...............
     * 17 ................S..........B
     * 18 ....S.......................
     * 19 ............................
     * 20 ............S......S........
     * 21 ............................
     * 22 .......................B....
     *
     * This sensor's closest beacon is at 2,10, and so you know there are no
     * beacons that close or closer (in any positions marked #).
     *
     * None of the detected beacons seem to be producing the distress signal, so
     * you'll need to work out where the distress beacon is by working out where
     * it isn't. For now, keep things simple by counting the positions where a
     * beacon cannot possibly be along just a single row.
     *
     * So, suppose you have an arrangement of beacons and sensors like in the
     * example above and, just in the row where y=10, you'd like to count the
     * number of positions a beacon cannot possibly exist. The coverage from all
     * sensors near that row looks like this:
     *
     *                  1    1    2    2
     *        0    5    0    5    0    5
     *  9 ...#########################...
     * 10 ..####B######################..
     * 11 .###S#############.###########.
     *
     * In this example, in the row where y=10, there are 26 positions where a
     * beacon cannot be present.
     *
     * Consult the report from the sensors you just deployed. In the row where
     * y=2000000, how many positions cannot contain a beacon?
     */
    public static int partOne(Scanner scanner, int y) {

        Network network = readInput(scanner);

        IntList xPositions = new IntArrayList();
        for (Sensor sensor : network.sensors()) {
            xPositions.add(sensor.position.x() + sensor.radius());
            xPositions.add(sensor.position.x() - sensor.radius());
        }

        xPositions.sort(IntComparators.NATURAL_COMPARATOR);
        Integer first = xPositions.getFirst();
        Integer last = xPositions.getLast();
        int count = 0;
        for (int x = first; x <= last; x++) {
            Point2D point = Point2D.of(x, y);
            if (!network.beacons().contains(point) && network.sensors().stream().anyMatch(s -> s.inside(point))) {
                count++;
            }
        }

        return count;
    }

    /**
     * --- Part Two ---
     *
     * Your handheld device indicates that the distress signal is coming from a
     * beacon nearby. The distress beacon is not detected by any sensor, but the
     * distress beacon must have x and y coordinates each no lower than 0 and no
     * larger than 4000000.
     *
     * To isolate the distress beacon's signal, you need to determine its tuning
     * frequency, which can be found by multiplying its x coordinate by 4000000
     * and then adding its y coordinate.
     *
     * In the example above, the search space is smaller: instead, the x and y
     * coordinates can each be at most 20. With this reduced search area, there is
     * only a single position that could have a beacon: x=14, y=11. The tuning
     * frequency for this distress beacon is 56000011.
     *
     * Find the only possible position for the distress beacon. What is its tuning
     * frequency?
     */
    public static long partTwo(Scanner scanner, int coordinate) {
        Network network = readInput(scanner);

        List<Sensor> sensors = network.sensors();
        Point2D first = sensors.stream().map(Sensor::border).flatMap(Collection::stream)
                .parallel()
                .filter(p -> p.x() >= 0)
                .filter(p -> p.y() >= 0)
                .filter(p -> p.x() <= coordinate)
                .filter(p -> p.y() <= coordinate)
                .filter(p -> sensors.stream().noneMatch(s -> s.inside(p)))
                .findFirst()
                .orElseThrow();

        LOGGER.info("{}", first);

        return first.x() * 4000000L + first.y();
    }

    private static Network readInput(Scanner scanner) {
        List<Sensor> sensors = new ArrayList<>();
        Set<Point2D> beacons = new HashSet<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = SENSOR_PATTERN.matcher(line);
            if (matcher.matches()) {
                int xSensor = Integer.parseInt(matcher.group(1));
                int ySensor = Integer.parseInt(matcher.group(2));
                int xBeacon = Integer.parseInt(matcher.group(3));
                int yBeacon = Integer.parseInt(matcher.group(4));

                LOGGER.info("Sensor = {}, {} Beacon = {}, {}", xSensor, ySensor, xBeacon, yBeacon);
                Point2D position = Point2D.of(xSensor, ySensor);
                Point2D beacon = Point2D.of(xBeacon, yBeacon);

                beacons.add(beacon);

                int radius = Point2D.manhattanDistance(position, beacon);
                Sensor sensor = new Sensor(position, radius);

                LOGGER.info("Sensor = {}", sensor);
                sensors.add(sensor);
            } else {
                LOGGER.error("Cannot read line: {}", line);
            }
        }
        return new Network(sensors, beacons);
    }

    private record Network(List<Sensor> sensors, Set<Point2D> beacons) {
    }

    private record Sensor(Point2D position, int radius) {
        boolean inside(Point2D p) {
            return Point2D.manhattanDistance(p, position) <= radius;
        }

        List<Point2D> border() {
            Set<Point2D> result = new HashSet<>();
            for (int x = 0; x <= radius + 1; ++x) {
                result.add(Point2D.of(position.x() - radius - 1 + x, position.y() + x));
                result.add(Point2D.of(position.x() - radius - 1 + x, position.y() - x));
                result.add(Point2D.of(position.x() + radius + 1 - x, position.y() + x));
                result.add(Point2D.of(position.x() + radius + 1 - x, position.y() - x));
            }

            return new ArrayList<>(result);
        }
    }
}
