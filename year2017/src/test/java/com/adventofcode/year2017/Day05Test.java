package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day05Test {

    @Test
    void inputExample() {
        String input = """
                0
                3
                0
                1
                -3""";

        assertThat(Day05.countStepsPartOne(new Scanner(input))).isEqualTo(5);
        assertThat(Day05.countStepsPartTwo(new Scanner(input))).isEqualTo(10);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/5/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day05.countStepsPartOne(scanner)).isEqualTo(378980);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/5/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day05.countStepsPartTwo(scanner)).isEqualTo(26889114);
        }
    }

}
