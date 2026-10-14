package com.adventofcode.year2016;

import com.adventofcode.common.point.map.BooleanMap;
import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 8)
class Day08Test {
    @Test
    void inputExample() {
        var input = """
                rect 3x2
                rotate column x=1 by 1
                rotate row y=0 by 4
                rotate column x=1 by 1""";

        var scanner = new Scanner(input);

        BooleanMap charMap = Day08.buildScreen(scanner, 7, 3);
        assertThat(charMap).hasToString("""
                .#..#.#
                #.#....
                .#.....
                """);
        assertThat(charMap.points()).hasSize(6);

    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day08.cardinality(scanner)).isEqualTo(121);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day08.buildScreen(scanner, 50, 6)).hasToString("""
                ###..#..#.###..#..#..##..####..##..####..###.#....
                #..#.#..#.#..#.#..#.#..#.#....#..#.#......#..#....
                #..#.#..#.#..#.#..#.#....###..#..#.###....#..#....
                ###..#..#.###..#..#.#....#....#..#.#......#..#....
                #.#..#..#.#.#..#..#.#..#.#....#..#.#......#..#....
                #..#..##..#..#..##...##..####..##..####..###.####.
                """);
    }
}
