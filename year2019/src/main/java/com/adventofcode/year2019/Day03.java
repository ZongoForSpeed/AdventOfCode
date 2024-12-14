package com.adventofcode.year2019;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;

public final class Day03 {

    private Day03() {
        // No-Op
    }

    /**
     * --- Day 3: Crossed Wires ---
     * <p>
     * The gravity assist was successful, and you're well on your way to the Venus refuelling station. During the rush
     * back on Earth, the fuel management system wasn't completely installed, so that's next on the priority list.
     * <p>
     * Opening the front panel reveals a jumble of wires. Specifically, two wires are connected to a central port and
     * extend outward on a grid. You trace the path each wire takes as it leaves the central port, one wire per line of
     * text (your puzzle input).
     * <p>
     * The wires twist and turn, but the two wires occasionally cross paths. To fix the circuit, you need to find the
     * intersection point closest to the central port. Because the wires are on a grid, use the Manhattan distance for
     * this measurement. While the wires do technically cross right at the central port where they both start, this point
     * does not count, nor does a wire count as crossing with itself.
     * <p>
     * For example, if the first wire's path is R8,U5,L5,D3, then starting from the central port (o), it goes right 8,
     * up 5, left 5, and finally down 3:
     * <p>
     * ...........
     * ...........
     * ...........
     * ....+----+.
     * ....|....|.
     * ....|....|.
     * ....|....|.
     * .........|.
     * .o-------+.
     * ...........
     * <p>
     * Then, if the second wire's path is U7,R6,D4,L4, it goes up 7, right 6, down 4, and left 4:
     * <p>
     * ...........
     * .+-----+...
     * .|.....|...
     * .|..+--X-+.
     * .|..|..|.|.
     * .|.-X--+.|.
     * .|..|....|.
     * .|.......|.
     * .o-------+.
     * ...........
     * <p>
     * These wires cross at two locations (marked X), but the lower-left one is closer to the central port: its distance is 3 + 3 = 6.
     * <p>
     * Here are a few more examples:
     * <p>
     * R75,D30,R83,U83,L12,D49,R71,U7,L72
     * U62,R66,U55,R34,D71,R55,D58,R83 = distance 159
     * R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
     * U98,R91,D20,R16,D67,R40,U7,R15,U6,R7 = distance 135
     * What is the Manhattan distance from the central port to the closest intersection?
     */
    public static long intersection(String path1, String path2) {
        List<Point2D> positions1 = readPath(path1);
        List<Point2D> positions2 = readPath(path2);

        Sets.SetView<Point2D> intersection = Sets.intersection(Sets.newHashSet(positions1.subList(1, positions1.size() - 1)), Sets.newHashSet(positions2.subList(1, positions2.size() - 1)));
        long distance = Integer.MAX_VALUE;
        for (Point2D position : intersection) {
            int d = Math.abs(position.x()) + Math.abs(position.y());
            if (d < distance) {
                distance = d;
            }
        }
        return distance;
    }

    public static List<Point2D> readPath(String path) {
        List<Point2D> result = new ArrayList<>();
        Point2D position = Point2D.of(0, 0);
        result.add(position);
        for (String move : Splitter.on(',').split(path)) {
            char direction = move.charAt(0);
            int distance = Integer.parseInt(move.substring(1));

            for (int i = 1; i < distance + 1; ++i) {
                position = switch (direction) {
                    case 'R' -> position.move(Direction.RIGHT);
                    case 'U' -> position.move(Direction.UP);
                    case 'L' -> position.move(Direction.LEFT);
                    case 'D' -> position.move(Direction.DOWN);
                    default -> Point2D.of(Integer.MIN_VALUE, Integer.MIN_VALUE);
                };
                result.add(position);
            }
            position = Iterables.getLast(result);
        }
        return result;
    }

    public static int intersectionSteps(String path1, String path2) {
        List<Point2D> positions1 = readPath(path1);
        List<Point2D> positions2 = readPath(path2);

        Sets.SetView<Point2D> intersection = Sets.intersection(Sets.newHashSet(positions1.subList(1, positions1.size() - 1)), Sets.newHashSet(positions2.subList(1, positions2.size() - 1)));
        int steps = Integer.MAX_VALUE;
        for (Point2D position : intersection) {
            int steps1 = positions1.indexOf(position);
            int steps2 = positions2.indexOf(position);

            if (steps > steps1 + steps2) {
                steps = steps1 + steps2;
            }

        }
        return steps;
    }

}
