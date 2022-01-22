package com.adventofcode.year2015;

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
                Butterscotch: capacity -1, durability -2, flavor 6, texture 3, calories 8
                Cinnamon: capacity 2, durability 3, flavor -2, texture -1, calories 3""";

        assertThat(Day15.highestCookieScore(new Scanner(input), false)).isEqualTo(62842880);
        assertThat(Day15.highestCookieScore(new Scanner(input), true)).isEqualTo(57600000);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day15Test.class.getResourceAsStream("/2015/day/15/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day15.highestCookieScore(scanner, false)).isEqualTo(222870);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day15Test.class.getResourceAsStream("/2015/day/15/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day15.highestCookieScore(scanner, true)).isEqualTo(117936);
        }
    }

}
