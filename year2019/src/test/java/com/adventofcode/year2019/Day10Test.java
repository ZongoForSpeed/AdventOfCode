package com.adventofcode.year2019;

import com.adventofcode.common.point.Point2D;
import com.adventofcode.test.AbstractTest;
import com.google.common.collect.Iterables;
import it.unimi.dsi.fastutil.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test extends AbstractTest {
    Day10Test() {
        super(2019, 10);
    }

    @Test
    void testSimpleExample() {
        String input = """
                .#..#
                .....
                #####
                ....#
                ...##""";

        try (Scanner scanner = new Scanner(input)) {
            Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(3, 4));
            assertThat(result.right()).hasSize(8);
        }

    }

    @Test
    void testLargerExample1() {
        String input = """
                ......#.#.
                #..#.#....
                ..#######.
                .#.#.###..
                .#..#.....
                ..#....#.#
                #..#....#.
                .##.#..###
                ##...#..#.
                .#....####""";

        try (Scanner scanner = new Scanner(input)) {
            Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(5, 8));
            assertThat(result.right()).hasSize(33);
        }
    }

    @Test
    void testLargerExample2() {
        String input = """
                #.#...#.#.
                .###....#.
                .#....#...
                ##.#.#.#.#
                ....#.#.#.
                .##..###.#
                ..#...##..
                ..##....##
                ......#...
                .####.###.""";

        try (Scanner scanner = new Scanner(input)) {
            Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(1, 2));
            assertThat(result.right()).hasSize(35);
        }
    }

    @Test
    void testLargerExample3() {
        String input = """               
                .#..#..###
                ####.###.#
                ....###.#.
                ..###.##.#
                ##.##.#.#.
                ....###..#
                ..#.#..#.#
                #..#.#.###
                .##...##.#
                .....#.#..""";

        try (Scanner scanner = new Scanner(input)) {
            Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(6, 3));
            assertThat(result.right()).hasSize(41);
        }
    }

    @Test
    void testLargerExample4() {
        String input = """
                .#..##.###...#######
                ##.############..##.
                .#.######.########.#
                .###.#######.####.#.
                #####.##.#.##.###.##
                ..#####..#.#########
                ####################
                #.####....###.#.#.##
                ##.#################
                #####.##.###..####..
                ..######..##.#######
                ####.##.####...##..#
                .#####..#.######.###
                ##...#.##########...
                #.##########.#######
                .####.#.###.###.#.##
                ....##.##.###..#####
                .#.#.###########.###
                #.#.#.#####.####.###
                ###.##.####.##.#..##""";

        try (Scanner scanner = new Scanner(input)) {
            Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(11, 13));
            assertThat(result.right()).hasSize(210);
        }
    }

    @Test
    void testVaporizeSimpleExample() {
        String input = """
                .#....#####...#..
                ##...##.#####..##
                ##...#...#.#####.
                ..#.....#...###..
                ..#.#.....#....##""";
        try (Scanner scanner = new Scanner(input)) {
            Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(8, 3));
            assertThat(result.right()).hasSize(30);

            List<Day10.Asteroids> asteroids = Day10.vaporizeAsteroids(result.right());
            Day10.Asteroids lastAsteroid = Iterables.getLast(asteroids);
            assertThat(lastAsteroid.position()).isEqualTo(Point2D.of(14, 3));
        }

    }

    @Test
    void testVaporizeLargerExample() {
        String input = """
                .#..##.###...#######
                ##.############..##.
                .#.######.########.#
                .###.#######.####.#.
                #####.##.#.##.###.##
                ..#####..#.#########
                ####################
                #.####....###.#.#.##
                ##.#################
                #####.##.###..####..
                ..######..##.#######
                ####.##.####...##..#
                .#####..#.######.###
                ##...#.##########...
                #.##########.#######
                .####.#.###.###.#.##
                ....##.##.###..#####
                .#.#.###########.###
                #.#.#.#####.####.###
                ###.##.####.##.#..##""";
        try (Scanner scanner = new Scanner(input)) {
            Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(11, 13));
            assertThat(result.right()).hasSize(210);

            List<Day10.Asteroids> asteroids = Day10.vaporizeAsteroids(result.right());

            assertThat(asteroids).hasSize(299);

            Day10.Asteroids lastAsteroid = Iterables.getLast(asteroids);
            assertThat(lastAsteroid.position()).isEqualTo(Point2D.of(11, 1));

            Day10.Asteroids asteroid = asteroids.get(199);
            assertThat(asteroid.getCoordinate()).isEqualTo(802);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(scanner);
        assertThat(result.left()).isEqualTo(Point2D.of(26, 29));
        assertThat(result.right()).hasSize(303);
    }

    @Override
    public void partTwo(Scanner scanner) {
        Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(scanner);

        List<Day10.Asteroids> asteroids = Day10.vaporizeAsteroids(result.right());
        Day10.Asteroids asteroid = asteroids.get(199);
        assertThat(asteroid.getCoordinate()).isEqualTo(408);
    }
}
