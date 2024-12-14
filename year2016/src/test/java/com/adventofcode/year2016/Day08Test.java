package com.adventofcode.year2016;

import com.adventofcode.common.point.map.BooleanMap;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day08Test extends AbstractTest {
    Day08Test() {
        super(2016, 8);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day08.cardinality(scanner)).isEqualTo(121);
    }

    @Override
    public void partTwo(Scanner scanner) {
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
