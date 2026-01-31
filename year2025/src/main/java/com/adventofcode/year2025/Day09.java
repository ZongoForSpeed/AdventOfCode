package com.adventofcode.year2025;

import com.adventofcode.common.point.Point2D;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Day09 {

    /**
     * --- Day 9: Movie Theater ---
     * <p>
     * You slide down the firepole in the corner of the playground and land in the
     * North Pole base movie theater!
     * <p>
     * The movie theater has a big tile floor with an interesting pattern. Elves
     * here are redecorating the theater by switching out some of the square tiles
     * in the big grid they form. Some of the tiles are red; the Elves would like
     * to find the largest rectangle that uses red tiles for two of its opposite
     * corners. They even have a list of where the red tiles are located in the
     * grid (your puzzle input).
     * <p>
     * For example:
     * <p>
     * 7,1
     * 11,1
     * 11,7
     * 9,7
     * 9,5
     * 2,5
     * 2,3
     * 7,3
     * <p>
     * Showing red tiles as # and other tiles as ., the above arrangement of red
     * tiles would look like this:
     * <p>
     * ..............
     * .......#...#..
     * ..............
     * ..#....#......
     * ..............
     * ..#......#....
     * ..............
     * .........#.#..
     * ..............
     * <p>
     * You can choose any two red tiles as the opposite corners of your rectangle;
     * your goal is to find the largest rectangle possible.
     * <p>
     * For example, you could make a rectangle (shown as O) with an area of 24
     * between 2,5 and 9,7:
     * <p>
     * ..............
     * .......#...#..
     * ..............
     * ..#....#......
     * ..............
     * ..OOOOOOOO....
     * ..OOOOOOOO....
     * ..OOOOOOOO.#..
     * ..............
     * <p>
     * Or, you could make a rectangle with area 35 between 7,1 and 11,7:
     * <p>
     * ..............
     * .......OOOOO..
     * .......OOOOO..
     * ..#....OOOOO..
     * .......OOOOO..
     * ..#....OOOOO..
     * .......OOOOO..
     * .......OOOOO..
     * ..............
     * <p>
     * You could even make a thin rectangle with an area of only 6 between 7,3 and
     * 2,3:
     * <p>
     * ..............
     * .......#...#..
     * ..............
     * ..OOOOOO......
     * ..............
     * ..#......#....
     * ..............
     * .........#.#..
     * ..............
     * <p>
     * Ultimately, the largest rectangle you can make in this example has area 50.
     * One way to do this is between 2,5 and 11,1:
     * <p>
     * ..............
     * ..OOOOOOOOOO..
     * ..OOOOOOOOOO..
     * ..OOOOOOOOOO..
     * ..OOOOOOOOOO..
     * ..OOOOOOOOOO..
     * ..............
     * .........#.#..
     * ..............
     * <p>
     * Using two red tiles as opposite corners, what is the largest area of any
     * rectangle you can make?
     * <p>
     * --- Part Two ---
     * <p>
     * The Elves just remembered: they can only switch out tiles that are red or
     * green. So, your rectangle can only include red or green tiles.
     * <p>
     * In your list, every red tile is connected to the red tile before and after
     * it by a straight line of green tiles. The list wraps, so the first red tile
     * is also connected to the last red tile. Tiles that are adjacent in your
     * list will always be on either the same row or the same column.
     * <p>
     * Using the same example as before, the tiles marked X would be green:
     * <p>
     * ..............
     * .......#XXX#..
     * .......X...X..
     * ..#XXXX#...X..
     * ..X........X..
     * ..#XXXXXX#.X..
     * .........X.X..
     * .........#X#..
     * ..............
     * <p>
     * In addition, all of the tiles inside this loop of red and green tiles are
     * also green. So, in this example, these are the green tiles:
     * <p>
     * ..............
     * .......#XXX#..
     * .......XXXXX..
     * ..#XXXX#XXXX..
     * ..XXXXXXXXXX..
     * ..#XXXXXX#XX..
     * .........XXX..
     * .........#X#..
     * ..............
     * <p>
     * The remaining tiles are never red nor green.
     * <p>
     * The rectangle you choose still must have red tiles in opposite corners, but
     * any other tiles it includes must now be red or green. This significantly
     * limits your options.
     * <p>
     * For example, you could make a rectangle out of red and green tiles with an
     * area of 15 between 7,3 and 11,1:
     * <p>
     * ..............
     * .......OOOOO..
     * .......OOOOO..
     * ..#XXXXOOOOO..
     * ..XXXXXXXXXX..
     * ..#XXXXXX#XX..
     * .........XXX..
     * .........#X#..
     * ..............
     * <p>
     * Or, you could make a thin rectangle with an area of 3 between 9,7 and 9,5:
     * <p>
     * ..............
     * .......#XXX#..
     * .......XXXXX..
     * ..#XXXX#XXXX..
     * ..XXXXXXXXXX..
     * ..#XXXXXXOXX..
     * .........OXX..
     * .........OX#..
     * ..............
     * <p>
     * The largest rectangle you can make in this example using only red and green
     * tiles has area 24. One way to do this is between 9,5 and 2,3:
     * <p>
     * ..............
     * .......#XXX#..
     * .......XXXXX..
     * ..OOOOOOOOXX..
     * ..OOOOOOOOXX..
     * ..OOOOOOOOXX..
     * .........XXX..
     * .........#X#..
     * ..............
     * <p>
     * Using two red tiles as opposite corners, what is the largest area of any
     * rectangle you can make using only red and green tiles?
     */
    private Day09() {
    }

    private static final Pattern PATTERN = Pattern.compile("(\\d+),(\\d+)");

    public static long partOne(Scanner scanner) {
        List<Point2D> positions = readInput(scanner);
        long maxArea = 0;
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                Rectangle rectangle = Rectangle.from(positions.get(i), positions.get(j));
                maxArea = Math.max(maxArea, rectangle.area());
            }
        }
        return maxArea;
    }

    public static long partTwo(Scanner scanner) {
        List<Point2D> positions = readInput(scanner);
        List<Rectangle> edges = findEdges(positions);
        long maxArea = 0;
        for (int i = 0; i < positions.size(); i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                Rectangle rectangle = Rectangle.from(positions.get(i), positions.get(j));
                boolean overlaps = false;
                for (Rectangle edge : edges) {
                    if (rectangle.overlapsWith(edge)) {
                        overlaps = true;
                        break;
                    }
                }
                if (!overlaps) {
                    maxArea = Math.max(maxArea, rectangle.area());
                }
            }
        }
        return maxArea;
    }

    private static List<Rectangle> findEdges(List<Point2D> positions) {
        List<Rectangle> edges = new java.util.ArrayList<>();
        int len = positions.size();
        for (int i = 1; i < len; i++) {
            edges.add(Rectangle.from(positions.get(i - 1), positions.get(i)));
        }
        if (len > 0) {
            edges.add(Rectangle.from(positions.getFirst(), positions.getLast()));
        }
        return edges;
    }

    private static List<Point2D> readInput(Scanner scanner) {
        List<Point2D> points = new java.util.ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = PATTERN.matcher(line);
            if (matcher.matches()) {
                points.add(new Point2D(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))));
            }
        }
        return points;
    }

    private record Rectangle(int minX, int minY, int maxX, int maxY) {
        static Rectangle from(Point2D p1, Point2D p2) {
            return new Rectangle(
                    Math.min(p1.x(), p2.x()),
                    Math.min(p1.y(), p2.y()),
                    Math.max(p1.x(), p2.x()),
                    Math.max(p1.y(), p2.y())
            );
        }

        long area() {
            return (maxX - minX + 1L) * (maxY - minY + 1L);
        }

        boolean overlapsWith(Rectangle other) {
            return this.minX < other.maxX && this.maxX > other.minX &&
                    this.minY < other.maxY && this.maxY > other.minY;
        }
    }
}
