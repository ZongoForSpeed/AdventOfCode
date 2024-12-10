package com.adventofcode.year2024;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;
import com.adventofcode.common.point.map.CharMap;
import it.unimi.dsi.fastutil.objects.ObjectCharPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public final class Day10 {

    private static final Logger LOGGER = LoggerFactory.getLogger(Day10.class);

    private Day10() {
        // No-Op
    }

    /**
     * --- Day 10: Hoof It ---
     * <p>
     * You all arrive at a Lava Production Facility on a floating island in the
     * sky. As the others begin to search the massive industrial complex, you feel
     * a small nose boop your leg and look down to discover a reindeer wearing a
     * hard hat.
     * <p>
     * The reindeer is holding a book titled "Lava Island Hiking Guide". However,
     * when you open the book, you discover that most of it seems to have been
     * scorched by lava! As you're about to ask how you can help, the reindeer
     * brings you a blank topographic map of the surrounding area (your puzzle
     * input) and looks up at you excitedly.
     * <p>
     * Perhaps you can help fill in the missing hiking trails?
     * <p>
     * The topographic map indicates the height at each position using a scale
     * from 0 (lowest) to 9 (highest). For example:
     * <p>
     * 0123
     * 1234
     * 8765
     * 9876
     * <p>
     * Based on un-scorched scraps of the book, you determine that a good hiking
     * trail is as long as possible and has an even, gradual, uphill slope. For
     * all practical purposes, this means that a hiking trail is any path that
     * starts at height 0, ends at height 9, and always increases by a height of
     * exactly 1 at each step. Hiking trails never include diagonal steps - only
     * up, down, left, or right (from the perspective of the map).
     * <p>
     * You look up from the map and notice that the reindeer has helpfully begun
     * to construct a small pile of pencils, markers, rulers, compasses, stickers,
     * and other equipment you might need to update the map with hiking trails.
     * <p>
     * A trailhead is any position that starts one or more hiking trails - here,
     * these positions will always have height 0. Assembling more fragments of
     * pages, you establish that a trailhead's score is the number of 9-height
     * positions reachable from that trailhead via a hiking trail. In the above
     * example, the single trailhead in the top left corner has a score of 1
     * because it can reach a single 9 (the one in the bottom left).
     * <p>
     * This trailhead has a score of 2:
     * <p>
     * ...0...
     * ...1...
     * ...2...
     * 6543456
     * 7.....7
     * 8.....8
     * 9.....9
     * <p>
     * (The positions marked . are impassable tiles to simplify these examples;
     * they do not appear on your actual topographic map.)
     * <p>
     * This trailhead has a score of 4 because every 9 is reachable via a hiking
     * trail except the one immediately to the left of the trailhead:
     * <p>
     * ..90..9
     * ...1.98
     * ...2..7
     * 6543456
     * 765.987
     * 876....
     * 987....
     * <p>
     * This topographic map contains two trailheads; the trailhead at the top has
     * a score of 1, while the trailhead at the bottom has a score of 2:
     * <p>
     * 10..9..
     * 2...8..
     * 3...7..
     * 4567654
     * ...8..3
     * ...9..2
     * .....01
     * <p>
     * Here's a larger example:
     * <p>
     * 89010123
     * 78121874
     * 87430965
     * 96549874
     * 45678903
     * 32019012
     * 01329801
     * 10456732
     * <p>
     * This larger example has 9 trailheads. Considering the trailheads in reading
     * order, they have scores of 5, 6, 5, 3, 1, 3, 5, 3, and 5. Adding these
     * scores together, the sum of the scores of all trailheads is 36.
     * <p>
     * The reindeer gleefully carries over a protractor and adds it to the pile.
     * What is the sum of the scores of all trailheads on your topographic map?
     */
    public static int partOne(Scanner scanner) {
        CharMap map = CharMap.read(scanner, ignore -> true);

        Map<Trail, Set<Point2D>> trailheads = new HashMap<>();

        PriorityQueue<Trail> queue = new PriorityQueue<>(Comparator.comparingInt(Trail::height));

        for (ObjectCharPair<Point2D> entry : map.entries()) {
            if (entry.rightChar() == '0') {
                Trail trail = new Trail(entry.left(), 0);
                queue.add(trail);
                trailheads.put(trail, Set.of(entry.left()));
            }
        }

        Set<Trail> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Trail trail = queue.poll();
            Point2D position = trail.position();
            Set<Point2D> path = trailheads.get(trail);
            if (path == null) {
                throw new IllegalStateException("Cannot find path length for " + trail);
            }

            for (Direction direction : Direction.values()) {
                Point2D move = position.move(direction);
                int c = map.get(move) - '0';
                if (c == trail.height() + 1) {
                    Trail newTrail = new Trail(move, c);
                    trailheads.computeIfAbsent(newTrail, ignore -> new HashSet<>()).addAll(path);
                    if (visited.add(newTrail)) {
                        queue.add(newTrail);
                    }
                }
            }
        }

        LOGGER.trace("trailheads = {}", trailheads);

        return trailheads.entrySet().stream()
                .filter(e -> e.getKey().height() == 9)
                .map(Map.Entry::getValue)
                .mapToInt(Set::size)
                .reduce(0, Integer::sum);
    }

    /**
     * --- Part Two ---
     * <p>
     * The reindeer spends a few minutes reviewing your hiking trail map before
     * realizing something, disappearing for a few minutes, and finally returning
     * with yet another slightly-charred piece of paper.
     * <p>
     * The paper describes a second way to measure a trailhead called its rating.
     * A trailhead's rating is the number of distinct hiking trails which begin at
     * that trailhead. For example:
     * <p>
     * .....0.
     * ..4321.
     * ..5..2.
     * ..6543.
     * ..7..4.
     * ..8765.
     * ..9....
     * <p>
     * The above map has a single trailhead; its rating is 3 because there are
     * exactly three distinct hiking trails which begin at that position:
     * <p>
     * .....0.   .....0.   .....0.
     * ..4321.   .....1.   .....1.
     * ..5....   .....2.   .....2.
     * ..6....   ..6543.   .....3.
     * ..7....   ..7....   .....4.
     * ..8....   ..8....   ..8765.
     * ..9....   ..9....   ..9....
     * <p>
     * Here is a map containing a single trailhead with rating 13:
     * <p>
     * ..90..9
     * ...1.98
     * ...2..7
     * 6543456
     * 765.987
     * 876....
     * 987....
     * <p>
     * This map contains a single trailhead with rating 227 (because there are 121
     * distinct hiking trails that lead to the 9 on the right edge and 106 that
     * lead to the 9 on the bottom edge):
     * <p>
     * 012345
     * 123456
     * 234567
     * 345678
     * 4.6789
     * 56789.
     * <p>
     * Here's the larger example from before:
     * <p>
     * 89010123
     * 78121874
     * 87430965
     * 96549874
     * 45678903
     * 32019012
     * 01329801
     * 10456732
     * <p>
     * Considering its trailheads in reading order, they have ratings of 20, 24,
     * 10, 4, 1, 4, 5, 8, and 5. The sum of all trailhead ratings in this larger
     * example topographic map is 81.
     * <p>
     * You're not sure how, but the reindeer seems to have crafted some tiny flags
     * out of toothpicks and bits of paper and is using them to mark trailheads on
     * your topographic map. What is the sum of the ratings of all trailheads?
     */
    public static int partTwo(Scanner scanner) {
        CharMap map = CharMap.read(scanner, ignore -> true);

        Map<Trail, Integer> trailheads = new HashMap<>();

        PriorityQueue<Trail> queue = new PriorityQueue<>(Comparator.comparingInt(Trail::height));

        for (ObjectCharPair<Point2D> entry : map.entries()) {
            if (entry.rightChar() == '0') {
                Trail trail = new Trail(entry.left(), 0);
                queue.add(trail);
                trailheads.put(trail, 1);
            }
        }

        Set<Trail> visited = new HashSet<>();

        while (!queue.isEmpty()) {
            Trail trail = queue.poll();
            Point2D position = trail.position();
            Integer path = trailheads.get(trail);
            if (path == null) {
                throw new IllegalStateException("Cannot find path length for " + trail);
            }

            for (Direction direction : Direction.values()) {
                Point2D move = position.move(direction);
                int c = map.get(move) - '0';
                if (c == trail.height() + 1) {
                    Trail newTrail = new Trail(move, c);
                    trailheads.merge(newTrail, path, Integer::sum);
                    if (visited.add(newTrail)) {
                        queue.add(newTrail);
                    }
                }
            }
        }

        LOGGER.trace("trailheads = {}", trailheads);

        return trailheads.entrySet().stream()
                .filter(e -> e.getKey().height() == 9)
                .mapToInt(Map.Entry::getValue)
                .reduce(0, Integer::sum);
    }

    record Trail(Point2D position, int height) {
    }

}
