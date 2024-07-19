package com.adventofcode.year2022;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day15Test {

    @Test
    void inputExample() {
        String input = """
                Sensor at x=2, y=18: closest beacon is at x=-2, y=15
                Sensor at x=9, y=16: closest beacon is at x=10, y=16
                Sensor at x=13, y=2: closest beacon is at x=15, y=3
                Sensor at x=12, y=14: closest beacon is at x=10, y=16
                Sensor at x=10, y=20: closest beacon is at x=10, y=16
                Sensor at x=14, y=17: closest beacon is at x=10, y=16
                Sensor at x=8, y=7: closest beacon is at x=2, y=10
                Sensor at x=2, y=0: closest beacon is at x=2, y=10
                Sensor at x=0, y=11: closest beacon is at x=2, y=10
                Sensor at x=20, y=14: closest beacon is at x=25, y=17
                Sensor at x=17, y=20: closest beacon is at x=21, y=22
                Sensor at x=16, y=7: closest beacon is at x=15, y=3
                Sensor at x=14, y=3: closest beacon is at x=15, y=3
                Sensor at x=20, y=1: closest beacon is at x=15, y=3""";

        {
            Scanner scanner = new Scanner(input);
            assertThat(Day15.partOne(scanner, 10)).isEqualTo(26);
        }

        {
            Scanner scanner = new Scanner(input);
            assertThat(Day15.partTwo(scanner, 20)).isEqualTo(56000011);
        }

    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2022/day/15/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day15.partOne(scanner, 2000000)).isEqualTo(6275922);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2022/day/15/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day15.partTwo(scanner, 4000000)).isEqualTo(11747175442119L);
        }
    }

}
