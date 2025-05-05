package com.adventofcode.year2024;

import com.adventofcode.common.graph.AStar;
import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.Position2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;

public final class Day16 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day16.class);

    private Day16() {
        // No-Op
    }

    /**
     * --- Day 16: Reindeer Maze ---
     * <p>
     * It's time again for the Reindeer Olympics! This year, the big event is the
     * Reindeer Maze, where the Reindeer compete for the lowest score.
     * <p>
     * You and The Historians arrive to search for the Chief right as the event is
     * about to start. It wouldn't hurt to watch a little, right?
     * <p>
     * The Reindeer start on the Start Tile (marked S) facing East and need to
     * reach the End Tile (marked E). They can move forward one tile at a time
     * (increasing their score by 1 point), but never into a wall (#). They can
     * also rotate clockwise or counterclockwise 90 degrees at a time (increasing
     * their score by 1000 points).
     * <p>
     * To figure out the best place to sit, you start by grabbing a map (your
     * puzzle input) from a nearby kiosk. For example:
     * <p>
     * ###############
     * #.......#....E#
     * #.#.###.#.###.#
     * #.....#.#...#.#
     * #.###.#####.#.#
     * #.#.#.......#.#
     * #.#.#####.###.#
     * #...........#.#
     * ###.#.#####.#.#
     * #...#.....#.#.#
     * #.#.#.###.#.#.#
     * #.....#...#.#.#
     * #.###.#.#.#.#.#
     * #S..#.....#...#
     * ###############
     * <p>
     * There are many paths through this maze, but taking any of the best paths
     * would incur a score of only 7036. This can be achieved by taking a total of
     * 36 steps forward and turning 90 degrees a total of 7 times:
     * <p>
     * <p>
     * ###############
     * #.......#....E#
     * #.#.###.#.###^#
     * #.....#.#...#^#
     * #.###.#####.#^#
     * #.#.#.......#^#
     * #.#.#####.###^#
     * #..>>>>>>>>v#^#
     * ###^#.#####v#^#
     * #>>^#.....#v#^#
     * #^#.#.###.#v#^#
     * #^....#...#v#^#
     * #^###.#.#.#v#^#
     * #S..#.....#>>^#
     * ###############
     * <p>
     * Here's a second example:
     * <p>
     * #################
     * #...#...#...#..E#
     * #.#.#.#.#.#.#.#.#
     * #.#.#.#...#...#.#
     * #.#.#.#.###.#.#.#
     * #...#.#.#.....#.#
     * #.#.#.#.#.#####.#
     * #.#...#.#.#.....#
     * #.#.#####.#.###.#
     * #.#.#.......#...#
     * #.#.###.#####.###
     * #.#.#...#.....#.#
     * #.#.#.#####.###.#
     * #.#.#.........#.#
     * #.#.#.#########.#
     * #S#.............#
     * #################
     * <p>
     * In this maze, the best paths cost 11048 points; following one such path
     * would look like this:
     * <p>
     * #################
     * #...#...#...#..E#
     * #.#.#.#.#.#.#.#^#
     * #.#.#.#...#...#^#
     * #.#.#.#.###.#.#^#
     * #>>v#.#.#.....#^#
     * #^#v#.#.#.#####^#
     * #^#v..#.#.#>>>>^#
     * #^#v#####.#^###.#
     * #^#v#..>>>>^#...#
     * #^#v###^#####.###
     * #^#v#>>^#.....#.#
     * #^#v#^#####.###.#
     * #^#v#^........#.#
     * #^#v#^#########.#
     * #S#>>^..........#
     * #################
     * <p>
     * Note that the path shown above includes one 90 degree turn as the
     * first move, rotating the Reindeer from facing East to facing North.
     * <p>
     * Analyze your map carefully. What is the lowest score a Reindeer could
     * possibly get?
     */
    public static long partOne(Scanner scanner) {
        CharMap maze = CharMap.read(scanner, ignore -> true);

        LOGGER.info("maze:\n{}", maze);

        Point2D startPoint = null;
        Point2D endPoint = null;
        for (ObjectCharPair<Point2D> entry : maze.entries()) {
            if (entry.rightChar() == 'S') {
                startPoint = entry.left();
            } else if (entry.rightChar() == 'E') {
                endPoint = entry.left();
            }
        }

        if (startPoint == null || endPoint == null) {
            throw new IllegalStateException("Cannot find start or end");
        }
        final Point2D finalEndPoint = endPoint;

        Position2D start = new Position2D(startPoint, Direction.RIGHT);
        Predicate<Position2D> ending = state -> finalEndPoint.equals(state.p());

        return new ReindeerMaze(maze, endPoint).algorithm(start, ending);
    }

    private static final class ReindeerMaze extends AStar<Position2D> {

        private final CharMap maze;
        private final Point2D end;

        private ReindeerMaze(CharMap maze, Point2D end) {
            this.maze = maze;
            this.end = end;
        }

        @Override
        public Iterable<Move<Position2D>> next(Position2D node) {
            List<Move<Position2D>> moves = new ArrayList<>(3);
            moves.add(AStar.Move.of(node.left(), 1000L));
            moves.add(AStar.Move.of(node.right(), 1000L));
            Position2D forward = node.move();
            char c = maze.get(forward.p());
            if (c == '.' || c == 'S' || c == 'E') {
                moves.add(AStar.Move.of(forward, 1L));
            }
            return moves;
        }

        @Override
        public long heuristic(Position2D node) {
            return Point2D.manhattanDistance(end, node.p());
        }
    }

    /**
     * --- Part Two ---
     * <p>
     * Now that you know what the best paths look like, you can figure out the
     * best spot to sit.
     * <p>
     * Every non-wall tile (S, ., or E) is equipped with places to sit along the
     * edges of the tile. While determining which of these tiles would be the best
     * spot to sit depends on a whole bunch of factors (how comfortable the seats
     * are, how far away the bathrooms are, whether there's a pillar blocking your
     * view, etc.), the most important factor is whether the tile is on one of the
     * best paths through the maze. If you sit somewhere else, you'd miss all the
     * action!
     * <p>
     * So, you'll need to determine which tiles are part of any best path through
     * the maze, including the S and E tiles.
     * <p>
     * In the first example, there are 45 tiles (marked O) that are part of at
     * least one of the various best paths through the maze:
     * <p>
     * ###############
     * #.......#....O#
     * #.#.###.#.###O#
     * #.....#.#...#O#
     * #.###.#####.#O#
     * #.#.#.......#O#
     * #.#.#####.###O#
     * #..OOOOOOOOO#O#
     * ###O#O#####O#O#
     * #OOO#O....#O#O#
     * #O#O#O###.#O#O#
     * #OOOOO#...#O#O#
     * #O###.#.#.#O#O#
     * #O..#.....#OOO#
     * ###############
     * <p>
     * In the second example, there are 64 tiles that are part of at least one of
     * the best paths:
     * <p>
     * #################
     * #...#...#...#..O#
     * #.#.#.#.#.#.#.#O#
     * #.#.#.#...#...#O#
     * #.#.#.#.###.#.#O#
     * #OOO#.#.#.....#O#
     * #O#O#.#.#.#####O#
     * #O#O..#.#.#OOOOO#
     * #O#O#####.#O###O#
     * #O#O#..OOOOO#OOO#
     * #O#O###O#####O###
     * #O#O#OOO#..OOO#.#
     * #O#O#O#####O###.#
     * #O#O#OOOOOOO..#.#
     * #O#O#O#########.#
     * #O#OOO..........#
     * #################
     * <p>
     * Analyze your map further. How many tiles are part of at least one of the
     * best paths through the maze?
     */
    public static long partTwo(Scanner scanner) {
        CharMap maze = CharMap.read(scanner, ignore -> true);

        Point2D startPoint = null;
        Point2D endPoint = null;

        for (ObjectCharPair<Point2D> entry : maze.entries()) {
            char c = entry.rightChar();
            if (c == 'S') {
                startPoint = entry.left();
            }
            if (c == 'E') {
                endPoint = entry.left();
            }
        }

        if (startPoint == null || endPoint == null) {
            throw new IllegalStateException("Cannot find start or end");
        }
        Position2D start = new Position2D(startPoint, Direction.RIGHT);

        Map<Position2D, Long> costs = new HashMap<>();
        for (ObjectCharPair<Point2D> entry : maze.entries()) {
            char c = entry.rightChar();
            if (c == '.' || c == 'S' || c == 'E') {
                for (Direction value : Direction.values()) {
                    costs.put(new Position2D(entry.left(), value), Long.MAX_VALUE);
                }
            }
        }
        costs.put(start, 0L);

        Map<Position2D, List<Position2D>> previous = new HashMap<>();
        previous.put(start, List.of());

        Queue<Path> queue = new PriorityQueue<>(Comparator.comparingLong(Path::cost));
        queue.add(new Path(start, 0));

        while (!queue.isEmpty()) {
            Path node = queue.poll();

            for (Path path : node.moves(maze)) {
                long cost = Objects.requireNonNull(costs.get(path.state()));
                if (cost == path.cost()) {
                    previous.computeIfAbsent(path.state(), ignore -> new ArrayList<>()).add(node.state());
                    queue.add(path);
                } else if (cost > path.cost()) {
                    costs.put(path.state(), path.cost());
                    previous.remove(path.state());
                    previous.computeIfAbsent(path.state(), ignore -> new ArrayList<>()).add(node.state());
                    queue.add(path);
                }
            }
        }

        Position2D endState = null;
        long optimum = Long.MAX_VALUE;
        for (Map.Entry<Position2D, Long> entry : costs.entrySet()) {
            Position2D state = entry.getKey();
            if (state.p().equals(endPoint) && optimum > entry.getValue()) {
                endState = state;
                optimum = entry.getValue();
            }
        }

        Set<Position2D> bestSeat = new HashSet<>();
        bestSeat.add(endState);
        Queue<Position2D> queueSeat = new ArrayDeque<>();
        queueSeat.add(endState);
        while (!queueSeat.isEmpty()) {
            Position2D poll = queueSeat.poll();
            for (Position2D state : previous.getOrDefault(poll, List.of())) {
                if (bestSeat.add(state)) {
                    queueSeat.add(state);
                }
            }
        }

        return bestSeat.stream().map(Position2D::p).distinct().count();
    }

    private record Path(Position2D state, long cost) {
        Path turnLeft() {
            return new Path(state.left(), cost + 1000);
        }

        Path turnRight() {
            return new Path(state.right(), cost + 1000);
        }

        Path forward() {
            return new Path(state.move(), cost + 1);
        }

        Point2D position() {
            return state.p();
        }

        List<Path> moves(CharMap maze) {
            List<Path> paths = new ArrayList<>(3);

            paths.add(turnLeft());
            paths.add(turnRight());

            Path forward = forward();
            char c = maze.get(forward.position());
            if (c == '.' || c == 'S' || c == 'E') {
                paths.add(forward);
            }

            return paths;
        }
    }
}
