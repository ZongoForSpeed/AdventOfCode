package com.adventofcode.year2021;

import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@AdventOfCode(year = 2021, day = 20)
class Day20Test {
    @Test
    void inputExample() {
        var input = """
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
    void partOne(Scanner scanner) {
        assertThat(Day20.enhanceImage(scanner, 2)).isEqualTo(5819);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day20.enhanceImage(scanner, 50)).isEqualTo(18516);
    }

}
