package com.adventofcode.year2023;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public final class Day10 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day10.class);

    private static final Map<Pair<Character, Direction>, Direction> PIPE_DIRECTIONS = Map.ofEntries(
            Map.entry(Pair.of('L', Direction.LEFT), Direction.UP),
            Map.entry(Pair.of('F', Direction.UP), Direction.RIGHT),
            Map.entry(Pair.of('7', Direction.RIGHT), Direction.DOWN),
            Map.entry(Pair.of('J', Direction.DOWN), Direction.LEFT),
            Map.entry(Pair.of('F', Direction.LEFT), Direction.DOWN),
            Map.entry(Pair.of('7', Direction.UP), Direction.LEFT),
            Map.entry(Pair.of('J', Direction.RIGHT), Direction.UP),
            Map.entry(Pair.of('L', Direction.DOWN), Direction.RIGHT),
            Map.entry(Pair.of('-', Direction.LEFT), Direction.LEFT),
            Map.entry(Pair.of('-', Direction.RIGHT), Direction.RIGHT),
            Map.entry(Pair.of('|', Direction.UP), Direction.UP),
            Map.entry(Pair.of('|', Direction.DOWN), Direction.DOWN)
    );

    private Day10() {
        // No-Op
    }

    /**
     * --- Day 10: Pipe Maze ---
     * <p>
     * You use the hang glider to ride the hot air from Desert Island all the way
     * up to the floating metal island. This island is surprisingly cold and there
     * definitely aren't any thermals to glide on, so you leave your hang glider
     * behind.
     * <p>
     * You wander around for a while, but you don't find any people or animals.
     * However, you do occasionally find signposts labeled "Hot Springs" pointing
     * in a seemingly consistent direction; maybe you can find someone at the hot
     * springs and ask them where the desert-machine parts are made.
     * <p>
     * The landscape here is alien; even the flowers and trees are made of metal.
     * As you stop to admire some metal grass, you notice something metallic
     * scurry away in your peripheral vision and jump into a big pipe! It didn't
     * look like any animal you've ever seen; if you want a better look, you'll
     * need to get ahead of it.
     * <p>
     * Scanning the area, you discover that the entire field you're standing on is
     * densely packed with pipes; it was hard to tell at first because they're the
     * same metallic silver color as the "ground". You make a quick sketch of all
     * of the surface pipes you can see (your puzzle input).
     * <p>
     * The pipes are arranged in a two-dimensional grid of tiles:
     * <p>
     * - | is a vertical pipe connecting north and south.
     * - - is a horizontal pipe connecting east and west.
     * - L is a 90-degree bend connecting north and east.
     * - J is a 90-degree bend connecting north and west.
     * - 7 is a 90-degree bend connecting south and west.
     * - F is a 90-degree bend connecting south and east.
     * - . is ground; there is no pipe in this tile.
     * - S is the starting position of the animal; there is a pipe on this
     * tile, but your sketch doesn't show what shape the pipe has.
     * <p>
     * Based on the acoustics of the animal's scurrying, you're confident the pipe
     * that contains the animal is one large, continuous loop.
     * <p>
     * For example, here is a square loop of pipe:
     * <p>
     * .....
     * .F-7.
     * .|.|.
     * .L-J.
     * .....
     * <p>
     * If the animal had entered this loop in the northwest corner, the sketch
     * would instead look like this:
     * <p>
     * .....
     * .S-7.
     * .|.|.
     * .L-J.
     * .....
     * <p>
     * In the above diagram, the S tile is still a 90-degree F bend: you can tell
     * because of how the adjacent pipes connect to it.
     * <p>
     * Unfortunately, there are also many pipes that aren't connected to the loop!
     * This sketch shows the same loop as above:
     * <p>
     * -L|F7
     * 7S-7|
     * L|7||
     * -L-J|
     * L|-JF
     * <p>
     * In the above diagram, you can still figure out which pipes form the main
     * loop: they're the ones connected to S, pipes those pipes connect to, pipes
     * those pipes connect to, and so on. Every pipe in the main loop connects to
     * its two neighbors (including S, which will have exactly two pipes
     * connecting to it, and which is assumed to connect back to those two pipes).
     * <p>
     * Here is a sketch that contains a slightly more complex main loop:
     * <p>
     * ..F7.
     * .FJ|.
     * SJ.L7
     * |F--J
     * LJ...
     * <p>
     * Here's the same example sketch with the extra, non-main-loop pipe tiles
     * also shown:
     * <p>
     * 7-F7-
     * .FJ|7
     * SJLL7
     * |F--J
     * LJ.LJ
     * <p>
     * If you want to get out ahead of the animal, you should find the tile in the
     * loop that is farthest from the starting position. Because the animal is in
     * the pipe, it doesn't make sense to measure this by direct distance.
     * Instead, you need to find the tile that would take the longest number of
     * steps along the loop to reach from the starting point - regardless of which
     * way around the loop the animal went.
     * <p>
     * In the first example with the square loop:
     * <p>
     * .....
     * .S-7.
     * .|.|.
     * .L-J.
     * .....
     * <p>
     * You can count the distance each tile in the loop is from the starting point
     * like this:
     * <p>
     * .....
     * .012.
     * .1.3.
     * .234.
     * .....
     * <p>
     * In this example, the farthest point from the start is 4 steps away.
     * <p>
     * Here's the more complex loop again:
     * <p>
     * ..F7.
     * .FJ|.
     * SJ.L7
     * |F--J
     * LJ...
     * <p>
     * Here are the distances for each tile on that loop:
     * <p>
     * ..45.
     * .236.
     * 01.78
     * 14567
     * 23...
     * <p>
     * Find the single giant loop starting at S. How many steps along the loop
     * does it take to get from the starting position to the point farthest from
     * the starting position?
     */
    public static final class PartOne {

        private PartOne() {
            // No-Op
        }

        public static int findMaxDepth(Scanner scanner) {
            CharMap pipes = readPipes(scanner);
            List<Pair<Point2D, Direction>> maxPath = findMaxPath(pipes, findStartPoint(pipes));
            Pair<Point2D, Direction> first = maxPath.getFirst();
            Pair<Point2D, Direction> last = maxPath.getLast();
            LOGGER.info("first = {}, last = {}", first, last);
            return maxPath.size() / 2;
        }
    }

    /**
     * --- Part Two ---
     * <p>
     * You quickly reach the farthest point of the loop, but the animal never
     * emerges. Maybe its nest is within the area enclosed by the loop?
     * <p>
     * To determine whether it's even worth taking the time to search for such a
     * nest, you should calculate how many tiles are contained within the loop.
     * For example:
     * <p>
     * ...........
     * .S-------7.
     * .|F-----7|.
     * .||.....||.
     * .||.....||.
     * .|L-7.F-J|.
     * .|..|.|..|.
     * .L--J.L--J.
     * ...........
     * <p>
     * The above loop encloses merely four tiles - the two pairs of . in the
     * southwest and southeast (marked I below). The middle . tiles (marked O
     * below) are not in the loop. Here is the same loop again with those regions
     * marked:
     * <p>
     * ...........
     * .S-------7.
     * .|F-----7|.
     * .||OOOOO||.
     * .||OOOOO||.
     * .|L-7OF-J|.
     * .|II|O|II|.
     * .L--JOL--J.
     * .....O.....
     * <p>
     * In fact, there doesn't even need to be a full tile path to the outside for
     * tiles to count as outside the loop - squeezing between pipes is also
     * allowed! Here, I is still within the loop and O is still outside the loop:
     * <p>
     * ..........
     * .S------7.
     * .|F----7|.
     * .||OOOO||.
     * .||OOOO||.
     * .|L-7F-J|.
     * .|II||II|.
     * .L--JL--J.
     * ..........
     * <p>
     * In both of the above examples, 4 tiles are enclosed by the loop.
     * <p>
     * Here's a larger example:
     * <p>
     * .F----7F7F7F7F-7....
     * .|F--7||||||||FJ....
     * .||.FJ||||||||L7....
     * FJL7L7LJLJ||LJ.L-7..
     * L--J.L7...LJS7F-7L7.
     * ....F-J..F7FJ|L7L7L7
     * ....L7.F7||L7|.L7L7|
     * .....|FJLJ|FJ|F7|.LJ
     * ....FJL-7.||.||||...
     * ....L---J.LJ.LJLJ...
     * <p>
     * The above sketch has many random bits of ground, some of which are in the
     * loop (I) and some of which are outside it (O):
     * <p>
     * OF----7F7F7F7F-7OOOO
     * O|F--7||||||||FJOOOO
     * O||OFJ||||||||L7OOOO
     * FJL7L7LJLJ||LJIL-7OO
     * L--JOL7IIILJS7F-7L7O
     * OOOOF-JIIF7FJ|L7L7L7
     * OOOOL7IF7||L7|IL7L7|
     * OOOOO|FJLJ|FJ|F7|OLJ
     * OOOOFJL-7O||O||||OOO
     * OOOOL---JOLJOLJLJOOO
     * <p>
     * In this larger example, 8 tiles are enclosed by the loop.
     * <p>
     * Any tile that isn't part of the main loop can count as being enclosed by
     * the loop. Here's another example with many bits of junk pipe lying around
     * that aren't connected to the main loop at all:
     * <p>
     * FF7FSF7F7F7F7F7F---7
     * L|LJ||||||||||||F--J
     * FL-7LJLJ||||||LJL-77
     * F--JF--7||LJLJ7F7FJ-
     * L---JF-JLJ.||-FJLJJ7
     * |F|F-JF---7F7-L7L|7|
     * |FFJF7L7F-JF7|JL---7
     * 7-L-JL7||F7|L7F-7F7|
     * L.L7LFJ|||||FJL7||LJ
     * L7JLJL-JLJLJL--JLJ.L
     * <p>
     * Here are just the tiles that are enclosed by the loop marked with I:
     * <p>
     * FF7FSF7F7F7F7F7F---7
     * L|LJ||||||||||||F--J
     * FL-7LJLJ||||||LJL-77
     * F--JF--7||LJLJIF7FJ-
     * L---JF-JLJIIIIFJLJJ7
     * |F|F-JF---7IIIL7L|7|
     * |FFJF7L7F-JF7IIL---7
     * 7-L-JL7||F7|L7F-7F7|
     * L.L7LFJ|||||FJL7||LJ
     * L7JLJL-JLJLJL--JLJ.L
     * <p>
     * In this last example, 10 tiles are enclosed by the loop.
     * <p>
     * Figure out whether you have time to search for the nest by calculating the
     * area within the loop. How many tiles are enclosed by the loop?
     */
    public static final class PartTwo {

        private PartTwo() {
            // No-Op
        }

        public static int countEnclosedTiles(Scanner scanner) {
            CharMap pipes = readPipes(scanner);
            Point2D start = findStartPoint(pipes);
            List<Pair<Point2D, Direction>> maxPath = findMaxPath(pipes, start);

            Set<Point2D> inPath = maxPath.stream().map(Pair::left).collect(Collectors.toSet());
            for (ObjectCharPair<Point2D> entry : pipes.entries()) {
                if (!inPath.contains(entry.left())) {
                    pipes.set(entry.left(), '.');
                }
            }

            Pair<Point2D, Direction> first = maxPath.getFirst();
            Pair<Point2D, Direction> last = maxPath.getLast();
            for (Map.Entry<Pair<Character, Direction>, Direction> entry : PIPE_DIRECTIONS.entrySet()) {
                if (entry.getValue() == first.right() && entry.getKey().right() == last.right()) {
                    LOGGER.info("Correct start = {}", entry);
                    pipes.set(start, entry.getKey().left());
                }
            }

            LOGGER.trace("Correct map: \n{}", pipes);

            int count = 0;

            List<String> lines = pipes.values();
            for (String line : lines) {
                line = line.replaceAll("F-*7|L-*J", "");
                line = line.replaceAll("F-*J|L-*7", "|");
                int parity = 0;
                for (int i = 0; i < line.length(); i++) {
                    char c = line.charAt(i);
                    if (c == '|') parity++;
                    if (c == '.' && parity % 2 == 1) count++;
                }
            }

            return count;
        }
    }

    private static List<Pair<Point2D, Direction>> findMaxPath(CharMap pipes, Point2D start) {

        int maxSize = 0;
        List<Pair<Point2D, Direction>> maxPath = null;

        for (Direction direction : Direction.values()) {
            List<Pair<Point2D, Direction>> path = findPath(pipes, start, direction);

            if (path == null) {
                continue;
            }

            LOGGER.info("Path from {}/{} : {}", start, direction, path);
            if (path.size() > maxSize) {
                maxSize = path.size();
                maxPath = path;
            }
        }

        if (maxPath == null) {
            throw new IllegalStateException("Cannot find max path");
        }

        LOGGER.info("max path is = {}", maxPath);
        LOGGER.info("max size is = {}", maxPath.size());

        return maxPath;
    }

    private static CharMap readPipes(Scanner scanner) {
        CharMap pipes = CharMap.read(scanner, c -> true);

        LOGGER.trace("pipes = \n{}", pipes);
        return pipes;
    }

    @Nullable
    private static List<Pair<Point2D, Direction>> findPath(CharMap pipes, Point2D start, Direction direction) {
        Point2D current = start.move(direction);
        if (current.x() < 0 || current.y() < 0) {
            return null;
        }
        char pipe = pipes.get(current);
        if (pipe == '.') {
            return null;
        }

        List<Pair<Point2D, Direction>> path = new ArrayList<>();
        path.add(Pair.of(start, direction));
        while (!current.equals(start)) {
            direction = PIPE_DIRECTIONS.get(Pair.of(pipe, direction));
            if (direction == null) {
                LOGGER.error("Cannot find direction");
                break;
            }

            path.add(Pair.of(current, direction));

            current = current.move(direction);
            pipe = pipes.get(current);
        }

        return path;
    }

    private static Point2D findStartPoint(CharMap pipes) {
        for (ObjectCharPair<Point2D> entry : pipes.entries()) {
            if (entry.rightChar() == 'S') {
                return entry.left();
            }
        }

        throw new IllegalStateException();
    }
}
