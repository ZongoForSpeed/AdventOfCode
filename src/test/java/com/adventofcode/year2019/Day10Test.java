package com.adventofcode.year2019;

import com.adventofcode.map.Point2D;
import com.adventofcode.utils.FileUtils;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class Day10Test {

    @Test
    void testSimpleExample() {
        List<String> map = Arrays.asList(
                ".#..#",
                ".....",
                "#####",
                "....#",
                "...##");
        Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(map);
        assertThat(result.getLeft()).isEqualTo(Point2D.of(3, 4));
        assertThat(result.getRight()).hasSize(8);
    }

    @Test
    void testLargerExample1() {
        List<String> map = Arrays.asList(
                "......#.#.",
                "#..#.#....",
                "..#######.",
                ".#.#.###..",
                ".#..#.....",
                "..#....#.#",
                "#..#....#.",
                ".##.#..###",
                "##...#..#.",
                ".#....####");
        Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(map);
        assertThat(result.getLeft()).isEqualTo(Point2D.of(5, 8));
        assertThat(result.getRight()).hasSize(33);
    }

    @Test
    void testLargerExample2() {
        List<String> map = Arrays.asList(
                "#.#...#.#.",
                ".###....#.",
                ".#....#...",
                "##.#.#.#.#",
                "....#.#.#.",
                ".##..###.#",
                "..#...##..",
                "..##....##",
                "......#...",
                ".####.###.");
        Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(map);
        assertThat(result.getLeft()).isEqualTo(Point2D.of(1, 2));
        assertThat(result.getRight()).hasSize(35);
    }

    @Test
    void testLargerExample3() {
        List<String> map = Arrays.asList(
                ".#..#..###",
                "####.###.#",
                "....###.#.",
                "..###.##.#",
                "##.##.#.#.",
                "....###..#",
                "..#.#..#.#",
                "#..#.#.###",
                ".##...##.#",
                ".....#.#..");
        Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(map);
        assertThat(result.getLeft()).isEqualTo(Point2D.of(6, 3));
        assertThat(result.getRight()).hasSize(41);
    }

    @Test
    void testLargerExample4() {
        List<String> map = Arrays.asList(
                ".#..##.###...#######",
                "##.############..##.",
                ".#.######.########.#",
                ".###.#######.####.#.",
                "#####.##.#.##.###.##",
                "..#####..#.#########",
                "####################",
                "#.####....###.#.#.##",
                "##.#################",
                "#####.##.###..####..",
                "..######..##.#######",
                "####.##.####...##..#",
                ".#####..#.######.###",
                "##...#.##########...",
                "#.##########.#######",
                ".####.#.###.###.#.##",
                "....##.##.###..#####",
                ".#.#.###########.###",
                "#.#.#.#####.####.###",
                "###.##.####.##.#..##");
        Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(map);
        assertThat(result.getLeft()).isEqualTo(Point2D.of(11, 13));
        assertThat(result.getRight()).hasSize(210);
    }

    @Test
    void testInputPartOne() throws IOException {
        Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(FileUtils.readLines("/2019/day/10/input"));
        assertThat(result.getLeft()).isEqualTo(Point2D.of(26, 29));
        assertThat(result.getRight()).hasSize(303);
    }

    @Test
    void testVaporizeSimpleExample() {
        List<String> map = Arrays.asList(
                ".#....#####...#..",
                "##...##.#####..##",
                "##...#...#.#####.",
                "..#.....#...###..",
                "..#.#.....#....##");
        Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(map);
        assertThat(result.getLeft()).isEqualTo(Point2D.of(8, 3));
        assertThat(result.getRight()).hasSize(30);

        List<Day10.Asteroids> asteroids = Day10.vaporizeAsteroids(result.getRight());
        Day10.Asteroids lastAsteroid = Iterables.getLast(asteroids);
        assertThat(lastAsteroid.position()).isEqualTo(Point2D.of(14, 3));
    }

    @Test
    void testVaporizeLargerExample() {
        List<String> map = Arrays.asList(
                ".#..##.###...#######",
                "##.############..##.",
                ".#.######.########.#",
                ".###.#######.####.#.",
                "#####.##.#.##.###.##",
                "..#####..#.#########",
                "####################",
                "#.####....###.#.#.##",
                "##.#################",
                "#####.##.###..####..",
                "..######..##.#######",
                "####.##.####...##..#",
                ".#####..#.######.###",
                "##...#.##########...",
                "#.##########.#######",
                ".####.#.###.###.#.##",
                "....##.##.###..#####",
                ".#.#.###########.###",
                "#.#.#.#####.####.###",
                "###.##.####.##.#..##");
        Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(map);
        assertThat(result.getLeft()).isEqualTo(Point2D.of(11, 13));
        assertThat(result.getRight()).hasSize(210);

        List<Day10.Asteroids> asteroids = Day10.vaporizeAsteroids(result.getRight());

        assertThat(asteroids).hasSize(299);

        Day10.Asteroids lastAsteroid = Iterables.getLast(asteroids);
        assertThat(lastAsteroid.position()).isEqualTo(Point2D.of(11, 1));

        Day10.Asteroids asteroid = asteroids.get(199);
        assertThat(asteroid.getCoordinate()).isEqualTo(802);
    }

    @Test
    void testInputPartTwo() throws IOException {
        Pair<Point2D, Map<Double, List<Day10.Asteroids>>> result = Day10.findBestLocation(FileUtils.readLines("/2019/day/10/input"));
        assertThat(result.getLeft()).isEqualTo(Point2D.of(26, 29));
        assertThat(result.getRight()).hasSize(303);

        List<Day10.Asteroids> asteroids = Day10.vaporizeAsteroids(result.getRight());
        Day10.Asteroids asteroid = asteroids.get(199);
        assertThat(asteroid.getCoordinate()).isEqualTo(408);
    }

}
