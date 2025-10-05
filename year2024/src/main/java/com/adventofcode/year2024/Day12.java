package com.adventofcode.year2024;

import com.adventofcode.common.graph.Connectivity;
import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public final class Day12 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day12.class);

    private static final List<List<Point2D>> CORNER_POINTS = List.of(
            List.of(Point2D.of(0, 0), Point2D.of(1, 0), Point2D.of(1, 1), Point2D.of(0, 1)),
            List.of(Point2D.of(0, 0), Point2D.of(1, 0), Point2D.of(1, -1), Point2D.of(0, -1)),
            List.of(Point2D.of(0, 0), Point2D.of(-1, 0), Point2D.of(-1, 1), Point2D.of(0, 1)),
            List.of(Point2D.of(0, 0), Point2D.of(-1, 0), Point2D.of(-1, -1), Point2D.of(0, -1))
    );

    private Day12() {
        // No-Op
    }

    private static Map<Point2D, Set<Point2D>> findRegions(CharMap garden) {
        Map<Point2D, List<Point2D>> graph = new HashMap<>();
        for (ObjectCharPair<Point2D> entry : garden.entries()) {
            char plant = entry.rightChar();
            Point2D current = entry.left();
            List<Point2D> neighbors = Arrays.stream(Direction.values()).map(current::move).filter(move -> garden.get(move) == plant).toList();
            graph.put(current, neighbors);
        }

        return Connectivity.findRegion(garden.points(), graph);
    }

    /**
     * --- Day 12: Garden Groups ---
     * <p>
     * Why not search for the Chief Historian near the gardener and his
     * massive farm? There's plenty of food, so The Historians grab something to
     * eat while they search.
     * <p>
     * You're about to settle near a complex arrangement of garden plots when some
     * Elves ask if you can lend a hand. They'd like to set up fences around each
     * region of garden plots, but they can't figure out how much fence they need
     * to order or how much it will cost. They hand you a map (your puzzle input)
     * of the garden plots.
     * <p>
     * Each garden plot grows only a single type of plant and is indicated by a
     * single letter on your map. When multiple garden plots are growing the same
     * type of plant and are touching (horizontally or vertically), they form a
     * region. For example:
     * <p>
     * AAAA
     * BBCD
     * BBCC
     * EEEC
     * <p>
     * This 4x4 arrangement includes garden plots growing five different types of
     * plants (labeled A, B, C, D, and E), each grouped into their own region.
     * <p>
     * In order to accurately calculate the cost of the fence around a single
     * region, you need to know that region's area and perimeter.
     * <p>
     * The area of a region is simply the number of garden plots the region
     * contains. The above map's type A, B, and C plants are each in a region of
     * area 4. The type E plants are in a region of area 3; the type D plants are
     * in a region of area 1.
     * <p>
     * Each garden plot is a square and so has four sides. The perimeter of a
     * region is the number of sides of garden plots in the region that do not
     * touch another garden plot in the same region. The type A and C plants are
     * each in a region with perimeter 10. The type B and E plants are each in a
     * region with perimeter 8. The lone D plot forms its own region with
     * perimeter 4.
     * <p>
     * Visually indicating the sides of plots in each region that contribute to
     * the perimeter using - and |, the above map's regions' perimeters are
     * measured as follows:
     * <p>
     * +-+-+-+-+
     * |A A A A|
     * +-+-+-+-+     +-+
     * |D|
     * +-+-+   +-+   +-+
     * |B B|   |C|
     * +   +   + +-+
     * |B B|   |C C|
     * +-+-+   +-+ +
     * |C|
     * +-+-+-+   +-+
     * |E E E|
     * +-+-+-+
     * <p>
     * Plants of the same type can appear in multiple separate regions, and
     * regions can even appear within other regions. For example:
     * <p>
     * OOOOO
     * OXOXO
     * OOOOO
     * OXOXO
     * OOOOO
     * <p>
     * The above map contains five regions, one containing all of the O garden
     * plots, and the other four each containing a single X plot.
     * <p>
     * The four X regions each have area 1 and perimeter 4. The region containing
     * 21 type O plants is more complicated; in addition to its outer edge
     * contributing a perimeter of 20, its boundary with each X region contributes
     * an additional 4 to its perimeter, for a total perimeter of 36.
     * <p>
     * Due to "modern" business practices, the price of fence required for a
     * region is found by multiplying that region's area by its perimeter. The
     * total price of fencing all regions on a map is found by adding together the
     * price of fence for every region on the map.
     * <p>
     * In the first example, region A has price 4 * 10 = 40, region B has price
     * 4 * 8 = 32, region C has price 4 * 10 = 40, region D has price 1 * 4 = 4,
     * and region E has price 3 * 8 = 24. So, the total price for the first
     * example is 140.
     * <p>
     * In the second example, the region with all of the O plants has price
     * 21 * 36 = 756, and each of the four smaller X regions has price 1 * 4 = 4,
     * for a total price of 772 (756 + 4 + 4 + 4 + 4).
     * <p>
     * Here's a larger example:
     * <p>
     * RRRRIICCFF
     * RRRRIICCCF
     * VVRRRCCFFF
     * VVRCCCJFFF
     * VVVVCJJCFE
     * VVIVCCJJEE
     * VVIIICJJEE
     * MIIIIIJJEE
     * MIIISIJEEE
     * MMMISSJEEE
     * <p>
     * It contains:
     * <p>
     * - A region of R plants with price 12 * 18 = 216.
     * - A region of I plants with price 4 * 8 = 32.
     * - A region of C plants with price 14 * 28 = 392.
     * - A region of F plants with price 10 * 18 = 180.
     * - A region of V plants with price 13 * 20 = 260.
     * - A region of J plants with price 11 * 20 = 220.
     * - A region of C plants with price 1 * 4 = 4.
     * - A region of E plants with price 13 * 18 = 234.
     * - A region of I plants with price 14 * 22 = 308.
     * - A region of M plants with price 5 * 12 = 60.
     * - A region of S plants with price 3 * 8 = 24.
     * <p>
     * So, it has a total price of 1930.
     * <p>
     * What is the total price of fencing all regions on your map?
     */
    public static long partOne(Scanner scanner) {
        CharMap garden = CharMap.read(scanner, _ -> true);

        Map<Point2D, Set<Point2D>> regions = findRegions(garden);

        long price = 0;

        for (Map.Entry<Point2D, Set<Point2D>> entry : regions.entrySet()) {
            char plant = garden.get(entry.getKey());
            int area = entry.getValue().size();
            int perimeter = 0;
            for (Point2D point : entry.getValue()) {
                for (Direction direction : Direction.values()) {
                    Point2D move = point.move(direction);
                    if (!entry.getValue().contains(move)) {
                        ++perimeter;
                    }
                }
            }

            LOGGER.info("A region of {} plants with price {} * {} = {}.", plant, area, perimeter, area * perimeter);
            price += (long) area * perimeter;
        }

        return price;
    }

    /**
     * --- Part Two ---
     * <p>
     * Fortunately, the Elves are trying to order so much fence that they qualify
     * for a bulk discount!
     * <p>
     * Under the bulk discount, instead of using the perimeter to calculate the
     * price, you need to use the number of sides each region has. Each straight
     * section of fence counts as a side, regardless of how long it is.
     * <p>
     * Consider this example again:
     * <p>
     * AAAA
     * BBCD
     * BBCC
     * EEEC
     * <p>
     * The region containing type A plants has 4 sides, as does each of the
     * regions containing plants of type B, D, and E. However, the more complex
     * region containing the plants of type C has 8 sides!
     * <p>
     * Using the new method of calculating the per-region price by multiplying the
     * region's area by its number of sides, regions A through E have prices
     * 16, 16, 32, 4, and 12, respectively, for a total price of 80.
     * <p>
     * The second example above (full of type X and O plants) would have a total
     * price of 436.
     * <p>
     * Here's a map that includes an E-shaped region full of type E plants:
     * <p>
     * EEEEE
     * EXXXX
     * EEEEE
     * EXXXX
     * EEEEE
     * <p>
     * The E-shaped region has an area of 17 and 12 sides for a price of 204.
     * Including the two regions full of type X plants, this map has a total price
     * of 236.
     * <p>
     * This map has a total price of 368:
     * <p>
     * AAAAAA
     * AAABBA
     * AAABBA
     * ABBAAA
     * ABBAAA
     * AAAAAA
     * <p>
     * It includes two regions full of type B plants (each with 4 sides) and a
     * single region full of type A plants (with 4 sides on the outside and 8 more
     * sides on the inside, a total of 12 sides). Be especially careful when
     * counting the fence around regions like the one full of type A plants; in
     * particular, each section of fence has an in-side and an out-side, so the
     * fence does not connect across the middle of the region (where the two B
     * regions touch diagonally). (The Elves would have used the Möbius Fencing
     * Company instead, but their contract terms were too one-sided.)
     * <p>
     * The larger example from before now has the following updated prices:
     * <p>
     * A region of R plants with price 12 * 10 = 120.
     * A region of I plants with price 4 * 4 = 16.
     * A region of C plants with price 14 * 22 = 308.
     * A region of F plants with price 10 * 12 = 120.
     * A region of V plants with price 13 * 10 = 130.
     * A region of J plants with price 11 * 12 = 132.
     * A region of C plants with price 1 * 4 = 4.
     * A region of E plants with price 13 * 8 = 104.
     * A region of I plants with price 14 * 16 = 224.
     * A region of M plants with price 5 * 6 = 30.
     * A region of S plants with price 3 * 6 = 18.
     * <p>
     * Adding these together produces its new total price of 1206.
     * <p>
     * What is the new total price of fencing all regions on your map?
     */
    public static long partTwo(Scanner scanner) {
        CharMap garden = CharMap.read(scanner, _ -> true);

        Map<Point2D, Set<Point2D>> regions = findRegions(garden);

        long price = 0;

        for (Map.Entry<Point2D, Set<Point2D>> entry : regions.entrySet()) {
            char plant = garden.get(entry.getKey());
            Set<Point2D> region = entry.getValue();
            int area = region.size();

            Set<Corner> corners = new HashSet<>();

            for (Point2D point : region) {
                for (List<Point2D> cornerPoints : CORNER_POINTS) {
                    List<Point2D> fullCorner = cornerPoints.stream().map(point::move).toList();
                    List<Point2D> match = fullCorner.stream().filter(region::contains).toList();
                    int size = match.size();
                    switch (size) {
                        case 1, 3 -> corners.add(new Corner(Set.copyOf(fullCorner), 1));
                        case 2 -> {
                            if (!aligned(match.getFirst(), match.getLast())) {
                                corners.add(new Corner(Set.copyOf(fullCorner), 2));
                            }
                        }
                        default -> {
                            // No-Op
                        }
                    }
                }
            }

            int sides = corners.stream().mapToInt(Corner::weight).sum();

            LOGGER.info("A region of {} plants with price {} * {} = {}.", plant, area, sides, area * sides);
            price += (long) area * sides;
        }

        return price;
    }

    private static boolean aligned(Point2D a, Point2D b) {
        return a.x() == b.x() || a.y() == b.y();
    }

    private record Corner(Set<Point2D> points, int weight) {

    }
}
