package com.adventofcode.year2019;

import com.adventofcode.common.point.Point2D;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.doubles.Double2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.doubles.Double2ObjectMap;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public final class Day10 {
    private static int doubleCompare(double a, double b) {
        if (Math.abs(a - b) < 0.000001) {
            return 0;
        }

        return a > b ? 1 : -1;
    }

    /**
     * --- Day 10: Monitoring Station ---
     * You fly into the asteroid belt and reach the Ceres monitoring station. The Elves here have an emergency: they're
     * having trouble tracking all of the asteroids and can't be sure they're safe.
     * <p>
     * The Elves would like to build a new monitoring station in a nearby area of space; they hand you a map of all of
     * the asteroids in that region (your puzzle input).
     * <p>
     * The map indicates whether each position is empty (.) or contains an asteroid (#). The asteroids are much smaller
     * than they appear on the map, and every asteroid is exactly in the center of its marked position. The asteroids
     * can be described with X,Y coordinates where X is the distance from the left edge and Y is the distance from the
     * top edge (so the top-left corner is 0,0 and the position immediately to its right is 1,0).
     * <p>
     * Your job is to figure out which asteroid would be the best place to build a new monitoring station. A monitoring
     * station can detect any asteroid to which it has direct line of sight - that is, there cannot be another asteroid
     * exactly between them. This line of sight can be at any angle, not just lines aligned to the grid or diagonally.
     * The best location is the asteroid that can detect the largest number of other asteroids.
     * <p>
     * For example, consider the following map:
     * <p>
     * .#..#
     * .....
     * #####
     * ....#
     * ...##
     * The best location for a new monitoring station on this map is the highlighted asteroid at 3,4 because it can
     * detect 8 asteroids, more than any other location. (The only asteroid it cannot detect is the one at 1,0; its view
     * of this asteroid is blocked by the asteroid at 2,2.) All other asteroids are worse locations; they can detect 7
     * or fewer other asteroids. Here is the number of other asteroids a monitoring station on each asteroid could
     * detect:
     * <p>
     * .7..7
     * .....
     * 67775
     * ....7
     * ...87
     * Here is an asteroid (#) and some examples of the ways its line of sight might be blocked. If there were another
     * asteroid at the location of a capital letter, the locations marked with the corresponding lowercase letter would
     * be blocked and could not be detected:
     * <p>
     * #.........
     * ...A......
     * ...B..a...
     * .EDCG....a
     * ..F.c.b...
     * .....c....
     * ..efd.c.gb
     * .......c..
     * ....f...c.
     * ...e..d..c
     * Here are some larger examples:
     * <p>
     * Best is 5,8 with 33 other asteroids detected:
     * <p>
     * ......#.#.
     * #..#.#....
     * ..#######.
     * .#.#.###..
     * .#..#.....
     * ..#....#.#
     * #..#....#.
     * .##.#..###
     * ##...#..#.
     * .#....####
     * Best is 1,2 with 35 other asteroids detected:
     * <p>
     * #.#...#.#.
     * .###....#.
     * .#....#...
     * ##.#.#.#.#
     * ....#.#.#.
     * .##..###.#
     * ..#...##..
     * ..##....##
     * ......#...
     * .####.###.
     * Best is 6,3 with 41 other asteroids detected:
     * <p>
     * .#..#..###
     * ####.###.#
     * ....###.#.
     * ..###.##.#
     * ##.##.#.#.
     * ....###..#
     * ..#.#..#.#
     * #..#.#.###
     * .##...##.#
     * .....#.#..
     * Best is 11,13 with 210 other asteroids detected:
     * <p>
     * .#..##.###...#######
     * ##.############..##.
     * .#.######.########.#
     * .###.#######.####.#.
     * #####.##.#.##.###.##
     * ..#####..#.#########
     * ####################
     * #.####....###.#.#.##
     * ##.#################
     * #####.##.###..####..
     * ..######..##.#######
     * ####.##.####...##..#
     * .#####..#.######.###
     * ##...#.##########...
     * #.##########.#######
     * .####.#.###.###.#.##
     * ....##.##.###..#####
     * .#.#.###########.###
     * #.#.#.#####.####.###
     * ###.##.####.##.#..##
     * Find the best location for a new monitoring station. How many other asteroids can be detected from that location?
     */
    public static Pair<Point2D, Double2ObjectMap<List<Asteroids>>> findBestLocation(Scanner scanner) {
        List<Point2D> asteroids = new ArrayList<>();
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (int j = 0, lineSize = line.length(); j < lineSize; j++) {
                if (line.charAt(j) == '#') {
                    asteroids.add(Point2D.of(j, i));
                }
            }
            ++i;
        }

        Point2D bestLocation = null;
        Double2ObjectMap<List<Asteroids>> bestBeamView = null;
        int count = 0;
        for (Point2D location : asteroids) {
            Double2ObjectMap<List<Asteroids>> beamView = new Double2ObjectAVLTreeMap<>(Day10::doubleCompare);
            for (Point2D asteroid : asteroids) {
                if (!asteroid.equals(location)) {
                    long dx = (long) location.x() - asteroid.x();
                    long dy = (long) location.y() - asteroid.y();
                    double theta = (Math.atan2(-dx, dy) + 2 * Math.PI) % (Math.PI * 2);
                    double r = Math.sqrt((double) dx * dx + dy * dy);
                    Asteroids asteroids1 = new Asteroids(r, asteroid);
                    beamView.computeIfAbsent(theta, ignore -> Lists.newArrayList()).add(asteroids1);
                }
            }

            if (beamView.size() > count) {
                count = beamView.size();
                bestLocation = location;
                bestBeamView = beamView;
            }
        }

        if (bestBeamView != null) {
            for (Double2ObjectMap.Entry<List<Asteroids>> entry : bestBeamView.double2ObjectEntrySet()) {
                entry.getValue().sort(Comparator.comparingDouble(Asteroids::distance));
            }
        }

        return Pair.of(bestLocation, bestBeamView);
    }

    /**
     * --- Part Two ---
     * Once you give them the coordinates, the Elves quickly deploy an Instant Monitoring Station to the location and
     * discover the worst: there are simply too many asteroids.
     * <p>
     * The only solution is complete vaporization by giant laser.
     * <p>
     * Fortunately, in addition to an asteroid scanner, the new monitoring station also comes equipped with a giant
     * rotating laser perfect for vaporizing asteroids. The laser starts by pointing up and always rotates clockwise,
     * vaporizing any asteroid it hits.
     * <p>
     * If multiple asteroids are exactly in line with the station, the laser only has enough power to vaporize one of
     * them before continuing its rotation. In other words, the same asteroids that can be detected can be vaporized,
     * but if vaporizing one asteroid makes another one detectable, the newly-detected asteroid won't be vaporized until
     * the laser has returned to the same position by rotating a full 360 degrees.
     * <p>
     * For example, consider the following map, where the asteroid with the new monitoring station (and laser) is marked
     * X:
     * <p>
     * .#....#####...#..
     * ##...##.#####..##
     * ##...#...#.#####.
     * ..#.....X...###..
     * ..#.#.....#....##
     * The first nine asteroids to get vaporized, in order, would be:
     * <p>
     * .#....###24...#..
     * ##...##.13#67..9#
     * ##...#...5.8####.
     * ..#.....X...###..
     * ..#.#.....#....##
     * Note that some asteroids (the ones behind the asteroids marked 1, 5, and 7) won't have a chance to be vaporized
     * until the next full rotation. The laser continues rotating; the next nine to be vaporized are:
     * <p>
     * .#....###.....#..
     * ##...##...#.....#
     * ##...#......1234.
     * ..#.....X...5##..
     * ..#.9.....8....76
     * The next nine to be vaporized are then:
     * <p>
     * .8....###.....#..
     * 56...9#...#.....#
     * 34...7...........
     * ..2.....X....##..
     * ..1..............
     * Finally, the laser completes its first full rotation (1 through 3), a second rotation (4 through 8), and
     * vaporizes the last asteroid (9) partway through its third rotation:
     * <p>
     * ......234.....6..
     * ......1...5.....7
     * .................
     * ........X....89..
     * .................
     * In the large example above (the one with the best monitoring station location at 11,13):
     * <p>
     * The 1st asteroid to be vaporized is at 11,12.
     * The 2nd asteroid to be vaporized is at 12,1.
     * The 3rd asteroid to be vaporized is at 12,2.
     * The 10th asteroid to be vaporized is at 12,8.
     * The 20th asteroid to be vaporized is at 16,0.
     * The 50th asteroid to be vaporized is at 16,9.
     * The 100th asteroid to be vaporized is at 10,16.
     * The 199th asteroid to be vaporized is at 9,6.
     * The 200th asteroid to be vaporized is at 8,2.
     * The 201st asteroid to be vaporized is at 10,9.
     * The 299th and final asteroid to be vaporized is at 11,1.
     * <p>
     * The Elves are placing bets on which will be the 200th asteroid to be vaporized. Win the bet by determining which
     * asteroid that will be; what do you get if you multiply its X coordinate by 100 and then add its Y coordinate? (For example, 8,2 becomes 802.)
     */
    public static List<Asteroids> vaporizeAsteroids(Double2ObjectMap<List<Asteroids>> beamView) {
        List<Asteroids> result = new ArrayList<>();
        while (!beamView.isEmpty()) {
            DoubleList keys = new DoubleArrayList();
            for (Double2ObjectMap.Entry<List<Asteroids>> entry : beamView.double2ObjectEntrySet()) {
                List<Asteroids> asteroids = entry.getValue();
                result.add(asteroids.removeFirst());
                if (asteroids.isEmpty()) {
                    keys.add(entry.getDoubleKey());
                }
            }
            keys.forEach(beamView::remove);
        }

        return result;
    }

    public record Asteroids(double distance, Point2D position) {
        public long getCoordinate() {
            return position.x() * 100L + position.y();
        }

        @Override
        public String toString() {
            return "A{" +
                   "d=" + distance +
                   ", p=" + position +
                   '}';
        }
    }
}
