package com.adventofcode.year2015;

import com.adventofcode.map.Direction;
import com.adventofcode.map.Point2D;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class Day03Test {

    private static long countDeliveriesPartOne(String input) {
        Set<Point2D> houses = new HashSet<>();
        Point2D position = Point2D.of(0, 0);
        houses.add(position);
        for (char c : input.toCharArray()) {
            position = move(position, c);
            houses.add(position);
        }

        return houses.size();
    }


    private static long countDeliveriesPartTwo(String input) {
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

    private static Point2D move(Point2D position, char c) {
        return switch (c) {
            case '^' -> position.move(Direction.NORTH);
            case 'v' -> position.move(Direction.SOUTH);
            case '<' -> position.move(Direction.WEST);
            case '>' -> position.move(Direction.EAST);
            default -> throw new IllegalStateException("Unknown direction " + c);
        };
    }

    @Test
    void inputExample() {
        assertThat(countDeliveriesPartOne(">")).isEqualTo(2);
        assertThat(countDeliveriesPartOne("^>v<")).isEqualTo(4);
        assertThat(countDeliveriesPartOne("^v^v^v^v^v")).isEqualTo(2);

        assertThat(countDeliveriesPartTwo(">")).isEqualTo(2);
        assertThat(countDeliveriesPartTwo("^>v<")).isEqualTo(3);
        assertThat(countDeliveriesPartTwo("^v^v^v^v^v")).isEqualTo(11);
    }

    /**
     * --- Day 3: Perfectly Spherical Houses in a Vacuum ---
     *
     * Santa is delivering presents to an infinite two-dimensional grid of houses.
     *
     * He begins by delivering a present to the house at his starting location,
     * and then an elf at the North Pole calls him via radio and tells him where
     * to move next. Moves are always exactly one house to the north (^), south
     * (v), east (>), or west (<). After each move, he delivers another present to
     * the house at his new location.
     *
     * However, the elf back at the north pole has had a little too much eggnog,
     * and so his directions are a little off, and Santa ends up visiting some
     * houses more than once. How many houses receive at least one present?
     *
     * For example:
     *
     *   - > delivers presents to 2 houses: one at the starting location, and one
     *     to the east.
     *   - ^>v< delivers presents to 4 houses in a square, including twice to the
     *     house at his starting/ending location.
     *   - ^v^v^v^v^v delivers a bunch of presents to some very lucky children at
     *     only 2 houses.
     *
     * Your puzzle answer was 2565.
     */
    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day03Test.class.getResourceAsStream("/2015/day/3/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));

            assertThat(countDeliveriesPartOne(scanner.nextLine())).isEqualTo(2565);
        }
    }

    /**
     * --- Part Two ---
     *
     * The next year, to speed up the process, Santa creates a robot version of
     * himself, Robo-Santa, to deliver presents with him.
     *
     * Santa and Robo-Santa start at the same location (delivering two presents to
     * the same starting house), then take turns moving based on instructions from
     * the elf, who is eggnoggedly reading from the same script as the previous
     * year.
     *
     * This year, how many houses receive at least one present?
     *
     * For example:
     *
     *   - ^v delivers presents to 3 houses, because Santa goes north, and then
     *     Robo-Santa goes south.
     *   - ^>v< now delivers presents to 3 houses, and Santa and Robo-Santa end
     *     up back where they started.
     *   - ^v^v^v^v^v now delivers presents to 11 houses, with Santa going one
     *     direction and Robo-Santa going the other.
     *
     * Your puzzle answer was 2639.
     */
    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day03Test.class.getResourceAsStream("/2015/day/3/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(countDeliveriesPartTwo(scanner.nextLine())).isEqualTo(2639);
        }
    }
}
