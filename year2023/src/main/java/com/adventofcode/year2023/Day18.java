package com.adventofcode.year2023;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import org.apache.commons.lang3.function.TriFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day18 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day18.class);
    private static final Pattern PATTERN_DIG_PLAN = Pattern.compile("(\\w) (\\d+) \\(#([0-9a-f]+)\\)");

    private Day18() {
        // No-Op
    }

    /**
     * --- Day 18: Lavaduct Lagoon ---
     *
     * Thanks to your efforts, the machine parts factory is one of the first
     * factories up and running since the lavafall came back. However, to catch up
     * with the large backlog of parts requests, the factory will also need a
     * large supply of lava for a while; the Elves have already started creating a
     * large lagoon nearby for this purpose.
     *
     * However, they aren't sure the lagoon will be big enough; they've asked you
     * to take a look at the dig plan (your puzzle input). For example:
     *
     * R 6 (#70c710)
     * D 5 (#0dc571)
     * L 2 (#5713f0)
     * D 2 (#d2c081)
     * R 2 (#59c680)
     * D 2 (#411b91)
     * L 5 (#8ceee2)
     * U 2 (#caa173)
     * L 1 (#1b58a2)
     * U 2 (#caa171)
     * R 2 (#7807d2)
     * U 3 (#a77fa3)
     * L 2 (#015232)
     * U 2 (#7a21e3)
     *
     * The digger starts in a 1 meter cube hole in the ground. They then dig the
     * specified number of meters up (U), down (D), left (L), or right (R),
     * clearing full 1 meter cubes as they go. The directions are given as seen
     * from above, so if "up" were north, then "right" would be east, and so on.
     * Each trench is also listed with the color that the edge of the trench
     * should be painted as an RGB hexadecimal color code.
     *
     * When viewed from above, the above example dig plan would result in the
     * following loop of trench (#) having been dug out from otherwise ground-level
     * terrain (.):
     *
     * #######
     * #.....#
     * ###...#
     * ..#...#
     * ..#...#
     * ###.###
     * #...#..
     * ##..###
     * .#....#
     * .######
     *
     * At this point, the trench could contain 38 cubic meters of lava. However,
     * this is just the edge of the lagoon; the next step is to dig out the
     * interior so that it is one meter deep as well:
     *
     * #######
     * #######
     * #######
     * ..#####
     * ..#####
     * #######
     * #####..
     * #######
     * .######
     * .######
     *
     * Now, the lagoon can contain a much more respectable 62 cubic meters of
     * lava. While the interior is dug out, the edges are also painted according
     * to the color codes in the dig plan.
     *
     * The Elves are concerned the lagoon won't be large enough; if they follow
     * their dig plan, how many cubic meters of lava could it hold?
     */
    public static final class PartOne {
        private PartOne() {
            // No-Op
        }

        public static long readDigPlan(Scanner scanner) {
            List<DiggerCommand> commandList = readDiggerCommands(scanner, (d, m, _) -> DiggerCommand.of(d, m));
            return findArea(commandList);
        }
    }

    /**
     * --- Part Two ---
     *
     * The Elves were right to be concerned; the planned lagoon would be much too
     * small.
     *
     * After a few minutes, someone realizes what happened; someone swapped the
     * color and instruction parameters when producing the dig plan. They don't
     * have time to fix the bug; one of them asks if you can extract the correct
     * instructions from the hexadecimal codes.
     *
     * Each hexadecimal code is six hexadecimal digits long. The first five
     * hexadecimal digits encode the distance in meters as a five-digit
     * hexadecimal number. The last hexadecimal digit encodes the direction to
     * dig: 0 means R, 1 means D, 2 means L, and 3 means U.
     *
     * So, in the above example, the hexadecimal codes can be converted into the
     * true instructions:
     *
     *     #70c710 = R 461937
     *     #0dc571 = D 56407
     *     #5713f0 = R 356671
     *     #d2c081 = D 863240
     *     #59c680 = R 367720
     *     #411b91 = D 266681
     *     #8ceee2 = L 577262
     *     #caa173 = U 829975
     *     #1b58a2 = L 112010
     *     #caa171 = D 829975
     *     #7807d2 = L 491645
     *     #a77fa3 = U 686074
     *     #015232 = L 5411
     *     #7a21e3 = U 500254
     *
     * Digging out this loop and its interior produces a lagoon that can hold an
     * impressive 952408144115 cubic meters of lava.
     *
     * Convert the hexadecimal color codes into the correct instructions; if the
     * Elves follow this new dig plan, how many cubic meters of lava could the
     * lagoon hold?
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        public static long readDigPlan(Scanner scanner) {
            List<DiggerCommand> commandList = readDiggerCommands(scanner, (_, _, hex) -> DiggerCommand.of(hex));
            return findArea(commandList);
        }
    }


    private static long findArea(List<DiggerCommand> commandList) {
        Point2D current = Point2D.of(0, 0);

        List<Point2D> vertices = new ArrayList<>();
        vertices.add(Point2D.of(0, 0));
        for (DiggerCommand command : commandList) {
            current = current.move(command.direction(), command.move());
            vertices.add(current);
        }

        LOGGER.trace("vertices = {}", vertices);
        return area(vertices);
    }

    private static long area(List<Point2D> vertices) {
        long area = 0;
        long peri = 0;
        for (int i = 1; i < vertices.size(); i++) {
            Point2D p1 = vertices.get(i - 1);
            Point2D p2 = vertices.get(i);
            area += ((long) p1.y() + p2.y()) * ((long) p1.x() - p2.x());
            peri += Math.abs(p1.y() - p2.y()) + Math.abs(p1.x() - p2.x());
        }

        Point2D p1 = vertices.getFirst();
        Point2D pn = vertices.getLast();

        area += ((long) pn.y() + p1.y()) * ((long) pn.x() - p1.x());
        peri += Math.abs(pn.y() - p1.y()) + Math.abs(pn.x() - p1.x());

        return (area + peri) / 2 + 1;
    }

    private static List<DiggerCommand> readDiggerCommands(Scanner scanner, TriFunction<String, Integer, String, DiggerCommand> triFunction) {
        List<DiggerCommand> commandList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN_DIG_PLAN.matcher(line);
            if (matcher.matches()) {
                String d = matcher.group(1);
                int m = Integer.parseInt(matcher.group(2));
                String hex = matcher.group(3);
                LOGGER.trace("d={}, m={}, hex={}", d, m, hex);
                commandList.add(triFunction.apply(d, m, hex));
            } else {
                throw new IllegalStateException("Cannot read line: " + line);
            }
        }

        LOGGER.info("commandList = {}", commandList);
        return commandList;
    }


    record DiggerCommand(Direction direction, int move) {
        public static DiggerCommand of(String d, int move) {
            Direction direction = switch (d) {
                case "U" -> Direction.UP;
                case "D" -> Direction.DOWN;
                case "L" -> Direction.LEFT;
                case "R" -> Direction.RIGHT;
                default -> throw new IllegalStateException();
            };
            return new DiggerCommand(direction, move);
        }

        public static DiggerCommand of(String hex) {
            int code = Integer.parseInt(hex, 16);
            Direction direction = switch (code % 16) {
                case 0 -> Direction.RIGHT;
                case 1 -> Direction.DOWN;
                case 2 -> Direction.LEFT;
                case 3 -> Direction.UP;
                default -> throw new IllegalStateException();
            };
            return new DiggerCommand(direction, code / 16);
        }
    }

}
