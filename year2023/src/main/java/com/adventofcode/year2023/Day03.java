package com.adventofcode.year2023;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public final class Day03 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day03.class);

    private Day03() {
        // No-Op
    }

    /**
     * --- Day 3: Gear Ratios ---
     *
     * You and the Elf eventually reach a gondola lift station; he says the
     * gondola lift will take you up to the water source, but this is as far as he
     * can bring you. You go inside.
     *
     * It doesn't take long to find the gondolas, but there seems to be a problem:
     * they're not moving.
     *
     * "Aaah!"
     *
     * You turn around to see a slightly-greasy Elf with a wrench and a look of
     * surprise. "Sorry, I wasn't expecting anyone! The gondola lift isn't working
     * right now; it'll still be a while before I can fix it." You offer to help.
     *
     * The engineer explains that an engine part seems to be missing from the
     * engine, but nobody can figure out which one. If you can add up all the part
     * numbers in the engine schematic, it should be easy to work out which part
     * is missing.
     *
     * The engine schematic (your puzzle input) consists of a visual
     * representation of the engine. There are lots of numbers and symbols you
     * don't really understand, but apparently any number adjacent to a symbol,
     * even diagonally, is a "part number" and should be included in your sum.
     * (Periods (.) do not count as a symbol.)
     *
     * Here is an example engine schematic:
     *
     * 467..114..
     * ...*......
     * ..35..633.
     * ......#...
     * 617*......
     * .....+.58.
     * ..592.....
     * ......755.
     * ...$.*....
     * .664.598..
     *
     * In this schematic, two numbers are not part numbers because they are not
     * adjacent to a symbol: 114 (top right) and 58 (middle right). Every other
     * number is adjacent to a symbol and so is a part number; their sum is 4361.
     *
     * Of course, the actual engine schematic is much larger. What is the sum of
     * all of the part numbers in the engine schematic?
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static int gearRatios(Scanner scanner) {
            CharMap map = CharMap.read(scanner, c -> c != '.');
            Set<Point2D> points = new HashSet<>(map.points());
            int xMax = points.stream().mapToInt(Point2D::x).max().orElse(0);
            int yMax = points.stream().mapToInt(Point2D::y).max().orElse(0);

            LOGGER.trace("Map :\n{}", map);

            List<Point2D> symbols = map.entries()
                    .stream()
                    .filter(e -> !Character.isDigit(e.rightChar()))
                    .map(it.unimi.dsi.fastutil.Pair::left)
                    .toList();

            LOGGER.info("symbols = {}", symbols);

            CharMap digitsMap = new CharMap(xMax, yMax, ' ');

            Deque<Point2D> queue = new ArrayDeque<>(symbols);
            Set<Point2D> visited = new HashSet<>(symbols);

            while (!queue.isEmpty()) {
                Point2D first = queue.pollFirst();
                char c = map.get(first);
                if (Character.isDigit(c)) {
                    digitsMap.set(first, c);
                    Point2D move1 = first.move(Direction.RIGHT);
                    if (points.contains(move1) && visited.add(move1)) {
                        queue.addLast(move1);
                    }
                    Point2D move2 = first.move(Direction.LEFT);
                    if (points.contains(move2) && visited.add(move2)) {
                        queue.addLast(move2);
                    }
                } else {
                    List<Point2D> adjacents = Point2D.ADJACENT.stream()
                            .map(first::move)
                            .filter(points::contains)
                            .toList();
                    for (Point2D adjacent : adjacents) {
                        if (visited.add(adjacent)) {
                            queue.addLast(adjacent);
                        }
                    }
                }
            }

            int sum = 0;

            LOGGER.trace("digitsMap :\n{}", digitsMap);
            try (Scanner digitsScanner = new Scanner(digitsMap.toString())) {
                while (digitsScanner.hasNextInt()) {
                    sum += digitsScanner.nextInt();
                }
            }
            return sum;
        }
    }

    /**
     * --- Part Two ---
     *
     * The engineer finds the missing part and installs it in the engine! As the
     * engine springs to life, you jump in the closest gondola, finally ready to
     * ascend to the water source.
     *
     * You don't seem to be going very fast, though. Maybe something is still
     * wrong? Fortunately, the gondola has a phone labeled "help", so you pick it
     * up and the engineer answers.
     *
     * Before you can explain the situation, she suggests that you look out the
     * window. There stands the engineer, holding a phone in one hand and waving
     * with the other. You're going so slowly that you haven't even left the
     * station. You exit the gondola.
     *
     * The missing part wasn't the only issue - one of the gears in the engine is
     * wrong. A gear is any * symbol that is adjacent to exactly two part numbers.
     * Its gear ratio is the result of multiplying those two numbers together.
     *
     * This time, you need to find the gear ratio of every gear and add them all
     * up so that the engineer can figure out which gear needs to be replaced.
     *
     * Consider the same engine schematic again:
     *
     * 467..114..
     * ...*......
     * ..35..633.
     * ......#...
     * 617*......
     * .....+.58.
     * ..592.....
     * ......755.
     * ...$.*....
     * .664.598..
     *
     * In this schematic, there are two gears. The first is in the top left; it
     * has part numbers 467 and 35, so its gear ratio is 16345. The second gear is
     * in the lower right; its gear ratio is 451490. (The * adjacent to 617 is not
     * a gear because it is only adjacent to one part number.) Adding up all of
     * the gear ratios produces 467835.
     *
     * What is the sum of all of the gear ratios in your engine schematic?
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        private static long gearRatio(CharMap map, Set<Point2D> points, Point2D start, int xMax, int yMax) {
            List<Point2D> adjacents = Point2D.ADJACENT.stream().map(start::move).filter(points::contains).toList();

            Deque<Point2D> queue = new ArrayDeque<>(adjacents);
            Set<Point2D> visited = new HashSet<>(adjacents);

            CharMap digitsMap = new CharMap(xMax, yMax, ' ');

            while (!queue.isEmpty()) {
                Point2D first = queue.pollFirst();
                char c = map.get(first);
                if (Character.isDigit(c)) {
                    digitsMap.set(first, c);
                    Point2D move1 = first.move(Direction.RIGHT);
                    if (points.contains(move1) && visited.add(move1)) {
                        queue.addLast(move1);
                    }
                    Point2D move2 = first.move(Direction.LEFT);
                    if (points.contains(move2) && visited.add(move2)) {
                        queue.addLast(move2);
                    }
                }
            }

            long ratio = 1;
            int count = 0;

            LOGGER.trace("digitsMap :\n{}", digitsMap);

            try (Scanner digitsScanner = new Scanner(digitsMap.toString())) {
                while (digitsScanner.hasNextInt()) {
                    int value = digitsScanner.nextInt();
                    LOGGER.trace("value = {}", value);
                    ratio *= value;
                    count++;
                }
            }
            if (count != 2) {
                return 0;
            }
            return ratio;

        }

        public static long gearRatios(Scanner scanner) {
            CharMap map = CharMap.read(scanner, c -> c != '.');
            Set<Point2D> points = new HashSet<>(map.points());
            int xMax = points.stream().mapToInt(Point2D::x).max().orElse(0);
            int yMax = points.stream().mapToInt(Point2D::y).max().orElse(0);

            LOGGER.trace("Map :\n{}", map);

            List<Point2D> symbols = map.entries()
                    .stream()
                    .filter(e -> e.rightChar() == '*')
                    .map(Pair::left)
                    .toList();

            LOGGER.debug("symbols = {}", symbols);

            long sum = 0;

            for (Point2D symbol : symbols) {
                sum += gearRatio(map, points, symbol, xMax, yMax);
            }

            return sum;
        }
    }
}
