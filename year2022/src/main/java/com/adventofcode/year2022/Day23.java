package com.adventofcode.year2022;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.InfiniteCharMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public final class Day23 {

    private Day23() {
        // No-Op
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Day23.class);

    private static final Point2D N = Point2D.of(0, -1);
    private static final Point2D S = Point2D.of(0, 1);
    private static final Point2D W = Point2D.of(-1, 0);
    private static final Point2D E = Point2D.of(1, 0);
    private static final Point2D NE = Point2D.of(1, -1);
    private static final Point2D NW = Point2D.of(-1, -1);
    private static final Point2D SE = Point2D.of(1, 1);
    private static final Point2D SW = Point2D.of(-1, 1);

    private static final Map<Direction, List<Point2D>> DIRECTIONS = Map.of(
            Direction.UP, List.of(N, NW, NE),   // NORTH
            Direction.DOWN, List.of(S, SW, SE), // SOUTH
            Direction.LEFT, List.of(W, NW, SW), // WEST
            Direction.RIGHT, List.of(E, NE, SE) // EAST
    );

    private static final List<Point2D> ADJACENT = List.of(N, S, W, E, NE, NW, SE, SW);

    /**
     * --- Day 23: Unstable Diffusion ---
     * <pre>
     * You enter a large crater of gray dirt where the grove is supposed to be.
     * All around you, plants you imagine were expected to be full of fruit are
     * instead withered and broken. A large group of Elves has formed in the
     * middle of the grove.
     *
     * "...but this volcano has been dormant for months. Without ash, the fruit
     * can't grow!"
     *
     * You look up to see a massive, snow-capped mountain towering above you.
     *
     * "It's not like there are other active volcanoes here; we've looked
     * everywhere."
     *
     * "But our scanners show active magma flows; clearly it's going somewhere."
     *
     * They finally notice you at the edge of the grove, your pack almost
     * overflowing from the random star fruit you've been collecting. Behind you,
     * elephants and monkeys explore the grove, looking concerned. Then, the Elves
     * recognize the ash cloud slowly spreading above your recent detour.
     *
     * "Why do you--" "How is--" "Did you just--"
     *
     * Before any of them can form a complete question, another Elf speaks up:
     * "Okay, new plan. We have almost enough fruit already, and ash from the
     * plume should spread here eventually. If we quickly plant new seedlings now,
     * we can still make it to the extraction point. Spread out!"
     *
     * The Elves each reach into their pack and pull out a tiny plant. The plants
     * rely on important nutrients from the ash, so they can't be planted too
     * close together.
     *
     * There isn't enough time to let the Elves figure out where to plant the
     * seedlings themselves; you quickly scan the grove (your puzzle input) and
     * note their positions.
     *
     * For example:
     *
     * ....#..
     * ..###.#
     * #...#.#
     * .#...##
     * #.###..
     * ##.#.##
     * .#..#..
     *
     * The scan shows Elves # and empty ground .; outside your scan, more empty
     * ground extends a long way in every direction. The scan is oriented so that
     * north is up; orthogonal directions are written N (north), S (south), W
     * (west), and E (east), while diagonal directions are written NE, NW, SE, SW.
     *
     * The Elves follow a time-consuming process to figure out where they should
     * each go; you can speed up this process considerably. The process consists
     * of some number of rounds during which Elves alternate between considering
     * where to move and actually moving.
     *
     * During the first half of each round, each Elf considers the eight positions
     * adjacent to themself. If no other Elves are in one of those eight
     * positions, the Elf does not do anything during this round. Otherwise, the
     * Elf looks in each of four directions in the following order and proposes
     * moving one step in the first valid direction:
     *
     *   - If there is no Elf in the N, NE, or NW adjacent positions, the Elf
     *     proposes moving north one step.
     *   - If there is no Elf in the S, SE, or SW adjacent positions, the Elf
     *     proposes moving south one step.
     *   - If there is no Elf in the W, NW, or SW adjacent positions, the Elf
     *     proposes moving west one step.
     *   - If there is no Elf in the E, NE, or SE adjacent positions, the Elf
     *     proposes moving east one step.
     *
     * After each Elf has had a chance to propose a move, the second half of the
     * round can begin. Simultaneously, each Elf moves to their proposed
     * destination tile if they were the only Elf to propose moving to that
     * position. If two or more Elves propose moving to the same position, none of
     * those Elves move.
     *
     * Finally, at the end of the round, the first direction the Elves considered
     * is moved to the end of the list of directions. For example, during the
     * second round, the Elves would try proposing a move to the south first, then
     * west, then east, then north. On the third round, the Elves would first
     * consider west, then east, then north, then south.
     *
     * As a smaller example, consider just these five Elves:
     *
     * .....
     * ..##.
     * ..#..
     * .....
     * ..##.
     * .....
     *
     * The northernmost two Elves and southernmost two Elves all propose moving
     * north, while the middle Elf cannot move north and proposes moving south.
     * The middle Elf proposes the same destination as the southwest Elf, so
     * neither of them move, but the other three do:
     *
     * ..##.
     * .....
     * ..#..
     * ...#.
     * ..#..
     * .....
     *
     * Next, the northernmost two Elves and the southernmost Elf all propose
     * moving south. Of the remaining middle two Elves, the west one cannot move
     * south and proposes moving west, while the east one cannot move south or
     * west and proposes moving east. All five Elves succeed in moving to their
     * proposed positions:
     *
     * .....
     * ..##.
     * .#...
     * ....#
     * .....
     * ..#..
     *
     * Finally, the southernmost two Elves choose not to move at all. Of the
     * remaining three Elves, the west one proposes moving west, the east one
     * proposes moving east, and the middle one proposes moving north; all three
     * succeed in moving:
     *
     * ..#..
     * ....#
     * #....
     * ....#
     * .....
     * ..#..
     *
     * At this point, no Elves need to move, and so the process ends.
     *
     * The larger example above proceeds as follows:
     *
     * == Initial State ==
     * ..............
     * ..............
     * .......#......
     * .....###.#....
     * ...#...#.#....
     * ....#...##....
     * ...#.###......
     * ...##.#.##....
     * ....#..#......
     * ..............
     * ..............
     * ..............
     *
     * == End of Round 1 ==
     * ..............
     * .......#......
     * .....#...#....
     * ...#..#.#.....
     * .......#..#...
     * ....#.#.##....
     * ..#..#.#......
     * ..#.#.#.##....
     * ..............
     * ....#..#......
     * ..............
     * ..............
     *
     * == End of Round 2 ==
     * ..............
     * .......#......
     * ....#.....#...
     * ...#..#.#.....
     * .......#...#..
     * ...#..#.#.....
     * .#...#.#.#....
     * ..............
     * ..#.#.#.##....
     * ....#..#......
     * ..............
     * ..............
     *
     * == End of Round 3 ==
     * ..............
     * .......#......
     * .....#....#...
     * ..#..#...#....
     * .......#...#..
     * ...#..#.#.....
     * .#..#.....#...
     * .......##.....
     * ..##.#....#...
     * ...#..........
     * .......#......
     * ..............
     *
     * == End of Round 4 ==
     * ..............
     * .......#......
     * ......#....#..
     * ..#...##......
     * ...#.....#.#..
     * .........#....
     * .#...###..#...
     * ..#......#....
     * ....##....#...
     * ....#.........
     * .......#......
     * ..............
     *
     * == End of Round 5 ==
     * .......#......
     * ..............
     * ..#..#.....#..
     * .........#....
     * ......##...#..
     * .#.#.####.....
     * ...........#..
     * ....##..#.....
     * ..#...........
     * ..........#...
     * ....#..#......
     * ..............
     *
     * After a few more rounds...
     *
     * == End of Round 10 ==
     * .......#......
     * ...........#..
     * ..#.#..#......
     * ......#.......
     * ...#.....#..#.
     * .#......##....
     * .....##.......
     * ..#........#..
     * ....#.#..#....
     * ..............
     * ....#..#..#...
     * ..............
     *
     * To make sure they're on the right track, the Elves like to check after
     * round 10 that they're making good progress toward covering enough ground.
     * To do this, count the number of empty ground tiles contained by the
     * smallest rectangle that contains every Elf. (The edges of the rectangle
     * should be aligned to the N/S/E/W directions; the Elves do not have the
     * patience to calculate arbitrary rectangles.) In the above example, that
     * rectangle is:
     *
     * ......#.....
     * ..........#.
     * .#.#..#.....
     * .....#......
     * ..#.....#..#
     * #......##...
     * ....##......
     * .#........#.
     * ...#.#..#...
     * ............
     * ...#..#..#..
     *
     * In this region, the number of empty ground tiles is 110.
     *
     * Simulate the Elves' process and find the smallest rectangle that contains
     * the Elves after 10 rounds. How many empty ground tiles does that rectangle
     * contain?
     * </pre>
     */
    public static int partOne(Scanner scanner) {
        InfiniteCharMap map = InfiniteCharMap.read(scanner, c -> c == '#');
        LOGGER.debug("map:\n{}", map);

        Deque<Direction> moves = new ArrayDeque<>(List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));
        Set<Point2D> elves = new HashSet<>(map.keySet());

        for (int i = 1; i <= 10; ++i) {
            int movingElves = moveElves(moves, elves);
            if (movingElves == 0) {
                LOGGER.info("Elves won't move");
                break;
            }
            LOGGER.trace("== End of Round {} ==\n{}", i, elves);
            moves.addLast(moves.pollFirst());
        }

        int xMin = elves.stream().mapToInt(Point2D::x).min().orElse(0);
        int xMax = elves.stream().mapToInt(Point2D::x).max().orElse(0);
        int yMin = elves.stream().mapToInt(Point2D::y).min().orElse(0);
        int yMax = elves.stream().mapToInt(Point2D::y).max().orElse(0);

        int dx = xMax - xMin + 1;
        int dy = yMax - yMin + 1;

        return dx * dy - elves.size();
    }

    private static int moveElves(Deque<Direction> moves, Set<Point2D> elves) {
        Map<Point2D, List<Point2D>> proposition = new HashMap<>();
        for (Point2D elf : elves) {
            if (ADJACENT.stream().map(elf::move).noneMatch(elves::contains)) {
                LOGGER.trace("Elf {} has no adjacent", elf);
                continue;
            }

            Direction proposed = null;
            for (Direction move : moves) {
                List<Point2D> directions = Objects.requireNonNull(DIRECTIONS.get(move));
                if (directions.stream().map(elf::move).noneMatch(elves::contains)) {
                    proposed = move;
                    break;
                }
            }

            if (proposed != null) {
                Point2D move = elf.move(proposed);
                proposition.computeIfAbsent(move, ignore -> new ArrayList<>()).add(elf);
            }
        }
        int count = 0;
        for (Map.Entry<Point2D, List<Point2D>> entry : proposition.entrySet()) {
            if (entry.getValue().size() > 1) {
                LOGGER.trace("elves {} cannot move to {}", entry.getValue(), entry.getKey());
            } else {
                LOGGER.trace("elf {} can move to {}", entry.getValue(), entry.getKey());
                Point2D elf = entry.getValue().getFirst();
                count++;
                elves.remove(elf);
                elves.add(entry.getKey());
            }
        }

        return count;
    }

    /**
     * --- Part Two ---
     * <pre>
     * It seems you're on the right track. Finish simulating the process and
     * figure out where the Elves need to go. How many rounds did you save them?
     *
     * In the example above, the first round where no Elf moved was round 20:
     *
     * .......#......
     * ....#......#..
     * ..#.....#.....
     * ......#.......
     * ...#....#.#..#
     * #.............
     * ....#.....#...
     * ..#.....#.....
     * ....#.#....#..
     * .........#....
     * ....#......#..
     * .......#......
     *
     * Figure out where the Elves need to go. What is the number of the first
     * round where no Elf moves?
     * </pre>
     */
    public static int partTwo(Scanner scanner) {
        InfiniteCharMap map = InfiniteCharMap.read(scanner, c -> c == '#');
        LOGGER.debug("map:\n{}", map);

        Deque<Direction> moves = new ArrayDeque<>(List.of(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT));
        Set<Point2D> elves = new HashSet<>(map.keySet());

        for (int i = 1; ; ++i) {
            int movingElves = moveElves(moves, elves);
            if (movingElves == 0) {
                LOGGER.info("Elves won't move");
                return i;
            }
            LOGGER.trace("== End of Round {} ==\n{}", i, elves);
            moves.addLast(moves.pollFirst());
        }
    }
}
