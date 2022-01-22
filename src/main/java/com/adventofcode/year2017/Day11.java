package com.adventofcode.year2017;

import com.adventofcode.map.Point3D;

public final class Day11 {
    private Day11() {
        // No-Op
    }

    private static Point3D move(Point3D p, String direction) {
        int x = p.x();
        int y = p.y();
        int z = p.z();
        switch (direction) {
            case "n" -> {
                y++;
                z--;
            }
            case "ne" -> {
                x++;
                z--;
            }
            case "se" -> {
                x++;
                y--;
            }
            case "s" -> {
                y--;
                z++;
            }
            case "sw" -> {
                x--;
                z++;
            }
            case "nw" -> {
                x--;
                y++;
            }
            default -> throw new IllegalStateException("Unknown direction " + direction);
        }

        return Point3D.of(x, y, z);
    }

    /**
     * --- Day 11: Hex Ed ---
     *
     * Crossing the bridge, you've barely reached the other side of the stream
     * when a program comes up to you, clearly in distress. "It's my child
     * process," she says, "he's gotten lost in an infinite grid!"
     *
     * Fortunately for her, you have plenty of experience with infinite grids.
     *
     * Unfortunately for you, it's a hex grid.
     *
     * The hexagons ("hexes") in this grid are aligned such that adjacent hexes
     * can be found to the north, northeast, southeast, south, southwest, and
     * northwest:
     *
     *   \ n  /
     * nw +--+ ne
     *   /    \
     * -+      +-
     *   \    /
     * sw +--+ se
     *   / s  \
     *
     * You have the path the child process took. Starting where he started, you
     * need to determine the fewest number of steps required to reach him. (A
     * "step" means to move from the hex you are in to any adjacent hex.)
     *
     * For example:
     *
     *   - ne,ne,ne is 3 steps away.
     *   - ne,ne,sw,sw is 0 steps away (back where you started).
     *   - ne,ne,s,s is 2 steps away (se,se).
     *   - se,sw,se,sw,sw is 3 steps away (s,s,sw).
     *
     * To begin, get your puzzle input.
     *
     * Your puzzle answer was 824.
     */
    static int hexGridDistance(String input) {
        Point3D position = Point3D.ORIGIN;
        String[] split = input.split(",");
        for (String d : split) {
            position = move(position, d);
        }

        return hexGridDistance(position);
    }

    /**
     * --- Part Two ---
     *
     * How many steps away is the furthest he ever got from his starting position?
     *
     * Your puzzle answer was 1548.
     */
    static int maxGridDistance(String input) {
        int maxDistance = 0;
        Point3D position = Point3D.ORIGIN;
        String[] split = input.split(",");
        for (String d : split) {
            position = move(position, d);
            maxDistance = Math.max(maxDistance, hexGridDistance(position));
        }

        return maxDistance;
    }

    private static int hexGridDistance(Point3D position) {
        return (Math.abs(position.x()) + Math.abs(position.y()) + Math.abs(position.z())) / 2;
    }
}
