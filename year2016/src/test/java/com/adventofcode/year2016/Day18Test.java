package com.adventofcode.year2016;

import com.adventofcode.common.point.map.BooleanMap;
import com.adventofcode.test.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day18Test extends AbstractTest {
    Day18Test() {
        super(2016, 18);
    }

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

    @Override
    public void partOne(Scanner scanner) {
        assertThat(Day18.mapSize(scanner, 40)).isEqualTo(1987);
    }

    @Override
    public void partTwo(Scanner scanner) {
        assertThat(Day18.mapSize(scanner, 400000)).isEqualTo(19984714);
    }
}
