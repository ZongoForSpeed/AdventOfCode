package com.adventofcode.year2025;

import com.adventofcode.common.graph.DisjointSet;
import com.adventofcode.common.point.Point3D;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.OptionalLong;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day08 {

    private static final Pattern PATTERN = Pattern.compile("(\\d+),(\\d+),(\\d+)");

    /**
     * --- Day 8: Playground ---
     * <p>
     * Equipped with a new understanding of teleporter maintenance, you
     * confidently step onto the repaired teleporter pad.
     * <p>
     * You rematerialize on an unfamiliar teleporter pad and find yourself in a
     * vast underground space which contains a giant playground!
     * <p>
     * Across the playground, a group of Elves are working on setting up an
     * ambitious Christmas decoration project. Through careful rigging, they have
     * suspended a large number of small electrical junction boxes.
     * <p>
     * Their plan is to connect the junction boxes with long strings of lights.
     * Most of the junction boxes don't provide electricity; however, when two
     * junction boxes are connected by a string of lights, electricity can pass
     * between those two junction boxes.
     * <p>
     * The Elves are trying to figure out which junction boxes to connect so that
     * electricity can reach every junction box. They even have a list of all of
     * the junction boxes' positions in 3D space (your puzzle input).
     * <p>
     * For example:
     * <p>
     * 162,817,812
     * 57,618,57
     * 906,360,560
     * 592,479,940
     * 352,342,300
     * 466,668,158
     * 542,29,236
     * 431,825,988
     * 739,650,466
     * 52,470,668
     * 216,146,977
     * 819,987,18
     * 117,168,530
     * 805,96,715
     * 346,949,466
     * 970,615,88
     * 941,993,340
     * 862,61,35
     * 984,92,344
     * 425,690,689
     * <p>
     * This list describes the position of 20 junction boxes, one per line. Each
     * position is given as X,Y,Z coordinates. So, the first junction box in the
     * list is at X=162, Y=817, Z=812.
     * <p>
     * To save on string lights, the Elves would like to focus on connecting pairs
     * of junction boxes that are as close together as possible according to
     * straight-line distance. In this example, the two junction boxes which are
     * closest together are 162,817,812 and 425,690,689.
     * <p>
     * By connecting these two junction boxes together, because electricity can
     * flow between them, they become part of the same circuit. After connecting
     * them, there is a single circuit which contains two junction boxes, and the
     * remaining 18 junction boxes remain in their own individual circuits.
     * <p>
     * Now, the two junction boxes which are closest together but aren't already
     * directly connected are 162,817,812 and 431,825,988. After connecting them,
     * since 162,817,812 is already connected to another junction box, there is
     * now a single circuit which contains three junction boxes and an additional
     * 17 circuits which contain one junction box each.
     * <p>
     * The next two junction boxes to connect are 906,360,560 and 805,96,715.
     * After connecting them, there is a circuit containing 3 junction boxes, a
     * circuit containing 2 junction boxes, and 15 circuits which contain one
     * junction box each.
     * <p>
     * The next two junction boxes are 431,825,988 and 425,690,689. Because these
     * two junction boxes were already in the same circuit, nothing happens!
     * <p>
     * This process continues for a while, and the Elves are concerned that they
     * don't have enough extension cables for all these circuits. They would like
     * to know how big the circuits will be.
     * <p>
     * After making the ten shortest connections, there are 11 circuits: one
     * circuit which contains 5 junction boxes, one circuit which contains 4
     * junction boxes, two circuits which contain 2 junction boxes each, and seven
     * circuits which each contain a single junction box. Multiplying together the
     * sizes of the three largest circuits (5, 4, and one of the circuits of size
     * 2) produces 40.
     * <p>
     * Your list contains many junction boxes; connect together the 1000 pairs of
     * junction boxes which are closest together. Afterward, what do you get if
     * you multiply together the sizes of the three largest circuits?
     * <p>
     * --- Part Two ---
     * <p>
     * The Elves were right; they definitely don't have enough extension cables.
     * You'll need to keep connecting junction boxes together until they're all in
     * one large circuit.
     * <p>
     * Continuing the above example, the first connection which causes all of the
     * junction boxes to form a single circuit is between the junction boxes at
     * 216,146,977 and 117,168,530. The Elves need to know how far those junction
     * boxes are from the wall so they can pick the right extension cable;
     * multiplying the X coordinates of those two junction boxes (216 and 117)
     * produces 25272.
     * <p>
     * Continue connecting the closest unconnected pairs of junction boxes
     * together until they're all in the same circuit. What do you get if you
     * multiply together the X coordinates of the last two junction boxes you need Your puzzle answer was 131150.
     * to connect?
     */
    private Day08() {
    }

    public static long partOne(Scanner scanner, int limit) {
        List<Point3D> boxes = readInput(scanner);
        List<Distance> distances = computeDistances(boxes);

        DisjointSet disjointSet = new DisjointSet(boxes.size());
        int count = 0;

        for (Distance d : distances) {
            var _ = disjointSet.union(d.p1, d.p2);
            count++;
            if (count == limit) {
                break;
            }
        }

        IntList sizes = disjointSet.getSizes();
        sizes.sort(IntComparators.OPPOSITE_COMPARATOR);
        long total = 1;
        for (int i = 0; i < 3 && i < sizes.size(); i++) {
            total *= sizes.getInt(i);
        }
        return total;
    }

    public static OptionalLong partTwo(Scanner scanner) {
        List<Point3D> boxes = readInput(scanner);
        List<Distance> distances = computeDistances(boxes);

        DisjointSet disjointSet = new DisjointSet(boxes.size());

        for (Distance d : distances) {
            if (disjointSet.union(d.p1, d.p2) && disjointSet.getCount() == 1) {
                Point3D box1 = boxes.get(d.p1);
                Point3D box2 = boxes.get(d.p2);
                return OptionalLong.of((long) box1.x() * box2.x());
            }
        }

        return OptionalLong.empty();
    }

    private static List<Point3D> readInput(Scanner scanner) {
        List<Point3D> boxes = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                boxes.add(new Point3D(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3))
                ));
            }
        }
        return boxes;
    }

    private static List<Distance> computeDistances(List<Point3D> boxes) {
        List<Distance> distances = new ArrayList<>();
        for (int i = 0; i < boxes.size(); i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                distances.add(new Distance(i, j, Point3D.distance(boxes.get(i), boxes.get(j))));
            }
        }
        distances.sort(Comparator.comparingDouble(Distance::d));
        return distances;
    }

    private record Distance(int p1, int p2, double d) {
    }

}
