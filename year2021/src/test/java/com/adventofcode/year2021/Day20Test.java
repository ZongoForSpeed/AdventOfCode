package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day20Test {

    @Test
    void inputExample() {
        String input = """
                ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..###..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###.######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#..#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#......#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#.....####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.......##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#
                        
                #..#.
                #....
                ##..#
                ..#..
                ..###""";

        assertThat(Day20.enhanceImage(new Scanner(input), 2)).isEqualTo(35);
        assertThat(Day20.enhanceImage(new Scanner(input), 50)).isEqualTo(3351);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day20Test.class.getResourceAsStream("/2021/day/20/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day20.enhanceImage(scanner, 2)).isEqualTo(5819);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day20Test.class.getResourceAsStream("/2021/day/20/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day20.enhanceImage(scanner, 50)).isEqualTo(18516);
        }
    }

}
