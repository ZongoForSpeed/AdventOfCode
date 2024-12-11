package com.adventofcode.year2017;

import com.adventofcode.common.hash.KnotHash;
import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.BooleanMap;
import it.unimi.dsi.fastutil.booleans.BooleanList;

import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Day14 {
    private static final Map<Character, BooleanList> MAPPING = Map.ofEntries(
            Map.entry('0', BooleanList.of(false, false, false, false)),
            Map.entry('1', BooleanList.of(false, false, false, true)),
            Map.entry('2', BooleanList.of(false, false, true, false)),
            Map.entry('3', BooleanList.of(false, false, true, true)),
            Map.entry('4', BooleanList.of(false, true, false, false)),
            Map.entry('5', BooleanList.of(false, true, false, true)),
            Map.entry('6', BooleanList.of(false, true, true, false)),
            Map.entry('7', BooleanList.of(false, true, true, true)),
            Map.entry('8', BooleanList.of(true, false, false, false)),
            Map.entry('9', BooleanList.of(true, false, false, true)),
            Map.entry('a', BooleanList.of(true, false, true, false)),
            Map.entry('b', BooleanList.of(true, false, true, true)),
            Map.entry('c', BooleanList.of(true, true, false, false)),
            Map.entry('d', BooleanList.of(true, true, false, true)),
            Map.entry('e', BooleanList.of(true, true, true, false)),
            Map.entry('f', BooleanList.of(true, true, true, true))
    );

    private Day14() {
        // No-Op
    }

    static BitSet toBooleanList(String input) {
        BitSet bitSet = new BitSet();
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            BooleanList booleans = MAPPING.get(input.charAt(i));
            for (Boolean aBoolean : booleans) {
                bitSet.set(count++, aBoolean);
            }
        }
        return bitSet;
    }

    /**
     * --- Day 14: Disk Defragmentation ---
     * <p>
     * Suddenly, a scheduled job activates the system's disk defragmenter. Were
     * the situation different, you might sit and watch it for a while, but today,
     * you just don't have that kind of time. It's soaking up valuable system
     * resources that are needed elsewhere, and so the only option is to help it
     * finish its task as soon as possible.
     * <p>
     * The disk in question consists of a 128x128 grid; each square of the grid is
     * either free or used. On this disk, the state of the grid is tracked by the
     * bits in a sequence of knot hashes.
     * <p>
     * A total of 128 knot hashes are calculated, each corresponding to a single
     * row in the grid; each hash contains 128 bits which correspond to individual
     * grid squares. Each bit of a hash indicates whether that square is free (0)
     * or used (1).
     * <p>
     * The hash inputs are a key string (your puzzle input), a dash, and a number
     * from 0 to 127 corresponding to the row. For example, if your key string
     * were flqrgnkx, then the first row would be given by the bits of the knot
     * hash of flqrgnkx-0, the second row from the bits of the knot hash of
     * flqrgnkx-1, and so on until the last row, flqrgnkx-127.
     * <p>
     * The output of a knot hash is traditionally represented by 32 hexadecimal
     * digits; each of these digits correspond to 4 bits, for a total of
     * 4 * 32 = 128 bits. To convert to bits, turn each hexadecimal digit to its
     * equivalent binary value, high-bit first: 0 becomes 0000, 1 becomes 0001,
     * e becomes 1110, f becomes 1111, and so on; a hash that begins with a0c2017...
     * in hexadecimal would begin with 10100000110000100000000101110000... in
     * binary.
     * <p>
     * Continuing this process, the first 8 rows and columns for key flqrgnkx
     * appear as follows, using # to denote used squares, and . to denote free
     * ones:
     * <p>
     * ##.#.#..-->
     * .#.#.#.#
     * ....#.#.
     * #.#.##.#
     * .##.#...
     * ##..#..#
     * .#...#..
     * ##.#.##.-->
     * |      |
     * V      V
     * <p>
     * <p>
     * In this example, 8108 squares are used across the entire 128x128 grid.
     * <p>
     * Given your actual key string, how many squares are used?
     * <p>
     * Your puzzle answer was 8140.
     */
    static BooleanMap buildDisk(String input) {
        BooleanMap map = new BooleanMap(128, 128);
        for (int i = 0; i < 128; ++i) {
            BitSet bitSet = toBooleanList(KnotHash.knotHash(input + "-" + i));
            int finalI = i;
            bitSet.stream().forEach(j -> map.set(j, finalI));
        }
        return map;
    }

    private static void visitDisk(Set<Point2D> seen, BooleanMap map, Point2D current) {
        if (seen.contains(current)) {
            return;
        }

        if (!map.get(current)) {
            return;
        }

        seen.add(current);
        for (Direction value : Direction.values()) {
            Point2D move = current.move(value);
            if (move.x() >= 0 && move.x() < 128 && move.y() >= 0 && move.y() < 128) {
                visitDisk(seen, map, move);
            }
        }
    }

    /**
     * --- Part Two ---
     * <p>
     * Now, all the defragmenter needs to know is the number of regions. A region
     * is a group of used squares that are all adjacent, not including diagonals.
     * Every used square is in exactly one region: lone used squares form their
     * own isolated regions, while several adjacent squares all count as a single
     * region.
     * <p>
     * In the example above, the following nine regions are visible, each marked
     * with a distinct digit:
     * <p>
     * 11.2.3..-->
     * .1.2.3.4
     * ....5.6.
     * 7.8.55.9
     * .88.5...
     * 88..5..8
     * .8...8..
     * 88.8.88.-->
     * |      |
     * V      V
     * <p>
     * Of particular interest is the region marked 8; while it does not appear
     * contiguous in this small view, all of the squares marked 8 are connected
     * when considering the whole 128x128 grid. In total, in this example, 1242
     * regions are present.
     * <p>
     * How many regions are present given your key string?
     * Your puzzle answer was 1182.
     */
    static int countRegion(BooleanMap map) {
        List<Point2D> points = map.points().toList();
        Set<Point2D> seen = new HashSet<>();
        int count = 0;
        for (Point2D point : points) {
            if (seen.contains(point)) {
                continue;
            }
            count++;
            visitDisk(seen, map, point);
        }

        return count;
    }
}
