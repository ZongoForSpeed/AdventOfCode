package com.adventofcode.year2022;

import com.adventofcode.common.graph.AStar;
import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;

public final class Day24 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day24.class);

    public static Basin readInput(Scanner scanner) {
        CharMap map = CharMap.read(scanner, c -> c != '.');
        LOGGER.info("map:\n{}", map);

        int xMax = map.xMax();
        int yMax = map.yMax();

        Map<Direction, Set<Point2D>> blizzard = new EnumMap<>(Direction.class);
        Set<Point2D> walls = new HashSet<>();
        for (ObjectCharPair<Point2D> entry : map.entries()) {
            Direction direction = switch (entry.rightChar()) {
                case '^' -> Direction.UP;
                case 'v' -> Direction.DOWN;
                case '<' -> Direction.LEFT;
                case '>' -> Direction.RIGHT;
                default -> null;
            };
            if (direction != null) {
                blizzard.computeIfAbsent(direction, _ -> new HashSet<>()).add(entry.left());
            }
            if (entry.rightChar() == '#') {
                walls.add(entry.left());
            }
        }

        LOGGER.info("blizzard: {}", blizzard);
        LOGGER.info("walls: {}", walls);

        return new Basin(walls, xMax, yMax, blizzard);
    }

    /**
     * --- Day 24: Blizzard Basin ---
     * <pre>
     * With everything replanted for next year (and with elephants and monkeys to
     * tend the grove), you and the Elves leave for the extraction point.
     *
     * Partway up the mountain that shields the grove is a flat, open area that
     * serves as the extraction point. It's a bit of a climb, but nothing the
     * expedition can't handle.
     *
     * At least, that would normally be true; now that the mountain is covered in
     * snow, things have become more difficult than the Elves are used to.
     *
     * As the expedition reaches a valley that must be traversed to reach the
     * extraction site, you find that strong, turbulent winds are pushing small
     * blizzards of snow and sharp ice around the valley. It's a good thing
     * everyone packed warm clothes! To make it across safely, you'll need to find
     * a way to avoid them.
     *
     * Fortunately, it's easy to see all of this from the entrance to the valley,
     * so you make a map of the valley and the blizzards (your puzzle input). For
     * example:
     *
     * #.#####
     * #.....#
     * #>....#
     * #.....#
     * #...v.#
     * #.....#
     * #####.#
     *
     * The walls of the valley are drawn as #; everything else is ground. Clear
     * ground - where there is currently no blizzard - is drawn as .. Otherwise,
     * blizzards are drawn with an arrow indicating their direction of motion: up
     * (^), down (v), left (<), or right (>).
     *
     * The above map includes two blizzards, one moving right (>) and one moving
     * down (v). In one minute, each blizzard moves one position in the direction
     * it is pointing:
     *
     * #.#####
     * #.....#
     * #.>...#
     * #.....#
     * #.....#
     * #...v.#
     * #####.#
     *
     * Due to conservation of blizzard energy, as a blizzard reaches the wall of
     * the valley, a new blizzard forms on the opposite side of the valley moving
     * in the same direction. After another minute, the bottom downward-moving
     * blizzard has been replaced with a new downward-moving blizzard at the top
     * of the valley instead:
     *
     * #.#####
     * #...v.#
     * #..>..#
     * #.....#
     * #.....#
     * #.....#
     * #####.#
     *
     * Because blizzards are made of tiny snowflakes, they pass right through each
     * other. After another minute, both blizzards temporarily occupy the same
     * position, marked 2:
     *
     * #.#####
     * #.....#
     * #...2.#
     * #.....#
     * #.....#
     * #.....#
     * #####.#
     *
     * After another minute, the situation resolves itself, giving each blizzard
     * back its personal space:
     *
     * #.#####
     * #.....#
     * #....>#
     * #...v.#
     * #.....#
     * #.....#
     * #####.#
     *
     * Finally, after yet another minute, the rightward-facing blizzard on the
     * right is replaced with a new one on the left facing the same direction:
     *
     * #.#####
     * #.....#
     * #>....#
     * #.....#
     * #...v.#
     * #.....#
     * #####.#
     *
     * This process repeats at least as long as you are observing it, but probably
     * forever.
     *
     * Here is a more complex example:
     *
     * #.######
     * #>>.<^<#
     * #.<..<<#
     * #>v.><>#
     * #<^v^^>#
     * ######.#
     *
     * Your expedition begins in the only non-wall position in the top row and
     * needs to reach the only non-wall position in the bottom row. On each
     * minute, you can move up, down, left, or right, or you can wait in place.
     * You and the blizzards act simultaneously, and you cannot share a position
     * with a blizzard.
     *
     * In the above example, the fastest way to reach your goal requires 18 steps.
     * Drawing the position of the expedition as E, one way to achieve this is:
     *
     * Initial state:
     * #E######
     * #>>.<^<#
     * #.<..<<#
     * #>v.><>#
     * #<^v^^>#
     * ######.#
     *
     * Minute 1, move down:
     * #.######
     * #E>3.<.#
     * #<..<<.#
     * #>2.22.#
     * #>v..^<#
     * ######.#
     *
     * Minute 2, move down:
     * #.######
     * #.2>2..#
     * #E^22^<#
     * #.>2.^>#
     * #.>..<.#
     * ######.#
     *
     * Minute 3, wait:
     * #.######
     * #<^<22.#
     * #E2<.2.#
     * #><2>..#
     * #..><..#
     * ######.#
     *
     * Minute 4, move up:
     * #.######
     * #E<..22#
     * #<<.<..#
     * #<2.>>.#
     * #.^22^.#
     * ######.#
     *
     * Minute 5, move right:
     * #.######
     * #2Ev.<>#
     * #<.<..<#
     * #.^>^22#
     * #.2..2.#
     * ######.#
     *
     * Minute 6, move right:
     * #.######
     * #>2E<.<#
     * #.2v^2<#
     * #>..>2>#
     * #<....>#
     * ######.#
     *
     * Minute 7, move down:
     * #.######
     * #.22^2.#
     * #<vE<2.#
     * #>>v<>.#
     * #>....<#
     * ######.#
     *
     * Minute 8, move left:
     * #.######
     * #.<>2^.#
     * #.E<<.<#
     * #.22..>#
     * #.2v^2.#
     * ######.#
     *
     * Minute 9, move up:
     * #.######
     * #<E2>>.#
     * #.<<.<.#
     * #>2>2^.#
     * #.v><^.#
     * ######.#
     *
     * Minute 10, move right:
     * #.######
     * #.2E.>2#
     * #<2v2^.#
     * #<>.>2.#
     * #..<>..#
     * ######.#
     *
     * Minute 11, wait:
     * #.######
     * #2^E^2>#
     * #<v<.^<#
     * #..2.>2#
     * #.<..>.#
     * ######.#
     *
     * Minute 12, move down:
     * #.######
     * #>>.<^<#
     * #.<E.<<#
     * #>v.><>#
     * #<^v^^>#
     * ######.#
     *
     * Minute 13, move down:
     * #.######
     * #.>3.<.#
     * #<..<<.#
     * #>2E22.#
     * #>v..^<#
     * ######.#
     *
     * Minute 14, move right:
     * #.######
     * #.2>2..#
     * #.^22^<#
     * #.>2E^>#
     * #.>..<.#
     * ######.#
     *
     * Minute 15, move right:
     * #.######
     * #<^<22.#
     * #.2<.2.#
     * #><2>E.#
     * #..><..#
     * ######.#
     *
     * Minute 16, move right:
     * #.######
     * #.<..22#
     * #<<.<..#
     * #<2.>>E#
     * #.^22^.#
     * ######.#
     *
     * Minute 17, move down:
     * #.######
     * #2.v.<>#
     * #<.<..<#
     * #.^>^22#
     * #.2..2E#
     * ######.#
     *
     * Minute 18, move down:
     * #.######
     * #>2.<.<#
     * #.2v^2<#
     * #>..>2>#
     * #<....>#
     * ######E#
     *
     * What is the fewest number of minutes required to avoid the blizzards and
     * reach the goal?
     * </pre>
     */
    public static long partOne(Scanner scanner) {
        Basin basin = readInput(scanner);

        Elf start = new Elf(Point2D.of(1, 0), 0);
        Point2D end = Point2D.of(basin.xMax() - 1, basin.yMax());

        long path = new BlizzardPath(basin, end).algorithm(start, elf -> elf.position(end));
        LOGGER.info("path: {}", path);
        return path;
    }

    private static final class BlizzardPath extends AStar<Elf> {
        private final Basin basin;
        private final Point2D objective;

        private BlizzardPath(Basin basin, Point2D objective) {
            this.basin = basin;
            this.objective = objective;
        }

        @Override
        public Iterable<Move<Elf>> next(Elf node) {
            Map<Direction, Set<Point2D>> blizzard = basin.blizzard(node.minutes() + 1);
            List<Move<Elf>> moves = new ArrayList<>();
            for (Direction direction : Direction.values()) {
                Elf move = node.move(direction);
                Point2D position = move.position();
                if (position.x() < 0 || position.y() < 0 || position.x() > basin.xMax || position.y() > basin.yMax) {
                    continue;
                }
                if (!basin.walls.contains(position) && blizzard.values().stream().noneMatch(s -> s.contains(position))) {
                    moves.add(AStar.Move.of(move));
                }
            }
            if (blizzard.values().stream().noneMatch(s -> s.contains(node.position()))) {
                moves.add(AStar.Move.of(new Elf(node.position(), node.minutes() + 1), 1L));
            }
            return moves;
        }

        @Override
        public long heuristic(Elf node) {
            return Point2D.manhattanDistance(objective, node.position());
        }
    }

    /**
     * --- Part Two ---
     * <pre>
     * As the expedition reaches the far side of the valley, one of the Elves
     * looks especially dismayed:
     *
     * He forgot his snacks at the entrance to the valley!
     *
     * Since you're so good at dodging blizzards, the Elves humbly request that
     * you go back for his snacks. From the same initial conditions, how quickly
     * can you make it from the start to the goal, then back to the start, then
     * back to the goal?
     *
     * In the above example, the first trip to the goal takes 18 minutes, the trip
     * back to the start takes 23 minutes, and the trip back to the goal again
     * takes 13 minutes, for a total time of 54 minutes.
     *
     * What is the fewest number of minutes required to reach the goal, go back to
     * the start, then reach the goal again?
     * </pre>
     */
    public static long partTwo(Scanner scanner) {
        Basin basin = readInput(scanner);

        Point2D start = Point2D.of(1, 0);
        Point2D end = Point2D.of(basin.xMax() - 1, basin.yMax());

        Predicate<Elf> ending = elf -> elf.position(end);
        Predicate<Elf> backToStart = elf -> elf.position(start);

        long firstTrip = new BlizzardPath(basin, end).algorithm(new Elf(start, 0), ending);
        LOGGER.info("firstTrip: {}", firstTrip);
        long secondTrip = new BlizzardPath(basin, start).algorithm(new Elf(end, (int) firstTrip), backToStart);
        LOGGER.info("secondTrip: {}", secondTrip);
        long thirdTrip = new BlizzardPath(basin, end).algorithm(new Elf(start, (int) (firstTrip + secondTrip)), ending);
        LOGGER.info("thirdTrip: {}", thirdTrip);
        return firstTrip + secondTrip + thirdTrip;
    }

    public record Basin(Set<Point2D> walls, int xMax, int yMax, List<Map<Direction, Set<Point2D>>> blizzards) {
        Basin(Set<Point2D> walls, int xMax, int yMax, Map<Direction, Set<Point2D>> blizzard) {
            this(walls, xMax, yMax, new ArrayList<>());
            blizzards.add(blizzard);
        }

        public Map<Direction, Set<Point2D>> blizzard(int minutes) {
            if (minutes < blizzards.size()) {
                return blizzards.get(minutes);
            }
            while (minutes >= blizzards.size()) {
                Map<Direction, Set<Point2D>> last = blizzards.getLast();
                blizzards.add(moveBlizzard(last));
            }

            return blizzards.get(minutes);
        }

        private Map<Direction, Set<Point2D>> moveBlizzard(Map<Direction, Set<Point2D>> blizzard) {
            Map<Direction, Set<Point2D>> next = new EnumMap<>(Direction.class);
            for (Map.Entry<Direction, Set<Point2D>> entry : blizzard.entrySet()) {
                Set<Point2D> moves = new HashSet<>();
                for (Point2D p : entry.getValue()) {
                    Point2D loop = p.moveLoop(entry.getKey(), xMax, yMax);
                    while (walls.contains(loop)) {
                        loop = loop.moveLoop(entry.getKey(), xMax, yMax);
                    }
                    moves.add(loop);
                }
                next.put(entry.getKey(), moves);
            }
            return next;
        }

    }

    record Elf(Point2D position, int minutes) {
        Elf move(Direction direction) {
            return new Elf(position.move(direction), minutes + 1);
        }

        boolean position(Point2D p) {
            return position.equals(p);
        }
    }

}
