package com.adventofcode.year2023;

import com.adventofcode.point.Direction;
import com.adventofcode.point.Point2D;
import com.adventofcode.point.map.CharMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public final class Day16 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day16.class);

    private Day16() {
        // No-Op
    }

    /**
     * --- Day 16: The Floor Will Be Lava ---
     *
     * With the beam of light completely focused somewhere, the reindeer leads you
     * deeper still into the Lava Production Facility. At some point, you realize
     * that the steel facility walls have been replaced with cave, and the
     * doorways are just cave, and the floor is cave, and you're pretty sure this
     * is actually just a giant cave.
     *
     * Finally, as you approach what must be the heart of the mountain, you see a
     * bright light in a cavern up ahead. There, you discover that the beam of
     * light you so carefully focused is emerging from the cavern wall closest to
     * the facility and pouring all of its energy into a contraption on the
     * opposite side.
     *
     * Upon closer inspection, the contraption appears to be a flat, two-
     * dimensional square grid containing empty space (.), mirrors (/ and \), and
     * splitters (| and -).
     *
     * The contraption is aligned so that most of the beam bounces around the
     * grid, but each tile on the grid converts some of the beam's light into heat
     * to melt the rock in the cavern.
     *
     * You note the layout of the contraption (your puzzle input). For example:
     *
     * .|...\....
     * |.-.\.....
     * .....|-...
     * ........|.
     * ..........
     * .........\
     * ..../.\\..
     * .-.-/..|..
     * .|....-|.\
     * ..//.|....
     *
     * The beam enters in the top-left corner from the left and heading to the
     * right. Then, its behavior depends on what it encounters as it moves:
     *
     *   - If the beam encounters empty space (.), it continues in the same
     *     direction.
     *   - If the beam encounters a mirror (/ or \), the beam is reflected 90
     *     degrees depending on the angle of the mirror. For instance, a
     *     rightward-moving beam that encounters a / mirror would continue upward
     *     in the mirror's column, while a rightward-moving beam that encounters
     *     a \ mirror would continue downward from the mirror's column.
     *   - If the beam encounters the pointy end of a splitter (| or -), the beam
     *     passes through the splitter as if the splitter were empty space. For
     *     instance, a rightward-moving beam that encounters a - splitter would
     *     continue in the same direction.
     *   - If the beam encounters the flat side of a splitter (| or -), the beam
     *     is split into two beams going in each of the two directions the
     *     splitter's pointy ends are pointing. For instance, a rightward-moving
     *     beam that encounters a | splitter would split into two beams: one that
     *     continues upward from the splitter's column and one that continues
     *     downward from the splitter's column.
     *
     * Beams do not interact with other beams; a tile can have many beams passing
     * through it at the same time. A tile is energized if that tile has at least
     * one beam pass through it, reflect in it, or split in it.
     *
     * In the above example, here is how the beam of light bounces around the
     * contraption:
     *
     * >|<<<\....
     * |v-.\^....
     * .v...|->>>
     * .v...v^.|.
     * .v...v^...
     * .v...v^..\
     * .v../2\\..
     * <->-/vv|..
     * .|<<<2-|.\
     * .v//.|.v..
     *
     * Beams are only shown on empty tiles; arrows indicate the direction of the
     * beams. If a tile contains beams moving in multiple directions, the number
     * of distinct directions is shown instead. Here is the same diagram but
     * instead only showing whether a tile is energized (#) or not (.):
     *
     * ######....
     * .#...#....
     * .#...#####
     * .#...##...
     * .#...##...
     * .#...##...
     * .#..####..
     * ########..
     * .#######..
     * .#...#.#..
     *
     * Ultimately, in this example, 46 tiles become energized.
     *
     * The light isn't energizing enough tiles to produce lava; to debug the
     * contraption, you need to start by analyzing the current situation. With the
     * beam starting in the top-left heading right, how many tiles end up being
     * energized?
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static long countEnergizedTiles(Scanner scanner) {
            CharMap map = CharMap.read(scanner, ignore -> true);

            LOGGER.info("map:\n{}", map);

            int xMax = map.xMax();
            int yMax = map.yMax();

            Beam start = new Beam(Point2D.of(0, 0), Direction.RIGHT);

            return Day16.countEnergizedTiles(map, start, xMax, yMax);
        }
    }

    /**
     * --- Part Two ---
     *
     * As you try to work out what might be wrong, the reindeer tugs on your shirt
     * and leads you to a nearby control panel. There, a collection of buttons
     * lets you align the contraption so that the beam enters from any edge tile
     * and heading away from that edge. (You can choose either of two directions
     * for the beam if it starts on a corner; for instance, if the beam starts in
     * the bottom-right corner, it can start heading either left or upward.)
     *
     * So, the beam could start on any tile in the top row (heading downward), any
     * tile in the bottom row (heading upward), any tile in the leftmost column
     * (heading right), or any tile in the rightmost column (heading left). To
     * produce lava, you need to find the configuration that energizes as many
     * tiles as possible.
     *
     * In the above example, this can be achieved by starting the beam in the
     * fourth tile from the left in the top row:
     *
     * .|<2<\....
     * |v-v\^....
     * .v.v.|->>>
     * .v.v.v^.|.
     * .v.v.v^...
     * .v.v.v^..\
     * .v.v/2\\..
     * <-2-/vv|..
     * .|<<<2-|.\
     * .v//.|.v..
     *
     * Using this configuration, 51 tiles are energized:
     *
     * .#####....
     * .#.#.#....
     * .#.#.#####
     * .#.#.##...
     * .#.#.##...
     * .#.#.##...
     * .#.#####..
     * ########..
     * .#######..
     * .#...#.#..
     *
     * Find the initial beam configuration that energizes the largest number of
     * tiles; how many tiles are energized in that configuration?
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        public static long maxEnergizedTiles(Scanner scanner) {
            CharMap map = CharMap.read(scanner, ignore -> true);

            LOGGER.info("map:\n{}", map);

            int xMax = map.xMax();
            int yMax = map.yMax();

            long max = 0;

            for (int x = 0; x <= xMax; ++x) {
                Beam start = new Beam(Point2D.of(x, 0), Direction.DOWN);
                long count = countEnergizedTiles(map, start, xMax, yMax);
                if (count > max) {
                    max = count;
                }
            }

            for (int x = 0; x <= xMax; ++x) {
                Beam start = new Beam(Point2D.of(x, yMax), Direction.UP);
                long count = countEnergizedTiles(map, start, xMax, yMax);
                if (count > max) {
                    max = count;
                }
            }

            for (int y = 0; y <= yMax; ++y) {
                Beam start = new Beam(Point2D.of(0, y), Direction.RIGHT);
                long count = countEnergizedTiles(map, start, xMax, yMax);
                if (count > max) {
                    max = count;
                }
            }

            for (int y = 0; y <= yMax; ++y) {
                Beam start = new Beam(Point2D.of(xMax, y), Direction.LEFT);
                long count = countEnergizedTiles(map, start, xMax, yMax);
                if (count > max) {
                    max = count;
                }
            }

            return max;
        }

    }

    private static long countEnergizedTiles(CharMap map, Beam start, int xMax, int yMax) {
        Set<Beam> seen = new HashSet<>();
        Deque<Beam> queue = new ArrayDeque<>();

        seen.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            Beam current = queue.poll();

            List<Beam> beams = current.nextBeam(map, xMax, yMax);
            for (Beam beam : beams) {
                if (seen.add(beam)) {
                    queue.add(beam);
                }
            }
        }

        return seen.stream().map(Beam::position).distinct().count();
    }


    private record Beam(Point2D position, Direction direction) {
        public Beam move() {
            return new Beam(position.move(direction), direction);
        }

        public Beam turnLeft() {
            Direction left = direction.left();
            return new Beam(position.move(left), left);
        }

        public Beam turnRight() {
            Direction right = direction.right();
            return new Beam(position.move(right), right);
        }

        public List<Beam> nextBeam(CharMap map, int xMax, int yMax) {
            List<Beam> next = new ArrayList<>(2);
            char tile = map.get(position);
            switch (tile) {
                case '.':
                    next.add(move());
                    break;
                case '/':
                    switch (direction) {
                        case UP, DOWN -> next.add(turnRight());
                        case LEFT, RIGHT -> next.add(turnLeft());
                    }
                    break;
                case '\\':
                    switch (direction) {
                        case UP, DOWN -> next.add(turnLeft());
                        case LEFT, RIGHT -> next.add(turnRight());
                    }
                    break;
                case '-':
                    switch (direction) {
                        case UP, DOWN -> {
                            next.add(turnLeft());
                            next.add(turnRight());
                        }
                        case LEFT, RIGHT -> next.add(move());
                    }
                    break;
                case '|':
                    switch (direction) {
                        case UP, DOWN -> next.add(move());
                        case LEFT, RIGHT -> {
                            next.add(turnLeft());
                            next.add(turnRight());
                        }
                    }
                    break;
                default:
                    throw new IllegalStateException();
            }

            return next.stream().filter(b -> b.position.x() >= 0
                                             && b.position.y() >= 0
                                             && b.position.x() <= xMax
                                             && b.position.y() <= yMax).toList();
        }
    }
}
