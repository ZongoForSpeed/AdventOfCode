package com.adventofcode.year2022;

import com.adventofcode.common.point.Point3D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public final class Day18 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Day18.class);

    private static final byte AIR = 0;
    private static final byte DROPLET = 1;
    private static final byte STEAM = 2;

    private static final List<Point3D> DIRECTIONS = List.of(
            Point3D.of(1, 0, 0),
            Point3D.of(-1, 0, 0),
            Point3D.of(0, 1, 0),
            Point3D.of(0, -1, 0),
            Point3D.of(0, 0, 1),
            Point3D.of(0, 0, -1)
    );

    private Day18() {
        // No-Op
    }

    /**
     * --- Day 18: Boiling Boulders ---
     *
     * You and the elephants finally reach fresh air. You've emerged near the base
     * of a large volcano that seems to be actively erupting! Fortunately, the
     * lava seems to be flowing away from you and toward the ocean.
     *
     * Bits of lava are still being ejected toward you, so you're sheltering in
     * the cavern exit a little longer. Outside the cave, you can see the lava
     * landing in a pond and hear it loudly hissing as it solidifies.
     *
     * Depending on the specific compounds in the lava and speed at which it
     * cools, it might be forming obsidian! The cooling rate should be based on
     * the surface area of the lava droplets, so you take a quick scan of a
     * droplet as it flies past you (your puzzle input).
     *
     * Because of how quickly the lava is moving, the scan isn't very good; its
     * resolution is quite low and, as a result, it approximates the shape of the
     * lava droplet with 1x1x1 cubes on a 3D grid, each given as its x,y,z
     * position.
     *
     * To approximate the surface area, count the number of sides of each cube
     * that are not immediately connected to another cube. So, if your scan were
     * only two adjacent cubes like 1,1,1 and 2,1,1, each cube would have a single
     * side covered and five sides exposed, a total surface area of 10 sides.
     *
     * Here's a larger example:
     *
     * 2,2,2
     * 1,2,2
     * 3,2,2
     * 2,1,2
     * 2,3,2
     * 2,2,1
     * 2,2,3
     * 2,2,4
     * 2,2,6
     * 1,2,5
     * 3,2,5
     * 2,1,5
     * 2,3,5
     *
     * In the above example, after counting up all the sides that aren't connected
     * to another cube, the total surface area is 64.
     *
     * What is the surface area of your scanned lava droplet?
     */
    static long computeSurfaceArea(Scanner scanner) {
        Set<Point3D> droplets = readInput(scanner);

        long surface = 0;
        for (Point3D droplet : droplets) {
            surface += DIRECTIONS.stream().map(p -> Point3D.add(droplet, p)).filter(p -> !droplets.contains(p)).count();
        }

        LOGGER.info("surface = {}", surface);
        return surface;
    }

    /**
     * --- Part Two ---
     *
     * Something seems off about your calculation. The cooling rate depends on
     * exterior surface area, but your calculation also included the surface area
     * of air pockets trapped in the lava droplet.
     *
     * Instead, consider only cube sides that could be reached by the water and
     * steam as the lava droplet tumbles into the pond. The steam will expand to
     * reach as much as possible, completely displacing any air on the outside of
     * the lava droplet but never expanding diagonally.
     *
     * In the larger example above, exactly one cube of air is trapped within the
     * lava droplet (at 2,2,5), so the exterior surface area of the lava droplet
     * is 58.
     *
     * What is the exterior surface area of your scanned lava droplet?
     */
    static long exteriorSurfaceArea(Scanner scanner) {
        Set<Point3D> droplets = readInput(scanner);

        int max = droplets.stream().flatMap(p -> Stream.of(p.x(), p.y(), p.z())).mapToInt(i -> i).max().orElseThrow() + 1;
        LOGGER.info("max = {}", max);
        byte[][][] grid = new byte[max + 1][max + 1][max + 1];
        droplets.forEach(point -> grid[point.x()][point.y()][point.z()] = DROPLET);

        Queue<Point3D> queue = new LinkedList<>();
        Set<Point3D> visited = new HashSet<>();
        queue.add(Point3D.ORIGIN);
        visited.add(Point3D.ORIGIN);

        while (!queue.isEmpty()) {
            Point3D point = queue.poll();
            grid[point.x()][point.y()][point.z()] = STEAM;

            List<Point3D> adjacents = DIRECTIONS.stream().map(d -> Point3D.add(d, point))
                    .filter(p -> valid(p, max))
                    .toList();

            for (Point3D adjacent : adjacents) {
                if (visited.add(adjacent) && grid[adjacent.x()][adjacent.y()][adjacent.z()] == AIR) {
                    queue.add(adjacent);
                }
            }
        }

        long surface = 0;
        for (Point3D droplet : droplets) {
            surface += DIRECTIONS.stream()
                    .map(p -> Point3D.add(droplet, p))
                    .filter(p -> valid(p, max))
                    .filter(p -> STEAM == grid[p.x()][p.y()][p.z()]).count();
        }

        LOGGER.info("surface = {}", surface);
        return surface;
    }

    private static boolean valid(Point3D point, int max) {
        return point.x() >= 0
               && point.y() >= 0
               && point.z() >= 0
               && point.x() <= max
               && point.y() <= max
               && point.z() <= max;
    }

    private static Set<Point3D> readInput(Scanner scanner) {
        Set<Point3D> droplets = new HashSet<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            int[] array = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
            droplets.add(new Point3D(array[0] + 1, array[1] + 1, array[2] + 1));
        }

        LOGGER.info("droplets = {}", droplets);
        return droplets;
    }
}
