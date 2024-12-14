package com.adventofcode.year2021;

import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day20Test extends AbstractTest {
    Day20Test() {
        super(2021, 20);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day20.enhanceImage(scanner, 2)).isEqualTo(5819);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day20.enhanceImage(scanner, 50)).isEqualTo(18516);
    }

}
