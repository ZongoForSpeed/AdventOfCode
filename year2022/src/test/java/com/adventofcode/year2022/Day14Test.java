package com.adventofcode.year2022;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Day14Test {


    @Test
    void inputExample() {
        String input = """
                498,4 -> 498,6 -> 496,6
                503,4 -> 502,4 -> 502,9 -> 494,9""";

        {
            Scanner scanner = new Scanner(input);
            assertThat(Day14.partOne(scanner)).isEqualTo(24);
        }

        {
            Scanner scanner = new Scanner(input);
            assertThat(Day14.partTwo(scanner)).isEqualTo(93);
        }
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2022/day/14/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day14.partOne(scanner)).isEqualTo(795);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream inputStream = Day14Test.class.getResourceAsStream("/2022/day/14/input");
             Scanner scanner = new Scanner(Objects.requireNonNull(inputStream))) {
            assertThat(Day14.partTwo(scanner)).isEqualTo(30214);
        }
    }
}
