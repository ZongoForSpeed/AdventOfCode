package com.adventofcode.year2018;

import com.adventofcode.point.Point3D;
import it.unimi.dsi.fastutil.ints.Int2IntAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day23Test {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day23Test.class);

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
    class PartOne {
        private static long countNanobots(Scanner scanner) {
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
    class PartTwo {

    }

    private static final Pattern NANOBOTS_PATTERN = Pattern.compile("pos=<(-?\\d+),(-?\\d+),(-?\\d+)>, r=(-?\\d+)");

    @Test
    void inputExample1() {
        String input = """
                pos=<0,0,0>, r=4
                pos=<1,0,0>, r=1
                pos=<4,0,0>, r=3
                pos=<0,2,0>, r=1
                pos=<0,5,0>, r=3
                pos=<0,0,3>, r=1
                pos=<1,1,1>, r=1
                pos=<1,1,2>, r=1
                pos=<1,3,1>, r=1""";

        Scanner scanner = new Scanner(input);
        long count = PartOne.countNanobots(scanner);

        Assertions.assertThat(count).isEqualTo(7);
    }

    @Test
    void inputExample2() {
        String input = """
                pos=<10,12,12>, r=2
                pos=<12,14,12>, r=2
                pos=<16,12,12>, r=4
                pos=<14,14,14>, r=6
                pos=<50,50,50>, r=200
                pos=<10,10,10>, r=5""";

        Scanner scanner = new Scanner(input);
        extracted(scanner);

        // Assertions.assertThat(mindistance).isEqualTo(36);
    }

    private static int extracted(Scanner scanner) {
        List<Nanobot> nanobots = readNanobots(scanner);
        Int2IntMap xMap = new Int2IntAVLTreeMap();
        for (Nanobot bot : nanobots) {
            int xMin = bot.x() + bot.y() + bot.z() - bot.radius();
            int xMax = bot.x() + bot.y() + bot.z() + bot.radius() + 1;

            xMap.mergeInt(xMin, 1, Integer::sum);
            xMap.mergeInt(xMax, -1, Integer::sum);
        }

        int running = 0;
        int max = 0;
        int maxStart= 0;
        int maxEnd = 0;
        for (Int2IntMap.Entry entry : xMap.int2IntEntrySet()) {
            running += entry.getIntValue();
            if (running > max) {
                max = running;
                maxStart = entry.getIntKey();
            }
        }

        LOGGER.info("xMap = {}", xMap.size());

        LOGGER.info("max = {}", max);
        LOGGER.info("maxStart = {}", maxStart);

        int finalMaxStart = maxStart;
        List<Int2IntMap.Entry> list = xMap.int2IntEntrySet().stream()
                .filter(e -> e.getIntKey() >= finalMaxStart)
                .toList();

        LOGGER.info("list = {}", list);
        return 0;
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day23Test.class.getResourceAsStream("/2018/day/23/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            long count = PartOne.countNanobots(scanner);
            Assertions.assertThat(count).isEqualTo(943);
        }
    }


    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day23Test.class.getResourceAsStream("/2018/day/23/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            extracted(scanner);
            // 84087794=>1, 84087817=>-12
            // long count = computeMindistance(scanner);
            // Assertions.assertThat(count).isEqualTo(943);
        }
    }

    record Nanobot(Point3D position, int radius) {
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
}
