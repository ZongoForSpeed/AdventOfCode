package com.adventofcode.year2016;

import com.adventofcode.common.point.map.BooleanMap;
import com.adventofcode.test.AdventOfCode;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


@AdventOfCode(year = 2016, day = 18)
class Day18Test {
    @Test
    void inputExample() {
        var input = "..^^.";
        BooleanMap booleanMap = Day18.getTiles(input, 3);
        assertThat(booleanMap).hasToString("""
                ..##.
                .####
                ##..#
                """);
    }

    @Test
    void inputLargerExample() {
        var input = ".^^.^.^^^^";
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
        int size = (booleanMap.yMax() + 1) * (booleanMap.xMax() + 1);
        assertThat(size - booleanMap.cardinality()).isEqualTo(38);
    }

    @Test
    void partOne(Scanner scanner) {
        assertThat(Day18.mapSize(scanner, 40)).isEqualTo(1987);
    }

    @Test
    void partTwo(Scanner scanner) {
        assertThat(Day18.mapSize(scanner, 400000)).isEqualTo(19984714);
    }
}
