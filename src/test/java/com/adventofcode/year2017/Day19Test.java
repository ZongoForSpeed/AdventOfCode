package com.adventofcode.year2017;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;


class Day19Test {

    @Test
    void inputExample() {
        String input = """
                     |         \s
                     |  +--+   \s
                     A  |  C   \s
                 F---|----E|--+\s
                     |  |  |  D\s
                     +B-+  +--+\s
                """;

        assertThat(Day19.findPacketsPartOne(new Scanner(input))).isEqualTo("ABCDEF");
        assertThat(Day19.findPacketsPartTwo(new Scanner(input))).isEqualTo(38);
    }

    @Test
    void inputPartOne() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/19/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day19.findPacketsPartOne(scanner)).isEqualTo("XYFDJNRCQA");
        }
    }

    @Test
    void inputPartTwo() throws IOException {
        try (InputStream is = Day02Test.class.getResourceAsStream("/2017/day/19/input"); Scanner scanner = new Scanner(Objects.requireNonNull(is))) {
            assertThat(Day19.findPacketsPartTwo(scanner)).isEqualTo(17450);
        }
    }
}
