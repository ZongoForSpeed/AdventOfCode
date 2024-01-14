package com.adventofcode.year2023;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public final class Day21 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day21.class);

    private Day21() {
        // No-Op
    }

    /**
     * --- Day 21: Step Counter ---
     *
     * You manage to catch the airship right as it's dropping someone else off on
     * their all-expenses-paid trip to Desert Island! It even helpfully drops you
     * off near the gardener and his massive farm.
     *
     * "You got the sand flowing again! Great work! Now we just need to wait until
     * we have enough sand to filter the water for Snow Island and we'll have snow
     * again in no time."
     *
     * While you wait, one of the Elves that works with the gardener heard how
     * good you are at solving problems and would like your help. He needs to get
     * his steps in for the day, and so he'd like to know which garden plots he
     * can reach with exactly his remaining 64 steps.
     *
     * He gives you an up-to-date map (your puzzle input) of his starting position
     * (S), garden plots (.), and rocks (#). For example:
     *
     * ...........
     * .....###.#.
     * .###.##..#.
     * ..#.#...#..
     * ....#.#....
     * .##..S####.
     * .##..#...#.
     * .......##..
     * .##.#.####.
     * .##..##.##.
     * ...........
     *
     * The Elf starts at the starting position (S) which also counts as a garden
     * plot. Then, he can take one step north, south, east, or west, but only onto
     * tiles that are garden plots. This would allow him to reach any of the tiles
     * marked O:
     *
     * ...........
     * .....###.#.
     * .###.##..#.
     * ..#.#...#..
     * ....#O#....
     * .##.OS####.
     * .##..#...#.
     * .......##..
     * .##.#.####.
     * .##..##.##.
     * ...........
     *
     * Then, he takes a second step. Since at this point he could be at either
     * tile marked O, his second step would allow him to reach any garden plot
     * that is one step north, south, east, or west of any tile that he could have
     * reached after the first step:
     *
     * ...........
     * .....###.#.
     * .###.##..#.
     * ..#.#O..#..
     * ....#.#....
     * .##O.O####.
     * .##.O#...#.
     * .......##..
     * .##.#.####.
     * .##..##.##.
     * ...........
     *
     * After two steps, he could be at any of the tiles marked O above, including
     * the starting position (either by going north-then-south or by going west-
     * then-east).
     *
     * A single third step leads to even more possibilities:
     *
     * ...........
     * .....###.#.
     * .###.##..#.
     * ..#.#.O.#..
     * ...O#O#....
     * .##.OS####.
     * .##O.#...#.
     * ....O..##..
     * .##.#.####.
     * .##..##.##.
     * ...........
     *
     * He will continue like this until his steps for the day have been exhausted.
     * After a total of 6 steps, he could reach any of the garden plots marked O:
     *
     * ...........
     * .....###.#.
     * .###.##.O#.
     * .O#O#O.O#..
     * O.O.#.#.O..
     * .##O.O####.
     * .##.O#O..#.
     * .O.O.O.##..
     * .##.#.####.
     * .##O.##.##.
     * ...........
     *
     * In this example, if the Elf's goal was to get exactly 6 more steps today,
     * he could use them to reach any of 16 garden plots.
     *
     * However, the Elf actually needs to get 64 steps today, and the map he's
     * handed you is much larger than the example map.
     *
     * Starting from the garden plot marked S on your map, how many garden plots
     * could the Elf reach in exactly 64 steps?
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static List<Point2D> stepCounter(Scanner scanner, int steps) {
            Input input = readInput(scanner);

            Map<Point2D, List<Point2D>> neighbours = new HashMap<>();
            for (Point2D position : input.gardenPlots()) {
                List<Point2D> list = Arrays.stream(Direction.values()).map(position::move).filter(input.gardenPlots()::contains).toList();
                neighbours.put(position, list);
            }

            List<Point2D> current = List.of(input.start());
            for (int step = 1; step <= steps; ++step) {
                List<Point2D> next = current.stream()
                        .map(neighbours::get)
                        .flatMap(Collection::stream)
                        .distinct()
                        .toList();
                LOGGER.trace("next = {}", next);
                current = next;
            }
            return current;
        }

    }

    /**
     * --- Part Two ---
     *
     * The Elf seems confused by your answer until he realizes his mistake: he was
     * reading from a list of his favorite numbers that are both perfect squares
     * and perfect cubes, not his step counter.
     *
     * The actual number of steps he needs to get today is exactly 26501365.
     *
     * He also points out that the garden plots and rocks are set up so that the
     * map repeats infinitely in every direction.
     *
     * So, if you were to look one additional map-width or map-height out from the
     * edge of the example map above, you would find that it keeps repeating:
     *
     * .................................
     * .....###.#......###.#......###.#.
     * .###.##..#..###.##..#..###.##..#.
     * ..#.#...#....#.#...#....#.#...#..
     * ....#.#........#.#........#.#....
     * .##...####..##...####..##...####.
     * .##..#...#..##..#...#..##..#...#.
     * .......##.........##.........##..
     * .##.#.####..##.#.####..##.#.####.
     * .##..##.##..##..##.##..##..##.##.
     * .................................
     * .................................
     * .....###.#......###.#......###.#.
     * .###.##..#..###.##..#..###.##..#.
     * ..#.#...#....#.#...#....#.#...#..
     * ....#.#........#.#........#.#....
     * .##...####..##..S####..##...####.
     * .##..#...#..##..#...#..##..#...#.
     * .......##.........##.........##..
     * .##.#.####..##.#.####..##.#.####.
     * .##..##.##..##..##.##..##..##.##.
     * .................................
     * .................................
     * .....###.#......###.#......###.#.
     * .###.##..#..###.##..#..###.##..#.
     * ..#.#...#....#.#...#....#.#...#..
     * ....#.#........#.#........#.#....
     * .##...####..##...####..##...####.
     * .##..#...#..##..#...#..##..#...#.
     * .......##.........##.........##..
     * .##.#.####..##.#.####..##.#.####.
     * .##..##.##..##..##.##..##..##.##.
     * .................................
     *
     * This is just a tiny three-map-by-three-map slice of the inexplicably-
     * infinite farm layout; garden plots and rocks repeat as far as you can see.
     * The Elf still starts on the one middle tile marked S, though - every other
     * repeated S is replaced with a normal garden plot (.).
     *
     * Here are the number of reachable garden plots in this new infinite version
     * of the example map for different numbers of steps:
     *
     *     In exactly 6 steps, he can still reach 16 garden plots.
     *     In exactly 10 steps, he can reach any of 50 garden plots.
     *     In exactly 50 steps, he can reach 1594 garden plots.
     *     In exactly 100 steps, he can reach 6536 garden plots.
     *     In exactly 500 steps, he can reach 167004 garden plots.
     *     In exactly 1000 steps, he can reach 668697 garden plots.
     *     In exactly 5000 steps, he can reach 16733044 garden plots.
     *
     * However, the step count the Elf needs is much larger! Starting from the
     * garden plot marked S on your infinite map, how many garden plots could the
     * Elf reach in exactly 26501365 steps?
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        private static int modulo(int a, int b) {
            int m = a % b;
            if (a < 0 && b < 0) {
                m -= b;
            }
            if (a < 0 && b > 0) {
                m += b;
            }
            return m % b;
        }

        private static Point2D modulo(Point2D position, int xMax, int yMax) {
            return Point2D.of(modulo(position.x(), xMax), modulo(position.y(), yMax));
        }

        public static long infiniteStepCounter(Scanner scanner, int steps) {
            Input input = readInput(scanner);

            Set<Point2D> gardenPlots = input.gardenPlots();

            int xMax = gardenPlots.stream().mapToInt(Point2D::x).max().orElseThrow() + 1;
            int yMax = gardenPlots.stream().mapToInt(Point2D::y).max().orElseThrow() + 1;

            if (xMax != yMax) {
                throw new IllegalStateException();
            }

            int half = xMax / 2;

            Set<Point2D> current = Set.of(input.start());
            IntList cycleSize = new IntArrayList();
            for (int s = 0; s < 3 * xMax; ++s) {
                if (s % xMax == half) {
                    cycleSize.add(current.size());
                }

                Set<Point2D> next = new HashSet<>();
                for (Point2D p : current) {
                    for (Direction direction : Direction.values()) {
                        Point2D move = p.move(direction);
                        Point2D modulo = modulo(move, xMax, yMax);
                        if (gardenPlots.contains(modulo)) {
                            next.add(move);
                        }
                    }
                }

                current = next;
            }

            LOGGER.info("cycleSize {}", cycleSize);
            long a = cycleSize.getInt(0);
            long b = cycleSize.getInt(1);
            long c = cycleSize.getInt(2);
            long n = steps / yMax;
            return a + n * (b - a + (n - 1) * (c - b - b + a) / 2);
        }
    }

    private static Input readInput(Scanner scanner) {
        CharMap charMap = CharMap.read(scanner, ignore -> true);
        Point2D start = charMap.entries().stream().filter(p -> p.rightChar() == 'S').map(Pair::left).findAny().orElseThrow();
        Set<Point2D> gardenPlots = new HashSet<>();
        for (ObjectCharPair<Point2D> entry : charMap.entries()) {
            if (entry.rightChar() == '.' || entry.rightChar() == 'S') {
                gardenPlots.add(entry.left());
            }
        }
        return new Input(start, gardenPlots);
    }


    private record Input(Point2D start, Set<Point2D> gardenPlots) {
    }
}
