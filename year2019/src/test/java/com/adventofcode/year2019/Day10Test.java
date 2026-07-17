package com.adventofcode.year2019;

import com.adventofcode.common.point.Point2D;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test extends AbstractTest {
    Day10Test() {
        super(2019, 10);
    }

    @Test
    void simpleExample() {
        var input = """
                .#..#
                .....
                #####
                ....#
                ...##""";

        try (var scanner = new Scanner(input)) {
            var result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(3, 4));
            assertThat(result.right()).hasSize(8);
        }

    }

    @Test
    void largerExample1() {
        var input = """
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

        try (var scanner = new Scanner(input)) {
            var result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(5, 8));
            assertThat(result.right()).hasSize(33);
        }
    }

    @Test
    void largerExample2() {
        var input = """
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

        try (var scanner = new Scanner(input)) {
            var result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(1, 2));
            assertThat(result.right()).hasSize(35);
        }
    }

    @Test
    void largerExample3() {
        var input = """               
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

        try (var scanner = new Scanner(input)) {
            var result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(6, 3));
            assertThat(result.right()).hasSize(41);
        }
    }

    @Test
    void largerExample4() {
        var input = """
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

        try (var scanner = new Scanner(input)) {
            var result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(11, 13));
            assertThat(result.right()).hasSize(210);
        }
    }

    @Test
    void vaporizeSimpleExample() {
        var input = """
                .#....#####...#..
                ##...##.#####..##
                ##...#...#.#####.
                ..#.....#...###..
                ..#.#.....#....##""";
        try (var scanner = new Scanner(input)) {
            var result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(8, 3));
            assertThat(result.right()).hasSize(30);

            var asteroids = Day10.vaporizeAsteroids(result.right());
            Day10.Asteroids lastAsteroid = asteroids.getLast();
            assertThat(lastAsteroid.position()).isEqualTo(Point2D.of(14, 3));
        }

    }

    @Test
    void vaporizeLargerExample() {
        var input = """
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
        try (var scanner = new Scanner(input)) {
            var result = Day10.findBestLocation(scanner);
            assertThat(result.left()).isEqualTo(Point2D.of(11, 13));
            assertThat(result.right()).hasSize(210);

            var asteroids = Day10.vaporizeAsteroids(result.right());

            assertThat(asteroids).hasSize(299);

            Day10.Asteroids lastAsteroid = asteroids.getLast();
            assertThat(lastAsteroid.position()).isEqualTo(Point2D.of(11, 1));

            Day10.Asteroids asteroid = asteroids.get(199);
            assertThat(asteroid.getCoordinate()).isEqualTo(802);
        }
    }

    @Override
    public void partOne(Scanner scanner) {
        var result = Day10.findBestLocation(scanner);
        assertThat(result.left()).isEqualTo(Point2D.of(26, 29));
        assertThat(result.right()).hasSize(303);
    }

    @Override
    public void partTwo(Scanner scanner) {
        var result = Day10.findBestLocation(scanner);

        var asteroids = Day10.vaporizeAsteroids(result.right());
        Day10.Asteroids asteroid = asteroids.get(199);
        assertThat(asteroid.getCoordinate()).isEqualTo(408);
    }
}
