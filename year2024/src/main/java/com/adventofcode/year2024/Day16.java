package com.adventofcode.year2024;

import com.adventofcode.common.graph.AStar;
import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.Pair;
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
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToLongFunction;

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

        State start = new State(startPoint, Direction.RIGHT);
        Function<State, List<Pair<State, Long>>> graph = state -> state.moves(maze);
        ToLongFunction<State> heuristic = value -> Point2D.manhattanDistance(finalEndPoint, value.position());
        Predicate<State> ending = state -> finalEndPoint.equals(state.position());

        return AStar.algorithmHeuristic(graph, heuristic, start, ending);
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
        State start = new State(startPoint, Direction.RIGHT);

        Map<State, Long> costs = new HashMap<>();
        for (ObjectCharPair<Point2D> entry : maze.entries()) {
            char c = entry.rightChar();
            if (c == '.' || c == 'S' || c == 'E') {
                for (Direction value : Direction.values()) {
                    costs.put(new State(entry.left(), value), Long.MAX_VALUE);
                }
            }
        }
        costs.put(start, 0L);

        Map<State, List<State>> previous = new HashMap<>();
        previous.put(start, List.of());

        Queue<Move> queue = new PriorityQueue<>(Comparator.comparingLong(Move::cost));
        queue.add(new Move(start, 0));

        while (!queue.isEmpty()) {
            Move node = queue.poll();

            for (Move move : node.moves(maze)) {
                Long cost = costs.get(move.state());
                if (cost == move.cost()) {
                    previous.computeIfAbsent(move.state(), ignore -> new ArrayList<>()).add(node.state());
                    queue.add(move);
                } else if (cost > move.cost()) {
                    costs.put(move.state(), move.cost());
                    previous.remove(move.state());
                    previous.computeIfAbsent(move.state(), ignore -> new ArrayList<>()).add(node.state());
                    queue.add(move);
                }
            }
        }

        State endState = null;
        long optimum = Long.MAX_VALUE;
        for (Map.Entry<State, Long> entry : costs.entrySet()) {
            State state = entry.getKey();
            if (state.position().equals(endPoint) && optimum > entry.getValue()) {
                endState = state;
                optimum = entry.getValue();
            }
        }

        Set<State> bestSeat = new HashSet<>();
        bestSeat.add(endState);
        Queue<State> queueSeat = new ArrayDeque<>();
        queueSeat.add(endState);
        while (!queueSeat.isEmpty()) {
            State poll = queueSeat.poll();
            for (State state : previous.getOrDefault(poll, List.of())) {
                if (bestSeat.add(state)) {
                    queueSeat.add(state);
                }
            }
        }

        return bestSeat.stream().map(State::position).distinct().count();
    }

    private record State(Point2D position, Direction direction) {
        State turnLeft() {
            return new State(position, direction.left());
        }

        State turnRight() {
            return new State(position, direction.right());
        }

        State forward() {
            return new State(position.move(direction), direction);
        }

        List<Pair<State, Long>> moves(CharMap maze) {
            List<Pair<State, Long>> moves = new ArrayList<>(3);
            moves.add(Pair.of(turnLeft(), 1000L));
            moves.add(Pair.of(turnRight(), 1000L));
            State forward = forward();
            char c = maze.get(forward.position());
            if (c == '.' || c == 'S' || c == 'E') {
                moves.add(Pair.of(forward, 1L));
            }
            return moves;
        }
    }

    private record Move(State state, long cost) {
        Move turnLeft() {
            return new Move(state.turnLeft(), cost + 1000);
        }

        Move turnRight() {
            return new Move(state.turnRight(), cost + 1000);
        }

        Move forward() {
            return new Move(state.forward(), cost + 1);
        }

        Point2D position() {
            return state.position();
        }

        Direction direction() {
            return state.direction();
        }

        List<Move> moves(CharMap maze) {
            List<Move> moves = new ArrayList<>(3);

            moves.add(turnLeft());
            moves.add(turnRight());

            Move forward = forward();
            char c = maze.get(forward.position());
            if (c == '.' || c == 'S' || c == 'E') {
                moves.add(forward);
            }

            return moves;
        }
    }
}
