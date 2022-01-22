package com.adventofcode.year2016;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day20Test {

    @Test
    void inputExample() {
        String input = """
                5-8
                0-2
                4-7""";

        assertThat(Day20.findFirstIP(new Scanner(input), 9)).isEqualTo(3);
        assertThat(Day20.countIPs(new Scanner(input), 9)).isEqualTo(2);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day20Test.class.getResourceAsStream("/2016/day/20/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day20.findFirstIP(scanner, 4294967295L)).isEqualTo(4793564);
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day20Test.class.getResourceAsStream("/2016/day/20/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day20.countIPs(scanner, 4294967295L)).isEqualTo(146);
        }
    }

}
