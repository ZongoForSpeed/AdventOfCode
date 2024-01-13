package com.adventofcode.year2015;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day17Test {

    @Test
    void inputExample() {
        String input = """
                20
                15
                10
                5
                5""";

        assertThat(Day17.findCombinationsPartOne(new Scanner(input), 25)).isEqualTo(4);
        assertThat(Day17.findCombinationsPartTwo(new Scanner(input), 25)).isEqualTo(3);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day17Test.class.getResourceAsStream("/2015/day/17/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day17.findCombinationsPartOne(scanner, 150)).isEqualTo(4372);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day17Test.class.getResourceAsStream("/2015/day/17/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day17.findCombinationsPartTwo(scanner, 150)).isEqualTo(4);
        }
    }
}
