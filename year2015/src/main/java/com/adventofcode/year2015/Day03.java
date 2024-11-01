package com.adventofcode.year2015;

import com.adventofcode.common.point.Direction;
import com.adventofcode.common.point.Point2D;

import java.util.HashSet;
import java.util.Set;

public final class Day03 {
    private Day03() {
        // No-Op
    }

    /**
     * --- Day 3: Perfectly Spherical Houses in a Vacuum ---
     * <p>
     * Santa is delivering presents to an infinite two-dimensional grid of houses.
     * <p>
     * He begins by delivering a present to the house at his starting location,
     * and then an elf at the North Pole calls him via radio and tells him where
     * to move next. Moves are always exactly one house to the north (^), south
     * (v), east (>), or west (<). After each move, he delivers another present to
     * the house at his new location.
     * <p>
     * However, the elf back at the north pole has had a little too much eggnog,
     * and so his directions are a little off, and Santa ends up visiting some
     * houses more than once. How many houses receive at least one present?
     * <p>
     * For example:
     * <p>
     * - > delivers presents to 2 houses: one at the starting location, and one
     * to the east.
     * - ^>v< delivers presents to 4 houses in a square, including twice to the
     * house at his starting/ending location.
     * - ^v^v^v^v^v delivers a bunch of presents to some very lucky children at
     * only 2 houses.
     * <p>
     * Your puzzle answer was 2565.
     */
    public static long countDeliveriesPartOne(String input) {
        Set<Point2D> houses = new HashSet<>();
        Point2D position = Point2D.of(0, 0);
        houses.add(position);
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            position = move(position, c);
            houses.add(position);
        }

        return houses.size();
    }


    /**
     * --- Part Two ---
     * <p>
     * The next year, to speed up the process, Santa creates a robot version of
     * himself, Robo-Santa, to deliver presents with him.
     * <p>
     * Santa and Robo-Santa start at the same location (delivering two presents to
     * the same starting house), then take turns moving based on instructions from
     * the elf, who is eggnoggedly reading from the same script as the previous
     * year.
     * <p>
     * This year, how many houses receive at least one present?
     * <p>
     * For example:
     * <p>
     * - ^v delivers presents to 3 houses, because Santa goes north, and then
     * Robo-Santa goes south.
     * - ^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end
     * up back where they started.
     * - ^v^v^v^v^v now delivers presents to 11 houses, with Santa going one
     * direction and Robo-Santa going the other.
     * <p>
     * Your puzzle answer was 2639.
     */
    public static long countDeliveriesPartTwo(String input) {
        Set<Point2D> houses = new HashSet<>();
        Point2D santasPosition = Point2D.of(0, 0);
        Point2D robosPosition = Point2D.of(0, 0);
        houses.add(santasPosition);
        char[] charArray = input.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (i % 2 == 0) {
                santasPosition = move(santasPosition, c);
                houses.add(santasPosition);
            } else {
                robosPosition = move(robosPosition, c);
                houses.add(robosPosition);
            }
        }

        return houses.size();
    }

    public static Point2D move(Point2D position, char c) {
        return switch (c) {
            case '^' -> position.move(Direction.UP);
            case 'v' -> position.move(Direction.DOWN);
            case '<' -> position.move(Direction.LEFT);
            case '>' -> position.move(Direction.RIGHT);
            default -> throw new IllegalStateException("Unknown direction " + c);
        };
    }
}
