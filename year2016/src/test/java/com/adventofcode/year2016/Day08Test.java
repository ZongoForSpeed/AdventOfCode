package com.adventofcode.year2016;

import com.adventofcode.common.point.map.BooleanMap;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day08Test {

    @Test
    void inputExample() {
        String input = """
                rect 3x2
                rotate column x=1 by 1
                rotate row y=0 by 4
                rotate column x=1 by 1""";

        Scanner scanner = new Scanner(input);

        BooleanMap charMap = Day08.buildScreen(scanner, 7, 3);
        assertThat(charMap).hasToString("""
                .#..#.#
                #.#....
                .#.....
                """);
        assertThat(charMap.points()).hasSize(6);

    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day08Test.class.getResourceAsStream("/2016/day/8/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day08.cardinality(scanner)).isEqualTo(121);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day08Test.class.getResourceAsStream("/2016/day/8/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
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
}
