package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class Day06Test {

    @Test
    void inputExample() {
        String input = "3,4,3,1,2";

        assertThat(Day06.nextDays(input, 18)).isEqualTo(26);
        assertThat(Day06.nextDays(input, 80)).isEqualTo(5934);
        assertThat(Day06.nextDays(input, 256)).isEqualTo(26984457539L);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day06Test.class.getResourceAsStream("/2021/day/6/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day06.nextDays(scanner.nextLine(), 80)).isEqualTo(386640);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day06Test.class.getResourceAsStream("/2021/day/6/input")) {
            Scanner scanner = new Scanner(Objects.requireNonNull(is));
            assertThat(Day06.nextDays(scanner.nextLine(), 256)).isEqualTo(1733403626279L);
        }
    }
}
