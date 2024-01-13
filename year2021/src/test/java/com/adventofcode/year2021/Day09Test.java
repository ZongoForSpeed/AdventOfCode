package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day09Test {

    @Test
    void inputExample() {
        String input = """
                2199943210
                3987894921
                9856789892
                8767896789
                9899965678""";

        assertThat(Day09.smokeBasinRisk(new Scanner(input))).isEqualTo(15);
        assertThat(Day09.smokeBasin(new Scanner(input))).isEqualTo(1134);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day09Test.class.getResourceAsStream("/2021/day/9/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day09.smokeBasinRisk(scanner)).isEqualTo(524);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day09Test.class.getResourceAsStream("/2021/day/9/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day09.smokeBasin(scanner)).isEqualTo(1235430);
        }
    }
}
