package com.adventofcode.year2018;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day06Test {

    @Test
    void inputExample() {
        String input = """
                1, 1
                1, 6
                8, 3
                3, 4
                5, 5
                8, 9""";

        assertThat(Day06.maxArea(new Scanner(input))).isEqualTo(17);
        assertThat(Day06.region(new Scanner(input), 30)).isEqualTo(16);
    }


    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day06Test.class.getResourceAsStream("/2018/day/6/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day06.maxArea(scanner)).isEqualTo(4060);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day06Test.class.getResourceAsStream("/2018/day/6/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day06.region(scanner, 10000)).isEqualTo(36136);
        }
    }

}
