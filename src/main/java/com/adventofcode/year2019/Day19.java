package com.adventofcode.year2019;

import com.adventofcode.Intcode;
import com.adventofcode.point.map.Map2D;
import com.adventofcode.point.Point2D;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

public final class Day19 {
    private Day19() {
        // No-Op
    }

    private static boolean tractorBeam(String line, long x, long y) {
        if (x < 0 || y < 0) {
            return false;
        }
        Queue<Long> input = new ArrayDeque<>();
        input.add(x);
        input.add(y);
        AtomicLong output = new AtomicLong();
        Intcode.intcode(line, input::poll, output::set);

        return output.get() != 0L;
    }

    private static Point2D findSquare(String line, int yStart, int yOffset) {
        boolean trackBeam = false;
        double slope = findSlope(line);
        for (int y = yStart; ; y += yOffset) {
            for (int x = (int) (y * slope); ; ++x) {
                boolean beam = tractorBeam(line, x, y);
                if (!trackBeam) {
                    trackBeam = beam;
                } else if (!beam || !tractorBeam(line, x + 99L, y)) {
                    break;
                }
                if (tractorBeam(line, x, y + 99L)) {
                    return Point2D.of(x, y);
                }
            }
        }
    }

    private static double findSlope(String line) {
        for (long x = 0; ; x++) {
            if (tractorBeam(line, x, 100))
                return x / 100D;
        }
    }

    /**
     * --- Day 19: Tractor Beam ---
     * Unsure of the state of Santa's ship, you borrowed the tractor beam technology from Triton. Time to test it out.
     *
     * When you're safely away from anything else, you activate the tractor beam, but nothing happens. It's hard to tell
     * whether it's working if there's nothing to use it on. Fortunately, your ship's drone system can be configured to
     * deploy a drone to specific coordinates and then check whether it's being pulled. There's even an Intcode program
     * (your puzzle input) that gives you access to the drone system.
     *
     * The program uses two input instructions to request the X and Y position to which the drone should be deployed.
     * Negative numbers are invalid and will confuse the drone; all numbers should be zero or positive.
     *
     * Then, the program will output whether the drone is stationary (0) or being pulled by something (1). For example,
     * the coordinate X=0, Y=0 is directly in front of the tractor beam emitter, so the drone control program will always
     * report 1 at that location.
     *
     * To better understand the tractor beam, it is important to get a good picture of the beam itself. For example,
     * suppose you scan the 10x10 grid of points closest to the emitter:
     *
     * .......X
     * ..0->      9
     * .0#.........
     * .|.#........
     * .v..##......
     * . ...###....
     * . ....###...
     * Y .....####.
     * . ......####
     * . ......####
     * . .......###
     * .9........##
     *
     * In this example, the number of points affected by the tractor beam in the 10x10 area closest to the emitter is 27.
     *
     * However, you'll need to scan a larger area to understand the shape of the beam. How many points are affected by
     * the tractor beam in the 50x50 area closest to the emitter? (For each of X and Y, this will be 0 through 49.)
     */
    static long countPartOne(String line) {
        long count = 0;

        Map2D map = new Map2D();
        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                if (tractorBeam(line, x, y)) {
                    count++;
                    map.put(Point2D.of(x, y), 1L);
                } else {
                    map.put(Point2D.of(x, y), 0L);
                }
            }
        }

        map.print(i -> i == 1 ? 'X' : '.');
        return count;
    }

    /**
     * --- Part Two ---
     * You aren't sure how large Santa's ship is. You aren't even sure if you'll need to use this thing on Santa's ship,
     * but it doesn't hurt to be prepared. You figure Santa's ship might fit in a 100x100 square.
     *
     * The beam gets wider as it travels away from the emitter; you'll need to be a minimum distance away to fit a square
     * of that size into the beam fully. (Don't rotate the square; it should be aligned to the same axes as the drone
     * grid.)
     *
     * For example, suppose you have the following tractor beam readings:
     *
     * #.......................................
     * .#......................................
     * ..##....................................
     * ...###..................................
     * ....###.................................
     * .....####...............................
     * ......#####.............................
     * ......######............................
     * .......#######..........................
     * ........########........................
     * .........#########......................
     * ..........#########.....................
     * ...........##########...................
     * ...........############.................
     * ............############................
     * .............#############..............
     * ..............##############............
     * ...............###############..........
     * ................###############.........
     * ................#################.......
     * .................########OOOOOOOOOO.....
     * ..................#######OOOOOOOOOO#....
     * ...................######OOOOOOOOOO###..
     * ....................#####OOOOOOOOOO#####
     * .....................####OOOOOOOOOO#####
     * .....................####OOOOOOOOOO#####
     * ......................###OOOOOOOOOO#####
     * .......................##OOOOOOOOOO#####
     * ........................#OOOOOOOOOO#####
     * .........................OOOOOOOOOO#####
     * ..........................##############
     * ..........................##############
     * ...........................#############
     * ............................############
     * .............................###########
     * In this example, the 10x10 square closest to the emitter that fits entirely within the tractor beam has been
     * marked O. Within it, the point closest to the emitter (the only highlighted O) is at X=25, Y=20.
     *
     * Find the 100x100 square closest to the emitter that fits entirely within the tractor beam; within that square,
     * find the point closest to the emitter. What value do you get if you take that point's X coordinate, multiply it
     * by 10000, then add the point's Y coordinate? (In the example above, this would be 250020.)
     */
    static long countPartTwo(String line) {
        Point2D position = findSquare(line, 100, 30); //y must be at least 100 to fit 100x100 square
        position = findSquare(line, position.y() - 30, 1);
        return position.x() * 10000L + position.y();
    }
}
