package com.adventofcode.year2016;

import com.adventofcode.common.point.map.BooleanMap;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day18Test {

    @Test
    void inputExample() {
        String input = "..^^.";
        BooleanMap booleanMap = Day18.getTiles(input, 3);
        assertThat(booleanMap).hasToString("""
                ..##.
                .####
                ##..#
                """);
    }

    @Test
    void inputLargerExample() {
        String input = ".^^.^.^^^^";
        BooleanMap booleanMap = Day18.getTiles(input, 10);
        assertThat(booleanMap).hasToString("""
                .##.#.####
                ###...#..#
                #.##.#.##.
                ..##...###
                .####.##.#
                ##..#.##..
                ####..###.
                #..####.##
                .###..#.##
                ##.###..##
                """);
        booleanMap.trim();
        int size = booleanMap.yMax() * booleanMap.xMax();
        assertThat(size - booleanMap.cardinality()).isEqualTo(38);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day18Test.class.getResourceAsStream("/2016/day/18/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day18.mapSize(scanner, 40)).isEqualTo(1987);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day18Test.class.getResourceAsStream("/2016/day/18/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day18.mapSize(scanner, 400000)).isEqualTo(19984714);
        }
    }
}
